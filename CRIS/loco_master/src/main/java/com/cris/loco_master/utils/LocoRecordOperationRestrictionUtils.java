package com.cris.loco_master.utils;

import java.util.List;

import com.orchestranetworks.workflow.ProcessInstanceKey;
import com.orchestranetworks.workflow.PublishedProcessKey;
import com.orchestranetworks.workflow.WorkflowEngine;

public class LocoRecordOperationRestrictionUtils {

	public static boolean checkRunningProcesses(WorkflowEngine wfEngine, String workflowName,
			String dataContextVariable, String xpathExpression) {

		List<ProcessInstanceKey> instanceKeysList = wfEngine.getProcessInstanceKeysForDataContextVariable(
				PublishedProcessKey.forName(workflowName), dataContextVariable, xpathExpression);

		for (ProcessInstanceKey processInstanceKey : instanceKeysList) {
			if (wfEngine.isProcessInstanceRunning(processInstanceKey))
				return true;
		}
		return false;
	}

	public static int countRunningProcesses(WorkflowEngine wfEngine, String workflowName, String dataContextVariable,
			String xpathExpression) {
		int count = 0;
		List<ProcessInstanceKey> instanceKeysList = wfEngine.getProcessInstanceKeysForDataContextVariable(
				PublishedProcessKey.forName(workflowName), dataContextVariable, xpathExpression);

		for (ProcessInstanceKey processInstanceKey : instanceKeysList) {
			if (wfEngine.isProcessInstanceRunning(processInstanceKey)) {
				count++;
				if (count >= 2)
					break;
			}

		}

		return count;
	}
}
