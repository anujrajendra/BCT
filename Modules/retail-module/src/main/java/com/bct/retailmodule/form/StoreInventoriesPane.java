package com.bct.retailmodule.form;

import com.bct.retailmodule.Paths;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPane;
import com.orchestranetworks.ui.form.UIFormPaneWriter;

public class StoreInventoriesPane implements UIFormPane {

	@Override
	public void writePane(UIFormPaneWriter writer, UIFormContext arg1) {
		writer.addWidget(Paths._Store._Inventories);
	}
}
