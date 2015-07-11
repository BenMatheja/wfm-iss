package org.camunda.bpm.iss.DTO.out;

public class AddInfoRequestDTO {
	
	private long addtitionalInfoId;
	
	private String question;
	
	private long jobId;

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

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}
	
	

}
