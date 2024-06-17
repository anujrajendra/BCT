package com.cris.reference_master.commonjars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.ConstraintEnumeration;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;

public class GetReferenceValuesFilter implements ConstraintEnumeration<String> {

	String refCode;
	HashMap<String, String> map = new HashMap<String, String>();

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	@Override
	public void checkOccurrence(String value, ValueContextForValidation context) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		if (map.get(value) == null) {
			context.addError("Invalid Reference Value - " + refCode);
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

	@Override
	public String displayOccurrence(String refVal, ValueContext arg1, Locale arg2) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		return map.get(refVal);
		// return ;
	}

	@Override
	public List<String> getValues(ValueContext context) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		String query = "Select s.\"$adaptation\" from \"/root/Reference_Value\" s where FK_AS_STRING('reference_data','/root/Reference_Code', s.Ref_Code) = '"
				+ refCode + "'";
		Repository repository = Repository.getDefault();
		final HomeKey dataSpaceKey = HomeKey.forBranchName("reference_data");
		final AdaptationHome dataspaceName = repository.lookupHome(dataSpaceKey);

		final AdaptationName dataSetKey = AdaptationName.forName("reference_data");
		final Adaptation datasetName = dataspaceName.findAdaptationOrNull(dataSetKey);

		Query<Tuple> queryTuple = datasetName.createQuery(query);

		QueryResult<Tuple> refCodeRecords = queryTuple.getResult();

		Adaptation refCodeRecord = null;
		List<String> list = new ArrayList<String>();
		for (Tuple result : refCodeRecords) {
			refCodeRecord = (Adaptation) result.get(0);
			String refVal = refCodeRecord.getString(Path.parse("./Ref_Value"));
			String refDisplay = refCodeRecord.getString(Path.parse("./Ref_Value_Display"));

			list.add(refVal);
			if (refVal.equalsIgnoreCase(refDisplay)) {
				map.put(refVal, refDisplay);
			} else {
				map.put(refVal, refVal + ", " + refDisplay);
			}

		}

		return list;
	}

}
