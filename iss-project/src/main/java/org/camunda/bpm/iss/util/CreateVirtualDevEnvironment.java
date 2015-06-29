package org.camunda.bpm.iss.util;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;

public class CreateVirtualDevEnvironment {

	 	private static Logger LOGGER = Logger.getLogger(CreateVirtualDevEnvironment.class.getName());
	
	    public static void create(DelegateExecution delegateExecution) {
	    	
	    	Map<String, Object> variables = delegateExecution.getVariables();
	    	String customerName = (String) variables.get("customerName");
	    	String projectTitle = (String) variables.get("projectTitle");
	    	
	    	File files = new File("C:\\Projects\\"+customerName+"\\"+projectTitle);
	    	if (files.exists()) {
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

