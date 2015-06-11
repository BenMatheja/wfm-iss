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

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.iss.ejb.CustomerRequestService;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.CustomerRequest;

@Named
@ConversationScoped
public class EvaluateRequestController implements Serializable{

	  private static  final long serialVersionUID = 1L;
	  
	  
	  private static Logger LOGGER = Logger.getLogger(EvaluateRequestController.class.getName());
	  // Inject the BusinessProcess to access the process variables
	  @Inject
	  private BusinessProcess businessProcess;
	 
	  // Inject the EntityManager to access the persisted order
	  @PersistenceContext
	  private EntityManager entityManager;
	 
	  // Inject the Logic 
	  @Inject
	  private CustomerRequestService customerRequestLogic;
	  private CustomerService customerLogic;
	 
	  // Caches the Entities during the conversation
	  private CustomerRequest customerRequestEntity;
	  private Customer customerEntity;
	 
	  /**
	  public CustomerRequest getCustomerRequestEntity() {
	    if (customerRequestEntity == null) {
	      // Load the entity from the database if not already cached
	      customerRequestEntity = customerRequestLogic.getCustomerRequest((Long) businessProcess.getVariable("customerRequestId"));
	    }
	    return customerRequestEntity;
	  }
	  **/
	  
	  public Customer getCustomerEntity() {
		    if (customerEntity == null) {
		      // Load the entity from the database if not already cached
		    Long a = (Long) businessProcess.getVariable("customerId");
		    LOGGER.log(Level.INFO, "This is Customer ID Long: " + a.toString()); 
		      customerEntity = customerLogic.getCustomer(a);
		    }
		    return customerEntity;
		  }
	 
	  public void submitForm() throws IOException {
	    // Do something awesome in here
	  }
	
}
