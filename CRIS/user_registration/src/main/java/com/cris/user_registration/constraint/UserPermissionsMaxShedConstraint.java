package com.cris.user_registration.constraint;

import java.util.Locale;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.Constraint;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.InvalidSchemaException;

public class UserPermissionsMaxShedConstraint implements Constraint<String> {

	@Override
	public void checkOccurrence(String shedCode, ValueContextForValidation context) throws InvalidSchemaException {
		// TODO Auto-generated method stub

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
				if (count > 2) {
					context.addError("Only 2 maximum user can register for one shed");
					break;
				}
			}
		}

	}

	@Override
	public void setup(ConstraintContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		return null;
	}

}
