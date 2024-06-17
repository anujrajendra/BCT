package com.bct.complaintmodule;

import com.orchestranetworks.schema.*;

/**
 * Generated by TIBCO EBX(R) 1214:0002, at 2023/03/22 23:14:01 [CET].
 * WARNING: Any manual changes to this class may be overwritten by generation process.
 * DO NOT MODIFY THIS CLASS.
 * 
 * This interface defines constants related to schema [Module: complaint-module, path: /WEB-INF/ebx/ComplaintModel.xsd].
 * 
 * Root paths in this interface: 
 * 	'/root'   relativeToRoot: false
 * 
 */
public interface Paths
{
	// ===============================================
	// Constants for nodes under '/root'.
	// Statistics:
	//		44 path constants.
	//		12 leaf nodes.
	public static final Path _Root = Path.parse("/root");

	// Table type path
	public final class _Root_Department
	{
		private static final Path _Root_Department = _Root.add("department");
		public static Path getPathInSchema()
		{
			return _Root_Department;
		}
		public static final Path _Root_Department_DeptId = Path.parse("./deptId");
		public static final Path _Root_Department_DeptName = Path.parse("./deptName");
		public static final Path _Root_Department_Mail = Path.parse("./mail");
	} 

	// Table type path
	public final class _Root_ComplaintList
	{
		private static final Path _Root_ComplaintList = _Root.add("ComplaintList");
		public static Path getPathInSchema()
		{
			return _Root_ComplaintList;
		}
		public static final Path _Root_ComplaintList_Id = Path.parse("./id");
		public static final Path _Root_ComplaintList_Department = Path.parse("./department");
		public static final Path _Root_ComplaintList_Description = Path.parse("./description");
	} 

	// Table type path
	public final class _Root_ComplaintRegistration
	{
		private static final Path _Root_ComplaintRegistration = _Root.add("ComplaintRegistration");
		public static Path getPathInSchema()
		{
			return _Root_ComplaintRegistration;
		}
		public static final Path _Root_ComplaintRegistration_TicketId = Path.parse("./ticketId");
		public static final Path _Root_ComplaintRegistration_Department = Path.parse("./department");
		public static final Path _Root_ComplaintRegistration_Complaint = Path.parse("./complaint");
		public static final Path _Root_ComplaintRegistration_Priority = Path.parse("./priority");
		public static final Path _Root_ComplaintRegistration_ComplaintImage = Path.parse("./ComplaintImage");
		public static final Path _Root_ComplaintRegistration_ComplaintImage_Attachment = Path.parse("./ComplaintImage/attachment");
		public static final Path _Root_ComplaintRegistration_ComplaintStatus = Path.parse("./complaintStatus");
	} 
	// ===============================================

}