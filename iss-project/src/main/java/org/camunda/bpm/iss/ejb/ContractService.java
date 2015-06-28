package org.camunda.bpm.iss.ejb;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.entity.Contract;
import org.camunda.bpm.iss.entity.CustomerRequest;

@Stateless
@Named
public class ContractService {

	@PersistenceContext
	  private EntityManager entityManager;
	  
	  // Inject task form available through the camunda cdi artifact
	  @Inject
	  private TaskForm taskForm;	  
	  	  
	  private static Logger LOGGER = Logger.getLogger(ContractService.class.getName());
	  
	  // Inject the BusinessProcess to access the process variables
	  @Inject
	  private BusinessProcess businessProcess;
	  
	  // Caches the Entities during the conversation
	  private Contract contractEntity;
	 
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
	 
	    LOGGER.log(Level.INFO, "Add newly created contract id as process variable. It is:" + contractEntity.getId());
	    // Add newly created customer id as process variable
	    delegateExecution.setVariable("contractId", contractEntity.getId());
	  }

	  public Contract getContract(Long contractId) {
		  // Load entity from database
		  return entityManager.find(Contract.class, contractId);
	  }
	  
	  public void updateContract(Long contractId) throws IOException {
		  Contract contract = getContract(contractId);		 
		  String contractTitle = businessProcess.getVariable("contractTitle");
		  String contractDescription = businessProcess.getVariable("contractDescription");
		  contract.setContractTitle(contractTitle);
		  contract.setContractDescription(contractDescription);
		  LOGGER.log(Level.INFO, "This is updateContract setting the contract to: "+contract.toString());
		  LOGGER.log(Level.INFO, "The contract title is now: "+contract.getContractTitle());
		  LOGGER.log(Level.INFO, "The contract description is now: "+contract.getContractDescription());
		  entityManager.merge(contract);
	  } 
	  
	  	
}
