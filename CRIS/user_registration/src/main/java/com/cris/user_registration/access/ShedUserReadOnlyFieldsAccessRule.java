package com.cris.user_registration.access;

import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.service.AccessPermission;
import com.orchestranetworks.service.AccessRule;
import com.orchestranetworks.service.Role;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.service.directory.DirectoryHandler;

public class ShedUserReadOnlyFieldsAccessRule implements AccessRule {

	@Override
	public AccessPermission getPermission(Adaptation adaptation, Session session, SchemaNode arg2) {

		Repository repository = Repository.getDefault();
		DirectoryHandler directoryHandler = DirectoryHandler.getInstance(repository);

		String userId = session.getUserReference().getUserId();
		UserReference userReference = UserReference.forUser(userId);

		boolean isShedUser = directoryHandler.isUserInRole(userReference, Role.forSpecificRole("Shed_DAA"))
				|| directoryHandler.isUserInRole(userReference, Role.forSpecificRole("Shed_DS"));

		if (isShedUser) {
			return AccessPermission.getReadOnly();
		}

		return AccessPermission.getReadWrite();

	}
}
