package com.cris.custom.indicator.function.field;

import java.util.Locale;

import com.cris.custom.indicator.logger.ModuleLogger;
import com.cris.custom.indicator.message.Messages;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.onwbp.adaptation.XPathFilter;
import com.onwbp.base.text.Severity;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.addon.dpra.ChartType;
import com.orchestranetworks.addon.dpra.DPRAException;
import com.orchestranetworks.addon.dpra.function.FunctionDefinition;
import com.orchestranetworks.addon.dpra.function.InputDefinition;
import com.orchestranetworks.addon.dpra.function.OutputDefinition;
import com.orchestranetworks.addon.dpra.function.chart.AxisConfig;
import com.orchestranetworks.addon.dpra.function.chart.ChartConfigForFunction;
import com.orchestranetworks.addon.dpra.function.execution.context.ExecutionContextOnField;
//import com.orchestranetworks.addon.dpra.function.execution.context.ExecutionContextOnTable;
import com.orchestranetworks.addon.dpra.function.execution.result.FunctionResult;
import com.orchestranetworks.addon.dpra.function.execution.result.ValueSequence;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.service.ValidationReport;
import com.orchestranetworks.service.ValidationReportItem;
import com.orchestranetworks.service.ValidationReportItemIterator;
import com.orchestranetworks.service.ValidationReportItemSubjectForAdaptation;

public class FieldValidationMessageCount {

	private FieldValidationMessageCount() {

	}

// 	Details of the input to take from the indicator.
	private static final String INPUT_DATA_ISSUE = Messages.get(FieldValidationMessageCount.class, Locale.ENGLISH,
			"FieldValidationMessageCount.INPUT_FIELD_NAME");
	private static final String INPUT_FIELD_LABEL = Messages.get(FieldValidationMessageCount.class, Locale.ENGLISH,
			"FieldValidationMessageCount.INPUT_FIELD_LABEL");
	private static final String INPUT_FIELD_DESC = Messages.get(FieldValidationMessageCount.class, Locale.ENGLISH,
			"FieldValidationMessageCount.INPUT_FIELD_DESCRIPTION");
//  ********************************************************************************************************************************

// 	Details of the output for the indicator.
	private static final String OUTPUT_RECORD_COUNT_WITH_DATA_ISSUE = Messages.get(FieldValidationMessageCount.class,
			Locale.ENGLISH, "FieldValidationMessageCount.OUTPUT_FIELD_NAME");
	private static final String OUTPUT_FIELD_LABEL = Messages.get(FieldValidationMessageCount.class, Locale.ENGLISH,
			"FieldValidationMessageCount.OUTPUT_FIELD_LABEL");
	private static final String OUTPUT_FIELD_DESCRIPTION = Messages.get(FieldValidationMessageCount.class,
			Locale.ENGLISH, "FieldValidationMessageCount.OUTPUT_FIELD_DESCRIPTION");
//	********************************************************************************************************************************

// 	Details of the function created for the indicator.
	private static final String FUNCTION_NAME = Messages.get(FieldValidationMessageCount.class, Locale.ENGLISH,
			"FieldValidationMessageCount.FUNCTION_NAME");
	private static final String FUNCTION_LABEL = Messages.get(FieldValidationMessageCount.class, Locale.ENGLISH,
			"FieldValidationMessageCount.FUNCTION_LABEL");
	private static final String FUNCTION_DESC = Messages.get(FieldValidationMessageCount.class, Locale.ENGLISH,
			"FieldValidationMessageCount.FUNCTION_DESCRIPTION");
//	*********************************************************************************************************************************

// 	Function Input Definition
	private static final InputDefinition DATA_ISSUE_INPUT_DEF = InputDefinition.forString(INPUT_DATA_ISSUE,
			UserMessage.createInfo(INPUT_FIELD_LABEL), UserMessage.createInfo(INPUT_FIELD_DESC), "All");

//	Function Output Definition
	private static final OutputDefinition COUNT_OUTPUT_DEF = OutputDefinition.forInteger(
			OUTPUT_RECORD_COUNT_WITH_DATA_ISSUE, UserMessage.createInfo(OUTPUT_FIELD_LABEL),
			UserMessage.createInfo(OUTPUT_FIELD_DESCRIPTION));

//	Function Definition
	public static final FunctionDefinition DEFINITION = FunctionDefinition.onField(FUNCTION_NAME)
			.withLabel(FUNCTION_LABEL).withDescription(FUNCTION_DESC).withInputs(DATA_ISSUE_INPUT_DEF)
			.withOutputs(COUNT_OUTPUT_DEF).withLinkedRecord().withFilter()
			.onExecution(FieldValidationMessageCount::execute).build();

// 	Function Chart Definition
	public static final ChartConfigForFunction CHART_CONFIG = ChartConfigForFunction
			.newBuilder(AxisConfig.outputLabelAsCategory(OUTPUT_RECORD_COUNT_WITH_DATA_ISSUE))
			.setDefaultChartType(ChartType.CARD).addOtherChartTypes(ChartType.COLUMN).build();

// 	Code/functionality to be executed by the indicator when it is created or refereshed.
	private static FunctionResult execute(ExecutionContextOnField context) throws DPRAException {

		StringBuilder filterBuilder = new StringBuilder();

		final AdaptationTable table = context.getCurrentTable();
		SchemaNode node = context.getCurrentField();
		int errorCount = 0;

		String dataIssue = context.getInputValue(INPUT_DATA_ISSUE).trim();
		ModuleLogger.getModuleLogger().info("Executing indicator for dataissue: " + dataIssue);

		String xPathFilter = context.getXpathFilterPredicate().trim();
		ModuleLogger.getModuleLogger().info("Executing indicator for filter " + xPathFilter);

		if (xPathFilter == null || xPathFilter.length() <= 1) {
			ModuleLogger.getModuleLogger().info("Inside the no XPath Filter If block");
			if (dataIssue != null && dataIssue.length() > 0) {
				ValidationReport tableValidationReport = table.getValidationReport();
				ModuleLogger.getModuleLogger()
						.info("Executing indicator for tableValidationReport " + tableValidationReport.hasItems());
				if (tableValidationReport.hasItems()) {
					ValidationReportItemIterator validationItemIterator = tableValidationReport
							.getItemsOfSeverityOrMore(Severity.INFO);
					ModuleLogger.getModuleLogger()
							.info("Executing indicator for validationItemIterator " + validationItemIterator.hasNext());
					while (validationItemIterator.hasNext()) {
						ValidationReportItem validationItem = validationItemIterator.nextItem();
						UserMessage userMessage = validationItem.getMessage();
						if (userMessage != null) {
							String validationMessage = validationItem.getMessage().formatMessage(Locale.ENGLISH);
							if (validationMessage != null) {
								validationMessage = validationMessage.trim();
								validationMessage = validationMessage.toLowerCase();
								ModuleLogger.getModuleLogger().info("validationMessage :" + validationMessage);
								ModuleLogger.getModuleLogger().info("dataIssue :" + dataIssue);
								dataIssue = dataIssue.toLowerCase();
								if (validationMessage.contains(dataIssue)) {
									ValidationReportItemSubjectForAdaptation itemSubject = validationItem
											.getSubjectForAdaptation();
									SchemaNode validationNode = itemSubject.getSchemaNode();
									String configuredNodePath = node.getPathInAdaptation().format();
									String validationNodePath = validationNode.getPathInAdaptation().format();
									if (configuredNodePath.equalsIgnoreCase(validationNodePath)) {
										errorCount++;
										filterBuilder = buildLinkedRecordXPathFilter(filterBuilder,
												itemSubject.getAdaptation());
									}
								}
							}
						}
					}
				}
			}
		} else {
			ModuleLogger.getModuleLogger().info("Inside the XPath Filter else block");
			try (RequestResult requestResult = table.createRequestResult(xPathFilter)) {
				Adaptation record = null;
				int count = 1;
				ModuleLogger.getModuleLogger().info("requestResult :" + requestResult);
				while ((record = requestResult.nextAdaptation()) != null) {

					ModuleLogger.getModuleLogger().info(Integer.toString(count++));

					ValidationReport recordValidationReport = record.getValidationReport();
					if (recordValidationReport.hasItems()) {
						ValidationReportItemIterator validationItemIterator = recordValidationReport
								.getItemsOfSeverityOrMore(Severity.INFO);
						while (validationItemIterator.hasNext()) {
							ModuleLogger.getModuleLogger().info(record.getOccurrencePrimaryKey().format());
							ValidationReportItem validationItem = validationItemIterator.nextItem();
							UserMessage userMessage = validationItem.getMessage();
							if (userMessage != null) {
								String validationMessage = validationItem.getMessage().formatMessage(Locale.ENGLISH);
								if (validationMessage != null) {
									validationMessage = validationMessage.trim();
									validationMessage = validationMessage.toLowerCase();
									ModuleLogger.getModuleLogger().info("validationMessage :" + validationMessage);
									ModuleLogger.getModuleLogger().info("dataIssue :" + dataIssue);
									dataIssue = dataIssue.toLowerCase();
									if (validationMessage.contains(dataIssue)) {
										ValidationReportItemSubjectForAdaptation itemSubject = validationItem
												.getSubjectForAdaptation();
										SchemaNode validationNode = itemSubject.getSchemaNode();
										String configuredNodePath = node.getPathInAdaptation().format();
										String validationNodePath = validationNode.getPathInAdaptation().format();
										if (configuredNodePath.equalsIgnoreCase(validationNodePath)) {
											errorCount++;
											filterBuilder = buildLinkedRecordXPathFilter(filterBuilder,
													itemSubject.getAdaptation());
										}
									}
								}
							}
						}
					}
				}
			}
		}

		final ValueSequence sequence = context.newValueSequence().set(OUTPUT_RECORD_COUNT_WITH_DATA_ISSUE,
				Integer.valueOf(errorCount));

		if (context.isLinkedRecordEnabled())
			sequence.linkWithRecords(XPathFilter.newFilter(filterBuilder.toString()));

		return FunctionResult.of(sequence);

	}

	private static StringBuilder buildLinkedRecordXPathFilter(StringBuilder filterBuilder, Adaptation record) {

		if (filterBuilder.length() > 0)
			filterBuilder.append(" or ");

		return filterBuilder.append(record.toXPathPredicateString());
	}
}