package com.cris.coach_master.workflow.scripttask;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.cris.coach_master.email.EmailUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.base.text.Severity;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.service.ValidationReportItem;
import com.orchestranetworks.service.ValidationReportItemIterator;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class ValidationConstraintReportScriptTask extends ScriptTaskBean {

	private String dataspace;
	private String dataset;
	private String table;
	private String record;

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

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	@Override
	public void executeScript(ScriptTaskBeanContext context) throws OperationException {
		// TODO Auto-generated method stub

		Session session = context.getSession();

		Repository repo;
		AdaptationHome dataspaceName;
		Adaptation datasetName;
		AdaptationTable adaptationTable = null;
		Adaptation adaptationRecord;

		repo = Repository.getDefault();
		dataspaceName = repo.lookupHome(HomeKey.forBranchName(dataspace));
		datasetName = dataspaceName.findAdaptationOrNull(AdaptationName.forName(dataset));

//		String tableString = record.substring(0, record.indexOf("["));
//
//		String recordIdentifier = record.substring(record.indexOf("=") + 2, record.indexOf("]") - 1);

		adaptationTable = datasetName.getTable((Path.parse("/root/Seat_Layout_Details")));
		// adaptationRecord =
		// adaptationTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(recordIdentifier));

		com.orchestranetworks.service.ValidationReport validationReport = adaptationTable.getValidationReport();
		ValidationReportItemIterator validationReportItemIterator = validationReport.getItemsOfSeverity(Severity.ERROR);

		FileWriter fw = null;
		System.out.println("===Validation Report File===" + readValidationReportFileName());

//		File existingFile = new File(readValidationReportFileName());
//
//		if (existingFile.exists() && existingFile.isFile()) {
//			existingFile.delete();
//		}

		try {
			fw = new FileWriter(readValidationReportFileName(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (validationReportItemIterator.hasNext()) {
			ValidationReportItem validationReportItem = validationReportItemIterator.nextItem();
			// validationReportItem.toString();
			try {
				fw.append(validationReportItem.toString());
				fw.write(System.getProperty("line.separator"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		EmailUtils emailUtils = new EmailUtils("Hello World", "Hello World", "none", "anuj.rajendra@bahwancybertek.com",
				"none", "D:/EBX/ebxHome/PassageFlagValidationReport.txt");
		try {
			emailUtils.sendMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
