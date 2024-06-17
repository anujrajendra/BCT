package com.bct.olammodule.function;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.schema.ValueFunction;
import com.orchestranetworks.schema.ValueFunctionContext;

public class MaterialDescriptionFunction implements ValueFunction{

	@Override
	public Object getValue(Adaptation MaterialPlant) {
		// TODO Auto-generated method stub
		
		final String basicMaterialId = (String) MaterialPlant.get(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_BasicMaterialID);
		
		Adaptation adaptationDataset = MaterialPlant.getContainer();
		AdaptationTable basicMaterialDetailsTable = adaptationDataset.getTable(com.bct.olammodule.Paths._Root_Basic.getPathInSchema());
		
		Adaptation basicRecord = basicMaterialDetailsTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(basicMaterialId));
		System.out.println("=====Record======="+basicRecord.get(
				com.bct.olammodule.Paths._Root_Basic._Root_Basic_Commodity));
		
		String recipeCodeString ="";
		String certificationString ="";
		String packSizeString ="";
		String palletString ="";
		
		if(basicRecord.get(Paths._Root_Basic._Root_Basic_RecipeCode) != null)
		{
			String recipeCodePk = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_RecipeCode);
			
			AdaptationTable recipeCodeTable = adaptationDataset.getTable(Paths._Root_RecipeCodeDescription.getPathInSchema());
			Adaptation recipeCodeRecord = recipeCodeTable.lookupAdaptationByPrimaryKey(
					PrimaryKey.parseString(recipeCodePk));
			if(recipeCodeRecord!=null)
				recipeCodeString = (String) recipeCodeRecord.get(Paths._Root_RecipeCodeDescription._Root_RecipeCodeDescription_RecipeCodeDescription);
		}
		if(basicRecord.get(Paths._Root_Basic._Root_Basic_Certification) != null)
		{
			String certificationPk = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_Certification);
			
			AdaptationTable certificationTable = adaptationDataset.getTable(Paths._Root_CertificationChainOfCustody.getPathInSchema());
			Adaptation certificationRecord = certificationTable.lookupAdaptationByPrimaryKey(
					PrimaryKey.parseString(certificationPk));
			if(certificationRecord != null)
				certificationString = (String) certificationRecord.get(Paths._Root_CertificationChainOfCustody._Root_CertificationChainOfCustody_CertificationChainOfCustody);
		}
		if(basicRecord.get(Paths._Root_Basic._Root_Basic_PackSize) != null)
		{
			String packSizePk = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_PackSize);
			
			AdaptationTable packSizeTable = adaptationDataset.getTable(Paths._Root_PackSize.getPathInSchema());
			Adaptation packSizeRecord = packSizeTable.lookupAdaptationByPrimaryKey(
					PrimaryKey.parseString(packSizePk));
			if(packSizeRecord!=null)
				packSizeString = (String) packSizeRecord.get(Paths._Root_PackSize._Root_PackSize_PackSize);
		}
		if(basicRecord.get(Paths._Root_Basic._Root_Basic_PalletTypeSize) != null)
		{
			String palletPk = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_PalletTypeSize);
			
			AdaptationTable palletTable = adaptationDataset.getTable(Paths._Root_PalletTypeSize.getPathInSchema());
			Adaptation palletRecord = palletTable.lookupAdaptationByPrimaryKey(
					PrimaryKey.parseString(palletPk));
			if(palletRecord!=null)
				palletString = (String) palletRecord.get(Paths._Root_PalletTypeSize._Root_PalletTypeSize_PalletTypeSize);
		}
		
		return " " + recipeCodeString + " " +
		certificationString + " " +
		packSizeString + " " +
		palletString + " ";
	}

	@Override
	public void setup(ValueFunctionContext arg0) {
		// TODO Auto-generated method stub
		
	}

}
