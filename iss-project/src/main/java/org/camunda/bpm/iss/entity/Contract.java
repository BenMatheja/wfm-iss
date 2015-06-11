package org.camunda.bpm.iss.entity;
import java.io.Serializable;

import javax.persistence.*;

import org.camunda.bpm.iss.entity.Project;

@Entity
public class Contract implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	protected Project project;
	
	@OneToOne
	protected Customer customer;
	
	protected String contractTitle;	
	protected int price;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getContractTitle() {
		return contractTitle;
	}
	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
