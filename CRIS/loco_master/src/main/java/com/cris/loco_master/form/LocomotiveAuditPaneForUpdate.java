package com.cris.loco_master.form;

import com.cris.loco_master.Paths;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPane;
import com.orchestranetworks.ui.form.UIFormPaneWriter;
import com.orchestranetworks.ui.form.widget.UITextBox;

public class LocomotiveAuditPaneForUpdate implements UIFormPane {

	@Override
	public void writePane(UIFormPaneWriter pWriter, UIFormContext aContext) {
		// TODO Auto-generated method stub

		String floatDivStyle = "float:left; width:48%; ";

		pWriter.add("<div style='" + floatDivStyle + " min-width:200px;'>");
		pWriter.add(
				"<table><tr><td style='margin-top: 3px; width: 30px; min-width: 30px; max-width: 30px; vertical-align:top'>");

		pWriter.startTableFormRow();

		UITextBox sourceRecordId = pWriter
				.newTextBox(Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Source_Record_Id);
		sourceRecordId.setEditorDisabled(true);

		UITextBox sourceSystemName = pWriter
				.newTextBox(Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Source_System_Name);
		sourceSystemName.setEditorDisabled(true);

		UITextBox sourceEventType = pWriter
				.newTextBox(Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Source_Event_Type);
		sourceEventType.setEditorDisabled(true);

		UITextBox createdByUser = pWriter
				.newTextBox(Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Created_By_User);
		createdByUser.setEditorDisabled(true);

		UITextBox loggedInUser = pWriter.newTextBox(Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Logged_In_User);
		createdByUser.setEditorDisabled(true);

		// String userId = aContext.getSession().getUserReference().getUserId();
//		pWriter.addJS("var userId = '" + userId + "';\n");

//		UITextBox loggedInUser = pWriter.newTextBox(Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Logged_In_User);
//		pWriter.addJS_setNodeValue("userId", Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Logged_In_User);
		// loggedInUser.setEditorDisabled(true);

		UITextBox inWorkflow = pWriter.newTextBox(Paths._Root_Locomotive._Root_Locomotive_Audit_Info_In_Workflow);
		inWorkflow.setEditorDisabled(true);

		pWriter.addFormRow(sourceRecordId);
		pWriter.addFormRow(sourceSystemName);
		pWriter.addFormRow(sourceEventType);
		pWriter.addFormRow(createdByUser);
		pWriter.addFormRow(loggedInUser);

		pWriter.addFormRow(inWorkflow);

		pWriter.endTableFormRow();
		pWriter.add("</td></tr></table>");

		pWriter.add("</div>");
	}
}