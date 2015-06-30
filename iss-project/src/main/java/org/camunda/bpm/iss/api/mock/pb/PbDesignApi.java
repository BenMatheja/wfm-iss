package org.camunda.bpm.iss.api.mock.pb;

import java.util.logging.Logger;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.camunda.bpm.iss.DTO.in.DesignFeedbackDTO;
import org.camunda.bpm.iss.DTO.in.DesignRequestDTO;
import org.camunda.bpm.iss.DTO.out.JobIdDTO;
import org.codehaus.jackson.map.ObjectMapper;

@WebService
@Path("/pb/design")
public class PbDesignApi{

	private final static Logger LOGGER = Logger.getLogger("PB-DESIGNREQUEST-API");

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JobIdDTO createDesignRequest(DesignRequestDTO designRequest){
		LOGGER.info("Webservice called!");
		
        //Create DTO
  		JobIdDTO resultDTO = new JobIdDTO();
  		resultDTO.setJobId(1);
        
        String json = null;
        try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
            //Log request dto
            LOGGER.info("Received DTO: " + mapper.writeValueAsString(designRequest));
            //Log request dto
            LOGGER.info("Returned DTO: " + mapper.writeValueAsString(resultDTO));
            
            //Parse to json
            json = mapper.writeValueAsString(designRequest);
        } catch (Exception e){
        	e.printStackTrace();
        	return null;
        }
    	

		//Return result
		return resultDTO;

	}
	
	@POST
	@Path("/feedback")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receiveDesignFeedback(DesignFeedbackDTO feedback){
		LOGGER.info("Webservice called!");
        
        try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
            //Log request dto
            LOGGER.info("Received DTO: " + mapper.writeValueAsString(feedback));

        } catch (Exception e){
        	e.printStackTrace();
        	return Response.serverError().build();
        }
    	

        //Return result with statusCode 200
      	return Response.ok().build(); 

	}
}