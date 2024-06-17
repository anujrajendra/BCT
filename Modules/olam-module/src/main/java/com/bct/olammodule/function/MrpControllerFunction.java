package com.bct.olammodule.function;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.schema.ValueFunction;
import com.orchestranetworks.schema.ValueFunctionContext;

public class MrpControllerFunction implements ValueFunction{

	@Override
	public Object getValue(Adaptation materialPlant) {
		// TODO Auto-generated method stub
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
		
		String jointKey = businessMaterialTypeString +"|"+ plantPk + "|" + procurementPk;
		AdaptationTable mrpControllerTable = adaptationDataset.getTable(Paths._Root_MrpController.getPathInSchema());
		Adaptation mrpControllerRecord = mrpControllerTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(jointKey));
		if(mrpControllerRecord==null)
			return "Not available";
		return mrpControllerRecord.get(Paths._Root_MrpController._Root_MrpController_MrpController);
	}

	@Override
	public void setup(ValueFunctionContext arg0) {
		// TODO Auto-generated method stub
		
	}

}
