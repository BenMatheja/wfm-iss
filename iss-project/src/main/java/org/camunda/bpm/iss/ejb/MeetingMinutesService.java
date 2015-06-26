package org.camunda.bpm.iss.ejb;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.entity.Employee;
import org.camunda.bpm.iss.entity.MeetingMinutes;

@Stateless
@Named
public class MeetingMinutesService {
	@PersistenceContext
	  private EntityManager entityManager;
	
	// Inject task form available through the camunda cdi artifact
		  @Inject
		  private TaskForm taskForm;
		  
		  private static Logger LOGGER = Logger.getLogger(MeetingMinutesService.class.getName());
			 
		  public void persistMeetingMinutes(DelegateExecution delegateExecution) {
		   
			LOGGER.log(Level.INFO, "Create new meeting minutes instance");  
			  // Create new customer instance
		    MeetingMinutes meetingMinutesEntity = new MeetingMinutes();
		 
		    LOGGER.log(Level.INFO, "Get all process variables");
		    // Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();
		 
		    LOGGER.log(Level.INFO, "Set order attributes");
		    // Set order attributes
		    try{	    	    	
		    	meetingMinutesEntity.setAppointment(appointmentService.getAppointment((Long) variables.get("appointmentId")));
			    }catch(EJBException e){
			    	Throwable cause = e.getCause();
			    	LOGGER.log(Level.SEVERE, cause.getMessage());
			    }
		 
		    /*
		      Persist meeting minutes instance and flush. After the flush the
		      id of the meeting minutes instance is set.
		    */
		    LOGGER.log(Level.INFO, " Persist meeting minutes instance and flush.");
		    
		    entityManager.persist(meetingMinutesEntity);
		    entityManager.flush();
		    
		 // Save the customer temporarily 
		    Object tempAppointment = variables.get("appointmentId");
		    
		    // Remove no longer needed process variables
		    delegateExecution.removeVariables(variables.keySet());
		 
		    // Add newly created id as process variable
		   	delegateExecution.setVariable("meetingMinutesId", meetingMinutesEntity.getMeetingMinutes());
		  }
		  
		  public MeetingMinutes getMeetingMinutes(Long meetingMinutesId) {
			  // Load entity from database
			  return entityManager.find(MeetingMinutes.class, meetingMinutesId);
		  }


}
