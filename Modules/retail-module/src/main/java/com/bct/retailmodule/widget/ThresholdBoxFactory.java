package com.bct.retailmodule.widget;

import java.math.BigDecimal;

import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.schema.SchemaTypeName;
import com.orchestranetworks.ui.form.widget.UIWidgetFactory;
import com.orchestranetworks.ui.form.widget.WidgetFactoryContext;
import com.orchestranetworks.ui.form.widget.WidgetFactorySetupContext;

public class ThresholdBoxFactory implements UIWidgetFactory<ThresholdBox>{

	private BigDecimal threshold;
	private String image;
	
	public final void setThreshold(final BigDecimal threshold) {
		this.threshold = threshold;
	}
	
	public final void setImage(final String image) {
		this.image = image;
	}

	@Override
	public ThresholdBox newInstance(WidgetFactoryContext context) {
		// TODO Auto-generated method stub
		return new ThresholdBox(context,this.threshold, this.image);
	}

	@Override
	public void setup(WidgetFactorySetupContext context) {
		
		final SchemaNode schemaNode = context.getSchemaNode();
		final SchemaTypeName schemaTypeName = schemaNode.getXsTypeName();
		
		if(!schemaTypeName.equals(SchemaTypeName.XS_DECIMAL)
				&& !schemaTypeName.equals(SchemaTypeName.XS_INT)) {
			//context.addMessage(Messages.get(ThresholdBoxFactory.class,Severity.ERROR,
				//	"TheAttributeMustHaveNumberType"));
		}
		
		if(this.threshold == null) {
			//context.addMessage(Messages.get(ThresholdBoxFactory.class,Severity.ERROR,
				//	"TheThresholdIsMandatory"));
		}
		if(this.image == null) {
			//context.addMessage(Messages.get(ThresholdBoxFactory.class,Severity.ERROR,
				//	"TheImageIsMandatory"));
		}
		
	}
	
	}
	
	
