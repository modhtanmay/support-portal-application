package com.tanmay.supportportal.service;

import java.util.Properties;

import javax.mail.Session;

import org.springframework.stereotype.Service;

import com.tanmay.supportportal.constant.EmailConstant;

@Service
public class EmailService {
	
	private Session getEmailSession() {
		Properties properties = System.getProperties();
		properties.put(EmailConstant.SMTP_HOST, EmailConstant.GMAIL_SMTP_SERVER);
		properties.put(EmailConstant.SMTP_AUTH, true);
		properties.put(EmailConstant.SMTP_PORT, EmailConstant.DEFAULT_PORT);
		properties.put(EmailConstant.SMTP_STARTTLS_ENABLE, true);
		properties.put(EmailConstant.SMTP_STARTTLS_REQUIRED, true);
		return Session.getInstance(properties,null);
	}
}
