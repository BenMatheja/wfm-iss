package org.camunda.bpm.iss.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Bill implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	
	private long jobId;
	
	//A base64 encoded byte[], which represents an pdf-file in binary format
	//See: DesigntDTO.java
	@Lob
	private byte[] bill;
		
	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public byte[] getBill() {
		return bill;
	}

	public void setBill(byte[] bill) {
		this.bill = bill;
	}
}
