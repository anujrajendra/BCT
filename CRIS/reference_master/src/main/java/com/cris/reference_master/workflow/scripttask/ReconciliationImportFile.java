package com.cris.reference_master.workflow.scripttask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.cris.reference_master.logger.ModuleLogger;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.orchestranetworks.addon.dex.CSVImportDataExchangeHelperContext;
import com.orchestranetworks.addon.dint.DataIntegrationException;
import com.orchestranetworks.addon.dint.DataIntegrationExecutionResults;
import com.orchestranetworks.addon.dint.DataIntegrationExecutor;
import com.orchestranetworks.addon.dint.template.CSVImportTemplateSpec;
import com.orchestranetworks.addon.dint.template.EBXTransferTemplateSpec;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Session;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class ReconciliationImportFile extends ScriptTaskBean {
	// private LoggingCategory logger = ModuleLogger.getModuleLogger(); //
	// Initialize here
	private String dataspace;
	private String dataset;
	private String ImportTableName;
	private String ImportMappingName;
	private String ImportFilePath;
	private String TransfertemplateId;
	private String Transfer_Master_templateId;
	private String Backup_File_Path;

	@Override
	public void executeScript(ScriptTaskBeanContext context) throws OperationException {
		Repository repository = Repository.getDefault();
		final AdaptationHome dataSpace = toDataSpace(repository, this.dataspace);
		final Adaptation dataSet = toDataSet(dataSpace, this.dataset);
		final AdaptationTable table = toTable(dataSet, this.ImportTableName);

		// Log the parameters being used in the script
		logParameters();

		File importFileName = new File(ImportFilePath);

		File BackupFilePath = new File(Backup_File_Path);

		CSVImportDataExchangeHelperContext dexhelperContext = new CSVImportDataExchangeHelperContext(ImportMappingName,
				table, context.getSession());
		dexhelperContext.setImportedFile(importFileName);

		ModuleLogger.logger
				.info("*******************************************************************************************");

		try {
			// Transfer the mdm data to stage table
			transferDataMaster(context.getSession());
			// Perform the data import
			importData(importFileName, context.getSession());
			// Backup the imported file
			backupImportedFile(importFileName, BackupFilePath);
			// Perform the data transfer
			transferData(context.getSession());
		} catch (Exception e) {
			ModuleLogger.logger.error("Error during data exchange: " + e.getMessage(), e);
		}
	}

	// Log the parameters used for the data exchange
	private void logParameters() {
		ModuleLogger.logger.info("Executing ReconciliationImportFile script with the following parameters:");
		ModuleLogger.logger.info("Dataspace: " + dataspace);
		ModuleLogger.logger.info("Dataset: " + dataset);
		ModuleLogger.logger.info("Table: " + ImportTableName);
		ModuleLogger.logger.info("PreferenceName: " + ImportMappingName);
		ModuleLogger.logger.info("FileName: " + ImportFilePath);
		ModuleLogger.logger.info("FileName: " + Backup_File_Path);
	}

	// Perform the data import using the Data Exchange Service
	private void importData(File importFile, Session session) throws IOException {
		CSVImportTemplateSpec ebxImportTemplateSpec = new CSVImportTemplateSpec(ImportMappingName, importFile, session);
		try {
			DataIntegrationExecutionResults dataTransferResults = DataIntegrationExecutor.getInstance()
					.execute(ebxImportTemplateSpec);
			// ModuleLogger.logger.info("Import Successful");
			// ModuleLogger.logger.info("*******************************************************************************************");

		} catch (DataIntegrationException e) {
			ModuleLogger.logger.error("Error during data transfer: " + e.getMessage(), e);
		}
	}

	// Backup the imported file to the backup folder
	private void backupImportedFile(File importFile, File backupFolder) throws IOException {
		java.nio.file.Path sourcePath = importFile.toPath();
		java.nio.file.Path backupFolderPath = backupFolder.toPath();

		if (!Files.exists(backupFolderPath)) {
			Files.createDirectory(backupFolderPath);
		}

		java.nio.file.Path backupFilePath = backupFolderPath.resolve(importFile.getName());
		Files.move(sourcePath, backupFilePath, StandardCopyOption.REPLACE_EXISTING);

		ModuleLogger.logger.info("File moved to backup folder: " + backupFilePath.toString());
	}

	// Perform the data transfer using the specified template
	private void transferDataMaster(Session session) {
		EBXTransferTemplateSpec ebxTransferTemplateSpec = new EBXTransferTemplateSpec(Transfer_Master_templateId,
				session);
		try {
			DataIntegrationExecutionResults dataTransferResults = DataIntegrationExecutor.getInstance()
					.execute(ebxTransferTemplateSpec);
		} catch (DataIntegrationException e) {
			ModuleLogger.logger.error("Error during data transfer: " + e.getMessage(), e);
		}
	}

	// Perform the data transfer using the specified template
	private void transferData(Session session) {
		EBXTransferTemplateSpec ebxTransferTemplateSpec = new EBXTransferTemplateSpec(TransfertemplateId, session);
		try {
			DataIntegrationExecutionResults dataTransferResults = DataIntegrationExecutor.getInstance()
					.execute(ebxTransferTemplateSpec);
		} catch (DataIntegrationException e) {
			ModuleLogger.logger.error("Error during data transfer: " + e.getMessage(), e);
		}
	}

	public static AdaptationHome toDataSpace(final Repository repository, final String dataSpaceName)
			throws OperationException {
		try {
			final HomeKey dataSpaceKey = HomeKey.forBranchName(dataSpaceName);
			final AdaptationHome dataSpace = repository.lookupHome(dataSpaceKey);
			if (dataSpace == null)
				throw new IllegalArgumentException();
			return dataSpace;
		} catch (final Exception exception) {
			throw exception;
		}
	}

	public Adaptation toDataSet(final AdaptationHome dataSpace, final String dataSetName) throws OperationException {
		try {
			final AdaptationName dataSetKey = AdaptationName.forName(dataSetName);
			final Adaptation dataSet = dataSpace.findAdaptationOrNull(dataSetKey);
			if (dataSet == null)
				throw new IllegalArgumentException();
			return dataSet;
		} catch (final Exception exception) {
			throw exception;
		}
	}

	public AdaptationTable toTable(final Adaptation dataSet, final String tablePath) throws OperationException {
		try {
			final AdaptationTable table = dataSet.getTable(Path.parse(tablePath));
			return table;
		} catch (final Exception exception) {
			throw exception;
		}
	}

	public String getDataspace() {
		return dataspace;
	}

	public void setDataspace(String dataspace) {
		this.dataspace = dataspace;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public String getImportTableName() {
		return ImportTableName;
	}

	public void setImportTableName(String importTableName) {
		ImportTableName = importTableName;
	}

	public String getImportMappingName() {
		return ImportMappingName;
	}

	public void setImportMappingName(String importMappingName) {
		ImportMappingName = importMappingName;
	}

	public String getImportFilePath() {
		return ImportFilePath;
	}

	public void setImportFilePath(String importFilePath) {
		ImportFilePath = importFilePath;
	}

	public String getTransfertemplateId() {
		return TransfertemplateId;
	}

	public void setTransfertemplateId(String transfertemplateId) {
		TransfertemplateId = transfertemplateId;
	}

	public String getTransfer_Master_templateId() {
		return Transfer_Master_templateId;
	}

	public void setTransfer_Master_templateId(String transfer_Master_templateId) {
		Transfer_Master_templateId = transfer_Master_templateId;
	}

	public String getBackup_File_Path() {
		return Backup_File_Path;
	}

	public void setBackup_File_Path(String backup_File_Path) {
		Backup_File_Path = backup_File_Path;
	}

}
