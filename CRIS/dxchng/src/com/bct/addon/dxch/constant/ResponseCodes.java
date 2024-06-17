package com.bct.addon.dxch.constant;

/**
 *
 * @author mostafashokiel
 */
public class ResponseCodes {

	public static String DATASPACE_ALREADY_EXISTS_ERROR_CODE = "MDM_006";
	public static String DATASPACE_ALREADY_EXISTS_ERROR_MESSAGE = "Dataspace Already Exists";

	public static String UN_EXPECTED_ERROR_CODE = "MDM_001";
	public static String UN_EXPECTED_ERROR_MESSAGE = "Unexpected Error happens";

	public static String NOT_FOUND_RECORD_ERROR_CODE = "MDM_002";
	public static String NOT_FOUND_RECORD_ERROR_MESSAGE = "Record Not Found in MDM";

	public static String EMPTY_REQUEST_ERROR_CODE = "MDM_003";
	public static String EMPTY_REQUEST__ERROR_MESSAGE = "Request is empty or missing";

	public static String NOT_FOUND_PERSON_RECORD_ERROR_CODE = "MDM_004";
	public static String NOT_FOUND_PERSON_RECORD_ERROR_MESSAGE = "Record Not Found in MDM";

	public static String PERSON_ID_EMPTY_CHILDREL_ERROR_CODE = "MDM_007";
	public static String PERSON_ID_EMPTY_CHILDREL_ERROR_MESSAGE = "Person ID cannot be empty while creating Employee/JobSeekers";

	public static String MANDATORY_ERROR_CODE = "MDM_005";
	public static String MANDATORY_ERROR_MESSAGE = "Mandatory Fields are Empty";

	public static String DATASPACE_NOT_EXISTS_ERROR_CODE = "MDM_008";
	public static String DATASPACE_NOT_EXISTS_ERROR_MESSAGE = "Dataspace Not Exists";

	public static String DATASPACE_CLOSED_ERROR_CODE = "MDM_009";
	public static String DATASPACE_CLOSED_ERROR_MESSAGE = "Dataspace Already Closed. Workflow cannot be Triggered";

	public static String DATASPACE_CREATION_SUCCESS_CODE = "MDM_010";
	public static String DATASPACE_CREATION_SUCCESS_MESSAGE = "Dataspace Created Successfully";

	public static String DATASPACE_DROP_SUCCESS_CODE = "MDM_011";
	public static String DATASPACE_DROP_SUCCESS_MESSAGE = "Dataspace Closed Successfully";

	public static String DATASPACE_WF_SUCCESS_CODE = "MDM_012";
	public static String DATASPACE_WF_SUCCESS_MESSAGE = "Workflow Initiated Successfully";

	public static String DATASET_NOT_PROVIDED = "MDM_013";
	public static String DATASET_NOT_PROVIDED_MESSAGE = "DATASET NOT PROVIDED";
	
	public static String DATASET_REPORTING_PERIOD = "MDM_014";
	public static String DATASET_REPORTING_PERIOD_MESSAGE = "DATASET or REPORTING PERIOD NOT DEFINED";

}
