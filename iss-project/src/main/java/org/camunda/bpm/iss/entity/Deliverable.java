package org.camunda.bpm.iss.entity;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Deliverable implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	
	@ManyToOne
	protected Project project;
	
	@Lob
	protected byte[] executableVersion;
	protected String title;
	protected String description;
	protected String contentType;
	protected String fileName;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date timestamp = Calendar.getInstance().getTime();
	
	public long getId() {
		return id;
	}	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public byte[] getExecutableVersion() {
		return executableVersion;
	}
	public void setExecutableVersion(byte[] executableVersion) {
		this.executableVersion = executableVersion;
	}
	
	public void setFileContent(String contentType){
		this.contentType = contentType;
	}
	
	public String getFileContent(){
		return contentType;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public Date getTimestamp(){
		return timestamp;
	}
}