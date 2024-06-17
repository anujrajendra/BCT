package com.cris.test_module.procedure;

import java.util.ArrayList;
import java.util.List;

import com.bct.addon.dxch.dto.ColumnMetadata;
import com.bct.addon.dxch.rule.DatabaseRules;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;

public class InitiatorProcedure implements Procedure {

	public List<ColumnMetadata> columns = new ArrayList<ColumnMetadata>();
	Adaptation tgtRecord;

	public InitiatorProcedure(Adaptation tgtRecord) {
		super();
		this.tgtRecord = tgtRecord;
	}

	public List<ColumnMetadata> getColumns() {
		return columns;
	}

	@Override
	public void execute(ProcedureContext pContext) throws Exception {
		// TODO Auto-generated method stub

		DatabaseRules dbRules = new DatabaseRules();

		LoggingCategory.getWorkflow().info("===Inside procedure====");

		if (tgtRecord != null)
			LoggingCategory.getWorkflow().info("===Inside tgtrecord not null====");

		columns = dbRules.createOrUpdateColumnsScriptTask(tgtRecord, pContext);

	}

}
