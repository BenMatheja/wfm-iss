package org.camunda.bpm.iss.api.mock.iss;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.iss.DTO.in.AddInfoResponseDTO;
import org.camunda.bpm.iss.DTO.out.AddInfoRequestDTO;
import org.camunda.bpm.iss.api.mock.SendThread;
import org.camunda.bpm.iss.ejb.AddInfoRequestService;
import org.camunda.bpm.iss.entity.AddInfoRequest;
import org.camunda.bpm.iss.entity.util.GlobalDefinitions;
import org.codehaus.jackson.map.ObjectMapper;

@WebService
@Path("/iss/additionalInformation")
public class IssAdditionalInformationAPI{

	private final static Logger LOGGER = Logger.getLogger("ISS-ADDITITIONALINFORMATION-API");
	
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	private RuntimeService rs = processEngine.getRuntimeService();
	
	@Inject
	AddInfoRequestService addInfoRequestService;
	
	@POST
	@Path("/request")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receiveAdditionalInformation(AddInfoRequestDTO infoRequest){
		LOGGER.info("Webservice called!");
		
		rs.correlateMessage("addinf_request");	    
 
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
        
        AddInfoRequest addInfo = new AddInfoRequest();
        addInfo.setAddtitionalInfoId(infoRequest.getAddtitionalInfoId());
        addInfo.setQuestion(infoRequest.getQuestion());
        
        AddInfoRequest persistedAddInfoRequest = addInfoRequestService.create(addInfo);
		LOGGER.info("AddInfoRequest persisted id:" + persistedAddInfoRequest.getAddtitionalInfoId());
		LOGGER.info("Question:" + persistedAddInfoRequest.getQuestion());
		
       
        //Send next API call in new thread, which delays the call
        Runnable sendThread = new SendThread(jsonToSend, url, 0, "SendAddInfo");
        new Thread(sendThread).start();        
        
        LOGGER.info("Successfully reached the end of IssAdditionalInformationAPI!");
        //Return result with statusCode 200
      	return Response.ok().build(); 
	}

}
