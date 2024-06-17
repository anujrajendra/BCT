package com.bct.addon.dxch.procedure;

import com.onwbp.adaptation.*;
import com.orchestranetworks.schema.*;
import com.orchestranetworks.service.*;

/**
 *
 * @author Lalith
 */
public class CreationofDSnDMProcedure implements Procedure {

	String dataspaceName;

	public CreationofDSnDMProcedure(final String dataspaceName) {
		super();
		this.dataspaceName = dataspaceName;

	}

	@Override
	public void execute(final ProcedureContext aContext) throws Exception {
		// TODO auto generated : to check

		SchemaLocation dataModelLocation = SchemaLocation.forPathInModule("/WEB-INF/ebx/schemas/AES_ST_2022.xsd",
				"dxchng");

		aContext.doCreateRoot(dataModelLocation, AdaptationReference.forPersistentName(this.dataspaceName),
				Profile.EVERYONE);

	}

}
