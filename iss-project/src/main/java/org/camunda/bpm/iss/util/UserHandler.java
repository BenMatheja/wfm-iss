package org.camunda.bpm.iss.util;

import java.util.LinkedList;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.identity.User;


public class UserHandler {

	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	private IdentityService is = processEngine.getIdentityService();
	
	/**
	 * Returns a LinkedList containing the first names of users which excludes the
	 * user with the corresponding <code>negId</code>.
	 * @author Shabdiz Ghasemivand 
	 * @param negId
	 * @return 
	 * @throws EmptyCandidateUserException 
	 */
	public LinkedList<String> findDifferentUser(String negId) throws EmptyCandidateUserException {
		List<User> users = is.createUserQuery().list();
		User userObj = is.createUserQuery().userId(negId).list().get(0);
		if (users.contains(userObj)){
			users.remove(userObj);
		}
		LinkedList<String> userRes = new LinkedList<String>();
		for(User u:users){
			userRes.add(u.getFirstName());
		}
		if (userRes.isEmpty()) throw new EmptyCandidateUserException("Candidate User List is empty");
		else return userRes;
	}
}
