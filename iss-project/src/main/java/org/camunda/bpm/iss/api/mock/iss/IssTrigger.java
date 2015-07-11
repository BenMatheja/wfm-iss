package org.camunda.bpm.iss.api.mock.iss;

import java.util.logging.Logger;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import org.camunda.bpm.iss.entity.util.GlobalDefinitions;
import org.camunda.bpm.iss.DTO.out.AddInfoRequestDTO;
import org.camunda.bpm.iss.DTO.out.BillDTO;
import org.camunda.bpm.iss.DTO.out.DesignDTO;
import org.camunda.bpm.iss.DTO.out.StatusDesignStartedDTO;
import org.camunda.bpm.iss.DTO.out.StatusDraftStartedDTO;
import org.camunda.bpm.iss.api.mock.SendThread;

@WebService
@Path("/iss/trigger")
public class IssTrigger{

	private final static Logger LOGGER = Logger.getLogger("ISS-API-TRIGGER");
	
	@GET
	@Path("/addInfoRequest")
	public Response sendAdditionalInformationRequest(){
		LOGGER.info("Webservice called!");
 
		// specify the REST web service to interact with
		String baseUrl = GlobalDefinitions.getIssBaseURL();
        String relativeUrl = GlobalDefinitions.URL_API_ISS_RECEIVE_ADDITIONAL_INFORMATION_REQEUST;
        String url = baseUrl + relativeUrl;
		
        //Create DTO for the API call
        AddInfoRequestDTO requestDTO = new AddInfoRequestDTO();
        requestDTO.setAddtitionalInfoId(1);
        requestDTO.setQuestion("Automatically generated question by " + this.getClass().getName());
        requestDTO.setDesignJobId(1);
        
        String jsonToSend = null;
        try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
           
            //Parse to json
            jsonToSend = mapper.writeValueAsString(requestDTO);
        } catch (Exception e){
        	e.printStackTrace();
        	return Response.serverError().build();
        }    
       
        //Send next API call in new thread, which delays the call
        Runnable sendThread = new SendThread(jsonToSend, url, 0, "SendAddInfoReq");
        new Thread(sendThread).start();
        
        //Return result with statusCode 200
      	return Response.ok().build(); 
	}
	
	@GET
	@Path("/statusUpdateDraft")
	public Response sendStatusUpdateDraft(){
		LOGGER.info("Webservice called!");
 
		// specify the REST web service to interact with
		String baseUrl = GlobalDefinitions.getIssBaseURL();
        String relativeUrl = GlobalDefinitions.URL_API_ISS_RECEIVE_STATUS_UPDATE_DRAFT;
        String url = baseUrl + relativeUrl;
		
        //Create DTO for the API call
        StatusDraftStartedDTO requestDTO = new StatusDraftStartedDTO();
        requestDTO.setJobId(1);
        requestDTO.setMessage("Automatically generated message by " + this.getClass().getName());
        
        String jsonToSend = null;
        try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
           
            //Parse to json
            jsonToSend = mapper.writeValueAsString(requestDTO);
        } catch (Exception e){
        	e.printStackTrace();
        	return Response.serverError().build();
        }    
       
        //Send next API call in new thread, which delays the call
        Runnable sendThread = new SendThread(jsonToSend, url, 0, "SendStatusUpdateDraft");
        new Thread(sendThread).start();
        
        //Return result with statusCode 200
      	return Response.ok().build(); 
	}
	
	@GET
	@Path("/statusUpdateDesign")
	public Response sendStatusUpdateDesign(){
		LOGGER.info("Webservice called!");
 
		// specify the REST web service to interact with
		String baseUrl = GlobalDefinitions.getIssBaseURL();
        String relativeUrl = GlobalDefinitions.URL_API_ISS_RECEIVE_STATUS_UPDATE_DESIGN;
        String url = baseUrl + relativeUrl;
		
        //Create DTO for the API call
        StatusDesignStartedDTO requestDTO = new StatusDesignStartedDTO();
        requestDTO.setJobId(1);
        requestDTO.setMessage("Automatically generated message by " + this.getClass().getName());
        
        String jsonToSend = null;
        try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
           
            //Parse to json
            jsonToSend = mapper.writeValueAsString(requestDTO);
        } catch (Exception e){
        	e.printStackTrace();
        	return Response.serverError().build();
        }    
       
        //Send next API call in new thread, which delays the call
        Runnable sendThread = new SendThread(jsonToSend, url, 0, "SendStatusUpdateDesign");
        new Thread(sendThread).start();
        
        //Return result with statusCode 200
      	return Response.ok().build(); 
	}

	@GET
	@Path("/sendDesign")
	public Response sendDesign(){
		LOGGER.info("Webservice called!");
 
		// specify the REST web service to interact with
		String baseUrl = GlobalDefinitions.getIssBaseURL();
        String relativeUrl = GlobalDefinitions.URL_API_ISS_RECEIVE_DESIGN;
        String url = baseUrl + relativeUrl;
		
        //Create DTO for the API call
        DesignDTO designDTO = new DesignDTO();
        designDTO.setJobId(1);
        designDTO.setDesignZIP(null);
        
        String jsonToSend = null;
        try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
           
            //Parse to json
            jsonToSend = mapper.writeValueAsString(designDTO);
        } catch (Exception e){
        	e.printStackTrace();
        	return Response.serverError().build();
        }    
       
        //Send next API call in new thread, which delays the call
        Runnable sendThread = new SendThread(jsonToSend, url, 0, "SendDesign");
        new Thread(sendThread).start();
        
        //Return result with statusCode 200
      	return Response.ok().build(); 
	}
	
	@GET
	@Path("/sendBill")
	public Response sendBill(){
		LOGGER.info("Webservice called!");
 
		// specify the REST web service to interact with
		String baseUrl = GlobalDefinitions.getIssBaseURL();
        String relativeUrl = GlobalDefinitions.URL_API_ISS_RECEIVE_BILL;
        String url = baseUrl + relativeUrl;
		
        //Create DTO for the API call
        BillDTO billDTO = new BillDTO();
        billDTO.setJobId(1);
        billDTO.setBill(null);
        
        String jsonToSend = null;
        try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
           
            //Parse to json
            jsonToSend = mapper.writeValueAsString(billDTO);
        } catch (Exception e){
        	e.printStackTrace();
        	return Response.serverError().build();
        }    
       
        //Send next API call in new thread, which delays the call
        Runnable sendThread = new SendThread(jsonToSend, url, 0, "SendBill");
        new Thread(sendThread).start();
        
        //Return result with statusCode 200
      	return Response.ok().build(); 
	}
	
}
