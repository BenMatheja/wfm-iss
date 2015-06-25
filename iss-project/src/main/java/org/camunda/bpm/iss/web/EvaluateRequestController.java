package org.camunda.bpm.iss.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;
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
	 
	  // Inject the Service 
	  @Inject
	  private CustomerRequestService customerRequestService;
	  @Inject
	  private CustomerService customerService;
	 
	  // Caches the Entities during the conversation
	  private CustomerRequest customerRequestEntity;
	  private Customer customerEntity;
	  
	  private DelegateExecution delegateExecution;

	  
	  public CustomerRequest getCustomerRequestEntity() {
	    if (customerRequestEntity == null) {
	      // Load the entity from the database if not already cached
	      LOGGER.log(Level.INFO, "This is customerRequestId from businessProcess: " + businessProcess.getVariable("customerRequestId")); 
		  LOGGER.log(Level.INFO, "This is the same casted as Long: " + (Long) businessProcess.getVariable("customerRequestId"));
		  LOGGER.log(Level.INFO, "This is getCustomer from the Service invoked with it " + customerRequestService.getCustomerRequest((Long) businessProcess.getVariable("customerRequestId")));
	      customerRequestEntity = customerRequestService.getCustomerRequest((Long) businessProcess.getVariable("customerRequestId"));
	    }
	    return customerRequestEntity;
	  }
	  
	  
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
		  // Accept Request and forward
		  String newline = System.getProperty("line.separator");
		  
		  customerRequestEntity.setEvaluated(true);
		  String mailtext = newline + "your Request '"+customerRequestEntity.getTitle()+"' has been accepted." + newline + "We're looking forward to working with you." +newline+newline;
		  String subject = "Your request was accepted";
		  businessProcess.setVariable("mailtext", mailtext);
		  businessProcess.setVariable("subject", subject);
		  customerRequestService.mergeAndComplete(customerRequestEntity);
	  }
	
	  public void declineRequest() throws IOException{
		  //Decline Request and forward
		  String newline = System.getProperty("line.separator");
		  customerRequestEntity.setEvaluated(false);
		  String mailtext = newline + "your Request '"+customerRequestEntity.getTitle()+"' has been rejected. We are sorry." +newline+newline;
		  String subject = "Your request was declined";
		  businessProcess.setVariable("mailtext", mailtext);
		  businessProcess.setVariable("subject", subject);
		  customerRequestService.mergeAndComplete(customerRequestEntity);		    	
	  }
}
