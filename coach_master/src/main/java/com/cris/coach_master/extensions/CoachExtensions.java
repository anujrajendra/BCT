package com.cris.coach_master.extensions;

import com.cris.coach_master.service.workflow.LaunchUpdateWorkflowServiceDeclaration;
import com.orchestranetworks.schema.SchemaExtensions;
import com.orchestranetworks.schema.SchemaExtensionsContext;
import com.orchestranetworks.userservice.declaration.UserServiceDeclaration;

public class CoachExtensions implements SchemaExtensions{

	@Override
	public void defineExtensions(SchemaExtensionsContext context) {
		// TODO Auto-generated method stub
		
		final UserServiceDeclaration.OnTableView userServiceDeclarationUpdate =
				new LaunchUpdateWorkflowServiceDeclaration();
		context.registerUserService(userServiceDeclarationUpdate);
		
	}

}
