/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Documents and Settings\\Administrator\\My Documents\\GitHub\\CommunicationService\\src\\cn\\kli\\queen\\communicationservice\\ICommunication.aidl
 */
package cn.kli.queen.communicationservice;
public interface ICommunication extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements cn.kli.queen.communicationservice.ICommunication
{
private static final java.lang.String DESCRIPTOR = "cn.kli.queen.communicationservice.ICommunication";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an cn.kli.queen.communicationservice.ICommunication interface,
 * generating a proxy if needed.
 */
public static cn.kli.queen.communicationservice.ICommunication asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof cn.kli.queen.communicationservice.ICommunication))) {
return ((cn.kli.queen.communicationservice.ICommunication)iin);
}
return new cn.kli.queen.communicationservice.ICommunication.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_sendMessage:
{
data.enforceInterface(DESCRIPTOR);
cn.kli.queen.communicationservice.ComMessage _arg0;
if ((0!=data.readInt())) {
_arg0 = cn.kli.queen.communicationservice.ComMessage.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
int _result = this.sendMessage(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements cn.kli.queen.communicationservice.ICommunication
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public int sendMessage(cn.kli.queen.communicationservice.ComMessage msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((msg!=null)) {
_data.writeInt(1);
msg.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_sendMessage, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_sendMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public int sendMessage(cn.kli.queen.communicationservice.ComMessage msg) throws android.os.RemoteException;
}
