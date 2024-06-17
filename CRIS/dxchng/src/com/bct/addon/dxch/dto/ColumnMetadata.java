package com.bct.addon.dxch.dto;

/**
 *
 * @author mostafashokiel
 */
public class ColumnMetadata {

	private String name;
	private String dataType;
	private String description;
	private String defaultValue;
	private int size;
	private int decimalDigits;
	private int index;
	private boolean isPrimaryKey;
	private boolean isNullable;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(final String dataType) {
		this.dataType = dataType;
	}

	public boolean isNullable() {
		return this.isNullable;
	}

	public void setNullable(final boolean isNullable) {
		this.isNullable = isNullable;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(final int size) {
		this.size = size;
	}

	public int getDecimalDigits() {
		return this.decimalDigits;
	}

	public void setDecimalDigits(final int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(final String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public boolean isPrimaryKey() {
		return this.isPrimaryKey;
	}

	public void setPrimaryKey(final boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ColumnMetadata [name=" + this.name + ", dataType=" + this.dataType + ", isNullable=" + this.isNullable
				+ ", size=" + this.size + ", decimalDigits=" + this.decimalDigits + ", defaultValue="
				+ this.defaultValue + ", index=" + this.index + ", isPrimaryKey=" + this.isPrimaryKey + "]";
	}

}
