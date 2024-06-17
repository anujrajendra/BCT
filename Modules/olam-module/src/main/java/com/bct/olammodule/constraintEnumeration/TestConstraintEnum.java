package com.bct.olammodule.constraintEnumeration;

import java.util.List;
import java.util.Locale;

import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.ConstraintEnumeration;
import com.orchestranetworks.schema.InvalidSchemaException;

public class TestConstraintEnum implements ConstraintEnumeration<String>{

	@Override
	public void checkOccurrence(String arg0, ValueContextForValidation arg1) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setup(ConstraintContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String displayOccurrence(String arg0, ValueContext arg1, Locale arg2) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getValues(ValueContext arg0) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		return null;
	}

}
