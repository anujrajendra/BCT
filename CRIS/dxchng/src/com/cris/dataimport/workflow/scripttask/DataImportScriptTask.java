package com.cris.dataimport.workflow.scripttask;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.bct.addon.dxch.rule.DataRules;
import com.cris.dataimport.procedure.InitiatorProcedure;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.ProgrammaticService;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class DataImportScriptTask extends ScriptTaskBean {

	private String dbDataspaceName;
	private String dbDatasetName;
	private String targetDataspaceName;
	private String targetTableName;

	@Override
	public void executeScript(ScriptTaskBeanContext aContext) throws OperationException {
		// TODO Auto-generated method stub
		Repository repository = Repository.getDefault();
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
		String jdbcURL = prop.getProperty("ebx.persistence.url");
		String username = prop.getProperty("ebx.persistence.user");
		String password = prop.getProperty("ebx.persistence.password");

		try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {

			DataRules dataRules = new DataRules();

			final HomeKey dbConfigDataSpaceKey = HomeKey.forBranchName(dbDataspaceName);
			final AdaptationHome dbConfigDataspaceName = repository.lookupHome(dbConfigDataSpaceKey);

			final AdaptationName dbConfigDataSetKey = AdaptationName.forName(dbDatasetName);
			final Adaptation dbConfigDatasetName = dbConfigDataspaceName.findAdaptationOrNull(dbConfigDataSetKey);

			// AdaptationTable dataSourceTable =
			// dbConfigDatasetName.getTable(Path.parse("/root/DataSourceTable"));

			// Adaptation tgtRecord =
			// dataSourceTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString("3"));

			Adaptation tgtRecord = null;

			String findLocoReconciliationTableEntryQuery = "Select s.\"$adaptation\" from \"/root/DataSourceTable\" s where s.tableName like '%"
					+ targetTableName + "%'";
			Query<Tuple> locoReconciliationTuple = dbConfigDatasetName
					.createQuery(findLocoReconciliationTableEntryQuery);
			QueryResult<Tuple> locoReconciliationRecords = locoReconciliationTuple.getResult();

			for (Tuple locoReconciliationRecord : locoReconciliationRecords) {
				tgtRecord = (Adaptation) locoReconciliationRecord.get(0);

				InitiatorProcedure initiatorProcedure = new InitiatorProcedure(tgtRecord);

				final HomeKey locoReconcialitionDataSpaceKey = HomeKey.forBranchName(targetDataspaceName);
				final AdaptationHome locoReconcialitionDataspaceName = repository
						.lookupHome(locoReconcialitionDataSpaceKey);
				ProgrammaticService svc = ProgrammaticService.createForSession(aContext.getSession(),
						locoReconcialitionDataspaceName);
				svc.execute(initiatorProcedure);

				dataRules.importData(tgtRecord, initiatorProcedure.getColumns());
			}

			// LoggingCategory.getWorkflow().info("===Procedure Executed====");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDbDataspaceName() {
		return dbDataspaceName;
	}

	public void setDbDataspaceName(String dbDataspaceName) {
		this.dbDataspaceName = dbDataspaceName;
	}

	public String getDbDatasetName() {
		return dbDatasetName;
	}

	public void setDbDatasetName(String dbDatasetName) {
		this.dbDatasetName = dbDatasetName;
	}

	public String getTargetDataspaceName() {
		return targetDataspaceName;
	}

	public void setTargetDataspaceName(String targetDataspaceName) {
		this.targetDataspaceName = targetDataspaceName;
	}

	public String getTargetTableName() {
		return targetTableName;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

}
