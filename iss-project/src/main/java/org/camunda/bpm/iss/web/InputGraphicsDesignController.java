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

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.iss.api.mock.SendThread;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Project;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

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
	  private TaskForm taskForm;
	  
	  // Caches the Entities during the conversation
	  private Project projectEntity; 

	  
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
	  
	  public void submit() throws IOException {	
		  // Create JSON
//		  ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//		  String json = ow.writeValueAsString(designRequest);
//		  // Create url
//		  String url = URL_API_PB_RECEIVE_DESIGN_REQUEST;
//		  
//		  //Send 
//		  SendThread sendThread = new SendThread(json,url,0,"Create Send Thread for designRequest");
//		  sendThread.run();
//		  
		  
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