package com.maorui.telephoneintercept.ui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

import com.maorui.telephoneintercept.R;
import com.maorui.telephoneintercept.bean.ContactsBean;
import com.maorui.telephoneintercept.engine.ContactsEngine;
import com.maorui.telephoneintercept.util.ContactsUtils;

@SuppressLint("HandlerLeak")
public class SplashActivity extends Activity {

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
				break;

			default:
				break;
			}
		};
	};

	private SharedPreferences configSp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		configSp = getSharedPreferences("config", MODE_PRIVATE);

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				boolean isCopyContact = configSp.getBoolean("isCopyContact", false);
				if (!isCopyContact) {// copy 到 myDB
					System.out.println("copy contact start  == " + startTime);
					ArrayList<ContactsBean> beans = ContactsUtils.getAllContacts(SplashActivity.this);
					ContactsEngine engine = ContactsEngine.getInstance(SplashActivity.this);
					long count = 0;
					for (ContactsBean bean : beans) {
						long result = engine.insert(bean);
						if (result != -1) {
							count++;
						}
					}

					if (count == beans.size()) {
						Editor editor = configSp.edit();
						editor.putBoolean("isCopyContact", true);
						editor.commit();
					}
					System.out.println("copy contact end  == " + (System.currentTimeMillis() - startTime));
					System.out.println(" count = "  + count + "  size = " + beans.size());
				}

				handler.sendEmptyMessage(0);
			}
		},0);
	}
 
}
