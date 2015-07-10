package org.camunda.bpm.iss.web;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.iss.ejb.StatusUpdateService;

@Named
@ConversationScoped
public class ConsiderStatusUpdateController implements Serializable{

	@Inject
	StatusUpdateService statusUpdateService;
	
	@Inject 
	BusinessProcess businessProcess;
	
	long statusUpdateId = (Long) businessProcess.getVariable("statusUpdateId");
	
	public Date getDate(){
		return statusUpdateService.getDate(statusUpdateId);
	}
	
	public String getMessage(){
		return statusUpdateService.getMessage(statusUpdateId);
	}
}