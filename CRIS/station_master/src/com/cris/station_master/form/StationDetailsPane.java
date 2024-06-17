package com.cris.station_master.form;

import com.cris.station_master.paths.*;
import com.cris.station_master.utilities.UIUtils;
import com.orchestranetworks.ui.ResourceType;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPane;
import com.orchestranetworks.ui.form.UIFormPaneWriter;

public class StationDetailsPane implements UIFormPane {

	@Override
	public void writePane(UIFormPaneWriter writer, UIFormContext context) {

		String cssUrl = context.getURLForResource(ResourceType.STYLESHEET, "UIFormCSS.css");
		writer.add("<link rel='stylesheet' type='text/css' href='").add(cssUrl).add("'>");

		writer.add("<div class='flex'>");

		addStationAttributes(writer, context);
		writer.add("<hr>");
		addDepartmentAttributes(writer, context);

		writer.add("</div>");

		addJSForBookingResource(writer, context);
		UIUtils.standardizeFieldLabelWidth(writer, true);
	}

	void addStationAttributes(UIFormPaneWriter writer, UIFormContext context) {

		writer.add("<div class='left' style='width:100%'>");

		writer.add("<h3>Station Identification Details</h3>");
		writer.add("<hr>");

		writer.add("<table><tr><td class='lefttd' style='vertical-align: top; width: 50%'>");
		{
			writer.add("<div style=\"padding-left:2px;\" >");
			{
				writer.startTableFormRow();
				{
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Station_Code);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Station_Name);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Zone_Code);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Station_Valid_From);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Latitude);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Station_Short_Name);
				}
				writer.endTableFormRow();
			}
			writer.add("</div>");
		}
		writer.add("</td><td class='centertd'>");
		writer.add("</td><td class='righttd' style='vertical-align: top; width: 50%'>");
		{
			writer.startTableFormRow();
			{
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Station_Numeric_Code);
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Station_Hindi_Name);
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Division_Code);
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Station_Valid_Upto);
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Longitude);
			}
			writer.endTableFormRow();
		}
		writer.add("</td></tr></table>");
		writer.add("</div>");

		writer.add("<hr>");

	}

	void addDepartmentAttributes(UIFormPaneWriter writer, UIFormContext context) {

		writer.add("<div class='left' style='width:100%'>");
		writer.add("<table><tr><td class='lefttd' style='vertical-align: top; width: 50%'>");
		{
			writer.add("<div style=\"padding-left:2px;\" >");
			{
				writer.add("<h3>CMI Details</h3>");
				writer.add("<hr>");

				writer.startTableFormRow();
				{
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Traffic_Type);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Station_Class);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Booking_Type);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Working_Division);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_State);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Pincode);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_District);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Tehsil);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Weight_Bridge);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Transhipment_Flag);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Siding);
					writer.addFormRow(StationPaths._Root_Station._Root_Station_Is_Station_Functional);
					// writer.addFormRow(StationPaths._Root_Station._Root_Station_Booking_Resource);
				}
				writer.endTableFormRow();

				writer.add("<div id='bookingResource'>");
				writer.startTableFormRow();
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Booking_Resource);
				writer.endTableFormRow();
				writer.add("</div>");
			}
			writer.add("</div>");
		}
		writer.add("</td><td class='centertd'>");
		writer.add("</td><td class='righttd' style='vertical-align: top; width: 50%'>");
		{

			writer.add("<h3>DTI Details</h3>");
			writer.add("<hr>");

			writer.startTableFormRow();
			{
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Station_Category);
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Traction);
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Gauge_Code);
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Interchange_Flag);
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Block_Section);
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Interlocking_Standard);
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Junction_Flag);
				writer.addFormRow(StationPaths._Root_Station._Root_Station_No_Of_Lines);
				writer.addFormRow(StationPaths._Root_Station._Root_Station_Operating_Station_Signal);
			}
			writer.endTableFormRow();
		}
		writer.add("</td></tr></table>");
		writer.add("</div>");

	}

	void addJSForBookingResource(UIFormPaneWriter writer, UIFormContext context) {

		writer.addJS("function handleBookingResource(isStationFunctional) { \r\n"
				+ " console.log(isStationFunctional); \r\n "
				+ " if(isStationFunctional !== null && isStationFunctional === 'Y') { \r\n"
				+ "     document.getElementById('bookingResource').style.pointerEvents = \"auto\"; \r\n " + "     \r\n"
				+ "  } else { \r\n" + "     var noValue = null; \r\n "
				+ "     document.getElementById('bookingResource').style.pointerEvents = \"none\"; \r\n "
				+ "     \r\n");

		writer.addJS_setNodeValue("noValue", StationPaths._Root_Station._Root_Station_Booking_Resource);
		writer.addJS("} \r\n" + "} \r\n");

		String isStationFunctional = (String) context.getValueContext()
				.getValue(StationPaths._Root_Station._Root_Station_Is_Station_Functional);

		writer.addJS("handleBookingResource(" + isStationFunctional + ");\r\n");
	}
}