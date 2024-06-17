package com.bct.addon.dxch.procedure;

import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationReference;
import com.onwbp.adaptation.AdaptationTable;
import com.orchestranetworks.instance.HomeCreationSpec;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.SchemaLocation;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;
import com.orchestranetworks.service.Profile;
import com.orchestranetworks.service.Session;

public class DeleteDataSetProcedure implements Procedure {

	String ip_dsname;
	

	public DeleteDataSetProcedure(final String ip_dsname) {
		super();
		this.ip_dsname = ip_dsname;
		

	}

	@Override
	public void execute(ProcedureContext pContext) throws Exception {
		// TODO Auto-generated method stub
		
		AdaptationName dataset = AdaptationName.forName(ip_dsname);

		pContext.doDelete(dataset, true);

	}

}
