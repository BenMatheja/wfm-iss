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
//	@PersistenceContext
//	  private EntityManager entityManager;
//	
//		  @Inject
//		  private AppointmentService appointmentService;
//		  
//		  private static Logger LOGGER = Logger.getLogger(MeetingMinutesService.class.getName());
//			 
//		  public MeetingMinutes create(MeetingMinutes meetingMinutes){
//			  entityManager.persist(meetingMinutes);
//			  return meetingMinutes;
//		  }
//		  
//		  public MeetingMinutes getMeetingMinutes(Long meetingMinutesId) {
//			  // Load entity from database
//			  return entityManager.find(MeetingMinutes.class, meetingMinutesId);
//		  }


}
