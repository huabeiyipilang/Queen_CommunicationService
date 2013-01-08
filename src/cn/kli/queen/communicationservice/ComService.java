package cn.kli.queen.communicationservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import cn.kli.queen.communicationservice.ICommunication.Stub;
import cn.kli.queen.communicationservice.Sender.SenderCallBack;

public class ComService extends Service {
	private ComBinder binder;
	private Klilog klilog = new Klilog(this.getClass());
	private Sender mSender;
	
	public class ComBinder extends Stub{

		public ComBinder(){
			mSender = new EmailSender(ComService.this);
			mSender.setCallBack(new SenderCallBack(){

				@Override
				public void onSendCompleted(int res) {
					
				}
				
			});
		}
		
		@Override
		public int sendMessage(ComMessage msg) throws RemoteException {
			mSender.send(msg);
			return 0;
		}
		
	}

	
	@Override
	public void onCreate() {
		super.onCreate();
		binder = new ComBinder();
	}


	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}
	
}
