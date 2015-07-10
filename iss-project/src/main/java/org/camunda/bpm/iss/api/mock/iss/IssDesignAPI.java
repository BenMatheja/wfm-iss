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
import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.iss.DTO.in.DesignFeedbackDTO;
import org.camunda.bpm.iss.DTO.out.DesignDTO;
import org.camunda.bpm.iss.ejb.DesignService;
import org.camunda.bpm.iss.entity.Design;
import org.camunda.bpm.iss.entity.util.GlobalDefinitions;
import org.codehaus.jackson.map.ObjectMapper;

@WebService
@Path("/iss/design")
public class IssDesignAPI {

	private final static Logger LOGGER = Logger.getLogger("ISS-DESIGN-API");
	
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	private RuntimeService rs = processEngine.getRuntimeService();
	
	@Inject
	DesignService designService;
	
	@Inject
	BusinessProcess businessProcess;
	
	@POST
	@Path("/receive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receiveAdditionalInformation(DesignDTO design){
		LOGGER.info("Webservice called!");
		
		rs.correlateMessage("design");	  
 
		// specify the REST web service to interact with
		String baseUrl = GlobalDefinitions.getPbBaseURL();
        String relativeUrl = GlobalDefinitions.URL_API_PB_RECEIVE_DESIGN_FEEDBACK;
        String url = baseUrl + relativeUrl;
		
        //Create DTO for the next API call
        DesignFeedbackDTO feedbackDTO = new DesignFeedbackDTO();
        feedbackDTO.setJobId(design.getJobId());
        feedbackDTO.setApproved(true);
        feedbackDTO.setComment("Automatically generated comment by " + this.getClass().getName());
        
        String jsonToSend = null;
        try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
            //Log received dto
            LOGGER.info("Received DTO: " + mapper.writeValueAsString(design));
           
            //Parse to json
            jsonToSend = mapper.writeValueAsString(feedbackDTO);
        } catch (Exception e){
        	e.printStackTrace();
        	return Response.serverError().build();
        }    
        
        Design designEntity = new Design();
        designEntity.setJobId(design.getJobId());
        designEntity.setDesignZIP(design.getDesignZIP());
        
        Design persistedDesign = designService.create(designEntity);
		LOGGER.info("Design persisted id: " + persistedDesign.getId());
		LOGGER.info("Job Id: " + persistedDesign.getJobId());
		LOGGER.info("ZIP: " + persistedDesign.getDesignZIP());
		
		businessProcess.setVariable("designId",
				persistedDesign.getId());
		LOGGER.info("Set Process Variable for Design ID:"
				+ businessProcess.getVariable("designId"));
       
        //Send next API call in new thread, which delays the call
//        Runnable sendThread = new SendThread(jsonToSend, url, 5000, "SendDesignFeedback");
//        new Thread(sendThread).start();
        
        //Return result with statusCode 200
      	return Response.ok().build(); 
	}

}
