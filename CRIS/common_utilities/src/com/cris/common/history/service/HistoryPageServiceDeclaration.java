package com.cris.common.history.service;

import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.declaration.ActivationContextOnTableView;
import com.orchestranetworks.userservice.declaration.UserServiceDeclaration;
import com.orchestranetworks.userservice.declaration.UserServicePropertiesDefinitionContext;
import com.orchestranetworks.userservice.declaration.WebComponentDeclarationContext;

public class HistoryPageServiceDeclaration implements UserServiceDeclaration.OnTableView {

	public static final ServiceKey SERVICE_KEY = ServiceKey.forName("HistoryPage");

	@Override
	public ServiceKey getServiceKey() {
		return SERVICE_KEY;
	}

	@Override
	public UserService<TableViewEntitySelection> createUserService() {
		return new HistoryPageService();
	}

	@Override
	public void declareWebComponent(WebComponentDeclarationContext context) {
		context.setAvailableAsPerspectiveAction(true);
		context.addInputParameter("viewName", UserMessage.createInfo("View"),
				UserMessage.createInfo("Name of the view to be applied on history page"));
	}

	@Override
	public void defineActivation(ActivationContextOnTableView context) {

	}

	@Override
	public void defineProperties(UserServicePropertiesDefinitionContext context) {
		context.setLabel("Table History Page");
	}
}