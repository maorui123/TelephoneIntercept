package com.maorui.telephoneintercept.ui.base;

import com.maorui.telephoneintercept.application.MyApplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends Activity implements OnClickListener {

	protected MyApplication app = null;
	protected SharedPreferences configSp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (MyApplication) getApplication();
		configSp = getSharedPreferences("config", MODE_PRIVATE);
		setContentView();
		init();
		setListener();
		fillData();
	}

	/**
	 * 设置布局
	 */
	protected abstract void setContentView();

	/**
	 * 初始化操作
	 */
	protected abstract void init();

	/**
	 * 设置监听
	 */
	protected abstract void setListener();

	/**
	 * 设置数据
	 */
	protected abstract void fillData();

	public abstract void onClick(View view);

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
//			overridePendingTransition(R.anim.zjg_slider_out_2, R.anim.zjg_slider_in_2);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
//		PromptManager.closeLoadDataDialog();
		super.onDestroy();
	}
}
