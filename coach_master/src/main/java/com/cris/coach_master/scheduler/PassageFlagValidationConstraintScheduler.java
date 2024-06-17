package com.cris.coach_master.scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.cris.coach_master.Paths;
import com.cris.coach_master.email.EmailUtils;
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
import com.orchestranetworks.service.ValidationReportItem;
import com.orchestranetworks.service.ValidationReportItemIterator;
import com.orchestranetworks.service.ValidationReportItemSubjectForAdaptation;

public class PassageFlagValidationConstraintScheduler extends ScheduledTask {

	private String dataspace;
	private String dataset;
	private String table;
	private String emailSubject;
	private String emailBody;
//	private String emailRecipients;
	private String emailRecipientsRoles;
	private String emailRecipientsUsers;
	private String emailRecipientsCC;

	public String getEmailRecipientsCC() {
		return emailRecipientsCC;
	}

	public void setEmailRecipientsCC(String emailRecipientsCC) {
		this.emailRecipientsCC = emailRecipientsCC;
	}

//	public String getEmailRecipients() {
//		return emailRecipients;
//	}
//
//	public void setEmailRecipients(String emailRecipients) {
//		this.emailRecipients = emailRecipients;
//	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
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

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	@Override
	public void execute(ScheduledExecutionContext context) throws OperationException, ScheduledTaskInterruption {
		// TODO Auto-generated method stub
		// Session session = context.getSession();
		Repository repository = Repository.getDefault();

		AdaptationHome dataspaceName;
		Adaptation datasetName;
		AdaptationTable adaptationTable = null;

		dataspaceName = repository.lookupHome(HomeKey.forBranchName(dataspace));
		datasetName = dataspaceName.findAdaptationOrNull(AdaptationName.forName(dataset));
		adaptationTable = datasetName.getTable((Path.parse(table)));

		com.orchestranetworks.service.ValidationReport validationReport = adaptationTable.getValidationReport();
		ValidationReportItemIterator validationReportItemIterator = validationReport.getItemsOfSeverity(Severity.ERROR);

		FileWriter fw = null;
		String attachmentFileName = readValidationReportFileName();
		// System.out.println("===Validation Report File===" + attachmentFileName);

		File existingFile = new File(attachmentFileName);

		if (existingFile.exists() && existingFile.isFile()) {
			existingFile.delete();
		}

		try {
			fw = new FileWriter(attachmentFileName, true);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			fw.append("Row Number,");
			fw.append("Column Number,");
			fw.append("Coach Class,");
			fw.append("Deck ID,");
			fw.append("Seat Layout ID,");
			fw.append("Error Message");
			fw.write(System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (validationReportItemIterator.hasNext()) {
			ValidationReportItem validationReportItem = validationReportItemIterator.nextItem();
			// validationReportItem.toString();

			try {
				if (validationReportItem.toString().contains("Passage Flag")) {
					ValidationReportItemSubjectForAdaptation validationReportItemSubjectForAdaptation = validationReportItem
							.getSubjectForAdaptation();
					Adaptation validationRecord = validationReportItemSubjectForAdaptation.getAdaptation();
					Integer rowNo = (Integer) validationRecord
							.get(Paths._Root_Seat_Layout_Details._Root_Seat_Layout_Details_Row_No);
					Integer columnNo = (Integer) validationRecord
							.get(Paths._Root_Seat_Layout_Details._Root_Seat_Layout_Details_Col_No);
					String coachClass = (String) validationRecord
							.get(Paths._Root_Seat_Layout_Details._Root_Seat_Layout_Details_Coach_Class);
					String deckId = (String) validationRecord
							.get(Paths._Root_Seat_Layout_Details._Root_Seat_Layout_Details_Deck_Id);
					String seatLayoutId = (String) validationRecord
							.get(Paths._Root_Seat_Layout_Details._Root_Seat_Layout_Details_Seat_Layout_Id);

					fw.append(Integer.toString(rowNo));
					fw.append(",");
					fw.append(Integer.toString(columnNo));
					fw.append(",");
					fw.append(coachClass);
					fw.append(",");
					fw.append(deckId);
					fw.append(",");
					fw.append((seatLayoutId));
					fw.append(",");

					fw.append(validationReportItem.toString());
					fw.write(System.getProperty("line.separator"));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Email with Attachment
		EmailUtils emailUtils = new EmailUtils(emailSubject, emailBody, emailRecipientsRoles, emailRecipientsUsers,
				emailRecipientsCC, attachmentFileName);
		try {
			emailUtils.sendMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getEmailRecipientsRoles() {
		return emailRecipientsRoles;
	}

	public void setEmailRecipientsRoles(String emailRecipientsRoles) {
		this.emailRecipientsRoles = emailRecipientsRoles;
	}

	public String getEmailRecipientsUsers() {
		return emailRecipientsUsers;
	}

	public void setEmailRecipientsUsers(String emailRecipientsUsers) {
		this.emailRecipientsUsers = emailRecipientsUsers;
	}

	private String readValidationReportFileName() {

		FileReader reader;
		Properties prop = new Properties();
		try {
			reader = new FileReader(System.getProperties().getProperty("ebx.properties"));
			prop.load(reader);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop.getProperty("ebx.coach.seatlayoutdetails.validationreportfile");

	}
}
