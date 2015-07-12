package org.camunda.bpm.iss.ejb;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.camunda.bpm.iss.entity.Employee;
import org.camunda.bpm.iss.entity.MeetingMinutes;

@Stateless
@Named
public class MeetingMinutesService {
	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private AppointmentService appointmentService;

	private static Logger LOGGER = Logger.getLogger(MeetingMinutesService.class
			.getName());

	public MeetingMinutes create(MeetingMinutes meetingMinutes) {
		entityManager.persist(meetingMinutes);
		return meetingMinutes;
	}

	public MeetingMinutes getMeetingMinutes(Long meetingMinutesId) {
		// Load entity from database
		return entityManager.find(MeetingMinutes.class, meetingMinutesId);
	}

	public Collection<MeetingMinutes> getAllMeetingMinutes() {
		LOGGER.log(Level.INFO, "This is getAllEmployees");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		LOGGER.log(Level.INFO, "criteriaBuilder: " + cb.toString());
		CriteriaQuery<MeetingMinutes> cq = cb.createQuery(MeetingMinutes.class);
		Root<MeetingMinutes> rootEntry = cq.from(MeetingMinutes.class);
		return entityManager.createQuery(cq.select(rootEntry)).getResultList();
	}

}