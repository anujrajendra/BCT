package com.bct.addon.dxch.procedure;

import com.bct.addon.dxch.path.AlteryxMangPath;
import com.bct.addon.dxch.path.DXchngPath;
import com.bct.addon.dxch.rest.utils.RequestUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.onwbp.adaptation.XPathExpressionHelper;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;
import com.orchestranetworks.service.ValueContextForUpdate;

public class CreateDatasetLookupEntryProcedure implements Procedure {

	AdaptationTable tbconfig;
	String ipDataSet;
	String WFID;
	public Adaptation createdRecord = null;

	public CreateDatasetLookupEntryProcedure(final AdaptationTable tbconfig, final String ipDataSet,final String WFID) {
		super();
		this.tbconfig = tbconfig;
		this.ipDataSet = ipDataSet;
		this.WFID = WFID;

	}

	@Override
	public void execute(ProcedureContext pContext) throws Exception {
		// TODO Auto-generated method stub

		ValueContextForUpdate vcfu = null;

		StringBuffer predicate = new StringBuffer();
		predicate.append(AlteryxMangPath._DatasetsLookup._DatasetName.format()).append("=")
				.append(XPathExpressionHelper.encodeLiteralStringWithDelimiters(ipDataSet));

		RequestResult requestResult = tbconfig.createRequestResult(predicate.toString());

		if (requestResult.isSizeGreaterOrEqual(1)) { // Update Current Record
			createdRecord = requestResult.nextAdaptation();
			vcfu = pContext.getContext(createdRecord.getAdaptationName());
			RequestUtils.setRecord(vcfu, WFID, Path.parse("./WFID"));
			RequestUtils.setRecord(vcfu, "Pending", AlteryxMangPath._DatasetsLookup._Status);
			
			createdRecord = pContext.doModifyContent(createdRecord, vcfu);

		} else {
			vcfu = pContext.getContextForNewOccurrence(this.tbconfig);
			RequestUtils.setRecord(vcfu, ipDataSet, AlteryxMangPath._DatasetsLookup._DatasetName);
			RequestUtils.setRecord(vcfu, WFID, Path.parse("./WFID"));
			RequestUtils.setRecord(vcfu, "Pending", AlteryxMangPath._DatasetsLookup._Status);

			createdRecord = pContext.doCreateOccurrence(vcfu, this.tbconfig);
		}
	}

}
