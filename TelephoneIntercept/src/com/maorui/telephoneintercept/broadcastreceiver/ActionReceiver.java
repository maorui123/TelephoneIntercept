package com.maorui.telephoneintercept.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.maorui.telephoneintercept.util.ContactsUtils;

public class ActionReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ContactsUtils.startService(context);
	}

}
