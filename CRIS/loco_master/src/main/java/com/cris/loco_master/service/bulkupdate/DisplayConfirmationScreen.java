package com.cris.loco_master.service.bulkupdate;

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

public class DisplayConfirmationScreen implements DisplayStep {

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<TableViewEntitySelection> context,
			UserServiceDisplayConfigurator config) {
		// TODO Auto-generated method stub
		config.setContent(this::writeContent);
		{
			final UIButtonSpec cancelButtonSpec = config.newCancelButton();
			config.setTitle("Confirmation Page");
			config.setLeftButtons(cancelButtonSpec);
		}
		{
			final UIButtonSpecNavigation nextButtonSpec = config.newNextButton(this::onNextPressed);
			nextButtonSpec.setLabel(UserMessage.createInfo("Confirm"));
			nextButtonSpec.setDefaultButton(true);
			config.setRightButtons(nextButtonSpec);
		}
	}

	protected void writeContent(final UserServicePaneContext context, final UserServicePaneWriter writer) {

		{
			final UserMessage message = UserMessage
					.createInfo("Please confirm if you want to perform bulk update for the selected records");
			writer.add("<div").addSafeAttribute("class", UICSSClasses.CONTAINER_WITH_TEXT).add(">");
			writer.addUILabel(new UIFormLabelSpec(message));
			writer.add("</div>");
		}
	}

	protected UserServiceEventOutcome onNextPressed(final UserServiceEventContext context) {
		return EventOutcome.DISPLAY_RESULT;
	}

}
