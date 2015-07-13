package org.camunda.bpm.iss.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.iss.entity.BillIss;

@Stateless
@Named
public class BillIssService {

	@PersistenceContext
	EntityManager em;

	public BillIss create(BillIss bill) {
		em.persist(bill);
		return bill;
	}

	public BillIss getBill(long id) {
		return em.find(BillIss.class, id);
	}
}	
