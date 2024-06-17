package com.bct.olammodule;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Wrapper {

	protected String geturl() throws IOException {
		// TODO Auto-generated method stub
		FileReader reader = new FileReader(System.getProperties().getProperty("ebx.properties"));
		Properties prop = new Properties();
		prop.load(reader);
		String url = prop.getProperty("ebx.persistence.url");
		String user = prop.getProperty("ebx.persistence.user");
		String password = "admin"; //prop.getProperty("ebx.persistence.password");
//		String Connectionurl = url + "?currentSchema=" + url.substring(url.lastIndexOf("/") + 1) + "&user=" + user + "&password=" + password + ";";
		String Connectionurl = url + "?currentSchema=public&user=" + user + "&password=" + password + ";";
		return Connectionurl;
	}
	
	public String masterViewMatrix(String Plant, String Commodity, String Material_type, String Procurement_type,
			String View_Name) {

		String accessPermissionString = "";

		try {
			String connectionUrl = geturl();
			ResultSet resultSet = null;

			Connection connection = DriverManager.getConnection(connectionUrl);
			Statement statement = connection.createStatement();

			// Create and execute a SELECT SQL statement.

			resultSet = statement.executeQuery("select accessPermission from dataSecurity where plant='" + Plant
					+ "' and commodity='" + Commodity + "' and materialType='" + Material_type + "' and\r\n"
					+ "procurementType='" + Procurement_type + "' and uiGroup='" + View_Name + "'");

			// Print results from select statement

			while (resultSet.next()) {
				accessPermissionString = resultSet.getString(1);
			}

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accessPermissionString;

		// TODO Auto-generated method stub

	}
	
	
}
