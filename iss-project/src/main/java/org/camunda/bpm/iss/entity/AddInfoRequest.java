package org.camunda.bpm.iss.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AddInfoRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	
	private long addtitionalInfoId;

	private String question;
	private boolean available;

	public long getId() {
		return id;
	}

	public long getAddtitionalInfoId() {
		return addtitionalInfoId;
	}

	public void setAddtitionalInfoId(long addtitionalInfoId) {
		this.addtitionalInfoId = addtitionalInfoId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	
	
	

}
