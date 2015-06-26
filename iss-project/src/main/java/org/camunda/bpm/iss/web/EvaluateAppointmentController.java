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
import org.camunda.bpm.iss.ejb.AppointmentService;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Appointment;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.Project;

@Named
@ConversationScoped
public class EvaluateAppointmentController implements Serializable{
//
//	  private static  final long serialVersionUID = 1L;
//	  
//	  
//	  private static Logger LOGGER = Logger.getLogger(EvaluateAppointmentController.class.getName());
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
//	  private CustomerService customerService;
//	  @Inject
//	  private AppointmentService appointmentService;
//	 
//	  // Caches the Entities during the conversation
//	  private Project projectEntity;
//	  private Customer customerEntity;
//	  private Appointment appointmentEntity;
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
//	  
//	  public Customer getCustomerEntity() {
//		    if (customerEntity == null) {
//		      // Load the entity from the database if not already cached
//		    LOGGER.log(Level.INFO, "This is customerId from businessProcess: " + businessProcess.getVariable("customerId")); 
//		    LOGGER.log(Level.INFO, "This is the same casted as Long: " + (Long) businessProcess.getVariable("customerId"));
//		    LOGGER.log(Level.INFO, "This is getCustomer from the Service invoked with it " + customerService.getCustomer((Long) businessProcess.getVariable("customerId")));
//		    customerEntity = customerService.getCustomer((Long) businessProcess.getVariable("customerId"));
//		    }
//		    return customerEntity;
//	  }
//	  
//	  public Appointment getAppointmentEntity() {
//		    if (appointmentEntity == null) {
//		      // Load the entity from the database if not already cached
//		      LOGGER.log(Level.INFO, "This is appointmentId from businessProcess: " + businessProcess.getVariable("appointmentId")); 
//			  LOGGER.log(Level.INFO, "This is the same casted as Long: " + (Long) businessProcess.getVariable("appointmentId"));
//			  LOGGER.log(Level.INFO, "This is getCustomer from the Service invoked with it " + appointmentService.getAppointment((Long) businessProcess.getVariable("appointmentId")));
//		      appointmentEntity = appointmentService.getAppointment((Long) businessProcess.getVariable("appointmentId"));
//		    }
//		    return appointmentEntity;
//		  }
}