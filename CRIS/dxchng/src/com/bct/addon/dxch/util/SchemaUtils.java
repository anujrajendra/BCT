package com.bct.addon.dxch.util;

import java.sql.*;
import java.util.*;

import com.bct.addon.dxch.datamodelgenerator.*;
import com.bct.addon.dxch.datamodelgenerator.dao.*;
import com.bct.addon.dxch.dto.*;
import com.onwbp.base.text.*;
import com.onwbp.base.text.bean.*;
import com.orchestranetworks.addon.adix.dataexchange.datamodelgenerator.model.*;
import com.orchestranetworks.addon.adix.dataexchange.datamodelgenerator.ui.ajax.*;
import com.orchestranetworks.addon.dml.*;
import com.orchestranetworks.schema.*;

/**
 *
 * @author mostafashokiel
 */
public class SchemaUtils {

	public static String generateXSD(final String tableName, final List<ColumnMetadata> columns) {
		DMLGenerateXSDProgressInformation pi = new DMLGenerateXSDProgressInformation();
		DMLNode root;
		try {
			root = SchemaUtils.createStructureOfDataModel(pi, columns, tableName, "root", new LabelDescription());
			ElementTemplateGenerator elementTemplates = new ElementTemplateGenerator();
			String xsdContent = SchemaUtils.generateToXSDFile(pi, root, elementTemplates);
			return xsdContent;
			// TODO save file in module path
//			File generatedFile = DMLUtils.getInstance().generateFile(root.getName(), xsdContent);

		} catch (DataModelException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static String generateToXSDFile(final DMLGenerateXSDProgressInformation pi, final DMLNode root,
			final ElementTemplateGenerator elementTemplates) {
		int indent = 3;

		List<String> xsdParams = new ArrayList<>();
		xsdParams.add(root.getXSDName());

		elementTemplates.putDocumentationInfo(root.getLabelAndDesciption(), xsdParams, indent);
		indent++;

		StringBuilder builder = new StringBuilder();
		List<DMLNode> childNodes = root.getChildren();
		
		String tableName= null;

		for (DMLNode node : childNodes) {
			if (DMLTable.class.isInstance(node)) {
				DMLTable tableNode = (DMLTable) node;
				builder.append(elementTemplates.generateTable(pi, tableNode, indent));
				tableName=tableNode.getName();
				if (pi != null) {
					pi.increaseCurrent();
				}
				continue;
			}
			builder.append(elementTemplates.generateGroup(pi, node, indent));
		}
		//TODO Handle Multiple Tables
		xsdParams.add(builder.toString());
		xsdParams.add(tableName);
		return DMLTemplateManager.generate(elementTemplates.getXsdTemplate(), xsdParams.toArray());
	}

	/**
	 * @param column
	 * @return
	 *
	 * @author mostafashokiel
	 */
	private static SchemaTypeName getFieldType(final String columnType) {
		// INT Type
		if (String.valueOf(Types.BIGINT).equals(columnType) || String.valueOf(Types.INTEGER).equals(columnType)
				|| String.valueOf(Types.DOUBLE).equals(columnType)
				|| String.valueOf(Types.SMALLINT).equals(columnType)) {
			return SchemaTypeName.XS_INTEGER;
		}
		// FLOAT Type
		if (String.valueOf(Types.DECIMAL).equals(columnType) || String.valueOf(Types.FLOAT).equals(columnType)
				|| String.valueOf(Types.DOUBLE).equals(columnType)) {
			return SchemaTypeName.XS_DECIMAL;

			// Boolean
		} else if (String.valueOf(Types.BOOLEAN).equals(columnType)) {
			return SchemaTypeName.XS_BOOLEAN;

			// Date Type
		} else if (String.valueOf(Types.DATE).equals(columnType)) {
			return SchemaTypeName.XS_DATE;

			// TIME Type
		} else if (String.valueOf(Types.TIME).equals(columnType)
				|| String.valueOf(Types.TIME_WITH_TIMEZONE).equals(columnType)
				|| String.valueOf(Types.TIMESTAMP).equals(columnType)
				|| String.valueOf(Types.TIMESTAMP_WITH_TIMEZONE).equals(columnType)) {
			return SchemaTypeName.XS_DATETIME;
		}

		// Otherwise return String type
		return SchemaTypeName.XS_STRING;
	}

	private static DMLNode createStructureOfDataModel(final DMLGenerateXSDProgressInformation pi,
			final List<ColumnMetadata> columms, final String tableName, final String nameOfRootPath,
			final LabelDescription labelDescription) throws DataModelException {

		// Create Root Node
		DMLNode rootNode = new DMLNode(nameOfRootPath, nameOfRootPath, labelDescription, Path.ROOT, 0);
		Map<Path, DMLNode> mapFullPath_Node = new HashMap<>();
		mapFullPath_Node.put(rootNode.getFullPath(), rootNode);

		// Create Table Node and add it to Root Node
		DMLTable tableNode = DMLUtils.getInstance().getTableNode(tableName);
		rootNode.addChild(tableNode);
		if (pi != null) {
			pi.increaseCurrent();
		}
		// Create Fields inside table
		SchemaUtils.addFieldsIntoStructure(pi, tableNode, columms);

		return rootNode;

	}

	private static void addFieldsIntoStructure(final DMLGenerateXSDProgressInformation pi, final DMLTable tableNode,
			final List<ColumnMetadata> columms) throws DataModelException {
		Map<Path, DMLNode> mapFullPath_Node = new HashMap<>();
		for (ColumnMetadata field : columms) {

			DMLField fieldNode = SchemaUtils.getFieldNode(field);
			tableNode.addPK(fieldNode);
			Path groupPath = fieldNode.getGroupPath();

			boolean hasGroupPath = !Path.ROOT.equals(groupPath);

			if (hasGroupPath) {
				DMLNode groupNode = mapFullPath_Node.get(groupPath);
				if (groupNode == null) {
					SchemaUtils.generateGroupStructure(tableNode, fieldNode, mapFullPath_Node);
				} else {
					groupNode.addChild(fieldNode);
				}
				if (pi != null) {
					pi.increaseCurrent();
				}
				continue;
			}
			tableNode.addChild(fieldNode);
			if (pi != null) {
				pi.increaseCurrent();
			}
		}
	}

	public static DMLField getFieldNode(final ColumnMetadata field) throws DataModelException {

		DMLField dmlField = new DMLField(field.getName(), field.getName(),
				SchemaUtils.defineLabelAndDescription(UserMessage.createInfo(field.getName()),
						UserMessage.createInfo(field.getDescription())),
				FieldType.parse(SchemaUtils.getFieldType(field.getDataType())), field.isPrimaryKey(), Path.ROOT,
				field.getIndex());

		dmlField.setDecimalDigits(field.getDecimalDigits());
		dmlField.setDefaultValue(field.getDefaultValue());
		dmlField.setSize(field.getSize());
		dmlField.setNullable(field.isNullable());

		return dmlField;
	}

	/**
	 * @param label
	 * @param description
	 * @return
	 *
	 * @author mostafashokiel
	 */
	private static LabelDescription defineLabelAndDescription(final UserMessage label, final UserMessage description) {
		return null;
	}

	private static void generateGroupStructure(final DMLNode rootNode, final DMLNode node,
			final Map<Path, DMLNode> mapFullPathNode) {
		Path groupPath = node.getGroupPath();
		DMLNode group = mapFullPathNode.get(rootNode.getFullPath());
		if (group == null) {
			Step[] steps = groupPath.getSteps();
			Path currentPath = Path.ROOT;
			for (Step step : steps) {
				DMLNode newGroup = mapFullPathNode.get(currentPath.add(step));
				if (newGroup == null) {
					String groupName = step.format();
					newGroup = new DMLNode(groupName, groupName, null, currentPath, 0);
					mapFullPathNode.put(newGroup.getFullPath(), newGroup);
					if (group == null) {
						rootNode.addChild(newGroup);
					}
				}
				if (group != null) {
					group.addChild(newGroup);
				}
				group = newGroup;
				currentPath = currentPath.add(step);
			}
		}
		if (group != null) {
			group.addChild(node);
		}
	}

}
