package org.camunda.bpm.iss.ejb;

import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.CustomerRequest;

@Stateless
@Named
public class CustomerRequestService {

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
	    customerRequestEntity.setCustomer((Customer) variables.get("customerId"));
	 
	    /*
	      Persist instance and flush. After the flush the
	      id of the instance is set.
	    */
	    entityManager.persist(customerRequestEntity);
	    entityManager.flush();
	 
	    // Remove no longer needed process variables
	    delegateExecution.removeVariables(variables.keySet());
	 
	    // Add newly created id as process variable
	    delegateExecution.setVariable("customerRequestId", customerRequestEntity.getId());
	  }


	  public CustomerRequest getCustomerRequest(long customerRequestId) {
		  // Load entity from database
		  return entityManager.find(CustomerRequest.class, customerRequestId);
	  }
}	