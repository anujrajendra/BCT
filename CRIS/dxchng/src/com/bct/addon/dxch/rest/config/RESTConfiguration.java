package com.bct.addon.dxch.rest.config;

import javax.ws.rs.*;

import com.bct.addon.dxch.rest.controller.*;
import com.orchestranetworks.rest.*;
import com.orchestranetworks.rest.annotation.*;

/**
 *
 * @author mostafashokiel
 */

@ApplicationPath(RESTApplicationAbstract.REST_DEFAULT_APPLICATION_PATH)
@Documentation("REST Application for Person Module")
@OpenApiApplicationPath(value = "/person-open-api/")
public final class RESTConfiguration extends RESTApplicationAbstract {

	public RESTConfiguration() {
		super((cfg) -> cfg.addPackages(LaborForceController.class.getPackage()));
	}
}
