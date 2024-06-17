package com.cris.user_registration.form;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.cris.user_registration.Paths;
import com.cris.user_registration.utils.UIUtils;
import com.orchestranetworks.ui.base.JsFunctionCall;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPane;
import com.orchestranetworks.ui.form.UIFormPaneWriter;
import com.orchestranetworks.ui.form.widget.UITextBox;

public class UserRegistrationMainPane implements UIFormPane {

	@Override
	public void writePane(UIFormPaneWriter pWriter, UIFormContext aContext) {
		// TODO Auto-generated method stub

		addHRMSFields(pWriter);
		addJSFunction(pWriter);
		UIUtils.standardizeFieldLabelWidth(pWriter, true);
	}

	private void addJSFunction(UIFormPaneWriter pWriter) {

		String hrmsURL = UserRegistrationMainPane.readHRMSURL();
		System.out.println("===hrms URL===" + hrmsURL);
		pWriter.addJS("function apiCall(newValue) {\r\n" + " console.log(newValue); \r\n"
				+ " if (newValue === null || newValue === undefined || newValue === '') { return; } \r\n"
				+ "	var ipasId = newValue; " + " var userName; " + " var userDepartment; " + " var userDesignation; "
				+ " var userZone = {key: \"\"}; " + " var userServingStatus;" + " \r\n" + "	fetch('" + hrmsURL
				+ "' + newValue, {method:\"POST\", body: {}, headers: {\r\n"
				+ "        \"Content-type\": \"application/json; charset=UTF-8\"\r\n" + "    }}) \r\n"
				+ "		.then(response => { \r\n" + "			if (response.ok) { \r\n"
				+ "				return response.json(); \r\n" + "			} else { \r\n"
				+ "				throw new Error('API request failed'); \r\n" + "		} \r\n" + "	}) \r\n"
				+ "	.then(data => { \r\n" + " 	try{userName = data[0].employee_name;"
				+ " userDepartment = data[0].department;" + " userDesignation = data[0].designation;"
				+ " userZone.key = data[0].railway_main_unit; " + " userServingStatus = data[0].serving_status; }\r\n"
				+ "		catch(err){" + "userName=''; " + "userDepartment=''; " + "userDesignation=''; "
				+ "userZone.key=''; " + "userServingStatus = ''; " + "ipasId='';  "
				+ "ebx_alert('Enter only valid IPAS ID')\r\n" + " ; }\r\n");

		pWriter.addJS_setNodeValue("userName",
				Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Name);
		pWriter.addJS_setNodeValue("userDepartment",
				Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Department);
		pWriter.addJS_setNodeValue("userDesignation",
				Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Designation);

		pWriter.addJS_setNodeValue("userZone",
				Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Zone);
		pWriter.addJS_setNodeValue("userServingStatus",
				Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Service_Status);

		pWriter.addJS_setNodeValue("ipasId",
				Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Ipas_Id);

		pWriter.addJS("		console.log('HRMS'); \r\n" + "	}) \r\n" + "	.catch(error => { \r\n"
				+ "		 console.error(error); ipasId=''; ebx_alert('Error in accessing HRMS service'); \r\n"
				+ "	});\r\n");

		pWriter.addJS("}");
	}

	private void addHRMSFields(UIFormPaneWriter pWriter) {

		JsFunctionCall jsFunctionCall = JsFunctionCall.on("apiCall");
		UITextBox ipasId = pWriter.newTextBox(
				Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Ipas_Id);
		ipasId.setActionOnAfterValueChanged(jsFunctionCall);

		// IPAS Id
		pWriter.startTableFormRow();
		pWriter.addFormRow(ipasId);
		pWriter.endTableFormRow();

		pWriter.add("<div id=\"readOnlyFields\" style=\"pointer-events: none;\">");
		{
			pWriter.startTableFormRow();
			{

				pWriter.addFormRow(
						Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Name);
				pWriter.addFormRow(
						Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Designation);
				pWriter.addFormRow(
						Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Department);
				pWriter.addFormRow(
						Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Service_Status);
				pWriter.addFormRow(
						Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Zone);
			}
			pWriter.endTableFormRow();
		}
		pWriter.add("</div>");

		pWriter.startTableFormRow();
		{
			// pWriter.addFormRow(Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Last_Name);
			pWriter.addFormRow(
					Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Divison);
			pWriter.addFormRow(
					Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Shed);
			pWriter.addFormRow(
					Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Entity);
			pWriter.addFormRow(
					Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_User_Type);
			pWriter.addFormRow(
					Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Mdm_User_Id);
//			pWriter.addFormRow(Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_From_Date);
//			pWriter.addFormRow(Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_To_Date);
			pWriter.addFormRow(
					Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Mobile_Number);
			pWriter.addFormRow(
					Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Email);
//			pWriter.addFormRow(Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Created_By);
//			pWriter.addFormRow(Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Loco_Type);
//			pWriter.addFormRow(
//					Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_User_Register_Approval);
			pWriter.addFormRow(
					Paths._Root_Business_Users_Registration_Details._Root_Business_Users_Registration_Details_Asset_Designation_Code);
		}
		pWriter.endTableFormRow();
	}

	private static String readHRMSURL() {

		FileReader reader;
		Properties prop = new Properties();
		try {
			reader = new FileReader(System.getProperties().getProperty("ebx.properties"));
			prop.load(reader);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop.getProperty("ebx.hrms.url");

	}
}