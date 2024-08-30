package com.cris.loco_master.service.bulkupdate;

import com.cris.loco_master.Paths;
import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.declaration.ActivationContextOnTableView;
import com.orchestranetworks.userservice.declaration.UserServiceDeclaration;
import com.orchestranetworks.userservice.declaration.UserServicePropertiesDefinitionContext;
import com.orchestranetworks.userservice.declaration.WebComponentDeclarationContext;

public class BulkUpdateServiceDeclaration implements UserServiceDeclaration.OnTableView {

	// Identifier for the service.
	public static final ServiceKey SERVICE_KEY = ServiceKey.forName("bulk_update_record");

	@Override
	public UserService<TableViewEntitySelection> createUserService() {
		return new BulkUpdateService();
	}

	@Override
	public void declareWebComponent(WebComponentDeclarationContext context) {
		// TODO Auto-generated method stub
		context.setAvailableAsPerspectiveAction(true);

	}

	@Override
	public void defineActivation(ActivationContextOnTableView arg0) {
		// TODO Auto-generated method stub
		arg0.includeSchemaNodesMatching(Paths._Root_Locomotive.getPathInSchema());

	}

	@Override
	public void defineProperties(UserServicePropertiesDefinitionContext context) {
		// TODO Auto-generated method stub
		context.setLabel("Bulk Update");
	}

	@Override
	public ServiceKey getServiceKey() {
		// TODO Auto-generated method stub
		return SERVICE_KEY;
	}

}
