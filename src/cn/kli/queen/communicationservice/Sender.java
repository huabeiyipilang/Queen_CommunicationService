package cn.kli.queen.communicationservice;

public abstract class Sender {
	
	final static int SUCCESS = 1;
	final static int ERROR_NETWORK_NOT_ENABLE = 2;
	final static int ERROR_HOST = 3;
	final static int ERROR_UNKNOWN = 4;
	
	private SenderCallBack mCallBack;
	
	interface SenderCallBack{
		void onSendCompleted(int res);
	}
	
	abstract void send(ComMessage msg);
	
	protected void setCallBack(SenderCallBack callBack){
		mCallBack = callBack;
	}
	
	protected void sendCallBack(int res){
		if(mCallBack != null){
			mCallBack.onSendCompleted(res);
		}
	}
	
}
