package com.bct.complaintmodule.procedure;

import java.util.Locale;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.TableRefFilter;
import com.orchestranetworks.schema.TableRefFilterContext;
import com.orchestranetworks.service.Procedure;
import com.orchestranetworks.service.ProcedureContext;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.service.ValueContextForUpdate;

public class ComplaintEntryProcedure implements Procedure{

	private AdaptationTable adaptationTable;
	private Adaptation adaptationRecord;
	
	public ComplaintEntryProcedure(AdaptationTable adaptationTable,Adaptation adaptationRecord) {
		super();
		this.adaptationTable = adaptationTable;
		this.adaptationRecord = adaptationRecord;
	}

	@Override
	public void execute(ProcedureContext pContext) throws Exception {
		// TODO Auto-generated method stub
		//ValueContextForUpdate vcfu = pContext.getContextForNewOccurrence(adaptationRecord, adaptationTable);
		System.out.println("-----Inside procedure execution--------");
		
	}

}
