package com.cris.commonjars.constraint;

import java.util.Date;
import java.util.Locale;

import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.schema.Constraint;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.InvalidSchemaException;

public class FutureDateRestrictionConstraint implements Constraint<Date> {

	@Override
	public void checkOccurrence(Date date, ValueContextForValidation context) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		Date todayDate = new Date();
		if (date != null)
			if (date.after(todayDate))
				context.addError("The date should not exceed from current date i.e future date is not allowed");
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

}
