package com.bct.olammodule.filter;

import java.util.Locale;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.schema.TableRefFilter;
import com.orchestranetworks.schema.TableRefFilterContext;

public class SapMaterialTypeFilter implements TableRefFilter{

	@Override
	public boolean accept(Adaptation arg0, ValueContext context) {
		// TODO Auto-generated method stub
		
		String basicMaterialId = (String) context.getValue(Path.PARENT.add(Path.PARENT.add(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_BasicMaterialID)));
		System.out.println("=======Value Context========"+context.getValue(Path.PARENT.add(Path.PARENT.add(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_BasicMaterialID)))); 
		
		Adaptation adaptationDataset = context.getAdaptationInstance();
		
		AdaptationTable basicMaterialDetailsTable = adaptationDataset.getTable(com.bct.olammodule.Paths._Root_Basic.getPathInSchema());
		Adaptation basicRecord = basicMaterialDetailsTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(basicMaterialId));
		String businessMaterialType = (String) basicRecord.get(Paths._Root_Basic._Root_Basic_MatType);
		System.out.println("========BusinessMaterialType========" + businessMaterialType);
		AdaptationTable materialTypeTable = adaptationDataset.getTable(Paths._Root_MaterialType.getPathInSchema());
		
		final String predicate = Paths._Root_MaterialType._Root_MaterialType_MatType.format() 
				+ "=\"" + businessMaterialType +"\"";

		System.out.println("======predicate======"+predicate);
		
		String matType = (String) arg0.get(Paths._Root_MaterialType._Root_MaterialType_MatType);
		AdaptationTable matTable = arg0.getContainerTable();
		
		if(matType != null) {
			System.out.println("=====Hello World========");
			return matType.equalsIgnoreCase(businessMaterialType);
		}
		//final RequestResult requestResult = materialTypeTable.createRequestResult(predicate);
		
		//return requestResult.isEmpty();
		return true;
	}

	@Override
	public void setup(TableRefFilterContext context) {
		// TODO Auto-generated method stub
		final SchemaNode materialPlantNode =  context.getSchemaNode();
		final SchemaNode materialTypeNode = materialPlantNode.getNode(Paths._Root_MaterialType.getPathInSchema());
		
		context.addDependencyToInsertDeleteAndModify(materialTypeNode);
	}

	@Override
	public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
