package com.bct.addon.dxch.util;

import java.io.*;
import java.util.*;

import com.bct.addon.dxch.datamodelgenerator.dao.*;
import com.onwbp.adaptation.*;
import com.onwbp.base.text.*;
import com.onwbp.base.text.bean.*;
import com.orchestranetworks.addon.*;
import com.orchestranetworks.addon.adix.dataexchange.datamodelgenerator.i18n.*;
import com.orchestranetworks.addon.adix.dataexchange.datamodelgenerator.model.*;
import com.orchestranetworks.addon.adix.dataexchange.i18n.*;
import com.orchestranetworks.addon.dml.DataModelException;
import com.orchestranetworks.addon.dml.DataModelField;
import com.orchestranetworks.addon.dml.DataModelFieldBridge;
import com.orchestranetworks.addon.dml.DataModelPaths;
import com.orchestranetworks.addon.dml.DataModelTable;
import com.orchestranetworks.addon.dml.DataModelTableBridge;
import com.orchestranetworks.addon.dml.SourceStructure;
import com.orchestranetworks.addon.dml.SourceStructureBridge;
import com.orchestranetworks.addon.utils.*;
import com.orchestranetworks.schema.*;
import com.orchestranetworks.service.*;

public final class DMLUtils {
	private static final DMLUtils INSTANCE = new DMLUtils();

	public static DMLUtils getInstance() {
		return DMLUtils.INSTANCE;
	}

	public String normalizeName(final String name) {
		return AddonStringUtils.collapse(name);
	}

	public String normalizeXSDName(final String name) {
		String newName = name.replaceAll("[^-a-zA-Z0-9_.]", "_");
		if (newName.matches("[-0-9.].*")) {
			return newName.replaceFirst("[-0-9.]", "_");
		}
		return newName;
	}

	public boolean isResultEmpty(final RequestResult requestResult) {
		try {
			return (requestResult.nextAdaptation() == null);
		} finally {
			requestResult.close();
		}
	}

	public LabelDescription generateLabel(final String value, final List<Locale> schemaLocales) {
		LabelDescription labelDescription = new LabelDescription();
		for (Locale locale : schemaLocales) {
			labelDescription.setLocalizedDocumentations(locale, value, "");
		}
		return labelDescription;
	}

	public String generateRequestByForeignKey(final Path fkPath, final PrimaryKey valueFK) {
		StringBuilder tableRequest = new StringBuilder();
		tableRequest.append(fkPath.format());
		tableRequest.append(" = ");
		tableRequest.append(XPathExpressionHelper.encodeLiteralStringWithDelimiters(valueFK.format()));
		return tableRequest.toString();
	}

	public Adaptation createTableConfiguration(final ProcedureContext procedureContext,
			final AdaptationTable tableConfiguration, final PrimaryKey datamodelRecord, final DataModelTable dmlTable)
			throws ConstraintViolationException, OperationException {
		ValueContextForUpdate valueContextForUpdate = procedureContext.getContextForNewOccurrence(tableConfiguration);
		valueContextForUpdate.setValue(dmlTable.getName(), DataModelPaths._Table._Name);
		valueContextForUpdate.setValue(datamodelRecord.format(), DataModelPaths._Table._FKDataModel);
		valueContextForUpdate.setValue(DataModelTableBridge.INSTANCE.getXSDName(dmlTable),
				DataModelPaths._Table._XsdName);
		valueContextForUpdate.setValue(DataModelTableBridge.INSTANCE.getXSDPath(dmlTable),
				DataModelPaths._Table._XsdPath);
		valueContextForUpdate.setValue(DataModelTableBridge.INSTANCE.getGroupPath(dmlTable),
				DataModelPaths._Table._GroupPath);
		valueContextForUpdate.setValue(
				DMLUtils.getInstance().generateLabelAndDescription(dmlTable.getLabel(), dmlTable.getDescription(),
						tableConfiguration.getTableNode().getSchemaLocales()),
				DataModelPaths._Table._LabelAndDescription);
		return procedureContext.doCreateOccurrence(valueContextForUpdate, tableConfiguration);
	}

	public Adaptation createFieldConfiguration(final ProcedureContext procedureContext,
			final AdaptationTable fieldConfiguration, final PrimaryKey tableRecord, final DataModelField dmlField)
			throws OperationException, UnavailableContentError, PathAccessException, IllegalArgumentException,
			DataModelException {
		ValueContextForUpdate valueContextForUpdate = procedureContext.getContextForNewOccurrence(fieldConfiguration);
		valueContextForUpdate.setValue(dmlField.getName(), DataModelPaths._Field._Name);
		valueContextForUpdate.setValue(
				DMLUtils.getInstance().generateLabelAndDescription(dmlField.getLabel(), dmlField.getDescription(),
						fieldConfiguration.getTableNode().getSchemaLocales()),
				DataModelPaths._Field._LabelAndDescription);
		valueContextForUpdate.setValue(tableRecord.format(), DataModelPaths._Field._FKTable);
		valueContextForUpdate.setValue(DataModelFieldBridge.getGroupPath(dmlField), DataModelPaths._Field._GroupPath);
		valueContextForUpdate.setValue(Boolean.valueOf(DataModelFieldBridge.isPrimaryKey(dmlField)),
				DataModelPaths._Field._IsPrimaryKey);
		valueContextForUpdate.setValue(FieldType.parse(dmlField.getSchemaTypeName()).getValueInExcel(),
				DataModelPaths._Field._Type);
		valueContextForUpdate.setValue(DataModelFieldBridge.getXsdPath(dmlField), DataModelPaths._Field._XsdPath);
		valueContextForUpdate.setValue(DataModelFieldBridge.getXsdName(dmlField), DataModelPaths._Field._XsdName);
		return procedureContext.doCreateOccurrence(valueContextForUpdate, fieldConfiguration);
	}

	public LabelDescription generateLabelAndDescription(final UserMessage label, final UserMessage description,
			final List<Locale> schemaLocales) {
		LabelDescription labelDescription = new LabelDescription();
		labelDescription.setLocalizedDocumentations(schemaLocales, label, description);
		return labelDescription;
	}

	public void convertDataModelRecordToBean(final Adaptation dmlDataSet, final SourceStructure sourceStructure,
			final Adaptation datamodelRecord) throws PathAccessException, DataModelException {
		PrimaryKey pkDataModelRecord = this.putInfoToStructureBean(sourceStructure, datamodelRecord);
		AdaptationTable dmlTableConfiguration = dmlDataSet.getTable(DataModelPaths._Table.getPathInSchema());
		AdaptationTable dmlFieldConfiguration = dmlDataSet.getTable(DataModelPaths._Field.getPathInSchema());
		String tableRequest = DMLUtils.getInstance().generateRequestByForeignKey(DataModelPaths._Table._FKDataModel,
				pkDataModelRecord);
		RequestResult tableResult = this.getSortedRequestResult(dmlTableConfiguration, tableRequest,
				DataModelPaths._Table._Order, true);
		List<DataModelTable> dataModelTables = new ArrayList<>();
		try {
			Adaptation tableRecord;
			while ((tableRecord = tableResult.nextAdaptation()) != null) {
				DataModelTable dataModelTable = new DataModelTable(tableRecord.getString(DataModelPaths._Table._Name));
				this.putInfoToDataModelTableBean(tableRecord, dataModelTable);
				List<DataModelField> dataModelFields = new ArrayList<>();
				String fieldRequest = DMLUtils.getInstance().generateRequestByForeignKey(DataModelPaths._Field._FKTable,
						tableRecord

								.getOccurrencePrimaryKey());
				RequestResult fieldResult = this.getSortedRequestResult(dmlFieldConfiguration, fieldRequest,
						DataModelPaths._Field._Order, true);
				try {
					Adaptation fieldRecord;
					while ((fieldRecord = fieldResult.nextAdaptation()) != null) {
						DataModelField dataModelField = new DataModelField(
								fieldRecord.getString(DataModelPaths._Field._Name), FieldType
										.parse(fieldRecord.getString(DataModelPaths._Field._Type)).getSchemaTypeName());
						this.putInfoToDataModelFieldBean(fieldRecord, dataModelField);
						dataModelFields.add(dataModelField);
					}
					dataModelTable.setFields(dataModelFields);
					dataModelTables.add(dataModelTable);
				} finally {
					fieldResult.close();
				}
			}
		} finally {
			sourceStructure.setTables(dataModelTables);
			tableResult.close();
		}
	}

	private PrimaryKey putInfoToStructureBean(final SourceStructure sourceStructure, final Adaptation datamodelRecord) {
		LabelDescription labelDescription = (LabelDescription) datamodelRecord
				.get(DataModelPaths._DataModel._LabelAndDescription);
		if (labelDescription != null) {
			sourceStructure.setLabel(labelDescription.getLabelAsUserMessage());
			sourceStructure.setDescription(labelDescription.getDescriptionAsUserMessage());
		}
		PrimaryKey pkDataModelRecord = datamodelRecord.getOccurrencePrimaryKey();
		SourceStructureBridge.INSTANCE.setPrimarykey(sourceStructure, pkDataModelRecord);
		return pkDataModelRecord;
	}

	private void putInfoToDataModelTableBean(final Adaptation tableRecord, final DataModelTable dataModelTable) {
		LabelDescription labelDescription = (LabelDescription) tableRecord
				.get(DataModelPaths._Table._LabelAndDescription);
		if (labelDescription != null) {
			dataModelTable.setLabel(labelDescription.getLabelAsUserMessage());
			dataModelTable.setDescription(labelDescription.getDescriptionAsUserMessage());
		}
		DataModelTableBridge.INSTANCE.setGroupPath(dataModelTable,
				tableRecord.getString(DataModelPaths._Table._GroupPath));
		DataModelTableBridge.INSTANCE.setOrder(dataModelTable, tableRecord.get_int(DataModelPaths._Table._Order));
		DataModelTableBridge.INSTANCE.setXSDName(dataModelTable, tableRecord.getString(DataModelPaths._Table._XsdName));
		DataModelTableBridge.INSTANCE.setXSDPath(dataModelTable, tableRecord.getString(DataModelPaths._Table._XsdPath));
	}

	private void putInfoToDataModelFieldBean(final Adaptation fieldRecord, final DataModelField dataModelField) {
		LabelDescription labelDescription = (LabelDescription) fieldRecord
				.get(DataModelPaths._Field._LabelAndDescription);
		if (labelDescription != null) {
			dataModelField.setLabel(labelDescription.getLabelAsUserMessage());
			dataModelField.setDescription(labelDescription.getDescriptionAsUserMessage());
		}
		DataModelFieldBridge.setGroupPath(dataModelField, fieldRecord.getString(DataModelPaths._Field._GroupPath));
		DataModelFieldBridge.setOrder(dataModelField, fieldRecord.get_int(DataModelPaths._Field._Order));
		DataModelFieldBridge.setXsdName(dataModelField, fieldRecord.getString(DataModelPaths._Field._XsdName));
		DataModelFieldBridge.setXsdPath(dataModelField, fieldRecord.getString(DataModelPaths._Field._XsdPath));
		DataModelFieldBridge.setPrimaryKey(dataModelField, fieldRecord

				.get_boolean(DataModelPaths._Field._IsPrimaryKey));
	}

	public LabelDescription defineLabelAndDescription(final UserMessage label, final UserMessage description) {
		LabelDescription labelDescription = new LabelDescription();
		labelDescription.setLocalizedDocumentationsForEBXLocales(label, description);
		return labelDescription;
	}

	public DMLTable getTableNode(final String tableName) {
		return new DMLTable(tableName, tableName, null, Path.ROOT, 0);
	}

	public DMLTable getTableNode(final DataModelTable table) {
		String groupPathValue = DataModelTableBridge.INSTANCE.getXSDPath(table);
		Path groupPath = AddonStringUtils.isEmpty(groupPathValue) ? Path.ROOT : Path.parse(groupPathValue);
		return new DMLTable(table.getName(), DataModelTableBridge.INSTANCE.getXSDName(table),
				this.defineLabelAndDescription(table.getLabel(), table.getDescription()), groupPath,
				DataModelTableBridge.INSTANCE

						.getOrder(table));
	}

	public DMLField getFieldNode(final DataModelField field) throws DataModelException {
		String groupPathValue = DataModelFieldBridge.getXsdPath(field);
		Path groupPath = AddonStringUtils.isEmpty(groupPathValue) ? Path.ROOT : Path.parse(groupPathValue);
		return new DMLField(field.getName(), DataModelFieldBridge.getXsdName(field),
				this.defineLabelAndDescription(field.getLabel(), field.getDescription()),
				FieldType.parse(field.getSchemaTypeName()), DataModelFieldBridge.isPrimaryKey(field), groupPath,

				DataModelFieldBridge.getOrder(field));
	}

	public File generateFile(final String dataModelName, final String xsdContent) throws DataModelException {
		try {
			OutputStreamWriter writer = null;
			FileOutputStream fileStream = null;
			File exportedFile = this.createExportedFile(dataModelName + dataModelName);
			try {
				fileStream = new FileOutputStream(exportedFile);
				writer = new OutputStreamWriter(fileStream, "UTF-8");
				writer.write(xsdContent);
				writer.flush();
				return exportedFile;
			} finally {
				if (fileStream != null) {
					fileStream.close();
				}
				if (writer != null) {
					writer.close();
				}
			}
		} catch (Exception ex) {
			throw new DataModelException(DataModelerErrorMessages.get_Generate_Exceptions_ErrorGeneratingXSDFile(ex));
		}
	}

	private File createExportedFile(final String fileName) throws DataModelException {
		File dataModelFolder = this.createFolder(AddonFileUtils.getTemporaryRootForModule("ebx-addon-adix"),
				"datamodel");
		StringBuilder tmpFolderName = new StringBuilder();
		tmpFolderName.append("datamodel");
		tmpFolderName.append("_");
		tmpFolderName.append(UUID.randomUUID().toString());
		File tmpFolder = this.createFolder(dataModelFolder, tmpFolderName.toString());
		return this.createFile(tmpFolder, fileName);
	}

	private File createFolder(final File parentFolder, final String folderName) throws DataModelException {
		try {
			File newFolder = new File(parentFolder, folderName);
			if (newFolder.exists()) {
				return newFolder;
			}
			boolean createdPermission = newFolder.mkdir();
			if (!createdPermission) {
				throw new DataModelException(DataModelerErrorMessages.get_Exceptions_UnableToWriteFile(null));
			}
			return newFolder;
		} catch (SecurityException ex) {
			throw new DataModelException(DataModelerErrorMessages.get_Exceptions_UnableToWriteFile(ex));
		}
	}

	private File createFile(final File parentFolder, final String fileName) throws DataModelException {
		try {
			File newFile = new File(parentFolder, DMLUtils.getInstance().normalizeXSDName(fileName));
			if (newFile.exists()) {
				return newFile;
			}
			boolean permissionCreated = newFile.createNewFile();
			if (!permissionCreated) {
				throw new DataModelException(DataExchangeErrorMessages.get_Exceptions_UnableToWriteFile(null));
			}
			return newFile;
		} catch (IOException ex) {
			throw new DataModelException(DataExchangeErrorMessages.get_Exceptions_UnableToWriteFile(ex));
		} catch (SecurityException ex) {
			throw new DataModelException(DataModelerErrorMessages.get_Exceptions_UnableToWriteFile(ex));
		}
	}

	private RequestResult getSortedRequestResult(final AdaptationTable containerTable, final String request,
			final Path sortedPath, final boolean isASC) {
		RequestSortCriteria sortCriteria = new RequestSortCriteria();
		sortCriteria.add(sortedPath, isASC);
		return containerTable.createRequestResult(request, sortCriteria);
	}
}
