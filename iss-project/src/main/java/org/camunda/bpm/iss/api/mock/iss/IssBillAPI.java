package org.camunda.bpm.iss.api.mock.iss;

import java.util.logging.Logger;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import org.camunda.bpm.iss.DTO.out.BillDTO;

@WebService
@Path("/iss/bill")
public class IssBillAPI {

	private final static Logger LOGGER = Logger.getLogger("ISS-BILL-API");
	
	@POST
	@Path("/receive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response receiveBill(BillDTO bill){
		LOGGER.info("Webservice called!");

        try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
            //Log request dto
            LOGGER.info("Received DTO: " + mapper.writeValueAsString(bill));
        } catch (Exception e){
        	e.printStackTrace();
        	return Response.serverError().build();
        }    
        
        //Return result with statusCode 200
      	return Response.ok().build(); 
	}

}
