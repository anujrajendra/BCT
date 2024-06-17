package com.bct.addon.dxch.constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.bct.addon.dxch.path.CategoryConfigPath;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.ConstraintEnumeration;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;

public class PeriodConstraintEnumeration implements ConstraintEnumeration {

	@Override
	public void checkOccurrence(Object arg0, ValueContextForValidation arg1) throws InvalidSchemaException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setup(ConstraintContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String displayOccurrence(Object arg0, ValueContext arg1, Locale arg2) throws InvalidSchemaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getValues(ValueContext arg0) throws InvalidSchemaException {

		Adaptation dataset = arg0.getAdaptationInstance();

		String category = (String) arg0
				.getValue(Path.PARENT.add(CategoryConfigPath._CategoryConfigurations._CategoryID));

		Query<Tuple> query = dataset.createQuery(
				"SELECT t.reportingPeriod FROM \"/root/Categories\" t  " + "WHERE t.ID ='" + category + "'");

		QueryResult<Tuple> queryResult = query.getResult();

		List<String> rpValues = new ArrayList<String>();

		for (Tuple tuple : queryResult) {
			String result = (String) tuple.get(0);

			if (result.equalsIgnoreCase("Monthly")) {

				Query<Tuple> query1 = dataset.createQuery("SELECT t.\"$pk\" FROM \"/root/months\" t");

				QueryResult<Tuple> queryResult1 = query1.getResult();

				for (Tuple tuple1 : queryResult1) {

					String result1 = (String) tuple1.get(0);

					rpValues.add(result1);
				}
			} else if (result.equalsIgnoreCase("Quarterly")) {

				Query<Tuple> query2 = dataset.createQuery("SELECT t.\"$pk\" FROM \"/root/quarters\" t");

				QueryResult<Tuple> queryResult1 = query2.getResult();

				for (Tuple tuple2 : queryResult1) {

					String result1 = (String) tuple2.get(0);

					rpValues.add(result1);
				}
			} else if (result.equalsIgnoreCase("Half Yearly")) {

				Query<Tuple> query3 = dataset.createQuery("SELECT t.\"$pk\" FROM \"/root/HalfYears\" t");

				QueryResult<Tuple> queryResult1 = query3.getResult();

				for (Tuple tuple3 : queryResult1) {

					String result1 = (String) tuple3.get(0);

					rpValues.add(result1);
				}

			} else if (result.equalsIgnoreCase("Yearly")) {

				rpValues.add("A");

			}
		}
		return rpValues;
	}
}
