package org.camunda.bpm.iss.api.mock;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.camunda.bpm.iss.DTO.out.JobIdDTO;
import org.camunda.bpm.iss.ejb.JobIdService;
import org.camunda.bpm.iss.entity.util.GlobalDefinitions;

@Named
public class SendDesignRequirements implements Runnable {

	private String jsonToSend;
	
	private String url;
	
	private long waitTime = 3000;

	@Inject
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
		JobIdDTO jobIdDTO = new JobIdDTO();
		
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
	        
	        jobIdDTO = response.readEntity(JobIdDTO.class);
	       
	        if (jobIdDTO == null) LOGGER.info("jobIdDTO is Null, dude!");
	        
	        LOGGER.info("JobIdDTO to String: "+ jobIdDTO.toString());
	        LOGGER.info("jobIdDTO JobId: " + jobIdDTO.getJobId());
	       
			// Now we save the jobId into a global var
			GlobalDefinitions.JOB_ID = jobIdDTO.getJobId();
			  
		} catch (Exception e) {
			e.printStackTrace();	       
	  	}
		} 

	}

	
