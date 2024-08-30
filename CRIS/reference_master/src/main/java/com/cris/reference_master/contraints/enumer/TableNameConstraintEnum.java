package com.cris.reference_master.contraints.enumer;

import java.util.List;
import java.util.Locale;

import com.cris.reference_master.logger.ModuleLogger;
import com.cris.reference_master.utilities.RepositoryUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.ConstraintEnumeration;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.service.LoggingCategory;

public class TableNameConstraintEnum implements ConstraintEnumeration<String> {

	private String pathToDataSpaceKey;
	private String pathToDataSetName;
	private Path pathDataSpace;
	private Path pathDataSet;

	@Override
	public void checkOccurrence(String arg0, ValueContextForValidation arg1) throws InvalidSchemaException {

	}

	@Override
	public void setup(ConstraintContext arg0) {

	}

	@Override
	public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {

		return null;
	}

	@Override
	public String displayOccurrence(String tbPathString, ValueContext aContext, Locale locale)
			throws InvalidSchemaException {
//		ModuleLogger.logger.info("table path string: "+tbPathString);
		if (tbPathString == null || tbPathString.trim().length() == 0)
			
			return null;

		Path tablePath = Path.parse(tbPathString);
		if (tablePath == null)
			return null;

		String dataspaceName = (String) aContext.getValue(this.pathDataSpace);
		String datasetName = (String) aContext.getValue(this.pathDataSet);

		AdaptationHome dataspace = RepositoryUtils.getDataspace(dataspaceName.substring(1));
		Adaptation dataset = RepositoryUtils.getDataset(dataspace, datasetName);

		if (dataset == null)
			return null;

		SchemaNode tableNode = dataset.getSchemaNode().getNode(tablePath);
		if (tableNode == null)
			return null;

		String label = tableNode.getLabel(locale);
		return label;
	}

	@Override
	public List<String> getValues(ValueContext aContext) throws InvalidSchemaException {

		LoggingCategory logger = ModuleLogger.logger;

		String dataspaceName = (String) aContext.getValue(this.pathDataSpace);
		String datasetName = (String) aContext.getValue(this.pathDataSet);

		AdaptationHome dataspace = RepositoryUtils.getDataspace(dataspaceName.substring(1));
		Adaptation dataset = RepositoryUtils.getDataset(dataspace, datasetName);

		List<String> tableList = RepositoryUtils.getTableListInDataset(dataset, logger);
		return tableList;
	}

	public String getPathToDataSpaceKey() {
		return pathToDataSpaceKey;
	}

	public void setPathToDataSpaceKey(String pathToDataSpaceKey) {
		this.pathToDataSpaceKey = pathToDataSpaceKey;
		this.pathDataSpace = Path.parse(this.pathToDataSpaceKey);
	}

	public String getPathToDataSetName() {
		return pathToDataSetName;
	}

	public void setPathToDataSetName(String pathToDataSetName) {
		this.pathToDataSetName = pathToDataSetName;
		this.pathDataSet = Path.parse(this.pathToDataSetName);
	}
}