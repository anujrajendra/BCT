package com.bct.retailmodule.form;

import java.net.URLEncoder;
import java.text.MessageFormat;

import com.bct.retailmodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.ui.form.UIFormContext;
import com.orchestranetworks.ui.form.UIFormPane;
import com.orchestranetworks.ui.form.UIFormPaneWriter;

public class StoreLocationPane implements UIFormPane {

	public static final String ADDRESS_STYLE = "margin-bottom:10px";
	public static final String MAP_STYLE = "margin-top:10px; width:600px; height:450px";
	public static final String MAP_URL = "https://maps.google.com?q={0},{1},{2},{3},{4}&output=embed";
	public static final String URL_ENCODING = "UTF-8";
	
	@Override
	public void writePane(UIFormPaneWriter writer, UIFormContext context) {

		writer.add("<div").addSafeAttribute("style", ADDRESS_STYLE).add(">");
		writer.addWidget(Paths._Store._Address_Street);
		writer.addWidget(Paths._Store._Address_City);
		writer.addWidget(Paths._Store._Address_Region);
		writer.addWidget(Paths._Store._Address_Postcode);
		writer.addWidget(Paths._Store._Address_Country);
		writer.add("</div>");
		
		final Adaptation store = context.getCurrentRecord();
		if (store == null) {
		return;
		}
		final String street = (String) store.get(Paths._Store._Address_Street);
		final String city = (String) store.get(Paths._Store._Address_City);
		final String region = (String) store.get(Paths._Store._Address_Region);
		final String postcode = (String) store.get(Paths._Store._Address_Postcode);
		final String country = (String) store.get(Paths._Store._Address_Country);
		final String mapUrl = MessageFormat.format(MAP_URL, encode(street), encode(city),
		encode(region), encode(postcode), encode(country));
		writer.add("<iframe").addSafeAttribute("style", MAP_STYLE).addSafeAttribute("src", mapUrl)
		.add(">");
		writer.add("</iframe>");
		}
	
	private static String encode(final String string) {
		try {
		return URLEncoder.encode(string, URL_ENCODING);
		} catch (final Exception exception) {
		return "";
		}
		}

	}
