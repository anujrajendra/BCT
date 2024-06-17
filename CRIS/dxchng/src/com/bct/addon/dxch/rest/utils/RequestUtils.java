package com.bct.addon.dxch.rest.utils;

import java.math.*;
import java.text.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.bct.addon.dxch.constant.*;
import com.onwbp.adaptation.*;
import com.onwbp.base.misc.*;
import com.orchestranetworks.instance.*;
import com.orchestranetworks.schema.*;
import com.orchestranetworks.service.*;

/**
 *
 * @author mostafashokiel
 */
public class RequestUtils {

	/**
	 * @param lfrRequest
	 * @return
	 *
	 * @author mostafashokiel
	 */

	/**
	 * @param request
	 * @return
	 *
	 * @author mostafashokiel
	 */

	public static Integer getInteger(final String sourceValue) {
		if (StringUtils.isNotBlank(sourceValue)) {
			try {
				return Integer.valueOf(Integer.parseInt(sourceValue));
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	public static String getString(final String sourceValue) {
		if (StringUtils.isNotBlank(sourceValue)) {
			try {
				return sourceValue;
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	public static Boolean getBoolean(final String sourceValue) {
		if (StringUtils.isNotBlank(sourceValue)) {
			try {
				return Boolean.valueOf(sourceValue);
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	public static void setRecord(final ValueContextForUpdate vcfu, final String sourceValue,
			final Path destinationPath) {
		if (StringUtils.equals(sourceValue, "null")) {
			vcfu.setValue("", destinationPath);
		} else if (StringUtils.isNotBlank(sourceValue)) {
			vcfu.setValue(sourceValue, destinationPath);
		}
	}

	public static void setRecord(final ValueContextForUpdate vcfu, final Date sourceValue, final Path destinationPath) {
		if (sourceValue != null) {
			vcfu.setValue(sourceValue, destinationPath);
		}
	}

	public static void setDateRecord(final ValueContextForUpdate vcfu, final String sourceValue,
			final Path destinationPath)
			throws UnavailableContentError, PathAccessException, IllegalArgumentException, ParseException {
		if (StringUtils.equals(sourceValue, "null")) {
			vcfu.setValue(null, destinationPath);
		} else if (StringUtils.isNotBlank(sourceValue)) {
			vcfu.setValue(org.apache.commons.lang3.time.DateUtils.parseDate(sourceValue, new String[] { "MM/dd/yyyy" }),
					destinationPath);
		}
	}

	public static void setDateTimeRecord(final ValueContextForUpdate vcfu, final String sourceValue,
			final Path destinationPath)
			throws UnavailableContentError, PathAccessException, IllegalArgumentException, ParseException {
		if (StringUtils.equals(sourceValue, "null")) {
			vcfu.setValue(null, destinationPath);
		} else if (StringUtils.isNotBlank(sourceValue)) {
			vcfu.setValue(org.apache.commons.lang3.time.DateUtils.parseDate(sourceValue,
					new String[] { "MM/dd/yyyy HH:mm:ss" }), destinationPath);
		}
	}

	public static void setRecord(final ValueContextForUpdate vcfu, final Boolean sourceValue,
			final Path destinationPath) {
		if (sourceValue != null) {
			vcfu.setValue(sourceValue, destinationPath);
		}
	}

	public static void setRecord(final ValueContextForUpdate vcfu, final List<String> sourceValueList,
			final Path destinationPath) {
		if ((sourceValueList != null) && !sourceValueList.isEmpty() && !sourceValueList.contains("null")) {
			vcfu.setValue(sourceValueList, destinationPath);
		} else if ((sourceValueList != null) && !sourceValueList.isEmpty() && sourceValueList.contains("null")) {
			vcfu.setValue(null, destinationPath);
		}
	}

	public static void setIntegerRecord(final ValueContextForUpdate vcfu, final String sourceValue,
			final Path destinationPath) {
		if (StringUtils.equals(sourceValue, "null")) {
			vcfu.setValue("", destinationPath);
		} else if (StringUtils.isNotBlank(sourceValue)) {
			vcfu.setValue(IntegerUtils.parse(sourceValue), destinationPath);
		}
	}

	public static void setBooleanRecord(final ValueContextForUpdate vcfu, final String sourceValue,
			final Path destinationPath) {
		if (StringUtils.equals(sourceValue, "null")) {
			vcfu.setValue("", destinationPath);
		} else if (StringUtils.isNotBlank(sourceValue)) {
			vcfu.setValue(RequestUtils.getBoolean(sourceValue), destinationPath);
		}
	}

	public static void setDecimalRecord(final ValueContextForUpdate vcfu, final String sourceValue,
			final Path destinationPath) {
		if (StringUtils.equals(sourceValue, "null")) {
			vcfu.setValue("", destinationPath);
		} else if (StringUtils.isNotBlank(sourceValue)) {
			vcfu.setValue(new BigDecimal(sourceValue), destinationPath);
		}
	}

	/**
	 * @param repository
	 * @param childDataspaceName
	 * @return
	 *
	 * @author mostafashokiel
	 * @param session
	 * @throws OperationException
	 */
	public static AdaptationHome getChildDataSpace(final Repository repository, final String childDataspaceName,
			final Session session) throws OperationException {
		AdaptationHome childDataspace = repository.lookupHome(HomeKey.forBranchName(childDataspaceName));
		if (childDataspace == null) { // Create Child Datapsace
			AdaptationHome personHome = repository.lookupHome(DataspaceConst.PERSON);
			HomeCreationSpec personChildSpec = new HomeCreationSpec();
			personChildSpec.setParent(personHome);
			personChildSpec.setOwner(Profile.EVERYONE);
			personChildSpec.setKey(HomeKey.forBranchName(childDataspaceName));
			childDataspace = repository.createHome(personChildSpec, session);

		}
		return childDataspace;
	}

	public static AdaptationHome getChildDataSpaceStats(final Repository repository, final String childDataspaceName,
			final Session session) throws OperationException {
		AdaptationHome childDataspace = repository.lookupHome(HomeKey.forBranchName(childDataspaceName));
		if (childDataspace == null) { // Create Child Datapsace
			AdaptationHome personHome = repository.lookupHome(DataspaceConst.STATS_PERSON);
			HomeCreationSpec personChildSpec = new HomeCreationSpec();
			personChildSpec.setParent(personHome);
			personChildSpec.setOwner(Profile.EVERYONE);
			personChildSpec.setKey(HomeKey.forBranchName(childDataspaceName));
			childDataspace = repository.createHome(personChildSpec, session);

		}
		return childDataspace;
	}
}
