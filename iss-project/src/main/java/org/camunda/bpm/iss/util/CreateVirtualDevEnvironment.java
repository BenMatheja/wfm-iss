package org.camunda.bpm.iss.util;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.Project;

@Stateless
@Named
public class CreateVirtualDevEnvironment {

	 	private static Logger LOGGER = Logger.getLogger(CreateVirtualDevEnvironment.class.getName());
	 	
	 	@Inject
		  private ProjectService projectService;
	 	
	 	@Inject
		  private CustomerService customerService;
	
	 	public void create(DelegateExecution delegateExecution) {
	    	
	 		LOGGER.log(Level.INFO, "this is Create Virtual Environment"); 
	 		
	    	Map<String, Object> variables = delegateExecution.getVariables();
	    	Long projectId = (Long) variables.get("projectId");
	    	Long customerId = (Long) variables.get("customerId");
	    	
	    	Project project = projectService.getProject(projectId);
	    	Customer customer = customerService.getCustomer(customerId);
	    	
	    	File files = new File("C:\\Projects\\"+customer.getName()+"\\"+project.getTitle());
	    	if (files.exists()) {
	    		LOGGER.log(Level.INFO, "files.exists() was true"); 
	    		if (files.mkdirs()) {
	    			System.out.println("Directories are created!");
	    			 LOGGER.log(Level.INFO, "Directories created"); 
	    		} else {
	    			System.out.println("Failed to create directories!");
	    			LOGGER.log(Level.INFO, "Failed to create Directories"); 
	    		}
	    	}	 
	    }
	}

