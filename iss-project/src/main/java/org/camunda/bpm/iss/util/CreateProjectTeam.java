package org.camunda.bpm.iss.util;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.ejb.ContractService;


@Stateless
@Named
public class CreateProjectTeam {
	
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	private IdentityService is = processEngine.getIdentityService();
	private static Logger LOGGER = Logger.getLogger(ContractService.class.getName());
	
	public void create(DelegateExecution delegateExecution) {
		Map<String, Object> variables = delegateExecution.getVariables();
		Long projectId = (Long) variables.get("projectId");
		
		LOGGER.log(Level.INFO, "Group name should be: project-team" + projectId); 
		is.newGroup("project-team"+projectId);
		
		
	}
}
