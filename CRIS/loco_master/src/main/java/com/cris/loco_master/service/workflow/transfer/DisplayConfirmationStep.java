package com.cris.loco_master.service.workflow.transfer;

import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.ui.UIButtonSpec;
import com.orchestranetworks.ui.UIButtonSpecNavigation;
import com.orchestranetworks.ui.UICSSClasses;
import com.orchestranetworks.ui.UIFormLabelSpec;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserServiceDisplayConfigurator;
import com.orchestranetworks.userservice.UserServiceEventContext;
import com.orchestranetworks.userservice.UserServiceEventOutcome;
import com.orchestranetworks.userservice.UserServicePaneContext;
import com.orchestranetworks.userservice.UserServicePaneWriter;
import com.orchestranetworks.userservice.UserServiceSetupDisplayContext;

public class DisplayConfirmationStep implements DisplayStep {

	String displayMessage;

	public DisplayConfirmationStep(String displayMessage) {
		super();
		this.displayMessage = displayMessage;
	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<TableViewEntitySelection> context,
			UserServiceDisplayConfigurator config) {
		// TODO Auto-generated method stub

		config.setContent(this::writeContent);
		{

			final UIButtonSpec cancelButtonSpec = config.newCancelButton();

			final UIButtonSpecNavigation backButtonSpec = config.newBackButton(this::onbackPressed);

			config.setLeftButtons(backButtonSpec, cancelButtonSpec);
		}
		{

			final UIButtonSpecNavigation nextButtonSpec = config.newNextButton(this::onNextPressed);
			nextButtonSpec.setLabel(UserMessage.createInfo("Confirm"));
			nextButtonSpec.setDefaultButton(true);
			config.setRightButtons(nextButtonSpec);

		}
	}

	protected void writeContent(final UserServicePaneContext context, final UserServicePaneWriter writer) {
		writer.add("<div").addSafeAttribute("class", UICSSClasses.CONTAINER_WITH_TEXT).add(">");

		final UserMessage message = UserMessage
				.createInfo("Do you really want to proceed with Loco Transfer for selected Loco/s ? " + displayMessage);
		writer.addUILabel(new UIFormLabelSpec(message));
		writer.add("</div>");
	}

	protected UserServiceEventOutcome onNextPressed(final UserServiceEventContext context) {
		return EventOutcome.DISPLAY_RESULT;
	}

	protected UserServiceEventOutcome onbackPressed(UserServiceEventContext userserviceeventcontext1) {
		return EventOutcome.DISPLAY_INPUT_SCREEN;
	}

}
