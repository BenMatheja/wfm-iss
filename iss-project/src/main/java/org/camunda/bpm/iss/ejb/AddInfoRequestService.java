package org.camunda.bpm.iss.ejb;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.iss.entity.AddInfoRequest;
import org.camunda.bpm.iss.entity.CustomerRequest;


@Stateless
@Named
public class AddInfoRequestService {

	@PersistenceContext
	EntityManager em;

	// Inject task form available through the camunda cdi artifact
	@Inject
	private TaskForm taskForm;

	public AddInfoRequest create(AddInfoRequest addInfoRequest) {
		em.persist(addInfoRequest);
		return addInfoRequest;
	}

	public AddInfoRequest getAddInfoRequest(long id) {
		return em.find(AddInfoRequest.class, id);
	}
	
	public String getQuestion(long id){
		AddInfoRequest addInfoRequest = getAddInfoRequest(id);
		return addInfoRequest.getQuestion();
	}
	
	public void mergeAndComplete(AddInfoRequest addInfoRequest) {
		// Merge detached customerRequest entity with current persisted state

		em.merge(addInfoRequest);

		try {
			// Complete user task from
			taskForm.completeTask();
		} catch (IOException e) {
			// Rollback both transactions on error
			throw new RuntimeException("Cannot complete task", e);
		}
	}
}
