package com.bct.addon.dxch.util;

import java.sql.*;
import java.util.*;

import javax.sql.*;

import com.orchestranetworks.addon.dint.dataaccess.sql.*;

public class SQLDatabaseConnection {
	private Connection connection;

	private final DatabaseIdentification identification;

	private final DataSource dataSource;

	public static SQLDatabaseConnection getSQLDBConnectionByIdentification(
			final DatabaseIdentification identification) {
		return new SQLDatabaseConnection(identification, null);
	}

	public static SQLDatabaseConnection getSQLDBConnectionByDataSource(final DataSource dataSource) {
		return new SQLDatabaseConnection(null, dataSource);
	}

//	public static SQLDatabaseConnection getSQLDBConnectionBySpec(final SQLTableSpec spec)
//			throws GeneralSecurityException, DataIntegrationException {
//		Objects.requireNonNull(spec);
//		if (Objects.nonNull(spec.getDataSource())) {
//			return SQLDatabaseConnection.getSQLDBConnectionByDataSource(spec.getDataSource());
//		}
//		Optional<DatabaseDataSourceTable> dataSourceTable = ConfigurationDataAccess.getInstance()
//				.getTableDatabaseDataSourceByName(spec.getDatabaseName());
//		if (dataSourceTable.isPresent()) {
//			return SQLDatabaseConnection.getSQLDBConnectionByIdentification(
//					SQLDatabaseUtil.getDatabaseIdentification(dataSourceTable.get()));
//		}
//		if (JNDIUtil.getJNDINamesInApplicationServer().contains(spec.getDatabaseName())) {
//			try {
//				DataSource ds = JNDIUtil.getJNDIDataSource(spec.getDatabaseName());
//				return SQLDatabaseConnection.getSQLDBConnectionByDataSource(ds);
//			} catch (Exception ex) {
//				throw new DataIntegrationException(UserMessage.createError(ex.getMessage()), ex);
//			}
//		}
//		throw new DataIntegrationException(
//
//				UserMessage.createError("The database " + spec.getDatabaseName() + " does not exist !"));
//	}

	private SQLDatabaseConnection(final DatabaseIdentification identification, final DataSource dataSource) {
		this.identification = identification;
		this.dataSource = dataSource;
	}

	private Connection getConnectionByDatabaseIdentification(final DatabaseIdentification databaseIdentification)
			throws SQLException {
		return DriverManager.getConnection(databaseIdentification.getUrl(), databaseIdentification.getUser(),
				databaseIdentification.getPassword());
	}

	private Connection getConnectionByDataSource(final DataSource dataSource) throws SQLException {
		return dataSource.getConnection();
	}

	public Connection getConnection() throws SQLException {
		if (Objects.nonNull(this.identification)) {
			this.connection = this.getConnectionByDatabaseIdentification(this.identification);
		} else {
			this.connection = this.getConnectionByDataSource(this.dataSource);
		}
		return this.connection;
	}

	public DatabaseIdentification getIdentification() {
		return this.identification;
	}

	public DataSource getDataSource() {
		return this.dataSource;
	}
}
