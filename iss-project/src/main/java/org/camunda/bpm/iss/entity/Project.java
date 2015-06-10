package org.camunda.bpm.iss.entity;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	protected String title;
	protected Collection<Employee> employee;
	//is this ever used throughout the process?
	protected int[] individualTime;
	protected boolean design;
	protected boolean finished;
	protected int costEstimate;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Collection<Employee> getEmployee() {
		return employee;
	}
	public void setEmployee(Collection<Employee> employee) {
		this.employee = employee;
	}
	public int[] getIndividualTime() {
		return individualTime;
	}
	public void setIndividualTime(int[] individualTime) {
		this.individualTime = individualTime;
	}
	public boolean isDesign() {
		return design;
	}
	public void setDesign(boolean design) {
		this.design = design;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public int getCostEstimate() {
		return costEstimate;
	}
	public void setCostEstimate(int costEstimate) {
		this.costEstimate = costEstimate;
	}
	
}
