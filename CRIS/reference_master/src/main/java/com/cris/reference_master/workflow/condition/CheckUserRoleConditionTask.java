package com.cris.reference_master.workflow.condition;

import java.util.Arrays;
import java.util.List;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Role;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.service.directory.DirectoryHandler;
import com.orchestranetworks.workflow.ConditionBean;
import com.orchestranetworks.workflow.ConditionBeanContext;

public class CheckUserRoleConditionTask extends ConditionBean {

	String dataspaceName;
	String datasetName;
	String tableName;
	String record;
	String roleName;

	@Override
	public boolean evaluateCondition(ConditionBeanContext aContext) throws OperationException {

		Repository repository = Repository.getDefault();
		final HomeKey dataSpaceKey = HomeKey.forBranchName(dataspaceName);
		final AdaptationHome dataspaceName = repository.lookupHome(dataSpaceKey);

		final AdaptationName dataSetKey = AdaptationName.forName(datasetName);
		final Adaptation datasetName = dataspaceName.findAdaptationOrNull(dataSetKey);

		AdaptationTable masterTable = datasetName.getTable(Path.parse(tableName));

		Adaptation masterRecord = masterTable.lookupAdaptationByPrimaryKey(
				PrimaryKey.parseString(record.substring(record.indexOf("'") + 1, record.lastIndexOf("'"))));

		String userId = "";
		if (masterRecord != null)
			userId = masterRecord.getLastUser().getUserId();

		UserReference userReference = UserReference.forUser(userId);

		List<String> roleList = Arrays.asList(roleName.split("\\s*,\\s*"));
		Boolean userRoleFlag = false;

		for (String role : roleList) {

			userRoleFlag = DirectoryHandler.getInstance(repository).isUserInRole(userReference,
					Role.forSpecificRole(role));

			if (userRoleFlag == true)
				break;

		}

		return userRoleFlag;

	}

	public String getDataspaceName() {
		return dataspaceName;
	}

	public void setDataspaceName(String dataspaceName) {
		this.dataspaceName = dataspaceName;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
