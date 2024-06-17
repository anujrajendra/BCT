package com.bct.addon.dxch.trigger;


import com.bct.addon.dxch.path.CategoryConfigPath;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.orchestranetworks.instance.HomeCreationSpec;
import com.orchestranetworks.instance.HomeKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.trigger.AfterCreateOccurrenceContext;
import com.orchestranetworks.schema.trigger.TableTrigger;
import com.orchestranetworks.schema.trigger.TriggerSetupContext;
import com.orchestranetworks.service.OperationException;
import com.orchestranetworks.service.Profile;
import com.orchestranetworks.service.Session;

public class DSTrigger extends TableTrigger {

	@Override
	public void setup(TriggerSetupContext arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleAfterCreate(AfterCreateOccurrenceContext context) throws OperationException {
		
		Adaptation currentRecord = context.getAdaptationOccurrence();
		AdaptationHome home = currentRecord.getHome();
		
		Repository repo = home.getRepository();
		Session session = context.getSession();
		
		createChildDS(context, repo, session);
	}
	
	
     public AdaptationHome createChildDS(AfterCreateOccurrenceContext context, Repository repository, Session session) throws OperationException {
			
			
			ValueContext vcfu = context.getOccurrenceContext();
			 
			String category = (String) vcfu.getValue(CategoryConfigPath._CategoryConfigurations._CategoryID); 
			String year = (String) vcfu.getValue(CategoryConfigPath._CategoryConfigurations._Year);
			String period = (String) vcfu.getValue(CategoryConfigPath._CategoryConfigurations._Period);
			
			
			AdaptationHome parentDataspace = repository.lookupHome(HomeKey.forBranchName("Reference"));
			
			AdaptationHome childDataspace = repository.lookupHome(HomeKey.forBranchName(category));
			
			AdaptationHome childDataspace1 = repository.lookupHome(HomeKey.forBranchName(category + "_" + year));
		
			AdaptationHome childDataspace2 = repository.lookupHome(HomeKey.forBranchName(category + "_" + year + "_" + period));
			
		    if (childDataspace == null) {
		    	
		        HomeCreationSpec childSpec = new HomeCreationSpec(); 
		        childSpec.setParent(parentDataspace); 
		        childSpec.setOwner(Profile.EVERYONE);
		        childSpec.setKey(HomeKey.forBranchName(category));
		        childDataspace = repository.createHome(childSpec, session); 
		        
		        
                HomeCreationSpec childSpec1= new HomeCreationSpec(); 
		        childSpec1.setParent(childDataspace); 
		        childSpec1.setOwner(Profile.EVERYONE);
		        childSpec1.setKey(HomeKey.forBranchName(category + "_" + year));
		        childDataspace = repository.createHome(childSpec1, session);
		        
                HomeCreationSpec childSpec2= new HomeCreationSpec(); 
		        childSpec2.setParent(childDataspace1); 
		        childSpec2.setOwner(Profile.EVERYONE);
		        childSpec2.setKey(HomeKey.forBranchName(category + "_" + year + "_" + period));
		        childDataspace = repository.createHome(childSpec2, session); 
		        }
		    
		     else if  (childDataspace1 == null) {
		        	
		        HomeCreationSpec childSpec = new HomeCreationSpec();		    	 
		        childSpec.setParent(childDataspace); 
		        childSpec.setOwner(Profile.EVERYONE);
		        childSpec.setKey(HomeKey.forBranchName(category + "_" + year));
		        childDataspace = repository.createHome(childSpec, session);
		        
                HomeCreationSpec childSpec2= new HomeCreationSpec(); 		        
		        childSpec2.setParent(childDataspace1); 
		        childSpec2.setOwner(Profile.EVERYONE);
		        childSpec2.setKey(HomeKey.forBranchName(category + "_" + year + "_" + period));
		        childDataspace = repository.createHome(childSpec2, session);
		        
		        }
		    
		      else if  (childDataspace2 == null) {
		        	 
		        HomeCreationSpec childSpec = new HomeCreationSpec();		        
		        childSpec.setParent(childDataspace1); 
		        childSpec.setOwner(Profile.EVERYONE);
		        childSpec.setKey(HomeKey.forBranchName(category + "_" + year + "_" + period));
                childDataspace = repository.createHome(childSpec, session); 
                
                }
		    
		    return childDataspace;
		}
}

       
                	
                	
           
