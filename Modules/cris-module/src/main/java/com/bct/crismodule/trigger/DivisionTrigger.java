package com.bct.crismodule.trigger;

import com.bct.crismodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.Request;
import com.onwbp.adaptation.RequestResult;
import com.orchestranetworks.dataservices.rest.Constants.ProcedureContext;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.trigger.AfterCreateOccurrenceContext;
import com.orchestranetworks.schema.trigger.AfterDeleteOccurrenceContext;
import com.orchestranetworks.schema.trigger.AfterModifyOccurrenceContext;
import com.orchestranetworks.schema.trigger.TableTrigger;
import com.orchestranetworks.schema.trigger.TriggerSetupContext;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.ValueContextForUpdate;

public class DivisionTrigger extends TableTrigger{

	@Override
	public void setup(TriggerSetupContext arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void handleAfterDelete(AfterDeleteOccurrenceContext aContext){
		// TODO Auto-generated method stub
		try {
		final ValueContext divContext = aContext.getOccurrenceContext();
		final String divIdentifier = (String) divContext.getValue(Paths._Division._Div_code);
		
		final Adaptation dataset = divContext.getAdaptationInstance();
		final AdaptationTable stationTable = dataset.getTable(Paths._Station.getPathInSchema());
		
		final String predicate = Paths._Station._Tab1_Division.format() +
				"= '" + divIdentifier + "'";
		System.out.println("-------------TIBCO EBX Debugging ---------------"+predicate);
		
//		As against SQL Queries, this approach gets adaptation table. However it is slow as it does not use indexing.
//		Request request = stationTable.createRequest();
//		request.setXPathFilter(predicate);
//		RequestResult requestResult request.execute();
//		
		
		RequestResult requestResult = stationTable.createRequestResult(predicate);
		for(Adaptation station; (station = requestResult.nextAdaptation())!=null;){
			com.orchestranetworks.service.ProcedureContext procedureContext = 
					aContext.getProcedureContext();
			AdaptationName stationName = station.getAdaptationName();
			procedureContext.doDelete(stationName, false);
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@Override
	public void handleAfterModify(AfterModifyOccurrenceContext aContext) throws OperationException {
		// TODO Auto-generated method stub
		try {
		final ValueContext divContext = aContext.getOccurrenceContext();
		final String divIdentifier = (String) divContext.getValue(Paths._Division._Div_code);
		
		final Adaptation dataset = divContext.getAdaptationInstance();
		final AdaptationTable stationTable = dataset.getTable(Paths._Station.getPathInSchema());
		
		String tab = Paths._Station._Tab1_Division.format();
		System.out.println("------------------tab :"+tab +" divIdentifier :"+divIdentifier);
		System.out.println(tab+"="+divIdentifier);
		
		final String predicate = Paths._Station._Tab1_Division.format() +
				"= '" + divIdentifier + "'";
		
		
		System.out.println("----------Tibco ebx ------------"+predicate);
		
		RequestResult requestResult = stationTable.createRequestResult(predicate);
		for(Adaptation station; (station = requestResult.nextAdaptation())!=null;){
			com.orchestranetworks.service.ProcedureContext procedureContext = 
					aContext.getProcedureContext();
			AdaptationName stationName = station.getAdaptationName();
			
			ValueContextForUpdate vcfu = procedureContext.getContext(stationName);
			vcfu.setValue("Broad", Paths._Station._Tab1_Gauge_type);
			vcfu.setValue("True", Paths._Station._Tab1_Halt_flag);
			
			procedureContext.doModifyContent(station, vcfu);
		}		
	
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	}
	@Override
	public void handleAfterCreate(AfterCreateOccurrenceContext aContext) throws OperationException {
		
		Adaptation record = aContext.getAdaptationOccurrence();
		String divName = (String) record.get(Paths._Division._Div_name);
		
		System.out.println("========Division Name==========="+divName);
		
		Adaptation dataset = record.getContainer();
		AdaptationTable stationTable = dataset.getTable(Paths._Station.getPathInSchema());
		
		com.orchestranetworks.service.ProcedureContext pContext = aContext.getProcedureContext();
		
		
		String query = "Select s.\"$adaptation\" from \"/root/station\" s where s.tab1.stn_name = ?";
		Query<Tuple> queryTuple = dataset.createQuery(query);
		queryTuple.setParameter(0, divName);
		
		QueryResult<Tuple> stationRecords = queryTuple.getResult();
		
		Adaptation stationRecord = null;
		for (Tuple result : stationRecords) {
			stationRecord = (Adaptation) result.get(0);
		}
		
		if(stationRecord!=null) {
			ValueContextForUpdate vcfu = pContext.getContext(stationRecord.getAdaptationName());
			vcfu.setValue("Pls change", Paths._Station._Tab1_Stn_name);
			
			pContext.doModifyContent(stationRecord, vcfu);
		}
		else {
			
		}
	}
	
	
	
	
}
