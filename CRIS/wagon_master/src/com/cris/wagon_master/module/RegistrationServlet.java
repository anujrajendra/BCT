package com.cris.wagon_master.module;

import com.orchestranetworks.module.ModuleContextOnRepositoryStartup;
import com.orchestranetworks.module.ModuleRegistrationServlet;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.OperationException;

public class RegistrationServlet extends ModuleRegistrationServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void handleRepositoryStartup(ModuleContextOnRepositoryStartup aContext) throws OperationException {

		LoggingCategory moduleLogger = aContext.getLoggingCategory();
		moduleLogger.info("Wagon Master Module Registered");

		Logger.logger = moduleLogger;
	}
}