package org.camunda.bpm.iss.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.ejb.BillIssService;
import org.camunda.bpm.iss.ejb.BillService;
import org.camunda.bpm.iss.ejb.ContractService;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Bill;
import org.camunda.bpm.iss.entity.BillIss;
import org.camunda.bpm.iss.entity.Contract;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.Employee;
import org.camunda.bpm.iss.entity.Project;

import com.itextpdf.text.DocumentException;

@Stateless
@Named
public class InformAccounting {

	private static Logger LOGGER = Logger.getLogger(InformAccounting.class
			.getName());

	private ProcessEngine processEngine = ProcessEngines
			.getDefaultProcessEngine();

	private IdentityService is = processEngine.getIdentityService();
	private RuntimeService rs = processEngine.getRuntimeService();

	// Inject the Services
	@Inject
	private ProjectService projectService;
	
	@Inject
	private GenerateBill generateBill;

	@Inject
	private CustomerService customerService;
	
	@Inject 
	private ContractService contractService;
	
	@Inject 
	private BillService billService;
	
	@Inject 
	private BillIssService billIssService;
	
	// Local vars
	long pmHourlyRate = 20;
	long emHourlyRate = 12;
	long noOfPm = 1;
	long workingHours;
	long projectCosts;
	long workingHoursAcc;
	
	public void calcTime(DelegateExecution delegateExecution) throws DocumentException, IOException {
		LOGGER.info("This is calc Time!");
		
		Map<String, Object> variables = delegateExecution.getVariables();
		Project project = projectService.getProject((Long) variables
				.get("projectId"));

		Date startTime = project.getProjectStart();
		Date endTime = project.getProjectEnd();

		LOGGER.info("Start Time: " + startTime);
		LOGGER.info("End Time: " + endTime);

		long diff = endTime.getTime() - startTime.getTime();
		long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		// We assume a 42 hour-week
		workingHours = days * 6;

		LOGGER.info("No. of days: " + days);
		LOGGER.info("Working Hours: " + workingHours);
		
		// Calc Project Manager Wage
		long pmWage = pmHourlyRate * noOfPm;
		
		// Calc Employee Wage
		LOGGER.info("Employee Size is: " + project.getEmployee().size());
		long emWage = project.getEmployee().size() * emHourlyRate;
		
		// Calc Project Costs
		projectCosts = pmWage + emWage;
		
		delegateExecution.setVariable("projectCosts", projectCosts);
		LOGGER.log(Level.INFO, "This is projectCosts in the business process: "
				+ delegateExecution.getVariable("projectCosts"));
		
		/** 
		 * Here begins some further calc bc. we changed requiremtnts
		 */
		workingHoursAcc = (workingHours * noOfPm) + (workingHours * project.getEmployee().size()); 
	}
	
	public void persistBill(DelegateExecution delegateExecution) throws DocumentException, IOException{
		
		try{
			calcTime(delegateExecution);	
		} catch(Exception e){
			
		}
		
		// Customer
		  Customer customerEntity = customerService.getCustomer((Long) delegateExecution.getVariable("customerId"));
		  String customerName = customerEntity.getName();
		  String customerAddress = customerEntity.getAddress();
		  
		// Invoice Date
		  Calendar calendar = Calendar.getInstance();			 
		  java.util.Date now = calendar.getTime();
		  Date date = now;
		  
		// Project Title
		  Project projectEntity = projectService.getProject((Long) delegateExecution.getVariable("projectId"));
		  String projectTitle = projectEntity.getTitle();
		  Date projectStart = projectEntity.getProjectStart();
		  Date projectEnd = projectEntity.getProjectEnd();
		  
		// Contract Title + Price
		  Contract contractEntity = contractService.getContract((Long) delegateExecution.getVariable("contractId"));
		  String contractTitle = contractEntity.getContractTitle();
		  int contractPrice = contractEntity.getPrice();
		  
		// Employees
		  Collection<Employee> employees = projectEntity.getEmployee();
		  
		  //Pb Bill
		  double pbTotal = 0;
		  try {
		  Bill billPb = billService.getBill((Long) delegateExecution.getVariable("billId"));
		  pbTotal = billPb.getTotalSum();
		  } catch (Exception e) {
			  pbTotal = 0;
		  }		  
		  
		  
		  // Initiate Bill Object
		  BillIss newbill = new BillIss();
		  newbill.setCostEstimation(contractPrice); 		//costEstimation
		  newbill.setCustomerName(customerName); 			//customerName
		  newbill.setCustomerAddress(customerAddress);		//customerAddress
		  newbill.setProjectTitle(projectTitle); 			//projectTitle
		  newbill.setContractTitle(contractTitle); 			//contractTitle
		  newbill.setDate(now);								//date
		  newbill.setProjectStart(projectStart);			//projectStart
		  newbill.setProjectEnd(projectEnd);				//projectEnd
		  newbill.setAccumulatedHours(workingHoursAcc);		//accumulatedHours
		  newbill.setIssTotal((int)projectCosts);			//issTotal
		  newbill.setPbTotal(pbTotal);						//pbTotal
		  newbill.setPriceInCent(newbill.getIssTotal() + (int)(newbill.getPbTotal()*100)); //priceInCent 
		  
		  BillIss persistedBill = billIssService.create(newbill);
		  delegateExecution.setVariable("billIssId", persistedBill.getId());
		  LOGGER.info("Bill Id in process Vars: " + delegateExecution.getVariable("billIssId"));
		  
		  generateBill.main(delegateExecution, persistedBill);
	}
	
}
