package com.cris.loco_master.constraint;

import java.util.Locale;

import com.cris.loco_master.Paths;
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
import com.orchestranetworks.schema.Path;

public class DivisionStatusContraints implements Constraint<String> {

	@Override
	public void checkOccurrence(String arg0, ValueContextForValidation context) throws InvalidSchemaException {
		// TODO Auto-generated method stub

		Repository repository = Repository.getDefault();
		final HomeKey referenceDataSpaceKey = HomeKey.forBranchName("reference_data");
		final AdaptationHome referenceDataspaceName = repository.lookupHome(referenceDataSpaceKey);

		final AdaptationName referenceDataSetKey = AdaptationName.forName("reference_data");
		final Adaptation referenceDatasetName = referenceDataspaceName.findAdaptationOrNull(referenceDataSetKey);

		String divisionValue = (String) context
				.getValue(Path.PARENT.add(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Division));

		String divisionQuery = "SELECT t.\"Active\" FROM \"/root/Division\" t WHERE t.\"$pk\" ='" + divisionValue + "'";

		Query<Tuple> divisionQueryTuple = referenceDatasetName.createQuery(divisionQuery);
		QueryResult<Tuple> divisionRecords = divisionQueryTuple.getResult();

		for (Tuple divisionResult : divisionRecords) {
			String isActive = (String) divisionResult.get(0);

			if (isActive.equalsIgnoreCase("No")) {
				// context.addError("Division is Inactive");
				context.addWarning("Division is Inactive");
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
