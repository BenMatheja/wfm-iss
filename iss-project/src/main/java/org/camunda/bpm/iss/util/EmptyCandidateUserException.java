package org.camunda.bpm.iss.util;

@SuppressWarnings("all")
public class EmptyCandidateUserException extends Exception{
	public EmptyCandidateUserException(String message){
		super(message);
	}
}
