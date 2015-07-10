package org.camunda.bpm.iss.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.iss.ejb.JobIdService;
import org.camunda.bpm.iss.entity.JobId;
import org.camunda.bpm.iss.entity.util.GlobalDefinitions;

@Stateless
@Named
public class JobIdHandler {

	@Inject
	JobIdService jobIdService;
	
	@Inject
	BusinessProcess businessProcess;
	
	private static Logger LOGGER = Logger
			.getLogger(JobIdHandler.class.getName());
	
	public void create() {
		LOGGER.log(Level.INFO, "This is jobIdHandler Create");
		JobId jobIdEntity = new JobId();
		
		jobIdEntity.setJobId(GlobalDefinitions.JOB_ID);
		jobIdEntity = jobIdService.create(jobIdEntity);

		businessProcess.setVariable("jobId", jobIdEntity.getJobId());
		LOGGER.log(Level.INFO, "This is jobId in the business process: "
				+ businessProcess.getVariable("jobId"));
	}
}
