package com.bct.crismodule.constraint;

import java.util.Locale;
import java.util.regex.Pattern;

import com.bct.crismodule.Paths;
import com.bct.crismodule.message.ErrorMessage;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.schema.Constraint;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;

public class StationTrafficConstraint implements Constraint<String>{
	
	@Override
	public void checkOccurrence(String value, ValueContextForValidation vcfv) throws InvalidSchemaException {
		// TODO Auto-generated method stub
	
		String regexPattern ="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		System.out.println("-------------Debugger value------------- = "+value);
		//
		System.out.println("------------PARENT PATH-----------"+Path.PARENT);
		
		String trafficString = (String) vcfv.getValue(Path.PARENT.add(Path.PARENT.add(Paths._Station._Tab1_Stn_name)));
		System.out.println("-------------Debugger string------------- = "+trafficString);
		if(!patternMatches(value, regexPattern)) {
			vcfv.addError(ErrorMessage.errorMsgForTrafficStringValidation);
		}		
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
	
	public static boolean patternMatches(String trafficAddress, String regexPattern) {
	    return Pattern.compile(regexPattern)
	      .matcher(trafficAddress)
	      .matches();
	}

}
