package org.camunda.bpm.iss.api.mock;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.camunda.bpm.iss.DTO.in.DesignRequestDTO;
import org.camunda.bpm.iss.DTO.out.JobIdDTO;
import org.camunda.bpm.iss.ejb.JobIdService;
import org.camunda.bpm.iss.entity.JobId;
import org.camunda.bpm.iss.entity.util.ArtType;
import org.camunda.bpm.iss.entity.util.GlobalDefinitions;
import org.codehaus.jackson.map.ObjectMapper;

public class SendDesignRequirements implements Runnable {

	private String jsonToSend;
	
	private String url;
	
	private long waitTime;
	
	@EJB
	JobIdService jobIdService;
	
	private static Logger LOGGER = Logger
			.getLogger(SendDesignRequirements.class.getName());
		
	public SendDesignRequirements(String json, String url, long waitTime,String logInfo) {
	       this.jsonToSend = json;
	       this.url = url;
	       this.waitTime = waitTime;
	       LOGGER = Logger.getLogger("SEND-THREAD-" + logInfo);
	}

	public void run() {	   	
		JobId jobIdEntity = new JobId();
		try {
			//Wait 10 seconds in order to let the other side process the answer the Webservice request
			Thread.sleep(waitTime);
			LOGGER.info("Send JSON: " + this.jsonToSend);
	        
	        //Instantiate
	        Client client = ClientBuilder.newClient();
	        //Send post request
	        Response response = client.target(url).request().post(Entity.entity(this.jsonToSend, MediaType.APPLICATION_JSON));
	        if (response == null){
	        	LOGGER.info("Null-response, dude!");
	        	return;
	        }
	        //Check, if status is "ok"
	        if (response.getStatus() != 200) {
	        	throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
	        }	
	        
			// this is the most important part for the first interface
			// we get the id for the remaining process communication			
			JobIdDTO jobId = (JobIdDTO) response.getEntity();
			
			// Looks complicated, but is easy: We set the Id from the DTO to a new entity called "jobIdEntity"
			jobIdEntity.setJobId(jobId.getJobId());
			
			// Now we persist "jobIdEntity"
			jobIdService.create(jobIdEntity);
					
		} catch (Exception e) {
			e.printStackTrace();	       
	  	}
		} 

	}

	
