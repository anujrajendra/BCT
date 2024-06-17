package com.cris.coach_master.form;

import com.cris.coach_master.Paths;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPane;
import com.orchestranetworks.ui.form.UIFormPaneWriter;

public class CoachMainPane implements UIFormPane {

	@Override
	public void writePane(UIFormPaneWriter pWriter, UIFormContext aContext) {
		// TODO Auto-generated method stub

		String floatDivStyle = "float:left; width:48%; ";
		String RightDivStyle = "float:right; width:48%; ";

		// Left Side

		pWriter.add("<div style='" + floatDivStyle + " min-width:200px;'>");
		pWriter.add(
				"<table><tr><td style='margin-top: 3px; width: 30px; min-width: 30px; max-width: 30px; vertical-align:top'>");

		// UIUtils.addTitleFormRow(pWriter, "Person Details");
		pWriter.startTableFormRow();

		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Coach_Number);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Coach_Type);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Owning_Rly);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Owning_Div);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Owning_Depot);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_RFID_Tag);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_No_of_Berth);
//		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Brake_Type);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Max_Speed);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Condemnation_Authority);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Bio_Toilet_Fitted);

		pWriter.endTableFormRow();
		pWriter.add("</td></tr></table>");
		pWriter.add("</div>");

		pWriter.add("<div style='" + RightDivStyle + " min-width:200px;'>");
		pWriter.add(
				"<table><tr><td style='margin-top: 3px; width: 30px; min-width: 30px; max-width: 30px; vertical-align:top'>");
//		pWriter.add("<br>");
//		pWriter.add("<br>");
//		pWriter.add("<br>");
//		pWriter.add("<br>");
		pWriter.startTableFormRow();

		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Coach_Id);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Coach_Status);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Coach_Gauge);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Tare_Weight);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Fitness_Type);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Coupling_Type);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Manufacturer);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Built_Date);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Commissioning_Date);
		pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Condemnation_Date);
		// pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Coach_Berth_Number);
		// pWriter.addFormRow(Paths._Root_Coach._Root_Coach_Location_For_Booking);

		pWriter.endTableFormRow();
		pWriter.add("</td></tr></table>");
		pWriter.add("</div>");

	}

}
