package org.camunda.bpm.iss.DTO.out;


public class DesignDTO {

	private long jobId;
	
	//A base64 encoded byte array, which represents a zip in binary format
	//Example: http://stackoverflow.com/questions/20706783/put-byte-array-to-json-and-vice-versa
	private byte[] designZIP;
	
	private String fileName;

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public byte[] getDesignZIP() {
		return designZIP;
	}

	public void setDesignZIP(byte[] designZIP) {
		this.designZIP = designZIP;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}

