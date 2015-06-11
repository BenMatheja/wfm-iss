package org.camunda.bpm.iss.entity;
import java.io.Serializable;

import javax.persistence.*;

@Entity
public class CustomerBill implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne
	protected Project project;
	
	protected int cost;
	protected boolean received;
	protected boolean dunned;
	
	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public boolean isReceived() {
		return received;
	}
	public void setReceived(boolean received) {
		this.received = received;
	}
	public boolean isDunned() {
		return dunned;
	}
	public void setDunned(boolean dunned) {
		this.dunned = dunned;
	}
	
	
}
