package com.cris.station_master.form;

import com.orchestranetworks.ui.form.UIForm;
import com.orchestranetworks.ui.form.UIFormBody;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPaneWithTabs;

public class StationUIForm extends UIForm {

	@Override
	public void defineBody(UIFormBody body, UIFormContext context) {

		UIFormPaneWithTabs uiFormTabs = new UIFormPaneWithTabs();

		uiFormTabs.addTab("Station Information", new StationDetailsPane());
		uiFormTabs.addTab("Audit Info", new AuditInfoPane());

		body.setContent(uiFormTabs);
	}
}