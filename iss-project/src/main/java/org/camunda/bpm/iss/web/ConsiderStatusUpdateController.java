package org.camunda.bpm.iss.web;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.ejb.StatusUpdateService;
import org.camunda.bpm.iss.entity.Project;
import org.camunda.bpm.iss.entity.StatusUpdate;

@Named
@ConversationScoped
public class ConsiderStatusUpdateController implements Serializable{

	private static final long serialVersionUID = 1L;
	 
	private static Logger LOGGER = Logger.getLogger(EvaluateAppointmentController.class.getName());
	 
	@Inject
	StatusUpdateService statusUpdateService;
			
	@Inject 
	ProjectService projectService;
	
	@Inject 
	BusinessProcess businessProcess;	
	
	private Project projectEntity;	
	private StatusUpdate statusUpdateEntity;	
	private String designTitle; 
		
		
	public Project getProjectEntity() {
		if (projectEntity == null) {
			// Load the entity from the database if not already cached
			LOGGER.log(Level.INFO, "This is projectId from businessProcess: "
					+ businessProcess.getVariable("projectId"));
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
	
	public StatusUpdate getStatusUpdateEntity() {
		if (statusUpdateEntity == null) {
			// Load the entity from the database if not already cached	
			Map<String, Object> variables = businessProcess.getCachedVariableMap();
			Collection<String> allVars = variables.keySet();
			for (String a:allVars) {
				LOGGER.log(Level.INFO, "Element in BusinessProcess: " + a);
			}
			
			LOGGER.log(Level.INFO, "This is statusUpdateId from businessProcess: "
					+ businessProcess.getVariable("statusUpdateId"));
			LOGGER.log(
					Level.INFO,
					"This is getStatusUpdate from the Service invoked with it "
							+ statusUpdateService.getStatusUpdate((Long) businessProcess
									.getVariable("statusUpdateId")));
			statusUpdateEntity = statusUpdateService.getStatusUpdate((Long) businessProcess.getVariable("statusUpdateId"));
		} else {
			LOGGER.log(Level.INFO, "Status Update wasn't NULL, but: " + statusUpdateEntity.getId());
		}
		return statusUpdateEntity;
	}
	
	//output Design Title
	public String getDesignTitle(){
		return businessProcess.getVariable("designRequestTitle");
	}
}