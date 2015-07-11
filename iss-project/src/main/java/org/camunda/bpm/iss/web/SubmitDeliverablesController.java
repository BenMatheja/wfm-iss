package org.camunda.bpm.iss.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.DeliverableService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.Deliverable;
import org.camunda.bpm.iss.entity.Project;


@Named
@ConversationScoped
public class SubmitDeliverablesController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static Logger LOGGER = Logger
			.getLogger(SubmitDeliverablesController.class.getName());
	
	@Inject
	private TaskForm taskForm;
	
	@Inject 
	private DeliverableService deliverableService;
	
	@Inject 
	private BusinessProcess businessProcess;
	
	@Inject 
	private CustomerService customerService;
	
	@Inject 
	private ProjectService projectService;

	Project project;
	private Customer customer;
	
	private Deliverable deliverable = new Deliverable();
	private Part file;
	private byte[] fileByte;
	
	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public Deliverable getDeliverable(){
		return deliverable;
	}
	
	public Project getProject(){
		if (project == null) {
			// Load the entity from the database if not already cached
			LOGGER.log(Level.INFO, "This is projectId from businessProcess: "
					+ businessProcess.getVariable("projectId"));
			LOGGER.log(
					Level.INFO,
					"This is getProject from the Service invoked with it "
							+ projectService.getProject((Long) businessProcess
									.getVariable("projectId")));
			project = projectService.getProject((Long) businessProcess
					.getVariable("projectId"));
		}
		return project;
	}
	
	public Customer getCustomer(){
		if (customer == null) {
			// Load the entity from the database if not already cached
			LOGGER.log(Level.INFO, "This is customerId from businessProcess: "
					+ businessProcess.getVariable("customerId"));
			LOGGER.log(
					Level.INFO,
					"This is getCustomer from the Service invoked with it "
							+ customerService.getCustomer((Long) businessProcess
									.getVariable("customerId")));
			customer = customerService.getCustomer((Long) businessProcess
					.getVariable("customerId"));
		}
		return customer;
	}
	
	
	public void upload() {
		if (file == null || file.getSize() == 0) {
			throw new IllegalStateException();
		}
		try {
			String fileName;
			String contentType;
			InputStream is = file.getInputStream();
			fileByte = new byte[(int) file.getSize()];
			is.read(fileByte);
			fileName = file.getSubmittedFileName();
			contentType = file.getContentType();
			deliverable.setExecutableVersion(fileByte);
			deliverable.setFileContent(contentType);
			deliverable.setFileName(fileName);
			is.close();
			LOGGER.log(Level.INFO, "This is the uploaded File:" + fileName);
			LOGGER.log(Level.INFO, "This is the file content type:"
					+ contentType);

			file.getInputStream().close();
		} catch (IOException e) {
			// Error handling
			throw new IllegalStateException();
		}
	}

	
	public void persist() throws IOException{
		LOGGER.log(Level.INFO, "Attempt to persist Deliverables");
		
		deliverable.setProject(project);
		Deliverable persistedDeliverable = deliverableService.create(deliverable);

		LOGGER.log(Level.INFO, "These are the Infos for Deliverable:");
		LOGGER.log(Level.INFO, "ID:  " + persistedDeliverable.getId());
		LOGGER.log(Level.INFO, "projectID:  "
				+ persistedDeliverable.getProject().getId());
		LOGGER.log(Level.INFO,
				"FileName:  " + persistedDeliverable.getFileName());

		
		taskForm.completeTask();
	}
	
	
}