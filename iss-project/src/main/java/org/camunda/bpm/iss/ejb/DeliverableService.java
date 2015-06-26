package org.camunda.bpm.iss.ejb;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.CustomerBill;
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
	 
	  public void persistDeliverable(DelegateExecution delegateExecution) {
	   
		LOGGER.log(Level.INFO, "Create new customer instance");  
		  // Create new customer instance
	    Deliverable deliverableEntity = new Deliverable();
	 
	    LOGGER.log(Level.INFO, "Get all process variables");
	    // Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	 
	    LOGGER.log(Level.INFO, "Set order attributes");
	    // Set order attributes
	    deliverableEntity.setTitle((String) variables.get("title"));
	    deliverableEntity.setDescription((String) variables.get("description"));
	    	 
	    /*
	      Persist deliverable instance and flush. After the flush the
	      id of the customer instance is set.
	    */
	    LOGGER.log(Level.INFO, " Persist deliverable instance and flush.");
	    
	    entityManager.persist(deliverableEntity);
	    entityManager.flush();
	 
	    // Save the customer temporarily 
	    Object tempProject = variables.get("projectId");
	    
	    // Remove no longer needed process variables
	    delegateExecution.removeVariables(variables.keySet());
	 
	    // Add newly created id as process variable
	    delegateExecution.setVariable("projectId", tempProject);
	    delegateExecution.setVariable("deliverableId", deliverableEntity.getId());
	  }
	  
	  
	  public Deliverable getDeliverable(Long deliverableId) {
		  // Load entity from database
		  return entityManager.find(Deliverable.class, deliverableId);
	  }
	  
	  
}
