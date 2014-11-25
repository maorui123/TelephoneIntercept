package com.maorui.telephoneintercept.application;

import java.util.LinkedList;

import android.app.Application;

import com.maorui.telephoneintercept.bean.ContactsBean;
import com.maorui.telephoneintercept.util.ContactsUtils;

public class MyApplication extends Application {

	
	public LinkedList<ContactsBean> intercepterContacts = new LinkedList<ContactsBean>();

	@Override
	public void onCreate() {
		ContactsUtils.startService(this);
		super.onCreate();
	}
}
