package com.cris.user_registration;

import com.orchestranetworks.module.ModuleContextOnRepositoryStartup;
import com.orchestranetworks.module.ModuleRegistrationServlet;
import com.orchestranetworks.service.OperationException;

public class RegistrationServlet extends ModuleRegistrationServlet{

	private static final long serialVersionUID = 1L;
	
	public void handleRepositoryStartup(ModuleContextOnRepositoryStartup aContext)
	 throws OperationException {
		aContext.getLoggingCategory().info("user_registration");
	}

}
