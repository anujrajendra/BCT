package com.bct.olammodule.filter;

import java.util.Locale;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.TableRefFilter;
import com.orchestranetworks.schema.TableRefFilterContext;

public class PlantUserFilter implements TableRefFilter{

	@Override
	public boolean accept(Adaptation arg0, ValueContext context) {
		// TODO Auto-generated method stub
		String userIdContext = (String) context.getValue(Path.PARENT.add(Path.PARENT.add(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_UserId)));
		
		String userIdAdaptation = (String) arg0.get(Paths._Root_UserPlant._Root_UserPlant_User);
		
		if(userIdAdaptation != null) {
			System.out.println("=====Hello World========");
			return userIdContext.equalsIgnoreCase(userIdAdaptation);
		}
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
