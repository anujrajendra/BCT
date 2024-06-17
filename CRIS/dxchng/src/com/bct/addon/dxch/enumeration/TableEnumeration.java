package com.bct.addon.dxch.enumeration;

import java.util.*;

import com.bct.addon.dxch.path.DXchngPath;
import com.bct.addon.dxch.util.DatabaseUtils;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;

import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.ConstraintEnumeration;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;



/**
 *
 * @author mostafashokiel
 */
public class TableEnumeration implements ConstraintEnumeration<String> {

	private String databaseId;

	@Override
	public void checkOccurrence(final String arg0, final ValueContextForValidation arg1) throws InvalidSchemaException {

	}

	@Override
	public void setup(final ConstraintContext arg0) {

	}

	@Override
	public String toUserDocumentation(final Locale arg0, final ValueContext arg1) throws InvalidSchemaException {
		return "";
	}

	@Override
	public String displayOccurrence(final String value, final ValueContext arg1, final Locale locale)
			throws InvalidSchemaException {
		if (value == null) {
			return "";
		}
		return value;

	}

	@Override
	public List<String> getValues(final ValueContext context) throws InvalidSchemaException {
		List<String> tables = new ArrayList<String>();
		String database = (String) context.getValue(Path.PARENT.add(Path.parse(this.databaseId)));
		Adaptation dataset = context.getAdaptationInstance().getContainer();
		AdaptationTable connectionTable = dataset.getTable(DXchngPath._DatabaseDataSource.getPathInSchema());
		Adaptation record = connectionTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(database));
		Map<String, String> tableMap = DatabaseUtils.getTables(record);

		if (tableMap != null) {
			tables = new ArrayList<>(tableMap.keySet());
		}

		return tables;
	}

	public String getDatabaseId() {
		return this.databaseId;
	}

	public void setDatabaseId(final String databaseId) {
		this.databaseId = databaseId;
	}

}
