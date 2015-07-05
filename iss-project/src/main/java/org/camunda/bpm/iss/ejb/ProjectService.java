package org.camunda.bpm.iss.ejb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.iss.entity.Employee;
import org.camunda.bpm.iss.entity.Project;

@Named
@Stateless
public class ProjectService {
	 @PersistenceContext
	  private EntityManager entityManager;
	  
	  @Inject
		CustomerService customerService;
	  
	  @Inject
		EmployeeService employeeService;
	  
	  @Inject
	  private BusinessProcess businessProcess;
		
		  	  
	  private static Logger LOGGER = Logger.getLogger(ProjectService.class.getName());
	 
	  public Project create (Project project) {	   
		entityManager.persist(project);
		// entityManager.flush();
		return project;	 
	  }

	  public Project getProject(Long projectId) {
		  // Load entity from database
		  return entityManager.find(Project.class, projectId);
	  }
	  
	  public void addEmployeesToProject(Project project, List<Long> employeeIds){
		  List<Employee> employeeList = new ArrayList<Employee>();
		  LOGGER.log(Level.INFO, "This is addEmployeesToProject. The first employee id is: "+employeeIds.get(0));
		  for (Long e:employeeIds) {
			  for (Employee emp:employeeService.getAllEmployees()) {
				  LOGGER.log(Level.INFO, "The employee-user ids are: "+emp.getId());
				  if (e == emp.getId().longValue()) {
					  employeeList.add(emp);
				  }
			  }
		  }
		  LOGGER.log(Level.INFO, "Finally, the first element of employeeList is: "+employeeList.get(0));
		  project.setEmployee(employeeList);
	  }
	  
	  public void updateProject(Long projectId) throws IOException {
		  Project project = getProject(projectId);		 
		  Boolean projectStatus = businessProcess.getVariable("projectStatus");
		  project.setProjectStatus(projectStatus);
		  LOGGER.log(Level.INFO, "This is updateProject for project: "+project.toString());
		  LOGGER.log(Level.INFO, "The project status now is: "+project.getProjectStatus());
		  entityManager.merge(project);
	  } 
	  
}
