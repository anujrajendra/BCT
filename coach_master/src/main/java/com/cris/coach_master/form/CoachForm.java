package com.cris.coach_master.form;

import com.orchestranetworks.ui.UIFormLabelSpec;
import com.orchestranetworks.ui.form.UIForm;
import com.orchestranetworks.ui.form.UIFormBody;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormHeader;
import com.orchestranetworks.ui.form.UIFormPaneWithTabs;

public class CoachForm extends UIForm {
	
	@Override
	public void defineHeader(final UIFormHeader header, final UIFormContext context) {
	if (context.isCreatingRecord()) {
	header.setTitle(new UIFormLabelSpec("Coach"));
	}
	}

	@Override
	public void defineBody(final UIFormBody body, final UIFormContext context) {
		UIFormPaneWithTabs tabs = new UIFormPaneWithTabs();
		tabs.addHomeTab(new CoachMainPane());
		tabs.addTab("Audit Info", new CoachAuditPane());
		body.setContent(tabs);

	}

}
