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
public class ContractService {

	@PersistenceContext
	  private EntityManager entityManager;
	  
	  // Inject task form available through the camunda cdi artifact
	  @Inject
	  private TaskForm taskForm;	  
	  	  
	  private static Logger LOGGER = Logger.getLogger(ContractService.class.getName());
	 
	  public void persistContract(DelegateExecution delegateExecution) {
	   
		LOGGER.log(Level.INFO, "Create new contract instance");  
		  // Create new contract instance
	    Contract contractEntity = new Contract();
	 
	    LOGGER.log(Level.INFO, "Get all process variables");
	    // Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	 
	    LOGGER.log(Level.INFO, "Set order attributes");
	    // Set order attributes
	    contractEntity.setContractTitle((String) variables.get("contractTitle"));
	    contractEntity.setContractDescription((String) variables.get("contractDescription"));

	    /*
	      Persist customer instance and flush. After the flush the
	      id of the customer instance is set.
	    */
	    LOGGER.log(Level.INFO, " Persist contract instance and flush.");
	    
	    entityManager.persist(contractEntity);
	    entityManager.flush();
	 
	    // Remove no longer needed process variables
	    // delegateExecution.removeVariables(variables.keySet());
	 
	    LOGGER.log(Level.INFO, "Add newly created customer id as process variable. It is:" + customerEntity.getId());
	    // Add newly created customer id as process variable
	    delegateExecution.setVariable("customerId", customerEntity.getId());
	  }

	  public Customer getCustomer(Long customerId) {
		  // Load entity from database
		  return entityManager.find(Customer.class, customerId);
	  }
	  	
}
