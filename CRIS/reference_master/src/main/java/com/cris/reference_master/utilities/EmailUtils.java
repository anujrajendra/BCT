package com.cris.reference_master.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.cris.reference_master.Paths;
import com.cris.reference_master.constants.Constants;
import com.cris.reference_master.logger.ModuleLogger;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.service.directory.DirectoryHandler;

public class EmailUtils {

	private String emailTemplateId;
	private Message emailMessage;
	private Multipart multipart;
	private String emailSubject;
	private String emailBody;

	DirectoryHandler directoryHandler = DirectoryHandler.getInstance(Repository.getDefault());

	public EmailUtils(String emailTemplateID) {
		super();
		this.emailTemplateId = emailTemplateID;
	}

	public void sendMessage() throws Exception {
		ModuleLogger.logger.info("Starting to send message with template ID: " + emailTemplateId);

		Adaptation emailTemplateRecord = RepositoryUtils.getRecordFromPrimaryKey(Constants.emailTemplateDataspace,
				Constants.emailTemplateDataset, Constants.emailTemplateTable, this.emailTemplateId);
		ModuleLogger.logger.info("Fetched email template record for ID: " + emailTemplateId);

		this.multipart = new MimeMultipart();

		getEmailBaseMessage();
		readEmailTemplates(emailTemplateRecord);
		setRecipientsValueForEmail(emailTemplateRecord);

		String attachmentFileName = readFileName();
		addAttachments(attachmentFileName);

		this.emailMessage.setContent(multipart);

		Address[] address = this.emailMessage.getAllRecipients();
		if (address != null && address.length > 0) {
			Transport.send(emailMessage);
			ModuleLogger.logger.info("Email sent successfully.");
			moveFileToBackup(attachmentFileName); // Move the attachment to backup folder after sending email
		} else {
			ModuleLogger.logger.warn("No recipients found for the email.");
		}
	}

	void getEmailBaseMessage() throws Exception {
		ModuleLogger.logger.info("Setting up email base message.");

		String ebxPropertiesPath = System.getProperties().getProperty("ebx.properties");

		try (FileReader reader = new FileReader(ebxPropertiesPath)) {
			Properties prop = new Properties();
			prop.load(reader);

			String smtpHost = prop.getProperty("ebx.mail.smtp.host");
			String smtpPort = prop.getProperty("ebx.mail.smtp.port");
			String smtpLogin = prop.getProperty("ebx.mail.smtp.login");
			String smtpPassword = prop.getProperty("ebx.mail.smtp.password");
			String smtpFrom = prop.getProperty("ebx.mail.smtp.from");

			ModuleLogger.logger.info("SMTP Host: " + smtpHost);
			ModuleLogger.logger.info("SMTP Port: " + smtpPort);
			ModuleLogger.logger.info("SMTP Login: " + smtpLogin);
			ModuleLogger.logger.info("SMTP From: " + smtpFrom);

			Properties properties = System.getProperties();
			properties.setProperty("mail.smtp.host", smtpHost);

			javax.mail.Session mailSession = null;
			if (smtpPassword != null) {
				properties.setProperty("mail.smtp.port", smtpPort);
				properties.put("mail.smtp.auth", "true");

				mailSession = javax.mail.Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(smtpLogin, smtpPassword);
					}
				});
			} else {
				mailSession = javax.mail.Session.getInstance(properties, null);
			}

			this.emailMessage = new MimeMessage(mailSession);
			this.emailMessage.setFrom(new InternetAddress(smtpFrom));
			ModuleLogger.logger.info("Email base message set up completed.");
		} catch (IOException e) {
			ModuleLogger.logger.error("Error reading properties file: " + ebxPropertiesPath, e);
			throw e;
		}
	}

	void readEmailTemplates(Adaptation emailTemplateRecord) throws Exception {
		ModuleLogger.logger.info("Reading email templates.");
		this.emailSubject = emailTemplateRecord.getString(Paths._Root_Email_Templates._Root_Email_Templates_Email_Subject);
		this.emailBody = emailTemplateRecord.getString(Paths._Root_Email_Templates._Root_Email_Templates_Email_Templates);
		ModuleLogger.logger.info("Email subject and body read successfully.");
	}

	void setRecipientsValueForEmail(Adaptation emailTemplateRecord) throws Exception {
		ModuleLogger.logger.info("Setting recipients for the email.");

		List<Adaptation> emailVariableRecords = RepositoryUtils.getListOfAssociatedRecords(
				Paths._Root_Email_Templates._Root_Email_Templates_Email_Variable, emailTemplateRecord);

		for (Adaptation adaptation : emailVariableRecords) {
			String users = adaptation.getString(Paths._Root_Email_Variables._Root_Email_Variables_User);
			List<String> userNameList = Arrays.asList(users.split(","));

			for (String userName : userNameList) {
				String userEmail = directoryHandler.getUserEmail(UserReference.forUser(userName));
				if (userEmail != null) {
					InternetAddress[] addresses = InternetAddress.parse(userEmail.trim());
					this.emailMessage.addRecipients(Message.RecipientType.TO, addresses);
					ModuleLogger.logger.info("Added recipient: " + userEmail);
				} else {
					ModuleLogger.logger.warn("No email found for user: " + userName);
				}
			}
		}

		this.emailMessage.setSubject(this.emailSubject.trim());
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(this.emailBody);
		multipart.addBodyPart(messageBodyPart);

		ModuleLogger.logger.info("Recipients set successfully.");
	}

	void addAttachments(String attachmentFileName) throws Exception {
		ModuleLogger.logger.info("Adding attachment: " + attachmentFileName);

		MimeBodyPart attachmentPart = new MimeBodyPart();
		DataSource source = new FileDataSource(attachmentFileName);
		attachmentPart.setDataHandler(new DataHandler(source));
		attachmentPart.setFileName(extractFileName(attachmentFileName));
		multipart.addBodyPart(attachmentPart);

		ModuleLogger.logger.info("Attachment added successfully.");
	}

	public String getEmailTemplateId() {
		return emailTemplateId;
	}

	public void setEmailTemplateId(String emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	private String extractFileName(String attachmentFileName) {
		return new File(attachmentFileName).getName();
	}

	private String readFileName() {
		FileReader reader;
		Properties properties = new Properties();
		try {
			reader = new FileReader(System.getProperties().getProperty("ebx.properties"));
			properties.load(reader);
		} catch (FileNotFoundException e) {
			ModuleLogger.logger.error("Properties file not found while reading attachment file name.", e);
		} catch (IOException e) {
			ModuleLogger.logger.error("Error reading properties file while reading attachment file name.", e);
		}

		return properties.getProperty("ebx.attachment.attachment.file");
	}

	private void moveFileToBackup(String attachmentFileName) {
		File file = new File(attachmentFileName);
		ModuleLogger.logger.info("Moving attachment to backup folder: " + file.getAbsolutePath());

		if (!file.exists()) {
			ModuleLogger.logger.warn("Attachment file does not exist: " + file.getAbsolutePath());
			return;
		}

		File parentFolder = file.getParentFile();
		File backupFolder = new File(parentFolder, "backup");
		if (!backupFolder.exists()) {
			backupFolder.mkdirs();
		}

		File backupFile = new File(backupFolder, file.getName());
		try {
			Files.move(file.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			ModuleLogger.logger.info("Attachment moved to backup folder successfully: " + backupFile.getPath());
		} catch (IOException e) {
			ModuleLogger.logger.error("Failed to move attachment to backup folder: " + backupFile.getPath(), e);
		}
	}
}
