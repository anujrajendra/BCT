package com.cris.coach_master;

import com.orchestranetworks.module.ModuleContextOnRepositoryStartup;
import com.orchestranetworks.module.ModuleRegistrationServlet;
import com.orchestranetworks.service.OperationException;

public class RegistrationServlet extends ModuleRegistrationServlet{

	private static final long serialVersionUID = 1L;
	
	public void handleRepositoryStartup(ModuleContextOnRepositoryStartup aContext)
	 throws OperationException {
		aContext.getLoggingCategory().info("coach_master");
	}

}
