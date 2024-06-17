package com.bct.crismodule.function;

import com.bct.crismodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.schema.ValueFunction;
import com.orchestranetworks.schema.ValueFunctionContext;

public class StationTempFunction implements ValueFunction{

	@Override
	public Object getValue(final Adaptation station) {
		// TODO Auto-generated method stub
		
		final String stn_code = (String) station.get(Paths._Station._Tab1_Stn_code);
		final String stn_name = (String) station.get(Paths._Station._Tab1_Stn_name);
		
		if(stn_code == null || stn_name == null)
			return null;
		
		return stn_code.concat(stn_name);
		
	}

	@Override
	public void setup(ValueFunctionContext arg0) {
		// TODO Auto-generated method stub
		
	}

}
