package com.cris.reference_master.extensions;

import com.orchestranetworks.schema.SchemaExtensions;
import com.orchestranetworks.schema.SchemaExtensionsContext;

public class ReferenceExtensions implements SchemaExtensions {

	@Override
	public void defineExtensions(SchemaExtensionsContext context) {

		context.registerUserService(new com.cris.common.iframe.integration.WebPageIntegrationServiceDeclaration());

	}

}
