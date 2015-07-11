package org.camunda.bpm.iss.api.mock.iss;

import java.util.HashMap;
import java.util.Map;
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
import org.camunda.bpm.iss.DTO.out.BillDTO;
import org.camunda.bpm.iss.ejb.BillService;
import org.camunda.bpm.iss.entity.Bill;
import org.codehaus.jackson.map.ObjectMapper;

@WebService
@Path("/iss/bill")
public class IssBillAPI {

	private final static Logger LOGGER = Logger.getLogger("ISS-BILL-API");
	
	private ProcessEngine processEngine = ProcessEngines
			.getDefaultProcessEngine();
	private RuntimeService rs = processEngine.getRuntimeService();
	
	@Inject
	BillService billService;
	
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
        
        Bill billEntity = new Bill();
		billEntity.setJobId(bill.getJobId());
		billEntity.setBill(bill.getBill());
		
        Bill persistedBill = billService.create(billEntity);
		
        LOGGER.info("Bill persisted id:" + persistedBill.getJobId());
		
        Map<String,Object> correlationKeys = new HashMap<String,Object>();
		Map<String,Object> processVariables = new HashMap<String,Object>();
		
		correlationKeys.put("jobId", bill.getJobId());
		LOGGER.info("Put jobId: " + bill.getJobId() + "into correlationKeys");
		
		rs.correlateMessage("bill", correlationKeys, processVariables);
        
        //Return result with statusCode 200
      	return Response.ok().build(); 
	}

}
