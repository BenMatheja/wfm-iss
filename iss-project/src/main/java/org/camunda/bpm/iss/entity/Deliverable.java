package org.camunda.bpm.iss.entity;
import javax.persistence.*;

public class Deliverable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
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
