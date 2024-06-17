package com.bct.addon.dxch.scripttask;

import com.bct.addon.dxch.procedure.ImportDataProcedure;
import com.bct.scad.mdm.common.utilities.RepositoryUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationTable;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.ProcedureResult;
import com.orchestranetworks.service.ProgrammaticService;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class ImportData extends ScriptTaskBean {
	
	private String DB_dataspace;
	private String DB_dataset;
	private String DB_table;
	private String ip_dsname;
	private String record;

	public String getDB_dataspace() {
		return DB_dataspace;
	}

	public void setDB_dataspace(String dB_dataspace) {
		DB_dataspace = dB_dataspace;
	}

	public String getDB_dataset() {
		return DB_dataset;
	}

	public void setDB_dataset(String dB_dataset) {
		DB_dataset = dB_dataset;
	}

	public String getDB_table() {
		return DB_table;
	}

	public void setDB_table(String dB_table) {
		DB_table = dB_table;
	}

	public String getIp_dsname() {
		return ip_dsname;
	}

	public void setIp_dsname(String ip_dsname) {
		this.ip_dsname = ip_dsname;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	@Override
	public void executeScript(ScriptTaskBeanContext context) throws OperationException {
		// TODO Auto-generated method stub
		
		Repository repository = context.getRepository();
		final AdaptationHome dataSpaceName = RepositoryUtils.toDataSpace(repository, this.DB_dataspace);
		final Adaptation dataSetName = RepositoryUtils.toDataSet(dataSpaceName, this.DB_dataset);
		final AdaptationTable tbconfig = RepositoryUtils.toTable(dataSetName, this.DB_table);
		
		String predicate = this.record.substring(this.record.indexOf("[") + 1, this.record.indexOf("]"));
		
		ImportDataProcedure wm = new ImportDataProcedure(tbconfig, predicate);
		ProgrammaticService programmaticService = ProgrammaticService.createForSession(context.getSession(),
				dataSpaceName);
		ProcedureResult procresult = programmaticService.execute(wm);

		if (procresult.hasFailed()) {
			LoggingCategory.getKernel().debug("Procedure Execution Failed");
		}
		
	}

}
