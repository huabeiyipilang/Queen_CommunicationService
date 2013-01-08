package cn.kli.queen.communicationservice;

import android.util.Log;

public class Klilog {
	private final static String TAG = "CommunicationService";
	Class mClass;
	public Klilog(Class c){
		mClass = c;
	}
	public void i(String msg){
		Log.i(TAG, "[" +mClass.getSimpleName()+"] "+ msg);
	}

}
