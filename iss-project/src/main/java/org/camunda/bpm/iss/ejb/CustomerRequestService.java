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
import org.camunda.bpm.iss.entity.CustomerRequest;


@Stateless
@Named
public class CustomerRequestService {
	@Inject
	CustomerService customerService;
	
	 @PersistenceContext
	  private EntityManager entityManager;
	  
	  // Inject task form available through the camunda cdi artifact
	  @Inject
	  private TaskForm taskForm;
	  
	  private static Logger LOGGER = Logger.getLogger(CustomerRequestService.class.getName());
	 
	  public void persistCustomerRequest(DelegateExecution delegateExecution) {
	    // Create new instance
	    CustomerRequest customerRequestEntity = new CustomerRequest();
	 
	    // Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	 
	    // Set attributes
	    customerRequestEntity.setTitle((String) variables.get("title"));
	    customerRequestEntity.setText((String) variables.get("text"));
	    try{	    	    	
	    customerRequestEntity.setCustomer(customerService.getCustomer((Long) variables.get("customerId")));
	    }catch(EJBException e){
	    	Throwable cause = e.getCause();
	    	LOGGER.log(Level.SEVERE, cause.getMessage());
	    }
	    /*
	      Persist instance and flush. After the flush the
	      id of the instance is set.
	    */
	    entityManager.persist(customerRequestEntity);
	    entityManager.flush();
	 
	    // Save the customer temporarily 
	    Object tempCust = variables.get("customerId");
	    
	    // Remove no longer needed process variables
	    delegateExecution.removeVariables(variables.keySet());
	 
	    // Add newly created id as process variable
	    delegateExecution.setVariable("customerId", tempCust);
	    delegateExecution.setVariable("customerRequestId", customerRequestEntity.getId());
	  }


	  public CustomerRequest getCustomerRequest(Long customerRequestId) {
		  // Load entity from database
		  return entityManager.find(CustomerRequest.class, customerRequestId);
	  }
	  
	  public void mergeAndComplete(CustomerRequest customerRequest) {
		  // Merge detached customerRequest entity with current persisted state
		 
		  entityManager.merge(customerRequest);		  			
		  
		  try {
		      // Complete user task from
		      taskForm.completeTask();
		   } catch (IOException e) {
		      // Rollback both transactions on error
		      throw new RuntimeException("Cannot complete task", e);
		   }
	  }
}	