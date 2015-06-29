package org.camunda.bpm.iss.api.mock.iss;

import java.util.logging.Logger;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import org.camunda.bpm.iss.entity.util.GlobalDefinitions;
import org.camunda.bpm.iss.DTO.in.AddInfoResponseDTO;
import org.camunda.bpm.iss.DTO.out.AddInfoRequestDTO;
import org.camunda.bpm.iss.api.mock.SendThread;

@WebService
@Path("/iss/additionalInformation")
public class IssAdditionalInformationAPI{

	private final static Logger LOGGER = Logger.getLogger("ISS-ADDITITIONALINFORMATION-API");
	
	@POST
	@Path("/request")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receiveAdditionalInformation(AddInfoRequestDTO infoRequest){
		LOGGER.info("Webservice called!");
 
		// specify the REST web service to interact with
		String baseUrl = GlobalDefinitions.getPbBaseURL();
        String relativeUrl = GlobalDefinitions.URL_API_PB_RECEIVE_ADDITIONAL_INFORMATION_RESPONSE;
        String url = baseUrl + relativeUrl;
		
        //Create DTO for the next API call
        AddInfoResponseDTO responseDTO = new AddInfoResponseDTO();
        responseDTO.setAddtitionalInfoId(infoRequest.getAddtitionalInfoId());
        responseDTO.setAnswer("Automatic test answer of " + this.getClass().getName());
        
        String jsonToSend = null;
        try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
            //Log request dto
            LOGGER.info("Received DTO: " + mapper.writeValueAsString(infoRequest));
           
            //Parse to json
            jsonToSend = mapper.writeValueAsString(responseDTO);
        } catch (Exception e){
        	e.printStackTrace();
        	return Response.serverError().build();
        }    
       
        //Send next API call in new thread, which delays the call
        Runnable sendThread = new SendThread(jsonToSend, url, 5000, "SendAddInfo");
        new Thread(sendThread).start();
        
        //Return result with statusCode 200
      	return Response.ok().build(); 
	}

}
