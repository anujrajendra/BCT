package com.cris.user_registration.extensions;

import com.cris.user_registration.Paths;
import com.cris.user_registration.access.ShedUserReadOnlyFieldsAccessRule;
import com.cris.user_registration.access.UserRegistrationAccessRule;
import com.cris.user_registration.service.workflow.userregistration.RegisterUserServiceDeclaration;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaExtensions;
import com.orchestranetworks.schema.SchemaExtensionsContext;
import com.orchestranetworks.service.AccessRule;
import com.orchestranetworks.userservice.declaration.UserServiceDeclaration;

public class UserRegistrationExtensions implements SchemaExtensions {

	@Override
	public void defineExtensions(SchemaExtensionsContext context) {
		// TODO Auto-generated method stub

		final UserServiceDeclaration.OnTableView userServiceDeclarationRegisterUser = new RegisterUserServiceDeclaration();
		context.registerUserService(userServiceDeclarationRegisterUser);

		final Path path = Paths._Root_Business_Users_Registration_Details.getPathInSchema();
		final AccessRule userRegistrationAccessRule = new UserRegistrationAccessRule();

		context.setAccessRuleOnOccurrence(path, userRegistrationAccessRule);

		final Path divisionPath = Paths._Root_Business_Users_Registration_Details.getPathInSchema()
				.add(Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Divison);
		final Path shedPath = Paths._Root_Business_Users_Registration_Details.getPathInSchema()
				.add(Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Shed);

		final AccessRule shedUserReadOnlyFieldsAccessRule = new ShedUserReadOnlyFieldsAccessRule();

		context.setAccessRuleOnNode(divisionPath, shedUserReadOnlyFieldsAccessRule);
		context.setAccessRuleOnNode(shedPath, shedUserReadOnlyFieldsAccessRule);

	}

}
