package org.camunda.bpm.iss.ejb;

import java.util.Date;
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
import org.camunda.bpm.iss.entity.Project;

@Named
@Stateless
public class ProjectService {
	 @PersistenceContext
	  private EntityManager entityManager;
	  
	  // Inject task form available through the camunda cdi artifact
	  @Inject
	  private TaskForm taskForm;	  
	  	  
	  private static Logger LOGGER = Logger.getLogger(ProjectService.class.getName());
	 
	  public void persistProject(DelegateExecution delegateExecution) {
	   
		LOGGER.log(Level.INFO, "Create new project instance");  
		  // Create new customer instance
		Project projectEntity = new Project();
	 
	    LOGGER.log(Level.INFO, "Get all process variables");
	    // Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	 
	    LOGGER.log(Level.INFO, "Set order attributes");
	    // Set order attributes
	    projectEntity.setTitle((String) variables.get("title"));
	    //projectEntity.setIndividualTime(((Integer)variables.get("individualTime")).intValue());
	    projectEntity.setFinished(false);
	    projectEntity.setDesign(((Boolean)variables.get("design")).booleanValue());
	    projectEntity.setCostEstimate(((Integer)variables.get("costEstimate")).intValue());
	    projectEntity.setProjectStart((Date) variables.get("projectStart"));
	    projectEntity.setProjectEnd((Date) variables.get("projectEnd"));
	    projectEntity.setStatusReport((String) variables.get("projectStatusreport"));
	    
	    entityManager.persist(projectEntity);
	    entityManager.flush();
	 
	    // Remove no longer needed process variables
	    // delegateExecution.removeVariables(variables.keySet());
	 
	    LOGGER.log(Level.INFO, "Add newly created project id as process variable. It is:" + projectEntity.getId());
	    // Add newly created customer id as process variable
	    delegateExecution.setVariable("projectId", projectEntity.getId());
	  }

	  public Project getProject(Long projectId) {
		  // Load entity from database
		  return entityManager.find(Project.class, projectId);
	  }
	  
	  public void addEmployeesToProject(Long id){
		  
	  }
	  
}
