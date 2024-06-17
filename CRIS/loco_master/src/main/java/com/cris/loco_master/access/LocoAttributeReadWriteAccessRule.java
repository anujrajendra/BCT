package com.cris.loco_master.access;

import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.service.AccessPermission;
import com.orchestranetworks.service.AccessRule;
import com.orchestranetworks.service.Session;

public class LocoAttributeReadWriteAccessRule implements AccessRule {

	@Override
	public AccessPermission getPermission(Adaptation adaptation, Session session, SchemaNode node) {

		if (adaptation.isSchemaInstance()) {
			return AccessPermission.getReadWrite();
		}

		if (session.isInWorkflowInteraction(true)) {
			return AccessPermission.getReadWrite();

		}

		return AccessPermission.getReadWrite();
	}
}