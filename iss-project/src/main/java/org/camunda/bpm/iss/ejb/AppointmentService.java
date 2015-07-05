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
import org.camunda.bpm.iss.entity.Appointment;
import org.camunda.bpm.iss.entity.Project;
import org.camunda.bpm.iss.ejb.MeetingMinutesService;


@Stateless
@Named
public class AppointmentService {
	
	
	@Inject
	ProjectService projectService;
	
	@PersistenceContext
	  private EntityManager entityManager;
	
	// Inject task form available through the camunda cdi artifact
		  @Inject
		  private TaskForm taskForm;
		  
	 private static Logger LOGGER = Logger.getLogger(AppointmentService.class.getName());
	 
	  public Appointment create (Appointment appointment) {	   
			entityManager.persist(appointment);
			// entityManager.flush();
			return appointment;	 
		  }
	  
	 
	  public Appointment getAppointment(Long appointmentId) {
		  // Load entity from database
		  return entityManager.find(Appointment.class, appointmentId);
	  }
	  
	  public void mergeAndComplete(Appointment appointment) {
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
