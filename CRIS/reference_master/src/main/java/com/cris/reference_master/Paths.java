package com.cris.reference_master;

import com.orchestranetworks.schema.*;

/**
 * Generated by TIBCO EBX(R) 1214:0002, at 2023/03/22 23:14:01 [CET].
 * WARNING: Any manual changes to this class may be overwritten by generation process.
 * DO NOT MODIFY THIS CLASS.
 * 
 * This interface defines constants related to schema [Module: reference_master, path: /WEB-INF/ebx/reference_master_data_model.xsd].
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
	//		237 path constants.
	//		96 leaf nodes.
	public static final Path _Root = Path.parse("/root");

	// Table type path
	public final class _Root_Reference_Code
	{
		private static final Path _Root_Reference_Code = _Root.add("Reference_Code");
		public static Path getPathInSchema()
		{
			return _Root_Reference_Code;
		}
		public static final Path _Root_Reference_Code_Ref_Code = Path.parse("./Ref_Code");
		public static final Path _Root_Reference_Code_Ref_Code_Description = Path.parse("./Ref_Code_Description");
	} 

	// Table type path
	public final class _Root_Reference_Value
	{
		private static final Path _Root_Reference_Value = _Root.add("Reference_Value");
		public static Path getPathInSchema()
		{
			return _Root_Reference_Value;
		}
		public static final Path _Root_Reference_Value_Ref_Value_ID = Path.parse("./Ref_Value_ID");
		public static final Path _Root_Reference_Value_Ref_Code = Path.parse("./Ref_Code");
		public static final Path _Root_Reference_Value_Ref_Value = Path.parse("./Ref_Value");
		public static final Path _Root_Reference_Value_Ref_Value_Display = Path.parse("./Ref_Value_Display");
		public static final Path _Root_Reference_Value_Ref_Value_Description = Path.parse("./Ref_Value_Description");
		public static final Path _Root_Reference_Value_Ref_Value_Sort_Order = Path.parse("./Ref_Value_Sort_Order");
	} 

	// Table type path
	public final class _Root_Zone
	{
		private static final Path _Root_Zone = _Root.add("Zone");
		public static Path getPathInSchema()
		{
			return _Root_Zone;
		}
		public static final Path _Root_Zone_Zone_Code = Path.parse("./Zone_Code");
		public static final Path _Root_Zone_Zone_Name = Path.parse("./Zone_Name");
		public static final Path _Root_Zone_Zone_Sequence_Number = Path.parse("./Zone_Sequence_Number");
		public static final Path _Root_Zone_Valid_From = Path.parse("./Valid_From");
		public static final Path _Root_Zone_Valid_Upto = Path.parse("./Valid_Upto");
		public static final Path _Root_Zone_Active = Path.parse("./Active");
		public static final Path _Root_Zone_IR_Zone = Path.parse("./IR_Zone");
	} 

	// Table type path
	public final class _Root_Division
	{
		private static final Path _Root_Division = _Root.add("Division");
		public static Path getPathInSchema()
		{
			return _Root_Division;
		}
		public static final Path _Root_Division_Division_Code = Path.parse("./Division_Code");
		public static final Path _Root_Division_Division_Name = Path.parse("./Division_Name");
		public static final Path _Root_Division_Zone_Code = Path.parse("./Zone_Code");
		public static final Path _Root_Division_Division_Serial_Number = Path.parse("./Division_Serial_Number");
		public static final Path _Root_Division_Valid_From = Path.parse("./Valid_From");
		public static final Path _Root_Division_Valid_Upto = Path.parse("./Valid_Upto");
		public static final Path _Root_Division_Active = Path.parse("./Active");
		public static final Path _Root_Division_IR_Division = Path.parse("./IR_Division");
	} 

	// Table type path
	public final class _Root_Shed
	{
		private static final Path _Root_Shed = _Root.add("Shed");
		public static Path getPathInSchema()
		{
			return _Root_Shed;
		}
		public static final Path _Root_Shed_Zone_Code = Path.parse("./Zone_Code");
		public static final Path _Root_Shed_Division_Code = Path.parse("./Division_Code");
		public static final Path _Root_Shed_Shed_Code = Path.parse("./Shed_Code");
		public static final Path _Root_Shed_Shed_Name = Path.parse("./Shed_Name");
		public static final Path _Root_Shed_Shed_Flag = Path.parse("./Shed_Flag");
		public static final Path _Root_Shed_Source_System_Name = Path.parse("./Source_System_Name");
		public static final Path _Root_Shed_IR_Flag = Path.parse("./IR_Flag");
		public static final Path _Root_Shed_Validity = Path.parse("./Validity");
		public static final Path _Root_Shed_Serving_Station = Path.parse("./Serving_Station");
		public static final Path _Root_Shed_Valid_From = Path.parse("./Valid_From");
		public static final Path _Root_Shed_Valid_To = Path.parse("./Valid_To");
	} 

	// Table type path
	public final class _Root_Depot
	{
		private static final Path _Root_Depot = _Root.add("Depot");
		public static Path getPathInSchema()
		{
			return _Root_Depot;
		}
		public static final Path _Root_Depot_Zone_Code = Path.parse("./Zone_Code");
		public static final Path _Root_Depot_Division_Code = Path.parse("./Division_Code");
		public static final Path _Root_Depot_Depot_Code = Path.parse("./Depot_Code");
		public static final Path _Root_Depot_Depot_Name = Path.parse("./Depot_Name");
	} 

	// Table type path
	public final class _Root_Wagon_Organisation_Type
	{
		private static final Path _Root_Wagon_Organisation_Type = _Root.add("Wagon_Organisation_Type");
		public static Path getPathInSchema()
		{
			return _Root_Wagon_Organisation_Type;
		}
		public static final Path _Root_Wagon_Organisation_Type_Organisation_Type = Path.parse("./Organisation_Type");
		public static final Path _Root_Wagon_Organisation_Type_Organisation_Type_Description = Path.parse("./Organisation_Type_Description");
		public static final Path _Root_Wagon_Organisation_Type_Organisation_Type_Level = Path.parse("./Organisation_Type_Level");
		public static final Path _Root_Wagon_Organisation_Type_PRTN_Org_Type = Path.parse("./PRTN_Org_Type");
	} 

	// Table type path
	public final class _Root_Wagon_Org_M
	{
		private static final Path _Root_Wagon_Org_M = _Root.add("Wagon_Org_M");
		public static Path getPathInSchema()
		{
			return _Root_Wagon_Org_M;
		}
		public static final Path _Root_Wagon_Org_M_Org_SlNo = Path.parse("./Org_SlNo");
		public static final Path _Root_Wagon_Org_M_Org_Code = Path.parse("./Org_Code");
		public static final Path _Root_Wagon_Org_M_Org_Type = Path.parse("./Org_Type");
		public static final Path _Root_Wagon_Org_M_Org_Desc = Path.parse("./Org_Desc");
		public static final Path _Root_Wagon_Org_M_Org_Seq = Path.parse("./Org_Seq");
		public static final Path _Root_Wagon_Org_M_PRNT_ORG_SLNO = Path.parse("./PRNT_ORG_SLNO");
		public static final Path _Root_Wagon_Org_M_Num_Code = Path.parse("./Num_Code");
		public static final Path _Root_Wagon_Org_M_PVT_RLY = Path.parse("./PVT_RLY");
	} 

	// Table type path
	public final class _Root_Coach_manufacturer
	{
		private static final Path _Root_Coach_manufacturer = _Root.add("coach_manufacturer");
		public static Path getPathInSchema()
		{
			return _Root_Coach_manufacturer;
		}
		public static final Path _Root_Coach_manufacturer_Manufacturing_unit_code = Path.parse("./manufacturing_unit_code");
		public static final Path _Root_Coach_manufacturer_Ir_flag = Path.parse("./ir_flag");
		public static final Path _Root_Coach_manufacturer_Manufacturing_unit_name = Path.parse("./manufacturing_unit_name");
	} 

	// Table type path
	public final class _Root_District
	{
		private static final Path _Root_District = _Root.add("District");
		public static Path getPathInSchema()
		{
			return _Root_District;
		}
		public static final Path _Root_District_District_Code = Path.parse("./District_Code");
		public static final Path _Root_District_District_Name = Path.parse("./District_Name");
		public static final Path _Root_District_State_Name = Path.parse("./State_Name");
	} 

	// Table type path
	public final class _Root_State
	{
		private static final Path _Root_State = _Root.add("State");
		public static Path getPathInSchema()
		{
			return _Root_State;
		}
		public static final Path _Root_State_State_Code = Path.parse("./State_Code");
		public static final Path _Root_State_State_Name = Path.parse("./State_Name");
	} 

	// Table type path
	public final class _Root_Transformation_Function_Table
	{
		private static final Path _Root_Transformation_Function_Table = _Root.add("Transformation_Function_Table");
		public static Path getPathInSchema()
		{
			return _Root_Transformation_Function_Table;
		}
		public static final Path _Root_Transformation_Function_Table_Entity = Path.parse("./Entity");
		public static final Path _Root_Transformation_Function_Table_Attribute_Name = Path.parse("./Attribute_Name");
		public static final Path _Root_Transformation_Function_Table_MDM = Path.parse("./MDM");
		public static final Path _Root_Transformation_Function_Table_SLAM = Path.parse("./SLAM");
		public static final Path _Root_Transformation_Function_Table_FOIS = Path.parse("./FOIS");
		public static final Path _Root_Transformation_Function_Table_RBS = Path.parse("./RBS");
		public static final Path _Root_Transformation_Function_Table_Lookup_SLAM = Path.parse("./Lookup_SLAM");
		public static final Path _Root_Transformation_Function_Table_Lookup_FOIS = Path.parse("./Lookup_FOIS");
		public static final Path _Root_Transformation_Function_Table_Lookup_RBS = Path.parse("./Lookup_RBS");
	} 

	// Table type path
	public final class _Root_User_Role_Mapping
	{
		private static final Path _Root_User_Role_Mapping = _Root.add("User_Role_Mapping");
		public static Path getPathInSchema()
		{
			return _Root_User_Role_Mapping;
		}
		public static final Path _Root_User_Role_Mapping_User = Path.parse("./User");
		public static final Path _Root_User_Role_Mapping_Level = Path.parse("./Level");
		public static final Path _Root_User_Role_Mapping_Zone = Path.parse("./Zone");
		public static final Path _Root_User_Role_Mapping_Division = Path.parse("./Division");
	} 

	// Table type path
	public final class _Root_User_Registration_Details
	{
		private static final Path _Root_User_Registration_Details = _Root.add("User_Registration_Details");
		public static Path getPathInSchema()
		{
			return _Root_User_Registration_Details;
		}
		public static final Path _Root_User_Registration_Details_User_Id = Path.parse("./User_Id");
		public static final Path _Root_User_Registration_Details_Name = Path.parse("./Name");
		public static final Path _Root_User_Registration_Details_Designation = Path.parse("./Designation");
		public static final Path _Root_User_Registration_Details_Department = Path.parse("./Department");
		public static final Path _Root_User_Registration_Details_Loco_Type = Path.parse("./Loco_Type");
		public static final Path _Root_User_Registration_Details_Shed = Path.parse("./Shed");
		public static final Path _Root_User_Registration_Details_From_Date = Path.parse("./From_Date");
		public static final Path _Root_User_Registration_Details_To_Date = Path.parse("./To_Date");
		public static final Path _Root_User_Registration_Details_Role_Type = Path.parse("./Role_Type");
		public static final Path _Root_User_Registration_Details_User_Type = Path.parse("./User_Type");
		public static final Path _Root_User_Registration_Details_Mobile_Number = Path.parse("./Mobile_Number");
		public static final Path _Root_User_Registration_Details_Entity = Path.parse("./Entity");
		public static final Path _Root_User_Registration_Details_Email = Path.parse("./Email");
		public static final Path _Root_User_Registration_Details_Created_By = Path.parse("./Created_By");
		public static final Path _Root_User_Registration_Details_Zone = Path.parse("./Zone");
		public static final Path _Root_User_Registration_Details_Divison = Path.parse("./Divison");
		public static final Path _Root_User_Registration_Details_User_Register_Approval = Path.parse("./User_Register_Approval");
		public static final Path _Root_User_Registration_Details_Service_Status = Path.parse("./Service_Status");
		public static final Path _Root_User_Registration_Details_Depo = Path.parse("./Depo");
		public static final Path _Root_User_Registration_Details_Old_User_Id = Path.parse("./Old_User_Id");
		public static final Path _Root_User_Registration_Details_Cris_User = Path.parse("./Cris_User");
		public static final Path _Root_User_Registration_Details_Asset_Designation_Code = Path.parse("./Asset_Designation_Code");
		public static final Path _Root_User_Registration_Details_Additional_Shed = Path.parse("./Additional_Shed");
		public static final Path _Root_User_Registration_Details_Additional_Shed_Active = Path.parse("./Additional_Shed_Active");
		public static final Path _Root_User_Registration_Details_Additional_Shed_Date = Path.parse("./Additional_Shed_Date");
	} 
	// ===============================================

}
