package com.bct.addon.dxch.datamodelgenerator.dao;

import com.onwbp.base.text.bean.*;
import com.orchestranetworks.addon.adix.dataexchange.datamodelgenerator.model.*;
import com.orchestranetworks.schema.*;

/**
 *
 * @author mostafashokiel
 */

public final class DMLField extends DMLNode {
	private final FieldType type;

	private String defaultValue;
	private int size;
	private int decimalDigits;

	private boolean isNullable;
	private boolean primaryKey;

	public DMLField(final String name, final String xsdName, final LabelDescription label, final FieldType type,
			final boolean primaryKey, final Path groupPath, final int order) {
		super(name, xsdName, label, groupPath, order);
		this.type = type;
		this.primaryKey = primaryKey;
	}

	public FieldType getType() {
		return this.type;
	}

	public boolean isPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(final boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(final String defaultValue) {
		this.defaultValue = defaultValue;
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

	public boolean isNullable() {
		return this.isNullable;
	}

	public void setNullable(final boolean isNullable) {
		this.isNullable = isNullable;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = super.hashCode();
		result = (31 * result) + ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!this.getClass().isInstance(obj)) {
			return false;
		}
		DMLField other = (DMLField) obj;
		if (this.type == null) {
			if (other.getType() != null) {
				return false;
			}
		} else if (!this.type.equals(other.getType())) {
			return false;
		}
		return true;
	}
}
