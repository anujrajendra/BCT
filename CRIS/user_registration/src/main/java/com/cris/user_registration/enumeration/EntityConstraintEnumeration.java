package com.cris.user_registration.enumeration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.cris.user_registration.Paths;
import com.cris.user_registration.constant.Constants;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.ConstraintEnumeration;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;

public class EntityConstraintEnumeration implements ConstraintEnumeration<String> {

	@Override
	public List<String> getValues(ValueContext context) throws InvalidSchemaException {
		// TODO Auto-generated method stub

		String businessUserdesignation = (String) context.getValue(Path.PARENT.add(
				Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Designation));

		List<String> list = new ArrayList<String>();

		String designationMasterQuery = "Select s.\"$adaptation\" from \"/root/Designation_Master\" s where s.Hrms_Designation = '"
				+ businessUserdesignation + "'";
		Query<Tuple> designationMasterQueryTuple = Constants.DATASET_USERREGISTRATION
				.createQuery(designationMasterQuery);
		QueryResult<Tuple> designationMasterRecords = designationMasterQueryTuple.getResult();
		Adaptation designationMasterRecord = null;

		for (Tuple designationMasterResult : designationMasterRecords) {
			designationMasterRecord = (Adaptation) designationMasterResult.get(0);
			list.add(designationMasterRecord.getString(Path.parse("./Entity")));

		}
		return list;
	}

	@Override
	public void checkOccurrence(String arg0, ValueContextForValidation arg1) throws InvalidSchemaException {
		// TODO Auto-generated method stub

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

	@Override
	public String displayOccurrence(String arg0, ValueContext arg1, Locale arg2) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		return null;
	}

}
