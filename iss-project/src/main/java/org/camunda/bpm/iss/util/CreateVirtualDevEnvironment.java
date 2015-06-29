package org.camunda.bpm.iss.util;
import java.io.File;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;

public class CreateVirtualDevEnvironment {

	
	    public static void create(DelegateExecution delegateExecution) {
	    	
	    	Map<String, Object> variables = delegateExecution.getVariables();
	    	
	    	File files = new File("C:\\Projects\\"+variables.get("customerId")+"\\"+variables.get("projectId"));
	    	if (files.exists()) {
	    		if (files.mkdirs()) {
	    			System.out.println("Directories are created!");
	    		} else {
	    			System.out.println("Failed to create directories!");
	    		}
	    	}	 
	    }
	}

