package com.megha.junit;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


public class SendGmail {
	private String FromEmail;
	private String ToEmail;
    private String Subject;
    private String Msg;
    private Session session;
    private String host;
    private String portnum;
    private String uname;
    private String pwd;
        
  	
	public SendGmail(String from, String to, String sub, String msg, String pwd){
		this.FromEmail = from;
		this.ToEmail = to;
		this.Subject = sub;
		this.Msg = msg;
		this.host = "smtp.gmail.com";
		this.portnum= "587";
		this.uname = "shah.iyer@gmail.com";
		this.pwd=pwd;
	}
	
	public void send(){
		
		//Configure host and smtp properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.port",portnum);
		props.put("mail.smtp.host", host); //SMTP Host
		props.put("mail.smtp.starttls.enable", "true");
	    //props.put("mail.smtp.socketFactory.port", portnum); //SSL Port
	    //props.put("mail.smtp.socketFactory.class",
	       //         "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
	    
        
       
	    //Get the Session object instance
	   
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(uname, pwd);
		   }
	         });
	    
	try {
			   // Create a default MimeMessage object.
			   Message message = new MimeMessage(session);
			
			   // Set From: header field of the header.
			   message.setFrom(new InternetAddress(FromEmail));
			
			   // Set To: header field of the header.
			   message.setRecipients(Message.RecipientType.TO,
		               InternetAddress.parse(ToEmail));
			
			   // Set Subject: header field
			   message.setSubject(Subject);
			
			   // Now set the actual message
			   message.setText(Msg);

			   // Send message
			   Transport.send(message);
 
			  System.out.println("Sending to: "+ FromEmail);
			   
			   } catch (MessagingException e) {
		         throw new RuntimeException(e);
		      }
		
	}
	
}
