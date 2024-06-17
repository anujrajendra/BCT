package com.cris.wagon_master.workflow.scripttask;

import java.math.BigDecimal;

import com.cris.wagon_master.paths.WagonPaths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;
import com.orchestranetworks.service.ProgrammaticService;
import com.orchestranetworks.service.ValueContextForUpdate;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class WagonTypeScriptTask extends ScriptTaskBean {

	private String dataspace;
	private String dataset;
	private String table;
	private String record;

	private Adaptation adaptationRecord = null;
	private AdaptationTable wagonTypeMasterTable;
	private Adaptation wagonTypeMasterRecord;

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

		Repository repo;
		AdaptationHome dataspaceName;
		Adaptation datasetName;
		AdaptationTable adaptationTable = null;

		repo = Repository.getDefault();
		dataspaceName = repo.lookupHome(HomeKey.forBranchName(dataspace));
		datasetName = dataspaceName.findAdaptationOrNull(AdaptationName.forName(dataset));

		adaptationTable = datasetName.getTable((Path.parse(table)));

		String query = "Select s.\"$adaptation\" from \"/root/Wagon\" s where s.Wagon_Id = " + Integer.parseInt(record)
				+ "";
		Query<Tuple> queryTuple = datasetName.createQuery(query);
		QueryResult<Tuple> records = queryTuple.getResult();

		for (Tuple result : records) {
			adaptationRecord = (Adaptation) result.get(0);
		}

		if (adaptationRecord != null)
			System.out.println("===Adaptation Record is Not Null===");

		String wagonType = adaptationRecord.getString(WagonPaths._Root_Wagon._Root_Wagon_Wagon_Type);
		wagonTypeMasterTable = datasetName.getTable(Path.parse("/root/Wagon_Type"));
		wagonTypeMasterRecord = null;
		wagonTypeMasterRecord = wagonTypeMasterTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(wagonType));

		if (adaptationRecord != null && wagonTypeMasterRecord != null) {

			BigDecimal wagonTare = (BigDecimal) wagonTypeMasterRecord.get(Path.parse("./Wagon_Tare"));

			BigDecimal wagonCarryingCapacity = (BigDecimal) wagonTypeMasterRecord
					.get(Path.parse("./Wagon_Carrying_Capacity"));

			String wagonGaugeCode = (String) wagonTypeMasterRecord.get(Path.parse("./Wagon_Gauge"));

			if (wagonTare != null) {

				final Procedure procedure = new Procedure() {

					@Override
					public void execute(ProcedureContext procedureContext) throws Exception {

						ValueContextForUpdate vcfuRecord = procedureContext
								.getContext(adaptationRecord.getAdaptationName());
						if (wagonTare != null)
							vcfuRecord.setValueEnablingPrivilegeForNode(wagonTare,
									WagonPaths._Root_Wagon._Root_Wagon_Wagon_Tare);
						if (wagonCarryingCapacity != null)
							vcfuRecord.setValueEnablingPrivilegeForNode(wagonCarryingCapacity,
									WagonPaths._Root_Wagon._Root_Wagon_Wagon_Carrying_Capacity);
						if (wagonGaugeCode != null)
							vcfuRecord.setValueEnablingPrivilegeForNode(wagonGaugeCode,
									WagonPaths._Root_Wagon._Root_Wagon_Wagon_Gauge_Code);

						procedureContext.setAllPrivileges(true);
						procedureContext.doModifyContent(adaptationRecord, vcfuRecord);
						procedureContext.setAllPrivileges(false);
					}

				};

				ProgrammaticService svc = ProgrammaticService.createForSession(context.getSession(), dataspaceName);
				svc.execute(procedure);
			}

		}
	}
}
