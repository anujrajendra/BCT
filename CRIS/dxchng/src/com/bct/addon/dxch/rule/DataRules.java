package com.bct.addon.dxch.rule;

import java.util.ArrayList;
import java.util.List;

import com.bct.addon.dxch.dto.ColumnMetadata;
import com.bct.addon.dxch.path.DXchngPath;
import com.bct.addon.dxch.util.DatabaseUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.orchestranetworks.addon.dataexchange.DataExchangeException;
import com.orchestranetworks.addon.dataexchange.sql.SQLDataType;
import com.orchestranetworks.addon.dataexchange.transformation.ApplicationType;
import com.orchestranetworks.addon.dex.DataExchangeServiceFactory;
import com.orchestranetworks.addon.dex.DataExchangeSpec;
import com.orchestranetworks.addon.dex.configuration.ImportMode;
import com.orchestranetworks.addon.dex.configuration.JNDIDataSource;
import com.orchestranetworks.addon.dex.configuration.SQLImportConfigurationSpec;
import com.orchestranetworks.addon.dex.mapping.ApplicationMapping;
import com.orchestranetworks.addon.dex.mapping.CommonApplication;
import com.orchestranetworks.addon.dex.mapping.EBXField;
import com.orchestranetworks.addon.dex.mapping.EBXTable;
import com.orchestranetworks.addon.dex.mapping.FieldMapping;
import com.orchestranetworks.addon.dex.mapping.FieldMappingList;
import com.orchestranetworks.addon.dex.mapping.SQLField;
import com.orchestranetworks.addon.dex.mapping.SQLTable;
import com.orchestranetworks.addon.dex.mapping.TableMapping;
import com.orchestranetworks.addon.dex.mapping.TableMappingList;
import com.orchestranetworks.addon.dex.result.DataExchangeResult;
import com.orchestranetworks.addon.dex.result.DataExchangeResult.SQLImport;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Session;

/**
 *
 * @author mostafashokiel
 */
public class DataRules {

	public void importData(final Adaptation record, final List<ColumnMetadata> columns) throws OperationException {
		try {
			String datasetName = record.getString(DXchngPath._DataSourceTable._TableName);

			// LoggingCategory.getWorkflow().info("===Inside import Data====");
			// LoggingCategory.getWorkflow().info("===Dataset Name====" + datasetName);

			String schemaName = "";
			if (datasetName.contains(".")) {
				schemaName = datasetName.split("\\.")[0];
				datasetName = datasetName.split("\\.")[1];
			}
			String targetTableName = (String) record.get(Path.parse("./tableName"));

			// System.out.println("===Table Name===" + targetTableName);

			Path tablePath;
			tablePath = Path.parse("/root/" + targetTableName.replaceFirst("public\\.", ""));

			System.out.println("===target table path===" + "/root/" + targetTableName.replaceFirst("public\\.", ""));

			if (targetTableName.contains("details"))
				tablePath = Path.parse("/root/Loco_Reconciliation_Details");
			else
				tablePath = Path.parse("/root/Loco_Reconciliation_Summary");

			Repository repository = Repository.getDefault();
			Session session = repository.createSessionFromLoginPassword("admin", "admin"); // TODO get this info
																							// dynamically

			/*
			 * AdaptationHome dataSpaceName = RepositoryUtils.toDataSpace(repository,
			 * "Child_"+datasetName); Adaptation dataSetName =
			 * RepositoryUtils.toDataSet(dataSpaceName, datasetName); AdaptationTable
			 * tbconfig = RepositoryUtils.toTable(dataSetName, "/root/"+datasetName);
			 */

//			HomeKey dataspace = HomeKey.forBranchName(datasetName + "_TEMP");
//			AdaptationName dataset = AdaptationName.forName(datasetName);

			// Output table

			HomeKey dataspace = HomeKey.forBranchName("loco_reconciliation");
			AdaptationName dataset = AdaptationName.forName("loco_reconciliation");

			AdaptationHome customerHome = repository.lookupHome(dataspace);
			Adaptation customerDataset = customerHome.findAdaptationOrNull(dataset);

			AdaptationTable ctable = customerDataset.getTable(tablePath);

			// Get Database Configurations
			SchemaNode connectionNode = record.getSchemaNode().getNode(DXchngPath._DataSourceTable._Database);
			Adaptation connectionRecord = connectionNode.getFacetOnTableReference().getLinkedRecord(record);

			JNDIDataSource jndiDataSource = DatabaseUtils.getJNDIDataSource(connectionRecord, datasetName);

			// Define the application mapping
			CommonApplication sourceApplication = new CommonApplication(datasetName, ApplicationType.DEFAULT_SQL);
			CommonApplication targetApplication = new CommonApplication(datasetName, ApplicationType.EBX);

			List<SQLField> sqlFields = new ArrayList<SQLField>();

			List<EBXField> ebxFields = new ArrayList<EBXField>();
			FieldMappingList<SQLField, EBXField> fieldMappingList = new FieldMappingList<SQLField, EBXField>();
			TableMappingList<SQLField, EBXField> tableMappingList = new TableMappingList<SQLField, EBXField>();

			SQLTable sqlTable = new SQLTable(schemaName, datasetName, sqlFields);
			SQLImportConfigurationSpec sqlImportConfigurationSpec = new SQLImportConfigurationSpec(ctable,
					jndiDataSource, sqlTable, session);
			sqlImportConfigurationSpec.setImportMode(ImportMode.REPLACE_ALL_CONTENT);

			AdaptationTable currentTable = sqlImportConfigurationSpec.getCurrentDataset().getTable(tablePath);

			this.generateMapping(columns, sqlFields, ebxFields, fieldMappingList, currentTable, tablePath);

			EBXTable ebxTable = new EBXTable(currentTable, ebxFields);

			tableMappingList.add(new TableMapping<SQLField, EBXField>(sqlTable, ebxTable, fieldMappingList));

			ApplicationMapping<SQLField, EBXField> applicationMapping = new ApplicationMapping<SQLField, EBXField>(
					sourceApplication, targetApplication, tableMappingList);

			// Define the EBX Data Exchange Add-on specification:
			DataExchangeSpec dataExchangeSpec = new DataExchangeSpec();
			dataExchangeSpec.setApplicationMapping(applicationMapping);
			dataExchangeSpec.setConfigurationSpec(sqlImportConfigurationSpec);

			// System.out.println("Bath Import Start =============>" + LocalTime.now());

			DataExchangeResult.SQLImport result = (SQLImport) DataExchangeServiceFactory.getDataExchangeService()
					.execute(dataExchangeSpec);

			// System.out.println("Bath Import Completed =============>" + LocalTime.now());

			if (result.getErrorMessages() == null) {
				return;
			}

		} catch (DataExchangeException ex) {
			// TODO: handle exception
		}

	}

	/**
	 * @param column
	 * @param sqlFields
	 * @param ebxFields
	 * @param tableMappingList
	 *
	 * @author mostafashokiel
	 * @param currentTable
	 * @throws DataExchangeException
	 */
	private void generateMapping(final List<ColumnMetadata> columns, final List<SQLField> sqlFields,
			final List<EBXField> ebxFields, final FieldMappingList<SQLField, EBXField> fieldMapping,
			final AdaptationTable currentTable, final Path tablePath) throws DataExchangeException {

		for (ColumnMetadata column : columns) {
			LoggingCategory.getWorkflow().info("===Inside import Data - generateMapping()====");
			// Create SQL Field
			SQLField sqlField = new SQLField(column.getName(), column.getName(),
					this.getSQLDataType(column.getDataType()));
			sqlFields.add(sqlField);

			// Create EBX Field
			EBXField eBXField = new EBXField(
					currentTable.getTableOccurrenceRootNode().getNode(tablePath.add(column.getName())));
			ebxFields.add(eBXField);

			// Create SQL-EBX mapping
			FieldMapping<SQLField, EBXField> idFieldMapping = new FieldMapping<SQLField, EBXField>(sqlField, eBXField);
			fieldMapping.add(idFieldMapping);

		}

	}

	/**
	 * @param dataType
	 * @return
	 *
	 * @author mostafashokiel
	 */
	private SQLDataType getSQLDataType(final String dataType) {
		return SQLDataType.parse(Integer.parseInt(dataType));
	}

}
