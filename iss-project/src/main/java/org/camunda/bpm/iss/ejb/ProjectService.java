package org.camunda.bpm.iss.ejb;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.entity.Project;

@Named
@Stateless
public class ProjectService {
	 @PersistenceContext
	  private EntityManager entityManager;
	  
	  @Inject
		CustomerService customerService;
	  
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
	  
	  public void addEmployeesToProject(Long id){
		  
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
