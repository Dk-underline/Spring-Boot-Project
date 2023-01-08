package com.smart.service;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	public void sendPlainTextEmail(final String userName, final String port, String toAddress, String subject,
			String message) throws AddressException, MessagingException {
		String host = "smpt.gmail.com";
		final String password = "wkcnzrrbqgwbgsoq";
		// sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		// *** BEGIN CHANGE
		properties.put("mail.smtp.user", userName);

		// creates a new session, no Authenticator (will connect() later)
		Session session = Session.getDefaultInstance(properties);
		// *** END CHANGE

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		// set plain text message
		msg.setText(message);

		// *** BEGIN CHANGE
		// sends the e-mail
		Transport t = session.getTransport("smtp");
		t.connect(userName, password);
		t.sendMessage(msg, msg.getAllRecipients());
		t.close();
		// *** END CHANGE
	}

	public boolean sendmail(String from, String to, String subject, String body) {
		System.out.println("Succesfull");
		// Variable for Email
		boolean flag = false;
		String host = "smpt.gmail.com";
//        String host = "smtp.googlemail.com";
		// Get the System properties
		Properties properties = System.getProperties();
//		System.out.println(properties);
		//
		properties.put("mail.smtp.host", host);
//		properties.put("mail.smtp.user", from);
//		properties.put("mail.smtp.password", "wkcnzrrbqgwbgsoq");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Step 1 : Get the Session Object

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("rj.dikshantnagar@gmail.com", "dik007@iitbhu");
			}

		});
		session.setDebug(true);
		// Step 2 : Compose the message
		MimeMessage message = new MimeMessage(session);
		try {
			// Set from
			message.setFrom(from);
			// Set to
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject
			message.setSubject(subject);
			// Set body
			message.setText(body);
			// Send Mail
			Transport.send(message);
			return !flag;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
	}

	public static void sendmailwithattachment(String from, String to, String subject, String body) {
		System.out.println("Succesfull");
		// Variable for Email
		String host = "smpt.gmail.com";

		// Get the System properties
		Properties properties = System.getProperties();
		System.out.println(properties);
		//
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Step 1 : Get the Session Object

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("your mail id", "*******");
			}

		});
		session.setDebug(true);
		// Step 2 : Compose the message
		MimeMessage message = new MimeMessage(session);
		try {
			// Set from
			message.setFrom(from);
			// Set to
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject
			message.setSubject(subject);
			// attachment
			// file path
			String path = "file path";
			MimeMultipart multimessage = new MimeMultipart();
			// text
			MimeBodyPart textBodyPart = new MimeBodyPart();
			// file
			MimeBodyPart filebodyPart = new MimeBodyPart();
			try {
				textBodyPart.setText(body);
				File file = new File(path);
				filebodyPart.attachFile(file);
				multimessage.addBodyPart(textBodyPart);
				multimessage.addBodyPart(filebodyPart);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Set body
			message.setContent(multimessage);
			// Send Mail
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
