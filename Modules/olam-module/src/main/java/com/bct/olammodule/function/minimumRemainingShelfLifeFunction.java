package com.bct.olammodule.function;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.schema.ValueFunction;
import com.orchestranetworks.schema.ValueFunctionContext;

public class minimumRemainingShelfLifeFunction implements ValueFunction{

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
		AdaptationTable minimumShelfTable = adaptationDataset.getTable(Paths._Root_MinShelfLife.getPathInSchema());
		
			Adaptation minimumShelfRecord = minimumShelfTable.lookupAdaptationByPrimaryKey(
					PrimaryKey.parseString(jointKey));
			System.out.println("minimum shelf record"+minimumShelfRecord);
			if (minimumShelfRecord == null)
				return "Not Available";
			return minimumShelfRecord.get(Paths._Root_MinShelfLife._Root_MinShelfLife_MinShelf);	
		
		
	}

	@Override
	public void setup(ValueFunctionContext arg0) {
		// TODO Auto-generated method stub
		
	}

}
