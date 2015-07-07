package org.camunda.bpm.iss.entity;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.servlet.http.Part;

@Entity
public class MeetingMinutes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
//	@OneToOne
//	protected Appointment appointment;
//	
//	protected Part meetingMinutes;
//	protected String meetingMinutesContent;
//	
	public Long getId(){
		return this.id;
	}
//	
//	public Appointment getAppointment() {
//		return appointment;
//	}
//	public void setAppointment(Appointment appointment) {
//		this.appointment = appointment;
//	}
//	public Part getMeetingMinutes() {
//		return meetingMinutes;
//	}
//	public void setMeetingMinutes(Part meetingMinutes) {
//		this.meetingMinutes = meetingMinutes;
//	}
//	
//	public void upload() {
//	    try {
//	    	meetingMinutesContent = new Scanner(meetingMinutes.getInputStream())
//	          .useDelimiter("\\A").next();
//	    } catch (IOException e) {
//	      // Error handling
//	    }
//	  }
//	 
	
}
