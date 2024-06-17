package com.bct.addon.dxch.procedure;

import com.onwbp.adaptation.*;
import com.orchestranetworks.schema.*;
import com.orchestranetworks.service.*;

/**
 *
 * @author Lalith
 */
public class DsStatusUpdateProcedure implements Procedure {

	AdaptationTable tbconfig;
	String ipDataSet;
	String dsStatus;

	public DsStatusUpdateProcedure(final AdaptationTable tbconfig, final String ipDataSet, final String dsStatus) {
		super();
		this.tbconfig = tbconfig;
		this.ipDataSet = ipDataSet;
		this.dsStatus = dsStatus;

	}

	@Override
	public void execute(final ProcedureContext aContext) throws Exception {
		// TODO auto generated : to check

		RequestResult reqResult = this.tbconfig.createRequestResult("./datasetName='" + this.ipDataSet + "'");

		Adaptation tgtRecord = null;
		while ((tgtRecord = reqResult.nextAdaptation()) != null) {
			ValueContextForUpdate vcfu = aContext.getContext(tgtRecord.getAdaptationName());
			vcfu.setValueEnablingPrivilegeForNode(this.dsStatus, Path.parse("./status"));
			aContext.doModifyContent(tgtRecord, vcfu);
		}

	}

}
