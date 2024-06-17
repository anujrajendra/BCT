package com.bct.addon.dxch.datamodelgenerator;

public class DatabaseDataSourceTable {
	private String name;

	private String url;

	private String authentication;

	private String username;

	private String password;

	private String accessKey;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getAuthentication() {
		return this.authentication;
	}

	public void setAuthentication(final String authentication) {
		this.authentication = authentication;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getAccessKey() {
		return this.accessKey;
	}

	public void setAccessKey(final String accessKey) {
		this.accessKey = accessKey;
	}
}
