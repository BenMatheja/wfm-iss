package org.camunda.bpm.iss.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import java.io.Serializable;

@Entity
public class Customer implements Serializable {
	 private static  final long serialVersionUID = 1L;
	 
	  @Id
	  @GeneratedValue
	  protected Long id;
	 
	  @Version
	  protected long version;
	 
	  // CustomerRequest Collection not implemented yet for simplicity!
	  
	  protected String name;
	  protected String address;
	  protected String eMail;
	  
	 
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
	 
	  public String getName() {
	    return name;
	  }
	 
	  public void setName (String name) {
	    this.name = name;
	  }
	 
	  public String getAddress() {
	    return address;
	  }
	 
	  public void setAddress(String address) {
	    this.address = address;
	  }	  
	  public String geteMail() {
			return eMail;
		}

		public void seteMail(String eMail) {
			this.eMail = eMail;
		}

	}
