package com.bct.addon.dxch.rule;

import java.util.*;

import com.bct.addon.dxch.dto.*;
import com.bct.addon.dxch.path.*;
import com.bct.addon.dxch.util.*;
import com.onwbp.adaptation.*;
import com.orchestranetworks.schema.*;
import com.orchestranetworks.service.*;

/**
 *
 * @author mostafashokiel
 */
public class DataModelRules {
	/**
	 * @param adaptation
	 * @param procedureContext
	 *
	 * @author mostafashokiel
	 */
	public String createXSDContent(final Adaptation record, final ProcedureContext procedureContext,
			final List<ColumnMetadata> columns) {
		String xmlModel=null;
		try {
			if ((columns != null) && (columns.size() > 0)) {
				String tableName = record.getString(DXchngPath._DataSourceTable._TableName);
				if (tableName.contains(".")) {
					tableName = tableName.split("\\.")[1];
				}
				 xmlModel = SchemaUtils.generateXSD(tableName, columns);
				ValueContextForUpdate valueContextForUpdate = procedureContext.getContext(record.getAdaptationName());

				valueContextForUpdate.setPrivilegeForNode(DXchngPath._DataSourceTable._XsdScehma);
				valueContextForUpdate.setValue(xmlModel, DXchngPath._DataSourceTable._XsdScehma);

				Boolean oldPrivilege = Boolean.valueOf(procedureContext.isAllPrivileges());

				procedureContext.setAllPrivileges(true);
				procedureContext.setTriggerActivation(false);

				procedureContext.doModifyContent(record, valueContextForUpdate);

				procedureContext.setTriggerActivation(true);
				procedureContext.setAllPrivileges(oldPrivilege.booleanValue());
			}

		} catch (ConstraintViolationException ex) {
			throw new RuntimeException(ex);
		} catch (OperationException ex) {
			throw new RuntimeException(ex);
		}
		return xmlModel;
	}
}