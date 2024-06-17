package com.cris.custom.indicator.function;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.onwbp.adaptation.XPathFilter;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.addon.dpra.ChartType;
import com.orchestranetworks.addon.dpra.DPRAException;
import com.orchestranetworks.addon.dpra.function.FunctionDefinition;
import com.orchestranetworks.addon.dpra.function.FunctionDefinitionRegistry;
import com.orchestranetworks.addon.dpra.function.OutputDefinition;
import com.orchestranetworks.addon.dpra.function.chart.AxisConfig;
import com.orchestranetworks.addon.dpra.function.chart.ChartConfigForFunction;
import com.orchestranetworks.addon.dpra.function.execution.context.ExecutionContextOnTable;
import com.orchestranetworks.addon.dpra.function.execution.result.FunctionResult;
import com.orchestranetworks.addon.dpra.function.execution.result.ValueSequence;

public final class CustomRecordCountFunction {
	private static final String OUTPUT_COUNT = "recordCount";
	private static final OutputDefinition COUNT_OUTPUT_DEF = OutputDefinition.forInteger(OUTPUT_COUNT,
			UserMessage.createInfo("Record count"), null);

	public static final FunctionDefinition DEFINITION = FunctionDefinition.onTable("CustomRecordCountFunction")
			.withLabel("Record count").withDescription("This function counts the number of records in a table.")
			.withOutputs(COUNT_OUTPUT_DEF).withLinkedRecord().onExecution(CustomRecordCountFunction::execute).build();

	public static final ChartConfigForFunction CHART_CONFIG = ChartConfigForFunction
			.newBuilder(AxisConfig.outputLabelAsCategory(OUTPUT_COUNT)).setDefaultChartType(ChartType.CARD)
			.addOtherChartTypes(ChartType.COLUMN).build();
	
	private static FunctionResult execute(ExecutionContextOnTable context) throws DPRAException {
		
		
		
		final AdaptationTable table = context.getCurrentTable();
		final String xpathPredicate = context.getXpathFilterPredicate();
		final boolean hasFilter = xpathPredicate != null && xpathPredicate.trim().length() > 0;
		try (RequestResult requestResult = hasFilter ? table.createRequestResult(xpathPredicate)
				: table.createRequest().execute()) {
			final int tableCount = requestResult.getSize();
			final ValueSequence sequence = context.newValueSequence().set(OUTPUT_COUNT, Integer.valueOf(tableCount));

			if (context.isLinkedRecordEnabled()) {
				if (hasFilter) {
					sequence.linkWithRecords(xpathPredicate);
				} else {
					sequence.linkWithRecords(buildLinkedRecordXPathFilter(requestResult));
				}
			}
			return FunctionResult.of(sequence);
		}
	}

	private static XPathFilter buildLinkedRecordXPathFilter(RequestResult requestResult) {
		final StringBuilder builder = new StringBuilder();
		for (Adaptation record; (record = requestResult.nextAdaptation()) != null;) {
			if (builder.length() > 0) {
				builder.append(" or ");
			}
			builder.append(record.toXPathPredicateString());
		}
		return XPathFilter.newFilter(builder.toString());
	}

	private CustomRecordCountFunction() {
		FunctionDefinitionRegistry.register(DEFINITION, CHART_CONFIG);
	}	
}