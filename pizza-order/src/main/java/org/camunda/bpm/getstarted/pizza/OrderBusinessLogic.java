package org.camunda.bpm.getstarted.pizza;
 
import java.io.IOException;
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
 
@Stateless
@Named
public class OrderBusinessLogic {
 
  // Inject the entity manager
  @PersistenceContext
  private EntityManager entityManager;
  
  // Inject task form available through the camunda cdi artifact
  @Inject
  private TaskForm taskForm;
  
  private static Logger LOGGER = Logger.getLogger(OrderBusinessLogic.class.getName());
 
  public void persistOrder(DelegateExecution delegateExecution) {
    // Create new order instance
    OrderEntity orderEntity = new OrderEntity();
 
    // Get all process variables
    Map<String, Object> variables = delegateExecution.getVariables();
 
    // Set order attributes
    orderEntity.setCustomer((String) variables.get("customer"));
    orderEntity.setAddress((String) variables.get("address"));
    orderEntity.setPizza((String) variables.get("pizza"));
 
    /*
      Persist order instance and flush. After the flush the
      id of the order instance is set.
    */
    entityManager.persist(orderEntity);
    entityManager.flush();
 
    // Remove no longer needed process variables
    delegateExecution.removeVariables(variables.keySet());
 
    // Add newly created order id as process variable
    delegateExecution.setVariable("orderId", orderEntity.getId());
  }
  
  public OrderEntity getOrder(Long orderId) {
	    // Load order entity from database
	    return entityManager.find(OrderEntity.class, orderId);
	  }
	 
	  /*
	    Merge updated order entity and complete task form in one transaction. This ensures
	    that both changes will rollback if an error occurs during transaction.
	   */
  public void mergeOrderAndCompleteTask(OrderEntity orderEntity) {
	   // Merge detached order entity with current persisted state
	   entityManager.merge(orderEntity);
	   try {
	      // Complete user task from
	      taskForm.completeTask();
	   } catch (IOException e) {
	      // Rollback both transactions on error
	      throw new RuntimeException("Cannot complete task", e);
	   }
  }
 
  public void rejectOrder(DelegateExecution delegateExecution) {
	    OrderEntity order = getOrder((Long) delegateExecution.getVariable("orderId"));
	    LOGGER.log(Level.INFO, "\n\n\nSending Email:\nDear {0}, your order {1} of a {2} pizza has been rejected.\n\n\n", new String[]{order.getCustomer(), String.valueOf(order.getId()), order.getPizza()});
	    final String username = "contactISSgroup@gmail.com";
		final String password = "verysecurepw";
	 
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
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
				InternetAddress.parse("Marlene.Beyer@gmx.de"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");
	 
				Transport.send(message);
	 
				System.out.println("Done");
	 
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}	  
  }
}
