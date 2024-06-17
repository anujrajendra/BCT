package com.cris.loco_master.service.spotfirescript;

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

public class SpotfireDashboardService implements UserService<TableViewEntitySelection> {

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
			public void writePane(UserServicePaneContext arg0, UserServicePaneWriter pWriter) {
				// TODO Auto-generated method stub
				pWriter.add(
						"<script type=\"text/javascript\" src=\"http://10.55.16.37/spotfire/wp/GetJavaScriptApi.ashx?Version=7.5\">\r\n"
								+ "</script>\r\n" + "\r\n" + "<script>\r\n" + "	\r\n" + "	\r\n" + "\r\n"
								+ "    var c_serverUrl = \"http://10.55.16.37/spotfire/wp/\";\r\n" + "\r\n"
								+ "    var c_analysisPath = \"/loco_dashboard/loco\";\r\n" + "	\r\n"
								+ "    var c_parameters = \"\";\r\n" + "\r\n"
								+ "    var customization = new spotfire.webPlayer.Customization();\r\n" + "\r\n"
								+ "    var app;\r\n" + "\r\n" + "    var c_reloadAnalysisInstance = false;\r\n" + "\r\n"
								+ "\r\n" + "    window.onload = function()\r\n" + "\r\n" + "    {\r\n" + "\r\n"
								+ "    app = new spotfire.webPlayer.Application(c_serverUrl, customization,c_analysisPath,\r\n"
								+ "\r\n" + "    c_parameters,c_reloadAnalysisInstance);\r\n" + "\r\n"
								+ "    //Hide UI elements\r\n" + "\r\n" + "    customization.showDodPanel = false;\r\n"
								+ "\r\n" + "    customization.showStatusBar = true;\r\n" + "\r\n"
								+ "    customization.showToolBar = true;\r\n" + "\r\n"
								+ "    customization. showPageNavigation = true;\r\n" + "\r\n"
								+ "    customization.showClose = false;\r\n" + "\r\n"
								+ "    customization.showAnalysisInfo = true;\r\n" + "\r\n"
								+ "    customization.showExportFile = true;\r\n" + "\r\n"
								+ "    customization.showExportVisualization = true;\r\n" + "\r\n"
								+ "    customization.showUndoRedo = true;\r\n" + "\r\n"
								+ "    customization.showFilterPanel = true;\r\n" + "\r\n"
								+ "    var viewOne = app.openDocument(\"demoViz\" , \"contents\",customization);\r\n"
								+ "\r\n"
								+ "    var viewTwo = app.openDocument(\"demoViz2\" , \"zone/division/shed\",customization);\r\n"
								+ "\r\n" + "    }\r\n" + "\r\n" + "</script>");

				pWriter.add("      <div id=\"demoViz\"></div>\r\n" + "\r\n" + "        <br>\r\n" + "\r\n"
						+ "        <div id=\"demoViz2\"></div>");

				pWriter.add("Anuj");
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
