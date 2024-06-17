package com.bct.addon.dxch;

import javax.servlet.annotation.*;

import com.bct.addon.dxch.rest.config.*;
import com.orchestranetworks.module.*;

@WebListener
public final class RegistrationModule extends ModuleRegistrationListener {

	@Override
	public void handleContextInitialized(final ModuleInitializedContext aContext) {
		// Registers dynamically a REST Toolkit application.
		aContext.registerRESTApplication(RESTConfiguration.class);
	}
}