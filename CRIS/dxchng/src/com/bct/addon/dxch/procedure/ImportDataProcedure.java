package com.bct.addon.dxch.procedure;

import java.util.List;

import com.bct.addon.dxch.dto.ColumnMetadata;
import com.bct.addon.dxch.rule.DataRules;
import com.bct.addon.dxch.rule.DatabaseRules;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;

public class ImportDataProcedure implements Procedure {
	
	AdaptationTable tbconfig;
	String predicate;
	String wfStatus;
	DatabaseRules dbRules = new DatabaseRules();
	DataRules dataRules = new DataRules();

	public ImportDataProcedure(final AdaptationTable tbconfig, final String predicate) {
		super();
		this.tbconfig = tbconfig;
		this.predicate = predicate;
		

	}

	@Override
	public void execute(ProcedureContext context) throws Exception {
		// TODO Auto-generated method stub
		RequestResult result = this.tbconfig.createRequestResult(this.predicate);
		
		Adaptation tgtRecord = result.nextAdaptation();
		
		
		List<ColumnMetadata> columns = this.dbRules.createOrUpdateColumnsScriptTask(tgtRecord,
				context);
		
		this.dataRules.importData(tgtRecord, columns);
		
	}

}
