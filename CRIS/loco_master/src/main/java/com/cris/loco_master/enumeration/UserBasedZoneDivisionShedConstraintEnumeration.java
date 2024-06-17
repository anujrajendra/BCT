package com.cris.loco_master.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.cris.loco_master.Paths;
import com.cris.loco_master.utils.DropDownUtils;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.ConstraintEnumeration;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.Role;
import com.orchestranetworks.service.UserReference;
import com.orchestranetworks.service.directory.DirectoryHandler;

public class UserBasedZoneDivisionShedConstraintEnumeration implements ConstraintEnumeration<String> {

	String attributeName;
	HashMap<String, String> valueMap = new HashMap<String, String>();

	@Override
	public String displayOccurrence(String code, ValueContext arg1, Locale arg2) throws InvalidSchemaException {
		return valueMap.get(code);
	}

	@Override
	public List<String> getValues(ValueContext context) throws InvalidSchemaException {

		Repository repository = Repository.getDefault();

		DirectoryHandler directoryHandler = DirectoryHandler.getInstance(repository);

		String userId = (String) context
				.getValue(Path.PARENT.add(Paths._Root_Locomotive._Root_Locomotive_Audit_Info_Logged_In_User));

		if (userId == null)
			return new ArrayList<String>();

		UserReference userReference = UserReference.forUser(userId);

		String zoneValue = (String) context
				.getValue(Path.PARENT.add(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Zone));

		String divisionValue = (String) context
				.getValue(Path.PARENT.add(Paths._Root_Locomotive._Root_Locomotive_Loco_Owning_Division));

		boolean isRBUser = directoryHandler.isUserInRole(userReference, Role.forSpecificRole("RB_DS"))
				|| directoryHandler.isUserInRole(userReference, Role.forSpecificRole("RB_DAA"));

		boolean isPUUser = directoryHandler.isUserInRole(userReference, Role.forSpecificRole("PU_DAA"))
				|| directoryHandler.isUserInRole(userReference, Role.forSpecificRole("PU_DS"));

		boolean isAdminUser = directoryHandler.isUserInRole(userReference, Role.ADMINISTRATOR);

		boolean isZonalAdmin = directoryHandler.isUserInRole(userReference, Role.forSpecificRole("Loco_Zonal_Admin"));

		boolean isShedUser = directoryHandler.isUserInRole(userReference, Role.forSpecificRole("Shed_DAA"))
				|| directoryHandler.isUserInRole(userReference, Role.forSpecificRole("Shed_DS"));

		if (isRBUser || isPUUser || isAdminUser) {
			if (this.attributeName.equalsIgnoreCase("Zone"))
				valueMap = DropDownUtils.getAllZoneFromRef();
			else if (this.attributeName.equalsIgnoreCase("Division"))
				valueMap = DropDownUtils.getDivisionForZoneFromRef(zoneValue);
			else if (this.attributeName.equalsIgnoreCase("Shed"))
				valueMap = DropDownUtils.getShedForDivisionFromRef(zoneValue, divisionValue);

		} else if (isZonalAdmin) {
			if (this.attributeName.equalsIgnoreCase("Zone"))
				valueMap = DropDownUtils.getAllZoneFromRef();
			else if (this.attributeName.equalsIgnoreCase("Division"))
				valueMap = DropDownUtils.getDivisionsForZoneFromUR(userId, zoneValue);
			else if (this.attributeName.equalsIgnoreCase("Shed"))
				valueMap = DropDownUtils.getShedForDivisionFromUR(userId, zoneValue, divisionValue);

		} else if (isShedUser) {
			if (this.attributeName.equalsIgnoreCase("Zone"))
				valueMap = DropDownUtils.getZonesForUser(userId);
			else if (this.attributeName.equalsIgnoreCase("Division"))
				valueMap = DropDownUtils.getDivisionsForZoneFromUR(userId, zoneValue);
			else if (this.attributeName.equalsIgnoreCase("Shed"))
				valueMap = DropDownUtils.getShedForDivisionFromUR(userId, zoneValue, divisionValue);
		}

		ArrayList<String> codeList = new ArrayList<String>(valueMap.keySet());

		return codeList;
	}

	@Override
	public void checkOccurrence(String key, ValueContextForValidation contextValidation) throws InvalidSchemaException {
//		if (valueMap.get(key) == null) {
//			contextValidation.addError("Invalid Reference Value - " + attributeName);
//		}

	}

	@Override
	public void setup(ConstraintContext arg0) {

	}

	@Override
	public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {
		return null;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
}