package org.camunda.bpm.iss.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.iss.entity.Bill;

@Stateless
@Named
public class BillService {

	@PersistenceContext
	EntityManager em;

	public Bill create(Bill bill) {
		em.persist(bill);
		return bill;
	}

	public Bill getBill(long id) {
		return em.find(Bill.class, id);
	}
}	
