package com.bct.retailmodule.function;

import com.bct.retailmodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.schema.ValueFunction;
import com.orchestranetworks.schema.ValueFunctionContext;
import java.math.BigDecimal;

public class InventoryTotalFunction implements ValueFunction{

	@Override
	public Object getValue(final Adaptation inventory) {

		final Integer stock = (Integer) inventory.get(Paths._Inventory._Stock);
		final BigDecimal price = (BigDecimal) inventory.get(Paths._Inventory._Price);
		
		if(stock == null || price == null) {
			return null;
		}
		
		final BigDecimal total = price.multiply(new BigDecimal(stock));
		
		return total;
		
	}

	@Override
	public void setup(ValueFunctionContext arg0) {
		// TODO Auto-generated method stub
		
	}

}
