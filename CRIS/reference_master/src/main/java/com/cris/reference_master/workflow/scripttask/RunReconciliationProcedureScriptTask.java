package com.cris.reference_master.workflow.scripttask;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.workflow.ScriptTaskBean;
import com.orchestranetworks.workflow.ScriptTaskBeanContext;

public class RunReconciliationProcedureScriptTask extends ScriptTaskBean {

	private String sqlProcedureName;

	public String getSqlProcedureName() {
		return sqlProcedureName;
	}

	public void setSqlProcedureName(String sqlProcedureName) {
		this.sqlProcedureName = sqlProcedureName;
	}

	@Override
	public void executeScript(ScriptTaskBeanContext arg0) throws OperationException {
		// TODO Auto-generated method stub

		FileReader reader;
		Properties prop = new Properties();
		try {
			reader = new FileReader(System.getProperties().getProperty("ebx.properties"));
			prop.load(reader);

			String jdbcURL = prop.getProperty("ebx.persistence.url");
			String username = prop.getProperty("ebx.persistence.user");
			String password = prop.getProperty("ebx.persistence.password");

			Connection connection = DriverManager.getConnection(jdbcURL, username, password);

			String sql = "call " + sqlProcedureName;
			Statement statement = connection.createStatement();
			statement.execute(sql);

			LoggingCategory.getWorkflow().info("===Procedure Executed====");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
