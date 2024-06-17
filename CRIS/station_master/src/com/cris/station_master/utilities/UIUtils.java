package com.cris.station_master.utilities;

import com.orchestranetworks.ui.UIComponentWriter;

public class UIUtils {

	public static void standardizeFieldLabelWidth(final UIComponentWriter pWriter, final boolean pIndependantTab)
			throws IllegalArgumentException {
		if (pWriter == null) {
			throw new IllegalArgumentException("pWriter argument shall not be null");
		}
		pWriter.addJS_cr();
		pWriter.addJS_cr("function setLabelWidth(id) {");
		pWriter.addJS_cr("    var query = 'td.ebx_Label';");
		pWriter.addJS_cr("    if (id) {");
		pWriter.addJS_cr("        query = '#' + id + ' ' + query;");
		pWriter.addJS_cr("    }");
		pWriter.addJS_cr("    var tdLabelList = document.querySelectorAll(query);");
		pWriter.addJS_cr("    var maxWidth = 0;");
		pWriter.addJS_cr("    for (var i = 0; i < tdLabelList.length; i++) {");
		pWriter.addJS_cr("        var tdLabel = tdLabelList[i];");
		pWriter.addJS_cr("        var width = tdLabel.clientWidth;");
		pWriter.addJS_cr("        if (width > maxWidth) {");
		pWriter.addJS_cr("            maxWidth = width;");
		pWriter.addJS_cr("        }");
		pWriter.addJS_cr("    }");
		final StringBuilder productInfo = new StringBuilder();
//		VM.properties.getProductInfo(productInfo);
		if (productInfo.toString().contains("5.9")) {
			pWriter.addJS_cr("    var defaultPadding = 0;");
		} else {
			pWriter.addJS_cr("    var defaultPadding = 0;");
		}
		pWriter.addJS_cr("    for (var i = 0; i < tdLabelList.length; i++) {");
		pWriter.addJS_cr("        var tdLabel = tdLabelList[i];");
		pWriter.addJS_cr("        tdLabel.style.minWidth = (maxWidth - defaultPadding) + 'px';");
		pWriter.addJS_cr("    }");
		pWriter.addJS_cr("}");
		pWriter.addJS_cr();
		pWriter.addJS_cr("(function () {");
		if (pIndependantTab) {
			pWriter.addJS_cr("    var tabs = document.querySelectorAll('.ebx_WorkspaceFormTabContent');");
			pWriter.addJS_cr("    if (tabs.length > 0) {");
			pWriter.addJS_cr("        for (var i = 0; i < tabs.length; i++) {");
			pWriter.addJS_cr("            var tab = tabs[i];");
			pWriter.addJS_cr("            setLabelWidth(tab.id);");
			pWriter.addJS_cr("        }");
			pWriter.addJS_cr("    } else {");
			pWriter.addJS_cr("        setLabelWidth();");
			pWriter.addJS_cr("    }");
		} else {
			pWriter.addJS_cr("    setLabelWidth();");
		}
		pWriter.addJS_cr("})();");
		pWriter.addJS_cr();
	}
}