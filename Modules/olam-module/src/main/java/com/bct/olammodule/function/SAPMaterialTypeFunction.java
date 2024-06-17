package com.bct.olammodule.function;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.schema.ValueFunction;
import com.orchestranetworks.schema.ValueFunctionContext;

public class SAPMaterialTypeFunction implements ValueFunction{

	@Override
	public Object getValue(Adaptation materialPlant) {
		// TODO Auto-generated method stub
		final String basicMaterialId = (String) materialPlant.get(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_BasicMaterialID);
		
		Adaptation adaptationDataset = materialPlant.getContainer();
		AdaptationTable basicMaterialDetailsTable = adaptationDataset.getTable(com.bct.olammodule.Paths._Root_Basic.getPathInSchema());
		
		Adaptation basicRecord = basicMaterialDetailsTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(basicMaterialId));
		System.out.println("=====Record======="+basicRecord.get(
				com.bct.olammodule.Paths._Root_Basic._Root_Basic_Commodity));
		
		
		String businessMaterialType = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_MatType);
		System.out.println("========BusinessMaterialType========" + businessMaterialType);
		AdaptationTable materialTypeTable = adaptationDataset.getTable(Paths._Root_MaterialType.getPathInSchema());
		Adaptation materialTypeRecord = materialTypeTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(businessMaterialType));
		if(materialTypeRecord == null)
			return "Not available";
		return materialTypeRecord.get(Paths._Root_MaterialType._Root_MaterialType_SapMaterialType);
	}

	@Override
	public void setup(ValueFunctionContext arg0) {
		// TODO Auto-generated method stub
		
	}

}
