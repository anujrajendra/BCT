package com.cris.reference_master.contraints.enumer;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.cris.reference_master.logger.ModuleLogger;
import com.cris.reference_master.utilities.RepositoryUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationTable;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.ConstraintEnumeration;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.service.LoggingCategory;

public class FieldPathConstraintEnum implements ConstraintEnumeration<String> {

	private String pathToDataspaceKey;
	private String pathToDatasetName;
	private String pathToTablePath;

	private Path pathDataspace;
	private Path pathDataset;
	private Path pathTable;

	@Override
	public void checkOccurrence(String arg0, ValueContextForValidation arg1) throws InvalidSchemaException {

	}

	@Override
	public void setup(ConstraintContext context) {

		if (this.pathToDataspaceKey == null || this.pathToDataspaceKey.trim().length() == 0)
			throw new IllegalArgumentException("Parameter 'pathToDataspaceKey' not filled.");

		this.pathDataspace = Path.parse(this.pathToDataspaceKey);
		SchemaNode node = context.getSchemaNode().getNode(this.pathDataspace);
		if (node == null)
			throw new IllegalArgumentException("Invalid Dataspace Field Path");

		if (this.pathToDatasetName == null || this.pathToDatasetName.trim().length() == 0)
			throw new IllegalArgumentException("Parameter 'pathToDataSetName' not filled.");

		this.pathDataset = Path.parse(this.pathToDatasetName);
		node = context.getSchemaNode().getNode(this.pathDataset);
		if (node == null)
			throw new IllegalArgumentException("Invalid Dataset Field Path");

		if (this.pathToTablePath == null || this.pathToTablePath.trim().length() == 0)
			throw new IllegalArgumentException("Parameter 'pathToTablePath' not filled.");

		this.pathTable = Path.parse(this.pathToTablePath);
		node = context.getSchemaNode().getNode(this.pathTable);
		if (	node == null)
			throw new IllegalArgumentException("Invalid Table Path");
	}

	@Override
	public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {

		return null;
	}

	@Override
	public String displayOccurrence(String fieldPath, ValueContext context, Locale locale)
			throws InvalidSchemaException {

		if (fieldPath == null || fieldPath.trim().length() == 0)
			return null;

		String delimiter = Path.ROOT.format();
		Path globalPath = Path.SELF.add(Path.ROOT);

		String dataspaceName = (String) context.getValue(this.pathDataspace);
		String datasetName = (String) context.getValue(this.pathDataset);
		String tablePath = (String) context.getValue(this.pathTable);

		AdaptationHome dataspace = RepositoryUtils.getDataspace(dataspaceName.substring(1));
		Adaptation dataset = RepositoryUtils.getDataset(dataspace, datasetName);
		AdaptationTable table = RepositoryUtils.getTable(dataset, tablePath);

		if (table == null)
			return null;
		String displayLabel = "";

		SchemaNode tableNode = table.getTableNode().getTableOccurrenceRootNode();

		if (fieldPath.startsWith(delimiter))
			fieldPath = fieldPath.substring(1);
		else
			fieldPath = fieldPath.substring(2);

		List<String> nodesInPath = Arrays.asList(fieldPath.split(delimiter));

		for (String nodePath : nodesInPath) {
			Path path = Path.parse(nodePath);
			globalPath = globalPath.add(path);
			SchemaNode nodeField = tableNode.getNode(globalPath);
			if (nodeField == null)
				return null;

			displayLabel = String.valueOf(displayLabel) + nodeField.getLabel(locale) + delimiter;
		}

		displayLabel = displayLabel.substring(0, displayLabel.length() - delimiter.length());
		return displayLabel;
	}

	@Override
	public List<String> getValues(ValueContext aContext) throws InvalidSchemaException {

		LoggingCategory logger = ModuleLogger.logger;

		String dataspaceName = (String) aContext.getValue(this.pathDataspace);
		String datasetName = (String) aContext.getValue(this.pathDataset);
		String tablePath = (String) aContext.getValue(this.pathTable);

		AdaptationHome dataspace = RepositoryUtils.getDataspace(dataspaceName.substring(1));
		Adaptation dataset = RepositoryUtils.getDataset(dataspace, datasetName);
		AdaptationTable table = RepositoryUtils.getTable(dataset, tablePath);

		List<String> fieldPath = RepositoryUtils.getFieldList(table.getTableNode().getTableOccurrenceRootNode(),
				logger);

		return fieldPath;
	}

	public String getPathToDataspaceKey() {
		return pathToDataspaceKey;
	}

	public void setPathToDataspaceKey(String pathToDataspaceKey) {
		this.pathToDataspaceKey = pathToDataspaceKey;
	}

	public String getPathToDatasetName() {
		return pathToDatasetName;
	}

	public void setPathToDatasetName(String pathToDatasetName) {
		this.pathToDatasetName = pathToDatasetName;
	}

	public String getPathToTablePath() {
		return pathToTablePath;
	}

	public void setPathToTablePath(String pathToTablePath) {
		this.pathToTablePath = pathToTablePath;
	}
}
