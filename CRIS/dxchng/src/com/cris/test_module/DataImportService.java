package com.cris.test_module;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.bct.addon.dxch.rule.DataRules;
import com.cris.test_module.procedure.InitiatorProcedure;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.ProgrammaticService;
import com.orchestranetworks.ui.UICSSClasses;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.UserServiceDisplayConfigurator;
import com.orchestranetworks.userservice.UserServiceEventOutcome;
import com.orchestranetworks.userservice.UserServiceObjectContextBuilder;
import com.orchestranetworks.userservice.UserServicePane;
import com.orchestranetworks.userservice.UserServicePaneContext;
import com.orchestranetworks.userservice.UserServicePaneWriter;
import com.orchestranetworks.userservice.UserServiceProcessEventOutcomeContext;
import com.orchestranetworks.userservice.UserServiceSetupDisplayContext;
import com.orchestranetworks.userservice.UserServiceSetupObjectContext;
import com.orchestranetworks.userservice.UserServiceValidateContext;

public class DataImportService implements UserService<TableViewEntitySelection> {

	@Override
	public UserServiceEventOutcome processEventOutcome(
			UserServiceProcessEventOutcomeContext<TableViewEntitySelection> context, UserServiceEventOutcome arg1) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<TableViewEntitySelection> context,
			UserServiceDisplayConfigurator config) {
		// TODO Auto-generated method stub
		config.setContent(new UserServicePane() {

			@Override
			public void writePane(UserServicePaneContext aContext, UserServicePaneWriter pWriter) {
				// TODO Auto-generated method stub

				Repository repository = Repository.getDefault();

				pWriter.add("<div").addSafeAttribute("class", UICSSClasses.CONTAINER_WITH_TEXT).add(">");

				pWriter.add("</div>");

				String floatDivStyle = "float:left; width:100%; ";

				pWriter.add("<div style='" + floatDivStyle + " min-width:200px;'>");
				// pWriter.startBorder(true);
				pWriter.add(
						"<table style= 'border-collapse: collapse;'><tr><td style='margin-top: 3px; width: 30px; min-width: 30px; max-width: 30px; vertical-align:top'>");

				pWriter.add(
						"<tr><td style='white-space: nowrap; color: gray; border: 1px solid; font-weight: bold;'>Procedure Executed</div></td>");

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

//					String sql = "call DQ_TEST_2()";
//					Statement statement = connection.createStatement();
//					statement.execute(sql);

					DataRules dataRules = new DataRules();

					final HomeKey dbConfigDataSpaceKey = HomeKey.forBranchName("db_config");
					final AdaptationHome dbConfigDataspaceName = repository.lookupHome(dbConfigDataSpaceKey);

					final AdaptationName dbConfigDataSetKey = AdaptationName.forName("db_config");
					final Adaptation dbConfigDatasetName = dbConfigDataspaceName
							.findAdaptationOrNull(dbConfigDataSetKey);

					AdaptationTable dataSourceTable = dbConfigDatasetName.getTable(Path.parse("/root/DataSourceTable"));

					// Adaptation tgtRecord =
					// dataSourceTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString("3"));

					Adaptation tgtRecord = null;

					String findLocoReconciliationTableEntryQuery = "Select s.\"$adaptation\" from \"/root/DataSourceTable\" s where s.tableName like '%"
							+ "loco_reconciliation" + "%'";
					Query<Tuple> locoReconciliationTuple = dbConfigDatasetName
							.createQuery(findLocoReconciliationTableEntryQuery);
					QueryResult<Tuple> locoReconciliationRecords = locoReconciliationTuple.getResult();

					for (Tuple locoReconciliationRecord : locoReconciliationRecords) {
						tgtRecord = (Adaptation) locoReconciliationRecord.get(0);

						InitiatorProcedure initiatorProcedure = new InitiatorProcedure(tgtRecord);

						final HomeKey testDataSpaceKey = HomeKey.forBranchName("loco_reconciliation");
						final AdaptationHome testDataspaceName = repository.lookupHome(testDataSpaceKey);
						ProgrammaticService svc = ProgrammaticService.createForSession(aContext.getSession(),
								testDataspaceName);
						svc.execute(initiatorProcedure);

						dataRules.importData(tgtRecord, initiatorProcedure.getColumns());
					}

					LoggingCategory.getWorkflow().info("===Procedure Executed====");

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pWriter.startTableFormRow();
				pWriter.endTableFormRow();

				pWriter.add("</td></tr></table>");
				// pWriter.endBorder();
				pWriter.add("</div>");
			}
		});

	}

	@Override
	public void setupObjectContext(UserServiceSetupObjectContext<TableViewEntitySelection> arg0,
			UserServiceObjectContextBuilder arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate(UserServiceValidateContext<TableViewEntitySelection> arg0) {
		// TODO Auto-generated method stub

	}

}
