package com.volkmer.godinho.core.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Email {

	public void sendEmail() throws EmailException {

		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("rafael.volkmer1@gmail.com", "xxxx"));
		email.setSSLOnConnect(true);
		email.setFrom("rafael.volkmer1@gmail.com");
		email.setSubject("TestMail");
		email.setMsg("This is a test mail 2 ... :-)");
		email.addTo("rafael.volkmer1@gmail.com");
		email.send();

		//https://myaccount.google.com/lesssecureapps?pli=1
		
	}

}
