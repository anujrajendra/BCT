package com.cris.user_registration.constraint;

import java.util.Locale;

import com.cris.user_registration.constant.Constants;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
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

public class UserRegistrationConstraint implements Constraint<String> {

	String attributeName;

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	@Override
	public void checkOccurrence(String attributeValue, ValueContextForValidation context)
			throws InvalidSchemaException {
		// TODO Auto-generated method stub

		Repository repository = Repository.getDefault();

		if (attributeName != null) {
			if (attributeName.equalsIgnoreCase("ipasId")) {
				String ipasIdQuery = "Select s.\"$adaptation\" from \"/root/Business_Users_Registration_Details\" s where s.Ipas_Id = '"
						+ attributeValue + "'";
				Query<Tuple> ipasIdQueryTuple = Constants.DATASET_USERREGISTRATION.createQuery(ipasIdQuery);
				QueryResult<Tuple> ipasIdRecords = ipasIdQueryTuple.getResult();

				if (!context.getHome().equals(repository.lookupHome(HomeKey.forBranchName("user_data")))) {
					if (ipasIdRecords.iterator().hasNext())
						context.addError("IPAS ID ALREAY exists.");
				}

			} else if (attributeName.equalsIgnoreCase("ZoneCode")) {

				if (attributeValue != null) {
					String divisionCode = (String) context.getValue(Path.PARENT.add(Path.parse("./Divison")));
					if (divisionCode != null) {
						AdaptationTable divisionTable = Constants.DATASET_REFERENCE
								.getTable((Path.parse("/root/Division")));
						Adaptation divisionRecord = divisionTable
								.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(divisionCode));

						if (divisionRecord != null) {
							String zoneCode = (String) divisionRecord.get(Path.parse("./Zone_Code"));
							if (!zoneCode.equalsIgnoreCase(attributeValue)) {
								context.addError("The User Zone is different ,you can only register same Zone User.");
							}
						}
					}
				}

			} else if (attributeName.equalsIgnoreCase("ServiceStatus")) {
				if (attributeValue != null && attributeValue.equalsIgnoreCase("SERVING (SALARY)")) {
				} else
					context.addError("Employee with 'SERVING/ SALARY' status can only registerd only in MDM.");

			} else if (attributeName.equalsIgnoreCase("MdmUserId")) {

				String designation = (String) context.getValue(Path.PARENT.add(Path.parse("./Designation")));
				String entity = (String) context.getValue(Path.PARENT.add(Path.parse("./Entity")));
				String mdmUserId = (String) context.getValue(Path.PARENT.add(Path.parse("./Mdm_User_Id")));

				if (mdmUserId != null) {
					AdaptationTable designationMasterTable = Constants.DATASET_USERREGISTRATION
							.getTable((Path.parse("/root/Designation_Master")));
					Adaptation designationRecord = designationMasterTable
							.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(designation + "|" + entity));

					if (designationRecord == null) {
						context.addError("Cannot register user with given designation since not allowed.");
					} else {
						if (mdmUserId.contains("DS1") || mdmUserId.contains("DS2")) {
							String dsRole = (String) designationRecord.get(Path.parse("./Ds_Role"));
							if (dsRole == null)
								context.addError(
										"Cannot register user with \"Data Steward DS Role\" since not allowed.");
						}
						if (mdmUserId.contains("DAA1") || mdmUserId.contains("DAA2")) {
							String daaRole = (String) designationRecord.get(Path.parse("./Daa_Role"));
							if (daaRole == null)
								context.addError(
										"Cannot register user with \"Data Approving Authority DAA Role\" since not allowed.");
						}
					}
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
