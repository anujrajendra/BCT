package com.cris.loco_master.service.transferredlocos;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import com.cris.loco_master.constant.Constants;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.Role;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.service.directory.DirectoryHandler;
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

public class TransferredLocosService implements UserService<TableViewEntitySelection> {

	@Override
	public UserServiceEventOutcome processEventOutcome(
			UserServiceProcessEventOutcomeContext<TableViewEntitySelection> arg0, UserServiceEventOutcome arg1) {
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
				DirectoryHandler directoryHandler = DirectoryHandler.getInstance(repository);
				String userId = aContext.getSession().getUserReference().getUserId();

				pWriter.add("<div").addSafeAttribute("class", UICSSClasses.CONTAINER_WITH_TEXT).add(">");

				pWriter.add("</div>");

				String floatDivStyle = "float:left; width:100%; ";

				pWriter.add("<div style='" + floatDivStyle + " min-width:200px;'>");
				// pWriter.startBorder(true);
				pWriter.add(
						"<table style= 'border-collapse: collapse;'><tr><td style='margin-top: 3px; width: 30px; min-width: 30px; max-width: 30px; vertical-align:top'>");

				pWriter.add(
						"<tr><td style='white-space: nowrap; color: gray; border: 1px solid; font-weight: bold;'>Loco Number</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;font-weight: bold;'>From Zone</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;font-weight: bold;'>To Zone</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;font-weight: bold;'>From Division</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;font-weight: bold;'>To Division</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;font-weight: bold;'>From Shed</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;font-weight: bold;'>To Shed</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;font-weight: bold;'>Source System Name</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;font-weight: bold;'>User Name</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;font-weight: bold;'>Transaction Id</div></td>");

				pWriter.add(
						"<td style='white-space: nowrap; color: gray; border: 1px solid;font-weight: bold;'>Transaction Date</div></td>");

				pWriter.add("</tr>");

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

					if (directoryHandler.isUserInRole(UserReference.forUser(userId), Role.ADMINISTRATOR)) {

						String sql = "Select * from V_LOCO_TRANFER_HISTORY";
						Statement statement = connection.createStatement();
						ResultSet result = statement.executeQuery(sql);

						displayResult(result, pWriter);

					} else {
						String userPermissionsShedQuery = "SELECT s.\"$adaptation\" FROM \"/root/User_Permissions_Shed\" s "
								+ "WHERE FK_AS_STRING('user_data','/root/User_Registration_Details', s.Ipas_Id) = '"
								+ userId + "'";

						Query<Tuple> userPermissionsShedQueryTuple = Constants.DATASET_USERREGISTRATION
								.createQuery(userPermissionsShedQuery);

						QueryResult<Tuple> userPermissionsShedRecords = userPermissionsShedQueryTuple.getResult();

						for (Tuple shedResult : userPermissionsShedRecords) {

							Adaptation userPermissionsShedRecord = (Adaptation) shedResult.get(0);
							String shedValue = (String) userPermissionsShedRecord.get(Path.parse("./Shed_Code"));

							String sql = "Select * from V_LOCO_TRANFER_HISTORY where from_shed = '" + shedValue + "'";
							Statement statement = connection.createStatement();
							ResultSet result = statement.executeQuery(sql);

							displayResult(result, pWriter);
						}
					}

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

	private static void displayResult(ResultSet result, UserServicePaneWriter pWriter) throws SQLException {
		while (result.next()) {

//			String valueString = "";
//			String locoNumber = "";
//			String fromZone = "";
//			String toZone = "";
//			String fromDivision = "";
//			String toDivision = "";
			String fromShed = "";
			String toShed = "";
//			String sourceSystemName = "";
//			String userName = "";
//			String transactionId = "";

//				if (valueObject != null) {
//					valueString = valueObject.toString();
//					locoNumber = result.getObject(1).toString();
//					fromZone = result.getObject(2).toString();
//					toZone = result.getObject(3).toString();
//					fromDivision = result.getObject(4).toString();
//					toDivision = result.getObject(5).toString();
//					fromShed = result.getObject(6).toString();
//					toShed = result.getObject(7).toString();
//					sourceSystemName = result.getObject(8).toString();
//					userName = result.getObject(9).toString();
//					transactionId = result.getObject(10).toString();
			//
//				}

			Object fromShedObject = result.getObject(6);
			Object toShedObject = result.getObject(7);

			if (fromShedObject != null && toShedObject != null) {
				fromShed = fromShedObject.toString();
				toShed = toShedObject.toString();

			}
			if (fromShed.equalsIgnoreCase(toShed))
				continue;

			pWriter.add("<tr>");
			pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + result.getObject(1)
					+ "</div></td>");
			pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + result.getObject(2)
					+ "</div></td>");

			pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + result.getObject(3)
					+ "</div></td>");

			pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + result.getObject(4)
					+ "</div></td>");
			pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + result.getObject(5)
					+ "</div></td>");
			pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + result.getObject(6)
					+ "</div></td>");

			pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + result.getObject(7)
					+ "</div></td>");

			pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + result.getObject(8)
					+ "</div></td>");
			pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + result.getObject(9)
					+ "</div></td>");
			pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>" + result.getObject(10)
					+ "</div></td>");

			Long bigInteger = (Long) result.getObject(11);

			pWriter.add("<td style='white-space: nowrap; color: gray; border: 1px solid;'>"
					+ new Date(bigInteger.longValue()) + "</div></td>");

		}
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
