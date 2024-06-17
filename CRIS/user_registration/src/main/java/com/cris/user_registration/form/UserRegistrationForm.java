package com.cris.user_registration.form;

import com.orchestranetworks.ui.form.UIForm;
import com.orchestranetworks.ui.form.UIFormBody;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPaneWithTabs;

public class UserRegistrationForm extends UIForm {

	@Override
	public void defineBody(UIFormBody aBody, UIFormContext aContext) {
		// TODO Auto-generated method stub
		UIFormPaneWithTabs tabs = new UIFormPaneWithTabs();

		tabs.addHomeTab(new UserRegistrationMainPane());
//		tabs.addTab("Allowed Zones", new UserRegistrationAllowedZonesPane());
//		tabs.addTab("Allowed Divisions", new UserRegistrationAllowedDivisionsPane());
//		tabs.addTab("Allowed Sheds", new UserRegistrationAllowedShedsPane());

		aBody.setContent(tabs);

	}

}
