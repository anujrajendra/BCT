package com.cris.coach_master.trigger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.cris.coach_master.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.trigger.AfterCreateOccurrenceContext;
import com.orchestranetworks.schema.trigger.AfterModifyOccurrenceContext;
import com.orchestranetworks.schema.trigger.TableTrigger;
import com.orchestranetworks.schema.trigger.TriggerSetupContext;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.ValueContextForUpdate;

public class SeatLayoutDetailsTrigger extends TableTrigger {

	private Repository repository;
	private AdaptationHome coachDataspaceName;
	private AdaptationName coachDataSetKey;
	private Adaptation coachDatasetName;
	private AdaptationTable seatLayoutMasterTable;
	private Adaptation seatLayoutMasterRecord;

	@Override
	public void handleAfterCreate(AfterCreateOccurrenceContext aContext) throws OperationException {
		// TODO Auto-generated method stub

		String seatLayoutId = (String) aContext.getAdaptationOccurrence()
				.get(Paths._Root_Seat_Layout_Details._Root_Seat_Layout_Details_Seat_Layout_Id);

		setValue(aContext.getAdaptationHome().getKey(), seatLayoutId);

		if (seatLayoutMasterRecord != null) {

			ValueContextForUpdate vcfuRecord = aContext.getProcedureContext()
					.getContext(seatLayoutMasterRecord.getAdaptationName());

			vcfuRecord.setValueEnablingPrivilegeForNode(getDateTime(),
					Paths._Root_Seat_Layout_Master._Root_Seat_Layout_Master_Audit_Info_SeatLayoutDetails_ModDate);

			aContext.getProcedureContext().doModifyContent(seatLayoutMasterRecord, vcfuRecord);

		}

	}

	@Override
	public void handleAfterModify(AfterModifyOccurrenceContext aContext) throws OperationException {
		// TODO Auto-generated method stub

		String seatLayoutId = (String) aContext.getAdaptationOccurrence()
				.get(Paths._Root_Seat_Layout_Details._Root_Seat_Layout_Details_Seat_Layout_Id);

		setValue(aContext.getAdaptationHome().getKey(), seatLayoutId);

		if (seatLayoutMasterRecord != null) {

			ValueContextForUpdate vcfuRecord = aContext.getProcedureContext()
					.getContext(seatLayoutMasterRecord.getAdaptationName());

			vcfuRecord.setValueEnablingPrivilegeForNode(getDateTime(),
					Paths._Root_Seat_Layout_Master._Root_Seat_Layout_Master_Audit_Info_SeatLayoutDetails_ModDate);

			aContext.getProcedureContext().doModifyContent(seatLayoutMasterRecord, vcfuRecord);
		}

	}

	private void setValue(HomeKey adaptationHomeKey, String seatLayoutId) {

		repository = Repository.getDefault();

		coachDataspaceName = repository.lookupHome(adaptationHomeKey);

		coachDatasetName = coachDataspaceName.findAdaptationOrNull(AdaptationName.forName("coach_data"));

		seatLayoutMasterTable = coachDatasetName.getTable(Path.parse("/root/Seat_Layout_Master"));

		seatLayoutMasterRecord = null;
		seatLayoutMasterRecord = seatLayoutMasterTable
				.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(seatLayoutId));

	}

	private String getDateTime() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
		String strDate = sdf.format(cal.getTime());
		// System.out.println("===Current date in String Format: " + strDate);

		return strDate;

	}

	@Override
	public void setup(TriggerSetupContext arg0) {
		// TODO Auto-generated method stub

	}

}
