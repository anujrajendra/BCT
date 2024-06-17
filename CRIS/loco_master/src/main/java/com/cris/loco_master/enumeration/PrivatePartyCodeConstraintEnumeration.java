package com.cris.loco_master.enumeration;

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

public class PrivatePartyCodeConstraintEnumeration implements ConstraintEnumeration<String> {

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
			context.addError("Invalid Reference Value");
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
	public String displayOccurrence(String code, ValueContext arg1, Locale arg2) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		return map.get(code);
	}

	@Override
	public List<String> getValues(ValueContext context) throws InvalidSchemaException {
		// TODO Auto-generated method stub

		String pvtOwnerFlag = (String) context.getValue(Path.PARENT.add(Path.parse("./Loco_Pvt_Owner_Flag")));

		System.out.println("===pvt owner flag===" + pvtOwnerFlag);

		List<String> list = new ArrayList<String>();
		if (!pvtOwnerFlag.equalsIgnoreCase("N")) {
			Repository repository = Repository.getDefault();
			final HomeKey referenceDataSpaceKey = HomeKey.forBranchName("reference_data");
			final AdaptationHome referenceDataspaceName = repository.lookupHome(referenceDataSpaceKey);

			final AdaptationName referenceDataSetKey = AdaptationName.forName("reference_data");
			final Adaptation referenceDatasetName = referenceDataspaceName.findAdaptationOrNull(referenceDataSetKey);

			String query = "Select s.\"$adaptation\" from \"/root/Reference_Value\" s where FK_AS_STRING('reference_data','/root/Reference_Code', s.Ref_Code) = '"
					+ "Loco_Pvt_Party_Code" + "'";

			Query<Tuple> queryTuple = referenceDatasetName.createQuery(query);
			QueryResult<Tuple> queryRecords = queryTuple.getResult();

			Adaptation pvtPartyCodeRecord = null;
			String refValue = "";
			String refDisplay = "";

			for (Tuple pvtPartyCodeResult : queryRecords) {
				pvtPartyCodeRecord = (Adaptation) pvtPartyCodeResult.get(0);

				refValue = (String) pvtPartyCodeRecord.get(Path.parse("./Ref_Value"));
				refDisplay = (String) pvtPartyCodeRecord.get(Path.parse("./Ref_Value_Display"));

				list.add(refValue);
				if (refValue.equalsIgnoreCase(refDisplay)) {
					map.put(refValue, refDisplay);
				} else {
					map.put(refValue, refValue + ", " + refDisplay);
				}
			}
		}
		return list;
	}

}
