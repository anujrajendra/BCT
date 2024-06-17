package com.cris.feedback.utils;

import java.util.*;

import com.onwbp.adaptation.*;
import com.orchestranetworks.instance.*;
import com.orchestranetworks.schema.*;
import com.orchestranetworks.schema.info.*;
import com.orchestranetworks.service.*;

public final class RepositoryUtils {

	public static AdaptationHome getDataspace(final String pDataspaceName) {

		if (pDataspaceName == null) {
			return null;
		}

		Repository repository = Repository.getDefault();
		return RepositoryUtils.getDataspace(repository, pDataspaceName);
	}

	public static AdaptationHome getDataspace(final Repository repository, final String pDataspaceName) {

		if ((repository == null) || (pDataspaceName == null)) {
			return null;
		}

		HomeKey hkDataspace = HomeKey.forBranchName(pDataspaceName);
		AdaptationHome dataspace = repository.lookupHome(hkDataspace);
		return dataspace;
	}

	public static Adaptation getDataset(final Repository repository, final String pDataspaceName,
			final String pDatasetName) {

		if ((pDataspaceName == null) || (pDatasetName == null) || (repository == null)) {
			return null;
		}

		AdaptationHome dataspace = RepositoryUtils.getDataspace(repository, pDataspaceName);
		if (dataspace == null) {
			return null;
		}

		return RepositoryUtils.getDataset(dataspace, pDatasetName);
	}

	public static AdaptationTable getTable(final Adaptation dataset, final String tablePath) {
		if (dataset == null) {
			return null;
		}

		try {
			return dataset.getTable(Path.parse(tablePath));
		} catch (Exception e) {
			LoggingCategory.getKernel().info("Failed with message: " + e.getMessage());
			return null;
		}
	}

	public static Adaptation getDataset(final String pDataspaceName, final String pDatasetName) {

		if ((pDataspaceName == null) || (pDatasetName == null)) {
			return null;
		}

		AdaptationHome dataspace = RepositoryUtils.getDataspace(pDataspaceName);
		if (dataspace == null) {
			return null;
		}

		return RepositoryUtils.getDataset(dataspace, pDatasetName);
	}

	public static Adaptation getDataset(final AdaptationHome dataspace, final String pDatasetName) {
		if ((dataspace == null) || (pDatasetName == null)) {
			return null;
		}

		return dataspace.findAdaptationOrNull(AdaptationName.forName(pDatasetName));
	}

	public static List<String> getTableListInDataset(final Adaptation dataset, final LoggingCategory logger) {
		SchemaNode rootNode = dataset.getSchemaNode().getNode(Path.parse("/root"));
		List<String> tableMap = RepositoryUtils.getTableList(rootNode, logger);
		return tableMap;
	}

	public static List<String> getTableList(final SchemaNode rootNode, final LoggingCategory logger) {

		List<String> tableMap = new ArrayList<String>();

		List<SchemaNode> nodes = Arrays.asList(rootNode.getNodeChildren());
		for (SchemaNode node : nodes) {
			if (node.isTableNode()) {
				tableMap.add(node.getPathInSchema().format());
				continue;
			}
			if (node.isComplex()) {
				tableMap.addAll(RepositoryUtils.getTableList(node, logger));
			}
		}

		return tableMap;
	}

	public static List<String> getFieldList(final SchemaNode complexNode, final LoggingCategory logger) {
		List<String> fieldList = new ArrayList<String>();

		List<SchemaNode> nodes = Arrays.asList(complexNode.getNodeChildren());
		for (SchemaNode node : nodes) {
			if (!node.isAssociationNode() && !node.isSelectNode()) {
				if (node.isComplex()) {
					fieldList.addAll(RepositoryUtils.getFieldList(node, logger));
				} else if (node.isTerminalValue()) {
					fieldList.add(String.valueOf(Path.SELF.format()) + node.getPathInAdaptation().format());
				}
			}
		}
		return fieldList;
	}

	public static Adaptation getRecordFromPrimaryKey(final String dataspaceName, final String datasetName,
			final String tablePath, final String primaryKey) {
		AdaptationHome dataspace = RepositoryUtils.getDataspace(dataspaceName);
		Adaptation dataset = RepositoryUtils.getDataset(dataspace, datasetName);
		AdaptationTable table = RepositoryUtils.getTable(dataset, tablePath);

		Adaptation record = table.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(primaryKey));

		return record;
	}

	public static RequestResult getResultSetOfAssociatedRecords(final Path associationNodePath,
			final Adaptation record) {

		SchemaNode tableNode = record.getContainerTable().getTableNode();
		SchemaNode associationNode = tableNode.getNode(associationNodePath);
		AssociationLink associationLink = associationNode.getAssociationLink();
		RequestResult rsAssociatedRecords = associationLink.getAssociationResult(record);
		return rsAssociatedRecords;
	}

	public static List<Adaptation> getListOfAssociatedRecords(final Path associationNodePath, final Adaptation record) {
		List<Adaptation> listOfRecords = new ArrayList<Adaptation>();
		RequestResult rsAssociatedRecords = RepositoryUtils.getResultSetOfAssociatedRecords(associationNodePath,
				record);
		Adaptation associatedRecord = null;
		while ((associatedRecord = rsAssociatedRecords.nextAdaptation()) != null) {
			listOfRecords.add(associatedRecord);
		}

		return listOfRecords;
	}

	public static Adaptation getLinkedRecords(final Path foreignKeyNodePath, final Adaptation record) {

		AdaptationTable tbClientPersonRole = record.getContainerTable();
		SchemaNode fkNode = tbClientPersonRole.getTableNode().getNode(foreignKeyNodePath);
		SchemaFacetTableRef tableRef = fkNode.getFacetOnTableReference();
		Adaptation personRecord = tableRef.getLinkedRecord(record);
		return personRecord;
	}

	public static AdaptationHome toDataSpace(final Repository repository, final String dataSpaceName)
			throws OperationException {
		try {
			final HomeKey dataSpaceKey = HomeKey.forBranchName(dataSpaceName);
			final AdaptationHome dataSpace = repository.lookupHome(dataSpaceKey);
			if (dataSpace == null) {
				throw new IllegalArgumentException();
			}
			return dataSpace;
		} catch (final Exception exception) {
			throw exception;
		}
	}

	public static Adaptation toDataSet(final AdaptationHome dataSpace, final String dataSetName)
			throws OperationException {
		try {
			final AdaptationName dataSetKey = AdaptationName.forName(dataSetName);
			final Adaptation dataSet = dataSpace.findAdaptationOrNull(dataSetKey);
			if (dataSet == null) {
				throw new IllegalArgumentException();
			}
			return dataSet;
		} catch (final Exception exception) {
			throw exception;
		}
	}

	public static AdaptationTable toTable(final Adaptation dataSet, final String tablePath) throws OperationException {
		try {
			final AdaptationTable table = dataSet.getTable(Path.parse(tablePath));
			return table;
		} catch (final Exception exception) {
			throw exception;
		}
	}
}