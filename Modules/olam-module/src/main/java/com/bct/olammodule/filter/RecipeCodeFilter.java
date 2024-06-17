package com.bct.olammodule.filter;

import java.util.Locale;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.TableRefFilter;
import com.orchestranetworks.schema.TableRefFilterContext;

public class RecipeCodeFilter implements TableRefFilter{

	@Override
	public boolean accept(Adaptation arg0, ValueContext context) {
		// TODO Auto-generated method stub
		
		String basicMaterialGroup = (String) context.getValue(Path.PARENT.add(Paths._Root_Basic._Root_Basic_MatGroup));
		
		System.out.println("===BasicMaterialGroup====="+basicMaterialGroup);
		
		String matGroup = (String) arg0.get(Paths._Root_RecipeCodeDescription._Root_RecipeCodeDescription_MaterialGroup);
		System.out.println("===BasicMaterialGroup====="+matGroup);
		
		if(matGroup != null) {
			System.out.println("=====Hello World========");
			return basicMaterialGroup.contains(matGroup);
		}
		//final RequestResult requestResult = materialTypeTable.createRequestResult(predicate);
		
		//return requestResult.isEmpty();
		return true;
		
	}

	@Override
	public void setup(TableRefFilterContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		return null;
	}

}
