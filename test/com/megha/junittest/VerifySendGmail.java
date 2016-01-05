package com.megha.junittest;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.UIDFolder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.megha.junit.*;

@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class VerifySendGmail {
	
	String Subject;
	String bodyContent;
    String FromEmail;
    String FromEmail_pwd;
    String ToEmail;
    String ToEmail_pwd;
	SearchGmail searchEmails;
	SendGmail mail;
	long startUID;
    long endUID;
    String emailFolder = "INBOX";
    final Logger logger = LoggerFactory.getLogger(VerifySendGmail.class);
    
    public VerifySendGmail(String Subject,String bodyContent, String FromEmail,String FromEmail_pwd,String ToEmail, String ToEmail_pwd){
    	this.Subject = Subject;
    	this.bodyContent = bodyContent;
    	this.FromEmail = FromEmail;
    	this.FromEmail_pwd = FromEmail_pwd;
    	this.ToEmail = ToEmail;
    	this.ToEmail_pwd = ToEmail_pwd;
    }
    
    @Rule 
    public TestRule watcher = new TestWatcher() {
    	   protected void starting(Description description) {
    		  logger.info("{} being run...", description.getMethodName());
    	   }
    	};

    
	@BeforeClass
	public static void beforeClass(){
	System.out.println("Executing tests in TestSendMail class.");
	}
	
	
	@Before
	public void setUp(){
		System.out.println("Executing before each test method.");
		//Create SendMail Object
			mail = new SendGmail(FromEmail,ToEmail,Subject,bodyContent,FromEmail_pwd);
		//Create SearchEmailForIMAPServer Object
		    searchEmails = new SearchGmail(FromEmail,ToEmail,Subject,bodyContent,ToEmail_pwd);
	}
	
	@Parameterized.Parameters
	public static Collection emailParameters(){
		return Arrays.asList(new Object[][]{
				{"JavaMailTest-1 Subject","JavaMailTest-1 Body","shah.iyer@googlemail.com","VedAni1982*","connecteduqa@gmail.com","experience"}
			    			
		});
	}
	
	@Test(timeout=120000)
	public void testSendingAndReceivingGmail() {
	
	try {
		//Get the next message UID before sending an email
		startUID = searchEmails.getNextUID(emailFolder);
	    
		//Send an email
		mail.send();
		System.out.println("Sent successfully!");
		
		//Pause for 10 seconds for mail to be received
		Thread.sleep(10000);
		
	    //Search for the sent email using the email folder name, Subject, From email address and email body content.
    
	    Message[] msgs2  = searchEmails.SearchEmailsForSendEmailClass(emailFolder);
		//System.out.println("startUID is " + startUID);
	    for(Message message : msgs2)
	       {
			boolean seen_flag = message.isSet(Flags.Flag.SEEN);
			UIDFolder uf =  (UIDFolder) message.getFolder();
			long messageUID = uf.getUID(message);
			//System.out.println("msgUID is " + msgUID);
			    
            if(messageUID > (startUID-1)){
               if(seen_flag == false){
            	 //System.out.println("Entered not seen flag loop ");
            	 if(message.getSubject().equals(Subject)){
            		//System.out.println("Entered Subject matching loop " );
            		 if (message.getContent().toString().contains(bodyContent)){
            			 if(message.getFrom()[0].toString().equals(FromEmail)){
            			 //logger.info("MessageID: " + messageUID);
            			 //System.out.println("Date Received:"+ message.getReceivedDate().toString());
            			 logger.info("Email from {} received by {}", FromEmail,ToEmail);
            			 logger.info("Validated subject {} and body {}.",Subject,bodyContent);
            			 assertEquals(Subject,message.getSubject());
            			 assertEquals(false,message.isSet(Flags.Flag.SEEN));
            			 assertEquals(FromEmail,message.getFrom()[0].toString());
            			 break;	
            		   }
					}
				 }
			  }
            }
            else
		       continue;
	        }
	
	        searchEmails.closeFolder();
	        searchEmails.closeStore();
	
}catch(MessagingException e){
	    e.printStackTrace();
}catch(InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}catch(Exception e){
	e.printStackTrace();
}
}
	@Test(timeout=1000)
	public void testPrintParameters(){
		System.out.println("Subject:"+Subject);
		System.out.println("FromEmail"+FromEmail);
		System.out.println("Message Text:"+bodyContent);
		System.out.println("ToEmail:"+ToEmail);
		
	}
		
	@After
    public void tearDown()
    {
    	System.out.println("Executed after each method.");
    	    	
    }
	
	@AfterClass
    public static void AfterClass()
    {
    	System.out.println("Finished executing tests in TestSendMail class.");
    	
    }
	
}

	
	

