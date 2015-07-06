package org.camunda.bpm.iss.entity;
import java.io.Serializable;

import javax.persistence.*;

@Entity
public class MeetingMinutes implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne
	protected Appointment appointment;
	
	protected byte[] meetingMinutes;
	
	public Long getId(){
		return this.id;
	}
	
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	public byte[] getMeetingMinutes() {
		return meetingMinutes;
	}
	public void setMeetingMinutes(byte[] meetingMinutes) {
		this.meetingMinutes = meetingMinutes;
	}
	
	
}
