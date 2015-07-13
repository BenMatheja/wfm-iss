package org.camunda.bpm.iss.entity;

import java.io.Serializable;

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
	
	String comment;
			
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
		
}
