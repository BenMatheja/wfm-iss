package org.camunda.bpm.iss.ejb;

import java.util.logging.Logger;
import java.util.logging.Level;

import javax.annotation.ManagedBean;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;

@ManagedBean
@Named
public class DummyService {

	
	public void doNothing(DelegateExecution delegateExecution){
		Logger.getLogger("logblub").info("Dummy Method, did nothing, don't worry ;)");
		
	}
}
