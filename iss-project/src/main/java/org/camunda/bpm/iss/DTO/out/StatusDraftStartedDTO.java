package org.camunda.bpm.iss.DTO.out;

public class StatusDraftStartedDTO {

	private long jobId;
	
	//optional textual description
	private String message;

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
