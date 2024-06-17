package com.cris.common.history.service;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.UIHttpManagerComponent;
import com.orchestranetworks.ui.UIHttpManagerComponent.ViewFeatures;
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

public class HistoryPageService implements UserService<TableViewEntitySelection> {

	@Override
	public UserServiceEventOutcome processEventOutcome(
			UserServiceProcessEventOutcomeContext<TableViewEntitySelection> context, UserServiceEventOutcome outcome) {

		return null;
	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<TableViewEntitySelection> context,
			UserServiceDisplayConfigurator config) {

		AdaptationTable table = context.getEntitySelection().getTable();
		Adaptation dataset = table.getContainerAdaptation();

		UIHttpManagerComponent httpComponent = config.createWebComponentForSubSession();
		httpComponent.setService(ServiceKey.DEFAULT_SERVICE);
		httpComponent.selectNode(dataset, table.getTablePath());
		httpComponent.hideViewFeature(ViewFeatures.TITLE);

		String url = httpComponent.getURIWithParameters();

		config.setContent(new UserServicePane() {

			@Override
			public void writePane(UserServicePaneContext context, UserServicePaneWriter writer) {

				writer.add(" <iframe id='tableFrame' src=\"" + url
						+ " \" frameborder=\"0\" style=\"overflow:hidden;height:100%;width:100%\" height=\"100%\" width=\"100%\"></iframe>");

				writer.addJS("var isViewSetup = false;\r\n");
				
				writer.addJS("function checkIframeLoaded() { \r\n "
						+ "var iframe = document.getElementById('tableFrame'); \r\n "
						+ "var iframeDoc = iframe.contentWindow.document; \r\n "
						+ "if (iframeDoc.getElementById('ebx-legacy-component') !== null) { \r\n"
						+ "var xpathActions = \"//*[text()='Actions']\";\r\n"
						+ "var matchingElementActions = document.evaluate(xpathActions, document.getElementById('tableFrame').contentWindow.document.getElementById('ebx-legacy-component').contentWindow.document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\r\n"
						+ "if(matchingElementActions !== null) { \r\n" + "matchingElementActions.click();\r\n" + "\r\n"
						+ "var xpathViewHistory = \"//*[text()='View history']\";\r\n"
						+ "var matchingElementViewHistory = document.evaluate(xpathViewHistory, document.getElementById('tableFrame').contentWindow.document.getElementById('ebx-legacy-component').contentWindow.document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\r\n"
						+ "if(matchingElementViewHistory !== null) {\r\n"
						+ "" 
						+ "matchingElementViewHistory.click();\r\n"
						+ "isViewSetup = true;\r\n"						
						+ "return; \r\n" + "}; \r\n " + "}; \r\n " + "}; \r\n "
						+ "window.setTimeout(checkIframeLoaded, 2000); \r\n" + "}\r\n");
				
				writer.addJS("function checkViewSetup() { \r\n "
						+ "console.log('checkViewSetup'); \r\n"
						+ "if (!isViewSetup) { \r\n"
						+ "window.setTimeout(checkViewSetup, 2000); \r\n"
						+ "return;"
						+ "}\r\n"
						+ "var iframe = document.getElementById('tableFrame'); \r\n "
						+ "console.log('IFrame: ' + iframe); \r\n"
						+ "var iframeDoc = iframe.contentWindow.document; \r\n "
						+ "console.log('IFrameDoc: ' + iframeDoc); \r\n"
						+ "if (iframeDoc.getElementById('ebx-legacy-component') !== null) { \r\n"
						+ "console.log('Inside 1'); \r\n"
						+ "var xpathView = \"//*[text()='View']\";\r\n"
						+ "var matchingElementView = document.evaluate(xpathView, document.getElementById('tableFrame').contentWindow.document.getElementById('ebx-legacy-component').contentWindow.document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\r\n"
						+ "if(matchingElementView !== null) { \r\n"
						+ "console.log(matchingElementView); \r\n" 
						+ "matchingElementView.click();\r\n" 
						+ "\r\n"
						+ "var xpathViewName = \"//*[text()='Test1']\";\r\n"
						+ "var matchingElementViewName = document.evaluate(xpathViewName, document.getElementById('tableFrame').contentWindow.document.getElementById('ebx-legacy-component').contentWindow.document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;\r\n"
						+ "if(matchingElementViewName !== null) {\r\n"
						+ "console.log('Inside 3');\r\n" 
						+ "matchingElementViewName.click();\r\n"						
						+ "return; \r\n" + "}; \r\n " + "}; \r\n " + "}; \r\n "
						+ "window.setTimeout(checkViewSetup, 2000); \r\n" + "}\r\n");

				writer.addJS("checkIframeLoaded();\r\n");
				writer.addJS("checkViewSetup();\r\n");
			}
		});
	}

	@Override
	public void setupObjectContext(UserServiceSetupObjectContext<TableViewEntitySelection> context,
			UserServiceObjectContextBuilder builder) {

	}

	@Override
	public void validate(UserServiceValidateContext<TableViewEntitySelection> context) {

	}
}