package com.cris.user_registration.trigger;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.trigger.BeforeCreateOccurrenceContext;
import com.orchestranetworks.schema.trigger.TableTrigger;
import com.orchestranetworks.schema.trigger.TriggerSetupContext;
import com.orchestranetworks.service.OperationException;

public class UserPermissionsMaxShedTrigger extends TableTrigger {

	@Override
	public void setup(TriggerSetupContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleBeforeCreate(BeforeCreateOccurrenceContext aContext) throws OperationException {
		// TODO Auto-generated method stub
		super.handleBeforeCreate(aContext);

		String shedCode = (String) aContext.getOccurrenceContext().getValue(Path.parse("./Shed_Code"));

		Repository repository = Repository.getDefault();
		final HomeKey userDataSpaceKey = HomeKey.forBranchName("user_data");
		final AdaptationHome userDataspaceName = repository.lookupHome(userDataSpaceKey);

		final AdaptationName userDataSetKey = AdaptationName.forName("user_data");
		final Adaptation userDatasetName = userDataspaceName.findAdaptationOrNull(userDataSetKey);

		String shedQuery = "Select s.\"$adaptation\" from \"/root/User_Permissions_Shed\" s where FK_AS_STRING('user_data','/root/User_Permissions_Shed', s.Shed_Code) = '"
				+ shedCode + "'";
		Query<Tuple> shedQueryTuple = userDatasetName.createQuery(shedQuery);
		QueryResult<Tuple> shedRecords = shedQueryTuple.getResult();

		int count = 0;
		if (shedRecords.iterator().hasNext()) {
			for (Tuple shedResult : shedRecords) {
				count = count + 1;
				if (count >= 2) {

					throw OperationException.createError("Only 2 maximum user can register for one shed");

				}

			}
		}

	}

}
