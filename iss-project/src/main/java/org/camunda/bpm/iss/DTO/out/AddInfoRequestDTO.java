package org.camunda.bpm.iss.DTO.out;

public class AddInfoRequestDTO {
	
	private long addtitionalInfoId;
	
	private String question;

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

}
