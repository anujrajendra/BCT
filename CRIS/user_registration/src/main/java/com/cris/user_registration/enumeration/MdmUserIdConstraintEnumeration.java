package com.cris.user_registration.enumeration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.cris.user_registration.Paths;
import com.cris.user_registration.constant.Constants;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.ConstraintEnumeration;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;

public class MdmUserIdConstraintEnumeration implements ConstraintEnumeration<String> {

	@Override
	public List<String> getValues(ValueContext context) throws InvalidSchemaException {
		// TODO Auto-generated method stub

		String shedCode = (String) context.getValue(Path.PARENT
				.add(Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Shed));
		String presentBusinessMdmUserId = (String) context.getValue(Path.PARENT.add(
				Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Mdm_User_Id));
		String designation = (String) context.getValue(Path.PARENT.add(
				Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Designation));
		String entity = (String) context.getValue(Path.PARENT
				.add(Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Entity));

		AdaptationTable designationMasterTable = Constants.DATASET_USERREGISTRATION
				.getTable((Path.parse("/root/Designation_Master")));
		Adaptation designationRecord = designationMasterTable
				.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(designation + "|" + entity));

		List<String> list = new ArrayList<String>();

		if (designationRecord == null) {
			return list;

		} else {
			if (shedCode != null) {
				String loginPattern = "Shed" + "_" + shedCode;

				String systemUsersQuery = "Select s.\"$adaptation\" from \"/root/User_Registration_Details\" s where s.Ipas_Id like '%"
						+ loginPattern + "%'";
				Query<Tuple> systemUsersTuple = Constants.DATASET_USERREGISTRATION.createQuery(systemUsersQuery);
				QueryResult<Tuple> systemUsersRecords = systemUsersTuple.getResult();

				Adaptation systemUsersRecord = null;
				String systemMdmUserId = "";

				for (Tuple systemUsersResult : systemUsersRecords) {
					systemUsersRecord = (Adaptation) systemUsersResult.get(0);
					systemMdmUserId = systemUsersRecord.getString(Path.parse("./Ipas_Id"));

					String businessMdmUserIdQuery = "Select s.\"$adaptation\" from \"/root/Business_Users_Registration_Details\" s where s.Mdm_User_Id = '"
							+ systemMdmUserId + "'";
					Query<Tuple> businessMdmUserIdTuple = Constants.DATASET_USERREGISTRATION
							.createQuery(businessMdmUserIdQuery);
					QueryResult<Tuple> businessMdmUserIdRecords = businessMdmUserIdTuple.getResult();

					if (presentBusinessMdmUserId != null) {
						if (systemMdmUserId.equalsIgnoreCase(presentBusinessMdmUserId))
							list.add(systemMdmUserId);
					}
					if (businessMdmUserIdRecords.iterator().hasNext())
						continue;

					if (systemMdmUserId.contains("DS1") || systemMdmUserId.contains("DS2")) {
						String dsRole = (String) designationRecord.get(Path.parse("./Ds_Role"));
						if (dsRole == null)
							continue;
					}
					if (systemMdmUserId.contains("DAA1") || systemMdmUserId.contains("DAA2")) {
						String daaRole = (String) designationRecord.get(Path.parse("./Daa_Role"));
						if (daaRole == null)
							continue;
					}
					list.add(systemMdmUserId);

				}
			}
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
