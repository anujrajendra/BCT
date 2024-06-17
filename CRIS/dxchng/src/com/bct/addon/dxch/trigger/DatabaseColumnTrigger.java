package com.bct.addon.dxch.trigger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.bct.addon.dxch.dto.*;
import com.bct.addon.dxch.procedure.CreationofDSnDMProcedure;
import com.bct.addon.dxch.rule.*;
import com.bct.scad.mdm.common.utilities.RepositoryUtils;
import com.onwbp.adaptation.AdaptationHome;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.trigger.*;
import com.orchestranetworks.service.*;

/**
 *
 * @author mostafashokiel
 */
public class DatabaseColumnTrigger extends TableTrigger {

	DatabaseRules dbRules = new DatabaseRules();
	DataModelRules datamodelRules = new DataModelRules();
	DataRules dataRules = new DataRules();

	@Override
	public void setup(final TriggerSetupContext arg0) {

	}

	/*
	 * @Override public void handleAfterModify(final AfterModifyOccurrenceContext
	 * context) throws OperationException { super.handleAfterModify(context); //
	 * Create Columns List<ColumnMetadata> columns =
	 * this.dbRules.createOrUpdateColumns(context.getAdaptationOccurrence(),
	 * context.getProcedureContext());
	 * 
	 * // Create XSD
	 * this.datamodelRules.createXSDContent(context.getAdaptationOccurrence(),
	 * context.getProcedureContext(), columns);
	 * 
	 * // TODO Create DataSpace and DataSet based on Generated XSD file
	 * 
	 * // Import Data this.dataRules.importData(context.getAdaptationOccurrence(),
	 * columns); }
	 */

	@Override
	public void handleAfterCreate(final AfterCreateOccurrenceContext context) throws OperationException {
		super.handleAfterCreate(context);
		List<ColumnMetadata> columns = this.dbRules.createOrUpdateColumns(context.getAdaptationOccurrence(),
				context.getProcedureContext());

		String xsd=this.datamodelRules.createXSDContent(context.getAdaptationOccurrence(), context.getProcedureContext(), columns);
		
		

		// Import Data
		//this.dataRules.importData(context.getAdaptationOccurrence(), columns);

	}

}
