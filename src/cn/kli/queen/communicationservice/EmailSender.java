package cn.kli.queen.communicationservice;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.content.Context;

public class EmailSender extends Sender {

	Session mSession;
	MimeMessage mMsg;
	EmailAccount mAccount;
	ComMessage mComMessage;

	public EmailSender(Context context) {
		super(context);
		mAccount = new EmailAccount(context);
		buildSession(); 
	}
	
	private void buildSession(){
		Properties props = new Properties();
		props.put("mail.smtp.host", mAccount.host);
		props.put("mail.smtp.auth",mAccount.auth);
		Authenticator auth = new PopupAuthenticator();
		mSession = Session.getDefaultInstance(props, auth);
	}
	
	private boolean buildMail(ComMessage msg){
		mComMessage = msg;
		Address addFrom;
		Address addTo;
		try {
			addFrom = new InternetAddress(mAccount.address,"client");
			addTo = new InternetAddress(mAccount.address,"Ingenic");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		if(mSession == null){
			return false;
		}
		mMsg = new MimeMessage(mSession);
		try {
			mMsg.setFrom(addFrom);
			mMsg.addRecipient(Message.RecipientType.TO, addTo);
			mMsg.setSubject("["+msg.from+"]"+msg.title);
			mMsg.setText(msg.content);
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	class PopupAuthenticator extends Authenticator{

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(mAccount.username, mAccount.password);
		}
		
	}

	@Override
	ComMessage onMsgSend(ComMessage cmsg) {
		int res = ComMessage.SUCCESS;
		if (!buildMail(cmsg)) {
			res = ComMessage.ERROR_HOST;
		} else {
			try {
				Transport transport = mSession.getTransport("smtp");
				transport.connect();
				Transport.send(mMsg);
				transport.close();
			} catch (NoSuchProviderException e) {
				res = ComMessage.ERROR_HOST;
				e.printStackTrace();
			} catch (MessagingException e) {
				res = ComMessage.ERROR_UNKNOWN;
				e.printStackTrace();
			}
		}
		cmsg.cause = res;
		return cmsg;
		
	}
}
