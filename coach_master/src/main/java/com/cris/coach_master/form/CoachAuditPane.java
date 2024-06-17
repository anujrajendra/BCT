package com.cris.coach_master.form;

import com.cris.coach_master.Paths;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPane;
import com.orchestranetworks.ui.form.UIFormPaneWriter;

public class CoachAuditPane implements UIFormPane{

	
	@Override
	public void writePane(UIFormPaneWriter pWriter, UIFormContext aContext) {
		// TODO Auto-generated method stub
		
		String floatDivStyle = "float:left; width:48%; ";
		
		pWriter.add("<div style='" + floatDivStyle + " min-width:200px;'>");
		pWriter.add(
				"<table><tr><td style='margin-top: 3px; width: 30px; min-width: 30px; max-width: 30px; vertical-align:top'>");

		pWriter.startTableFormRow();
		
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Audit_Info_Source_Record_Id);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Audit_Info_Source_System_Name);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Audit_Info_Source_Event_Type);
		
		pWriter.endTableFormRow();
		pWriter.add("</td></tr></table>");
		//pWriter.endBorder();
		pWriter.add("</div>");
		
}
}