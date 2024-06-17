package com.cris.common.iframe.integration;

import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.declaration.ActivationContextOnTableView;
import com.orchestranetworks.userservice.declaration.UserServiceDeclaration;
import com.orchestranetworks.userservice.declaration.UserServicePropertiesDefinitionContext;
import com.orchestranetworks.userservice.declaration.WebComponentDeclarationContext;

public class WebPageIntegrationServiceDeclaration implements UserServiceDeclaration.OnTableView {
	
	public static final ServiceKey SERVICE_KEY = ServiceKey.forName("WebPage_Integration");
	
	@Override
	public ServiceKey getServiceKey() {
		return SERVICE_KEY;
	}
	
	@Override
	public UserService<TableViewEntitySelection> createUserService() {
		return new WebPageIntegrationService();
	}

	@Override
	public void declareWebComponent(WebComponentDeclarationContext context) {
		context.setAvailableAsPerspectiveAction(true);
		context.addInputParameter("webPageURL", 
					UserMessage.createInfo("WebPage URL"),
					UserMessage.createInfo("URL for the page to be loaded"));
	}
	
	@Override
	public void defineActivation(ActivationContextOnTableView context) {
		
	}

	@Override
	public void defineProperties(UserServicePropertiesDefinitionContext context) {
		context.setLabel("Web Page Integration");
	}
}