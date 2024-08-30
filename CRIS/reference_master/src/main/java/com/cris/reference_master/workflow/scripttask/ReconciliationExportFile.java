package com.cris.reference_master.workflow.scripttask;

import java.io.File;

import com.cris.reference_master.logger.ModuleLogger;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.orchestranetworks.addon.dint.DataIntegrationException;
import com.orchestranetworks.addon.dint.DataIntegrationExecutionResults;
import com.orchestranetworks.addon.dint.DataIntegrationExecutor;
import com.orchestranetworks.addon.dint.template.ExcelExportTemplateSpec;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class ReconciliationExportFile extends ScriptTaskBean {
	private String ExportFilePath;
    private String ExporttemplateId;
	
    
    @Override
	public void executeScript(ScriptTaskBeanContext context) throws OperationException {
    	
    
    	//ModuleLogger.logger.info("Executing ReconciliationExportFile script with the following parameters:");
        //ModuleLogger.logger.info("ExportFilePath: " + ExportFilePath);
        //ModuleLogger.logger.info("ExporttemplateId: " + ExporttemplateId);
        File ExportFile = new File(ExportFilePath);
        ModuleLogger.logger.info("ExportFile: " + ExportFile);
		// TODO Auto-generated method stub
    	ExcelExportTemplateSpec export= new ExcelExportTemplateSpec(ExporttemplateId, ExportFile, context.getSession());
		try {
			DataIntegrationExecutionResults export2 =  DataIntegrationExecutor.getInstance().execute(export);
		} catch (DataIntegrationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public static AdaptationHome toDataSpace(final Repository repository, final String dataSpaceName) throws OperationException {
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
	

}
