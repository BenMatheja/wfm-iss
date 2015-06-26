package org.camunda.bpm.iss.util;

import java.util.Arrays;
import java.util.List;

import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.*;

private TaskService taskService;



public class UserHandler {

	public List<String> findDifferentUser(String contractuser) {
		List<String> userList = Arrays.asList("mary", "peter", "demo");
		for(String user: userList) {
			if (user!=contractuser) {
				
			}
		}
		
	}
}
