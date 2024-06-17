package com.bct.retailmodule.form;

import com.bct.retailmodule.Paths;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPane;
import com.orchestranetworks.ui.form.UIFormPaneWriter;
import com.orchestranetworks.ui.form.UIFormRow;
import com.orchestranetworks.ui.form.widget.UIRadioButtonGroup;

public class StoreMainPane implements UIFormPane {

	public static final String LEFT_CELL_STYLE = "width:50%; padding-right:20px; vertical-align:top";
	public static final String RIGHT_CELL_STYLE = "width:50%; padding-left:20px; vertical-align:top";
	
	@Override
	public void writePane(UIFormPaneWriter writer, UIFormContext context) {
	
		writer.add("<table>");
		writer.add("<tr>");
		writer.add("<td").addSafeAttribute("style", LEFT_CELL_STYLE).add(">");
		writer.startTableFormRow();
		writer.addFormRow(Paths._Store._Identifier);
		writer.addFormRow(Paths._Store._Name);
		writer.endTableFormRow();
		writer.add("</td>");
		
		final UIFormRow typeFormRow = writer.newFormRow(Paths._Store._Type);
		final UIRadioButtonGroup typeWidget = writer.newRadioButtonGroup(Paths._Store._Type);
		
		typeWidget.setColumnsNumber(1);
		writer.add("<td").addSafeAttribute("style", RIGHT_CELL_STYLE).add(">");
		writer.startTableFormRow();
		writer.addFormRow(typeFormRow, typeWidget);
		writer.endTableFormRow();
		writer.add("</td>");
		writer.add("</tr>");
		writer.add("</table>");

	}

}
