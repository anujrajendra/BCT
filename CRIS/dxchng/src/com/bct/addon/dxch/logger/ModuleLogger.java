package com.bct.addon.dxch.logger;

import com.orchestranetworks.service.*;

/**
 * @author Tibco Class to maintain the static handler to the Logging object for
 *         the module. This moduleLogger variable is initialized at the time of
 *         registration through ModuleRegistrationServlet class.
 */
public class ModuleLogger {
	private static LoggingCategory moduleLogger;

	/**
	 * @return Returns the object for logging to the module log file category.
	 */
	public static LoggingCategory getModuleLogger() {
		return ModuleLogger.moduleLogger;
	}

	/**
	 * Set the object for logging to the module log file category.
	 *
	 * @param logger Reference to the logger object of the module.
	 *
	 */
	public static void setModuleLogger(final LoggingCategory logger) {
		ModuleLogger.moduleLogger = logger;
	}
}