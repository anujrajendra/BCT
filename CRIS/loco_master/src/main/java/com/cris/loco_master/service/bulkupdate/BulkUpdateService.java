package com.cris.loco_master.service.bulkupdate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cris.loco_master.Paths;
import com.cris.loco_master.logger.ModuleLogger;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.Request;
import com.onwbp.adaptation.RequestResult;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;
import com.orchestranetworks.service.ProgrammaticService;
import com.orchestranetworks.service.ValueContextForUpdate;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.UserServiceDisplayConfigurator;
import com.orchestranetworks.userservice.UserServiceEventOutcome;
import com.orchestranetworks.userservice.UserServiceObjectContextBuilder;
import com.orchestranetworks.userservice.UserServiceProcessEventOutcomeContext;
import com.orchestranetworks.userservice.UserServiceSetupDisplayContext;
import com.orchestranetworks.userservice.UserServiceSetupObjectContext;
import com.orchestranetworks.userservice.UserServiceValidateContext;

public class BulkUpdateService implements UserService<TableViewEntitySelection> {

	private DisplayStep currentStep = null;

	BulkUpdateService() {
		this.currentStep = new DisplayConfirmationScreen();
	}

	@Override
	public UserServiceEventOutcome processEventOutcome(
			UserServiceProcessEventOutcomeContext<TableViewEntitySelection> context, UserServiceEventOutcome outcome) {
		// TODO Auto-generated method stub
		if (!(outcome instanceof DisplayStep.EventOutcome)) {
			return outcome;
		}

		switch ((DisplayStep.EventOutcome) outcome) {

		case DISPLAY_CONFIRMATION:
			this.currentStep = new DisplayConfirmationScreen();

			return null;

		case DISPLAY_RESULT:

			// Getting all the selected records
			Request request = context.getEntitySelection().getSelectedRecords();
			RequestResult requestResult = request.execute();

			List<String> list = new ArrayList<String>();

			for (Adaptation selectedRecords; (selectedRecords = requestResult.nextAdaptation()) != null;) {

				String locoNumber = (String) selectedRecords.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Number);
				list.add(locoNumber);

				if (locoNumber != null) {

//					String bulkUpdate = (String) selectedRecords
//							.get(Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Bulk_Update);

//					Date date = (Date) selectedRecords
//							.get(Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Current_Date);

					try {
						bulkUpdateMethod(context);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			this.currentStep = new DisplayResultStep();
			return null;

		default:
			return null;
		}
	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<TableViewEntitySelection> context,
			UserServiceDisplayConfigurator config) {

		this.currentStep.setupDisplay(context, config);
	}

	@Override
	public void setupObjectContext(UserServiceSetupObjectContext<TableViewEntitySelection> context,
			UserServiceObjectContextBuilder builder) {

	}

	@Override
	public void validate(UserServiceValidateContext<TableViewEntitySelection> arg0) {
		// TODO Auto-generated method stub

	}

	private static void bulkUpdateMethod(UserServiceProcessEventOutcomeContext<TableViewEntitySelection> context)
			throws Exception {

		Repository repository = context.getRepository();
		AdaptationHome dataspace = repository.lookupHome(HomeKey.parse(HomeKey.forBranchName("loco_data").format()));

		Request request = context.getEntitySelection().getSelectedRecords();
		RequestResult requestResult = request.execute();

		for (Adaptation records; (records = requestResult.nextAdaptation()) != null;) {

			final Adaptation adaptation = records;

			// Create a Procedure for each iteration
			Procedure procedure = new Procedure() {

				@Override
				public void execute(ProcedureContext procedureContext) throws Exception {

					// Use the current value of adaptation for each iteration
					ValueContextForUpdate vcfuRecord = procedureContext.getContext(adaptation.getAdaptationName());

					String bulkUpdate = "Yes";

					ModuleLogger.logger.info("bulkUpdate " + bulkUpdate);

					// Ensure to set the correct node path
					vcfuRecord.setValueEnablingPrivilegeForNode(bulkUpdate,
							Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Bulk_Update);
					vcfuRecord.setValueEnablingPrivilegeForNode(new Date(),
							Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Current_Date);

					procedureContext.setAllPrivileges(true);
					procedureContext.doModifyContent(adaptation, vcfuRecord);
					procedureContext.setAllPrivileges(false);
				}

			};
			ProgrammaticService svc = ProgrammaticService.createForSession(context.getSession(), dataspace);
			svc.execute(procedure);
		}

	}

}
