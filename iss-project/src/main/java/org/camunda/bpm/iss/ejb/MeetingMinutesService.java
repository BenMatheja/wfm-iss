package org.camunda.bpm.iss.ejb;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.entity.MeetingMinutes;

@Stateless
@Named
public class MeetingMinutesService {
	@PersistenceContext
	  private EntityManager entityManager;
	
		  @Inject
		  private AppointmentService appointmentService;
		  
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
