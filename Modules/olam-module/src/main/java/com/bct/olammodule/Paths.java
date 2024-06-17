package com.bct.olammodule;

import com.orchestranetworks.schema.*;

/**
 * Generated by TIBCO EBX(R) 1214:0002, at 2023/03/22 23:14:01 [CET].
 * WARNING: Any manual changes to this class may be overwritten by generation process.
 * DO NOT MODIFY THIS CLASS.
 * 
 * This interface defines constants related to schema [Module: olam-module, path: /WEB-INF/ebx/OlamModel.xsd].
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
	//		480 path constants.
	//		205 leaf nodes.
	public static final Path _Root = Path.parse("/root");

	// Table type path
	public final class _Root_Plant
	{
		private static final Path _Root_Plant = _Root.add("plant");
		public static Path getPathInSchema()
		{
			return _Root_Plant;
		}
		public static final Path _Root_Plant_PlantID = Path.parse("./plantID");
		public static final Path _Root_Plant_Username = Path.parse("./username");
	} 

	// Table type path
	public final class _Root_UserPlant
	{
		private static final Path _Root_UserPlant = _Root.add("userPlant");
		public static Path getPathInSchema()
		{
			return _Root_UserPlant;
		}
		public static final Path _Root_UserPlant_User = Path.parse("./user");
		public static final Path _Root_UserPlant_Plant = Path.parse("./plant");
	} 

	// Table type path
	public final class _Root_DataSecurity
	{
		private static final Path _Root_DataSecurity = _Root.add("dataSecurity");
		public static Path getPathInSchema()
		{
			return _Root_DataSecurity;
		}
		public static final Path _Root_DataSecurity_Commodity = Path.parse("./commodity");
		public static final Path _Root_DataSecurity_MaterialType = Path.parse("./materialType");
		public static final Path _Root_DataSecurity_ProcurementType = Path.parse("./procurementType");
		public static final Path _Root_DataSecurity_Plant = Path.parse("./plant");
		public static final Path _Root_DataSecurity_UiGroup = Path.parse("./uiGroup");
		public static final Path _Root_DataSecurity_AccessPermission = Path.parse("./accessPermission");
	} 

	// Table type path
	public final class _Root_Commodity
	{
		private static final Path _Root_Commodity = _Root.add("commodity");
		public static Path getPathInSchema()
		{
			return _Root_Commodity;
		}
		public static final Path _Root_Commodity_CommodityName = Path.parse("./commodityName");
	} 

	// Table type path
	public final class _Root_MaterialType
	{
		private static final Path _Root_MaterialType = _Root.add("materialType");
		public static Path getPathInSchema()
		{
			return _Root_MaterialType;
		}
		public static final Path _Root_MaterialType_Comoodity = Path.parse("./comoodity");
		public static final Path _Root_MaterialType_MatType = Path.parse("./matType");
		public static final Path _Root_MaterialType_SapMaterialType = Path.parse("./sapMaterialType");
	} 

	// Table type path
	public final class _Root_MaterialGroupTable
	{
		private static final Path _Root_MaterialGroupTable = _Root.add("materialGroupTable");
		public static Path getPathInSchema()
		{
			return _Root_MaterialGroupTable;
		}
		public static final Path _Root_MaterialGroupTable_MaterialGroup = Path.parse("./materialGroup");
	} 

	// Table type path
	public final class _Root_MaterialGroup
	{
		private static final Path _Root_MaterialGroup = _Root.add("materialGroup");
		public static Path getPathInSchema()
		{
			return _Root_MaterialGroup;
		}
		public static final Path _Root_MaterialGroup_Commodity = Path.parse("./commodity");
		public static final Path _Root_MaterialGroup_MaterialType = Path.parse("./materialType");
		public static final Path _Root_MaterialGroup_MaterialGroup = Path.parse("./materialGroup");
	} 

	// Table type path
	public final class _Root_ProcurementType
	{
		private static final Path _Root_ProcurementType = _Root.add("procurementType");
		public static Path getPathInSchema()
		{
			return _Root_ProcurementType;
		}
		public static final Path _Root_ProcurementType_ProcType = Path.parse("./procType");
	} 

	// Table type path
	public final class _Root_RecipeCodeDescription
	{
		private static final Path _Root_RecipeCodeDescription = _Root.add("recipeCodeDescription");
		public static Path getPathInSchema()
		{
			return _Root_RecipeCodeDescription;
		}
		public static final Path _Root_RecipeCodeDescription_MaterialGroup = Path.parse("./materialGroup");
		public static final Path _Root_RecipeCodeDescription_RecipeCodeDescription = Path.parse("./recipeCodeDescription");
	} 

	// Table type path
	public final class _Root_CertificationChainOfCustody
	{
		private static final Path _Root_CertificationChainOfCustody = _Root.add("certificationChainOfCustody");
		public static Path getPathInSchema()
		{
			return _Root_CertificationChainOfCustody;
		}
		public static final Path _Root_CertificationChainOfCustody_Commodity = Path.parse("./commodity");
		public static final Path _Root_CertificationChainOfCustody_CertificationChainOfCustody = Path.parse("./certificationChainOfCustody");
	} 

	// Table type path
	public final class _Root_PackSize
	{
		private static final Path _Root_PackSize = _Root.add("packSize");
		public static Path getPathInSchema()
		{
			return _Root_PackSize;
		}
		public static final Path _Root_PackSize_MaterialType = Path.parse("./materialType");
		public static final Path _Root_PackSize_MaterialGroup = Path.parse("./materialGroup");
		public static final Path _Root_PackSize_PackSize = Path.parse("./packSize");
	} 

	// Table type path
	public final class _Root_PalletTypeSize
	{
		private static final Path _Root_PalletTypeSize = _Root.add("palletTypeSize");
		public static Path getPathInSchema()
		{
			return _Root_PalletTypeSize;
		}
		public static final Path _Root_PalletTypeSize_MaterialType = Path.parse("./materialType");
		public static final Path _Root_PalletTypeSize_MaterialGroup = Path.parse("./materialGroup");
		public static final Path _Root_PalletTypeSize_PalletTypeSize = Path.parse("./palletTypeSize");
	} 

	// Table type path
	public final class _Root_MaterialDescription
	{
		private static final Path _Root_MaterialDescription = _Root.add("materialDescription");
		public static Path getPathInSchema()
		{
			return _Root_MaterialDescription;
		}
		public static final Path _Root_MaterialDescription_MaterialDescription = Path.parse("./materialDescription");
		public static final Path _Root_MaterialDescription_MaterialType = Path.parse("./materialType");
		public static final Path _Root_MaterialDescription_MaterialGroup = Path.parse("./materialGroup");
		public static final Path _Root_MaterialDescription_RecipeCodeDescription = Path.parse("./recipeCodeDescription");
		public static final Path _Root_MaterialDescription_CertificationCoC = Path.parse("./certificationCoC");
		public static final Path _Root_MaterialDescription_PackSize = Path.parse("./packSize");
		public static final Path _Root_MaterialDescription_PalletTypeSize = Path.parse("./palletTypeSize");
	} 

	// Table type path
	public final class _Root_ProfitCenter
	{
		private static final Path _Root_ProfitCenter = _Root.add("profitCenter");
		public static Path getPathInSchema()
		{
			return _Root_ProfitCenter;
		}
		public static final Path _Root_ProfitCenter_MatGroup = Path.parse("./matGroup");
		public static final Path _Root_ProfitCenter_Plant = Path.parse("./plant");
		public static final Path _Root_ProfitCenter_ProfitCenter = Path.parse("./profitCenter");
	} 

	// Table type path
	public final class _Root_MrpType
	{
		private static final Path _Root_MrpType = _Root.add("mrpType");
		public static Path getPathInSchema()
		{
			return _Root_MrpType;
		}
		public static final Path _Root_MrpType_MatType = Path.parse("./matType");
		public static final Path _Root_MrpType_Plant = Path.parse("./plant");
		public static final Path _Root_MrpType_MrpType = Path.parse("./mrpType");
	} 

	// Table type path
	public final class _Root_MrpController
	{
		private static final Path _Root_MrpController = _Root.add("mrpController");
		public static Path getPathInSchema()
		{
			return _Root_MrpController;
		}
		public static final Path _Root_MrpController_MatType = Path.parse("./matType");
		public static final Path _Root_MrpController_Plant = Path.parse("./plant");
		public static final Path _Root_MrpController_ProcType = Path.parse("./procType");
		public static final Path _Root_MrpController_MrpController = Path.parse("./mrpController");
	} 

	// Table type path
	public final class _Root_MinShelfLife
	{
		private static final Path _Root_MinShelfLife = _Root.add("minShelfLife");
		public static Path getPathInSchema()
		{
			return _Root_MinShelfLife;
		}
		public static final Path _Root_MinShelfLife_MatType = Path.parse("./matType");
		public static final Path _Root_MinShelfLife_MatGroup = Path.parse("./matGroup");
		public static final Path _Root_MinShelfLife_ProcType = Path.parse("./procType");
		public static final Path _Root_MinShelfLife_Plant = Path.parse("./plant");
		public static final Path _Root_MinShelfLife_MinShelf = Path.parse("./minShelf");
	} 

	// Table type path
	public final class _Root_TotalShelfLife
	{
		private static final Path _Root_TotalShelfLife = _Root.add("totalShelfLife");
		public static Path getPathInSchema()
		{
			return _Root_TotalShelfLife;
		}
		public static final Path _Root_TotalShelfLife_MatType = Path.parse("./matType");
		public static final Path _Root_TotalShelfLife_MatGroup = Path.parse("./matGroup");
		public static final Path _Root_TotalShelfLife_ProcType = Path.parse("./procType");
		public static final Path _Root_TotalShelfLife_Plant = Path.parse("./plant");
		public static final Path _Root_TotalShelfLife_TotalShelf = Path.parse("./totalShelf");
	} 

	// Table type path
	public final class _Root_DistributionChannel
	{
		private static final Path _Root_DistributionChannel = _Root.add("distributionChannel");
		public static Path getPathInSchema()
		{
			return _Root_DistributionChannel;
		}
		public static final Path _Root_DistributionChannel_ChannelCode = Path.parse("./channelCode");
		public static final Path _Root_DistributionChannel_DistributionChannel = Path.parse("./distributionChannel");
	} 

	// Table type path
	public final class _Root_Languages
	{
		private static final Path _Root_Languages = _Root.add("languages");
		public static Path getPathInSchema()
		{
			return _Root_Languages;
		}
		public static final Path _Root_Languages_LanguageCode = Path.parse("./languageCode");
		public static final Path _Root_Languages_Language = Path.parse("./language");
	} 

	// Table type path
	public final class _Root_UnitsOfMeasure
	{
		private static final Path _Root_UnitsOfMeasure = _Root.add("unitsOfMeasure");
		public static Path getPathInSchema()
		{
			return _Root_UnitsOfMeasure;
		}
		public static final Path _Root_UnitsOfMeasure_UomCode = Path.parse("./uomCode");
		public static final Path _Root_UnitsOfMeasure_Uom = Path.parse("./uom");
	} 

	// Table type path
	public final class _Root_ClassTypeList
	{
		private static final Path _Root_ClassTypeList = _Root.add("classTypeList");
		public static Path getPathInSchema()
		{
			return _Root_ClassTypeList;
		}
		public static final Path _Root_ClassTypeList_ClassTypeCode = Path.parse("./classTypeCode");
		public static final Path _Root_ClassTypeList_ClassType = Path.parse("./classType");
	} 

	// Table type path
	public final class _Root_ClassList
	{
		private static final Path _Root_ClassList = _Root.add("classList");
		public static Path getPathInSchema()
		{
			return _Root_ClassList;
		}
		public static final Path _Root_ClassList_Class = Path.parse("./class");
	} 

	// Table type path
	public final class _Root_Basic
	{
		private static final Path _Root_Basic = _Root.add("basic");
		public static Path getPathInSchema()
		{
			return _Root_Basic;
		}
		public static final Path _Root_Basic_MDMMatID = Path.parse("./MDMMatID");
		public static final Path _Root_Basic_Commodity = Path.parse("./commodity");
		public static final Path _Root_Basic_MatType = Path.parse("./matType");
		public static final Path _Root_Basic_MatGroup = Path.parse("./matGroup");
		public static final Path _Root_Basic_RecipeCode = Path.parse("./recipeCode");
		public static final Path _Root_Basic_Certification = Path.parse("./certification");
		public static final Path _Root_Basic_PackSize = Path.parse("./packSize");
		public static final Path _Root_Basic_PalletTypeSize = Path.parse("./palletTypeSize");
		public static final Path _Root_Basic_MaterialPlant = Path.parse("./materialPlant");
	} 

	// Table type path
	public final class _Root_MaterialPlant
	{
		private static final Path _Root_MaterialPlant = _Root.add("materialPlant");
		public static Path getPathInSchema()
		{
			return _Root_MaterialPlant;
		}
		public static final Path _Root_MaterialPlant_InitialScreen = Path.parse("./initialScreen");
		public static final Path _Root_MaterialPlant_InitialScreen_MdmID = Path.parse("./initialScreen/mdmID");
		public static final Path _Root_MaterialPlant_InitialScreen_BasicMaterialID = Path.parse("./initialScreen/basicMaterialID");
		public static final Path _Root_MaterialPlant_InitialScreen_ProcurementType = Path.parse("./initialScreen/procurementType");
		public static final Path _Root_MaterialPlant_InitialScreen_Plant = Path.parse("./initialScreen/plant");
		public static final Path _Root_MaterialPlant_InitialScreen_IndustrySector = Path.parse("./initialScreen/industrySector");
		public static final Path _Root_MaterialPlant_InitialScreen_MaterialType = Path.parse("./initialScreen/materialType");
		public static final Path _Root_MaterialPlant_InitialScreen_SalesOrg = Path.parse("./initialScreen/salesOrg");
		public static final Path _Root_MaterialPlant_InitialScreen_DistributionChannel = Path.parse("./initialScreen/distributionChannel");
		public static final Path _Root_MaterialPlant_InitialScreen_Warehouse = Path.parse("./initialScreen/warehouse");
		public static final Path _Root_MaterialPlant_InitialScreen_UserId = Path.parse("./initialScreen/userId");
		public static final Path _Root_MaterialPlant_BasicData1 = Path.parse("./basicData1");
		public static final Path _Root_MaterialPlant_BasicData1_MaterialDescription = Path.parse("./basicData1/materialDescription");
		public static final Path _Root_MaterialPlant_BasicData1_BaseUnitOfMeasure = Path.parse("./basicData1/baseUnitOfMeasure");
		public static final Path _Root_MaterialPlant_BasicData1_MaterialGroup = Path.parse("./basicData1/materialGroup");
		public static final Path _Root_MaterialPlant_BasicData1_OlaMaterialNumber = Path.parse("./basicData1/olaMaterialNumber");
		public static final Path _Root_MaterialPlant_BasicData1_Division = Path.parse("./basicData1/division");
		public static final Path _Root_MaterialPlant_BasicData1_GeneralItemCategoryGroup = Path.parse("./basicData1/generalItemCategoryGroup");
		public static final Path _Root_MaterialPlant_BasicData1_GrossWeight = Path.parse("./basicData1/grossWeight");
		public static final Path _Root_MaterialPlant_BasicData1_WeightUnit = Path.parse("./basicData1/weightUnit");
		public static final Path _Root_MaterialPlant_BasicData1_NetWeight = Path.parse("./basicData1/netWeight");
		public static final Path _Root_MaterialPlant_BasicData1_MaterialGroupPackingMaterials = Path.parse("./basicData1/materialGroupPackingMaterials");
		public static final Path _Root_MaterialPlant_BasicData2 = Path.parse("./basicData2");
		public static final Path _Root_MaterialPlant_BasicData2_Document = Path.parse("./basicData2/document");
		public static final Path _Root_MaterialPlant_SalesOrg1 = Path.parse("./salesOrg1");
		public static final Path _Root_MaterialPlant_SalesOrg1_SalesUnit = Path.parse("./salesOrg1/salesUnit");
		public static final Path _Root_MaterialPlant_SalesOrg1_DeliveringPlant = Path.parse("./salesOrg1/deliveringPlant");
		public static final Path _Root_MaterialPlant_SalesOrg1_TaxData1Country = Path.parse("./salesOrg1/taxData1Country");
		public static final Path _Root_MaterialPlant_SalesOrg1_TaxData1Category = Path.parse("./salesOrg1/taxData1Category");
		public static final Path _Root_MaterialPlant_SalesOrg1_TaxData1Classification = Path.parse("./salesOrg1/taxData1Classification");
		public static final Path _Root_MaterialPlant_SalesOrg1_TaxData2Country = Path.parse("./salesOrg1/taxData2Country");
		public static final Path _Root_MaterialPlant_SalesOrg1_TaxData2Category = Path.parse("./salesOrg1/taxData2Category");
		public static final Path _Root_MaterialPlant_SalesOrg1_TaxData2Classification = Path.parse("./salesOrg1/taxData2Classification");
		public static final Path _Root_MaterialPlant_SalesOrg1_TaxData3Country = Path.parse("./salesOrg1/taxData3Country");
		public static final Path _Root_MaterialPlant_SalesOrg1_TaxData3Category = Path.parse("./salesOrg1/taxData3Category");
		public static final Path _Root_MaterialPlant_SalesOrg1_TaxData3Classification = Path.parse("./salesOrg1/taxData3Classification");
		public static final Path _Root_MaterialPlant_SalesOrg2 = Path.parse("./salesOrg2");
		public static final Path _Root_MaterialPlant_SalesOrg2_AccountAssignmentGroup = Path.parse("./salesOrg2/accountAssignmentGroup");
		public static final Path _Root_MaterialPlant_SalesOrg2_ItemCategoryGroup = Path.parse("./salesOrg2/itemCategoryGroup");
		public static final Path _Root_MaterialPlant_SalesOrg2_MaterialGroup1 = Path.parse("./salesOrg2/materialGroup1");
		public static final Path _Root_MaterialPlant_SalesOrg2_MaterialGroup2 = Path.parse("./salesOrg2/materialGroup2");
		public static final Path _Root_MaterialPlant_SalesOrg2_MaterialGroup3 = Path.parse("./salesOrg2/materialGroup3");
		public static final Path _Root_MaterialPlant_SalesOrg2_MaterialGroup4 = Path.parse("./salesOrg2/materialGroup4");
		public static final Path _Root_MaterialPlant_SalesOrg2_MaterialGroup5 = Path.parse("./salesOrg2/materialGroup5");
		public static final Path _Root_MaterialPlant_SalesGeneralPlant = Path.parse("./salesGeneralPlant");
		public static final Path _Root_MaterialPlant_SalesGeneralPlant_AvailabilityCheck = Path.parse("./salesGeneralPlant/availabilityCheck");
		public static final Path _Root_MaterialPlant_SalesGeneralPlant_TransportationGroup = Path.parse("./salesGeneralPlant/transportationGroup");
		public static final Path _Root_MaterialPlant_SalesGeneralPlant_LoadingGroup = Path.parse("./salesGeneralPlant/loadingGroup");
		public static final Path _Root_MaterialPlant_SalesGeneralPlant_ProfitCenter = Path.parse("./salesGeneralPlant/profitCenter");
		public static final Path _Root_MaterialPlant_SalesText = Path.parse("./salesText");
		public static final Path _Root_MaterialPlant_SalesText_Language = Path.parse("./salesText/language");
		public static final Path _Root_MaterialPlant_SalesText_Text = Path.parse("./salesText/text");
		public static final Path _Root_MaterialPlant_SalesText_Languagefrench = Path.parse("./salesText/languagefrench");
		public static final Path _Root_MaterialPlant_SalesText_TextFrench = Path.parse("./salesText/textFrench");
		public static final Path _Root_MaterialPlant_Purchasing = Path.parse("./purchasing");
		public static final Path _Root_MaterialPlant_Purchasing_OrderUnit = Path.parse("./purchasing/orderUnit");
		public static final Path _Root_MaterialPlant_Purchasing_PurchasingGroup = Path.parse("./purchasing/purchasingGroup");
		public static final Path _Root_MaterialPlant_PurchasingOrderText = Path.parse("./purchasingOrderText");
		public static final Path _Root_MaterialPlant_PurchasingOrderText_Language = Path.parse("./purchasingOrderText/language");
		public static final Path _Root_MaterialPlant_PurchasingOrderText_Text = Path.parse("./purchasingOrderText/text");
		public static final Path _Root_MaterialPlant_Mrp1 = Path.parse("./mrp1");
		public static final Path _Root_MaterialPlant_Mrp1_MrpType = Path.parse("./mrp1/mrpType");
		public static final Path _Root_MaterialPlant_Mrp1_MrpController = Path.parse("./mrp1/mrpController");
		public static final Path _Root_MaterialPlant_Mrp1_LotSize = Path.parse("./mrp1/lotSize");
		public static final Path _Root_MaterialPlant_Mrp2 = Path.parse("./mrp2");
		public static final Path _Root_MaterialPlant_Mrp2_ProcurementType = Path.parse("./mrp2/procurementType");
		public static final Path _Root_MaterialPlant_Mrp2_SpecialProc = Path.parse("./mrp2/specialProc");
		public static final Path _Root_MaterialPlant_Mrp2_BatchEntry = Path.parse("./mrp2/batchEntry");
		public static final Path _Root_MaterialPlant_Mrp2_ProdStorageLoc = Path.parse("./mrp2/prodStorageLoc");
		public static final Path _Root_MaterialPlant_Mrp2_Backflush = Path.parse("./mrp2/backflush");
		public static final Path _Root_MaterialPlant_Mrp2_InProd = Path.parse("./mrp2/inProd");
		public static final Path _Root_MaterialPlant_Mrp2_GrProcessTime = Path.parse("./mrp2/grProcessTime");
		public static final Path _Root_MaterialPlant_Mrp2_SchedulingMarginKey = Path.parse("./mrp2/schedulingMarginKey");
		public static final Path _Root_MaterialPlant_Mrp3 = Path.parse("./mrp3");
		public static final Path _Root_MaterialPlant_Mrp3_PeriodIndicator = Path.parse("./mrp3/periodIndicator");
		public static final Path _Root_MaterialPlant_Mrp3_StrategyGroup = Path.parse("./mrp3/strategyGroup");
		public static final Path _Root_MaterialPlant_Mrp3_AvailCheck = Path.parse("./mrp3/availCheck");
		public static final Path _Root_MaterialPlant_Mrp4 = Path.parse("./mrp4");
		public static final Path _Root_MaterialPlant_Mrp4_SelectionMethod = Path.parse("./mrp4/selectionMethod");
		public static final Path _Root_MaterialPlant_WorkScheduling = Path.parse("./workScheduling");
		public static final Path _Root_MaterialPlant_WorkScheduling_ProductionSupervisor = Path.parse("./workScheduling/productionSupervisor");
		public static final Path _Root_MaterialPlant_WorkScheduling_ProdSchedlingProfile = Path.parse("./workScheduling/prodSchedlingProfile");
		public static final Path _Root_MaterialPlant_PlantDataStorageLocation1 = Path.parse("./plantDataStorageLocation1");
		public static final Path _Root_MaterialPlant_PlantDataStorageLocation1_BatchManagement = Path.parse("./plantDataStorageLocation1/batchManagement");
		public static final Path _Root_MaterialPlant_PlantDataStorageLocation1_MinShelfLife = Path.parse("./plantDataStorageLocation1/minShelfLife");
		public static final Path _Root_MaterialPlant_PlantDataStorageLocation1_TotalShelfLife = Path.parse("./plantDataStorageLocation1/totalShelfLife");
		public static final Path _Root_MaterialPlant_PlantDataStorageLocation1_PeriodIndicatorForSLED = Path.parse("./plantDataStorageLocation1/periodIndicatorForSLED");
		public static final Path _Root_MaterialPlant_PlantDataStorageLocation2 = Path.parse("./plantDataStorageLocation2");
		public static final Path _Root_MaterialPlant_PlantDataStorageLocation2_ProfitCenter = Path.parse("./plantDataStorageLocation2/profitCenter");
		public static final Path _Root_MaterialPlant_WarehouseManagement1 = Path.parse("./warehouseManagement1");
		public static final Path _Root_MaterialPlant_WarehouseManagement1_StockRemoval = Path.parse("./warehouseManagement1/stockRemoval");
		public static final Path _Root_MaterialPlant_WarehouseManagement1_StockPlacement = Path.parse("./warehouseManagement1/stockPlacement");
		public static final Path _Root_MaterialPlant_WarehouseManagement1_StorageSectionIndicators = Path.parse("./warehouseManagement1/storageSectionIndicators");
		public static final Path _Root_MaterialPlant_QualityManagement = Path.parse("./qualityManagement");
		public static final Path _Root_MaterialPlant_QualityManagement_InspectionType1 = Path.parse("./qualityManagement/inspectionType1");
		public static final Path _Root_MaterialPlant_QualityManagement_PreferInspectType1 = Path.parse("./qualityManagement/preferInspectType1");
		public static final Path _Root_MaterialPlant_QualityManagement_ActiveCheck1 = Path.parse("./qualityManagement/activeCheck1");
		public static final Path _Root_MaterialPlant_QualityManagement_InspectHUCheck1 = Path.parse("./qualityManagement/inspectHUCheck1");
		public static final Path _Root_MaterialPlant_QualityManagement_InspectionType2 = Path.parse("./qualityManagement/inspectionType2");
		public static final Path _Root_MaterialPlant_QualityManagement_PreferInspectType2 = Path.parse("./qualityManagement/preferInspectType2");
		public static final Path _Root_MaterialPlant_QualityManagement_ActiveCheck2 = Path.parse("./qualityManagement/activeCheck2");
		public static final Path _Root_MaterialPlant_QualityManagement_InspectHUCheck2 = Path.parse("./qualityManagement/inspectHUCheck2");
		public static final Path _Root_MaterialPlant_QualityManagement_InspectionType3 = Path.parse("./qualityManagement/inspectionType3");
		public static final Path _Root_MaterialPlant_QualityManagement_PreferInspectType3 = Path.parse("./qualityManagement/preferInspectType3");
		public static final Path _Root_MaterialPlant_QualityManagement_ActiveCheck3 = Path.parse("./qualityManagement/activeCheck3");
		public static final Path _Root_MaterialPlant_QualityManagement_InspectHUCheck3 = Path.parse("./qualityManagement/inspectHUCheck3");
		public static final Path _Root_MaterialPlant_QualityManagement_InspectionType4 = Path.parse("./qualityManagement/inspectionType4");
		public static final Path _Root_MaterialPlant_QualityManagement_PreferInspectType4 = Path.parse("./qualityManagement/preferInspectType4");
		public static final Path _Root_MaterialPlant_QualityManagement_ActiveCheck4 = Path.parse("./qualityManagement/activeCheck4");
		public static final Path _Root_MaterialPlant_QualityManagement_InspectHUCheck4 = Path.parse("./qualityManagement/inspectHUCheck4");
		public static final Path _Root_MaterialPlant_QualityManagement_InspectionType5 = Path.parse("./qualityManagement/inspectionType5");
		public static final Path _Root_MaterialPlant_QualityManagement_PreferInspectType5 = Path.parse("./qualityManagement/preferInspectType5");
		public static final Path _Root_MaterialPlant_QualityManagement_ActiveCheck5 = Path.parse("./qualityManagement/activeCheck5");
		public static final Path _Root_MaterialPlant_QualityManagement_InspectHUCheck5 = Path.parse("./qualityManagement/inspectHUCheck5");
		public static final Path _Root_MaterialPlant_Accounting1 = Path.parse("./accounting1");
		public static final Path _Root_MaterialPlant_Accounting1_ValuationClass = Path.parse("./accounting1/valuationClass");
		public static final Path _Root_MaterialPlant_Accounting1_ValueCategory = Path.parse("./accounting1/valueCategory");
		public static final Path _Root_MaterialPlant_Accounting1_PriceDetermination = Path.parse("./accounting1/priceDetermination");
		public static final Path _Root_MaterialPlant_Accounting1_MlActiveCheck = Path.parse("./accounting1/mlActiveCheck");
		public static final Path _Root_MaterialPlant_Accounting1_PriceControl = Path.parse("./accounting1/priceControl");
		public static final Path _Root_MaterialPlant_Accounting1_StandardPrice = Path.parse("./accounting1/standardPrice");
		public static final Path _Root_MaterialPlant_Accounting1_Priceunit = Path.parse("./accounting1/priceunit");
		public static final Path _Root_MaterialPlant_Costing1 = Path.parse("./costing1");
		public static final Path _Root_MaterialPlant_Costing1_QuantityStructure = Path.parse("./costing1/quantityStructure");
		public static final Path _Root_MaterialPlant_Costing1_MatOriginCheck = Path.parse("./costing1/matOriginCheck");
		public static final Path _Root_MaterialPlant_Costing1_VarianceKey = Path.parse("./costing1/varianceKey");
		public static final Path _Root_MaterialPlant_Costing1_CostingLotSize = Path.parse("./costing1/costingLotSize");
		public static final Path _Root_MaterialPlant_Costing2 = Path.parse("./costing2");
		public static final Path _Root_MaterialPlant_Costing2_PlannedPriceFuture = Path.parse("./costing2/plannedPriceFuture");
		public static final Path _Root_MaterialPlant_AddDataDescriptions = Path.parse("./addDataDescriptions");
		public static final Path _Root_MaterialPlant_AddDataDescriptions_Language1 = Path.parse("./addDataDescriptions/language1");
		public static final Path _Root_MaterialPlant_AddDataDescriptions_MatDesc1 = Path.parse("./addDataDescriptions/matDesc1");
		public static final Path _Root_MaterialPlant_AddDataDescriptions_Language2 = Path.parse("./addDataDescriptions/language2");
		public static final Path _Root_MaterialPlant_AddDataDescriptions_MatDesc2 = Path.parse("./addDataDescriptions/matDesc2");
		public static final Path _Root_MaterialPlant_AddDataUnitsOfMeasure = Path.parse("./addDataUnitsOfMeasure");
		public static final Path _Root_MaterialPlant_AddDataUnitsOfMeasure_AltUomQty1 = Path.parse("./addDataUnitsOfMeasure/altUomQty1");
		public static final Path _Root_MaterialPlant_AddDataUnitsOfMeasure_AltUom1 = Path.parse("./addDataUnitsOfMeasure/altUom1");
		public static final Path _Root_MaterialPlant_AddDataUnitsOfMeasure_BaseUomQty1 = Path.parse("./addDataUnitsOfMeasure/baseUomQty1");
		public static final Path _Root_MaterialPlant_AddDataUnitsOfMeasure_AltUomQty2 = Path.parse("./addDataUnitsOfMeasure/altUomQty2");
		public static final Path _Root_MaterialPlant_AddDataUnitsOfMeasure_AltUom2 = Path.parse("./addDataUnitsOfMeasure/altUom2");
		public static final Path _Root_MaterialPlant_AddDataUnitsOfMeasure_BaseUomQty2 = Path.parse("./addDataUnitsOfMeasure/baseUomQty2");
		public static final Path _Root_MaterialPlant_AddDataUnitsOfMeasure_AltUomQty3 = Path.parse("./addDataUnitsOfMeasure/altUomQty3");
		public static final Path _Root_MaterialPlant_AddDataUnitsOfMeasure_AltUom3 = Path.parse("./addDataUnitsOfMeasure/altUom3");
		public static final Path _Root_MaterialPlant_AddDataUnitsOfMeasure_BaseUomQty3 = Path.parse("./addDataUnitsOfMeasure/baseUomQty3");
		public static final Path _Root_MaterialPlant_Classification = Path.parse("./classification");
		public static final Path _Root_MaterialPlant_Classification_ClassType1 = Path.parse("./classification/classType1");
		public static final Path _Root_MaterialPlant_Classification_Class1 = Path.parse("./classification/class1");
		public static final Path _Root_MaterialPlant_Classification_CharLegacyCode = Path.parse("./classification/charLegacyCode");
		public static final Path _Root_MaterialPlant_Classification_ClassType2 = Path.parse("./classification/classType2");
		public static final Path _Root_MaterialPlant_Classification_Class2 = Path.parse("./classification/class2");
		public static final Path _Root_MaterialPlant_Classification_CharProdCategory = Path.parse("./classification/charProdCategory");
		public static final Path _Root_MaterialPlant_Classification_CharProdSubcat = Path.parse("./classification/charProdSubcat");
		public static final Path _Root_MaterialPlant_Classification_ProductGroup = Path.parse("./classification/productGroup");
		public static final Path _Root_MaterialPlant_Classification_ClassType3 = Path.parse("./classification/classType3");
		public static final Path _Root_MaterialPlant_Classification_Class3 = Path.parse("./classification/class3");
	} 
	// ===============================================

}
