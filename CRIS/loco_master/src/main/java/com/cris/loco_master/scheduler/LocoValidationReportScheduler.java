package com.cris.loco_master.scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.cris.loco_master.Paths;
import com.cris.loco_master.email.EmailUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.base.text.Severity;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.scheduler.ScheduledExecutionContext;
import com.orchestranetworks.scheduler.ScheduledTask;
import com.orchestranetworks.scheduler.ScheduledTaskInterruption;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.ValidationReport;
import com.orchestranetworks.service.ValidationReportItem;
import com.orchestranetworks.service.ValidationReportItemIterator;
import com.orchestranetworks.service.ValidationReportItemSubjectForAdaptation;

public class LocoValidationReportScheduler extends ScheduledTask {

	private String dataspace;
	private String dataset;
	private String table;
	private String emailSubject;
	private String emailBody;
	private String emailRecipientsRole;
	private String emailRecipientsUsers;
	private String emailRecipientsCC;

	@Override
	public void execute(ScheduledExecutionContext arg0) throws OperationException, ScheduledTaskInterruption {
		// TODO Auto-generated method stub

		Repository repository = Repository.getDefault();

		AdaptationHome dataspaceName;
		Adaptation datasetName;
		AdaptationTable adaptationTable = null;

		dataspaceName = repository.lookupHome(HomeKey.forBranchName(dataspace));
		datasetName = dataspaceName.findAdaptationOrNull(AdaptationName.forName(dataset));
		adaptationTable = datasetName.getTable(Path.parse(table));

		ValidationReport validationReport = adaptationTable.getValidationReport();
		ValidationReportItemIterator validationReportItemIterator = validationReport.getItemsOfSeverity(Severity.ERROR);

		FileWriter fw = null;
		String attachmentFileName = readValidationReportFileName();

		File existingFile = new File(attachmentFileName);

		if (existingFile.exists() && existingFile.isFile()) {
			existingFile.delete();
		}

		try {
			fw = new FileWriter(attachmentFileName, true);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {
			fw.append("Loco Type");
			fw.append(",");
			fw.append("Error Message");
			fw.write(System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		while (validationReportItemIterator.hasNext()) {
			ValidationReportItem validationReportItem = validationReportItemIterator.nextItem();

			try {

				ValidationReportItemSubjectForAdaptation validationReportItemSubjectForAdaptation = validationReportItem
						.getSubjectForAdaptation();
				if (validationReportItemSubjectForAdaptation != null) {

					Adaptation validationRecord = validationReportItemSubjectForAdaptation.getAdaptation();

					String locoType = (String) validationRecord.get(Paths._Root_Loco_Type._Root_Loco_Type_Loco_Type);

					fw.append(locoType);
					fw.append(",");

					fw.append(validationReportItem.toString());
					fw.write(System.getProperty("line.separator"));
				}
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		try {
			fw.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// Email with attachment'
		EmailUtils emailUtils = new EmailUtils(emailSubject, emailBody, emailRecipientsRole, emailRecipientsUsers,
				emailRecipientsCC, attachmentFileName);

		try {
			emailUtils.sendMessage();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String getDataspace() {
		return dataspace;
	}

	public void setDataspace(String dataspace) {
		this.dataspace = dataspace;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public String getEmailRecipientsRole() {
		return emailRecipientsRole;
	}

	public void setEmailRecipientsRole(String emailRecipientsRole) {
		this.emailRecipientsRole = emailRecipientsRole;
	}

	public String getEmailRecipientsUsers() {
		return emailRecipientsUsers;
	}

	public void setEmailRecipientsUsers(String emailRecipientsUsers) {
		this.emailRecipientsUsers = emailRecipientsUsers;
	}

	public String getEmailRecipientsCC() {
		return emailRecipientsCC;
	}

	public void setEmailRecipientsCC(String emailRecipientsCC) {
		this.emailRecipientsCC = emailRecipientsCC;
	}

	private String readValidationReportFileName() {

		FileReader reader;
		Properties properties = new Properties();
		try {
			reader = new FileReader(System.getProperties().getProperty("ebx.properties"));
			properties.load(reader);
		} catch (FileNotFoundException e1) {
			// TODO: handle exception
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return properties.getProperty("ebx.loco.loco.validationreportfile");

	}

}