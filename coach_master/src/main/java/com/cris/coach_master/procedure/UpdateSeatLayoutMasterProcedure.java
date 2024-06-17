package com.cris.coach_master.procedure;

import java.util.Date;

import com.cris.coach_master.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;
import com.orchestranetworks.service.ValueContextForUpdate;

public class UpdateSeatLayoutMasterProcedure implements Procedure {

	String seatLayoutId;

	public UpdateSeatLayoutMasterProcedure(String seatLayoutId) {
		super();
		this.seatLayoutId = seatLayoutId;
	}

	@Override
	public void execute(ProcedureContext procedureContext) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("===inside update procedure exe===");
		Repository repository = Repository.getDefault();

		final HomeKey coachDataSpaceKey = HomeKey.forBranchName("coach_data");
		final AdaptationHome coachDataspaceName = repository.lookupHome(coachDataSpaceKey);

		final AdaptationName coachDataSetKey = AdaptationName.forName("coach_data");
		final Adaptation coachDatasetName = coachDataspaceName.findAdaptationOrNull(coachDataSetKey);

		AdaptationTable seatLayoutMasterTable = coachDatasetName.getTable(Path.parse("/root/Seat_Layout_Master"));

		Adaptation seatLayoutMasterRecord = null;
		seatLayoutMasterRecord = seatLayoutMasterTable
				.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(seatLayoutId));
		System.out.println("===inside update procedure exe===" + seatLayoutId);
		if (seatLayoutMasterRecord != null) {
			System.out.println("===inside update procedure record not null ===");
			ValueContextForUpdate vcfuRecord = procedureContext.getContext(seatLayoutMasterRecord.getAdaptationName());

			vcfuRecord.setValueEnablingPrivilegeForNode(new Date(),
					Paths._Root_Seat_Layout_Master._Root_Seat_Layout_Master_Audit_Info_SeatLayoutDetails_ModDate);

			procedureContext.setAllPrivileges(true);
			procedureContext.doModifyContent(seatLayoutMasterRecord, vcfuRecord);
			procedureContext.setAllPrivileges(false);

		}

	}

}
