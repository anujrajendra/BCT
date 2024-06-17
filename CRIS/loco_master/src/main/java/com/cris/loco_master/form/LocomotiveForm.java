package com.cris.loco_master.form;

import com.orchestranetworks.ui.form.UIForm;
import com.orchestranetworks.ui.form.UIFormBody;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPaneWithTabs;

public class LocomotiveForm extends UIForm {

	@Override
	public void defineBody(final UIFormBody body, final UIFormContext context) {
		UIFormPaneWithTabs tabs = new UIFormPaneWithTabs();

		tabs.addHomeTab(new LocomotiveUserBasedDivisionsPane());
		tabs.addTab("Audit Info", new LocomotiveAuditPane());
		tabs.addTab("Reference Documents", new LocomotiveReferenceDocsPane());

		body.setContent(tabs);

	}

}
