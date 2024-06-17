package com.bct.olammodule.extensions;

import com.bct.olammodule.Paths;
import com.bct.olammodule.access.*;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaExtensions;
import com.orchestranetworks.schema.SchemaExtensionsContext;
import com.orchestranetworks.schema.SchemaNode;
import com.orchestranetworks.service.AccessPermission;
import com.orchestranetworks.service.AccessRule;
import com.orchestranetworks.service.AccessRuleForCreate;
import com.orchestranetworks.service.Role;
import com.orchestranetworks.service.Session;

public class MaterialPlantExtensions implements SchemaExtensions{

	@Override
	public void defineExtensions(SchemaExtensionsContext context) {
		// TODO Auto-generated method stub
		
		final Path path = Paths._Root_MaterialPlant.getPathInSchema();
		System.out.println("===Path====="+path);
		
		final AccessRule accessRule = new AccessRule() {
			@Override
			public AccessPermission getPermission(final Adaptation adaptation, Session session, SchemaNode node) {
				// TODO Auto-generated method stub
				System.out.println("=====Access rule====");
				System.out.println("====User Reference===="+session.getUserReference());
				System.out.println("====adaptation====="+adaptation);
				
				if(adaptation.isSchemaInstance())
				{
					System.out.println("======Inside isSchemaInstance====");
					return AccessPermission.getReadWrite();
				}
				
//				if(session.isUserInRole(Role.ADMINISTRATOR))
//				{
//					System.out.println("====User Reference===="+session.getUserReference());
//					return AccessPermission.getReadWrite();
//				}
				final String plantPkString = (String) adaptation.get(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_Plant);
				String plantPk="";
				if(plantPkString!=null)
						plantPk = plantPkString.substring(plantPkString.length()-4);
				
				final String userId = session.getUserReference().getUserId();
				
				System.out.println("====Plant PK======User ID"+plantPk+" "+userId);
				
			//	final String userId = session.getUserReference().getUserId();
				final String userIdString = (String) adaptation.get(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen_UserId);
				if (session.getUserReference().getUserId().equalsIgnoreCase("admin"))
					return AccessPermission.getReadWrite();
				if(session.isUserInRole(Role.forSpecificRole("RProduct Development Team")) || 
						session.isUserInRole(Role.forSpecificRole("RSales Team")))
					return AccessPermission.getReadWrite();
				if(!userId.equalsIgnoreCase(userIdString))
					return AccessPermission.getHidden();
				else
					return AccessPermission.getReadWrite();
//				
//				if(plantPk.matches("4861|4899") && userId.equals("ram"))
//				{
//					System.out.println("=====Inside plantPk.matches(\"4861|4899\") && userId.equals(\"ram\")=====");
//					return AccessPermission.getHidden();
//				}
//				else if(plantPk.matches("4184|4899") && userId.equals("prashant"))
//				{
//					return AccessPermission.getHidden();
//				}
//				else if(plantPk.matches("4861|4184") && userId.equals("lalith"))
//				{
//					return AccessPermission.getHidden();
//				}
//				else if(plantPk.matches("4861") && userId.equalsIgnoreCase("Saurabh"))
//				{
//					return AccessPermission.getHidden();
//				}
//				else if(userId.equalsIgnoreCase("diksha"))
//				{
//					return AccessPermission.getReadWrite();
//				} 
//			
//				return AccessPermission.getReadWrite();
			}
		};
		context.setAccessRuleOnOccurrence(path, accessRule);
		//context.setAccessRuleOnNodeAndAllDescendants(path, false, accessRule);
		
		final Path initialScreenPath = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_InitialScreen);
		final AccessRule initialScreenAccessRule = new InitialScreenAccessRule();
		final AccessRuleForCreate initialScreenAccessRuleForCreate = new InitialScreenAccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(initialScreenPath, false, initialScreenAccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(initialScreenPath, false, initialScreenAccessRule);
		
		
		final Path basicData1Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_BasicData1);
		final AccessRule basicData1AccessRule = new BasicData01AccessRule();
		final AccessRuleForCreate basicData1AccessRuleForCreate = new BasicData01AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(basicData1Path, false, basicData1AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(basicData1Path, false, basicData1AccessRule);
		
		final Path basicData2Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_BasicData2);
		final AccessRule basicData2AccessRule = new BasicData02AccessRule();
		final AccessRuleForCreate basicData2AccessRuleForCreate = new BasicData02AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(basicData2Path, false, basicData2AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(basicData2Path, false, basicData2AccessRule);
		
		final Path SalesOrg1Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_SalesOrg1);
		final AccessRule SalesOrg1AccessRule = new SalesOrg01AccessRule();
		final AccessRuleForCreate SalesOrg1AccessRuleForCreate = new SalesOrg01AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(SalesOrg1Path, false, SalesOrg1AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(SalesOrg1Path, false, SalesOrg1AccessRule);
		
		final Path SalesOrg2Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_SalesOrg2);
		final AccessRule SalesOrg2AccessRule = new SalesOrg02AccessRule();
		final AccessRuleForCreate SalesOrg2AccessRuleForCreate = new SalesOrg02AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(SalesOrg2Path, false, SalesOrg2AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(SalesOrg2Path, false, SalesOrg2AccessRule);
		
		final Path salesGeneralPath = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_SalesGeneralPlant);
		final AccessRule salesGeneralAccessRule = new SalesGeneralAccessRule();
		final AccessRuleForCreate salesGeneralAccessRuleForCreate = new SalesGeneralAccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(salesGeneralPath, false, salesGeneralAccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(salesGeneralPath, false, salesGeneralAccessRule);
		
		final Path salesTextPath = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_SalesText);
		final AccessRule salesTextAccessRule = new SalesTextAccessRule();
		final AccessRuleForCreate salesTextAccessRuleForCreate = new SalesGeneralAccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(salesTextPath, false, salesTextAccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(salesTextPath, false, salesTextAccessRule);
		
		final Path purchasingPath = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_Purchasing);
		final AccessRule purchasingAccessRule = new PurchasingAccessRule();
		final AccessRuleForCreate purchasingAccessRuleForCreate = new PurchasingAccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(purchasingPath, false, purchasingAccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(purchasingPath, false, purchasingAccessRule);
		
		final Path purchaseOrderTextPath = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_PurchasingOrderText);
		final AccessRule purchaseOrderTextAccessRule = new PurchaseOrderTextAccessRule();
		final AccessRuleForCreate purchaseOrderTextAccessRuleForCreate = new PurchaseOrderTextAccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(purchaseOrderTextPath, false, purchaseOrderTextAccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(purchaseOrderTextPath, false, purchaseOrderTextAccessRule);
						
		final Path mrp1Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_Mrp1);
		final AccessRule mrp1AccessRule = new MRP01AccessRule();
		final AccessRuleForCreate mrp1AccessRuleForCreate = new MRP01AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(mrp1Path, false, mrp1AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(mrp1Path, false, mrp1AccessRule);
		
		final Path mrp2Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_Mrp2);
		final AccessRule mrp2AccessRule = new MRP02AccessRule();
		final AccessRuleForCreate mrp2AccessRuleForCreate = new MRP02AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(mrp2Path, false, mrp2AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(mrp2Path, false, mrp2AccessRule);
		
		final Path mrp3Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_Mrp3);
		final AccessRule mrp3AccessRule = new MRP03AccessRule();
		final AccessRuleForCreate mrp3AccessRuleForCreate = new MRP03AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(mrp3Path, false, mrp3AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(mrp3Path, false, mrp3AccessRule);
		
		final Path mrp4Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_Mrp4);
		final AccessRule mrp4AccessRule = new MRP04AccessRule();
		final AccessRuleForCreate mrp4AccessRuleForCreate = new MRP04AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(mrp4Path, false, mrp4AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(mrp4Path, false, mrp4AccessRule);
		
		final Path workSchedulingPath = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_WorkScheduling);
		final AccessRule workSchedulingAccessRule = new WorkSchedulingAccessRule();
		final AccessRuleForCreate workSchedulingAccessRuleForCreate = new WorkSchedulingAccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(workSchedulingPath, false, workSchedulingAccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(workSchedulingPath, false, workSchedulingAccessRule);
		
		final Path plantDataStorageLoc1Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_PlantDataStorageLocation1);
		final AccessRule plantDataStorageLoc1AccessRule = new PlantDataStorageLocation01AccessRule();
		final AccessRuleForCreate plantDataStorageLoc1AccessRuleForCreate = new PlantDataStorageLocation01AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(plantDataStorageLoc1Path, false, plantDataStorageLoc1AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(plantDataStorageLoc1Path, false, plantDataStorageLoc1AccessRule);
		
		final Path plantDataStorageLoc2Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_PlantDataStorageLocation2);
		final AccessRule plantDataStorageLoc2AccessRule = new PlantDataStorageLocation02AccessRule();
		final AccessRuleForCreate plantDataStorageLoc2AccessRuleForCreate = new PlantDataStorageLocation02AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(plantDataStorageLoc2Path, false, plantDataStorageLoc2AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(plantDataStorageLoc2Path, false, plantDataStorageLoc2AccessRule);
		
		final Path warehouseManagementPath = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_WarehouseManagement1);
		final AccessRule warehouseManagementAccessRule = new WarehouseManagement01AccessRule();
		final AccessRuleForCreate warehouseManagementAccessRuleForCreate = new WarehouseManagement01AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(warehouseManagementPath, false, warehouseManagementAccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(warehouseManagementPath, false, warehouseManagementAccessRule);
		
		final Path qualityManagementPath = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_QualityManagement);
		final AccessRule qualityManagementAccessRule = new QualityManagementAccessRule();
		final AccessRuleForCreate qualityManagementAccessRuleForCreate = new QualityManagementAccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(qualityManagementPath, false, qualityManagementAccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(qualityManagementPath, false, qualityManagementAccessRule);
		
		final Path accounting1Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_Accounting1);
		final AccessRule accounting1AccessRule = new Accounting01AccessRule();
		final AccessRuleForCreate accounting1AccessRuleForCreate = new Accounting01AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(accounting1Path, false, accounting1AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(accounting1Path, false, accounting1AccessRule);
		
		final Path costing1Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_Costing1);
		final AccessRule costing1AccessRule = new Costing01AccessRule();
		final AccessRuleForCreate costing1AccessRuleForCreate = new Costing01AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(costing1Path, false, costing1AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(costing1Path, false, costing1AccessRule);
		
		final Path costing2Path = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_Costing2);
		final AccessRule costing2AccessRule = new Costing02AccessRule();
		final AccessRuleForCreate costing2AccessRuleForCreate = new Costing02AccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(costing2Path, false, costing2AccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(costing2Path, false, costing2AccessRule);
				
		final Path additionalDescriptionsPath = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_AddDataDescriptions);
		final AccessRule additionalDescriptionsAccessRule = new AdditionalDescriptionsAccessRule();
		final AccessRuleForCreate additionalDescriptionsAccessRuleForCreate = new AdditionalDescriptionsAccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(additionalDescriptionsPath, false, additionalDescriptionsAccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(additionalDescriptionsPath, false, additionalDescriptionsAccessRule);
		
		final Path additionalUOMPath = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_AddDataUnitsOfMeasure);
		final AccessRule additionalUOMAccessRule = new AdditionalUOMAccessRule();
		final AccessRuleForCreate additionalUOMAccessRuleForCreate = new AdditionalUOMAccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(additionalUOMPath, false, additionalUOMAccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(additionalUOMPath, false, additionalUOMAccessRule);
		
		final Path classificationPath = Paths._Root_MaterialPlant.getPathInSchema().
				add(Paths._Root_MaterialPlant._Root_MaterialPlant_Classification);
		final AccessRule classificationAccessRule = new ClassificationAccessRule();
		final AccessRuleForCreate classificationAccessRuleForCreate = new ClassificationAccessRuleForCreate();
		context.setAccessRuleForCreateOnNodeAndAllDescendants(classificationPath, false, classificationAccessRuleForCreate);
		context.setAccessRuleOnNodeAndAllDescendants(classificationPath, false, classificationAccessRule);

	}

}
