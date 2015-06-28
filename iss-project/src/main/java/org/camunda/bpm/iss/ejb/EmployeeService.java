package org.camunda.bpm.iss.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.iss.entity.Employee;
import org.camunda.bpm.iss.web.EvaluateRequestController;


@Stateless
@Named
public class EmployeeService {
	
	@PersistenceContext
	  private EntityManager entityManager;
	
	// Inject task form available through the camunda cdi artifact
		  @Inject
		  private TaskForm taskForm;
		  //private Class<Employee> employeeClass;
		  
		  private static Logger LOGGER = Logger.getLogger(EvaluateRequestController.class.getName());
			 
		  public void persistEmployee(DelegateExecution delegateExecution) {
			LOGGER.log(Level.INFO, "persistEmployee started kr1");
			IdentityService is = ProcessEngines.getDefaultProcessEngine().getIdentityService();
			List<User> users = is.createUserQuery().list();
			LOGGER.log(Level.INFO, "Show first element of user list: "+users.get(0));
			Collection<Employee> employees = getAllEmployees();
			LOGGER.log(Level.INFO, "Is employees empty?: "+ employees.isEmpty());
			// LOGGER.log(Level.INFO, "Show first element of all employees:"+employees.iterator().next().getUserId());
			ArrayList<String> employeeId = new ArrayList<String>();
			ArrayList<String> userId = new ArrayList<String>();
			for(User u:users) userId.add(u.getId());
			LOGGER.log(Level.INFO, "List userId filled w/: "+userId.get(0));
			if (!employees.isEmpty()) {
				for(Employee e:employees) employeeId.add(e.getUserId());			
				for(String uId:userId){
					if(!employeeId.contains(uId)){
						for(User u:users)
							if(u.getId()==uId){
								Employee employeeEnt = new Employee();
								employeeEnt.setUserId(uId);
								employeeEnt.setFirstName(u.getFirstName());
								employeeEnt.setLastName(u.getLastName());
								employees.add(employeeEnt);
								LOGGER.log(Level.INFO, "EmployeeEnt filled w/: "+employeeEnt.getUserId());	
								entityManager.persist(employeeEnt);
								entityManager.flush();
							}
					}
				}
			} else {
				for(String uId:userId) {
					for(User u:users) {
						if(u.getId()==uId){
							Employee employeeEnt = new Employee();
							employeeEnt.setUserId(uId);
							employeeEnt.setFirstName(u.getFirstName());
							employeeEnt.setLastName(u.getLastName());
							employees.add(employeeEnt);
							LOGGER.log(Level.INFO, "EmployeeEnt filled w/: "+employeeEnt.getUserId());	
							entityManager.persist(employeeEnt);
							entityManager.flush();
						}
					}
				}
			}
		  }
		  
		  public Employee getEmployee(Long employeeId) {
			  // Load entity from database
			  return entityManager.find(Employee.class, employeeId);
		  }
		  
		  public Collection<Employee> getAllEmployees(){
			  	LOGGER.log(Level.INFO, "This is getAllEmployees");	
			    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			    LOGGER.log(Level.INFO, "criteriaBuilder: "+cb.toString());				    
				CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
				Root<Employee> rootEntry = cq.from(Employee.class);
				return entityManager.createQuery(cq.select(rootEntry)).getResultList();			    
			    
		  }

}
