package org.camunda.bpm.iss.DTO.out;

public class BillDTO {

	private long jobId;
	
	//A base64 encoded byte[], which represents an pdf-file in binary format
	//See: DesigntDTO.java
	private byte[] bill;
		
	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public byte[] getBill() {
		return bill;
	}

	public void setBill(byte[] bill) {
		this.bill = bill;
	}

}
