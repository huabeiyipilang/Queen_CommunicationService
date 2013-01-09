package cn.kli.queen.communicationservice;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

public abstract class Sender {
	
	private final static int MSG_SEND = 1;
	private final static int MSG_SENT = 2;
	
	private SenderCallBack mCallBack;
	
	private HandlerThread mHandlerThread;
	
	private Handler mHandler;
	private Klilog klilog = new Klilog(this.getClass());
	
	interface SenderCallBack{
		void onSendCompleted(ComMessage cmsg);
	}
	
	public Sender(Context context){
		mHandlerThread = new HandlerThread("handler_thread");
		mHandlerThread.start();
		mHandler = new Handler(mHandlerThread.getLooper()){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				klilog.i(msgToString(msg.what));
				switch(msg.what){
				case MSG_SEND:
					Message send_msg = obtainMessage(MSG_SENT);
					send_msg.obj = onMsgSend((ComMessage)msg.obj);
					send_msg.sendToTarget();
					break;
				case MSG_SENT:
					onMsgSent((ComMessage)msg.obj);
					break;
				}
			}
			
		};
	}
	
	public void send(ComMessage cmsg){
		Message msg = mHandler.obtainMessage(MSG_SEND);
		msg.obj = cmsg;
		msg.sendToTarget();
	}
	
	abstract ComMessage onMsgSend(ComMessage cmsg);
	
	protected void onMsgSent(ComMessage cmsg){
		sendCallBack(cmsg);
	}
	
	protected void setCallBack(SenderCallBack callBack){
		mCallBack = callBack;

	}
	
	private void sendCallBack(ComMessage msg){
		if(mCallBack != null){
			mCallBack.onSendCompleted(msg);
		}
	}
	
	private String msgToString(int msg){
		String res = "";
		switch(msg){
		case MSG_SEND:
			res = "MSG_SEND";
			break;
		case MSG_SENT:
			res = "MSG_SENT";
			break;
		}
		return res;
	}
}
