package cn.kli.queen.communicationservice;

import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;

public class ComMessage implements Parcelable{
	public String from;
	public String title;
	public String content;
	public Message callBack;
	
	public ComMessage(){
	}
	
	private ComMessage(Parcel in){
		from = in.readString();
		title = in.readString();
		content = in.readString();
		if(in.readInt() != 0){
			callBack = in.readParcelable(Message.class.getClassLoader());
		}
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(from);
		out.writeString(title);
		out.writeString(content);
		if(callBack == null){
			out.writeInt(0);
		}else{
			out.writeInt(1);
			out.writeParcelable(callBack, flags);
		}
	}
	
	public static final Parcelable.Creator<ComMessage> CREATOR = new Parcelable.Creator<ComMessage>(){

		@Override
		public ComMessage createFromParcel(Parcel in) {
			return new ComMessage(in);
		}

		@Override
		public ComMessage[] newArray(int size) {
			return new ComMessage[size];
		}
		
	};
}
