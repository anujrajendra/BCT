package com.bct.olammodule.form;

import com.bct.olammodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.ui.form.UIForm;
import com.orchestranetworks.ui.form.UIFormBody;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPaneWithTabs;

public class MaterialPlantForm extends UIForm{

	private static final String String = null;

	@Override
	public void defineBody(UIFormBody aBody, UIFormContext aContext) {
		// TODO Auto-generated method stub
		
		ValueContext valueContext = aContext.getValueContext();
		final String basicMaterialId = (String) valueContext.getValue(Path.PARENT.add(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_BasicMaterialID));
		final String plantPkString = (String) valueContext.getValue(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_Plant);
		String plantPk="";
		if(plantPkString!=null)
				plantPk = plantPkString.substring(plantPkString.length()-4);
		
		Adaptation adaptationDataset =  valueContext.getAdaptationInstance();
		
		
		UIFormPaneWithTabs uiFormPaneWithTabs = new UIFormPaneWithTabs();
		uiFormPaneWithTabs.addTab("Initial screen", null);
		super.defineBody(aBody, aContext);
	}

}
