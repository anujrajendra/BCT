package com.cris.feedback.form;

import com.orchestranetworks.ui.form.UIForm;
import com.orchestranetworks.ui.form.UIFormBody;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPaneWithTabs;

public class FeedbackUserForm extends UIForm{

	public void defineBody(final UIFormBody body, final UIFormContext context) {
		
		UIFormPaneWithTabs tabs = new UIFormPaneWithTabs();
		tabs.addHomeTab(new FeedbackUserMainPane());
		body.setContent(tabs);
	}
}
