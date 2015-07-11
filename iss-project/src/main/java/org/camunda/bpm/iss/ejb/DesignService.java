package org.camunda.bpm.iss.ejb;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.iss.entity.AddInfoRequest;
import org.camunda.bpm.iss.entity.Design;

@Stateless
@Named
public class DesignService {
	
		@PersistenceContext
		EntityManager em;
		
		@Inject
		private TaskForm taskForm;
		
		public Design create(Design design){
			em.persist(design);
			return design;
		}
		
		public Design getDesign(long id){
			return em.find(Design.class, id);
		}	
		
		public void mergeAndComplete(Design design) {
			// Merge detached customerRequest entity with current persisted state

			em.merge(design);

			try {
				// Complete user task from
				taskForm.completeTask();
			} catch (IOException e) {
				// Rollback both transactions on error
				throw new RuntimeException("Cannot complete task", e);
			}
		}
		
}


