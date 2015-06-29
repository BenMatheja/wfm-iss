package org.camunda.bpm.iss.DTO.in;

import org.camunda.bpm.iss.entity.util.ArtType;

public class DesignRequestDTO {
	
	private String DesignTitle;
	/**
	 * Implementation ov enum ArtType
	 * public enum ArtType {
			website, logo, flyer, slogon
		}
	 */
	private ArtType artType;
	private String additionalInformation;
	
	
	public String getDesignTitle() {
		return DesignTitle;
	}

	public void setDesignTitle(String designTitle) {
		DesignTitle = designTitle;
	}

	public ArtType getArtType() {
		return artType;
	}

	public void setArtType(ArtType artType) {
		this.artType = artType;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
}
