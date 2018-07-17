package com.volkmer.godinho.core.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Email {

	public void sendEmail(String email, String senha, Integer porta, String host, String titulo, String mensagem, String emaildestino) throws EmailException {

		System.out.println("Início");
		
		SimpleEmail envioemail = new SimpleEmail();
		envioemail.setHostName(host);
		envioemail.setSmtpPort(porta);
		envioemail.setAuthenticator(new DefaultAuthenticator(email, ""+senha));
		envioemail.setSSLOnConnect(true);
		envioemail.setFrom(email);
		envioemail.setSubject(titulo);
		envioemail.setMsg(mensagem);
		envioemail.addTo(emaildestino);
		envioemail.send();
		 
		/*
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("idsindicadores@gmail.com", "XXX"));
		email.setSSLOnConnect(true);
		email.setFrom("idsindicadores@gmail.com");
		email.setSubject("TestMail");
		email.setMsg("Teste de envio de E-mail");
		email.addTo("rafael.volkmer1@gmail.com");
		email.send();
		 */
		System.out.println("Fim");
		
	}
	
}
