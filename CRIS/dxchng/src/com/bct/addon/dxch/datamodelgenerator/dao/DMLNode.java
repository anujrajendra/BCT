package com.bct.addon.dxch.datamodelgenerator.dao;

import java.util.*;

import com.onwbp.base.text.bean.*;
import com.orchestranetworks.schema.*;

/**
 *
 * @author mostafashokiel
 */

public class DMLNode {
	private final String name;

	private final String xsdName;

	private final LabelDescription labelAndDescription;

	private final List<DMLNode> children;

	private Path groupPath;

	private final int order;

	public DMLNode(final String name, final String xsdName, final LabelDescription label, final Path groupPath,
			final int order) {
		this.name = name;
		this.xsdName = xsdName;
		this.labelAndDescription = label;
		this.children = new ArrayList<>();
		if (groupPath == null) {
			this.groupPath = Path.ROOT;
		} else {
			this.groupPath = groupPath;
		}
		this.order = order;
	}

	public final String getName() {
		return this.name;
	}

	public final String getXSDName() {
		return this.xsdName;
	}

	public final LabelDescription getLabelAndDesciption() {
		return this.labelAndDescription;
	}

	public final List<DMLNode> getChildren() {
		return this.children;
	}

	public void setGroupPath(final Path groupPath) {
		this.groupPath = groupPath;
	}

	public final Path getGroupPath() {
		return this.groupPath;
	}

	public final int getOrder() {
		return this.order;
	}

	public final void addChild(final DMLNode node) {
		if (this.children.contains(node)) {
			return;
		}
		if (node == this) {
			return;
		}
		this.children.add(node);
	}

	public final Path getFullPath() {
		return this.groupPath.add(this.xsdName);
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = (31 * result) + ((this.groupPath == null) ? 0 : this.groupPath.hashCode());
		result = (31 * result) + ((this.name == null) ? 0 : this.name.hashCode());
		result = (31 * result) + ((this.xsdName == null) ? 0 : this.xsdName.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!this.getClass().isInstance(obj)) {
			return false;
		}
		DMLNode other = (DMLNode) obj;
		if (this.groupPath == null) {
			if (other.getGroupPath() != null) {
				return false;
			}
		} else if (!this.groupPath.equals(other.getGroupPath())) {
			return false;
		}
		if (this.name == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!this.name.equals(other.getName())) {
			return false;
		}
		if (this.xsdName == null) {
			if (other.getXSDName() != null) {
				return false;
			}
		} else if (!this.xsdName.equals(other.getXSDName())) {
			return false;
		}
		return true;
	}
}