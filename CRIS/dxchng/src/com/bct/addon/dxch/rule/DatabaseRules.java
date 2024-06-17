package com.bct.addon.dxch.rule;

import java.util.*;

import com.bct.addon.dxch.dto.*;
import com.bct.addon.dxch.path.*;
import com.bct.addon.dxch.util.*;
import com.onwbp.adaptation.*;
import com.onwbp.org.apache.commons.lang3.*;
import com.orchestranetworks.instance.*;
import com.orchestranetworks.schema.*;
import com.orchestranetworks.service.*;

/**
 *
 * @author mostafashokiel
 */
public class DatabaseRules {

	/**
	 * @param adaptation
	 * @param procedureContext
	 *
	 * @author mostafashokiel
	 */
	public List<ColumnMetadata> createOrUpdateColumns(final Adaptation adaptation,
			final ProcedureContext procedureContext) {

		// Get All Table Columns

		String tableName = adaptation.getString(DXchngPath._DataSourceTable._TableName);

		SchemaNode connectionNode = adaptation.getSchemaNode().getNode(DXchngPath._DataSourceTable._Database);
		Adaptation connectionRecord = connectionNode.getFacetOnTableReference().getLinkedRecord(adaptation);

		List<ColumnMetadata> columns = DatabaseUtils.getColumns(connectionRecord, tableName);

		if ((columns != null) && (columns.size() > 0)) {
			// Delete All Columns Records
			this.deleteAllColumnData(adaptation.createValueContext(), procedureContext);

			// Create a new ones
			this.createColumnsRecord(adaptation.createValueContext(), procedureContext, columns);

		}
		return columns;

	}
	
	public List<ColumnMetadata> createOrUpdateColumnsScriptTask(final Adaptation adaptation,
			final ProcedureContext procedureContext) {

		// Get All Table Columns

		String tableName = adaptation.getString(DXchngPath._DataSourceTable._TableName);

		SchemaNode connectionNode = adaptation.getSchemaNode().getNode(DXchngPath._DataSourceTable._Database);
		Adaptation connectionRecord = connectionNode.getFacetOnTableReference().getLinkedRecord(adaptation);

		List<ColumnMetadata> columns = DatabaseUtils.getColumns(connectionRecord, tableName);

		
		return columns;

	}

	/**
	 * @param occurrenceContext
	 * @param procedureContext
	 *
	 * @author mostafashokiel
	 */
	private void deleteAllColumnData(final ValueContext occurrenceContext, final ProcedureContext procedureContext) {
		// TODO NEED TO BE IMPLEMENTED
	}

	private void createColumnsRecord(final ValueContext context, final ProcedureContext procedureContext,
			final List<ColumnMetadata> columns) {

		ValueContextForUpdate vcfu = null;
		Integer tableId = (Integer) context.getValue(DXchngPath._DataSourceTable._TableID);

		AdaptationTable adaptationTable = context.getAdaptationInstance()
				.getTable(DXchngPath._ColumnsSpecifications.getPathInSchema());

		for (ColumnMetadata column : columns) {

			vcfu = procedureContext.getContextForNewOccurrence(adaptationTable);
			vcfu.setValue(tableId.toString(), DXchngPath._ColumnsSpecifications._Table);

			this.setRecord(vcfu, column.getName(), DXchngPath._ColumnsSpecifications._Name);
			this.setRecord(vcfu, column.getDataType(), DXchngPath._ColumnsSpecifications._ColumnType);
			this.setRecord(vcfu, column.getDefaultValue(), DXchngPath._ColumnsSpecifications._ColumnDefaultValue);
			this.setRecord(vcfu, column.getDescription(), DXchngPath._ColumnsSpecifications._ColumnDescription);
			vcfu.setValue(column.getDecimalDigits(), DXchngPath._ColumnsSpecifications._DecimalDigits);
			vcfu.setValue(column.getIndex(), DXchngPath._ColumnsSpecifications._ColumnIndex);
			vcfu.setValue(column.getSize(), DXchngPath._ColumnsSpecifications._ColumnSize);
			vcfu.setValue(column.isNullable(), DXchngPath._ColumnsSpecifications._Nullable);
			vcfu.setValue(column.isPrimaryKey(), DXchngPath._ColumnsSpecifications._IsPrimaryKey);

			try {
				procedureContext.doCreateOccurrence(vcfu, adaptationTable);
			} catch (ConstraintViolationException | OperationException ex) {
				throw new RuntimeException(ex);
			}
		}

	}

	private void setRecord(final ValueContextForUpdate vcfu, final String sourceValue, final Path destinationPath) {
		if (StringUtils.equals("null", sourceValue)) {
			vcfu.setValue("", destinationPath);
		} else if (StringUtils.isNotBlank(sourceValue)) {
			vcfu.setValue(sourceValue, destinationPath);
		}
	}

}
