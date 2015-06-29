package org.camunda.bpm.iss.DTO.in;

public class AddInfoResponseDTO {
	
	private long addtitionalInfoId;
	
	private String answer;

	public long getAddtitionalInfoId() {
		return addtitionalInfoId;
	}

	public void setAddtitionalInfoId(long addtitionalInfoId) {
		this.addtitionalInfoId = addtitionalInfoId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
