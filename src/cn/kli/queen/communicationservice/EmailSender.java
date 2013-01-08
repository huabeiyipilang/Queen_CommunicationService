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
import android.util.Log;

public class EmailSender extends Sender {
	private final static String MAIL_SUBJECT = "BUG";
	
	Session mSession;
	MimeMessage mMsg;
	EmailAccount mAccount;
	ComMessage currentComMessage;
	
	public EmailSender(Context context){
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
		currentComMessage = msg;
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
	
	private boolean sendMail(){
		try {
			Transport transport = mSession.getTransport("smtp");
			transport.connect();
			Transport.send(mMsg);
			transport.close();
		} catch (NoSuchProviderException e) {
			sendCallBack(ERROR_HOST);
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			sendCallBack(ERROR_UNKNOWN);
			e.printStackTrace();
			return false;
		}
		sendCallBack(SUCCESS);
		return true;
	}
	
	@Override
	public void send(ComMessage msg) {
		if(!buildMail(msg)){
			sendCallBack(ERROR_HOST);
		}
		new Thread(){

			@Override
			public void run() {
				super.run();
				sendMail();
			}
			
		}.start();
	}

	class PopupAuthenticator extends Authenticator{

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(mAccount.username, mAccount.password);
		}
		
	}

	@Override
	protected void sendCallBack(int res) {
		super.sendCallBack(res);
		currentComMessage.callBack.arg1 = res;
		try {
			currentComMessage.callBack.sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
