package com.cris.custom.indicator.module;

import com.cris.custom.indicator.function.ValidationMessageCustomIndicator;
import com.cris.custom.indicator.function.field.FieldValidationMessageCount;
import com.cris.custom.indicator.logger.ModuleLogger;
import com.orchestranetworks.addon.dpra.function.FunctionDefinitionRegistry;
import com.orchestranetworks.module.ModuleContextOnRepositoryStartup;
import com.orchestranetworks.module.ModuleRegistrationServlet;
import com.orchestranetworks.service.OperationException;

public class RegistrationServlet extends ModuleRegistrationServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void handleRepositoryStartup(ModuleContextOnRepositoryStartup aContext) throws OperationException {

		aContext.getLoggingCategory().info("custom-indicator Module");
		super.handleRepositoryStartup(aContext);

		FunctionDefinitionRegistry.register(ValidationMessageCustomIndicator.DEFINITION,
				ValidationMessageCustomIndicator.CHART_CONFIG);
		
		FunctionDefinitionRegistry.register(FieldValidationMessageCount.DEFINITION,
				FieldValidationMessageCount.CHART_CONFIG);

		// To get the independent logger class for this module.
		// It will help in maintaining the separate log file for the project module.
		// Here we are creating a static object for the logging object to be used
		// through out the project.
		ModuleLogger.setModuleLogger(aContext.getLoggingCategory());
	}
}