package com.cris.feedback.extension;

import com.cris.feedback.path.FeedbackPath;
import com.cris.feedback.permission.FeedbackAccessRule;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaExtensions;
import com.orchestranetworks.schema.SchemaExtensionsContext;
import com.orchestranetworks.service.AccessRule;
import com.orchestranetworks.service.LoggingCategory;

public class FeedbackExtension implements SchemaExtensions{

	@Override
	public void defineExtensions(SchemaExtensionsContext context) {
		
		
		final Path path = FeedbackPath._Root_Feedback.getPathInSchema();
		final AccessRule FeedbackAccessRule = new FeedbackAccessRule();
		
		context.setAccessRuleOnOccurrence(path, FeedbackAccessRule);
		
	}

}
