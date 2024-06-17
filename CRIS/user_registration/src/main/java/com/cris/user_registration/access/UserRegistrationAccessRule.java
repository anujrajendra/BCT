package com.cris.user_registration.access;

import com.cris.user_registration.constant.Constants;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.service.AccessPermission;
import com.orchestranetworks.service.AccessRule;
import com.orchestranetworks.service.Role;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.service.directory.DirectoryHandler;

public class UserRegistrationAccessRule implements AccessRule {

	@Override
	public AccessPermission getPermission(Adaptation adaptation, Session session, SchemaNode arg2) {
		// TODO Auto-generated method stub

		Repository repository = Repository.getDefault();
		DirectoryHandler directoryHandler = DirectoryHandler.getInstance(repository);

		String userId = session.getUserReference().getUserId();
		UserReference userReference = UserReference.forUser(userId);

		boolean isAdminUser = directoryHandler.isUserInRole(userReference, Role.ADMINISTRATOR);

		if (isAdminUser || userId.equalsIgnoreCase("registeruser")) {
			return AccessPermission.getReadWrite();
		}

		String shedCode = (String) adaptation.get(Path.parse("./Shed"));

		AdaptationTable systemUsersTable = Constants.DATASET_USERREGISTRATION
				.getTable((Path.parse("/root/User_Registration_Details")));
		Adaptation systemUserRecord = systemUsersTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(userId));

		if (systemUserRecord == null || shedCode == null) {
			return AccessPermission.getHidden();

		} else {

			String systemUserShedCode = (String) systemUserRecord.get(Path.parse("./Shed"));

			if (systemUserShedCode != null && shedCode.equalsIgnoreCase(systemUserShedCode))
				return AccessPermission.getReadWrite();
			else
				return AccessPermission.getHidden();
		}

//		if (session.isInWorkflowInteraction(false)) {
//			return AccessPermission.getReadWrite();
//		} else {
//			return AccessPermission.getReadOnly();
//		}
	}
}