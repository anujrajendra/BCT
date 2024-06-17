package com.bct.addon.dxch.util;

import java.security.*;
import java.sql.*;
import java.util.*;
import java.util.stream.*;

import javax.naming.*;
import javax.sql.*;

import com.bct.addon.dxch.datamodelgenerator.*;
import com.bct.addon.dxch.exception.*;
import com.onwbp.base.text.*;
import com.orchestranetworks.addon.dint.common.constant.*;
import com.orchestranetworks.addon.dint.common.util.*;
import com.orchestranetworks.addon.dint.dataaccess.sql.*;
import com.orchestranetworks.addon.utils.*;

public class SQLDatabaseUtil {
	public static void testConnection(final DatabaseIdentification id) throws SQLException, NamingException {
		SQLDatabaseConnection databaseConnection = SQLDatabaseConnection.getSQLDBConnectionByIdentification(id);
		Connection connection = databaseConnection.getConnection();
		if (connection != null) {
			connection.close();
		}
	}

	public static DatabaseIdentification getDatabaseIdentification(
			final DatabaseDataSourceTable databaseDataSourceTable) {
		DatabaseIdentification identification = null;
		String authentication = databaseDataSourceTable.getAuthentication();
		if (AddonStringUtils.isEmpty(authentication)) {
			return identification;
		}
		DintConstants.DatabaseAuthenticationMode authenMode = DintConstants.DatabaseAuthenticationMode
				.parse(authentication);
		switch (authenMode) {
		case DIRECT:
			identification = SQLDatabaseUtil.getDirectModeIdentification(databaseDataSourceTable);
			return identification;
		case EXTERNAL_SYSTEM:
			identification = SQLDatabaseUtil.getExtSystemIdentification(databaseDataSourceTable);
			return identification;
		}
		throw new IllegalArgumentException("Unsupported authentication mode: " + authenMode);
	}

	private static DatabaseIdentification getExtSystemIdentification(
			final DatabaseDataSourceTable databaseDataSourceTable) {
		String url = databaseDataSourceTable.getUrl();
		String accessKey = databaseDataSourceTable.getAccessKey();
		return new DatabaseIdentification(url, accessKey, null);
	}

	private static DatabaseIdentification getDirectModeIdentification(
			final DatabaseDataSourceTable databaseDataSourceTable) {
		String url = databaseDataSourceTable.getUrl();
		String user = databaseDataSourceTable.getUsername();
		String password = databaseDataSourceTable.getPassword();
		return new DatabaseIdentification(url, user, password);
	}

	public static Map<String, String> getTableAndViewBySchema(final SQLDataSourceTable sqlDataSourceTable)
			throws DataIntegrationException, com.orchestranetworks.addon.dint.DataIntegrationException {
		try {
			SQLDatabaseConnection databaseConnection = SQLDatabaseUtil.getSQLConnection(sqlDataSourceTable);
			if (databaseConnection == null) {
				return Collections.emptyMap();
			}
			return SQLDatabaseUtil
					.sortTables(SQLSchemaUtil.getTableAndViewBySchema(databaseConnection, sqlDataSourceTable

							.getSchemaNamePattern(), sqlDataSourceTable.getTableNamePattern()));
		} catch (SQLException | NamingException | GeneralSecurityException ex) {
			throw new DataIntegrationException(UserMessage.createError(ex.getMessage()), ex);
		}
	}

	public static Map<String, String> getTableBySchema(final SQLDataSourceTable sqlDataSourceTable)
			throws DataIntegrationException, com.orchestranetworks.addon.dint.DataIntegrationException {
		try {
			SQLDatabaseConnection databaseConnection = SQLDatabaseUtil.getSQLConnection(sqlDataSourceTable);
			if (databaseConnection == null) {
				return Collections.emptyMap();
			}
			return SQLDatabaseUtil.sortTables(SQLSchemaUtil.getTableBySchema(databaseConnection, sqlDataSourceTable

					.getSchemaNamePattern(), sqlDataSourceTable.getTableNamePattern()));
		} catch (SQLException | NamingException | GeneralSecurityException ex) {
			throw new DataIntegrationException(UserMessage.createError(ex.getMessage()), ex);
		}
	}

	private static SQLDatabaseConnection getSQLConnection(final SQLDataSourceTable sqlDataSourceTable)
			throws NamingException, GeneralSecurityException, com.orchestranetworks.addon.dint.DataIntegrationException {
		String dataSourceName = sqlDataSourceTable.getName();
		if (SQLDatabaseUtil.hasJNDINamesInApplicationServer(dataSourceName)) {
			DataSource ds = JNDIUtil.getJNDIDataSource(dataSourceName);
			return SQLDatabaseConnection.getSQLDBConnectionByDataSource(ds);
		}
		ConfigurationDataAccess configurationDataAccess = ConfigurationDataAccess.getInstance();
		Optional<DatabaseDataSourceTable> dataSourceTable = configurationDataAccess
				.getTableDatabaseDataSourceByName(dataSourceName);
		if (dataSourceTable.isEmpty()) {
			return null;
		}
		DatabaseIdentification databaseIdentification = SQLDatabaseUtil
				.getDatabaseIdentification(dataSourceTable.get());
		if (databaseIdentification == null) {
			return null;
		}
		return SQLDatabaseConnection.getSQLDBConnectionByIdentification(databaseIdentification);
	}

	private static Map<String, String> sortTables(final Map<String, String> tables) {
		return tables.entrySet().stream()
				.sorted((table1, table2) -> table1.getKey().compareToIgnoreCase(table2.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (table1, table2) -> table1,
						java.util.LinkedHashMap::new));
	}

	public static boolean hasJNDINamesInApplicationServer(final String dataSourceName) {
		return JNDIUtil.getJNDINamesInApplicationServer().contains(dataSourceName);
	}
}
