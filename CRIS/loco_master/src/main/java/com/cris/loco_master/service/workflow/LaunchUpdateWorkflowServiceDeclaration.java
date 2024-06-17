package com.cris.loco_master.service.workflow;
import com.cris.loco_master.Paths;
import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.declaration.ActivationContextOnTableView;
import com.orchestranetworks.userservice.declaration.UserServiceDeclaration;
import com.orchestranetworks.userservice.declaration.UserServicePropertiesDefinitionContext;
import com.orchestranetworks.userservice.declaration.WebComponentDeclarationContext;

public class LaunchUpdateWorkflowServiceDeclaration implements UserServiceDeclaration.OnTableView{

	public static final ServiceKey SERVICE_KEY = ServiceKey.forName("UpdateRecords");
	
	@Override
	public UserService<TableViewEntitySelection> createUserService() {
		// TODO Auto-generated method stub
		return new LaunchUpdateWorkflowService();
	}

	@Override
	public void declareWebComponent(WebComponentDeclarationContext context) {
		// TODO Auto-generated method stub
		context.setAvailableAsToolbarAction(true);
		context.setAvailableAsWorkflowUserTask(true);
	}

	@Override
	public void defineActivation(ActivationContextOnTableView context) {
		// TODO Auto-generated method stub
		context.includeSchemaNodesMatching(Paths._Root_Locomotive.getPathInSchema());
	}

	@Override
	public void defineProperties(UserServicePropertiesDefinitionContext aContext) {
		// TODO Auto-generated method stub
		aContext.setLabel("Update Loco record");
		aContext.setDescription("Update Loco record");
	}

	@Override
	public ServiceKey getServiceKey() {
		// TODO Auto-generated method stub
		return SERVICE_KEY;
	}

}
