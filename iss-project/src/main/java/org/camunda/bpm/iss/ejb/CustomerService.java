package org.camunda.bpm.iss.ejb;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.entity.Customer;

@Stateless
@Named
public class CustomerService {

	 @PersistenceContext
	  private EntityManager entityManager;
	  
	  // Inject task form available through the camunda cdi artifact
	  @Inject
	  private TaskForm taskForm;
	  
	  private static Logger LOGGER = Logger.getLogger(CustomerService.class.getName());
	 
	  public void persistCustomer(DelegateExecution delegateExecution) {
	   
		LOGGER.log(Level.INFO, "Create new customer instance");  
		  // Create new customer instance
	    Customer customerEntity = new Customer();
	 
	    LOGGER.log(Level.INFO, "Get all process variables");
	    // Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	 
	    LOGGER.log(Level.INFO, "Set order attributes");
	    // Set order attributes
	    customerEntity.setName((String) variables.get("name"));
	    customerEntity.setAddress((String) variables.get("address"));
	    customerEntity.setEMail((String) variables.get("eMail"));
	 
	    /*
	      Persist customer instance and flush. After the flush the
	      id of the customer instance is set.
	    */
	    LOGGER.log(Level.INFO, " Persist customer instance and flush.");
	    
	    entityManager.persist(customerEntity);
	    entityManager.flush();
	 
	    // Remove no longer needed process variables
	    // delegateExecution.removeVariables(variables.keySet());
	 
	    LOGGER.log(Level.INFO, "Add newly created customer id as process variable");
	    // Add newly created customer id as process variable
	    delegateExecution.setVariable("customerId", customerEntity.getId());
	  }

	  public Customer getCustomer(Long customerId) {
		  // Load entity from database
		  return entityManager.find(Customer.class, customerId);
	  }
}