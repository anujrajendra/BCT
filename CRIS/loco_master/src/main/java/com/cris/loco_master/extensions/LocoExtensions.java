package com.cris.loco_master.extensions;

import com.cris.loco_master.Paths;
import com.cris.loco_master.access.LocoAttributeHiddenAccessRule;
import com.cris.loco_master.access.LocoAttributeReadWriteAccessRule;
import com.cris.loco_master.access.LocoUpdateAccessRule;
import com.cris.loco_master.access.LocoUserAccessRule;
import com.cris.loco_master.service.spotfire.SpotfireDashboardServiceDeclaration;
import com.cris.loco_master.service.transferredlocos.TransferredLocosServiceDeclaration;
import com.cris.loco_master.service.workflow.condemn.LocoCondemnServiceDeclaration;
import com.cris.loco_master.service.workflow.transfer.LocoTransferServiceDeclaration;
import com.cris.loco_master.service.workflow.update.LocoUpdateDaaServiceDeclaration;
import com.cris.loco_master.service.workflow.update.LocoUpdateServiceDeclaration;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaExtensions;
import com.orchestranetworks.schema.SchemaExtensionsContext;
import com.orchestranetworks.service.AccessRule;
import com.orchestranetworks.service.Role;
import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.selection.TableViewEntitySelection;
import com.orchestranetworks.userservice.declaration.UserServiceDeclaration;
import com.orchestranetworks.userservice.permission.ServicePermissionRule;
import com.orchestranetworks.userservice.permission.ServicePermissionRuleContext;
import com.orchestranetworks.userservice.permission.UserServicePermission;

public class LocoExtensions implements SchemaExtensions {

	@Override
	public void defineExtensions(SchemaExtensionsContext context) {
		// TODO Auto-generated method stub

		context.registerUserService(new com.cris.common.history.service.HistoryPageServiceDeclaration());

		final UserServiceDeclaration.OnTableView userServiceDeclarationUpdate = new LocoUpdateServiceDeclaration();
		context.registerUserService(userServiceDeclarationUpdate);

		final UserServiceDeclaration.OnTableView userServiceDeclarationUpdateDaa = new LocoUpdateDaaServiceDeclaration();
		context.registerUserService(userServiceDeclarationUpdateDaa);

		context.setServicePermissionRuleOnNode(Paths._Root_Locomotive.getPathInSchema(),
				ServiceKey.forName("DaaUpdateRecords"), new ServicePermissionRule<TableViewEntitySelection>() {

					@Override
					public UserServicePermission getPermission(
							ServicePermissionRuleContext<TableViewEntitySelection> arg0) {
						// TODO Auto-generated method stub
						if (arg0.getSession().isUserInRole(Role.ADMINISTRATOR))
							return UserServicePermission.getDisabled();
						return UserServicePermission.getEnabled();
					}
				});
		final UserServiceDeclaration.OnTableView userServiceDeclarationTransfer = new LocoTransferServiceDeclaration();
		context.registerUserService(userServiceDeclarationTransfer);

		final UserServiceDeclaration.OnTableView userServiceDeclarationCondemn = new LocoCondemnServiceDeclaration();
		context.registerUserService(userServiceDeclarationCondemn);

		final UserServiceDeclaration.OnTableView userServiceDeclarationTransferredLocos = new TransferredLocosServiceDeclaration();
		context.registerUserService(userServiceDeclarationTransferredLocos);

		final UserServiceDeclaration.OnTableView spotfireDashboardiFrameServiceDeclaration = new SpotfireDashboardServiceDeclaration();
		context.registerUserService(spotfireDashboardiFrameServiceDeclaration);

		final UserServiceDeclaration.OnTableView spotfireDashboardServiceDeclaration = new com.cris.loco_master.service.spotfirescript.SpotfireDashboardServiceDeclaration();
		context.registerUserService(spotfireDashboardServiceDeclaration);

//		final UserServiceDeclaration.OnTableView locoDQuserServiceDeclaration =
//				new LocoDQServiceDeclaration();
//		context.registerUserService(locoDQuserServiceDeclaration);

		final Path path = Paths._Root_Locomotive.getPathInSchema();
		final AccessRule locomotiveUserAccessRule = new LocoUserAccessRule();

		context.setAccessRuleOnOccurrence(path, locomotiveUserAccessRule);

		final Path locoZonePath = Paths._Root_Locomotive.getPathInSchema()
				.add(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Zone);
		final Path locoDivisionPath = Paths._Root_Locomotive.getPathInSchema()
				.add(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Division);
		final Path locoShedPath = Paths._Root_Locomotive.getPathInSchema()
				.add(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Shed);

		final Path locoCondemnReasonPath = Paths._Root_Locomotive.getPathInSchema()
				.add(Paths._Root_Locomotive._Root_Locomotive_Loco_Condemnation_Reason);
		final Path locoCondemnDatePath = Paths._Root_Locomotive.getPathInSchema()
				.add(Paths._Root_Locomotive._Root_Locomotive_Loco_Condemn_Date);

//		final Path locoCondemnDatePath = Paths._Root_Locomotive.getPathInSchema()
//				.add(Paths._Root_Locomotive._Root_Locomotive_Loco_Condemn_Date);

		final AccessRule locomotiveUpdateAccessRule = new LocoUpdateAccessRule();
		// final AccessRule locomotiveCondemnAccessRule = new
		// LocomotiveCondemnAccessRule();
		// final AccessRule locomotiveAttributeReadOnlyAccessRule = new
		// LocomotiveAttributeReadOnlyAccessRule();
		final AccessRule locomotiveAttributeReadWriteAccessRule = new LocoAttributeReadWriteAccessRule();
		final AccessRule locomotiveHiddenAccessRule = new LocoAttributeHiddenAccessRule();

		context.setAccessRuleOnNode(locoZonePath, locomotiveUpdateAccessRule);
		context.setAccessRuleOnNode(locoDivisionPath, locomotiveUpdateAccessRule);
		context.setAccessRuleOnNode(locoShedPath, locomotiveUpdateAccessRule);

		context.setAccessRuleOnNode(locoCondemnReasonPath, locomotiveUpdateAccessRule);
		context.setAccessRuleOnNode(locoCondemnDatePath, locomotiveUpdateAccessRule);

		final Path locoCondemnProposalTypePath = Paths._Root_Locomotive.getPathInSchema()
				.add(Paths._Root_Locomotive._Root_Locomotive_Loco_Condemn_Proposal_Type);
		final Path locoStatusPath = Paths._Root_Locomotive.getPathInSchema()
				.add(Paths._Root_Locomotive._Root_Locomotive_Loco_Status);
		final Path locoPOHDatePath = Paths._Root_Locomotive.getPathInSchema()
				.add(Paths._Root_Locomotive._Root_Locomotive_Loco_POH_Date);

		context.setAccessRuleOnNodeAndAllDescendants(locoStatusPath, true, locomotiveAttributeReadWriteAccessRule);

		context.setAccessRuleOnNodeAndAllDescendants(locoCondemnProposalTypePath, true, locomotiveHiddenAccessRule);
		context.setAccessRuleOnNodeAndAllDescendants(locoPOHDatePath, true, locomotiveHiddenAccessRule);

	}
}