package com.cris.feedback.trigger;

import com.cris.feedback.constant.Constants;
import com.cris.feedback.path.FeedbackPath;
import com.cris.feedback.utils.RepositoryUtils;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.trigger.NewTransientOccurrenceContext;
import com.orchestranetworks.schema.trigger.TableTrigger;
import com.orchestranetworks.schema.trigger.TriggerSetupContext;
import com.orchestranetworks.service.Role;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.service.ValueContextForUpdate;
import com.orchestranetworks.service.directory.DirectoryHandler;

public class FeedbackUserTrigger extends TableTrigger {

	@Override
	public void setup(TriggerSetupContext arg0) {

	}

	public void handleNewContext(NewTransientOccurrenceContext context) {

		ValueContextForUpdate vcfu = context.getOccurrenceContextForUpdate();
		Session session = context.getSession();

		String userId = session.getUserReference().getUserId(); // Fetch Current User ID

		Repository repository = Repository.getDefault();
		DirectoryHandler directoryHandler = DirectoryHandler.getInstance(repository);

		UserReference userReference = UserReference.forUser(userId);

		boolean isAdminUser = directoryHandler.isUserInRole(userReference, Role.ADMINISTRATOR);
		if (isAdminUser) {
			vcfu.setValue("Administrator", FeedbackPath._Root_Feedback._Root_Feedback_UserId);
			vcfu.setValue("Administrator", FeedbackPath._Root_Feedback._Root_Feedback_UserName);
		}

		Adaptation dataset = RepositoryUtils.getDataset("user_data", "user_data");

		String businessMdmUserIdQuery = "Select s.\"$adaptation\" from \"/root/Business_Users_Registration_Details\" s where s.Mdm_User_Id = '"
				+ userId + "'";
		Query<Tuple> businessMdmUserIdTuple = Constants.DATASET_USERREGISTRATION.createQuery(businessMdmUserIdQuery);
		QueryResult<Tuple> businessMdmUserIdRecords = businessMdmUserIdTuple.getResult();

		Adaptation businessUsersRecord = null;

		for (Tuple mdmUserIdResult : businessMdmUserIdRecords) {
			businessUsersRecord = (Adaptation) mdmUserIdResult.get(0);

			vcfu.setValue(businessUsersRecord.get(Path.parse("./Ipas_Id")),
					FeedbackPath._Root_Feedback._Root_Feedback_UserId);
			vcfu.setValue(businessUsersRecord.get(Path.parse("./Name")),
					FeedbackPath._Root_Feedback._Root_Feedback_UserName);
			vcfu.setValue(businessUsersRecord.get(Path.parse("./Designation")),
					FeedbackPath._Root_Feedback._Root_Feedback_UserDesignation);

			vcfu.setValue(businessUsersRecord.get(Path.parse("./Zone")),
					FeedbackPath._Root_Feedback._Root_Feedback_UserZone);
			vcfu.setValue(businessUsersRecord.get(Path.parse("./Divison")),
					FeedbackPath._Root_Feedback._Root_Feedback_UserDivision);
			vcfu.setValue(businessUsersRecord.get(Path.parse("./Mobile_Number")),
					FeedbackPath._Root_Feedback._Root_Feedback_MobileNumber);

		}

	}

}
