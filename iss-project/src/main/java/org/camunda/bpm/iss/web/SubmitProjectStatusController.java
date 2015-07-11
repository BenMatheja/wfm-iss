package org.camunda.bpm.iss.web;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Project;

@Named
@ConversationScoped
public class SubmitProjectStatusController implements Serializable{

	@Inject
	private BusinessProcess businessProcess;
	
	@Inject
	private ProjectService projectService;
	
	@Inject
	private CustomerService customerService;
	
	@Inject 
	private TaskForm taskform;
	
	private Project project;
	
	public Project getProject() {
		if(project == null){
			projectService.getProject((Long)businessProcess.getVariable("projectId"));
		}
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
//	public void submit() throws IOException{
//		try{
//		projectService.updateProject(project);
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		taskform.completeTask();
//	}
}