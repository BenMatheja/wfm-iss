package issproject;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import issproject.CustomerRequestLogic;
import issproject.CustomerRequestEntity;
import issproject.CustomerLogic;
import issproject.CustomerEntity;

@Named
@ConversationScoped
public class EvaluateRequestController implements Serializable{

	  private static  final long serialVersionUID = 1L;
	  
	  // Inject the BusinessProcess to access the process variables
	  @Inject
	  private BusinessProcess businessProcess;
	 
	  // Inject the EntityManager to access the persisted order
	  @PersistenceContext
	  private EntityManager entityManager;
	 
	  // Inject the Logic 
	  @Inject
	  private CustomerRequestLogic customerRequestLogic;
	  private CustomerLogic customerLogic;
	 
	  // Caches the Entities during the conversation
	  private CustomerRequestEntity customerRequestEntity;
	  private CustomerEntity customerEntity;
	 
	  public CustomerRequestEntity getCustomerRequestEntity() {
	    if (customerRequestEntity == null) {
	      // Load the entity from the database if not already cached
	      customerRequestEntity = customerRequestLogic.getCustomerRequest((Long) businessProcess.getVariable("customerRequestId"));
	    }
	    return customerRequestEntity;
	  }
	  
	  public CustomerEntity getCustomerEntity() {
		    if (customerEntity == null) {
		      // Load the entity from the database if not already cached
		      customerEntity = customerLogic.getCustomer((Long) businessProcess.getVariable("customerId"));
		    }
		    return customerEntity;
		  }
	 
	  public void submitForm() throws IOException {
	    // Do something awesome in here
	  }
	
}
