package com.bct.addon.dxch.scripttask;

import com.bct.addon.dxch.procedure.DsStatusUpdateProcedure;
import com.bct.scad.mdm.common.utilities.*;

import com.onwbp.adaptation.*;
import com.orchestranetworks.instance.*;
import com.orchestranetworks.service.*;
import com.orchestranetworks.workflow.*;

/**
 *
 * @author Lalith
 */
public class DsStatusUpdateScript extends ScriptTaskBean {

	private String Altx_dataspace;
	private String Altx_dataset;
	private String Altx_datasettable;
	private String ipDataSet;
	private String dsStatus;

	

	public String getAltx_dataspace() {
		return Altx_dataspace;
	}

	public void setAltx_dataspace(String altx_dataspace) {
		Altx_dataspace = altx_dataspace;
	}

	public String getAltx_dataset() {
		return Altx_dataset;
	}

	public void setAltx_dataset(String altx_dataset) {
		Altx_dataset = altx_dataset;
	}

	public String getAltx_datasettable() {
		return Altx_datasettable;
	}

	public void setAltx_datasettable(String altx_datasettable) {
		Altx_datasettable = altx_datasettable;
	}

	public String getIpDataSet() {
		return this.ipDataSet;
	}

	public void setIpDataSet(final String ipDataSet) {
		this.ipDataSet = ipDataSet;
	}

	public String getDsStatus() {
		return this.dsStatus;
	}

	public void setDsStatus(final String dsStatus) {
		this.dsStatus = dsStatus;
	}

	@Override
	public void executeScript(final ScriptTaskBeanContext context) throws OperationException {
		// TODO auto generated : to check

		Repository repository = context.getRepository();
		final AdaptationHome dataSpaceName = RepositoryUtils.toDataSpace(repository, this.Altx_dataspace);
		final Adaptation dataSetName = RepositoryUtils.toDataSet(dataSpaceName, this.Altx_dataset);
		final AdaptationTable tbconfig = RepositoryUtils.toTable(dataSetName, this.Altx_datasettable);

		DsStatusUpdateProcedure wm = new DsStatusUpdateProcedure(tbconfig, this.ipDataSet, this.dsStatus);
		ProgrammaticService programmaticService = ProgrammaticService.createForSession(context.getSession(),
				dataSpaceName);
		ProcedureResult procresult = programmaticService.execute(wm);

		if (procresult.hasFailed()) {
			LoggingCategory.getKernel().debug("Procedure Execution Failed");
		}

	}

}
