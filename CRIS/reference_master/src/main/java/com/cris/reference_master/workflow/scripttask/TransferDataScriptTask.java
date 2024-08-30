package com.cris.reference_master.workflow.scripttask;

import com.orchestranetworks.addon.dint.DataIntegrationException;
import com.orchestranetworks.addon.dint.DataIntegrationExecutionResults;
import com.orchestranetworks.addon.dint.DataIntegrationExecutor;
import com.orchestranetworks.addon.dint.template.EBXTransferTemplateSpec;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class TransferDataScriptTask extends ScriptTaskBean {

	private String templateId;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Override
	public void executeScript(ScriptTaskBeanContext context) throws OperationException {
		// TODO Auto-generated method stub

		Session session = context.getSession();

		EBXTransferTemplateSpec ebxTransferTemplateSpec = new EBXTransferTemplateSpec(templateId, session);

		try {
			DataIntegrationExecutionResults dataTransferResults = DataIntegrationExecutor.getInstance()
					.execute(ebxTransferTemplateSpec);
		} catch (DataIntegrationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
