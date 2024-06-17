package com.cris.feedback.utils;

import com.onwbp.boot.VM;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.ui.UIComponentWriter;
import com.orchestranetworks.ui.UIFormLabelSpec;
import com.orchestranetworks.ui.form.UIFormWriter;
import com.orchestranetworks.ui.form.widget.UICheckBox;

public class UIUtils {
	
	public static void addTitleFormRow(final UIFormWriter pWriter, final String pTitle)
			throws IllegalArgumentException {
		if (pWriter == null) {
			throw new IllegalArgumentException("pWriter argument shall not be null");
		}
		pWriter.startFormRow(new UIFormLabelSpec(""));
		if (pTitle != null) {
			pWriter.add("<h3>");
			pWriter.addSafeInnerHTML(pTitle);
			pWriter.add("</h3>");
		}
		pWriter.endFormRow();
	}

	public static void standardizeFieldLabelWidth(final UIComponentWriter pWriter, final boolean pIndependantTab)
			throws IllegalArgumentException {
		if (pWriter == null) {
			throw new IllegalArgumentException("pWriter argument shall not be null");
		}
		pWriter.addJS_cr();
		pWriter.addJS_cr("function setLabelWidth(id) {");
		pWriter.addJS_cr("var query = 'td.ebx_Label';");
		pWriter.addJS_cr("if (id) {");
		pWriter.addJS_cr("query = '#' + id + ' ' + query;");
		pWriter.addJS_cr("}");
		pWriter.addJS_cr("var tdLabelList = document.querySelectorAll(query);");
		pWriter.addJS_cr("var maxWidth = 0;");
		pWriter.addJS_cr("for (var i = 0; i < tdLabelList.length; i++) {");
		pWriter.addJS_cr("var tdLabel = tdLabelList[i];");
		pWriter.addJS_cr("var width = tdLabel.clientWidth;");
		pWriter.addJS_cr("if (width > maxWidth) {");
		pWriter.addJS_cr("maxWidth = width;");
		pWriter.addJS_cr("}");
		pWriter.addJS_cr("}");
		final StringBuilder productInfo = new StringBuilder();
		VM.properties.getProductInfo(productInfo);
		if (productInfo.toString().contains("5.9")) {
			pWriter.addJS_cr("var defaultPadding = 2;");
		} else {
			pWriter.addJS_cr("var defaultPadding = 17;");
		}
		pWriter.addJS_cr("for (var i = 0; i < tdLabelList.length; i++) {");
		pWriter.addJS_cr("var tdLabel = tdLabelList[i];");
		pWriter.addJS_cr("tdLabel.style.minWidth = (maxWidth - defaultPadding) + 'px';");
		pWriter.addJS_cr("}");
		pWriter.addJS_cr("}");
		pWriter.addJS_cr();
		pWriter.addJS_cr("(function () {");
		if (pIndependantTab) {
			pWriter.addJS_cr("var tabs = document.querySelectorAll('.ebx_WorkspaceFormTabContent');");
			pWriter.addJS_cr("if (tabs.length > 0) {");
			pWriter.addJS_cr("for (var i = 0; i < tabs.length; i++) {");
			pWriter.addJS_cr("var tab = tabs[i];");
			pWriter.addJS_cr("setLabelWidth(tab.id);");
			pWriter.addJS_cr("}");
			pWriter.addJS_cr("} else {");
			pWriter.addJS_cr("setLabelWidth();");
			pWriter.addJS_cr("}");
		} else {
			pWriter.addJS_cr("setLabelWidth();");
		}
		pWriter.addJS_cr("})();");
		pWriter.addJS_cr();
	}

	public static void addCheckBox(final UIFormWriter pWriter, final Path path) {
		UICheckBox checkBox = pWriter.newCheckBox(path);
		pWriter.addFormRow(checkBox);
	}

	public static void clearStyle(final UIFormWriter pWriter) {
		// Clear the Style applied so far
		String clearDivStyle = "clear:both";
		// Clear Styling
		pWriter.add("<div style='" + clearDivStyle + "'>");
		pWriter.add("</div>");
	}

}
