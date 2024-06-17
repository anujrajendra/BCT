package com.cris.coach_master.constraint;

import java.util.Locale;

import com.cris.coach_master.Paths;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.schema.Constraint;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.ConstraintOnNull;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;

public class PassageFlagBasedConstraint implements ConstraintOnNull, Constraint<Object> {

	String attributeName;

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	@Override
	public void checkOccurrence(Object attributeValue, ValueContextForValidation context)
			throws InvalidSchemaException {
		// TODO Auto-generated method stub

		String passageFlag = (String) context
				.getValue(Path.PARENT.add(Paths._Root_Seat_Layout_Details._Root_Seat_Layout_Details_Passage_Flag));

		if (passageFlag != null && passageFlag.equalsIgnoreCase("N")) {
			if (attributeName.equalsIgnoreCase("SeatDirection")) {
				if (attributeValue != null && attributeValue.toString().equalsIgnoreCase("NA")) {
					context.addError("When the Passage Flag is " + passageFlag + " - Field \'" + attributeName
							+ "\' should not be NA");
				}

			} else if (attributeName.equalsIgnoreCase("BerthNumber")) {
				Integer berthNumber; // = (Integer) context
				// .getValue(Path.PARENT.add(Paths._Root_Seat_Layout_Details._Root_Seat_Layout_Details_Berth_No));

				try {
					berthNumber = ((Integer) attributeValue).intValue();

					if (attributeValue != null && berthNumber.equals(0)) {
						context.addError("When the Passage Flag is " + passageFlag + " - Field \'" + attributeName
								+ "\' should not be 0");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (attributeName.equalsIgnoreCase("BerthSerialNumber")) {
				if (attributeValue != null && attributeValue.toString().equalsIgnoreCase("0")) {
					context.addError("When the Passage Flag is " + passageFlag + " - Field \'" + attributeName
							+ "\' should not be 0");
				}
			}
		} else if (passageFlag != null && (passageFlag.equalsIgnoreCase("L") || passageFlag.equalsIgnoreCase("P"))) {
			if (attributeName.equalsIgnoreCase("SeatDirection")) {
				if (attributeValue != null && !attributeValue.toString().equalsIgnoreCase("NA")) {
					context.addError("When the Passage Flag is " + passageFlag + " - Field \'" + attributeName
							+ "\' should be NA");
				}
			} else if (attributeName.equalsIgnoreCase("BerthNumber")
					|| attributeName.equalsIgnoreCase("BerthSerialNumber")) {
				if (attributeValue != null) {
					context.addError("When the Passage Flag is " + passageFlag + " - Field \'" + attributeName
							+ "\' should be null / empty");
				}
			}
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

	@Override
	public void checkNull(ValueContextForValidation context) throws InvalidSchemaException {
		// TODO Auto-generated method stub

		String passageFlag = (String) context
				.getValue(Path.PARENT.add(Paths._Root_Seat_Layout_Details._Root_Seat_Layout_Details_Passage_Flag));
		if (attributeName.equalsIgnoreCase("BerthNumber") || attributeName.equalsIgnoreCase("BerthSerialNumber")) {
			if (passageFlag != null && passageFlag.equalsIgnoreCase("N")) {
				context.addError(
						"When the Passage Flag is " + passageFlag + "- Field \'" + attributeName + "\' is mandatory");
			}
		}

	}

}
