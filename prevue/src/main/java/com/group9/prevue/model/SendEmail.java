package com.group9.prevue.model;

import com.sun.mail.smtp.SMTPTransport;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
    private SendEmail() {
    }
    
	public static void Send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message) throws AddressException, MessagingException {
		
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);


        final MimeMessage mime = new MimeMessage(session);

        mime.setFrom(new InternetAddress(username + "@gmail.com"));
        mime.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

        if (ccEmail.length() > 0) {
        	mime.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }

        mime.setSubject(title);
        mime.setText(message, "utf-8");
        mime.setSentDate(new Date());

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

        t.connect("smtp.gmail.com", username, password);
        t.sendMessage(mime, mime.getAllRecipients());      
        t.close();
    }
    public static void main(String[] args) throws AddressException, MessagingException
    {
    	final String from = "prevuebooking";
    	final String pass = "P465565group9";
    	User user = new User();
    	String recipient = user.getEmail();
    	String cc = "";
    	String title = "One Time Password";
    	OneTimePassword otp = new OneTimePassword();
    	String OTP = otp.pass(6);
    	Send(from,pass,recipient,cc,title,OTP);
    }
}
