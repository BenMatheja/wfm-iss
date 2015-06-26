package org.camunda.bpm.iss.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.entity.Customer;


@Named
@ConversationScoped
public class NegotiateContractController implements Serializable{

	  private static  final long serialVersionUID = 1L;
	  
	  
	  private static Logger LOGGER = Logger.getLogger(NegotiateContractController.class.getName());
	  // Inject the BusinessProcess to access the process variables
	  @Inject
	  private BusinessProcess businessProcess;
	 
	  @Inject
	  private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	  private IdentityService identityService = processEngine.getIdentityService();
	  
	  
	  @Inject
	  private TaskForm taskForm;
	  
	  // Inject the EntityManager to access the persisted order
	  @PersistenceContext
	  private EntityManager entityManager;
	 
	  // Inject the Service 
	  @Inject
	  private CustomerService customerService;
	 
	  // Caches the Entities during the conversation
	  private Customer customerEntity;
	  
	  public Customer getCustomerEntity() {
		    if (customerEntity == null) {
		      // Load the entity from the database if not already cached
		    LOGGER.log(Level.INFO, "This is customerId from businessProcess: " + businessProcess.getVariable("customerId")); 
		    LOGGER.log(Level.INFO, "This is the same casted as Long: " + (Long) businessProcess.getVariable("customerId"));
		    LOGGER.log(Level.INFO, "This is getCustomer from the Service invoked with it " + customerService.getCustomer((Long) businessProcess.getVariable("customerId")));
		    customerEntity = customerService.getCustomer((Long) businessProcess.getVariable("customerId"));
		    }
		    return customerEntity;
	  }	
	  
	  public void confirmRequest() throws IOException {
		  Authentication currentAuth = identityService.getCurrentAuthentication();
		  String contractUser = currentAuth.getUserId();
		  businessProcess.setVariable("contractuser", contractUser);
		  taskForm.completeTask();
	  }
}
