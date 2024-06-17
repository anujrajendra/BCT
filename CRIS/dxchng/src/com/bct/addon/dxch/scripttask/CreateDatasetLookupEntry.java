package com.bct.addon.dxch.scripttask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.bct.addon.dxch.path.AlteryxMangPath;
import com.bct.addon.dxch.path.DXchngPath;
import com.bct.addon.dxch.procedure.CreateDatasetLookupEntryProcedure;
import com.bct.addon.dxch.procedure.CreateDatasourceTableEntryProcedure;
import com.bct.scad.mdm.common.utilities.RepositoryUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.onwbp.adaptation.XPathExpressionHelper;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.ProcedureResult;
import com.orchestranetworks.service.ProgrammaticService;

import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class CreateDatasetLookupEntry extends ScriptTaskBean {

	private String Altx_dataspace;
	private String Altx_dataset;
	private String Altx_datasettable;
	private String ip_dsname;
	private String WFID;

	private String Altx_datasetrecord;

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

	public String getAltx_datasetrecord() {
		return Altx_datasetrecord;
	}

	public void setAltx_datasetrecord(String altx_datasetrecord) {
		Altx_datasetrecord = altx_datasetrecord;
	}

	public String getIp_dsname() {
		return ip_dsname;
	}

	public void setIp_dsname(String ip_dsname) {
		this.ip_dsname = ip_dsname;
	}
	
	

	public String getWFID() {
		return WFID;
	}

	public void setWFID(String wFID) {
		WFID = wFID;
	}

	@Override
	public void executeScript(ScriptTaskBeanContext context) throws OperationException {
		// TODO Auto-generated method stub
		Repository repository = context.getRepository();
		final AdaptationHome dataSpaceName = RepositoryUtils.toDataSpace(repository, this.Altx_dataspace);
		final Adaptation dataSetName = RepositoryUtils.toDataSet(dataSpaceName, this.Altx_dataset);
		final AdaptationTable tbconfig = RepositoryUtils.toTable(dataSetName, this.Altx_datasettable);

		CreateDatasetLookupEntryProcedure wm = new CreateDatasetLookupEntryProcedure(tbconfig, this.ip_dsname,WFID);
		ProgrammaticService programmaticService = ProgrammaticService.createForSession(context.getSession(),
				dataSpaceName);
		ProcedureResult procresult = programmaticService.execute(wm);

		if (procresult.hasFailed()) {
			LoggingCategory.getKernel().debug("Procedure Execution Failed");
		}

		Integer datasetid = (Integer) wm.createdRecord.get_int(AlteryxMangPath._DatasetsLookup._DatasetId);

		StringBuffer predicate = new StringBuffer();
		predicate.append(AlteryxMangPath._DatasetsLookup._DatasetId.format()).append("=")
				.append(XPathExpressionHelper.encodeLiteralStringWithDelimiters(datasetid.toString()));

		RequestResult requestResult = tbconfig.createRequestResult(predicate.toString());

		Adaptation Record = null;

		if (requestResult.isSizeGreaterOrEqual(1)) { // Update Current Record
			Record = requestResult.nextAdaptation();
			Altx_datasetrecord = "/root/DatasetsLookup[./datasetId=" + datasetid.toString() + "]";

		}

	}
}
