package org.camunda.bpm.iss.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class StatusUpdate implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String message;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	public long getId(){
		return id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate(){
		return date;
	}
	
}
