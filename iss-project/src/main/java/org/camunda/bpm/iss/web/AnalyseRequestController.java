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
import org.camunda.bpm.iss.DTO.in.AddInfoResponseDTO;
import org.camunda.bpm.iss.api.mock.SendThread;
import org.camunda.bpm.iss.ejb.AddInfoRequestService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.AddInfoRequest;
import org.camunda.bpm.iss.entity.Project;
import org.camunda.bpm.iss.entity.util.GlobalDefinitions;
import org.codehaus.jackson.map.ObjectMapper;

@Named
@ConversationScoped
public class AnalyseRequestController implements Serializable{

	  private static  final long serialVersionUID = 1L;
	  
	  
	  private static Logger LOGGER = Logger.getLogger(AnalyseRequestController.class.getName());
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
	  private AddInfoRequestService addInfoRequestService;
	  @Inject 
	  private TaskForm taskForm;
	 
	  // Caches the Entities during the conversation
	  private Project projectEntity;
	  private AddInfoRequest addInfoRequestEntity;
	  private AddInfoResponseDTO addInfoResp = new AddInfoResponseDTO();
	  private String designTitle;
	 
	  
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
	  
	 public AddInfoRequest getAddInfoRequestEntity() {
		    if (addInfoRequestEntity == null) {
		      // Load the entity from the database if not already cached
		      LOGGER.log(Level.INFO, "This is addInfoRequestId from businessProcess: " + businessProcess.getVariable("addInfoRequestId")); 
			  LOGGER.log(Level.INFO, "This is getAddInfoRequest from the Service invoked with it " + addInfoRequestService.getAddInfoRequest((Long) businessProcess.getVariable("addInfoRequestId")));
		      addInfoRequestEntity = addInfoRequestService.getAddInfoRequest((Long) businessProcess.getVariable("addInfoRequestId"));
		    }
		    return addInfoRequestEntity;
		  }	 
	  
	  public String getDesignTitle() {
			return businessProcess.getVariable("designRequestTitle");
		}
	  
	  
	  
	  public AddInfoResponseDTO getAddInfoResp() {
		return addInfoResp;
	}

	public void setAddInfoResp(AddInfoResponseDTO addInfoResp) {
		this.addInfoResp = addInfoResp;
	}

	public void response() throws IOException {
		  // respond to PB
		  addInfoRequestEntity.setAvailable(true);
		  addInfoRequestService.mergeAndComplete(addInfoRequestEntity);
		  
		// specify the REST web service to interact with
			String baseUrl = GlobalDefinitions.getPbBaseURL();
			String relativeUrl = GlobalDefinitions.URL_API_PB_RECEIVE_ADDITIONAL_INFORMATION_RESPONSE;
			String url = baseUrl + relativeUrl;

			 String jsonToSend = null;
		        try {    
		            //Instantiate JSON mapper
		            ObjectMapper mapper = new ObjectMapper();
		            //Log request dto
		            LOGGER.info("Sent DTO: " + mapper.writeValueAsString(addInfoResp));
		           
		            //Parse to json
		            jsonToSend = mapper.writeValueAsString(addInfoResp);
		        } catch (Exception e){
		        	e.printStackTrace();
		        	Response.serverError().build();
		        }    
			
			// Send
			Runnable sendThread = new SendThread(
					jsonToSend, url, 0, "Create Send Thread for addInfoResponse");
			new Thread(sendThread).start();			  
		 
			taskForm.completeTask();
			
	  }
	  
	  public void forward() throws IOException {
		  // forward to customer
		  addInfoRequestEntity.setAvailable(false);
		  addInfoRequestService.mergeAndComplete(addInfoRequestEntity);
		  taskForm.completeTask();	  				  
		 
	  }
}