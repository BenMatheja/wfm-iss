package org.camunda.bpm.iss.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class BillIss implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
		
	@Lob
	private byte[] bill;
	
	private String comment;
			
	private int priceInCent;
	
	private int costEstimation;
	
	private String customerName;
	
	private String projectTitle;
	
	private String contractTitle;	
	
//	private Collection<Employee> employees;
	
	private Date projectStart;
	
	private Date projectEnd;
	
	private long accumulatedHours;
	
	private double pbTotal;
	
	private int issTotal;
	
		
	
	public long getId() {
		return id;
	}	

	public byte[] getBill() {
		return bill;
	}

	public void setBill(byte[] bill) {
		this.bill = bill;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getPriceInCent() {
		return priceInCent;
	}

	public void setPriceInCent(int priceInCent) {
		this.priceInCent = priceInCent;
	}

	public int getCostEstimation() {
		return costEstimation;
	}

	public void setCostEstimation(int costEstimation) {
		this.costEstimation = costEstimation;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getContractTitle() {
		return contractTitle;
	}

	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
	}

//	public Collection<Employee> getEmployees() {
//		return employees;
//	}
//
//	public void setEmployees(Collection<Employee> employees) {
//		this.employees = employees;
//	}

	public Date getProjectStart() {
		return projectStart;
	}

	public void setProjectStart(Date projectStart) {
		this.projectStart = projectStart;
	}

	public Date getProjectEnd() {
		return projectEnd;
	}

	public void setProjectEnd(Date projectEnd) {
		this.projectEnd = projectEnd;
	}

	public long getAccumulatedHours() {
		return accumulatedHours;
	}

	public void setAccumulatedHours(long accumulatedHours) {
		this.accumulatedHours = accumulatedHours;
	}

	public double getPbTotal() {
		return pbTotal;
	}

	public void setPbTotal(double pbTotal) {
		this.pbTotal = pbTotal;
	}

	public int getIssTotal() {
		return issTotal;
	}

	public void setIssTotal(int issTotal) {
		this.issTotal = issTotal;
	}
	
	
	
		
}
