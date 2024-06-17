package com.bct.olammodule.access;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.service.AccessPermission;
import com.orchestranetworks.service.AccessRule;
import com.orchestranetworks.service.Session;

public class MRP03AccessRule implements AccessRule{

	@Override
	public AccessPermission getPermission(final Adaptation adaptation, Session session, SchemaNode node) {
		// TODO Auto-generated method stub
		System.out.println("=====Access rule====");
		System.out.println("====User Reference===="+session.getUserReference());
		System.out.println("====adaptation====="+adaptation);
		
		if(adaptation.isSchemaInstance())
		{
			System.out.println("======Inside isSchemaInstance initial Screen====");
			return AccessPermission.getReadWrite();
		}
		
	
		final String plantPkString = (String) adaptation.get(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_Plant);
		String plantPk="";
		if(plantPkString!=null)
				plantPk = plantPkString.substring(plantPkString.length()-4);
		
		//final String userId = session.getUserReference().getUserId();
		final String basicMaterialId = (String) adaptation.get(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_BasicMaterialID);
		
		final String procurementPk = (String) adaptation.get(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_ProcurementType);
				
		Adaptation adaptationDataset = adaptation.getContainer();
		
		AdaptationTable basicMaterialDetailsTable = adaptationDataset.getTable(com.bct.olammodule.Paths._Root_Basic.getPathInSchema());
		Adaptation basicRecord = basicMaterialDetailsTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(basicMaterialId));
		if(basicRecord == null)
			return AccessPermission.parseFlag("Hello world");
		String businessMaterialTypeString = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_MatType);
		String commodityString = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_Commodity);
		
		String jointKey = commodityString +"|"+ businessMaterialTypeString + "|" + procurementPk +"|"+ plantPk +"|"+ "MRP 3";
		
		AdaptationTable dataSecurityTable = adaptationDataset.getTable(com.bct.olammodule.Paths._Root_DataSecurity.getPathInSchema());
		Adaptation dataSecurityRecord = dataSecurityTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(jointKey));

		if (session.getUserReference().getUserId().equalsIgnoreCase("admin"))
			return AccessPermission.getReadWrite();
		
		if(dataSecurityRecord != null) //&& !session.getUserReference().getUserId().equalsIgnoreCase("admin"))
		{
			String accessPermissionString = (String) dataSecurityRecord.get(Paths._Root_DataSecurity._Root_DataSecurity_AccessPermission);
			final String userId = session.getUserReference().getUserId();
			final String userIdString = (String) adaptation.get(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_UserId);
			if(!userId.equalsIgnoreCase(userIdString))
				return AccessPermission.getHidden();
			
			if(accessPermissionString.equalsIgnoreCase("hidden"))
				return AccessPermission.getHidden();
			else
				return AccessPermission.getReadWrite();
				
		}
		
		//String accessPermissionString = (String) dataSecurityRecord.get(Paths._Root_DataSecurity._Root_DataSecurity_AccessPermission);
		final String userId = session.getUserReference().getUserId();
		final String userIdString = (String) adaptation.get(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_UserId);
		if(!userId.equalsIgnoreCase(userIdString))
			return AccessPermission.getHidden();
		
		return AccessPermission.getReadWrite();
}

}
