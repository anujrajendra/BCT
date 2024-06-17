package com.bct.addon.dxch.datamodelgenerator;

public class SQLDataSourceTable {
	private String code;

	private String name;

	private String ebxDataModel;

	private String tableNamePattern;

	private String schemaNamePattern;

	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getEbxDataModel() {
		return this.ebxDataModel;
	}

	public void setEbxDataModel(final String ebxDataModel) {
		this.ebxDataModel = ebxDataModel;
	}

	public String getTableNamePattern() {
		return this.tableNamePattern;
	}

	public void setTableNamePattern(final String tableNamePattern) {
		this.tableNamePattern = tableNamePattern;
	}

	public String getSchemaNamePattern() {
		return this.schemaNamePattern;
	}

	public void setSchemaNamePattern(final String schemaNamePattern) {
		this.schemaNamePattern = schemaNamePattern;
	}
}
