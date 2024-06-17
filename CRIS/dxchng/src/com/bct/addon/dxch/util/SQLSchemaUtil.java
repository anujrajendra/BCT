package com.bct.addon.dxch.util;

import java.sql.*;
import java.util.*;

import javax.naming.*;

import com.bct.addon.dxch.dto.*;
import com.onwbp.com.google.gson.*;
import com.orchestranetworks.addon.dint.service.dataconnector.sql.*;
import com.orchestranetworks.addon.utils.*;

public final class SQLSchemaUtil {

	private static boolean isSqlServerTimeStamp(final DatabaseMetaData metaData, final String columnTypeName)
			throws SQLException {
		return ("Microsoft SQL Server".equalsIgnoreCase(metaData.getDatabaseProductName())
				&& "timestamp".equalsIgnoreCase(columnTypeName));
	}

	public static List<String> getFieldNamesWithUnsupportedDataType(final SQLDatabaseConnection databaseConnection,
			final String tableName, final String tableSchema) throws SQLException {
		Map<String, String> fieldNames = SQLSchemaUtil.getUnsupportedDataTypeByFieldName(databaseConnection, tableName,
				tableSchema);
		List<String> fieldNamesBuild = new ArrayList<>();
		for (Map.Entry<String, String> fieldName : fieldNames.entrySet()) {
			StringBuilder builder = new StringBuilder("'");
			builder.append(fieldName.getKey());
			builder.append("'");
			builder.append(" - ");
			builder.append(fieldName.getValue());
			fieldNamesBuild.add(builder.toString());
		}
		return fieldNamesBuild;
	}

	private static Map<String, String> getUnsupportedDataTypeByFieldName(final SQLDatabaseConnection databaseConnection,
			final String tableName, final String tableSchema) throws SQLException {
		Map<String, String> unsupportedFieldNames = new LinkedHashMap<>();
		Connection connection = databaseConnection.getConnection();
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, tableSchema, tableName, "%");
			while (resultSet.next()) {
				String colName = resultSet.getString("COLUMN_NAME");
				String columnTypeName = resultSet.getString("TYPE_NAME");
				Optional<SQLDataTypeName> sqlTypeName = SQLDataTypeName.getType(columnTypeName);
				if (!sqlTypeName.isPresent() || SQLSchemaUtil.isSqlServerTimeStamp(metaData, columnTypeName)) {
					unsupportedFieldNames.put(colName, columnTypeName);
				}
			}
			Map<String, String> map = unsupportedFieldNames;
			if (connection != null) {
				connection.close();
			}
			return map;
		} catch (Throwable throwable) {
			if (connection != null) {
				try {
					connection.close();
				} catch (Throwable throwable1) {
					throwable.addSuppressed(throwable1);
				}
			}
			throw throwable;
		}
	}

	private static List<String> getPrimaryKeyColumns(final Connection connection, final String tableName,
			final String tableSchema) throws SQLException, NamingException {
		ResultSet resultSet = connection.getMetaData().getPrimaryKeys(null, tableSchema, tableName);
		List<String> pkColumns = new ArrayList<>();
		while (resultSet.next()) {
			pkColumns.add(resultSet.getString("COLUMN_NAME"));
		}
		return pkColumns;
	}

	public static Map<String, String> getTableAndViewBySchema(final SQLDatabaseConnection databaseConnection,
			final String schemaNamePattern, final String tablePattern) throws SQLException, NamingException {
		return SQLSchemaUtil.getTableBySchema(databaseConnection, schemaNamePattern, tablePattern,
				new String[] { "TABLE", "VIEW" });
	}

	public static Map<String, String> getTableBySchema(final SQLDatabaseConnection databaseConnection,
			final String schemaNamePattern, final String tablePattern) throws SQLException, NamingException {
		return SQLSchemaUtil.getTableBySchema(databaseConnection, schemaNamePattern, tablePattern,
				new String[] { "TABLE" });
	}

	private static Map<String, String> getTableBySchema(final SQLDatabaseConnection databaseConnection,
			final String schemaNamePattern, final String tablePattern, final String... types)
			throws SQLException, NamingException {
		Connection connection = databaseConnection.getConnection();
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet tables = metaData.getTables(null, "%%", "%%", types);
		Map<String, String> sqlTables = new HashMap<>();
		while (tables.next()) {
			String tableSchema = tables.getString("TABLE_SCHEM");
			if ("SYS".equalsIgnoreCase(tableSchema) || "INFORMATION_SCHEMA".equalsIgnoreCase(tableSchema)) {
				continue;
			}
			String tableName = tables.getString("TABLE_NAME");
			String generateSQLTableLabel = SQLSchemaUtil.generateSQLTableLabel(tableSchema, tableName);
			String[] sqlTableValue = { tableSchema, tableName };
			Gson gson = new Gson();
			String valueSQLTable = gson.toJson(sqlTableValue);
			sqlTables.put(generateSQLTableLabel, valueSQLTable);
		}
		connection.close();
		return sqlTables;
	}

	public static String generateSQLTableLabel(final String tableSchema, final String tableName) {
		StringBuilder builder = new StringBuilder();
		if (!AddonStringUtils.isEmpty(tableSchema)) {
			builder.append(tableSchema);
			builder.append(".");
		}
		builder.append(tableName);
		return builder.toString();
	}

	public static List<String> getFieldNamesWithAutoIncrementDataType(final SQLDatabaseConnection databaseConnection,
			final String tableName, final String tableSchema) throws SQLException {
		Map<String, String> fieldNames = SQLSchemaUtil.getFieldWithAutoIncrement(databaseConnection, tableName,
				tableSchema);
		List<String> fieldNamesBuild = new ArrayList<>();
		for (Map.Entry<String, String> fieldName : fieldNames.entrySet()) {
			StringBuilder builder = new StringBuilder("'");
			builder.append(fieldName.getKey());
			builder.append("'");
			builder.append(" - ");
			builder.append(fieldName.getValue());
			fieldNamesBuild.add(builder.toString());
		}
		return fieldNamesBuild;
	}

	private static Map<String, String> getFieldWithAutoIncrement(final SQLDatabaseConnection databaseConnection,
			final String tableName, final String tableSchema) throws SQLException {
		Map<String, String> unsupportedFieldNames = new LinkedHashMap<>();
		Connection connection = databaseConnection.getConnection();
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, tableSchema, tableName, "%");
			while (resultSet.next()) {
				String colName = resultSet.getString("COLUMN_NAME");
				String columnTypeName = resultSet.getString("TYPE_NAME");
				boolean isAutoIncrement = resultSet.getString("IS_AUTOINCREMENT").equals("YES");
				if (isAutoIncrement) {
					unsupportedFieldNames.put(colName, columnTypeName);
				}
			}
			Map<String, String> map = unsupportedFieldNames;
			if (connection != null) {
				connection.close();
			}
			return map;
		} catch (Throwable throwable) {
			if (connection != null) {
				try {
					connection.close();
				} catch (Throwable throwable1) {
					throwable.addSuppressed(throwable1);
				}
			}
			throw throwable;
		}
	}

	public static List<ColumnMetadata> getFields(final SQLDatabaseConnection databaseConnection, final String tableName,
			final String tableSchema) throws SQLException {
		Connection connection = databaseConnection.getConnection();
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			// Get primary key information
			ResultSet primaryKeyResultSet = metaData.getPrimaryKeys(null, tableSchema, tableName);

			// Store primary key column names in a list
			List<String> primaryKeyColumns = new ArrayList<>();
			while (primaryKeyResultSet.next()) {
				String columnName = primaryKeyResultSet.getString("COLUMN_NAME");
				primaryKeyColumns.add(columnName);
			}

			ResultSet resultSet = metaData.getColumns(null, tableSchema, tableName, "%");
			List<ColumnMetadata> columns = new ArrayList<ColumnMetadata>();
			while (resultSet.next()) { // Todo Check for other databases
				ColumnMetadata columnData = new ColumnMetadata();
				String colName = resultSet.getString("COLUMN_NAME");
				columnData.setName(colName);
				columnData.setPrimaryKey(primaryKeyColumns.contains(colName));
				columnData.setNullable("YES".equals(resultSet.getString("IS_NULLABLE")));
				columnData.setDataType(resultSet.getString("DATA_TYPE"));
				columnData.setDecimalDigits(resultSet.getInt("DECIMAL_DIGITS"));
				columnData.setSize(resultSet.getInt("COLUMN_SIZE"));
				columnData.setDefaultValue(resultSet.getString("COLUMN_DEF"));
				columnData.setDescription(resultSet.getString("REMARKS"));
				columns.add(columnData);
			}
			if (connection != null) {
				connection.close();
			}
			return columns;
		} catch (Throwable throwable) {
			if (connection != null) {
				try {
					connection.close();
				} catch (Throwable throwable1) {
					throwable.addSuppressed(throwable1);
				}
			}
			throw throwable;
		}
	}
}
