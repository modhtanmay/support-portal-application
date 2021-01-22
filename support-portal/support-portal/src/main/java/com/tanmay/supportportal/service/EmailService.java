package com.tanmay.supportportal.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.sun.mail.smtp.SMTPTransport;
import com.tanmay.supportportal.constant.EmailConstant;

@Service
public class EmailService {

	public void sendNewPasswordEmail(String firstName,String password,String email) throws MessagingException {
		Message message = createEmail(firstName,password,email);
		SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(EmailConstant.SIMPLE_MAIL_TRANSFER_PROTOCOL);
		smtpTransport.connect(EmailConstant.GMAIL_SMTP_SERVER,EmailConstant.USERNAME,EmailConstant.PASSWORD);
		smtpTransport.sendMessage(message, message.getAllRecipients());
		smtpTransport.close();
	}
	
	private Message createEmail(String firstName, String password, String email) throws  MessagingException {
		Message message = new MimeMessage(getEmailSession());					// call EmailSession
		message.setFrom(new InternetAddress(EmailConstant.FROM_EMAIL));			// From email
		message.setRecipients(RecipientType.TO, InternetAddress.parse(email,false));	// toEmail
		message.setRecipients(RecipientType.CC, InternetAddress.parse(EmailConstant.CC_EMAIL, false));	// ccEmail
		message.setSubject(EmailConstant.EMAIL_SUBJECT);							// Email Subject
		message.setText("Hello "+firstName+",\n \n Your new account password is: "+password+"\n \n The Support Team");		// Subject content
		message.setSentDate(new Date());		// set Date
		message.saveChanges();					// save changes
		return message;							// return message
	}

	private Session getEmailSession() {
		Properties properties = System.getProperties();
		properties.put(EmailConstant.SMTP_HOST, EmailConstant.GMAIL_SMTP_SERVER);
		properties.put(EmailConstant.SMTP_AUTH, true);
		properties.put(EmailConstant.SMTP_PORT, EmailConstant.DEFAULT_PORT);
		properties.put(EmailConstant.SMTP_STARTTLS_ENABLE, true);
		properties.put(EmailConstant.SMTP_STARTTLS_REQUIRED, true);
		return Session.getInstance(properties, null);
	}
}
