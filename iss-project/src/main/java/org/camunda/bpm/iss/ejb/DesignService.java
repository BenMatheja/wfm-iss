package org.camunda.bpm.iss.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.iss.entity.Design;

@Stateless
@Named
public class DesignService {
	
		@PersistenceContext
		EntityManager em;
		
		public Design create(Design design){
			em.persist(design);
			return design;
		}
		
		public Design getDesign(long id){
			return em.find(Design.class, id);
		}		
		
}


