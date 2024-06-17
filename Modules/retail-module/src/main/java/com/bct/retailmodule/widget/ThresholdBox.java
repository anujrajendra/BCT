package com.bct.retailmodule.widget;

import java.math.BigDecimal;

import com.onwbp.base.misc.StringUtils;
import com.onwbp.com.ibm.icu.text.MessageFormat;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.ui.form.widget.UISimpleCustomWidget;
import com.orchestranetworks.ui.form.widget.WidgetDisplayContext;
import com.orchestranetworks.ui.form.widget.WidgetFactoryContext;
import com.orchestranetworks.ui.form.widget.WidgetWriter;

public class ThresholdBox extends UISimpleCustomWidget {

	private static final String TABLE_STYLE = "background-image: url(''{0}'')"
			+ "background-repeat: no-repeat; background-position: left center; padding-left: 20px";
	private static final String FORM_STYLE = "background-image: url(''{0}'')"
			+ "background-repeat: no-repeat; background-position: right center; padding-left: 20px";;
	
	private final BigDecimal threshold;
	private final String image;
	
	ThresholdBox(WidgetFactoryContext context, BigDecimal threshold, String image) {
		super(context);
		this.threshold = threshold;
		this.image = image;
	}

	@Override
	public void write(WidgetWriter writer, WidgetDisplayContext context) {
		
		final String style = getStyle(context);
		
		writer.add("<span").addSafeAttribute("style", style).add(">");
		writer.addWidget(Path.SELF);
		writer.add("</span>");
		
	}

	private String getStyle(WidgetDisplayContext context) {

		final ValueContext valueContext = context.getValueContext();
		final Object value = valueContext.getValue(Path.SELF);
		
		if(value==null) {
			return StringUtils.EMPTY_STRING;
		}
		if(this.threshold.compareTo(new BigDecimal(value.toString()))<0) {
			return StringUtils.EMPTY_STRING;
		}
		if(context.isDisplayedInTable()) {
			return MessageFormat.format(TABLE_STYLE,this.image);
		}
		if(context.isDisplayedInForm()) {
			return MessageFormat.format(FORM_STYLE,this.image);
		}
				
		return StringUtils.EMPTY_STRING;
	}

	
}
