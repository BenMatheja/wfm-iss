package org.camunda.bpm.iss.api.mock;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SendThread implements Runnable {

	private Logger LOGGER;
	
	private String jsonToSend;
	
	private String url;
	
	private long waitTime;
	
	public SendThread(String json, String url, long waitTime,String logInfo) {
	       this.jsonToSend = json;
	       this.url = url;
	       this.waitTime = waitTime;
	       LOGGER = Logger.getLogger("SEND-THREAD-" + logInfo);
	}

	public void run() {	   	
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
	
		} catch (Exception e) {
			e.printStackTrace();	       
	  	}
	}

}
