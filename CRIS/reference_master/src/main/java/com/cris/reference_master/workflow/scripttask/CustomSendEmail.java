package com.cris.reference_master.workflow.scripttask;

import com.cris.reference_master.utilities.EmailUtils;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class CustomSendEmail extends ScriptTaskBean {

	private String emailTemplateId;

	public String getEmailTemplateId() {
		return emailTemplateId;
	}

	public void setEmailTemplateId(String emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	@Override
	public void executeScript(ScriptTaskBeanContext arg0) throws OperationException {
		// TODO Auto-generated method stub

		EmailUtils emailUtility = new EmailUtils(emailTemplateId);
			try {
				emailUtility.sendMessage();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		}
		}
}