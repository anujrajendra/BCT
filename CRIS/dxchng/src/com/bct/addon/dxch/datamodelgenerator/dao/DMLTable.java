package com.bct.addon.dxch.datamodelgenerator.dao;

import java.util.*;

import com.onwbp.base.text.bean.*;
import com.orchestranetworks.schema.*;

/**
 *
 * @author mostafashokiel
 */

public final class DMLTable extends DMLNode {
	private final List<Path> pks;

	public DMLTable(final String name, final String xsdName, final LabelDescription label, final Path groupPath,
			final int order) {
		super(name, xsdName, label, groupPath, order);
		this.pks = new ArrayList<>();
	}

	public List<Path> getPKs() {
		return this.pks;
	}

	public void addPK(final DMLField field) {
		if (field.isPrimaryKey()) {
			this.addPK(field.getFullPath());
		}
	}

	private void addPK(final Path pk) {
		this.pks.add(pk);
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = super.hashCode();
		result = (31 * result) + ((this.pks == null) ? 0 : this.pks.hashCode());
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
		DMLTable other = (DMLTable) obj;
		if (this.pks.isEmpty()) {
			if (!other.getPKs().isEmpty()) {
				return false;
			}
		} else if (!this.pks.equals(other.getPKs())) {
			return false;
		}
		return true;
	}
}
