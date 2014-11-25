package com.maorui.telephoneintercept.service;

import java.lang.reflect.Method;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.maorui.telephoneintercept.application.MyApplication;
import com.maorui.telephoneintercept.engine.ContactsEngine;

public class PhoneListenService extends Service {

	private TelephonyManager tm;
	private PhoneListener listener;
	private MyApplication app;
	private ContactsEngine engine;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		app = (MyApplication) this.getApplication();
		engine = ContactsEngine.getInstance(this);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		listener = new PhoneListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		System.out.println("PhoneListenService onCreate");
	}

	private static final String TAG = "PhoneListenService";

	private class PhoneListener extends PhoneStateListener {
//		long startTime = 0;

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:// 响铃状态.
//				startTime = System.currentTimeMillis();

				if (engine.getContactsByNumber(incomingNumber)) { // 需要拦截的电话
					Log.i(TAG, "挂断电话");
//					Uri uri = Uri.parse("content://call_log/calls/");
//					getContentResolver().registerContentObserver(uri, true, new CallLogObserver(new Handler(), incomingNumber));
					endCall(incomingNumber);// 电话挂断了.. 但是生成呼叫记录 同步生成..
					// deleteCallLog(incomingNumber);
				}
				break;
			case TelephonyManager.CALL_STATE_IDLE:
//				long endTime = System.currentTimeMillis();
//				if ((endTime - startTime) < 3000) {// 零响时间 < 3秒钟
//					showNotification(incomingNumber);
//				}

				break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}

	}

	/**
	 * 挂断电话.
	 * 
	 * @param incomingNumber
	 *            电话号码
	 */
	@SuppressWarnings("unchecked")
	public void endCall(String incomingNumber) {
		try {
			Class clazz = getClassLoader().loadClass("android.os.ServiceManager");
			Method method = clazz.getMethod("getService", new Class[] { String.class });
			// IBinder b = ServiceManager.getService(TELEPHONY_SERVICE);
			IBinder b = (IBinder) method.invoke(null, new String[] { TELEPHONY_SERVICE });
			ITelephony iTeletphony = ITelephony.Stub.asInterface(b);
			iTeletphony.endCall();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("PhoneListenService onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("PhoneListenService onDestroy");
	}

}
