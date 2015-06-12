package org.camunda.bpm.iss.entity;
import java.io.Serializable;

import javax.persistence.*;


@Entity
public class Deliverable implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	protected Project project;
	
	protected byte[] executableVersion;
	
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
	
	
}
