package org.camunda.bpm.iss.ejb;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.entity.Employee;


@Stateless
@Named
public class EmployeeService {
	
	@PersistenceContext
	  private EntityManager entityManager;
	
	// Inject task form available through the camunda cdi artifact
		  @Inject
		  private TaskForm taskForm;
		  
		  private static Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());
			 
		  public void persistEmployee(DelegateExecution delegateExecution) {
		   
			LOGGER.log(Level.INFO, "Create new customer bill instance");  
			  // Create new customer instance
		    Employee employeeEntity = new Employee();
		 
		    LOGGER.log(Level.INFO, "Get all process variables");
		    // Get all process variables
		    Map<String, Object> variables = delegateExecution.getVariables();
		 
		    LOGGER.log(Level.INFO, "Set order attributes");
		    // Set order attributes
		    employeeEntity.setName((String) variables.get("name"));
		    employeeEntity.setHourlyRate((String) variables.get("hourlyRate"));
		 
		    /*
		      Persist customer instance and flush. After the flush the
		      id of the customer instance is set.
		    */
		    LOGGER.log(Level.INFO, " Persist customer bill instance and flush.");
		    
		    entityManager.persist(employeeEntity);
		    entityManager.flush();
		    
		    // Remove no longer needed process variables
		    delegateExecution.removeVariables(variables.keySet());
		 
		    // Add newly created id as process variable
		   	delegateExecution.setVariable("employeeId", employeeEntity.getEmployee());
		  }
		  
		  public Employee getEmployee(Long employeeId) {
			  // Load entity from database
			  return entityManager.find(Employee.class, employeeId);
		  }

}
