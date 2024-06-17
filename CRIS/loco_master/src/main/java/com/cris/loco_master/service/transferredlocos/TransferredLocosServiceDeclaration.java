package com.cris.loco_master.service.transferredlocos;

import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.declaration.ActivationContextOnTableView;
import com.orchestranetworks.userservice.declaration.UserServiceDeclaration;
import com.orchestranetworks.userservice.declaration.UserServicePropertiesDefinitionContext;
import com.orchestranetworks.userservice.declaration.WebComponentDeclarationContext;

public class TransferredLocosServiceDeclaration implements UserServiceDeclaration.OnTableView {

	public static final ServiceKey SERVICE_KEY = ServiceKey.forName("Transferred_Locos");

	@Override
	public ServiceKey getServiceKey() {
		// TODO Auto-generated method stub
		return SERVICE_KEY;
	}

	@Override
	public UserService<TableViewEntitySelection> createUserService() {
		// TODO Auto-generated method stub
		return new TransferredLocosService();
	}

	@Override
	public void declareWebComponent(WebComponentDeclarationContext context) {
		// TODO Auto-generated method stub
		context.setAvailableAsPerspectiveAction(true);
		context.setAvailableAsToolbarAction(false);
	}

	@Override
	public void defineActivation(ActivationContextOnTableView arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void defineProperties(UserServicePropertiesDefinitionContext arg0) {
		// TODO Auto-generated method stub
		arg0.setLabel("Transferred (Out) Locos");

	}
}
