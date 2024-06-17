package com.bct.retailmodule.trigger;

import com.bct.retailmodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.trigger.AfterDeleteOccurrenceContext;
import com.orchestranetworks.schema.trigger.TableTrigger;
import com.orchestranetworks.schema.trigger.TriggerSetupContext;
import com.orchestranetworks.service.OperationException;

public class ItemTrigger extends TableTrigger{

	@Override
	public void setup(TriggerSetupContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleAfterDelete(final AfterDeleteOccurrenceContext context) 
	throws OperationException {
		
		final ValueContext itemContext = context.getOccurrenceContext();
		final Integer itemIdentifier = (Integer) itemContext.getValue(Paths._Item._Identifier);
		final Adaptation dataset = itemContext.getAdaptationInstance();
		final AdaptationTable inventoryTable = dataset.getTable(Paths._Inventory.getPathInSchema());
		final String predicate = Paths._Inventory._Item.format() + "= '" + itemIdentifier + "'";
		
		try (RequestResult requestResult = inventoryTable.createRequestResult(predicate)){
			for(Adaptation inventory; (inventory = requestResult.nextAdaptation())!=null;) {
				final com.orchestranetworks.service.ProcedureContext procedureContext = context.getProcedureContext();
				final AdaptationName inventoryName = inventory.getAdaptationName();
				
				procedureContext.doDelete(inventoryName,false);
			}
		}
		
		
	}

	
}
