package com.cris.coach_master.email;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.onwbp.org.apache.commons.io.IOUtils;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.service.Profile;
import com.orchestranetworks.service.Role;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.service.directory.DirectoryHandler;

public class EmailUtils {

	private Repository repository;
	private Session session;

	private Message emailMessage;
	private Multipart multipart;
	private String emailSubject;
	private String emailBody;
	private String emailRecipients;

	private String emailRecipientsRoles;
	private String emailRecipientsUsers;
	private String emailRecipientsCC;

	private String attachmentFileName;

	DirectoryHandler directoryHandler = DirectoryHandler.getInstance(Repository.getDefault());

	public EmailUtils(String emailSubject, String emailBody, String emailRecipientsRoles, String emailRecipientsUsers,
			String emailRecipientsCC, String attachmentFileName) {
		super();
		this.repository = Repository.getDefault();
		// this.session = session;
		this.emailSubject = emailSubject;
		this.emailBody = emailBody;

		this.emailRecipientsRoles = emailRecipientsRoles;
		this.emailRecipientsUsers = emailRecipientsUsers;
		this.emailRecipientsCC = emailRecipientsCC;
		this.attachmentFileName = attachmentFileName;
	}

	public void sendMessage() throws Exception {

		// Get the email template record, email subject and email body.

		this.multipart = new MimeMultipart();

		getEmailBaseMessage();
		setRecipients();
		setSubjectBody();
		addAttachments();

		this.emailMessage.setContent(multipart);

		Address[] address = this.emailMessage.getAllRecipients();
		// System.out.println("===Inside Email Utils Send Message===");
		if (address != null && address.length > 0) {
			Transport.send(emailMessage);
			// ModuleLogger.logger.info("Email Successfully sent with subject: " +
			// this.emailSubject);
		} else {
			// ModuleLogger.logger.info("No Recipients to send the mail");
		}

	}

	void getEmailBaseMessage() throws Exception {

		String ebxPropertiesPath = System.getProperties().getProperty("ebx.properties");
		// ModuleLogger.logger.info(ebxPropertiesPath);

		try (FileReader reader = new FileReader(ebxPropertiesPath)) {

			Properties prop = new Properties();
			prop.load(reader);

			String smtpHost = prop.getProperty("ebx.mail.smtp.host");
			// ModuleLogger.logger.info(smtpHost);

			String smtpPort = prop.getProperty("ebx.mail.smtp.port");
			// ModuleLogger.logger.info(smtpPort);

			String smtpLogin = prop.getProperty("ebx.mail.smtp.login");
			// ModuleLogger.logger.info(smtpLogin);

			String smtpPassword = prop.getProperty("ebx.mail.smtp.password");
			// ModuleLogger.logger.info(smtpPassword);

			// Getting system properties
			Properties properties = System.getProperties();

			// Setting up mail server
			properties.setProperty("mail.smtp.host", smtpHost);

			javax.mail.Session mailSession = null;
			if (smtpPassword != null) {

				properties.setProperty("mail.smtp.port", smtpPort);
				properties.put("mail.smtp.auth", "true");

				mailSession = javax.mail.Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
					@Override
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication(smtpLogin, smtpPassword);
					}
				});
			} else {
				mailSession = javax.mail.Session.getInstance(properties, null);
			}

			// Create a default MimeMessage object.
			this.emailMessage = new MimeMessage(mailSession);

			// Set From: header field of the header.
			this.emailMessage.setFrom(new InternetAddress(smtpLogin));

		}
	}

	void setRecipients() throws Exception {

//		String emailAddressesValue = this.emailRecipients;
//
//		if (emailAddressesValue != null && emailAddressesValue.trim().length() > 0) {
//			InternetAddress[] addresses = InternetAddress.parse(emailAddressesValue.trim());
//			this.emailMessage.addRecipients(Message.RecipientType.TO, addresses);
//		}

		if (!emailRecipientsRoles.equalsIgnoreCase("none")) {
			List<String> rolesName = Arrays.asList(emailRecipientsRoles.split(","));
			for (String roleName : rolesName) {
				if (roleName != null) {
					Role role = Profile.forSpecificRole(roleName);
					String roleEmail = directoryHandler.getRoleEmail(role);

					if (roleEmail != null) {
						InternetAddress[] addresses = InternetAddress.parse(roleEmail.trim());
						this.emailMessage.addRecipients(Message.RecipientType.TO, addresses);
					}

					List<UserReference> listOfUsers = directoryHandler.getUsersInRole(role);
					for (UserReference userReference : listOfUsers) {

						String userEmail = directoryHandler.getUserEmail(userReference);
						if (userEmail != null) {
							InternetAddress[] addresses = InternetAddress.parse(userEmail.trim());
							this.emailMessage.addRecipients(Message.RecipientType.TO, addresses);
						}
					}
				}
			}
		}

		if (!emailRecipientsUsers.equalsIgnoreCase("none")) {
			List<String> usersName = Arrays.asList(emailRecipientsUsers.split(","));
			for (String userName : usersName) {
				String userEmail = directoryHandler.getUserEmail(UserReference.forUser(userName));
				if (userEmail != null) {
					InternetAddress[] addresses = InternetAddress.parse(userEmail.trim());
					this.emailMessage.addRecipients(Message.RecipientType.TO, addresses);
				}
			}
		}

		if (!emailRecipientsCC.equalsIgnoreCase("none")) {
			List<String> ccUsersName = Arrays.asList(emailRecipientsCC.split(","));
			for (String ccUserName : ccUsersName) {
				String userEmail = directoryHandler.getUserEmail(UserReference.forUser(ccUserName));
				if (userEmail != null) {
					InternetAddress[] addresses = InternetAddress.parse(userEmail.trim());
					this.emailMessage.addRecipients(Message.RecipientType.CC, addresses);
				}
			}
		}
	}

	void setSubjectBody() throws MessagingException {

		this.emailMessage.setSubject(this.emailSubject.trim());
		BodyPart messageBodyPart = new MimeBodyPart();
		// Now set the actual message
		messageBodyPart.setText(this.emailBody);
		// Set text message part
		multipart.addBodyPart(messageBodyPart);
	}

	@SuppressWarnings("unchecked")
	void addAttachments() throws Exception {

		InputStream inputstream = new FileInputStream(attachmentFileName);

		final MimeBodyPart attachmentPart = new MimeBodyPart();

		ByteArrayDataSource source = new ByteArrayDataSource(new ByteArrayInputStream(IOUtils.toByteArray(inputstream)),
				"*/*");

		attachmentPart.setDataHandler(new DataHandler(source));

		// String physicalName = digitalAsset.getPhysicalName();
		attachmentPart.setFileName("PassageFlagValidationReportFile");

		multipart.addBodyPart(attachmentPart);

	}
}
