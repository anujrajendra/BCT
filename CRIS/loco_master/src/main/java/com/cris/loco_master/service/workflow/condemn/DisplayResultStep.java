package com.cris.loco_master.service.workflow.condemn;

import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.ui.UIButtonSpecNavigation;
import com.orchestranetworks.ui.UICSSClasses;
import com.orchestranetworks.ui.UIFormLabelSpec;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserServiceDisplayConfigurator;
import com.orchestranetworks.userservice.UserServicePaneContext;
import com.orchestranetworks.userservice.UserServicePaneWriter;
import com.orchestranetworks.userservice.UserServiceSetupDisplayContext;

public class DisplayResultStep implements DisplayStep {

	String displayMessage;

	public DisplayResultStep(String displayMessage) {
		super();
		this.displayMessage = displayMessage;
	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<TableViewEntitySelection> context,
			UserServiceDisplayConfigurator config) {

		config.setContent(this::writeContent);
		{
			final UIButtonSpecNavigation closeButtonSpec = config.newCloseButton();
			closeButtonSpec.setDefaultButton(true);
			config.setRightButtons(closeButtonSpec);
		}
	}

	protected void writeContent(final UserServicePaneContext context, final UserServicePaneWriter writer) {
		writer.add("<div").addSafeAttribute("class", UICSSClasses.CONTAINER_WITH_TEXT).add(">");

		final UserMessage message = UserMessage.createInfo(
				"The request for loco condemnation " + displayMessage + " has been submitted successfully.");
		writer.addUILabel(new UIFormLabelSpec(message));
		writer.add("</div>");
	}
}