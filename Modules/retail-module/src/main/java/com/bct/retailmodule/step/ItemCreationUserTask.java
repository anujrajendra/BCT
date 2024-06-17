package com.bct.retailmodule.step;

import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.workflow.UserTask;
import com.orchestranetworks.workflow.UserTaskWorkItemCompletionContext;

public class ItemCreationUserTask extends UserTask{

	public static final String STEWARD_VARIABLE_NAME = "steward";
	
	@Override
	public void handleWorkItemCompletion(UserTaskWorkItemCompletionContext context) throws OperationException {
		
		final WorkItem workItem = context.getCompletedWorkItem();
		final UserReference user = workItem.getUserReference();
		final String userName = user.getUserId();
		
		context.setVariableString(STEWARD_VARIABLE_NAME, userName);
		
		context.completeUserTask();

	}

	
}
