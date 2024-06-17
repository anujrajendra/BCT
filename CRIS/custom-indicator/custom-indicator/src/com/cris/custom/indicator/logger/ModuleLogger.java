package com.cris.custom.indicator.logger;

import com.orchestranetworks.service.LoggingCategory;

public class ModuleLogger {
	private static LoggingCategory moduleLogger;

	/**
	 * @return Returns the object for logging to the module log file category.
	 */
	public static LoggingCategory getModuleLogger() {
		return moduleLogger;
	}

	/**
	 * Set the object for logging to the module log file category.
	 * @param logger Reference to the logger object of the module.
	 * 
	 */
	public static void setModuleLogger(LoggingCategory logger) {
		moduleLogger = logger;
	}
}