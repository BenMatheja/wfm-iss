package org.camunda.bpm.iss.web;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;

@Named
@ConversationScoped
public class ConductPaymentController implements Serializable{

	  private static  final long serialVersionUID = 1L;
	  
	  
	  private static Logger LOGGER = Logger.getLogger(ConductPaymentController.class.getName());
	  // Inject the BusinessProcess to access the process variables
	  @Inject
	  private BusinessProcess businessProcess;
	 
	  // Inject the EntityManager to access the persisted order
	  @PersistenceContext
	  private EntityManager entityManager;
	 
	  // Inject the Service 
	  @Inject
	  private PBRequestService pBRequestService;
	  @Inject
	  private DesignRequestService designRequestService;

	 
	  // Caches the Entities during the conversation
	  private PBRequest pBRequestEntity;
	  private DesignRequest designRequestEntity;
	  
	  private DelegateExecution delegateExecution;

	  public PBRequest getPBRequestEntity() {
		    if (pBRequestEntity == null) {
		      // Load the entity from the database if not already cached
		      LOGGER.log(Level.INFO, "This is pBRequestId from businessProcess: " + businessProcess.getVariable("pBRequestId")); 
			  LOGGER.log(Level.INFO, "This is the same casted as Long: " + (Long) businessProcess.getVariable("pBRequestId"));
			  LOGGER.log(Level.INFO, "This is getCustomer from the Service invoked with it " + pBRequestService.getPBRequest((Long) businessProcess.getVariable("pBRequestId")));
		      pBRequestEntity = pBRequestService.getPBRequest((Long) businessProcess.getVariable("pBRequestId"));
		    }
		    return pBRequestEntity;
		  }
	  
	  public DesignRequest getDesignRequestEntity() {
		    if (designRequestEntity == null) {
		      // Load the entity from the database if not already cached
		      LOGGER.log(Level.INFO, "This is designRequestId from businessProcess: " + businessProcess.getVariable("designRequestId")); 
			  LOGGER.log(Level.INFO, "This is the same casted as Long: " + (Long) businessProcess.getVariable("designRequestId"));
			  LOGGER.log(Level.INFO, "This is getCustomer from the Service invoked with it " + designRequestService.getDesignRequest((Long) businessProcess.getVariable("designRequestId")));
		      designRequestEntity = designRequestService.getDesignRequest((Long) businessProcess.getVariable("designRequestId"));
		    }
		    return designRequestEntity;
		  }
}