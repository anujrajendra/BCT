package com.bct.retailmodule.constraint;

import java.math.BigDecimal;
import java.util.Locale;

import com.bct.retailmodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.schema.Constraint;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;

public class InventoryPriceConstraint implements Constraint<BigDecimal>{

	private BigDecimal tolerance;
	
	public void setTolerance(final BigDecimal tolerance) {
		this.tolerance = tolerance;
	}
	
	@Override
	public void setup(ConstraintContext context) {
		// TODO Auto-generated method stub
		
		if(this.tolerance == null || this.tolerance.doubleValue()<0) {
			//context.addMessage("TheToleranceMustBeAPositiveDecimal");
		}
	}
	
	@Override
	public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		return null; //return Message.get()
	}
	
	@Override
	public void checkOccurrence(final BigDecimal value, final ValueContextForValidation context) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		
		final Double price = (value != null) ? value.doubleValue():null;
		final Double defaultPrice = getItemDefaultPrice(context);
		
		if (price == null || defaultPrice == null)
			return;
		
		if(Math.abs(price - defaultPrice) > (this.tolerance.doubleValue() * defaultPrice)) {
			context.addMessage(null);
		}
		
	}

	private Double getItemDefaultPrice(ValueContextForValidation context) {
		// TODO Auto-generated method stub
		final String itemIdentifier = (String) context.getValue(Path.PARENT.add(Paths._Inventory._Item));
		
		final Adaptation dataset = context.getAdaptationInstance();
		
		final AdaptationTable items = dataset.getTable(Paths._Item.getPathInSchema());
		
		final Adaptation item = items.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(itemIdentifier));

		if(item==null)
			return null;
		
		final BigDecimal defaultPrice = (BigDecimal) item.get(Paths._Item._DefaultPrice);
		
		if (defaultPrice==null)
			return null;

		return defaultPrice.doubleValue();
	}
}
