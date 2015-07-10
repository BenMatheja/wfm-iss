package org.camunda.bpm.iss.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.iss.ejb.DeliverableService;
import org.camunda.bpm.iss.entity.Deliverable;

@Named
@ConversationScoped
public class SubmitDeliverablesController implements Serializable{

	@Inject 
	DeliverableService deliverableService;
	
	@Inject 
	BusinessProcess businessProcess;
	
	Deliverable deliverable = new Deliverable();
	private Part file;
	private byte[] fileByte;
	
	
//	public void upload() {
//		if (file == null || file.getSize() == 0) {
//			throw new IllegalStateException();
//		}
//		try {
//			String fileName;
//			String contentType;
//			InputStream is = file.getInputStream();
//			fileByte = new byte[(int) file.getSize()];
//			is.read(fileByte);
//			fileName = file.getSubmittedFileName();
//			contentType = file.getContentType();
//			deliverable.setExecutableVersion(fileByte);
//			deliverable.setFileContent(contentType);
//			deliverable.setFileName(fileName);
//			is.close();
//			LOGGER.log(Level.INFO, "This is the uploaded File:" + fileName);
//			LOGGER.log(Level.INFO, "This is the file content type:"
//					+ contentType);
//
//			file.getInputStream().close();
//		} catch (IOException e) {
//			// Error handling
//			throw new IllegalStateException();
//		}
//	}
	
	public void persist(){
		
	}
}