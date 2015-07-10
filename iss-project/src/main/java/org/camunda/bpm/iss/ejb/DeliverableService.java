package org.camunda.bpm.iss.ejb;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.iss.entity.Deliverable;

@Stateless
@Named
public class DeliverableService {
	@PersistenceContext
	  private EntityManager entityManager;
	  
	  // Inject task form available through the camunda cdi artifact
	  @Inject
	  private TaskForm taskForm;	  
	  	  
	  private static Logger LOGGER = Logger.getLogger(DeliverableService.class.getName());
	  
	  public Deliverable persist(Deliverable deliverable){
		  entityManager.persist(deliverable);
		  return deliverable;
	  }
	  
	  public Deliverable getDeliverable(Long deliverableId) {
		  // Load entity from database
		  return entityManager.find(Deliverable.class, deliverableId);
	  }
	  
	  
}
