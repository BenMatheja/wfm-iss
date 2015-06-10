package org.camunda.bpm.iss.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import java.io.Serializable;

public class CustomerRequest implements Serializable {

	private static  final long serialVersionUID = 1L;
	 
	  @Id
	  @GeneratedValue
	  protected Long id;
	 
	  @Version
	  protected long version;
	 
	  protected String title;
	  protected String text;
	  
	  @ManyToOne
	  protected Customer customer;
	 	 
	  public Long getId() {
	    return id;
	  }
	 
	  public void setId(Long id) {
	    this.id = id;
	  }
	 
	  public long getVersion() {
	    return version;
	  }
	 
	  public void setVersion(long version) {
	    this.version = version;
	  }
	 
	  public String getTitle() {
	    return title;
	  }
	 
	  public void setTitle(String title) {
	    this.title = title;
	  }
	 
	  public String getText() {
	    return text;
	  }
	 
	  public void setText(String text) {
	    this.text = text;
	  }
	 
	  public Customer getCustomer() {
	    return customer;
	  }
	 
	  public void setCustomer(Customer customer) {
	    this.customer = customer;
	  }	 

	}

