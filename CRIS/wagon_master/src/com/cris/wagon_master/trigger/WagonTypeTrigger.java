package com.cris.wagon_master.trigger;

import com.cris.wagon_master.paths.WagonPaths;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.trigger.AfterCreateOccurrenceContext;
import com.orchestranetworks.schema.trigger.AfterModifyOccurrenceContext;
import com.orchestranetworks.schema.trigger.TableTrigger;
import com.orchestranetworks.schema.trigger.TriggerSetupContext;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.workflow.ProcessLauncher;
import com.orchestranetworks.workflow.PublishedProcessKey;
import com.orchestranetworks.workflow.WorkflowEngine;

public class WagonTypeTrigger extends TableTrigger {

	private Repository repository;

	@Override
	public void handleAfterCreate(AfterCreateOccurrenceContext aContext) throws OperationException {
		// TODO Auto-generated method stub

		repository = Repository.getDefault();

		Integer wagonId = (Integer) aContext.getAdaptationOccurrence().get(WagonPaths._Root_Wagon._Root_Wagon_Wagon_Id);
		launchWorkflow(wagonId, repository, aContext.getSession());
	}

	@Override
	public void handleAfterModify(AfterModifyOccurrenceContext aContext) throws OperationException {
		repository = Repository.getDefault();

		Integer wagonId = (Integer) aContext.getAdaptationOccurrence().get(WagonPaths._Root_Wagon._Root_Wagon_Wagon_Id);
		launchWorkflow(wagonId, repository, aContext.getSession());
	}

	private void launchWorkflow(Integer wagonId, Repository repository, Session session) throws OperationException {

		WorkflowEngine wfEngine = WorkflowEngine.getFromRepository(repository, session);
		ProcessLauncher launcher = null;
		launcher = wfEngine.getProcessLauncher(PublishedProcessKey.forName("wagon_type_inherited_fields"));
		launcher.setInputParameter("record", Integer.toString(wagonId));
		launcher.launchProcess();
	}

	@Override
	public void setup(TriggerSetupContext arg0) {
		// TODO Auto-generated method stub

	}

}
