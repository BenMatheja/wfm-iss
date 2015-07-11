package org.camunda.bpm.iss.DTO.out;

public class AddInfoRequestDTO {
	
	private long addtitionalInfoId;
	
	private String question;
	
	private long designJobId;

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

	public long getDesignJobId() {
		return designJobId;
	}

	public void setDesignJobId(long designJobId) {
		this.designJobId = designJobId;
	}


}
