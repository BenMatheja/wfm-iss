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
import org.camunda.bpm.iss.ejb.AppointmentService;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.MeetingMinutesService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Appointment;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.MeetingMinutes;
import org.camunda.bpm.iss.entity.Project;

@Named
@ConversationScoped
public class SubmitMeetingMinutesController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TaskForm taskForm;

	private static Logger LOGGER = Logger
			.getLogger(SubmitMeetingMinutesController.class.getName());
	// Inject the BusinessProcess to access the process variables
	@Inject
	private BusinessProcess businessProcess;

	// Inject the Service
	@Inject
	private ProjectService projectService;
	@Inject
	private CustomerService customerService;
	@Inject
	private AppointmentService appointmentService;
	@Inject
	private MeetingMinutesService meetingMinutesService;

	// Caches the Entities during the conversation
	private Project projectEntity;
	private Customer customerEntity;
	private Appointment appointmentEntity;
	private MeetingMinutes meetingMinutes = new MeetingMinutes();
	private Part file;
	private byte[] fileByte;
	
	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

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

	public Customer getCustomerEntity() {
		if (customerEntity == null) {
			// Load the entity from the database if not already cached
			LOGGER.log(Level.INFO, "This is customerId from businessProcess: "
					+ businessProcess.getVariable("customerId"));
			LOGGER.log(Level.INFO, "This is the same casted as Long: "
					+ (Long) businessProcess.getVariable("customerId"));
			LOGGER.log(
					Level.INFO,
					"This is getCustomer from the Service invoked with it "
							+ customerService
									.getCustomer((Long) businessProcess
											.getVariable("customerId")));
			customerEntity = customerService.getCustomer((Long) businessProcess
					.getVariable("customerId"));
		}
		return customerEntity;
	}

	public Appointment getAppointmentEntity() {
		if (appointmentEntity == null) {
			// Load the entity from the database if not already cached
			LOGGER.log(Level.INFO,
					"This is appointmentId from businessProcess: "
							+ businessProcess.getVariable("appointmentId"));
			LOGGER.log(Level.INFO, "This is the same casted as Long: "
					+ (Long) businessProcess.getVariable("appointmentId"));
			LOGGER.log(
					Level.INFO,
					"This is getCustomer from the Service invoked with it "
							+ appointmentService
									.getAppointment((Long) businessProcess
											.getVariable("appointmentId")));
			appointmentEntity = appointmentService
					.getAppointment((Long) businessProcess
							.getVariable("appointmentId"));
		}
		return appointmentEntity;
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
			meetingMinutes.setFile(fileByte);
			meetingMinutes.setFileContent(contentType);
			meetingMinutes.setFileName(fileName);
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

	public void persist() throws IOException {

		LOGGER.log(Level.INFO, "Attempt to persist Meeting Minutes");

		meetingMinutes.setAppointment(appointmentService
				.getAppointment((Long) businessProcess
						.getVariable("appointmentId")));
		MeetingMinutes persistedMeetingMinutes = meetingMinutesService
				.create(meetingMinutes);

		LOGGER.log(Level.INFO, "These are the Infos for MeetingMinutes:");
		LOGGER.log(Level.INFO, "ID:  " + persistedMeetingMinutes.getId());
		LOGGER.log(Level.INFO, "appointmentID:  "
				+ persistedMeetingMinutes.getAppointment().getId());
		LOGGER.log(Level.INFO,
				"FileName:  " + persistedMeetingMinutes.getFileName());

		taskForm.completeTask();
	}
}