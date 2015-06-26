package org.camunda.bpm.iss.ejb;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
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

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.Appointment;
import org.camunda.bpm.iss.entity.CustomerRequest;
import org.camunda.bpm.iss.entity.Project;

@Stateless
@Named
public class AppointmentService {
	
	@Inject
	AppointmentService appointmentService;
	
	@PersistenceContext
	  private EntityManager entityManager;
	
	// Inject task form available through the camunda cdi artifact
		  @Inject
		  private TaskForm taskForm;
		  
	 private static Logger LOGGER = Logger.getLogger(AppointmentService.class.getName());
	 
	  public void persistAppointment(DelegateExecution delegateExecution) {
		  
		  
		  	LOGGER.log(Level.INFO, "Create new appointment instance");  
		    // Create new instance
		    Appointment appointmentEntity = new Appointment();
		 
		    LOGGER.log(Level.INFO, "Get all process variables");
		    // Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();
		    
		    LOGGER.log(Level.INFO, "Set appointment attributes");
		 // Set attributes
		    appointmentEntity.setTitle((String) variables.get("title"));
		    
		    try{	    	    	
		    	appointmentEntity.setCustomer(customerService.getCustomer((Long) variables.get("name")));
		    }catch(EJBException e){
		    	Throwable cause = e.getCause();
		    	LOGGER.log(Level.SEVERE, cause.getMessage());
		    }
		    
		    try{	    	    	
		    	appointmentEntity.setProjectTitle(projectService.getProject((Long) variables.get("projectId")));
		    }catch(EJBException e){
		    	Throwable cause = e.getCause();
		    	LOGGER.log(Level.SEVERE, cause.getMessage());
		    }
		    
		    /*
		      Persist instance and flush. After the flush the
		      id of the instance is set.
		    */
		    entityManager.persist(appointmentEntity);
		    entityManager.flush();
		    
		    // Save the customer temporarily 
		    Object tempCust = variables.get("customerName");
		    Object tempProject = variables.get("projectId");
		    
		 // Remove no longer needed process variables
		    delegateExecution.removeVariables(variables.keySet());
		    
		    // Add newly created id as process variable
		    delegateExecution.setVariable("customerName", tempCust);
		    delegateExecution.setVariable("projectId", tempProject);
		    delegateExecution.setVariable("appointmentId", appointmentEntity.getId());
		  }
	  
	 
	  public Appointment getAppointment(Long appointmentId) {
		  // Load entity from database
		  return entityManager.find(Appointment.class, appointmentId);
	  }
	  
	  public void mergeAndComplete_App(Appointment appointment) {
		  // Merge detached appointment entity with current persisted state
		 
		  entityManager.merge(appointment);		  			
		  
		  try {
		      // Complete user task from
		      taskForm.completeTask();
		   } catch (IOException e) {
		      // Rollback both transactions on error
		      throw new RuntimeException("Cannot complete task", e);
		   }
	  }

}
