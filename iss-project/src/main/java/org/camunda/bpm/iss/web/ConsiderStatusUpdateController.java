package org.camunda.bpm.iss.web;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.ejb.StatusUpdateService;
import org.camunda.bpm.iss.entity.Customer;

@Named
@ConversationScoped
public class ConsiderStatusUpdateController implements Serializable{

	@Inject
	StatusUpdateService statusUpdateService;
	
	@Inject 
	CustomerService customerService;
	
	@Inject 
	ProjectService projectService;
	
	@Inject 
	BusinessProcess businessProcess;
	
	Customer customerEntity = getCustomer();
	
	long statusUpdateId = (Long) businessProcess.getVariable("statusUpdateId");
	
	public Customer getCustomer(){
		return customerService.getCustomer((Long)businessProcess.getVariable("customerId"));
	}
	
	//output Date of Status Update received
	public Date getDate(){
		return statusUpdateService.getDate(statusUpdateId);
	}
	
	//output Status Update Message
	public String getMessage(){
		return statusUpdateService.getMessage(statusUpdateId);
	}
	
	//output Project Title
	public String getProjectTitle(){
		return projectService.getProject((Long) businessProcess.getVariable("projectId")).getTitle();
	}
	
	//output Design Title
	public String getDesignRequestTitle(){
		return businessProcess.getVariable("designRequestTitle");
	}
}