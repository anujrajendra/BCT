package com.cris.test_module;

import com.orchestranetworks.schema.SchemaExtensions;
import com.orchestranetworks.schema.SchemaExtensionsContext;
import com.orchestranetworks.userservice.declaration.UserServiceDeclaration;

public class TestExtension implements SchemaExtensions {

	@Override
	public void defineExtensions(SchemaExtensionsContext context) {

		final UserServiceDeclaration.OnTableView userServiceDeclarationSQLProcedure = new DataImportServiceDeclaration();
		context.registerUserService(userServiceDeclarationSQLProcedure);
	}
}
