package com.cris.feedback.permission;

import com.cris.feedback.constant.Constants;
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

public class FeedbackAccessRule implements AccessRule {

	@Override
	public AccessPermission getPermission(Adaptation adaptation, Session session, SchemaNode node) {

//		String userId = session.getUserReference().getUserId();
//		String createdBy = (String) adaptation.get(FeedbackPath._Root_Feedback._Root_Feedback_UserId);
//
//		Repository repository = Repository.getDefault();
//		DirectoryHandler directoryHandler = DirectoryHandler.getInstance(repository);
//
//		boolean isAdminUser = directoryHandler.isUserInRole(UserReference.forUser(userId), Role.ADMINISTRATOR);
//
//		if (isAdminUser) {
//			return AccessPermission.getReadWrite();
//		}
//
//		if (!userId.equals(createdBy)) {
//			return AccessPermission.getHidden();
//		}

		Repository repository = Repository.getDefault();
		DirectoryHandler directoryHandler = DirectoryHandler.getInstance(repository);

		String userId = session.getUserReference().getUserId();
		UserReference userReference = UserReference.forUser(userId);

		boolean isAdminUser = directoryHandler.isUserInRole(userReference, Role.ADMINISTRATOR);

		if (isAdminUser) {
			return AccessPermission.getReadWrite();
		}

		String ipasId = (String) adaptation.get(Path.parse("./UserId"));

		AdaptationTable businessUsersTable = Constants.DATASET_USERREGISTRATION
				.getTable((Path.parse("/root/Business_Users_Registration_Details")));
		Adaptation businessUserRecord = businessUsersTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(ipasId));

		if (businessUserRecord == null) {
			return AccessPermission.getHidden();

		} else {

			String businessMdmUserId = (String) businessUserRecord.get(Path.parse("./Mdm_User_Id"));

			if (businessMdmUserId != null && userId.equalsIgnoreCase(businessMdmUserId))
				return AccessPermission.getReadWrite();
			else
				return AccessPermission.getHidden();
		}
	}

}
