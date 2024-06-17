package com.bct.addon.dxch.procedure;

import com.onwbp.adaptation.AdaptationHome;
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

public class CreateDataSetProcedure implements Procedure {

	String ip_dsname;
	String xsdmodule;
	Repository repository;
	Session session;

	public CreateDataSetProcedure(final String ip_dsname, final String xsdmodule,
			final Repository repository, final Session session) {
		super();
		this.ip_dsname = ip_dsname;
		this.xsdmodule = xsdmodule;
		this.repository = repository;
		this.session = session;

	}

	@Override
	public void execute(ProcedureContext pContext) throws Exception {
		// TODO Auto-generated method stub

		SchemaLocation dataModelLocation = SchemaLocation.forPathInModule("/WEB-INF/ebx/schemas/"+ip_dsname+".xsd",
				xsdmodule);

		pContext.doCreateRoot(dataModelLocation, AdaptationReference.forPersistentName(this.ip_dsname),
				Profile.EVERYONE);

	}

}
