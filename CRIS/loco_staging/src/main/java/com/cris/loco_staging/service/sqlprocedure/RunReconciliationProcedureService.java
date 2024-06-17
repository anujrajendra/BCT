package com.cris.loco_staging.service.sqlprocedure;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.service.LoggingCategory;
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

public class RunReconciliationProcedureService implements UserService<TableViewEntitySelection> {

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
						"<tr><td style='white-space: nowrap; color: gray; border: 1px solid; font-weight: bold;'> Procedure Executed </div></td>");

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

					String sql = "call DQ_TEST()";
					Statement statement = connection.createStatement();
					statement.execute(sql);

					LoggingCategory.getWorkflow().info("===Procedure Executed====");

				} catch (SQLException e) {
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
