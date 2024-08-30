package com.cris.reference_master.scheduler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.cris.reference_master.logger.ModuleLogger;
import com.cris.reference_master.utilities.WorkflowLauncher;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.scheduler.ScheduledExecutionContext;
import com.orchestranetworks.scheduler.ScheduledTask;
import com.orchestranetworks.scheduler.ScheduledTaskInterruption;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Session;

public class WorkflowInvocationScheduler extends ScheduledTask {
	private String workflowName;
	private String workflowLabel;
	private String workflowDescription;
	private String ImportTableName;
	private String ImportMappingName;
	private String ImportFilePath;
	private String TransfertemplateId;
	private String ExportFilePath;
	private String ExporttemplateId;
	private String Transfer_Master_templateId;
	private String Backup_File_Path;

	/*
	 * execute method, will start the code execution when the scheduler execution
	 * starts.
	 */
	@Override
	public void execute(ScheduledExecutionContext context) throws OperationException, ScheduledTaskInterruption {
		ModuleLogger.logger.info("Starting workflow invocation scheduler...");
		if (!validateParameters()) {
			return;
		}

		if (!Files.exists(Paths.get(ImportFilePath))) {
			String errorMessage = "The import file path does not exist: " + ImportFilePath;
			ModuleLogger.logger.error(errorMessage);
			return;
		}

		try {
			Repository repository = context.getRepository();
			Session session = context.getSession();

			WorkflowLauncher wfLauncher = new WorkflowLauncher(repository, session);
			HashMap<String, String> importFile = new HashMap<>();
			importFile.put("ImportMappingName", ImportMappingName);
			importFile.put("ImportFilePath", ImportFilePath);
			importFile.put("ImportTableName", ImportTableName);
			importFile.put("TransfertemplateId", TransfertemplateId);
			importFile.put("ExportFilePath", ExportFilePath);
			importFile.put("ExporttemplateId", ExporttemplateId);
			importFile.put("Transfer_Master_templateId", Transfer_Master_templateId);
			importFile.put("Backup_File_Path", Backup_File_Path);
			wfLauncher.launch(workflowName, importFile, workflowLabel, workflowDescription);
			// ModuleLogger.logger.info("ImportFile HashMap contents: " + importFile);

			// ModuleLogger.logger.info("Workflow launched successfully.");
		} catch (Exception e) {
			ModuleLogger.logger.error("Exception: " + e.getMessage(), e);
		}
	}

	/* Validate method to check scheduler configuration */
	private boolean validateParameters() {
		boolean isValid = true;

		if (isEmpty(workflowName)) {
			String errorMessage = "Workflow name is empty.";
			// ModuleLogger.logger.error(errorMessage);
			isValid = false;
		}
		if (isEmpty(workflowLabel)) {
			String errorMessage = "Workflow label is empty.";
			ModuleLogger.logger.error(errorMessage);
			isValid = false;
		}
		if (isEmpty(workflowDescription)) {
			String errorMessage = "Workflow description is empty.";
			ModuleLogger.logger.error(errorMessage);
			isValid = false;
		}
		if (isEmpty(ImportMappingName)) {
			String errorMessage = "Import mapping name is empty.";
			ModuleLogger.logger.error(errorMessage);
			isValid = false;
		}
		if (isEmpty(ImportFilePath)) {
			String errorMessage = "Import file path is empty.";
			ModuleLogger.logger.error(errorMessage);
			isValid = false;
		}
		if (isEmpty(ImportTableName)) {
			String errorMessage = "Import table name is empty.";
			ModuleLogger.logger.error(errorMessage);
			isValid = false;
		}

		return isValid;
	}

	private boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

//Getter Setter
	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getWorkflowLabel() {
		return workflowLabel;
	}

	public void setWorkflowLabel(String workflowLabel) {
		this.workflowLabel = workflowLabel;
	}

	public String getWorkflowDescription() {
		return workflowDescription;
	}

	public void setWorkflowDescription(String workflowDescription) {
		this.workflowDescription = workflowDescription;
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

	public String getExportFilePath() {
		return ExportFilePath;
	}

	public void setExportFilePath(String exportFilePath) {
		ExportFilePath = exportFilePath;
	}

	public String getExporttemplateId() {
		return ExporttemplateId;
	}

	public void setExporttemplateId(String exporttemplateId) {
		ExporttemplateId = exporttemplateId;
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
