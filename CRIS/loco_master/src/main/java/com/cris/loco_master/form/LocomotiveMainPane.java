package com.cris.loco_master.form;

import com.cris.loco_master.Paths;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPane;
import com.orchestranetworks.ui.form.UIFormPaneWriter;

public class LocomotiveMainPane implements UIFormPane {

	@Override
	public void writePane(UIFormPaneWriter pWriter, UIFormContext aContext) {
		// TODO Auto-generated method stub

		// String floatDivStyle = "float:left; width:48%; ";
		String LeftDivStyle = "display: inline-block; width: 49%; padding-bottom: 10px;";

		String RightDivStyle = "float:right; width:48%; ";

		// Left Side

		pWriter.add("<div style='" + LeftDivStyle + " min-width:200px;'>");
		pWriter.add(
				"<table><tr><td style='margin-top: 3px; width: 30px; min-width: 30px; max-width: 30px; vertical-align:top'>");

		pWriter.startTableFormRow();

		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Number);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Type);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Zone);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Division);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Shed);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Number);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Manufacturer);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Manufacturing_Date);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Entry_Date);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Commissioning_Date);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Control_Type);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Service_Type);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Brake_Type);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Brake_Sub_Type);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Hotel_Load);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Cab1_AC);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Cab2_AC);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Axle_Load);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Condemn_Proposal_Type);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_CVVRS_Type);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Hauling_Power);

		pWriter.endTableFormRow();
		pWriter.add("</td></tr></table>");
		pWriter.add("</div>");

		pWriter.add("<div style='" + RightDivStyle + " min-width:200px;'>");
		pWriter.add(
				"<table><tr><td style='margin-top: 3px; width: 30px; min-width: 30px; max-width: 30px; vertical-align:top'>");

		pWriter.startTableFormRow();

		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Permanent_Domain);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Traction_Motor_Type);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Traction_Code);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Boogie_Type);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Lease_Type);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Status);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Receiving_Date);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Condemn_Date);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_POH_Date);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Auxilary);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Gauge_Type);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Rtis_Flag);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Hog_Flag);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Power_Type);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Remmlot_Flag);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_HRP_Flag);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Kavach_Flag);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_VCD_Flag);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Pvt_Owner_Flag);
		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Loco_Pvt_Party_Code);

		pWriter.addFormRow(Paths._Root_Locomotive._Root_Locomotive_Allotment_Letter_Allotment_Letter);

		pWriter.endTableFormRow();
		pWriter.add("</td></tr></table>");
		pWriter.add("</div>");

	}

}
