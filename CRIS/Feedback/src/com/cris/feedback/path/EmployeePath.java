package com.cris.feedback.path;

import com.orchestranetworks.schema.*;

/**
 * Generated by TIBCO EBX(R) 1215:0003, at 2023/07/10 16:41:39 [CEST].
 * WARNING: Any manual changes to this class may be overwritten by generation process.
 * DO NOT MODIFY THIS CLASS.
 * 
 * This interface defines constants related to schema [Publication: Employee].
 * 
 * Root paths in this interface: 
 * 	'/root'   relativeToRoot: false
 * 
 */
public interface EmployeePath
{
	// ===============================================
	// Constants for nodes under '/root'.
	// Statistics:
	//		16 path constants.
	//		5 leaf nodes.
	public static final Path _Root = Path.parse("/root");

	// Table type path
	public final class _Root_Employee
	{
		private static final Path _Root_Employee = _Root.add("Employee");
		public static Path getPathInSchema()
		{
			return _Root_Employee;
		}
		public static final Path _Root_Employee_Id = Path.parse("./id");
		public static final Path _Root_Employee_EmployeeId = Path.parse("./EmployeeId");
		public static final Path _Root_Employee_EmployeeName = Path.parse("./EmployeeName");
		public static final Path _Root_Employee_EmployeeDesignation = Path.parse("./EmployeeDesignation");
		public static final Path _Root_Employee_EmployeeMobileNumber = Path.parse("./EmployeeMobileNumber");
	} 
	// ===============================================

}
