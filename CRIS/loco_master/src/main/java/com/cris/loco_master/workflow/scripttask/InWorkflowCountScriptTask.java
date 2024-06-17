package com.cris.loco_master.workflow.scripttask;

import com.cris.loco_master.Paths;
import com.cris.loco_master.utils.LocoRecordOperationRestrictionUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;
import com.orchestranetworks.service.ProgrammaticService;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.service.ValueContextForUpdate;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;
import com.orchestranetworks.workflow.WorkflowEngine;

public class InWorkflowCountScriptTask extends ScriptTaskBean {

	private String dataspace;
	private String dataset;
	private String table;
	private String record;

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

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	@Override
	public void executeScript(ScriptTaskBeanContext context) throws OperationException {
		// TODO Auto-generated method stub

		Session session = context.getSession();

		Repository repo;
		AdaptationHome dataspaceName;
		Adaptation datasetName;
		AdaptationTable adaptationTable = null;
		Adaptation adaptationRecord;

		repo = Repository.getDefault();
		dataspaceName = repo.lookupHome(HomeKey.forBranchName(dataspace));
		datasetName = dataspaceName.findAdaptationOrNull(AdaptationName.forName(dataset));

		String tableString = record.substring(0, record.indexOf("["));

		String recordIdentifier = record.substring(record.indexOf("=") + 2, record.indexOf("]") - 1);

		adaptationTable = datasetName.getTable((Path.parse(tableString)));
		adaptationRecord = adaptationTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(recordIdentifier));

		Procedure procedure = new Procedure() {

			@Override
			public void execute(ProcedureContext pContext) throws Exception {
				// TODO Auto-generated method stub
				ValueContextForUpdate vcfu = pContext.getContext(adaptationRecord.getAdaptationName());

				Repository repository = Repository.getDefault();
				WorkflowEngine wfEngine = WorkflowEngine.getFromRepository(repository, pContext.getSession());

				int countWorkflowInstances = LocoRecordOperationRestrictionUtils.countRunningProcesses(wfEngine,
						"loco_create_api", "record", "/root/Locomotive[./Loco_Number=\"" + recordIdentifier + "\"]")
						+ LocoRecordOperationRestrictionUtils.countRunningProcesses(wfEngine, "loco_create_ui",
								"record", adaptationRecord.toXPathExpression())
						+ LocoRecordOperationRestrictionUtils.countRunningProcesses(wfEngine, "loco_update_ui",
								"record", adaptationRecord.toXPathExpression())
						+ LocoRecordOperationRestrictionUtils.countRunningProcesses(wfEngine, "loco_update_ui_daa",
								"record", adaptationRecord.toXPathExpression())
						+ LocoRecordOperationRestrictionUtils.countRunningProcesses(wfEngine, "loco_condemn_ui",
								"record", adaptationRecord.toXPathExpression())
						+ LocoRecordOperationRestrictionUtils.countRunningProcesses(wfEngine, "loco_transfer_ui",
								"record", adaptationRecord.toXPathExpression());

				System.out.println("===Count Instances===" + countWorkflowInstances);

				if (countWorkflowInstances == 1) {

					vcfu.setValueEnablingPrivilegeForNode("No",
							Paths._Root_Locomotive._Root_Locomotive_Audit_Info_In_Workflow);
					pContext.doModifyContent(adaptationRecord, vcfu);
				}

			}
		};
		ProgrammaticService svc = ProgrammaticService.createForSession(context.getSession(), dataspaceName);
		svc.execute(procedure);

	}

}
