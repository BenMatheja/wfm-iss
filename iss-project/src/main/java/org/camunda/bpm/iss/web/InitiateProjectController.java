package org.camunda.bpm.iss.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.iss.ejb.CustomerService;
import org.camunda.bpm.iss.ejb.EmployeeService;
import org.camunda.bpm.iss.ejb.ProjectService;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.Employee;
import org.camunda.bpm.iss.entity.Project;

@Named
@ConversationScoped
public class InitiateProjectController implements Serializable{
	
	private static  final long serialVersionUID = 1L;
	  
	  @Inject
	  private TaskForm taskForm;
	  
	  private static Logger LOGGER = Logger.getLogger(InitiateProjectController.class.getName());
	  // Inject the BusinessProcess to access the process variables
	  @Inject
	  private BusinessProcess businessProcess;
	 
	  // Inject the EntityManager to access the persisted order
	  @PersistenceContext
	  private EntityManager entityManager;
	 
	  // Inject the Service 
	  @Inject
	  private CustomerService customerService;
	  
	  @Inject
	  private ProjectService projectService;
	  
	  @Inject
	  private EmployeeService employeeService;
	  
	  private Project project = new Project();
	  private LinkedList<String> employeeHelper;
 	  private List<SelectItem> userList = new ArrayList<SelectItem>(); 
	  
	public List<SelectItem> getUserList() {
		Collection<Employee> allEmployees = employeeService.getAllEmployees();
		for (Employee e:allEmployees) {
			if (e.getUserId() != "demo") {
				userList.add(new SelectItem(e.getId(), e.getFirstName()+" "+e.getLastName()));
			}
		}		
		return userList;
	}

	public LinkedList<String> getEmployeeHelper() {
		return employeeHelper;
	}

	public void setEmployeeHelper(LinkedList<String> employeeHelper) {
		this.employeeHelper = employeeHelper;
	}

	// Caches the Entities during the conversation
	  private Customer customerEntity;
	  
	  public Project getProject() {
		  return project;
	  }
	  
	  public Customer getCustomerEntity() {
		    if (customerEntity == null) {
		      // Load the entity from the database if not already cached
		    LOGGER.log(Level.INFO, "This is customerId from businessProcess: " + businessProcess.getVariable("customerId")); 
		    LOGGER.log(Level.INFO, "This is the same casted as Long: " + (Long) businessProcess.getVariable("customerId"));
		    LOGGER.log(Level.INFO, "This is getCustomer from the Service invoked with it " + customerService.getCustomer((Long) businessProcess.getVariable("customerId")));
		    customerEntity = customerService.getCustomer((Long) businessProcess.getVariable("customerId"));
		    }
		    return customerEntity;
	  }	 
	  
	  public void persist() throws IOException {		  
		  project.setEmployee(employeeHelper);
		  project = projectService.create(project);
		  businessProcess.setVariable("projectId", project.getId());
		  LOGGER.log(Level.INFO, "This is projectid in the business process: " + businessProcess.getVariable("projectId"));
		  
		  Project persistedProject = projectService.getProject(project.getId());
		  LOGGER.log(Level.INFO, "This is the persisted Project: ");
		  LOGGER.log(Level.INFO, " toString: "+ persistedProject.toString());
		  LOGGER.log(Level.INFO, " costEstimate: "+ persistedProject.getCostEstimate());
		  LOGGER.log(Level.INFO, " Title: "+ persistedProject.getTitle());
		  LOGGER.log(Level.INFO, " Employees: "+ persistedProject.getEmployee().toString());
		  LOGGER.log(Level.INFO, " Start Date: "+ persistedProject.getProjectStart());
		  LOGGER.log(Level.INFO, " End Date: "+ persistedProject.getProjectEnd());
		  LOGGER.log(Level.INFO, " Design: "+ persistedProject.isDesign());
		  
		  taskForm.completeTask();
	  }
}
