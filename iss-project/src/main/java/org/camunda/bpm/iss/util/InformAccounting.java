package org.camunda.bpm.iss.util;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Project;

@Stateless
@Named
public class InformAccounting {
	
	private static Logger LOGGER = Logger.getLogger(InformAccounting.class.getName());

	// Inject the Services
	@Inject
	private ProjectService projectService;
		
	//Local vars
		

	public void calcTime(DelegateExecution delegateExecution) {
		LOGGER.info("This is calc Time!");
		
		Map<String, Object> variables = delegateExecution.getVariables();
    	Project project = projectService.getProject((Long) variables.get("projectId"));
    	
    	Date startTime = project.getProjectStart();
    	Date endTime = project.getProjectEnd();
    	
    	LOGGER.info("Start Time: "+ startTime);
    	LOGGER.info("End Time: "+ endTime);
    	
    	long diff = endTime.getTime() - startTime.getTime();
    	long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    	
    	// We assume a 42 hour-week
    	long workingHours = days * 6;
    	
    	LOGGER.info("No. of days: " + days);
    	LOGGER.info("Working Hours: " + workingHours);
	}
}
