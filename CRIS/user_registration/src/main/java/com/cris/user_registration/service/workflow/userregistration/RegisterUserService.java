package com.cris.user_registration.service.workflow.userregistration;

import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.service.OperationException;
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

public class RegisterUserService implements UserService<TableViewEntitySelection> {

	private WorkItemKey wiKey;

	public RegisterUserService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserServiceEventOutcome processEventOutcome(
			UserServiceProcessEventOutcomeContext<TableViewEntitySelection> aContext, UserServiceEventOutcome arg1) {
		// TODO Auto-generated method stub
		System.out.println("===Inside user registration service===");
		Repository repository = aContext.getRepository();
		WorkflowEngine wfEngine = WorkflowEngine.getFromRepository(repository, aContext.getSession());
		ProcessLauncher launcher = null;

		launcher = wfEngine.getProcessLauncher(PublishedProcessKey.forName("user_registration_ui"));
		launcher.setLabel(UserMessage.createInfo("User Registration Request"));
		launcher.setDescription(UserMessage.createInfo("User Registration Request"));
		launcher.setCreator(aContext.getSession().getUserReference());
		try {
			ProcessLauncherResult launcherResult = launcher.launchProcessWithResult();
			wiKey = launcherResult.getWorkItemKey();
		} catch (OperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (wiKey != null)
			return UserServiceNext.nextWorkItem(wiKey);
		else
			return UserServiceNext.nextClose();

	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<TableViewEntitySelection> context,
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
	public void setupObjectContext(UserServiceSetupObjectContext<TableViewEntitySelection> context,
			UserServiceObjectContextBuilder arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate(UserServiceValidateContext<TableViewEntitySelection> arg0) {
		// TODO Auto-generated method stub

	}

}
