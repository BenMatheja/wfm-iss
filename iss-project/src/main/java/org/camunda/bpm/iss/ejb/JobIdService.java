package org.camunda.bpm.iss.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.iss.entity.JobId;

@Stateless
@Named
public class JobIdService {
	
		 @PersistenceContext
		  private EntityManager entityManager;
		 
		 public JobId getJobId(Long jobIdId) {
			  // Load entity from database
			  return entityManager.find(JobId.class, jobIdId);
		  }
		 
		 public JobId create (JobId jobId) {	   
				entityManager.persist(jobId);
				// entityManager.flush();
				return jobId;	 
			  }
		  
}
