package com.cris.user_registration.trigger;

import com.cris.user_registration.Paths;
import com.cris.user_registration.constant.Constants;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.trigger.NewTransientOccurrenceContext;
import com.orchestranetworks.schema.trigger.TableTrigger;
import com.orchestranetworks.schema.trigger.TriggerSetupContext;
import com.orchestranetworks.service.Role;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.service.ValueContextForUpdate;
import com.orchestranetworks.service.directory.DirectoryHandler;

public class NewUserRegistrationTrigger extends TableTrigger {

	@Override
	public void handleNewContext(NewTransientOccurrenceContext aContext) {
		// TODO Auto-generated method stub
		super.handleNewContext(aContext);

		Repository repository = Repository.getDefault();
		DirectoryHandler directoryHandler = DirectoryHandler.getInstance(repository);
		ValueContextForUpdate vcfu = aContext.getOccurrenceContextForUpdate();

		String userId = aContext.getSession().getUserReference().getUserId();
		UserReference userReference = UserReference.forUser(userId);

		// boolean isAdminUser = directoryHandler.isUserInRole(userReference,
		// Role.ADMINISTRATOR);
		boolean isShedUser = directoryHandler.isUserInRole(userReference, Role.forSpecificRole("Shed_DAA"))
				|| directoryHandler.isUserInRole(userReference, Role.forSpecificRole("Shed_DS"));

		if (isShedUser) {

			AdaptationTable systemUsersTable = Constants.DATASET_USERREGISTRATION
					.getTable((Path.parse("/root/User_Registration_Details")));
			Adaptation systemUserRecord = systemUsersTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(userId));

			if (systemUserRecord != null) {
				vcfu.setValueEnablingPrivilegeForNode(systemUserRecord.getString(Path.parse("./Shed")),
						Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Shed);
				vcfu.setValueEnablingPrivilegeForNode(systemUserRecord.getString(Path.parse("./Divison")),
						Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Divison);
				vcfu.setValueEnablingPrivilegeForNode(systemUserRecord.getString(Path.parse("./Zone")),
						Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Zone);

			}
		}
	}

	@Override
	public void setup(TriggerSetupContext arg0) {
		// TODO Auto-generated method stub

	}

}
