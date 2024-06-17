package com.bct.olammodule.function;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.schema.ValueFunction;
import com.orchestranetworks.schema.ValueFunctionContext;

public class totalShelfLifeFunction implements ValueFunction{

	@Override
	public Object getValue(Adaptation materialPlant) {
		final String basicMaterialId = (String) materialPlant.get(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_BasicMaterialID);
		final String plantPkString = (String) materialPlant.get(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_Plant);
		String plantPk="";
		if(plantPkString!=null)
				plantPk = plantPkString.substring(plantPkString.length()-4);
		
		final String procurementPk = (String) materialPlant.get(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_ProcurementType);
				
		Adaptation adaptationDataset = materialPlant.getContainer();
		
		AdaptationTable basicMaterialDetailsTable = adaptationDataset.getTable(com.bct.olammodule.Paths._Root_Basic.getPathInSchema());
		Adaptation basicRecord = basicMaterialDetailsTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(basicMaterialId));
		String businessMaterialTypeString = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_MatType);
		String materialGroupString = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_MatGroup);
		
		String jointKey = businessMaterialTypeString +"|"+ materialGroupString +"|" + procurementPk + "|" + plantPk;
		AdaptationTable totalShelfTable = adaptationDataset.getTable(Paths._Root_TotalShelfLife.getPathInSchema());
		
			Adaptation totalShelfRecord = totalShelfTable.lookupAdaptationByPrimaryKey(
					PrimaryKey.parseString(jointKey));
			if(totalShelfRecord ==  null)
				return "Not available";
			return totalShelfRecord.get(Paths._Root_TotalShelfLife._Root_TotalShelfLife_TotalShelf);	
		
		
		
	}

	@Override
	public void setup(ValueFunctionContext arg0) {
		// TODO Auto-generated method stub
		
	}

}
