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
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.Project;

@Named
@ConversationScoped
public class RequestAdditionalInformationController implements Serializable{
//
//	  private static  final long serialVersionUID = 1L;
//	  
//	  
//	  private static Logger LOGGER = Logger.getLogger(RequestAdditionalInformationController.class.getName());
//	  // Inject the BusinessProcess to access the process variables
//	  @Inject
//	  private BusinessProcess businessProcess;
//	 
//	  // Inject the EntityManager to access the persisted order
//	  @PersistenceContext
//	  private EntityManager entityManager;
//	 
//	  // Inject the Service 
//	  @Inject
//	  private ProjectService projectService;
//	  @Inject
//	  private CustomerService designRequestService;
//
//	 
//	  // Caches the Entities during the conversation
//	  private Project projectEntity;
//	  private Customer designRequestEntity;
//	  
//	  private DelegateExecution delegateExecution;
//
//	  
//	  public Project getProjectEntity() {
//	    if (projectEntity == null) {
//	      // Load the entity from the database if not already cached
//	      LOGGER.log(Level.INFO, "This is projectId from businessProcess: " + businessProcess.getVariable("projectId")); 
//		  LOGGER.log(Level.INFO, "This is the same casted as Long: " + (Long) businessProcess.getVariable("projectId"));
//		  LOGGER.log(Level.INFO, "This is getProject from the Service invoked with it " + projectService.getProject((Long) businessProcess.getVariable("projectId")));
//	      projectEntity = projectService.getProject((Long) businessProcess.getVariable("projectId"));
//	    }
//	    return projectEntity;
//	  }
//	  
//	  public DesignRequest getDesignRequestEntity() {
//		    if (designRequestEntity == null) {
//		      // Load the entity from the database if not already cached
//		      LOGGER.log(Level.INFO, "This is designRequestId from businessProcess: " + businessProcess.getVariable("designRequestId")); 
//			  LOGGER.log(Level.INFO, "This is the same casted as Long: " + (Long) businessProcess.getVariable("designRequestId"));
//			  LOGGER.log(Level.INFO, "This is getCustomer from the Service invoked with it " + designRequestService.getDesignRequest((Long) businessProcess.getVariable("designRequestId")));
//		      designRequestEntity = designRequestService.getDesignRequest((Long) businessProcess.getVariable("designRequestId"));
//		    }
//		    return designRequestEntity;
//		  }
}