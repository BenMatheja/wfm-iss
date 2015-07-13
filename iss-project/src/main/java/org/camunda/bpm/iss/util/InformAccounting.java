package org.camunda.bpm.iss.util;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Project;

import com.itextpdf.text.DocumentException;

@Stateless
@Named
public class InformAccounting {

	private static Logger LOGGER = Logger.getLogger(InformAccounting.class
			.getName());

	private ProcessEngine processEngine = ProcessEngines
			.getDefaultProcessEngine();

	private IdentityService is = processEngine.getIdentityService();
	private RuntimeService rs = processEngine.getRuntimeService();

	// Inject the Services
	@Inject
	private ProjectService projectService;
	
	@Inject
	private GenerateBill generateBill;

	// Local vars

	public void calcTime(DelegateExecution delegateExecution) throws DocumentException, IOException {
		LOGGER.info("This is calc Time!");
		
		// Local vars
		long pmHourlyRate = 20;
		long emHourlyRate = 12;
		long noOfPm = 1;

		Map<String, Object> variables = delegateExecution.getVariables();
		Project project = projectService.getProject((Long) variables
				.get("projectId"));

		Date startTime = project.getProjectStart();
		Date endTime = project.getProjectEnd();

		LOGGER.info("Start Time: " + startTime);
		LOGGER.info("End Time: " + endTime);

		long diff = endTime.getTime() - startTime.getTime();
		long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		// We assume a 42 hour-week
		long workingHours = days * 6;

		LOGGER.info("No. of days: " + days);
		LOGGER.info("Working Hours: " + workingHours);
		
		// Calc Project Manager Wage
		long pmWage = pmHourlyRate * noOfPm;
		
		// Calc Employee Wage
		LOGGER.info("Employee Size is: " + project.getEmployee().size());
		long emWage = project.getEmployee().size() * emHourlyRate;
		
		// Calc Project Costs
		long projectCosts = pmWage + emWage;
		
		delegateExecution.setVariable("projectCosts", projectCosts);
		LOGGER.log(Level.INFO, "This is projectCosts in the business process: "
				+ delegateExecution.getVariable("projectCosts"));
		
		/** 
		 * Here begins some further calc bc. we changed requiremtnts
		 */
		
		
		generateBill.main(delegateExecution);

	}
}
