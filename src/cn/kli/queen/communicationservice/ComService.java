package cn.kli.queen.communicationservice;

import java.util.ArrayList;
import java.util.List;

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
	final private List<IComCallback> mCallbacks = 
			new ArrayList<IComCallback>();
	
	public class ComBinder extends Stub{
		
		@Override
		public void sendMessage(ComMessage msg) throws RemoteException {
			klilog.i("[ComBinder] sendMessage");
			mSender.send(msg);
		}

		@Override
		public void registerForComState(IComCallback callback)
				throws RemoteException {
			klilog.i("[ComBinder] registerForComState callback null ? "+(callback == null));
			if(callback != null){
				mCallbacks.add(callback);
			}
		}

		@Override
		public void unRegisterForComState(IComCallback callback)
				throws RemoteException {
			klilog.i("[ComBinder] unRegisterForComState callback null ? "+(callback == null));
			if(callback != null){
				mCallbacks.remove(callback);
			}
		}
		
	}

	
	@Override
	public void onCreate() {
		super.onCreate();
		binder = new ComBinder();
		mSender = new EmailSender(ComService.this);
		mSender.setCallBack(new SenderCallBack(){

			@Override
			public void onSendCompleted(ComMessage cmsg) {
				if(cmsg == null){
					return;
				}
				klilog.i("onSendCompleted mCallbacks size = "+mCallbacks.size());
				for(IComCallback callback : mCallbacks){
					try {
						callback.onSendComplete(cmsg);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		});
	}


	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}
	
}
