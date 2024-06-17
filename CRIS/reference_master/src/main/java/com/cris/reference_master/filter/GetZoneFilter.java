package com.cris.reference_master.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
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

public class GetZoneFilter implements ConstraintEnumeration<String>{

	HashMap<String, String> map = new HashMap<String, String>();
	
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

	@Override
	public List<String> getValues(ValueContext arg0) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		Repository repository = Repository.getDefault();
		final HomeKey dataSpaceKey = HomeKey.forBranchName("reference_data");
		final AdaptationHome dataspaceName = repository.lookupHome(dataSpaceKey);
		
		final AdaptationName dataSetKey = AdaptationName.forName("reference_data");
		final Adaptation datasetName = dataspaceName.findAdaptationOrNull(dataSetKey);
		
		AdaptationTable zoneTable = datasetName.getTable(Path.parse("/root/Zone"));
		
		String query = "Select s.\"$adaptation\" from \"/root/User_Permissions_Zone\" s where FK_AS_STRING('reference_data','/root/User_Permissions_Zone', s.User_Id) = \'RB_DS_User_01\'"; // '"+refCode+"'";
		
		Query<Tuple> queryTuple = datasetName.createQuery(query);
		
		QueryResult<Tuple> refCodeRecords = queryTuple.getResult();
		
		Adaptation userPermissionsZoneRecord = null;
		List<String> list = new ArrayList<String>();
		for (Tuple result : refCodeRecords) {
			userPermissionsZoneRecord = (Adaptation) result.get(0);
			String userZoneCode = userPermissionsZoneRecord.getString(Path.parse("./Zone_Code"));
			Adaptation zoneRecord = zoneTable.lookupAdaptationByPrimaryKey(
					PrimaryKey.parseString(userZoneCode));
			
//			String refDisplay = refCodeRecord.getString(Path.parse("./Ref_Value_Display"));
//			
//			list.add(refVal);
//			if(refVal.equalsIgnoreCase(refDisplay)) {
//				map.put(refVal, refDisplay);
//			}
//			else {
//				map.put(refVal, refVal +", "+refDisplay);
//			}
//			System.out.println("====Ref code record===="+refCodeRecord);
		}
		//System.out.println("===List print==="+list2);
		
		return list;
		
	}

}
