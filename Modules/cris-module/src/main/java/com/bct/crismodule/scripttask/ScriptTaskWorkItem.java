package com.bct.crismodule.scripttask;

import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class ScriptTaskWorkItem extends ScriptTaskBean{

	private String dataspace;
	private String dataset;
	private String table;
	private String record;
	
	@Override
	public void executeScript(ScriptTaskBeanContext arg0) throws OperationException {
		// TODO Auto-generated method stub
		
	}

	public String getdataspace() {
		return dataspace;
	}

	public void setdataspace(String dataspace) {
		this.dataspace = dataspace;
	}

	public String getdataset() {
		return dataset;
	}

	public void setdataset(String dataset) {
		this.dataset = dataset;
	}

	public String gettable() {
		return table;
	}

	public void settable(String table) {
		this.table = table;
	}

	public String getrecord() {
		return record;
	}

	public void setrecord(String record) {
		this.record = record;
	}
	
	

}
