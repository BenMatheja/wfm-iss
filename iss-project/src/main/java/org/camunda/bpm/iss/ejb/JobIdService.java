package org.camunda.bpm.iss.ejb;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.iss.api.mock.SendDesignRequirements;
import org.camunda.bpm.iss.entity.JobId;

@Stateless
@Named
public class JobIdService {
	
		 @PersistenceContext
		  private EntityManager entityManager;
		 
		 private static Logger LOGGER = Logger
					.getLogger(SendDesignRequirements.class.getName());
		 
		 public JobId getJobId(Long jobIdId) {
			  // Load entity from database
			  return entityManager.find(JobId.class, jobIdId);
		  }
		 
		 public JobId create (JobId jobId) {	   
				entityManager.persist(jobId);
				// entityManager.flush();
				LOGGER.info("Created the JobId with Id: " + jobId.getId() + "and jobId: "+ jobId.getJobId());
				return jobId;	 
			  }
		  
}
