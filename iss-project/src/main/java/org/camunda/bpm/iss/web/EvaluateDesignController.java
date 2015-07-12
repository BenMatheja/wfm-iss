package org.camunda.bpm.iss.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ConversationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.iss.DTO.in.DesignFeedbackDTO;
import org.camunda.bpm.iss.api.mock.SendThread;
import org.camunda.bpm.iss.ejb.DesignService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Design;
import org.camunda.bpm.iss.entity.Project;
import org.camunda.bpm.iss.entity.util.GlobalDefinitions;
import org.codehaus.jackson.map.ObjectMapper;

@Named
@ConversationScoped
public class EvaluateDesignController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = Logger
			.getLogger(EvaluateDesignController.class.getName());
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
	private DesignService designService;

	@Inject
	private TaskForm taskForm;

	// Caches the Entities during the conversation
	private Project projectEntity;
	private Design designEntity;
	private DesignFeedbackDTO designFeedback = new DesignFeedbackDTO();

	public Project getProjectEntity() {
		if (projectEntity == null) {
			// Load the entity from the database if not already cached
			LOGGER.log(Level.INFO, "This is projectId from businessProcess: "
					+ businessProcess.getVariable("projectId"));
			LOGGER.log(Level.INFO, "This is the same casted as Long: "
					+ (Long) businessProcess.getVariable("projectId"));
			LOGGER.log(
					Level.INFO,
					"This is getProject from the Service invoked with it "
							+ projectService.getProject((Long) businessProcess
									.getVariable("projectId")));
			projectEntity = projectService.getProject((Long) businessProcess
					.getVariable("projectId"));
		}
		return projectEntity;
	}

	public Design getDesignEntity() {
		if (designEntity == null) {
			// Load the entity from the database if not already cached
			LOGGER.log(Level.INFO, "This is designId from businessProcess: "
					+ businessProcess.getVariable("designId"));
			LOGGER.log(
					Level.INFO,
					"This is getDesign from the Service invoked with it "
							+ designService.getDesign((Long) businessProcess
									.getVariable("designId")));
			designEntity = designService.getDesign((Long) businessProcess
					.getVariable("designId"));
		} else {
			LOGGER.log(Level.INFO, "DesignEntity wasn't NULL");
		}
		return designEntity;
	}

	public void setDesignEntity(Design designEntity) {
		this.designEntity = designEntity;
	}

	public DesignFeedbackDTO getDesignFeedback() {
		return designFeedback;
	}

	public void setDesignFeedback(DesignFeedbackDTO designFeedback) {
		this.designFeedback = designFeedback;
	}
	
	public void startDownload() {
	    designEntity = getDesignEntity();
	    	
	    
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    externalContext.setResponseHeader("Content-Type", "application");
	    externalContext.setResponseHeader("Content-Length", "4");
	    externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + "Super File" + "\"");
	    try {
			externalContext.getResponseOutputStream().write(designEntity.getDesignZIP());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    facesContext.responseComplete();

	}

	public void accept() throws IOException {
		  // respond to PB
		  designEntity = getDesignEntity();
		  designEntity.setApproved(true);
		  designService.mergeAndComplete(designEntity);
		  
		  LOGGER.log(Level.INFO,"This is the jobId" + businessProcess.getVariable("jobId"));
		  designFeedback.setApproved(true);
		  designFeedback.setJobId((long) businessProcess.getVariable("jobId"));
		  
		  
		// specify the REST web service to interact with
			String baseUrl = GlobalDefinitions.getPbBaseURL();
			String relativeUrl = GlobalDefinitions.URL_API_PB_RECEIVE_DESIGN_FEEDBACK;
			String url = baseUrl + relativeUrl;

			 String jsonToSend = null;
		        try {    
		            //Instantiate JSON mapper
		            ObjectMapper mapper = new ObjectMapper();
		            //Log request dto
		            LOGGER.info("Sent DTO: " + mapper.writeValueAsString(designFeedback));
		           
		            //Parse to json
		            jsonToSend = mapper.writeValueAsString(designFeedback);
		        } catch (Exception e){
		        	e.printStackTrace();
		        	Response.serverError().build();
		        }    
			
			// Send
			Runnable sendThread = new SendThread(
					jsonToSend, url, 0, "Create Send Thread for designFeedback");
			new Thread(sendThread).start();			  
		 
			taskForm.completeTask();
			
	  }	public void decline() throws IOException {
		// respond to PB

		designEntity = getDesignEntity();
		designEntity.setApproved(false);
		designService.mergeAndComplete(designEntity);

		LOGGER.info("DesignEntity id: " + designEntity.getId());
		LOGGER.info("DesignEntity JobId: " + designEntity.getJobId());
		LOGGER.info("DesignEntity Approved: " + designEntity.isApproved());
		
		designFeedback.setApproved(false);
		designFeedback.setJobId((long) businessProcess.getVariable("jobId"));

		// specify the REST web service to interact with
		String baseUrl = GlobalDefinitions.getPbBaseURL();
		String relativeUrl = GlobalDefinitions.URL_API_PB_RECEIVE_DESIGN_FEEDBACK;
		String url = baseUrl + relativeUrl;

		String jsonToSend = null;
		try {
			// Instantiate JSON mapper
			ObjectMapper mapper = new ObjectMapper();
			// Log request dto
			LOGGER.info("Sent DTO: "
					+ mapper.writeValueAsString(designFeedback));

			// Parse to json
			jsonToSend = mapper.writeValueAsString(designFeedback);
		} catch (Exception e) {
			e.printStackTrace();
			Response.serverError().build();
		}

		// Send
		Runnable sendThread = new SendThread(jsonToSend, url, 0,
				"Create Send Thread for designFeedback");
		new Thread(sendThread).start();

		taskForm.completeTask();

	}

}