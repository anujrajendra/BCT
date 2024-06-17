package com.cris.loco_master.service.spotfirescript;

import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.declaration.ActivationContextOnTableView;
import com.orchestranetworks.userservice.declaration.UserServiceDeclaration;
import com.orchestranetworks.userservice.declaration.UserServicePropertiesDefinitionContext;
import com.orchestranetworks.userservice.declaration.WebComponentDeclarationContext;

public class SpotfireDashboardServiceDeclaration implements UserServiceDeclaration.OnTableView {

	public static final ServiceKey SERVICE_KEY = ServiceKey.forName("Spotfire_Dashboard_Script");

	@Override
	public ServiceKey getServiceKey() {
		// TODO Auto-generated method stub
		return SERVICE_KEY;
	}

	@Override
	public UserService<TableViewEntitySelection> createUserService() {
		// TODO Auto-generated method stub
		return new SpotfireDashboardService();
	}

	@Override
	public void declareWebComponent(WebComponentDeclarationContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void defineActivation(ActivationContextOnTableView arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void defineProperties(UserServicePropertiesDefinitionContext arg0) {
		// TODO Auto-generated method stub
		arg0.setLabel("Spotfire Script Dashboard");

	}
}
