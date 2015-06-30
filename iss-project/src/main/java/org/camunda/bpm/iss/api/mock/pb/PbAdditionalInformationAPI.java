package org.camunda.bpm.iss.api.mock.pb;

import java.util.logging.Logger;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.camunda.bpm.iss.DTO.in.AddInfoResponseDTO;
import org.codehaus.jackson.map.ObjectMapper;

@WebService
@Path("/pb/additionalInformation")
public class PbAdditionalInformationAPI{

	private final static Logger LOGGER = Logger.getLogger("PB-AdditionalInformation-API");
	
	@POST
	@Path("/respond")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createDesignRequest(AddInfoResponseDTO infoResponse){
		LOGGER.info("Webservice called!");
		
		try {    
            //Instantiate JSON mapper
            ObjectMapper mapper = new ObjectMapper();
            //Log results
            LOGGER.info(mapper.writeValueAsString(infoResponse));
            
        } catch (Exception e){
        	e.printStackTrace();
        	return Response.serverError().build();
        }
		//Return result with statusCode 200
		return Response.ok().build();

	}

}
