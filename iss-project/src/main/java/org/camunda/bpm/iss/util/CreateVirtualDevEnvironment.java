package org.camunda.bpm.iss.util;
import java.io.File;

public class CreateVirtualDevEnvironment {
		
	    public static void create(String projectTitle, String customerName) {	
	    	File files = new File("C:\\"+projectTitle+"\\"+customerName);
	    	if (files.exists()) {
	    		if (files.mkdirs()) {
	    			System.out.println("Directories are created!");
	    		} else {
	    			System.out.println("Failed to create directories!");
	    		}
	    	}	 
	    }
	}

