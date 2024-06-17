package com.bct.addon.dxch.scripttask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.bct.addon.dxch.path.DXchngPath;
import com.bct.addon.dxch.procedure.CreateDataSetProcedure;

import com.bct.addon.dxch.procedure.CreateDatasourceTableEntryProcedure;
import com.bct.addon.dxch.procedure.DeleteDataSetProcedure;
import com.bct.scad.mdm.common.utilities.RepositoryUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.onwbp.adaptation.XPathExpressionHelper;
import com.orchestranetworks.instance.HomeCreationSpec;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.ProcedureResult;
import com.orchestranetworks.service.Profile;
import com.orchestranetworks.service.ProgrammaticService;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class CreateDataSpacenDataset extends ScriptTaskBean {

	private String parent_dsname;
	private String childdataSpace_name;
	private String ip_dsname;
	private String xsdpath;
	private String xsdmodule;

	public String getXsdpath() {
		return xsdpath;
	}

	public void setXsdpath(String xsdpath) {
		this.xsdpath = xsdpath;
	}

	public String getXsdmodule() {
		return xsdmodule;
	}

	public void setXsdmodule(String xsdmodule) {
		this.xsdmodule = xsdmodule;
	}

	public String getParent_dsname() {
		return parent_dsname;
	}

	public void setParent_dsname(String parent_dsname) {
		this.parent_dsname = parent_dsname;
	}

	public String getChilddataSpace_name() {
		return childdataSpace_name;
	}

	public void setChilddataSpace_name(String childdataSpace_name) {
		this.childdataSpace_name = childdataSpace_name;
	}

	public String getIp_dsname() {
		return ip_dsname;
	}

	public void setIp_dsname(String ip_dsname) {
		this.ip_dsname = ip_dsname;
	}

	@Override
	public void executeScript(ScriptTaskBeanContext context) throws OperationException {
		// TODO Auto-generated method stub
		Repository repository = context.getRepository();

		Session session = context.getSession();

		AdaptationHome Dataspace = repository.lookupHome(HomeKey.forBranchName(this.ip_dsname));

		if (Dataspace == null) { // Create Datapsace

			AdaptationHome parentHome = repository.lookupHome(HomeKey.forBranchName(this.parent_dsname));
			if (parentHome == null) { // Create Datapsace

				AdaptationHome Reference = repository.lookupHome(HomeKey.forBranchName("Reference"));
				HomeCreationSpec personChildSpec = new HomeCreationSpec();
				personChildSpec.setParent(Reference);
				personChildSpec.setOwner(Profile.EVERYONE);
				personChildSpec.setKey(HomeKey.forBranchName(this.parent_dsname));
				parentHome = repository.createHome(personChildSpec, context.getSession());

			}
			HomeCreationSpec personChildSpec = new HomeCreationSpec();
			personChildSpec.setParent(parentHome);
			personChildSpec.setOwner(Profile.EVERYONE);
			personChildSpec.setKey(HomeKey.forBranchName(this.ip_dsname));
			Dataspace = repository.createHome(personChildSpec, context.getSession());

		} else {
			AdaptationHome childHome = repository.lookupHome(HomeKey.forBranchName(this.childdataSpace_name));

			if (!(childHome == null)) {
				if (childHome.isOpen()) {
					DeleteDataSetProcedure wm1 = new DeleteDataSetProcedure(ip_dsname);
					ProgrammaticService programmaticService1 = ProgrammaticService
							.createForSession(context.getSession(), childHome);
					ProcedureResult procresult = programmaticService1.execute(wm1);

					if (procresult.hasFailed()) {
						LoggingCategory.getKernel().debug("Procedure Execution Failed");
					}
					repository.closeHome(childHome, context.getSession());
				} else {

					repository.reopenHome(childHome, context.getSession());
					DeleteDataSetProcedure wm1 = new DeleteDataSetProcedure(ip_dsname);
					ProgrammaticService programmaticService1 = ProgrammaticService
							.createForSession(context.getSession(), childHome);
					ProcedureResult procresult = programmaticService1.execute(wm1);

					if (procresult.hasFailed()) {
						LoggingCategory.getKernel().debug("Procedure Execution Failed");
					}
					repository.closeHome(childHome, context.getSession());
					repository.deleteHome(childHome, context.getSession());
				}
			}

			DeleteDataSetProcedure wm2 = new DeleteDataSetProcedure(ip_dsname);
			ProgrammaticService programmaticService2 = ProgrammaticService.createForSession(context.getSession(),
					Dataspace);
			ProcedureResult procresult2 = programmaticService2.execute(wm2);

			if (procresult2.hasFailed()) {
				LoggingCategory.getKernel().debug("Procedure Execution Failed");
			}

			repository.closeHome(Dataspace, context.getSession());
			repository.deleteHome(Dataspace, context.getSession());
			AdaptationHome personHome = repository.lookupHome(HomeKey.forBranchName(this.parent_dsname));
			HomeCreationSpec personChildSpec = new HomeCreationSpec();
			personChildSpec.setParent(personHome);
			personChildSpec.setOwner(Profile.EVERYONE);
			personChildSpec.setKey(HomeKey.forBranchName(this.ip_dsname));
			Dataspace = repository.createHome(personChildSpec, context.getSession());
		}

		final AdaptationHome dataSpaceName = RepositoryUtils.toDataSpace(repository, ip_dsname);

		CreateDataSetProcedure wm1 = new CreateDataSetProcedure(ip_dsname, xsdmodule, repository, session);
		ProgrammaticService programmaticService1 = ProgrammaticService.createForSession(context.getSession(),
				dataSpaceName);
		ProcedureResult procresult1 = programmaticService1.execute(wm1);

		if (procresult1.hasFailed()) {
			LoggingCategory.getKernel().debug("Procedure Execution Failed");
		}

	}

}
