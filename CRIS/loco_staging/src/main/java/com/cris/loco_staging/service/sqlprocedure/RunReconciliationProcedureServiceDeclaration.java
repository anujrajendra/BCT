package com.cris.loco_staging.service.sqlprocedure;

import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.declaration.ActivationContextOnTableView;
import com.orchestranetworks.userservice.declaration.UserServiceDeclaration;
import com.orchestranetworks.userservice.declaration.UserServicePropertiesDefinitionContext;
import com.orchestranetworks.userservice.declaration.WebComponentDeclarationContext;

public class RunReconciliationProcedureServiceDeclaration implements UserServiceDeclaration.OnTableView {

	public static final ServiceKey SERVICE_KEY = ServiceKey.forName("Run_Reconciliation_SQL_Procedure");

	@Override
	public ServiceKey getServiceKey() {
		// TODO Auto-generated method stub
		return SERVICE_KEY;
	}

	@Override
	public UserService<TableViewEntitySelection> createUserService() {
		// TODO Auto-generated method stub
		return new RunReconciliationProcedureService();
	}

	@Override
	public void declareWebComponent(WebComponentDeclarationContext context) {
		// TODO Auto-generated method stub
		context.setAvailableAsToolbarAction(true);
	}

	@Override
	public void defineActivation(ActivationContextOnTableView arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void defineProperties(UserServicePropertiesDefinitionContext arg0) {
		// TODO Auto-generated method stub
		arg0.setLabel("Run Reconciliation Procedure");

	}
}
