package com.cris.reference_master.utilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.workflow.ProcessLauncher;
import com.orchestranetworks.workflow.ProcessLauncherResult;
import com.orchestranetworks.workflow.PublishedProcessKey;
import com.orchestranetworks.workflow.WorkItemKey;
import com.orchestranetworks.workflow.WorkflowEngine;

public class WorkflowLauncher {

	private Repository repository;
	private Session session;

	// Constructor to initialize the WorkflowLauncher class with initializing the
	// instance variable repository and session.
	// No default constructor is provided because without the repository and session
	// object no activity can be performed.
	public WorkflowLauncher(Repository repository, Session session) {
		super();
		this.repository = repository;
		this.session = session;
	}

	// Constructor to initialize the WorkflowLauncher class only with session
	// variable for repository using the default repository.
	public WorkflowLauncher(Session session) {
		super();
		this.session = session;
		this.repository = Repository.getDefault();
	}

	/*
	 * Method to invoke the workflow without returning any result.
	 */
	public WorkItemKey launch(String wfPublicationName, HashMap<String, String> inputParameters, String wfLabel,
			String wfDescription) throws OperationException {

		// Check if the workflow name is provided.
		if (wfPublicationName == null)
			throw OperationException.createError("Name of the process to launch is not specified. "
					+ "Please contact the administrator for more details");

		// Get the workflow engine object, to perform the various workflow
		// administrative activity.
		WorkflowEngine wfEngine = getWorkflowEngine();

		// Get the object representation from the workflow publication name.
		PublishedProcessKey processKey = getPublishedProcessKey(wfPublicationName);
		if (processKey == null)
			throw OperationException.createError("No Workflow found with the name specified.");

		ProcessLauncher launcher = getProcessLauncher(wfEngine, processKey);
		if (launcher == null)
			throw OperationException.createError("No process launcher found for the specified workflow name.");

		setDataContextVariable(launcher, inputParameters);
		setWorkflowCreator(launcher, session.getUserReference());
		setWorkflowLabel(launcher, wfLabel);
		setWorkflowDescription(launcher, wfDescription);

		ProcessLauncherResult launchResult = launcher.launchProcessWithResult();
		return launchResult.getWorkItemKey();
	}

	/*
	 * Method to get the reference for the WorkflowEngine object.
	 */
	public WorkflowEngine getWorkflowEngine() {
		return WorkflowEngine.getFromRepository(repository, session);
	}

	/*
	 * Method to get the PublishedProcessKey (Class representing the published
	 * workflow in EBX) from the workflow publication name.
	 */
	public PublishedProcessKey getPublishedProcessKey(String wfPublicationName) {
		if (wfPublicationName == null)
			return null;
		return PublishedProcessKey.forName(wfPublicationName);
	}

	/*
	 * Get the ProcessLauncher (Class to launch the workflow) for the published
	 * workflow name.
	 */
	public ProcessLauncher getProcessLauncher(WorkflowEngine wfEngine, PublishedProcessKey processKey) {
		if (processKey == null)
			return null;
		return wfEngine.getProcessLauncher(processKey);
	}

	/*
	 * Initialize the Data Context Variable for the workflow. If the data context
	 * variable is not defined in the workflow no action will be taken. It will not
	 * throw the error.
	 */
	public void setDataContextVariable(ProcessLauncher launcher, HashMap<String, String> dataContextValues) {
		if (dataContextValues == null || launcher == null)
			return;
		Iterator<Map.Entry<String, String>> iterator = dataContextValues.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> dataContextValue = iterator.next();
			String dataContextVariable = dataContextValue.getKey();
			if (isDataContextVariableDefined(launcher, dataContextVariable)) {
				String value = dataContextValue.getValue();
				launcher.setInputParameter(dataContextVariable, value);
			}
		}
	}

	/*
	 * Checks if the data context variable is defined in the workflow or not.
	 */
	public boolean isDataContextVariableDefined(ProcessLauncher launcher, String dataContextVariable) {
		if (dataContextVariable == null)
			return false;
		return launcher.isDefinedAsInputParameter(dataContextVariable);
	}

	/*
	 * Set the creator for the workflow instance.
	 */
	public void setWorkflowCreator(ProcessLauncher launcher, UserReference user) {
		if (launcher == null || user == null)
			return;
		launcher.setCreator(user);
	}

	/*
	 * Set the label for the workflow label.
	 */
	public void setWorkflowLabel(ProcessLauncher launcher, String wfLabel) {
		if (launcher != null && wfLabel != null)
			launcher.setLabel(UserMessage.createInfo(wfLabel));
	}

	/*
	 * Set the description for the workflow instance.
	 */
	public void setWorkflowDescription(ProcessLauncher launcher, String wfDescription) {
		if (launcher != null && wfDescription != null)
			launcher.setDescription(UserMessage.createInfo(wfDescription));
	}
}