package org.camunda.bpm.iss.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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


@Stateless
@Named
public class EmployeeService {
	
	@PersistenceContext
	  private EntityManager entityManager;
	
	// Inject task form available through the camunda cdi artifact
		  @Inject
		  private TaskForm taskForm;
		  private Class<Employee> employeeClass;
		  
			 
		  public void persistEmployee(DelegateExecution delegateExecution) {
			IdentityService is = ProcessEngines.getDefaultProcessEngine().getIdentityService();
			List<User> users = is.createUserQuery().list();
			Collection<Employee> employees = getAllEmployees();
			
			ArrayList<String> employeeId = new ArrayList<String>(); 
			for(Employee e:employees) employeeId.add(e.getUserId());
			
			ArrayList<String> userId = new ArrayList<String>();
			for(User u:users) userId.add(u.getId());
			
			for(String uId:userId){
				if(!employeeId.contains(uId)){
					for(User u:users)
						if(u.getId()==uId){
							Employee employeeEnt = new Employee(uId,u.getFirstName(),u.getLastName());
							employees.add(employeeEnt);
							entityManager.persist(employeeEnt);
							entityManager.flush();
						}
				}
			}

		  }
		  
		  public Employee getEmployee(Long employeeId) {
			  // Load entity from database
			  return entityManager.find(Employee.class, employeeId);
		  }
		  
		  public Collection<Employee> getAllEmployees(){
			    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			    CriteriaQuery<Employee> cq = cb.createQuery(employeeClass);
			    Root<Employee> rootEntry = cq.from(employeeClass);
			    return entityManager.createQuery(cq.select(rootEntry)).getResultList();
		  }

}
