package org.camunda.bpm.iss.util;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.iss.ejb.ContractService;

@Stateless
@Named
public class UserHandler {

	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	private IdentityService is = processEngine.getIdentityService();
	
	private static Logger LOGGER = Logger.getLogger(ContractService.class.getName());
	
	/**
	 * Returns a LinkedList containing the first names of users which excludes the
	 * user with the corresponding <code>negId</code>.
	 * @author Shabdiz Ghasemivand 
	 * @param negId
	 * @return 
	 * @throws EmptyCandidateUserException 
	 */
	public String findDifferentUser(String negId, String groupId) throws EmptyCandidateUserException {
		List<User> users = is.createUserQuery().memberOfGroup(groupId).list();		
		User userObj = is.createUserQuery().userId(negId).list().get(0);
		if (users.contains(userObj)){
			users.remove(userObj);
		}
		List<String> userRes = new LinkedList<String>();
		for(User u:users){
			userRes.add(u.getId());
		}
		LOGGER.log(Level.INFO, "Different Users are: "+ userRes.toString());  
		if (userRes.isEmpty()) throw new EmptyCandidateUserException("Candidate User List is empty");
		else return userRes.get(0);
	}
}
