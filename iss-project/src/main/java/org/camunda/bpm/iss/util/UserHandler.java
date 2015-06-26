package org.camunda.bpm.iss.util;

import java.util.Arrays;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.identity.User;


public class UserHandler {

	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	private TaskService taskService = processEngine.getTaskService();
	
	private IdentityService is = processEngine.getIdentityService();
	private List<User> users = is.createUserQuery().list();
	
	public List<String> findDifferentUser(String contractuser) {
		List<String> userList = Arrays.asList("mary", "peter", "demo");
		for(String user: userList) {
			if (user!=contractuser) {
				
			}
		}
		
	}
}
