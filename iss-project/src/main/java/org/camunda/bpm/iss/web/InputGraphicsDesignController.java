package org.camunda.bpm.iss.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.iss.DTO.in.DesignRequestDTO;
import org.camunda.bpm.iss.api.mock.SendDesignRequirements;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.Project;
import org.camunda.bpm.iss.entity.util.GlobalDefinitions;
import org.codehaus.jackson.map.ObjectMapper;

@Named
@ConversationScoped
public class InputGraphicsDesignController implements Serializable{

	  private static  final long serialVersionUID = 1L;
	  
	  
	  private static Logger LOGGER = Logger.getLogger(InputGraphicsDesignController.class.getName());
	  // Inject the BusinessProcess to access the process variables
	  @Inject
	  private BusinessProcess businessProcess;
	 
	  // Inject the EntityManager to access the persisted order
	  @PersistenceContext
	  private EntityManager entityManager;
	 
	  // Inject the Service 
	  @Inject
	  private ProjectService projectService;
	  @Inject
	  private CustomerService customerService;
	  
	  
	  @Inject
	  private TaskForm taskForm;
	  
	  // Caches the Entities during the conversation
	  private Project projectEntity;
	  private Customer customerEntity;
	  private DesignRequestDTO designRequestDTO = new DesignRequestDTO();
	  
	  
	  
	  public DesignRequestDTO getDesignRequestDTO() {
		return designRequestDTO;
	}
	

	public Customer getCustomerEntity() {
	    if (customerEntity == null) {
	      // Load the entity from the database if not already cached
	    LOGGER.log(Level.INFO, "This is customerId from businessProcess: " + businessProcess.getVariable("customerId")); 
	    LOGGER.log(Level.INFO, "This is the same casted as Long: " + (Long) businessProcess.getVariable("customerId"));
	    LOGGER.log(Level.INFO, "This is getCustomer from the Service invoked with it " + customerService.getCustomer((Long) businessProcess.getVariable("customerId")));
	    customerEntity = customerService.getCustomer((Long) businessProcess.getVariable("customerId"));
	    }
	    return customerEntity;
  }
	
	public Project getProjectEntity() {
	    if (projectEntity == null) {
	      // Load the entity from the database if not already cached
	      LOGGER.log(Level.INFO, "This is projectId from businessProcess: " + businessProcess.getVariable("projectId")); 
		  LOGGER.log(Level.INFO, "This is the same casted as Long: " + (Long) businessProcess.getVariable("projectId"));
		  LOGGER.log(Level.INFO, "This is getProject from the Service invoked with it " + projectService.getProject((Long) businessProcess.getVariable("projectId")));
	      projectEntity = projectService.getProject((Long) businessProcess.getVariable("projectId"));
	    }
	    return projectEntity;
	  }
	  
	// Actually, here the designRequest has to be persisted as new object (mybe leave it due to time?
	  public void submit() throws IOException {	
		
		// specify the REST web service to interact with
		String baseUrl = GlobalDefinitions.getPbBaseURL();
		String relativeUrl = GlobalDefinitions.URL_API_PB_RECEIVE_DESIGN_REQUEST;
		String url = baseUrl + relativeUrl;

		 String jsonToSend = null;
	        try {    
	            //Instantiate JSON mapper
	            ObjectMapper mapper = new ObjectMapper();
	            //Log request dto
	            LOGGER.info("Sent DTO: " + mapper.writeValueAsString(designRequestDTO));
	           
	            //Parse to json
	            jsonToSend = mapper.writeValueAsString(designRequestDTO);
	        } catch (Exception e){
	        	e.printStackTrace();
	        	Response.serverError().build();
	        }    
		
		// Send
		Runnable sendDesignRequirements = new SendDesignRequirements(
				jsonToSend, url, 0, "Create Send Thread for designRequest");
		new Thread(sendDesignRequirements).start();
		  
		  /**
		  designRequest = designRequestService.create(designRequest);
		  businessProcess.setVariable("designRequestId", designRequest.getId());
		  
		  LOGGER.log(Level.INFO, "This is designRequestId in the business process: " + businessProcess.getVariable("designRequestId"));
		  
		  DesignRequest persistedDesignRequest = designRequestService.getDesignRequest(designRequest.getId());
		  LOGGER.log(Level.INFO, "This is the persisted DesingRequest: ");
		  LOGGER.log(Level.INFO, " Title: "+ persistedDesignRequest.getTitle());
		  LOGGER.log(Level.INFO, " Description: "+ persistedDesignRequest.getDescription());
			**/
		  
		  taskForm.completeTask();
	  }

}