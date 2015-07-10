package org.camunda.bpm.iss.ejb;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.iss.entity.StatusUpdate;

@Stateless
@Named
public class StatusUpdateService {

	@PersistenceContext
	EntityManager em;
	
	public StatusUpdate create(StatusUpdate statusUpdate){
		em.persist(statusUpdate);
		return statusUpdate;
	}
	
	public StatusUpdate getStatusUpdate(long id){
		return em.find(StatusUpdate.class, id);
	}
	
	public Date getDate(long id){
		StatusUpdate su = getStatusUpdate(id);
		return su.getDate();
	}
	
	public String getMessage(long id){
		StatusUpdate su = getStatusUpdate(id);
		return su.getMessage();
	}
}
