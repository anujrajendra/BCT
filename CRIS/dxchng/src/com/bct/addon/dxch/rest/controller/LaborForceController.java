package com.bct.addon.dxch.rest.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.orchestranetworks.rest.annotation.*;
import com.orchestranetworks.rest.inject.*;

/**
 *
 * @author mostafashokiel
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/lfr/v1")
@Documentation("Labor Force Register service")
@OpenApiResource
public class LaborForceController {

	@Context
	private SessionContext sessionContext;

	/**
	 * Updates one Record.
	 */
	@PUT
	@Path("/record")
	@Documentation("Update one Record for Labor Foece Register" + "Responses:-"
			+ "1 --> 201 : All tables has been created or updated successfully"
			+ "2 --> 202 : Some Tables has been created or updated successfully and others failed"
			+ "3 --> 500 : Unexpected Error")

	public Response createOrUpdateOneRecord() {
		return null;
	}

}