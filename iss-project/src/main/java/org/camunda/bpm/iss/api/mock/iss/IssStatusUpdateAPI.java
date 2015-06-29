package org.camunda.bpm.iss.api.mock.iss;

import java.util.logging.Logger;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import org.camunda.bpm.iss.DTO.out.StatusDesignStartedDTO;
import org.camunda.bpm.iss.DTO.out.StatusDraftStartedDTO;

@WebService
@Path("/iss/status")
public class IssStatusUpdateAPI {

private final static Logger LOGGER = Logger.getLogger("ISS-STATUSUPDATE-API");
	
	@POST
	@Path("/draft/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receiveStatusUpdateDraft(StatusDraftStartedDTO statusDTO){
		LOGGER.info("Webservice called!");

        try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
            //Log request dto
            LOGGER.info("Received DTO: " + mapper.writeValueAsString(statusDTO));
        } catch (Exception e){
        	e.printStackTrace();
        	return Response.serverError().build();
        }    
        
        //Return result with statusCode 200
      	return Response.ok().build(); 
	}
	
	@POST
	@Path("/design/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receiveStatusUpdateDesign(StatusDesignStartedDTO statusDTO){
		LOGGER.info("Webservice called!");

        String jsonToSend = null;
        try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
            //Log request dto
            LOGGER.info("Received DTO: " + mapper.writeValueAsString(statusDTO));
        } catch (Exception e){
        	e.printStackTrace();
        	return Response.serverError().build();
        }    
        
        //Return result with statusCode 200
      	return Response.ok().build(); 
	}

}
