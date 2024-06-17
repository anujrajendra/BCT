package com.bct.addon.dxch.util;

import java.security.*;
import java.util.*;

import com.bct.addon.dxch.dto.*;
import com.bct.addon.dxch.path.*;
import com.onwbp.adaptation.*;
import com.orchestranetworks.addon.dex.configuration.*;
import com.orchestranetworks.addon.dint.common.constant.*;
import com.orchestranetworks.addon.dint.common.util.*;
import com.orchestranetworks.addon.dint.controller.*;
import com.orchestranetworks.addon.dint.dataaccess.sql.*;
import com.orchestranetworks.addon.utils.*;

/**
 *
 * @author mostafashokiel
 */
public class DatabaseUtils {

	/**
	 * @param context
	 *
	 * @author mostafashokiel
	 */
	public static List<ColumnMetadata> getTableColumns(final ConnectionInfoDTO connectionInfo, final String tableName,
			final String tableSchema) {
		String error = null;
		try {
			DatabaseIdentification dbId = DatabaseUtils.getDatabaseIdentification(connectionInfo);

			SQLDatabaseConnection conn = SQLDatabaseConnection.getSQLDBConnectionByIdentification(dbId);
			List<ColumnMetadata> fields = SQLSchemaUtil.getFields(conn, tableName, tableSchema);
			return fields;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			error = ex.getMessage();
		}
		return null;
	}

	public static Map<String, String> getTables(final ConnectionInfoDTO connectionInfo, final String schemaPattern,
			final String tablePattern) {
		String error = null;
		try {
			DatabaseIdentification dbId = DatabaseUtils.getDatabaseIdentification(connectionInfo);
			SQLDatabaseConnection conn = SQLDatabaseConnection.getSQLDBConnectionByIdentification(dbId);

			Map<String, String> tables = SQLSchemaUtil.getTableAndViewBySchema(conn, schemaPattern, tablePattern);
			return tables;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			error = ex.getMessage();
		}
		return null;
	}

	public static Map<String, String> getTables(final Adaptation record) {
		String error = null;
		try {
			ConnectionInfoDTO connectionInfo = DatabaseUtils.getConnectionInfo(record);

			DatabaseIdentification dbId = DatabaseUtils.getDatabaseIdentification(connectionInfo);
			SQLDatabaseConnection conn = SQLDatabaseConnection.getSQLDBConnectionByIdentification(dbId);

			Map<String, String> tables = SQLSchemaUtil.getTableAndViewBySchema(conn, "", "");
			return tables;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			error = ex.getMessage();
		}
		return null;
	}

	public static JNDIDataSource getJNDIDataSource(final Adaptation record, final String tableName) {
		ConnectionInfoDTO connectionInfo = DatabaseUtils.getConnectionInfo(record);
		JNDIDataSource jndiDataSource = new JNDIDataSource();
		jndiDataSource.setName(tableName);
		jndiDataSource.setURL(connectionInfo.getUrl());
		jndiDataSource.setUser(connectionInfo.getUser());
		DesEncrypter encrypter = new DesEncrypter("AES");
		try {
			if (connectionInfo.getPassword() != null) {
				jndiDataSource.setPassword(encrypter.decrypt(encrypter.hexToBytes(connectionInfo.getPassword())));
			}
		} catch (GeneralSecurityException ex) {
			throw new RuntimeException(ex);
		}

		return jndiDataSource;

	}

	public static List<ColumnMetadata> getColumns(final Adaptation record, final String tableName) {
		String error = null;
		try {
			ConnectionInfoDTO connectionInfo = DatabaseUtils.getConnectionInfo(record);
			DatabaseIdentification dbId = DatabaseUtils.getDatabaseIdentification(connectionInfo);
			String[] tableAndSchema = tableName.split("\\.");

			SQLDatabaseConnection conn = SQLDatabaseConnection.getSQLDBConnectionByIdentification(dbId);

			List<ColumnMetadata> fields = SQLSchemaUtil.getFields(conn, tableAndSchema[1], tableAndSchema[0]);
			return fields;

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			error = ex.getMessage();
		}
		new ResultDTO(ResultState.ERROR, "Failed", error);

		return null;
	}

	private static ConnectionInfoDTO getConnectionInfo(final Adaptation connectionRecord) {
		ConnectionInfoDTO connectionInfo = new ConnectionInfoDTO();

		if (connectionRecord != null) {
			String url = connectionRecord.getString(DXchngPath._DatabaseDataSource._Url);
			String password = connectionRecord.getString(DXchngPath._DatabaseDataSource._Password);
			String user = connectionRecord.getString(DXchngPath._DatabaseDataSource._User);
			String authentication = connectionRecord.getString(DXchngPath._DatabaseDataSource._Authentication);
			String accessKey = connectionRecord.getString(DXchngPath._DatabaseDataSource._AccessKey);

			connectionInfo.setOldPassword(password);
			connectionInfo.setPassword(password);
			connectionInfo.setUrl(url);
			connectionInfo.setUser(user);
			connectionInfo.setAuthentication(authentication);
			connectionInfo.setAccessKey(accessKey);
		}
		return connectionInfo;

	}

	private static DatabaseIdentification getDatabaseIdentification(final ConnectionInfoDTO connectionInfo)
			throws Exception {
		String url = connectionInfo.getUrl();
		String user = connectionInfo.getUser();
		String password = connectionInfo.getPassword();
		String oldPassword = connectionInfo.getOldPassword();
		String accessKey = connectionInfo.getAccessKey();
		DintConstants.DatabaseAuthenticationMode authenMode = DintConstants.DatabaseAuthenticationMode
				.parse(connectionInfo.getAuthentication());
		DatabaseIdentification identification = null;
		switch (authenMode) {
		case DIRECT:
			try {
				identification = DatabaseUtils.getDirectModeIdentification(url, user, password, oldPassword);
			} catch (GeneralSecurityException ex) {
				throw ex;
			}
			return identification;
		case EXTERNAL_SYSTEM:
			identification = DatabaseUtils.getExtSystemIdentification(url, user, accessKey);
			return identification;
		}
		throw new IllegalArgumentException("Authentication mode isn't supported: " + authenMode);
	}

	private static DatabaseIdentification getExtSystemIdentification(final String url, final String user,
			final String accessKey) throws Exception {
		if (AddonStringUtils.isEmpty(url)) {
			throw new Exception("url is Empty");
		}
		if (AddonStringUtils.isEmpty(accessKey)) {
			throw new Exception("User is Empyt");
		}
		return new DatabaseIdentification(url, accessKey, null);
	}

	private static DatabaseIdentification getDirectModeIdentification(final String url, final String user,
			String password, final String oldPassword) throws GeneralSecurityException, Exception {
		if (AddonStringUtils.isEmpty(url)) {
			throw new Exception("url is Empty");
		}
		if (AddonStringUtils.isEmpty(user)) {
			throw new Exception("User is Empyt");
		}
		if ((password != null) && password.equals(oldPassword)) {
			password = DatabaseUtils.getDecryptedPassword(oldPassword);
		}
		return new DatabaseIdentification(url, user, password);
	}

	private static String getDecryptedPassword(final String password) throws GeneralSecurityException {
		DesEncrypter encrypter = new DesEncrypter("AES");
		return encrypter.decrypt(encrypter.hexToBytes(password));
	}

}
