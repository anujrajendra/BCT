package com.bct.complaintmodule.scripttask;

import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.ValueContextForUpdate;
import com.orchestranetworks.workflow.MailSpec;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class ComplaintEntryScriptTask extends ScriptTaskBean{

	private String referenceDataspace;
	private String dataspace;
	private String dataset;
	private String table;
	private String record;
	@Override
	public void executeScript(ScriptTaskBeanContext arg0) throws OperationException {
		// TODO Auto-generated method stub
		MailSpec mailSpec = arg0.createMailSpec();
		mailSpec.setSubject("Hello world");
		//mailSpec.sendMail("");
		//arg0
	}
	

}
