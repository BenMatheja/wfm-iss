package org.camunda.bpm.iss.entity;
import javax.persistence.*;

@Entity
public class MeetingMinutes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	protected Appointment appointment;
	protected byte[] meetingMinutes;
	
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
