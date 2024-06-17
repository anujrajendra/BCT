package com.cris.user_registration.form;

import com.cris.user_registration.Paths;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPane;
import com.orchestranetworks.ui.form.UIFormPaneWriter;

public class UserRegistrationAllowedZonesPane implements UIFormPane {

	@Override
	public void writePane(UIFormPaneWriter pWriter, UIFormContext aContext) {
		// TODO Auto-generated method stub
		String floatDivStyle = "float:left; width:100%; ";

		pWriter.add("<div style='" + floatDivStyle + " min-width:200px;'>");
		pWriter.add("<table><tr><td>"); // style='margin-top: 3px; width: 30px; min-width: 30px; max-width: 30px;
										// vertical-align:top'>");

		pWriter.startTableFormRow();

		pWriter.addFormRow(Paths._Root_User_Registration_Details._Root_User_Registration_Details_Allowed_Zones);

		pWriter.endTableFormRow();
		pWriter.add("</td></tr></table>");
		pWriter.add("</div>");

	}

}