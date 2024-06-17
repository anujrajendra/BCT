package com.cris.user_registration.procedure;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;
import com.orchestranetworks.service.ValueContextForUpdate;
import com.orchestranetworks.service.directory.EncryptPassword;
import com.orchestranetworks.workflow.ProcessInstance;

public class UserRegistrationProcedure implements Procedure {

	private AdaptationTable directoryTable;
	private ProcessInstance wfInstance;
	private String password;
	private Adaptation userAdaptation;
	private AdaptationTable roletable;

	public UserRegistrationProcedure(AdaptationTable directoryTable, ProcessInstance wfInstance, String password,
			Adaptation userAdaptation, AdaptationTable roletable) {
		super();
		this.directoryTable = directoryTable;
		this.wfInstance = wfInstance;
		this.password = password;
		this.userAdaptation = userAdaptation;
		this.roletable = roletable;
	}

	@Override
	public void execute(ProcedureContext context) throws Exception {

		ValueContextForUpdate vcfuUser = null;
		ValueContextForUpdate vcfuRole = null;
		vcfuUser = context.getContextForNewOccurrence(this.directoryTable);

		this.registerUserRecord(vcfuUser);
		Adaptation newUserRecord = context.doCreateOccurrence(vcfuUser, this.directoryTable);

		String roles = (String) this.userAdaptation.get(Path.parse("/Role_Type"));
		if (roles != null) {
			vcfuRole = context.getContextForNewOccurrence(this.roletable);
			vcfuRole.setValueEnablingPrivilegeForNode(this.userAdaptation.get(Path.parse("./User_Id")),
					Path.parse("/user"));
			vcfuRole.setValueEnablingPrivilegeForNode(roles, Path.parse("/role"));

			Adaptation newUserRoleRecord = context.doCreateOccurrence(vcfuRole, this.roletable);
		}

	}

	void registerUserRecord(final ValueContextForUpdate vcfuUser) {

		vcfuUser.setValueEnablingPrivilegeForNode(this.userAdaptation.get(Path.parse("./User_Id")),
				Path.parse("./login"));

		vcfuUser.setValueEnablingPrivilegeForNode(this.userAdaptation.get(Path.parse("./Name")),
				Path.parse("./firstName"));

		vcfuUser.setValueEnablingPrivilegeForNode(this.userAdaptation.get(Path.parse("./Last_Name")),
				Path.parse("./lastName"));

		String encpassword = EncryptPassword.encrypt((String) this.userAdaptation.get(Path.parse("./User_Id")),
				this.password);

		vcfuUser.setValueEnablingPrivilegeForNode(encpassword, Path.parse("./password"));
	}

}
