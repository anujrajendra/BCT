package com.bct.crismodule.trigger;

import com.bct.crismodule.Paths;
import com.orchestranetworks.schema.trigger.AfterCreateOccurrenceContext;
import com.orchestranetworks.schema.trigger.BeforeCreateOccurrenceContext;
import com.orchestranetworks.schema.trigger.NewTransientOccurrenceContext;
import com.orchestranetworks.schema.trigger.TableTrigger;
import com.orchestranetworks.schema.trigger.TriggerSetupContext;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.ValueContextForUpdate;

public class StationTrigger extends TableTrigger{

	@Override
	public void setup(TriggerSetupContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleAfterCreate(AfterCreateOccurrenceContext aContext) throws OperationException {
		
	}

	@Override
	public void handleBeforeCreate(BeforeCreateOccurrenceContext aContext) throws OperationException {
		ValueContextForUpdate valueContextForUpdate = aContext.getOccurrenceContextForUpdate();
		valueContextForUpdate.setValue("Meter", Paths._Station._Tab1_Gauge_type);
		valueContextForUpdate.setValue("False", Paths._Station._Tab1_Halt_flag);
	}

	@Override
	public void handleNewContext(NewTransientOccurrenceContext aContext) {
		
		ValueContextForUpdate vcfu = aContext.getOccurrenceContextForUpdate();
		vcfu.setValue("0.0", Paths._Station._Tab2_Latitude);
		vcfu.setValue("0.0", Paths._Station._Tab2_Longitude);
	}
	
	

}
