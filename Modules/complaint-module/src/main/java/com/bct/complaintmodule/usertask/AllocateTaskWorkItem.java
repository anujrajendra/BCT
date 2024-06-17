package com.bct.complaintmodule.usertask;

import com.bct.complaintmodule.Paths;
import com.bct.complaintmodule.procedure.ComplaintEntryProcedure;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;
import com.orchestranetworks.service.ProgrammaticService;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.service.ValueContextForUpdate;
import com.orchestranetworks.userservice.UserServiceTransaction;
import com.orchestranetworks.workflow.CreationWorkItemSpec;
import com.orchestranetworks.workflow.UserTask;
import com.orchestranetworks.workflow.UserTaskBeforeWorkItemCompletionContext;
import com.orchestranetworks.workflow.UserTaskCreationContext;
import com.orchestranetworks.workflow.UserTaskWorkItemCompletionContext;

public class AllocateTaskWorkItem extends UserTask{

	private String referenceDataspace;
	private String dataspace;
	private String dataset;
	private String table;
	private String record;
	
	private String user1;
	private String user2;
	private String user3;
	private String user4;
	private String user5;
	private String user6;
	
	Repository repo;
	AdaptationHome dataspaceName;
	Adaptation datasetName;
	AdaptationTable adaptationTable = null;
	Adaptation adaptationRecord = null;
	@Override
	public void handleCreate(UserTaskCreationContext context) throws OperationException {
		
		user1 = context.getVariableString("user1");
		user2 = context.getVariableString("user2");
		user3 = context.getVariableString("user3");
		user4 = context.getVariableString("user4");
		user5 = context.getVariableString("user5");
		user6 = context.getVariableString("user6");
		
		UserReference reference1 = UserReference.forUser(user1);
		UserReference reference2 = UserReference.forUser(user2);
		UserReference reference3 = UserReference.forUser(user3);
		UserReference reference4 = UserReference.forUser(user4);
		UserReference reference5 = UserReference.forUser(user5);
		UserReference reference6 = UserReference.forUser(user6);
		
		CreationWorkItemSpec creationWorkItemSpec1 =
				CreationWorkItemSpec.forAllocation(reference1);
		CreationWorkItemSpec creationWorkItemSpec2 =
				CreationWorkItemSpec.forAllocation(reference2);
		CreationWorkItemSpec creationWorkItemSpec3 =
				CreationWorkItemSpec.forAllocation(reference3);
		CreationWorkItemSpec creationWorkItemSpec4 =
				CreationWorkItemSpec.forAllocation(reference4);
		CreationWorkItemSpec creationWorkItemSpec5 =
				CreationWorkItemSpec.forAllocation(reference5);
		CreationWorkItemSpec creationWorkItemSpec6 =
				CreationWorkItemSpec.forAllocation(reference6);
		
		dataspace = context.getVariableString("dataspace");
		dataset = context.getVariableString("dataset");
		table = context.getVariableString("table");
		record = context.getVariableString("record");
		
		repo = Repository.getDefault();
		dataspaceName = repo.lookupHome(HomeKey.forBranchName(dataspace));
		datasetName = dataspaceName.findAdaptationOrNull(AdaptationName.forName(dataset));
		
		String tableString = record.substring(0, record.indexOf("["));
		String recordIdentifier = record.substring(record.indexOf("=")+1, record.indexOf("]"));
		
		adaptationTable = datasetName.getTable(Path.parse(tableString));
		adaptationRecord = adaptationTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(recordIdentifier));
		System.out.println("-----Reference Dataspace------"+referenceDataspace);
		System.out.println("-------Department ID--------"+adaptationRecord.get(Paths._Root_ComplaintRegistration._Root_ComplaintRegistration_Department)); 
		System.out.println("-----Record------"+adaptationRecord.toString());
		
		String deptId = (String) adaptationRecord.get(Paths._Root_ComplaintRegistration._Root_ComplaintRegistration_Department);
		String status = (String) adaptationRecord.get(Paths._Root_ComplaintRegistration._Root_ComplaintRegistration_ComplaintStatus);
		System.out.println("---------Appellate---------"+status);
		
		if (status.equalsIgnoreCase("Rejected"))
		{
			creationWorkItemSpec6.setSpecificLabel(UserMessage.createInfo("Task assigned to"+user6));
			context.createWorkItem(creationWorkItemSpec6);
		}
		else {
			
		switch (deptId) {
		case "1":
			creationWorkItemSpec1.setSpecificLabel(UserMessage.createInfo("Task assigned to "+user1));
			context.createWorkItem(creationWorkItemSpec1);
			
			break;
		case "2":
			creationWorkItemSpec2.setSpecificLabel(UserMessage.createInfo("Task assigned to "+user2));
			context.createWorkItem(creationWorkItemSpec2);
			
			break;
		case "3":
			creationWorkItemSpec3.setSpecificLabel(UserMessage.createInfo("Task assigned to "+user3));
			context.createWorkItem(creationWorkItemSpec3);
			
			break;
		case "4":
			creationWorkItemSpec4.setSpecificLabel(UserMessage.createInfo("Task assigned to "+user4));
			context.createWorkItem(creationWorkItemSpec4);
			
			break;
			
		case "5":
			creationWorkItemSpec5.setSpecificLabel(UserMessage.createInfo("Task assigned to "+user5));
			context.createWorkItem(creationWorkItemSpec5);
			
			break;
		default:
			creationWorkItemSpec6.setSpecificLabel(UserMessage.createInfo("Task assigned to "+user6));
			context.createWorkItem(creationWorkItemSpec6);
			
			break;
		}
		}
	}
	
	@Override
	public void handleWorkItemCompletion(UserTaskWorkItemCompletionContext aContext) throws OperationException {
		// TODO Auto-generated method stub
		System.out.println("-----UserTask.WorkItem.State.COMPLETED----- "+UserTask.WorkItem.State.COMPLETED);
		System.out.println("-----UserTask.CompletionStrategy.ALL_WORK_ITEMS_ACCEPTED---"+UserTask.CompletionStrategy.ALL_WORK_ITEMS_ACCEPTED);
		super.handleWorkItemCompletion(aContext);
	}

	@Override
	public void checkBeforeWorkItemCompletion(UserTaskBeforeWorkItemCompletionContext aContext) {
		// TODO Auto-generated method stub
		dataspace = aContext.getVariableString("dataspace");
		dataset = aContext.getVariableString("dataset");
		table = aContext.getVariableString("table");
		record = aContext.getVariableString("record");
		
		repo = Repository.getDefault();
		
		dataspaceName = repo.lookupHome(HomeKey.forBranchName(dataspace));
		datasetName = dataspaceName.findAdaptationOrNull(AdaptationName.forName(dataset));
		
		String tableString = record.substring(0, record.indexOf("["));
		String recordIdentifier = record.substring(record.indexOf("=")+1, record.indexOf("]"));
		
		adaptationTable = datasetName.getTable(Path.parse(tableString));
		adaptationRecord = adaptationTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(recordIdentifier));
		String status = (String) adaptationRecord.get(Paths._Root_ComplaintRegistration._Root_ComplaintRegistration_ComplaintStatus);
		
		Procedure procedure = new Procedure() {
			@Override
			
			public void execute(ProcedureContext pContext) throws Exception {
				// TODO Auto-generated method stub
				
				ValueContextForUpdate vcfu = 
						pContext.getContext(adaptationRecord.getAdaptationName());
				
				if(aContext.isAcceptAction()) {
					System.out.println("-------"+aContext.isAcceptAction()+"-----Task not accepted------");
					System.out.println("-----AcceptAction Record------"+adaptationRecord.toString());
					vcfu.setValue("Resolved", Paths._Root_ComplaintRegistration._Root_ComplaintRegistration_ComplaintStatus);
					//vcfu.setValueEnablingPrivilegeForNode("Resolved", Paths._Root_ComplaintRegistration._Root_ComplaintRegistration_ComplaintStatus);
					
				}
				else {
					vcfu.setValue("Rejected", Paths._Root_ComplaintRegistration._Root_ComplaintRegistration_ComplaintStatus);
				}
				
				pContext.doModifyContent(adaptationRecord, vcfu);
			}
		};
		ProgrammaticService svc = ProgrammaticService.createForSession(aContext.getSession(), dataspaceName);
		svc.execute(procedure);
		
//		if(!aContext.isAcceptAction()) {
//			System.out.println("-----Task not accepted------");
//		}
		
		super.checkBeforeWorkItemCompletion(aContext);
	}
	
	
	
}
