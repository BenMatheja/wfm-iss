package org.camunda.bpm.iss.ejb;

import java.io.IOException;
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
import org.camunda.bpm.iss.entity.CustomerBill;


@Stateless
@Named
public class CustomerBillService {
	
	@PersistenceContext
	  private EntityManager entityManager;
	
	// Inject task form available through the camunda cdi artifact
		  @Inject
		  private TaskForm taskForm;
		  
		  private static Logger LOGGER = Logger.getLogger(CustomerBillService.class.getName());
			 
		  public void persistCustomerBill(DelegateExecution delegateExecution) {
		   
			LOGGER.log(Level.INFO, "Create new customer bill instance");  
			  // Create new customer instance
		    CustomerBill customerbillEntity = new CustomerBill();
		 
		    LOGGER.log(Level.INFO, "Get all process variables");
		    // Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();
		 
		    LOGGER.log(Level.INFO, "Set order attributes");
		    // Set order attributes
		    try{	    	    	
			    customerbillEntity.setCustomer(customerService.getCustomer((Long) variables.get("customerId")));
			    }catch(EJBException e){
			    	Throwable cause = e.getCause();
			    	LOGGER.log(Level.SEVERE, cause.getMessage());
			    }
		    try{	    	    	
		    	customerbillEntity.setProject(projectService.getProject((Long) variables.get("projectId")));
			    }catch(EJBException e){
			    	Throwable cause = e.getCause();
			    	LOGGER.log(Level.SEVERE, cause.getMessage());
			    }
		 
		    /*
		      Persist customer instance and flush. After the flush the
		      id of the customer instance is set.
		    */
		    LOGGER.log(Level.INFO, " Persist customer bill instance and flush.");
		    
		    entityManager.persist(customerbillEntity);
		    entityManager.flush();
		 
		    // Save the customer temporarily 
		    Object tempCust = variables.get("customerId");
		    Object tempProject = variables.get("projectId");
		    
		    // Remove no longer needed process variables
		    delegateExecution.removeVariables(variables.keySet());
		 
		    // Add newly created id as process variable
		    delegateExecution.setVariable("customerId", tempCust);
		    delegateExecution.setVariable("projectId", tempProject);
		    delegateExecution.setVariable("customerbillId", customerbillEntity.getCostumerBill());
		  }

		  public CustomerBill getCostumerBill(Long customerbillId) {
			  // Load entity from database
			  return entityManager.find(CustomerBill.class, customerbillId);
		  }
		  public void mergeAndComplete_Bill(CustomerBill customerBill) {
			  // Merge detached customerRequest entity with current persisted state
			 
			  entityManager.merge(customerBill);		  			
			  
			  try {
			      // Complete user task from
			      taskForm.completeTask();
			   } catch (IOException e) {
			      // Rollback both transactions on error
			      throw new RuntimeException("Cannot complete task", e);
			   }
		  }
}
