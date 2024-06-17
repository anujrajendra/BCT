package com.cris.loco_master.workflow.usertask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.cris.loco_master.Paths;
import com.cris.loco_master.constant.Constants;
import com.cris.loco_master.utils.LocoRecordOperationRestrictionUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.onwbp.base.text.Severity;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.interactions.InteractionHelper.ParametersMap;
import com.orchestranetworks.interactions.SessionInteraction;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Profile;
import com.orchestranetworks.service.Role;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.service.ValidationReport;
import com.orchestranetworks.service.directory.DirectoryHandler;
import com.orchestranetworks.workflow.CreationWorkItemSpec;
import com.orchestranetworks.workflow.DataContextReadOnly;
import com.orchestranetworks.workflow.ProcessExecutionContext;
import com.orchestranetworks.workflow.UserTask;
import com.orchestranetworks.workflow.UserTaskBeforeWorkItemCompletionContext;
import com.orchestranetworks.workflow.UserTaskCreationContext;
import com.orchestranetworks.workflow.WorkflowEngine;

public class LocoTaskAssignmentUserTask extends UserTask {

	String specificRoleName;
	String workflowName;

	public String getSpecificRoleName() {
		return specificRoleName;
	}

	public void setSpecificRoleName(String specificRoleName) {
		this.specificRoleName = specificRoleName;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	private String dataspace;
	private String dataset;
	private String table;
	private String record;
	private String userRole;

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

		if (workflowName.equalsIgnoreCase("create")) {
			if (context.isAcceptAction()) {
				SessionInteraction sessionInteraction = context.getSession().getInteraction(false);
				ParametersMap sessionParamMap = sessionInteraction.getInternalParameters();

				Iterator<String> variableNames = sessionParamMap.getVariableNames();

				String record = sessionParamMap.getVariableString("created");
				String locoNumber = record.substring(record.indexOf("=") + 1, record.lastIndexOf("]"));

				String tableString = record.substring(0, record.indexOf("["));
				String recordIdentifier = record.substring(record.indexOf("=") + 2, record.indexOf("]") - 1);

				adaptationTable = datasetName.getTable((Path.parse(tableString)));
				adaptationRecord = adaptationTable
						.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(recordIdentifier));

				WorkflowEngine wfEngine = WorkflowEngine.getFromRepository(repo, context.getSession());

				if (LocoRecordOperationRestrictionUtils.checkRunningProcesses(wfEngine, "loco_create_ui", "record",
						adaptationRecord.toXPathExpression())) {

					context.reportMessage(UserMessage.createError(" The selected record Loco Number = " + locoNumber
							+ " has already an operation (Loco Create) in process. Please Cancel this operation"));
				}
				if (LocoRecordOperationRestrictionUtils.checkRunningProcesses(wfEngine, "loco_create_api", "record",
						"/root/Locomotive[./Loco_Number=\"" + locoNumber + "\"]")) {
					context.reportMessage(UserMessage.createError(" The selected record Loco Number = " + locoNumber
							+ " has already an operation (Loco Create API) in process. Please Cancel this operation"));
				}

				String userId = context.getSession().getUserReference().getUserId();

				UserReference userReference = UserReference.forUser(userId);

				String emptyFieldsString = "";
				Boolean errorFlag = false;

				if (adaptationRecord.get(Path.parse("./Loco_Type")) == null) {
					emptyFieldsString = emptyFieldsString.concat("\n \n Loco Type \n");
					errorFlag = true;
				}

				if (adaptationRecord.get(Path.parse("./Loco_Permanent_Domain")) == null) {
					emptyFieldsString = emptyFieldsString.concat("\n Loco Permanent Domain \n");
					errorFlag = true;
				}

				if (adaptationRecord.get(Path.parse("./Loco_Traction_Code")) == null) {
					emptyFieldsString = emptyFieldsString.concat("\n Loco Traction Code \n");
					errorFlag = true;
				}

				if (adaptationRecord.get(Path.parse("./Loco_Owning_Zone")) == null) {
					emptyFieldsString = emptyFieldsString.concat("\n Loco Owning Zone \n");
					errorFlag = true;
				}

				if (adaptationRecord.get(Path.parse("./Loco_Owning_Division")) == null) {
					emptyFieldsString = emptyFieldsString.concat("\n Loco Owning Division \n");
					errorFlag = true;
				}

				if (adaptationRecord.get(Path.parse("./Loco_Owning_Shed")) == null) {
					emptyFieldsString = emptyFieldsString.concat("\n Loco Owning Shed \n");
					errorFlag = true;
				}

				if ((DirectoryHandler.getInstance(Repository.getDefault()).isUserInRole(userReference,
						Role.forSpecificRole("Shed_DS")))
						|| (DirectoryHandler.getInstance(Repository.getDefault()).isUserInRole(userReference,
								Role.forSpecificRole("Shed_DAA")))) {

					if (adaptationRecord.get(Path.parse("./Loco_Manufacturing_Date")) == null) {
						emptyFieldsString = emptyFieldsString.concat("\n Loco Manufacturing Date \n");
						errorFlag = true;
					}

					if (adaptationRecord.get(Path.parse("./Loco_Receiving_Date")) == null) {
						emptyFieldsString = emptyFieldsString.concat("\n Loco Receiving Date \n");
						errorFlag = true;
					}

					if (adaptationRecord.get(Path.parse("./Loco_Commissioning_Date")) == null) {
						emptyFieldsString = emptyFieldsString.concat("\n Loco Commissioning Date \n");
						errorFlag = true;
					}
				}

				if (errorFlag == true) {
					context.reportMessage(
							UserMessage.createError("Following Fields cannot be empty - " + emptyFieldsString));
				}

			}
		}

		if (workflowName.equalsIgnoreCase("update")) {
			if (context.isAcceptAction()) {

				String tableString = record.substring(0, record.indexOf("["));
				String recordIdentifier = record.substring(record.indexOf("=") + 2, record.indexOf("]") - 1);

				adaptationTable = datasetName.getTable((Path.parse(table)));
				adaptationRecord = adaptationTable
						.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(recordIdentifier));

				ValidationReport validationReport = adaptationRecord.getValidationReport();

				int countErrorMessage = validationReport.countItemsOfSeverity(Severity.ERROR);
				if (countErrorMessage >= 1) {
					context.reportMessage(
							UserMessage.createError("Without mandatory fields, form cannot be submitted "));
				}
			}

		}
	}

	@Override
	public void handleCreate(UserTaskCreationContext context) throws OperationException {

		Collection<Profile> profiles = new ArrayList<Profile>();

		if (workflowName.equalsIgnoreCase("create")) {
			profiles.add(UserReference.forSpecificRole("RB_DS"));
			profiles.add(UserReference.forSpecificRole("Shed_DS"));
			profiles.add(UserReference.forSpecificRole("RB_DAA"));
			profiles.add(UserReference.forSpecificRole("Shed_DAA"));

			context.createWorkItem(CreationWorkItemSpec.forOfferring(profiles));

		} else {
			initializeVariables(context);

			String tableString = record.substring(0, record.indexOf("["));
			String recordIdentifier = record.substring(record.indexOf("=") + 2, record.indexOf("]") - 1);

			adaptationTable = datasetName.getTable((Path.parse(tableString)));
			adaptationRecord = adaptationTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(recordIdentifier));

			String shedCode = (String) adaptationRecord.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Shed);
			String zoneCode = (String) adaptationRecord.get(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Zone);

			Repository repository = Repository.getDefault();

			Adaptation userPermissionsRecord = null;

			String userPermissionsQuery = "";
			Query<Tuple> userPermissionsQueryTuple;
			QueryResult<Tuple> userPermissionsQueryRecords;

			if (workflowName == null)
				workflowName = "";
			if (specificRoleName == null)
				specificRoleName = "";

			if (workflowName.equalsIgnoreCase("transfer")) {
				userPermissionsQuery = "Select s.\"$adaptation\" from \"/root/User_Permissions_Zone\" s where FK_AS_STRING('user_data','/root/User_Permissions_Zone', s.Zone_Code) = '"
						+ zoneCode + "'";
			} else {
				userPermissionsQuery = "Select s.\"$adaptation\" from \"/root/User_Permissions_Shed\" s where FK_AS_STRING('user_data','/root/User_Permissions_Shed', s.Shed_Code) = '"
						+ shedCode + "'";
			}
			userPermissionsQueryTuple = Constants.DATASET_USERREGISTRATION.createQuery(userPermissionsQuery);
			userPermissionsQueryRecords = userPermissionsQueryTuple.getResult();

			for (Tuple record : userPermissionsQueryRecords) {
				// System.out.println("===Inside for loop transfer handle Create===");
				userPermissionsRecord = (Adaptation) record.get(0);
				String userId = (String) userPermissionsRecord.get(Path.parse("./Ipas_Id"));

				UserReference userReference = UserReference.forUser(userId);
				Boolean userRoleFlag = DirectoryHandler.getInstance(repository).isUserInRole(userReference,
						Role.forSpecificRole(specificRoleName));

				if (userRoleFlag) {
					profiles.add(Profile.forUser(userId));
				}
			}

			if (workflowName.equalsIgnoreCase("create_production"))
				profiles.add(UserReference.forSpecificRole("PU_DS"));

			if (!profiles.isEmpty()) {
				context.createWorkItem(CreationWorkItemSpec.forOfferring(profiles));
			} else {
				// context.createWorkItem(CreationWorkItemSpec.forOffer(Role.ADMINISTRATOR));
				context.createWorkItem(CreationWorkItemSpec.forOfferring(UserReference.forUser("admin")));
			}
		}
	}

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