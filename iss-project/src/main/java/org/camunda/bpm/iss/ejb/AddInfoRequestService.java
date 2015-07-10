package org.camunda.bpm.iss.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.iss.entity.AddInfoRequest;


@Stateless
@Named
public class AddInfoRequestService {

	@PersistenceContext
	EntityManager em;
	
	public AddInfoRequest create(AddInfoRequest addInfoRequest){
		em.persist(addInfoRequest);
		return addInfoRequest;
	}
	
	public AddInfoRequest getAddInfoRequest(long id){
		return em.find(AddInfoRequest.class, id);
	}
	
	public String getQuestion(long id){
		AddInfoRequest addInfoRequest = getAddInfoRequest(id);
		return addInfoRequest.getQuestion();
	}
}
