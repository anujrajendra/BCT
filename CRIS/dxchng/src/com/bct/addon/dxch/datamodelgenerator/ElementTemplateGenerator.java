package com.bct.addon.dxch.datamodelgenerator;

import java.util.*;

import com.bct.addon.dxch.datamodelgenerator.dao.*;
import com.onwbp.base.text.bean.*;
import com.onwbp.org.apache.commons.lang3.*;
import com.orchestranetworks.addon.adix.dataexchange.common.*;
import com.orchestranetworks.addon.adix.dataexchange.datamodelgenerator.model.*;
import com.orchestranetworks.addon.adix.dataexchange.datamodelgenerator.ui.ajax.*;
import com.orchestranetworks.addon.dml.*;
import com.orchestranetworks.schema.*;

/**
 *
 * @author mostafashokiel
 */

public final class ElementTemplateGenerator {

	private static final String EBX_ID = "EBX_ID";

	public ElementTemplateGenerator() throws DataModelException {
	}

	private final String xsdTemplate = DMLTemplateManager
			.loadTemplate("/com/bct/addon/dxch/templates/template_Datamodel.txt");

	private final String tableTemplate = DMLTemplateManager
			.loadTemplate("/com/bct/addon/dxch/templates/template_Table.txt");

	private final String fieldTemplate = DMLTemplateManager
			.loadTemplate("/com/bct/addon/dxch/templates/template_Field.txt");

	private final String fieldSimpleTypeTemplate = DMLTemplateManager
			.loadTemplate("/com/bct/addon/dxch/templates/template_FieldSimpleType.txt");

	private final String groupTemplate = DMLTemplateManager
			.loadTemplate("/com/bct/addon/dxch/templates/template_Group.txt");

	private final String documentationTemplate = DMLTemplateManager
			.loadTemplate("/com/bct/addon/dxch/templates/template_Documentation.txt");

	private final String pkFieldTemplate = DMLTemplateManager
			.loadTemplate("/com/bct/addon/dxch/templates/template_PK_Field.txt");

	public String getXsdTemplate() {
		return this.xsdTemplate;
	}

	public String getTableTemplate() {
		return this.tableTemplate;
	}

	public String getFieldTemplate() {
		return this.fieldTemplate;
	}

	public String getGroupTemplate() {
		return this.groupTemplate;
	}

	public String getDocumentationTemplate() {
		return this.documentationTemplate;
	}

	public String generateTable(final DMLGenerateXSDProgressInformation pi, final DMLTable tableNode,
			final int indent) {
		List<String> tableParams = new ArrayList<>();
		tableParams.add(this.getIndentString(indent)); // 1 indent
		tableParams.add(tableNode.getName()); // 2 Table Name
		this.putDocumentationInfo(tableNode.getLabelAndDesciption(), tableParams, indent + 3);
		StringBuilder pkInfo = new StringBuilder();
		StringBuilder fieldsBuilder = new StringBuilder();

		if ((tableNode.getPKs() != null) && (tableNode.getPKs().size() > 0)) { // 3 Primary Key
			for (Path pk : tableNode.getPKs()) {
				pkInfo.append(pk.format());
				pkInfo.append(" ");
			}
		} else {
			pkInfo.append("/" + ElementTemplateGenerator.EBX_ID);
			fieldsBuilder.append(this.generatePK(ElementTemplateGenerator.EBX_ID, indent + 3));
		}
		tableParams.add(pkInfo.toString());
		// 4 Generaate fields
		for (DMLNode node : tableNode.getChildren()) {
			if (DMLField.class.isInstance(node)) {
				DMLField nodeField = (DMLField) node;
				fieldsBuilder.append(this.generateField(nodeField, indent + 3));
				if (pi != null) {
					pi.increaseCurrent();
				}
				continue;
			}
			fieldsBuilder.append(this.generateGroup(pi, node, indent + 3));
		}
		tableParams.add(fieldsBuilder.toString());
		return DMLTemplateManager.generate(this.tableTemplate, tableParams.toArray());
	}

	/**
	 * @param ebxId
	 * @param i
	 *
	 * @author mostafashokiel
	 * @return
	 */
	private String generatePK(final String fieldName, final int indent) {
		List<String> fieldParams = new ArrayList<>();
		fieldParams.add(this.getIndentString(indent)); // {0} indent
		fieldParams.add(fieldName); // {1} Name

		fieldParams.add("type=\"xs:int\""); // {2} type

		fieldParams.add("1"); // {3} minOccur
		fieldParams.add("1"); // {4} maxOccur
		fieldParams.add(""); // {5}
		fieldParams.add(""); // {6}

		fieldParams.add(this.pkFieldTemplate); // {7}primarykey

		return DMLTemplateManager.generate(this.fieldTemplate, fieldParams.toArray());
	}

	public String generateGroup(final DMLGenerateXSDProgressInformation pi, final DMLNode groupNode, final int indent) {
		List<String> groupParams = new ArrayList<>();
		groupParams.add(this.getIndentString(indent));
		groupParams.add(groupNode.getName());
		groupParams.add("1");
		groupParams.add("1");
		this.putDocumentationInfo(groupNode.getLabelAndDesciption(), groupParams, indent + 3);
		StringBuilder builder = new StringBuilder();
		for (DMLNode node : groupNode.getChildren()) {
			if (DMLField.class.isInstance(node)) {
				DMLField fieldNode = (DMLField) node;
				builder.append(this.generateField(fieldNode, indent + 3));
				if (pi != null) {
					pi.increaseCurrent();
				}
				continue;
			}
			if (DMLTable.class.isInstance(node)) {
				DMLTable tableNode = (DMLTable) node;
				builder.append(this.generateTable(pi, tableNode, indent + 3));
				if (pi != null) {
					pi.increaseCurrent();
				}
				continue;
			}
			builder.append(this.generateGroup(pi, node, indent + 3));
		}
		groupParams.add(builder.toString());
		return DMLTemplateManager.generate(this.groupTemplate, groupParams.toArray());
	}

	public String generateField(final DMLField nodeField, final int indent) {
		List<String> fieldParams = new ArrayList<>();
		fieldParams.add(this.getIndentString(indent)); // {0} indent
		fieldParams.add(nodeField.getName()); // {1} Name

		if (FieldType.STRING.equals(nodeField.getType()) && (nodeField.getSize() > 0)) { // {2} Type in simple block
			fieldParams.add("");
		} else { // {2} Type in simple block
			fieldParams.add("type=\"" + nodeField.getType().getValueInXSD() + "\"");
		}

		if (nodeField.isNullable()) { // {3} minOccur
			fieldParams.add("0");
		} else { // {3} minOccur
			fieldParams.add("1");
		}

		fieldParams.add("1"); // {4} maxOccur

		if (StringUtils.isNotEmpty(nodeField.getDefaultValue())) { // {5} default
			fieldParams.add("default=\"" + nodeField.getDefaultValue() + "\"");
		} else { // {5} default
			fieldParams.add("");
		}

		if (nodeField.getSize() > 0) { // {6} Type in simple block
			fieldParams.add(this.generateSimpleBlock(nodeField, indent + 2));
		} else { // {6} Type in simple block
			fieldParams.add("");
		}

		this.putDocumentationInfo(nodeField.getLabelAndDesciption(), fieldParams, indent + 2); // {7} Documentation;
		return DMLTemplateManager.generate(this.fieldTemplate, fieldParams.toArray());
	}

	private String generateSimpleBlock(final DMLField nodeField, final int indent) {
		List<String> fieldParams = new ArrayList<>();
		fieldParams.add(this.getIndentString(indent)); // {0} Indent
		fieldParams.add(nodeField.getType().getValueInXSD()); // {1} Type

		if (FieldType.STRING.equals(nodeField.getType())) { // {2}
			fieldParams.add("<xs:maxLength value=\"" + nodeField.getSize() + "\"/>");
		} else if (FieldType.INTEGER.equals(nodeField.getType()) || FieldType.DECIMAL.equals(nodeField.getType())) {
			fieldParams.add("<xs:totalDigits value=\"" + nodeField.getSize() + "\"/>");
		} else {
			fieldParams.add("");
		}

		if (FieldType.DECIMAL.equals(nodeField.getType()) && (nodeField.getDecimalDigits() > 0)) { // {3}
			fieldParams.add("<xs:fractionDigits value=\"" + nodeField.getDecimalDigits() + "\"/>");
		} else { // {3}
			fieldParams.add("");
		}

		return DMLTemplateManager.generate(this.fieldSimpleTypeTemplate, fieldParams.toArray());
	}

	public void putDocumentationInfo(final LabelDescription labelAndDescription, final List<String> info,
			final int indent) {
		info.add(""); // Override current implementation to keep documentation block empty
//		if (labelAndDescription == null) {
//			info.add("");
//			return;
//		}
//		List<LabelDescriptionForLocale> localizedDocumentations = labelAndDescription.getLocalizedDocumentations();
//		if (localizedDocumentations.isEmpty()) {
//			info.add("");
//			return;
//		}
//		StringBuilder builder = new StringBuilder();
//		for (LabelDescriptionForLocale labelDescriptionForLocale : localizedDocumentations) {
//			builder.append(this.putLabelDescriptionForLocale(labelDescriptionForLocale, indent));
//		}
//		info.add(builder.toString());
	}

	private String putEmptyLabelDescriptionDefault(final int indent) {
		return "";
	}

	private String putLabelDescriptionDefault(final int indent) {
		LabelDescriptionForLocale labelDescriptionForUS = new LabelDescriptionForLocale();
		labelDescriptionForUS.setLocale(Locale.US);
		LabelDescriptionForLocale lableDescriptionForFR = new LabelDescriptionForLocale();
		lableDescriptionForFR.setLocale(Locale.FRANCE);
		StringBuilder builder = new StringBuilder();
		builder.append(this.putLabelDescriptionForLocale(labelDescriptionForUS, indent));
		builder.append(this.putLabelDescriptionForLocale(lableDescriptionForFR, indent));
		return builder.toString();
	}

	private String putLabelDescriptionForLocale(final LabelDescriptionForLocale descriptionForLocale,
			final int indent) {
		String[] fieldParams = new String[4];
		fieldParams[0] = this.getIndentString(indent);
		fieldParams[1] = this.getLanguageAndCountry(descriptionForLocale.getLocale());
		fieldParams[2] = "";
		fieldParams[3] = "";
		String label = descriptionForLocale.getLabel();
		if (label != null) {
			fieldParams[2] = XMLEntities.getInstance().encode(label);
		}
		String description = descriptionForLocale.getDescription();
		if (description != null) {
			fieldParams[3] = XMLEntities.getInstance().encode(description);
		}
		return DMLTemplateManager.generate(this.documentationTemplate, fieldParams);
	}

	private String getIndentString(final int indent) {
		StringBuilder indentStr = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			indentStr.append("\t");
		}
		return indentStr.toString();
	}

	private String getLanguageAndCountry(final Locale locale) {
		return locale.getLanguage() + "-" + locale.getLanguage();
	}
}
