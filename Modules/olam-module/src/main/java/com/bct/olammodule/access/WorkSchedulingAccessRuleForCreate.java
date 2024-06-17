package com.bct.olammodule.access;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.service.AccessPermission;
import com.orchestranetworks.service.AccessRuleForCreate;
import com.orchestranetworks.service.AccessRuleForCreateContext;

public class WorkSchedulingAccessRuleForCreate implements AccessRuleForCreate{

	@Override
	public AccessPermission getPermission(AccessRuleForCreateContext aContext) {
		// TODO Auto-generated method stub
		SchemaNode schemaNode = aContext.getNode();
		if(schemaNode.isCheckNullInput())
		{
			System.out.println("===Null input===");
		}
		else
		{
			System.out.println("===Not Null input===");
		}
		
		ValueContext valueContext = aContext.getValueContext();
		final String basicMaterialId = (String) valueContext.getValue(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_BasicMaterialID);
		
		final String procurementPk = (String) valueContext.getValue(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_ProcurementType);
			
		final String plantPkString = (String) valueContext.getValue(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_Plant);
		String plantPk="";
		if(plantPkString!=null)
				plantPk = plantPkString.substring(plantPkString.length()-4);
		
		if(basicMaterialId == null || procurementPk == null || plantPk == null)
			return AccessPermission.getHidden();
		
		
		Adaptation adaptationDataset = valueContext.getAdaptationInstance();
		AdaptationTable basicMaterialDetailsTable = adaptationDataset.getTable(com.bct.olammodule.Paths._Root_Basic.getPathInSchema());
		Adaptation basicRecord = basicMaterialDetailsTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(basicMaterialId));
		String businessMaterialTypeString = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_MatType);
		String commodityString = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_Commodity);
		
		String jointKey = commodityString +"|"+ businessMaterialTypeString + "|" + procurementPk +"|"+ plantPk +"|"+ "Work scheduling";
		
		AdaptationTable dataSecurityTable = adaptationDataset.getTable(com.bct.olammodule.Paths._Root_DataSecurity.getPathInSchema());
		Adaptation dataSecurityRecord = dataSecurityTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(jointKey));
				
		if(dataSecurityRecord != null) //&& !session.getUserReference().getUserId().equalsIgnoreCase("admin"))
		{
			String accessPermissionString = (String) dataSecurityRecord.get(Paths._Root_DataSecurity._Root_DataSecurity_AccessPermission);
			
			if(accessPermissionString.equalsIgnoreCase("hidden"))
				return AccessPermission.getHidden();
			else
				return AccessPermission.getReadWrite();
				
		}
		
		//String accessPermissionString = (String) dataSecurityRecord.get(Paths._Root_DataSecurity._Root_DataSecurity_AccessPermission);
		
		return AccessPermission.getReadWrite();
	}

}
