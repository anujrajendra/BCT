package com.bct.addon.dxch.rest.controller;

import java.time.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.bct.scad.mdm.common.rest.json.response.*;
import com.bct.scad.mdm.common.utilities.RepositoryUtils;
import com.bct.addon.dxch.constant.*;
import com.bct.addon.dxch.path.AlteryxMangPath;
import com.bct.addon.dxch.path.CategoryConfigPath;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.onwbp.adaptation.XPathExpressionHelper;
import com.onwbp.base.text.*;
import com.orchestranetworks.instance.*;
import com.orchestranetworks.rest.annotation.Documentation;
import com.orchestranetworks.rest.inject.*;
import com.orchestranetworks.service.*;
import com.orchestranetworks.workflow.*;

/**
 *
 * @author sushant
 */

@Path("/common/v1")
@Documentation("Workflow Trigger after Batch Completion Common service")
public class SVAutomationTriggerController {

	@Context
	private SessionContext sessionContext;

	/**
	 * Batch Init.
	 */
	@POST
	@Path("/initparam")
	@Documentation("Initialize the Batch")
	public Response handleInitBatch(@QueryParam("category") final String category,
			@QueryParam("year") final String year, @QueryParam("period") final String period,
			@QueryParam("dsname") final String dsname, @QueryParam("wfid") final String wfid) {
		Repository repository = Repository.getDefault();
		Session session = this.sessionContext.getSession();
		try {

			if (!(dsname == null)) {

				WorkflowEngine wfEngine = WorkflowEngine.getFromRepository(repository, session);

				ProcessLauncher launcher = null;
				launcher = wfEngine.getProcessLauncher(PublishedProcessKey.forName(WfConst.BC_WF));
				String full_dsname = null;
				if (period.equals("A") || period.length() == 0) {
					full_dsname = category + "_" + year + "_" + dsname;
				} else {
					full_dsname = category + "_" + year + "_" + period + "_" + dsname;
				}
				launcher.setLabel(
						UserMessage.createInfo(full_dsname + " - Imputation Workflow - " + LocalDateTime.now()));

				launcher.setInputParameter("ip_dsname", full_dsname);

				launcher.setInputParameter("parent_dsname", category);

				launcher.setInputParameter("childdataSpace_name", full_dsname + "_TEMP");

				launcher.setInputParameter("WFID", wfid);

				launcher.launchProcess();

			} else {
				return Response.status(Response.Status.OK).entity(new ErrorResponse(ResponseCodes.DATASET_NOT_PROVIDED,
						ResponseCodes.DATASET_NOT_PROVIDED_MESSAGE)).build();
			}

		} catch (

		Exception ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new ErrorResponse(ResponseCodes.UN_EXPECTED_ERROR_CODE, ex.getMessage())).build();
		}

		return Response.status(Response.Status.CREATED)
				.entity(new SuccessResponse(ResponseCodes.DATASPACE_WF_SUCCESS_CODE,
						ResponseCodes.DATASPACE_WF_SUCCESS_MESSAGE))
				.build();
	}
}