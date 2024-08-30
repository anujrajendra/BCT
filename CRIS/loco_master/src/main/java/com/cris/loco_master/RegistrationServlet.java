package com.cris.loco_master;

import com.cris.loco_master.logger.ModuleLogger;
import com.orchestranetworks.module.ModuleContextOnRepositoryStartup;
import com.orchestranetworks.module.ModuleRegistrationServlet;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.OperationException;

public class RegistrationServlet extends ModuleRegistrationServlet {
	private static final long serialVersionUID = 1L;

	public void handleRepositoryStartup(ModuleContextOnRepositoryStartup aContext) throws OperationException {
		LoggingCategory moduleLogger = aContext.getLoggingCategory();
		moduleLogger.info("loco_master");

		ModuleLogger.logger = moduleLogger;

	}
}