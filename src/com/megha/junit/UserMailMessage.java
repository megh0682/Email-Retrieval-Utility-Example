package com.megha.junit;

import javax.mail.Session;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPMessage;

public class UserMailMessage extends IMAPMessage {
	private Session emailSession; 

	
	public UserMailMessage(Session emailSession)
	{
		super(emailSession);
	}
	
	public long getMessageUID()
	{
		UserMailMessage umm = new UserMailMessage(emailSession);
		return umm.getUID();
	}
	
}
