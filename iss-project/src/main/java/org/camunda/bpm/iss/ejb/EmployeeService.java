package org.camunda.bpm.iss.ejb;

import java.util.Collection;
import java.util.List;
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
			
			for(User u:users){
				for(Employee e:employees){
					Employee employeeEntity = new Employee(u.getId(),u.getFirstName(),u.getLastName());
					if(u.getId()==e.getUserId()) employees.add(employeeEntity);
				    entityManager.persist(employeeEntity);
				    entityManager.flush();
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
