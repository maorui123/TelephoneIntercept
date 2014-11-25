package com.maorui.telephoneintercept.broadcastreceiver;

import com.maorui.telephoneintercept.util.ContactsUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 启动拦截电话的服务
		ContactsUtils.startService(context);
	}

}
