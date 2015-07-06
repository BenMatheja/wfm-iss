package org.camunda.bpm.iss.entity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Project implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany(fetch=FetchType.EAGER)
	protected Collection<Employee> employee;
		
	protected String title;
	// individualTime: is this ever used throughout the process?
	protected int[] individualTime;
	protected int[] individualRate;
	protected boolean design;
	protected boolean projectStatus;
	protected int costEstimate;
	protected Date projectStart;
	protected Date projectEnd;
	protected String statusReport;
	
	
	
	public String getStatusReport() {
		return statusReport;
	}

	public void setStatusReport(String statusReport) {
		this.statusReport = statusReport;
	}

	public int[] getIndividualRate() {
		return individualRate;
	}

	public void setIndividualRate(int[] individualRate) {
		this.individualRate = individualRate;
	}

	public Long getId() {
		return id;
	}

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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public boolean getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(boolean projectStatus) {
		this.projectStatus = projectStatus;
	}
	public int getCostEstimate() {
		return costEstimate;
	}
	public void setCostEstimate(int costEstimate) {
		this.costEstimate = costEstimate;
	}

	public Collection<Employee> getEmployee() {
		return employee;
	}

	public void setEmployee(Collection<Employee> employee) {
		this.employee = employee;
	}

	
}
