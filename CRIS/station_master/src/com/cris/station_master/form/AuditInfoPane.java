package com.cris.station_master.form;

import com.cris.station_master.paths.StationPaths;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPane;
import com.orchestranetworks.ui.form.UIFormPaneWriter;

public class AuditInfoPane implements UIFormPane {

	@Override
	public void writePane(UIFormPaneWriter writer, UIFormContext context) {

		writer.startTableFormRow();

		writer.addFormRow(StationPaths._Root_Station._Root_Station_Audit_Info_Source_Record_Id);
		writer.addFormRow(StationPaths._Root_Station._Root_Station_Audit_Info_Source_System_Name);
		writer.addFormRow(StationPaths._Root_Station._Root_Station_Audit_Info_Source_Event_Type);

		writer.endTableFormRow();
	}
}