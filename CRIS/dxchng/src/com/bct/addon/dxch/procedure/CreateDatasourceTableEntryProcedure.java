package com.bct.addon.dxch.procedure;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.bct.addon.dxch.path.DXchngPath;
import com.bct.addon.dxch.rest.utils.RequestUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.onwbp.adaptation.XPathExpressionHelper;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;
import com.orchestranetworks.service.ValueContextForUpdate;

public class CreateDatasourceTableEntryProcedure implements Procedure {

	AdaptationTable tbconfig;
	String ipDataSet;
	public Adaptation createdRecord = null;

	public CreateDatasourceTableEntryProcedure(final AdaptationTable tbconfig, final String ipDataSet) {
		super();
		this.tbconfig = tbconfig;
		this.ipDataSet = ipDataSet;

	}

	@Override
	public void execute(ProcedureContext pContext) throws Exception {
		// TODO Auto-generated method stub

		ValueContextForUpdate vcfu = null;
		vcfu = pContext.getContextForNewOccurrence(this.tbconfig);
		RequestUtils.setRecord(vcfu, "AAAA", DXchngPath._DataSourceTable._Database);

		RequestUtils.setRecord(vcfu, "dbo." + this.ipDataSet, DXchngPath._DataSourceTable._TableName);

		createdRecord = pContext.doCreateOccurrence(vcfu, this.tbconfig);

	}

}
