package com.bct.retailmodule.filter;

import java.util.Locale;

import com.bct.retailmodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.schema.TableRefFilter;
import com.orchestranetworks.schema.TableRefFilterContext;

public class ItemCategoryFilter implements TableRefFilter{

	@Override
	public boolean accept(final Adaptation category, final ValueContext context) {
		
		final Integer categoryIdentifier = (Integer) category.get(Paths._Category._Identifier);
		final AdaptationTable categoryTable = category.getContainerTable();
		
		final String predicate = Paths._Category._Parent.format() + "='" +categoryIdentifier +"'";
		
		final RequestResult requestResult = categoryTable.createRequestResult(predicate);
					
		return requestResult.isEmpty();
	}

	@Override
	public void setup(final TableRefFilterContext context) {
		final SchemaNode itemCategoryNode =  context.getSchemaNode();
		final SchemaNode categoriesNode = itemCategoryNode.getNode(Paths._Category.getPathInSchema());
		
		context.addDependencyToInsertDeleteAndModify(categoriesNode);
	}

	@Override
	public String toUserDocumentation(final Locale locale, final ValueContext context) throws InvalidSchemaException {
		
		return null; //Messages.get(ItemCategoryFilter.class, locale, "TheCategoryMustBeLeafCategory");
	}

}
