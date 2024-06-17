package com.bct.addon.dxch.datamodelgenerator;

import java.security.*;
import java.util.*;

import com.onwbp.adaptation.*;
import com.orchestranetworks.addon.common.util.*;
import com.orchestranetworks.addon.dint.common.constant.*;
import com.orchestranetworks.addon.dint.common.util.*;
import com.orchestranetworks.addon.dint.dataaccess.*;
import com.orchestranetworks.addon.dint.schema.config.*;
import com.orchestranetworks.addon.dint.schema.config.ajaxcomponent.*;

public final class ConfigurationDataAccess {
	private static final ConfigurationDataAccess INSTANCE = new ConfigurationDataAccess();

	public static ConfigurationDataAccess getInstance() {
		return ConfigurationDataAccess.INSTANCE;
	}

	public List<String> getDateTimeConfigPatterns() {
		return this.getDateTimeConfigPatternsByType(DintConstants.DateTimePattern.TYPE_DATETIME_PATTERN);
	}

	public List<String> getDateConfigPatterns() {
		return this.getDateTimeConfigPatternsByType(DintConstants.DateTimePattern.TYPE_DATE_PATTERN);
	}

	public List<String> getTimeConfigPatterns() {
		return this.getDateTimeConfigPatternsByType(DintConstants.DateTimePattern.TYPE_TIME_PATTERN);
	}

	private List<String> getDateTimeConfigPatternsByType(final Integer patternType) {
		List<String> timePatterns = new ArrayList<>();
		AdaptationTable configAdaptationTable = this.getDateTimeConfigAdaptationTable();
		String query = DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._Type.format() + "="
				+ DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._Type.format();
		RequestResult requestResult = null;
		try {
			requestResult = configAdaptationTable.createRequestResult(query);
			Adaptation record;
			while ((record = requestResult.nextAdaptation()) != null) {
				String pattern = record
						.getString(DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._Pattern);
				if (!timePatterns.contains(pattern)) {
					timePatterns.add(pattern);
				}
			}
		} finally {
			if (requestResult != null) {
				requestResult.close();
			}
		}
		return timePatterns;
	}

	public List<Integer> getDateTimeConfigRecordIds(final String applicationType, final Integer type,
			final String pattern) {
		List<Integer> uuidPatterns = new ArrayList<>();
		String query = DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._ApplicationType.format()
				+ "=\"EBX\" and "
				+ DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._ApplicationType.format()
				+ "=\"" + DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._Type.format()
				+ "\" and " + type + "=\""
				+ DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._Pattern.format() + "\"";
		AdaptationTable configTable = this.getDateTimeConfigAdaptationTable();
		RequestResult requestResult = configTable.createRequestResult(query);
		try {
			Adaptation record;
			while ((record = requestResult.nextAdaptation()) != null) {
				Integer uuid = (Integer) record
						.get(DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._Uuid);
				if (!uuidPatterns.contains(uuid)) {
					uuidPatterns.add(uuid);
				}
			}
			if (requestResult != null) {
				requestResult.close();
			}
		} catch (Throwable throwable) {
			if (requestResult != null) {
				try {
					requestResult.close();
				} catch (Throwable throwable1) {
					throwable.addSuppressed(throwable1);
				}
			}
			throw throwable;
		}
		return uuidPatterns;
	}

	public List<Integer> getDefaultDateTimeConfigRecordIds(final String applicationType, final Integer type) {
		List<Integer> uuidPatterns = new ArrayList<>();
		String query = DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._ApplicationType.format()
				+ "!=\"EBX\" and "
				+ DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._ApplicationType.format()
				+ "=\""
				+ DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._ApplicationType.format()
				+ "\" and " + applicationType + "=\""
				+ DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._Type.format() + "\"";
		AdaptationTable configTable = this.getDateTimeConfigAdaptationTable();
		RequestResult requestResult = configTable.createRequestResult(query);
		try {
			Adaptation record;
			while ((record = requestResult.nextAdaptation()) != null) {
				Integer uuid = (Integer) record
						.get(DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._Uuid);
				if (!uuidPatterns.contains(uuid)) {
					uuidPatterns.add(uuid);
				}
			}
			if (requestResult != null) {
				requestResult.close();
			}
		} catch (Throwable throwable) {
			if (requestResult != null) {
				try {
					requestResult.close();
				} catch (Throwable throwable1) {
					throwable.addSuppressed(throwable1);
				}
			}
			throw throwable;
		}
		return uuidPatterns;
	}

	public String getDefaultCSVTimePattern() {
		return this.getDefaultDateTimePatternByMode("Default CSV", DintConstants.DateTimePattern.TYPE_TIME_PATTERN);
	}

	public String getDefaultCSVDateTimePattern() {
		return this.getDefaultDateTimePatternByMode("Default CSV", DintConstants.DateTimePattern.TYPE_DATETIME_PATTERN);
	}

	public String getDefaultCSVDatePattern() {
		return this.getDefaultDateTimePatternByMode("Default CSV", DintConstants.DateTimePattern.TYPE_DATE_PATTERN);
	}

	public String getDefaultExelDateTimePattern() {
		return this.getDefaultDateTimePatternByMode("Default Excel",
				DintConstants.DateTimePattern.TYPE_DATETIME_PATTERN);
	}

	public String getDefaultExelDatePattern() {
		return this.getDefaultDateTimePatternByMode("Default Excel", DintConstants.DateTimePattern.TYPE_DATE_PATTERN);
	}

	public String getDefaultExelTimePattern() {
		return this.getDefaultDateTimePatternByMode("Default Excel", DintConstants.DateTimePattern.TYPE_TIME_PATTERN);
	}

	public String getDefaultTransferDateTimePattern() {
		return this.getDefaultDateTimePatternByMode("Default Transfer",
				DintConstants.DateTimePattern.TYPE_DATETIME_PATTERN);
	}

	public String getDefaultTransferDatePattern() {
		return this.getDefaultDateTimePatternByMode("Default Transfer",
				DintConstants.DateTimePattern.TYPE_DATE_PATTERN);
	}

	public String getDefaultTransferTimePattern() {
		return this.getDefaultDateTimePatternByMode("Default Transfer",
				DintConstants.DateTimePattern.TYPE_TIME_PATTERN);
	}

	private String getDefaultDateTimePatternByMode(final String mode, final Integer type) {
		String query = DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._ApplicationType.format()
				+ "= '"
				+ DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._ApplicationType.format()
				+ "' and  " + mode + "= "
				+ DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._Type.format();
		AdaptationTable adaptationTable = this.getDateTimeConfigAdaptationTable();
		Adaptation adaptation = adaptationTable.lookupFirstRecordMatchingPredicate(query);
		return adaptation.getString(DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern._Pattern);
	}

	private AdaptationTable getDateTimeConfigAdaptationTable() {
		return EBXContainerReader.getConfigurationDataset().getTable(

				DintConfigurationPaths._DataIntegration_ReferenceData_DateTimePattern.getPathInSchema());
	}

	public AdaptationTable getSQLDataSourceAdaptationTable() {
		return EBXContainerReader.getConfigurationDataset().getTable(

				DintConfigurationPaths._DataIntegration_ReferenceData_SQLDataSource.getPathInSchema());
	}

	public boolean hasSQLDataSourcesForDataModel(final String schemaLocation) {
		List<Adaptation> sqlDataSources = DataAccessUtils.getInstance()
				.getAdaptations(this.getSQLDataSourceAdaptationTable().createRequestResult(null));
		Objects.requireNonNull(schemaLocation);
		return sqlDataSources.stream()
				.map(adaptation -> (String) adaptation
						.get(DintConfigurationPaths._DataIntegration_ReferenceData_SQLDataSource._EbxDataModel))
				.anyMatch(schemaLocation::equals);
	}

	public Optional<DatabaseDataSourceTable> getTableDatabaseDataSourceByName(final String nameSQLDatasource)
			throws GeneralSecurityException {
		AdaptationTable table = this.getDatabaseTable();
		RequestResult requestResult = table.createRequestResult(null);
		List<Adaptation> adaptations = DataAccessUtils.getInstance().getAdaptations(requestResult);
		for (Adaptation adaptation : adaptations) {
			String name = adaptation
					.getString(DintConfigurationPaths._DataIntegration_ReferenceData_DatabaseDataSource._Name);
			if (name.equals(nameSQLDatasource)) {
				DatabaseDataSourceTable databaseDataSourceTable = new DatabaseDataSourceTable();
				String url = adaptation
						.getString(DintConfigurationPaths._DataIntegration_ReferenceData_DatabaseDataSource._Url);
				String username = adaptation
						.getString(DintConfigurationPaths._DataIntegration_ReferenceData_DatabaseDataSource._User);
				String password = adaptation
						.getString(DintConfigurationPaths._DataIntegration_ReferenceData_DatabaseDataSource._Password);
				String authentication = adaptation.getString(
						DintConfigurationPaths._DataIntegration_ReferenceData_DatabaseDataSource._Authentication);
				String accessKey = adaptation
						.getString(DintConfigurationPaths._DataIntegration_ReferenceData_DatabaseDataSource._AccessKey);
				databaseDataSourceTable.setName(name);
				databaseDataSourceTable.setUrl(url);
				databaseDataSourceTable.setUsername(username);
				databaseDataSourceTable.setPassword(SQLUtils.getDecryptedPassword(password));
				databaseDataSourceTable.setAuthentication(authentication);
				databaseDataSourceTable.setAccessKey(accessKey);
				return Optional.of(databaseDataSourceTable);
			}
		}
		return Optional.empty();
	}

	public AdaptationTable getDatabaseTable() {
		return EBXContainerReader.getConfigurationDataset().getTable(

				DintConfigurationPaths._DataIntegration_ReferenceData_DatabaseDataSource.getPathInSchema());
	}

	public Optional<SQLDataSourceTable> getTableSQLDataSourceByCode(final String code) {
		AdaptationTable table = EBXContainerReader.getConfigurationDataset().getTable(

				DintConfigurationPaths._DataIntegration_ReferenceData_SQLDataSource.getPathInSchema());
		RequestResult requestResult = table.createRequestResult(null);
		List<Adaptation> adaptations = DataAccessUtils.getInstance().getAdaptations(requestResult);
		for (Adaptation adaptation : adaptations) {
			String codeValue = adaptation
					.getString(DintConfigurationPaths._DataIntegration_ReferenceData_SQLDataSource._Code);
			if (code.equals(codeValue)) {
				SQLDataSourceTable sqlDataSourceTable = new SQLDataSourceTable();
				sqlDataSourceTable.setCode(codeValue);
				sqlDataSourceTable.setName(adaptation
						.getString(DintConfigurationPaths._DataIntegration_ReferenceData_SQLDataSource._Name));
				sqlDataSourceTable.setEbxDataModel(adaptation
						.getString(DintConfigurationPaths._DataIntegration_ReferenceData_SQLDataSource._EbxDataModel));
				sqlDataSourceTable.setTableNamePattern(adaptation.getString(
						DintConfigurationPaths._DataIntegration_ReferenceData_SQLDataSource._TableNamePattern));
				sqlDataSourceTable.setSchemaNamePattern(adaptation.getString(
						DintConfigurationPaths._DataIntegration_ReferenceData_SQLDataSource._SchemaNamePattern));
				return Optional.of(sqlDataSourceTable);
			}
		}
		return Optional.empty();
	}
}
