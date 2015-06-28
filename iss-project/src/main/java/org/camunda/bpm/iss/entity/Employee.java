package org.camunda.bpm.iss.entity;
import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Employee implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	protected String userId;
	protected String firstName;
	protected String lastName;
	
//	public Employee(String userId, String firstName, String lastName){
//		setUserId(userId);
//		setFirstName(firstName);
//		setLastName(lastName);
//	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getUserId(){
		 return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
}
	
