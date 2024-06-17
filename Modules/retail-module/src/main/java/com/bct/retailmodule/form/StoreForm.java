package com.bct.retailmodule.form;

import com.orchestranetworks.ui.UIFormLabelSpec;
import com.orchestranetworks.ui.form.UIForm;
import com.orchestranetworks.ui.form.UIFormBody;
import com.orchestranetworks.ui.form.UIFormBottomBar;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormHeader;
import com.orchestranetworks.ui.form.UIFormPaneWithTabs;

public class StoreForm extends UIForm {

	@Override
	public void defineHeader(UIFormHeader aHeader, UIFormContext aContext) {
	
		if (aContext.isCreatingRecord()) {
			
			aHeader.setTitle(new UIFormLabelSpec("NewStore"));
		}
	}
	
	@Override
	public void defineBody(UIFormBody aBody, UIFormContext aContext) {
		
		final UIFormPaneWithTabs rootPane = new UIFormPaneWithTabs();
		
		rootPane.addTab("Main", new StoreMainPane());
		rootPane.addTab("Location", new StoreLocationPane());
		rootPane.addTab("Inventories", new StoreInventoriesPane());
		
		aBody.setContent(rootPane);
	}
	
	@Override
	public void defineBottomBar(UIFormBottomBar aBottomBar, UIFormContext aContext) {
		
		if(aContext.isCreatingRecord()) {
			aBottomBar.setSubmitButtonDisplayable(true);
			aBottomBar.setSubmitAndCloseButtonDisplayable(true);
			aBottomBar.setCloseButtonDisplayable(true);
		}
	}
	
}
