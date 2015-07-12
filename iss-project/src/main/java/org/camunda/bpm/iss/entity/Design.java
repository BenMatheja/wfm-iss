package org.camunda.bpm.iss.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Design implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	
	private long jobId;
	
	//A base64 encoded byte array, which represents a zip in binary format
	//Example: http://stackoverflow.com/questions/20706783/put-byte-array-to-json-and-vice-versa
	@Lob
	private byte[] designZIP;
	
	private String fileName;
	
	private boolean approved;
	
	
	public long getId() {
		return id;
	}

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public byte[] getDesignZIP() {
		return designZIP;
	}

	public void setDesignZIP(byte[] designZIP) {
		this.designZIP = designZIP;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
}
