package com.cris.user_registration.usertask;

import java.util.Iterator;

import com.cris.user_registration.constant.Constants;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.interactions.InteractionHelper.ParametersMap;
import com.orchestranetworks.interactions.SessionInteraction;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.workflow.DataContextReadOnly;
import com.orchestranetworks.workflow.ProcessExecutionContext;
import com.orchestranetworks.workflow.UserTask;
import com.orchestranetworks.workflow.UserTaskBeforeWorkItemCompletionContext;

public class UserRegistrationUserTask extends UserTask {

	private String dataspace;
	private String dataset;
	private String table;
	private String record;

	Repository repo;
	AdaptationHome dataspaceName;
	Adaptation datasetName;
	AdaptationTable adaptationTable = null;
	Adaptation adaptationRecord = null;

	@Override
	public void checkBeforeWorkItemCompletion(UserTaskBeforeWorkItemCompletionContext context) {
		// TODO Auto-generated method stub
		super.checkBeforeWorkItemCompletion(context);

		initializeVariables(context);
		if (context.isAcceptAction()) {
			SessionInteraction sessionInteraction = context.getSession().getInteraction(false);
			ParametersMap sessionParamMap = sessionInteraction.getInternalParameters();

			Iterator<String> variableNames = sessionParamMap.getVariableNames();

			String record = sessionParamMap.getVariableString("created");
			String ipasId = record.substring(record.indexOf("=") + 1, record.lastIndexOf("]"));

			String tableString = record.substring(0, record.indexOf("["));
			String recordIdentifier = record.substring(record.indexOf("=") + 2, record.indexOf("]") - 1);

			adaptationTable = datasetName.getTable((Path.parse(tableString)));
			adaptationRecord = adaptationTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(recordIdentifier));

			String designation = (String) adaptationRecord.get(Path.parse("./Designation"));
			String entity = (String) adaptationRecord.get(Path.parse("./Entity"));
			String serviceStatus = (String) adaptationRecord.get(Path.parse("./Service_Status"));
			String mdmUserId = (String) adaptationRecord.get(Path.parse("./Mdm_User_Id"));
			String ZoneCode = (String) adaptationRecord.get(Path.parse("./Zone"));
			String divisionCode = (String) adaptationRecord.get(Path.parse("./Divison"));

			if (serviceStatus == null) {
				context.reportMessage(UserMessage
						.createError("Employee with 'SERVING/ SALARY' status can only registerd only in MDM."));
			} else if (!serviceStatus.equals("SERVING (SALARY)")) {
				context.reportMessage(UserMessage
						.createError("Employee with 'SERVING/ SALARY' status can only registerd only in MDM."));
			}

			AdaptationTable designationMasterTable = datasetName.getTable((Path.parse("/root/Designation_Master")));
			Adaptation designationRecord = designationMasterTable
					.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(designation + "|" + entity));

			if (designationRecord == null) {
				context.reportMessage(
						UserMessage.createError("Cannot register user with given designation since not allowed."));
			} else if (mdmUserId != null) {

				if (mdmUserId.contains("DS1") || mdmUserId.contains("DS2")) {
					String dsRole = (String) designationRecord.get(Path.parse("./Ds_Role"));
					if (dsRole == null)
						context.reportMessage(UserMessage
								.createError("Cannot register user with \"Data Steward DS Role\" since not allowed."));
				}
				if (mdmUserId.contains("DAA1") || mdmUserId.contains("DAA2")) {
					String daaRole = (String) designationRecord.get(Path.parse("./Daa_Role"));
					if (daaRole == null)
						context.reportMessage(UserMessage.createError(
								"Cannot register user with \"Data Approving Authority DAA Role\" since not allowed."));
				}
			}

			if (divisionCode != null) {
				AdaptationTable divisionTable = Constants.DATASET_REFERENCE.getTable((Path.parse("/root/Division")));
				Adaptation divisionRecord = divisionTable
						.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(divisionCode));

				if (divisionRecord != null) {
					String zoneCodefromDivision = (String) divisionRecord.get(Path.parse("./Zone_Code"));
					if (!zoneCodefromDivision.equalsIgnoreCase(ZoneCode)) {
						context.reportMessage(UserMessage
								.createError(("The User Zone is different ,you can only register same Zone User.")));
					}
				}
			}
		}
	}

//	@Override
//	public void handleWorkItemCompletion(UserTaskWorkItemCompletionContext context) throws OperationException {
//		// TODO Auto-generated method stub
//
//		initializeVariables(context);
//
//		String tableString = record.substring(0, record.indexOf("["));
//		String recordIdentifier = record.substring(record.indexOf("=") + 2, record.indexOf("]") - 1);
//
//		adaptationTable = datasetName.getTable((Path.parse(tableString)));
//		adaptationRecord = adaptationTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(recordIdentifier));
//
//		String zoneCode = (String) adaptationRecord
//				.get(Paths._Root_User_Registration_Details._Root_User_Registration_Details_Zone);
//		String divisionCode = (String) adaptationRecord
//				.get(Paths._Root_User_Registration_Details._Root_User_Registration_Details_Divison);
//		String shedCode = (String) adaptationRecord
//				.get(Paths._Root_User_Registration_Details._Root_User_Registration_Details_Shed);
//
//		AdaptationTable allowedZoneTable = datasetName.getTable((Path.parse("/root/User_Permissions_Zone")));
//		;
//		AdaptationTable allowedDivisionTable = datasetName.getTable((Path.parse("/root/User_Permissions_Division")));
//		;
//		AdaptationTable allowedShedTable = datasetName.getTable((Path.parse("/root/User_Permissions_Shed")));
//		;
//
//		Procedure procedure = new Procedure() {
//
//			@Override
//			public void execute(ProcedureContext pContext) throws Exception {
//				// TODO Auto-generated method stub
//
//				ValueContextForUpdate valueContextForUpdateZone = pContext.getContextForNewOccurrence(allowedZoneTable);
//				ValueContextForUpdate valueContextForUpdateDivision = pContext
//						.getContextForNewOccurrence(allowedDivisionTable);
//				ValueContextForUpdate valueContextForUpdateShed = pContext.getContextForNewOccurrence(allowedShedTable);
//
//				if (zoneCode != null && divisionCode != null && shedCode != null) {
//					valueContextForUpdateZone.setValue(recordIdentifier, Path.parse("./Ipas_Id"));
//					valueContextForUpdateZone.setValue(zoneCode, Path.parse("./Zone_Code"));
//
//					valueContextForUpdateDivision.setValue(recordIdentifier, Path.parse("./Ipas_Id"));
//					valueContextForUpdateDivision.setValue(divisionCode, Path.parse("./Division_Code"));
//
//					valueContextForUpdateShed.setValue(recordIdentifier, Path.parse("./Ipas_Id"));
//					valueContextForUpdateShed.setValue(shedCode, Path.parse("./Shed_Code"));
//
//					pContext.doCreateOccurrence(valueContextForUpdateShed, allowedShedTable);
//					pContext.doCreateOccurrence(valueContextForUpdateDivision, allowedDivisionTable);
//					pContext.doCreateOccurrence(valueContextForUpdateZone, allowedZoneTable);
//				}
//			}
//		};
//
//		ProgrammaticService svc = ProgrammaticService.createForSession(context.getSession(), dataspaceName);
//		svc.execute(procedure);
//		super.handleWorkItemCompletion(context);
//	}

	private void initializeVariables(ProcessExecutionContext context) {
		dataspace = ((DataContextReadOnly) context).getVariableString("childDataSpace");
		dataset = ((DataContextReadOnly) context).getVariableString("dataset");
		table = ((DataContextReadOnly) context).getVariableString("table");
		record = ((DataContextReadOnly) context).getVariableString("record");

		repo = Repository.getDefault();
		dataspaceName = repo.lookupHome(HomeKey.forBranchName(dataspace));
		datasetName = dataspaceName.findAdaptationOrNull(AdaptationName.forName(dataset));
	}
}
