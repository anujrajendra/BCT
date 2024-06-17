package com.cris.loco_master.service.workflow;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.Request;
import com.onwbp.adaptation.RequestResult;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.ui.UIButtonSpecSubmit;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.UserServiceDisplayConfigurator;
import com.orchestranetworks.userservice.UserServiceEventContext;
import com.orchestranetworks.userservice.UserServiceEventOutcome;
import com.orchestranetworks.userservice.UserServiceNext;
import com.orchestranetworks.userservice.UserServiceObjectContextBuilder;
import com.orchestranetworks.userservice.UserServicePane;
import com.orchestranetworks.userservice.UserServicePaneContext;
import com.orchestranetworks.userservice.UserServicePaneWriter;
import com.orchestranetworks.userservice.UserServiceProcessEventOutcomeContext;
import com.orchestranetworks.userservice.UserServiceSetupDisplayContext;
import com.orchestranetworks.userservice.UserServiceSetupObjectContext;
import com.orchestranetworks.userservice.UserServiceValidateContext;
import com.orchestranetworks.workflow.ProcessLauncher;
import com.orchestranetworks.workflow.ProcessLauncherResult;
import com.orchestranetworks.workflow.PublishedProcessKey;
import com.orchestranetworks.workflow.WorkItemKey;
import com.orchestranetworks.workflow.WorkflowEngine;

public class LaunchUpdateWorkflowService implements UserService<TableViewEntitySelection>{

	private WorkItemKey wiKey;
	@Override
	public UserServiceEventOutcome processEventOutcome(
			UserServiceProcessEventOutcomeContext<TableViewEntitySelection> aContext, UserServiceEventOutcome eOutcome) {
		// TODO Auto-generated method stub
		try {
		//String user = aContext.getSession().getUserReference().getUserId();

		Repository repository = aContext.getRepository();
		WorkflowEngine wfEngine = WorkflowEngine.getFromRepository(repository, aContext.getSession());
		ProcessLauncher launcher = null;
		
		launcher = wfEngine.getProcessLauncher(PublishedProcessKey.forName("loco_update"));
		Request selectedRecord = aContext.getEntitySelection().getSelectedRecords();
		RequestResult result = selectedRecord.execute();

		if (result.isSizeGreaterOrEqual(1)) {
			Adaptation productRecord = result.nextAdaptation();
			String xPathExpression = productRecord.toXPathExpression();
			//LoggingCategory.getWorkflow().info("XPath Expression: " + xPathExpression);
			launcher.setInputParameter("record", xPathExpression);
		}
		else
			return UserServiceNext.nextClose();

		launcher.setLabel(UserMessage.createInfo("Loco Record Modification"));
		launcher.setDescription(UserMessage.createInfo("Loco Record Modification"));
		ProcessLauncherResult launcherResult = launcher.launchProcessWithResult();
		if (launcherResult != null) {
			wiKey = launcherResult.getWorkItemKey();				
		}
		} catch (Exception e) {
		LoggingCategory.getWorkflow().info(e.getMessage());
		}
		if (wiKey != null)
			return UserServiceNext.nextWorkItem(wiKey);
		else
			return UserServiceNext.nextClose();
}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<TableViewEntitySelection> aContext,
			UserServiceDisplayConfigurator aConfig) {
		// TODO Auto-generated method stub
		
		aConfig.setContent(new UserServicePane() {

			@Override
			public void writePane(UserServicePaneContext aContext, UserServicePaneWriter aWriter) {

				aWriter.add("<div style=\"text-align: center; padding: 25% 0;\">");
				aWriter.add("<br/>");
				aWriter.add("</div>");

				UIButtonSpecSubmit btn = aWriter.newSubmitButton("Submit", this::submitForm);

				btn.setId("InitiateWorkflow");
				aWriter.add("<div style='display:none;'>");
				aWriter.addButton(btn);
				aWriter.add("</div>");
				aWriter.addJS_cr("document.getElementById('InitiateWorkflow').click(); ");
			}

			protected UserServiceEventOutcome submitForm(final UserServiceEventContext context) {
				return UserServiceNext.nextClose();
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
