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

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.iss.ejb.BillService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Bill;
import org.camunda.bpm.iss.entity.Design;
import org.camunda.bpm.iss.entity.Project;

@Named
@ConversationScoped
public class ConductPaymentController implements Serializable{

	  private static  final long serialVersionUID = 1L;
	  
	  
	  private static Logger LOGGER = Logger.getLogger(ConductPaymentController.class.getName());
	  // Inject the BusinessProcess to access the process variables
	  @Inject
	  private BusinessProcess businessProcess;
	  
	  @Inject
	  private TaskForm taskForm;
	 
	  // Inject the EntityManager to access the persisted order
	  @PersistenceContext
	  private EntityManager entityManager;
	 
	  // Inject the Service 
	  @Inject
	  private ProjectService projectService;
	  
	  @Inject
	  private BillService billService;
	
	 
	  // Caches the Entities during the conversation
	  private Project projectEntity;
	  private Bill billEntity;
	  	  
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
	  
	  public Bill getBillEntity() {
			if (billEntity == null) {
				// Load the entity from the database if not already cached
				LOGGER.log(Level.INFO, "This is billId from businessProcess: "
						+ businessProcess.getVariable("billId"));
				LOGGER.log(
						Level.INFO,
						"This is getDesign from the Service invoked with it "
								+ billService.getBill((Long) businessProcess
										.getVariable("billId")));
				billEntity = billService.getBill((Long) businessProcess
						.getVariable("billId"));
			} else {
				LOGGER.log(Level.INFO, "BillEntity wasn't NULL");
			}
			return billEntity;
		}
	  
	  public void startDownload() {
		    billEntity = getBillEntity();		    	
		    
		    FacesContext facesContext = FacesContext.getCurrentInstance();
		    ExternalContext externalContext = facesContext.getExternalContext();
		    externalContext.setResponseHeader("Content-Type", "application");
		    externalContext.setResponseHeader("Content-Length", ""+billEntity.getBill().length);
		    externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + projectEntity.getTitle()+" PinkBlobBill.pdf" + "\"");
		    try {
				externalContext.getResponseOutputStream().write(billEntity.getBill());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    facesContext.responseComplete();

		}
	  
	  public void submit() throws IOException {
		  taskForm.completeTask();
	  }
	
}