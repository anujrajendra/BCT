package com.cris.reference_master.scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.orchestranetworks.addon.dataexchange.DataExchangeException;
import com.orchestranetworks.addon.dex.CSVImportDataExchangeHelperContext;
import com.orchestranetworks.addon.dex.DataExchangeHelperFactory;
import com.orchestranetworks.addon.dex.DataExchangeServiceFactory;
import com.orchestranetworks.addon.dex.result.DataExchangeResult;
import com.orchestranetworks.addon.dex.result.DataExchangeResult.CSVImport;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.scheduler.ScheduledExecutionContext;
import com.orchestranetworks.scheduler.ScheduledTask;
import com.orchestranetworks.scheduler.ScheduledTaskInterruption;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.OperationException;

public class ReconciliationImportFileScheduler extends ScheduledTask {

	private String entity;
	private String dataspace;
	private String dataset;
	private String table;
	private String preferenceName;
	private String fileName;

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

//		File importFileName = new File(readImportFileName(entity));

		File importFileName = new File(fileName);

		CSVImportDataExchangeHelperContext dexhelperContext = new CSVImportDataExchangeHelperContext(preferenceName,
				adaptationTable, context.getSession());
		dexhelperContext.setImportedFile(importFileName);

		com.orchestranetworks.addon.dex.DataExchangeSpec dataExchangeSpec = null;
		try {
			dataExchangeSpec = DataExchangeHelperFactory.getDataExchangeHelper().getDataExchangeSpec(dexhelperContext);

			DataExchangeResult.CSVImport dataExchangeResult = (CSVImport) DataExchangeServiceFactory
					.getDataExchangeService().execute(dataExchangeSpec);
		} catch (DataExchangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String readImportFileName(String entity) {

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
		switch (entity) {
		case "Loco":
			return prop.getProperty("ebx.loco.reconciliation.importfile");
		case "Station":
			return prop.getProperty("ebx.station.reconciliation.importfile");
		case "Wagon":
			return prop.getProperty("ebx.wagon.reconciliation.importfile");
		case "Coach":
			return prop.getProperty("ebx.coach.reconciliation.importfile");
		default:
			return prop.getProperty("ebx.loco.reconciliation.importfile");
		}

	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
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

	public String getPreferenceName() {
		return preferenceName;
	}

	public void setPreferenceName(String preferenceName) {
		this.preferenceName = preferenceName;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
