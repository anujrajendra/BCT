package com.bct.olammodule.function;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.schema.ValueFunction;
import com.orchestranetworks.schema.ValueFunctionContext;

public class MrpTypeFunction implements ValueFunction{

	@Override
	public Object getValue(Adaptation materialPlant) {
		// TODO Auto-generated method stub
		final String basicMaterialId = (String) materialPlant.get(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_BasicMaterialID);
		final String plantPkString = (String) materialPlant.get(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_Plant);
		String plantPk="";
		if(plantPkString!=null)
				plantPk = plantPkString.substring(plantPkString.length()-4);
		Adaptation adaptationDataset = materialPlant.getContainer();
		
		AdaptationTable basicMaterialDetailsTable = adaptationDataset.getTable(com.bct.olammodule.Paths._Root_Basic.getPathInSchema());
		Adaptation basicRecord = basicMaterialDetailsTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(basicMaterialId));
		String businessMaterialTypeString = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_MatType);
		
		String jointKey = businessMaterialTypeString +"|"+ plantPk;
		AdaptationTable mrpTypeTable = adaptationDataset.getTable(Paths._Root_MrpType.getPathInSchema());
		Adaptation mrpTypeRecord = mrpTypeTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(jointKey));
		if(mrpTypeRecord==null)
			return "Not available";
		return mrpTypeRecord.get(Paths._Root_MrpType._Root_MrpType_MrpType);
	}

	@Override
	public void setup(ValueFunctionContext arg0) {
		// TODO Auto-generated method stub
		
	}

}
