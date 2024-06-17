package com.bct.crismodule.usertask;

import com.bct.crismodule.Paths;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.LoggingCategory;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.workflow.CreationWorkItemSpec;
import com.orchestranetworks.workflow.UserTask;
import com.orchestranetworks.workflow.UserTaskCreationContext;

public class UserTaskWorkItem extends UserTask{

	private String dataspace;
	private String dataset;
	private String table;
	private String record;
	private String stationName;
	@Override
	public void handleCreate(UserTaskCreationContext context) throws OperationException {
		
		String user1 = context.getVariableString("user1");
		String user2 = context.getVariableString("user2");
		String user3 = context.getVariableString("user3");
		
		dataspace = context.getVariableString("dataspace");
		dataset = context.getVariableString("dataset");
		table = context.getVariableString("table");
		record = context.getVariableString("record");
		
		UserReference reference1 = UserReference.forUser(user1);
		UserReference reference2 = UserReference.forUser(user2);
		UserReference reference3 = UserReference.forUser(user3);
		
		CreationWorkItemSpec creationWorkItemSpec1 =
				CreationWorkItemSpec.forAllocation(reference1);
		CreationWorkItemSpec creationWorkItemSpec2 =
				CreationWorkItemSpec.forAllocation(reference2);
		CreationWorkItemSpec creationWorkItemSpec3 =
				CreationWorkItemSpec.forAllocation(reference3);
		
//		Repository repo = Repository.getDefault();
//		AdaptationHome dataspaceName = repo.lookupHome(HomeKey.forBranchName(dataspace));
//		Adaptation datasetName = dataspaceName.findAdaptationOrNull(AdaptationName.forName(dataset));
//		
//		String stationTable = record.substring(0, record.indexOf("["));
//		String stationIdentifier = record.substring(record.indexOf("=")+1, record.indexOf("]"));
//		
//		Query<Tuple> query = datasetName.createQuery("SELECT t.\"$adaptation\" from \"" +
//		stationTable + "\" t where t.\"$pk\"='" + stationIdentifier + "'");
//		
//		QueryResult<Tuple> result = query.getResult();
//		
//		java.util.Iterator<Tuple> itr = result.iterator();
//		
//		while (itr.hasNext()) {
//			Tuple tup = itr.next();
//			Adaptation stationRecord = (Adaptation) tup.get(0);
//			stationName = (String) (stationRecord.createValueContext()).getValue(Paths._Station._Tab1_Stn_name);
//		}
		
//		AdaptationTable stationTable = datasetName.getTable(Paths._Station.getPathInSchema());
//		Adaptation stationName = (Adaptation) ((Adaptation) stationTable).get(Paths._Station._Tab1_Stn_name);
//
		
		com.orchestranetworks.instance.Repository repository = Repository.getDefault();
		final HomeKey dataSpaceKey = HomeKey.forBranchName(dataspace);
		final AdaptationHome dataspaceName = repository.lookupHome(dataSpaceKey);
		
		final AdaptationName dataSetKey = AdaptationName.forName(dataset);
		final Adaptation datasetName = dataspaceName.findAdaptationOrNull(dataSetKey);
		
		final AdaptationTable stationTable = datasetName.getTable(Path.parse(table));
		
		Adaptation stationRecord = stationTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(record.substring(record.indexOf("=") + 1, record.indexOf("]"))));

		System.out.println("--------DataspaceName---------"+dataspaceName);
		System.out.println("--------DatasetName---------"+datasetName);
		System.out.println("--------TableName---------"+stationTable);
		System.out.println("--------stationName---------"+stationRecord);
		System.out.println("--------Primary key---------"+(record.substring(record.indexOf("=") + 1, record.indexOf("]"))));
		
		String stationTable1 = record.substring(0, record.indexOf("["));
		String stationIdentifier = record.substring(record.indexOf("=")+1, record.indexOf("]"));

		System.out.println("------TableName from record--------"+stationTable1);
		System.out.println("--------stationIdsentifier---------"+stationIdentifier);
		
//		Query<Tuple> query = datasetName.createQuery("SELECT t.\"$adaptation\" from \"" +
//				stationTable1 + "\" t where t.\"$pk\"='" + stationIdentifier + "'");
				
		//LoggingCategory.getWorkflow().info(stationRecord.toString());
//		System.out.println(stationRecord);
//		stationName = (String) (stationRecord.createValueContext()).getValue(Paths._Station._Tab1_Stn_name);
//		stationName = String.valueOf(stationRecord.get(Path.parse(Paths._Station._Tab1_Stn_name.format())));
//		
		//stationName = (String) stationRecord.get(Paths._Station._Tab1_Stn_name.format());
		//stationName = (String) stationRecord.getValueWithoutResolution(Paths._Station._Tab1_Stn_name);
		//stationName = String.valueOf(stationRecord.get(Path.parse(Paths._Station._Tab1_Stn_name.format())));

		System.out.println("--------------------Workflow stationName----------------------");
		System.out.println("---------Workflow stationName------------"+stationName);
		
		System.out.println("------------Record---------------"+context.getVariableString("record"));
		context.createWorkItem(creationWorkItemSpec1);
		
		
		
	}
//
//	public String getDataspace() {
//		return dataspace;
//	}
//
//	public void setDataspace(String dataspace) {
//		this.dataspace = dataspace;
//	}
//
//	public String getDataset() {
//		return dataset;
//	}
//
//	public void setDataset(String dataset) {
//		this.dataset = dataset;
//	}
//
//	public String getTable() {
//		return table;
//	}
//
//	public void setTable(String table) {
//		this.table = table;
//	}
//
//	public String getRecord() {
//		return record;
//	}
//
//	public void setRecord(String record) {
//		this.record = record;
//	}

	
}
