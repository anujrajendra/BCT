package com.cris.common.iframe.integration;

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

public class WebPageIntegrationService implements UserService<TableViewEntitySelection> {

	@Override
	public UserServiceEventOutcome processEventOutcome(
			UserServiceProcessEventOutcomeContext<TableViewEntitySelection> arg0, UserServiceEventOutcome arg1) {
		return null;
	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<TableViewEntitySelection> context,
			UserServiceDisplayConfigurator config) {
		config.setContent(new UserServicePane() {

			@Override
			public void writePane(UserServicePaneContext arg0, UserServicePaneWriter writer) {

				writer.add(" <iframe src=\"" + context.getSession().getInputParameterValue(true, "webPageURL")
						+ " \" frameborder=\"0\" style=\"overflow:hidden;height:100%;width:100%\" height=\"100%\" width=\"100%\"></iframe>");

			}
		});
	}

	@Override
	public void setupObjectContext(UserServiceSetupObjectContext<TableViewEntitySelection> arg0,
			UserServiceObjectContextBuilder arg1) {

	}

	@Override
	public void validate(UserServiceValidateContext<TableViewEntitySelection> arg0) {

	}
}