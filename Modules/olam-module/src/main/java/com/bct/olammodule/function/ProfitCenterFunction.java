package com.bct.olammodule.function;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.schema.ValueFunction;
import com.orchestranetworks.schema.ValueFunctionContext;

public class ProfitCenterFunction implements ValueFunction{

	@Override
	public Object getValue(Adaptation materialPlant) {
		// TODO Auto-generated method stub

		final String basicMaterialId = (String) materialPlant.get(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_BasicMaterialID);
		final String plantPkString = (String) materialPlant.get(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_Plant);
		System.out.println("===plantPkString====="+plantPkString);
		String plantPk="";
		if(plantPkString!=null)
				plantPk = plantPkString.substring(plantPkString.length()-4);
		System.out.println("===plantPk====="+plantPk);
		
		Adaptation adaptationDataset = materialPlant.getContainer();
		AdaptationTable basicMaterialDetailsTable = adaptationDataset.getTable(com.bct.olammodule.Paths._Root_Basic.getPathInSchema());
		
		Adaptation basicRecord = basicMaterialDetailsTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(basicMaterialId));
		String materialGroupPk = (String) basicRecord.get(
				com.bct.olammodule.Paths._Root_Basic._Root_Basic_MatGroup);
		System.out.println("=====Record======="+basicRecord.get(
				com.bct.olammodule.Paths._Root_Basic._Root_Basic_Commodity));
		
		AdaptationTable materialGroupTable = adaptationDataset.getTable(Paths._Root_MaterialGroup.getPathInSchema());
		Adaptation materialGroupRecord = materialGroupTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(materialGroupPk));
		String materialGroupString = (String) materialGroupRecord.get(
				Paths._Root_MaterialGroup._Root_MaterialGroup_MaterialGroup);
				
		AdaptationTable profitCenterTable = adaptationDataset.getTable(Paths._Root_ProfitCenter.getPathInSchema());
		
		
		String jointKey = materialGroupString +"|"+ plantPk;
		Adaptation profitCenterRecord = profitCenterTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(jointKey));
		
		
		if(profitCenterRecord == null)
			return "Not available";
		return profitCenterRecord.get(Paths._Root_ProfitCenter._Root_ProfitCenter_ProfitCenter); //profitCenterRecord.get(Paths._Root_ProfitCenter._Root_ProfitCenter_ProfitCenter);
	}

	@Override
	public void setup(ValueFunctionContext arg0) {
		// TODO Auto-generated method stub
		
	}

}
