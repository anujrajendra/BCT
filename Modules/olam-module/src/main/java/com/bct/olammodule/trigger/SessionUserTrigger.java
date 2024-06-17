package com.bct.olammodule.trigger;

import com.bct.olammodule.Paths;
import com.orchestranetworks.schema.trigger.BeforeModifyOccurrenceContext;
import com.orchestranetworks.schema.trigger.NewTransientOccurrenceContext;
import com.orchestranetworks.schema.trigger.TableTrigger;
import com.orchestranetworks.schema.trigger.TriggerSetupContext;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.service.ValueContextForUpdate;

public class SessionUserTrigger extends TableTrigger{

	@Override
	public void setup(TriggerSetupContext arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleNewContext(NewTransientOccurrenceContext aContext) {
		// TODO Auto-generated method stub
		Session session = aContext.getSession();
		String userID = session.getUserReference().getUserId();
		
		ValueContextForUpdate valueContextForUpdate = aContext.getOccurrenceContextForUpdate();
		valueContextForUpdate.setValue(userID, Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_UserId);
	
		super.handleNewContext(aContext);
	}

	@Override
	public void handleBeforeModify(BeforeModifyOccurrenceContext aContext) throws OperationException {
		// TODO Auto-generated method stub
		Session session = aContext.getSession();
		String userID = session.getUserReference().getUserId();
		
		ValueContextForUpdate valueContextForUpdate = aContext.getOccurrenceContextForUpdate();
		valueContextForUpdate.setValue(userID, Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_UserId);
	
		super.handleBeforeModify(aContext);
	}

		

//
//	@Override
//	public void handleNewContext(NewTransientOccurrenceContext aContext) {
//		// TODO Auto-generated method stub
//		System.out.println("====Hello World 123=====");
//		Session session = aContext.getSession();
//		String userID = session.getUserReference().getUserId();
//		ValueContextForUpdate vcfu = aContext.getOccurrenceContextForUpdate();
//		if(userID.equals("ram"))
//			vcfu.setValue("4184", Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_Plant);
//		
//		TableRefFilter tableRefFilter = new TableRefFilter() {
//			@Override
//			public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public void setup(TableRefFilterContext arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public boolean accept(Adaptation plantTable, ValueContext materialPlantTable) {
//				// TODO Auto-generated method stub
//				System.out.println("====Hello World 123456789=====");
//				Session session = aContext.getSession();
//				String userID = session.getUserReference().getUserId();
//				final String plantPk = (String) materialPlantTable.getValue(Path.PARENT.add(com.bct.olammodule.Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_Plant));
//				final String plantTableString = (String) plantTable.get(Paths._Root_Plant._Root_Plant_PlantID);
//				if(plantPk != null && userID.equals("ram")) {
//					System.out.println("=====Hello World========");
//					return plantTableString.equals(plantPk);
//				}
//				
//				return true;
//			}
//		};
//		// tableRefFilter.accept((Adaptation) Paths._Root_MaterialPlant.getPathInSchema().add
//			//	(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_Plant), aContext.getOccurrenceContext());
//		super.handleNewContext(aContext);
//	}
	
	
	

}
