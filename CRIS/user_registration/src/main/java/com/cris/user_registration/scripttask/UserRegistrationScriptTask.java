package com.cris.user_registration.scripttask;

import java.util.Locale;

import com.cris.user_registration.procedure.UserRegistrationProcedure;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.ProcedureResult;
import com.orchestranetworks.service.ProgrammaticService;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.workflow.ProcessInstance;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class UserRegistrationScriptTask extends ScriptTaskBean {

	private String password;
	private String record;
	private String dataspace;
	private String dataset;
	private String table;

	@Override
	public void executeScript(final ScriptTaskBeanContext context) throws OperationException {

		Session session = context.getSession();

		Repository repo;
		AdaptationHome dataspaceName;
		Adaptation datasetName;
		AdaptationTable adaptationTable = null;
		Adaptation adaptationRecord = null;

		repo = Repository.getDefault();
		dataspaceName = repo.lookupHome(HomeKey.forBranchName(dataspace));
		datasetName = dataspaceName.findAdaptationOrNull(AdaptationName.forName(dataset));

		String tableString = record.substring(0, record.indexOf("["));
		String recordIdentifier = record.substring(record.indexOf("=") + 1, record.indexOf("]"));

		adaptationTable = datasetName.getTable((Path.parse(tableString)));

		String predicate = this.record.substring(this.record.indexOf("[") + 1, this.record.indexOf("]"));

		RequestResult resultuser = adaptationTable.createRequestResult(predicate);

		Adaptation userAdaptation = null;
		if (resultuser.isSizeGreaterOrEqual(1)) {
			userAdaptation = resultuser.nextAdaptation();
		} else {
			return;
		}

		AdaptationHome directoryDataspace = repo.lookupHome(HomeKey.forBranchName("ebx-directory"));
		Adaptation directoryDataset = directoryDataspace.findAdaptationOrNull(AdaptationName.forName("ebx-directory"));
		AdaptationTable directoryTable = directoryDataset.getTable(Path.parse("./directory/users"));
		AdaptationTable roleTable = directoryDataset.getTable(Path.parse("./directory/usersRoles"));

		ProgrammaticService svc = ProgrammaticService.createForSession(session, directoryDataspace);

		ProcessInstance wfInstance = context.getProcessInstance();

		UserRegistrationProcedure userRegistrationProcedure = new UserRegistrationProcedure(directoryTable, wfInstance,
				this.password, userAdaptation, roleTable);

		ProcedureResult pResult = svc.execute(userRegistrationProcedure);
		if (pResult.hasFailed()) {
			LoggingCategory.getWorkflow()
					.info("Supervision record script task failed. \n Execution timestamp: "
							+ pResult.getExecutionTimestamp().toString() + "\n  Reason is: "
							+ pResult.getExceptionFullMessage(Locale.ENGLISH));
		}

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getDataspace() {
		return dataspace;
	}

	public void setDataspace(String dataspace) {
		this.dataspace = dataspace;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

}