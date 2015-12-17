package com.megha.junit;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.DateTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import javax.mail.search.SubjectTerm;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.IMAPFolder.FetchProfileItem;
import com.sun.mail.imap.SortTerm;

@SuppressWarnings("unused")
public class SearchGmail {
	
	private String Subject;
	private String emailFolder;
	private String FromEmail;
	private String ToEmail;
	private String ToEmail_pwd;
	private Properties props;
	private Store store;
	private Message[] msgs;
	private Folder ef;
	private SubjectTerm st ;
	
	
	public SearchGmail(String FromEmail, String ToEmail, String Subject, String bodyContent, String ToEmail_pwd)
	{
		
				
		//configure properties		
		props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		Session emailSession = Session.getDefaultInstance(props,null);
	  
	    try {
			store = emailSession.getStore("imaps");
		    store.connect("imap.gmail.com",ToEmail,ToEmail_pwd);
		}catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (MessagingException e) {			
			e.printStackTrace();
		}
    }
	
	public void closeStore() throws MessagingException{
		
		store.close();
		
	}
    
    public void closeFolder() throws MessagingException{
    	
    	ef.close(false);
		
	}
	
	
	public long getNextUID(String emailFolder) throws MessagingException
	{
		 IMAPFolder ef = (IMAPFolder) store.getFolder(emailFolder);
        //System.out.println("NextUID: "+ef.getUIDNext());
        return ef.getUIDNext();
	}
	
	public Message[] SearchEmailsBySubject(String emailFolder) throws MessagingException
	{
		this.emailFolder = emailFolder;
					
		try {
		    ef = (IMAPFolder) store.getFolder(emailFolder);
	        ef.open(Folder.READ_ONLY);
	        st = new SubjectTerm(Subject);
	        msgs = (IMAPMessage[]) ef.search(st);
        }catch (MessagingException e) {
		    e.printStackTrace();
		}
		return msgs;
	}
	
	
	public Message[] SearchEmailsByUID(long startUID, String emailFolder)
	{
		this.emailFolder = emailFolder;
		long endUID;
		
		try {
			IMAPFolder ef = (IMAPFolder) store.getFolder(emailFolder);
			endUID = ef.getUIDNext();
	        ef.open(Folder.READ_ONLY);
	        msgs =(IMAPMessage[]) ef.getMessagesByUID(startUID-1, endUID);
	    }catch (MessagingException e) {
		    e.printStackTrace();
		}
		return msgs;
	}
	
	
	public Message[] SearchEmailsForSendEmailClass(String emailFolder) throws MessagingException
	{
		 this.emailFolder = emailFolder;
		 ef = store.getFolder(emailFolder);
		 ef.open(Folder.READ_ONLY);
		 Message[] msgs = ef.getMessages();
		 FetchProfile fp = new FetchProfile();
		 fp.add(FetchProfile.Item.FLAGS);
		 fp.add(FetchProfile.Item.CONTENT_INFO);
		 fp.add(FetchProfile.Item.ENVELOPE);
		 fp.add(UIDFolder.FetchProfileItem.UID);
		 ef.fetch(msgs, fp);
		 return msgs;
		 		 		 
		 /*FlagTerm UnSeenFlagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
		 FlagTerm RecentFlagTerm = new FlagTerm(new Flags(Flags.Flag.RECENT), true);
		 AndTerm unseenAndRecentTerm = new AndTerm(UnSeenFlagTerm, RecentFlagTerm);
		// FromStringTerm fromTerm = new FromStringTerm("shah.iyer@gmail.com");
		 //SentDateTerm sentDateTerm = new SentDateTerm(ComparisonTerm.EQ, new java.util.Date());
		// AndTerm FromAndDateTerm = new AndTerm(fromTerm, sentDateTerm);
		// AndTerm FromAndDateunseenAndRecentTerm = new AndTerm(FromAndDateTerm, unseenAndRecentTerm);
		// SubjectTerm subjectTerm = new SubjectTerm("Testing JavaMail API");
		 //AndTerm FromDateUnSeenRecentSubTerm = new AndTerm(FromAndDateunseenAndRecentTerm,subjectTerm);
		  SearchTerm st = unseenAndRecentTerm;
		  * */
		
	}
	
	
		}
