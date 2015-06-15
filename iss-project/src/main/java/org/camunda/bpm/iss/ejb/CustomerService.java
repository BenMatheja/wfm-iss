package org.camunda.bpm.iss.ejb;

import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.cdi.jsf.TaskForm;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.iss.entity.Customer;
import org.camunda.bpm.iss.ejb.CustomerRequestService;

@Stateless
@Named
public class CustomerService {

	 @PersistenceContext
	  private EntityManager entityManager;
	  
	  // Inject task form available through the camunda cdi artifact
	  @Inject
	  private TaskForm taskForm;	  
	  	  
	  private static Logger LOGGER = Logger.getLogger(CustomerService.class.getName());
	 
	  public void persistCustomer(DelegateExecution delegateExecution) {
	   
		LOGGER.log(Level.INFO, "Create new customer instance");  
		  // Create new customer instance
	    Customer customerEntity = new Customer();
	 
	    LOGGER.log(Level.INFO, "Get all process variables");
	    // Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	 
	    LOGGER.log(Level.INFO, "Set order attributes");
	    // Set order attributes
	    customerEntity.setName((String) variables.get("name"));
	    customerEntity.setAddress((String) variables.get("address"));
	    customerEntity.setEmail((String) variables.get("email"));
	 
	    /*
	      Persist customer instance and flush. After the flush the
	      id of the customer instance is set.
	    */
	    LOGGER.log(Level.INFO, " Persist customer instance and flush.");
	    
	    entityManager.persist(customerEntity);
	    entityManager.flush();
	 
	    // Remove no longer needed process variables
	    // delegateExecution.removeVariables(variables.keySet());
	 
	    LOGGER.log(Level.INFO, "Add newly created customer id as process variable. It is:" + customerEntity.getId());
	    // Add newly created customer id as process variable
	    delegateExecution.setVariable("customerId", customerEntity.getId());
	  }

	  public Customer getCustomer(Long customerId) {
		  // Load entity from database
		  return entityManager.find(Customer.class, customerId);
	  }
	  
	  public void mailCustomer(DelegateExecution delegateExecution) {
		  	Customer customer = getCustomer((Long) delegateExecution.getVariable("customerId"));		  	
		    String mailtext = (String) delegateExecution.getVariable("mailtext");		  	
		  			  
		  	LOGGER.log(Level.INFO, "\n\n\nSending Email:\nDear {0}, {1} \n\n\n", new String[]{customer.getName(), mailtext});
		    final String username = "contactISSgroup@gmail.com";
			final String password = "verysecurepw";
		 
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
		 
				Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		 
				try {
		 
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("contactISSgroup@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(customer.getEmail()));
				message.setSubject("Testing Subject");
				message.setText("Dear "+customer.getName()+"," 
					+mailtext
					+ ""
					+ "Best regards, ISS");
		 
					Transport.send(message);
		 
					System.out.println("Done");
		 
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}	  
	  }
}