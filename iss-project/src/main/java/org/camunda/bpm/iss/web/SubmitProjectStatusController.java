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

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.MeetingMinutesService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.MeetingMinutes;
import org.camunda.bpm.iss.entity.Project;

@Named
@ConversationScoped
public class SubmitProjectStatusController implements Serializable {
	
	private static  final long serialVersionUID = 1L;
	
	private static Logger LOGGER = Logger.getLogger(SubmitProjectStatusController.class.getName());

	@Inject
	private BusinessProcess businessProcess;

	@Inject
	private ProjectService projectService;

	@Inject
	private CustomerService customerService;

	@Inject 
	private MeetingMinutesService meetingMinutesService;
	
	@Inject
	private TaskForm taskform;

	private Project project;
	private Customer customer;
	//only the last meeting minutes
	private MeetingMinutes meetingMinutes;

	public Project getProject() {
		if (project == null) {
			project = projectService.getProject((Long) businessProcess
					.getVariable("projectId"));
		}
		return project;
	}

	public Customer getCustomer() {
		if (customer == null) {
			customer = customerService.getCustomer((Long) businessProcess
					.getVariable("customerId"));
		}
		return customer;
	}
	
	public MeetingMinutes getMeetingMinutes(){
		if (meetingMinutes == null){
			meetingMinutes = meetingMinutesService.getMeetingMinutes((Long) businessProcess
					.getVariable("meetingMinutesId"));
			
			LOGGER.log(Level.INFO, "This is the MM object:" + meetingMinutes.toString());
		}
		return meetingMinutes;
	}

	public void startDownload() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.setResponseHeader("Content-Type", "application");
		externalContext.setResponseHeader("Content-Length", ""+meetingMinutes.getFile().length);
		externalContext.setResponseHeader("Content-Disposition",
				"attachment;filename=\"" + meetingMinutes.getFileName() + "\"");
		try {
			externalContext.getResponseOutputStream().write(
					meetingMinutes.getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		facesContext.responseComplete();

	}
	
	public void finished() throws IOException {
		project.setProjectStatus(true);
		complete();
	}

	public void pending() throws IOException {
		project.setProjectStatus(false);
		complete();
	}
	
	public void complete() throws IOException{
		projectService.updateProject(project);
		taskform.completeTask();
	}

}