package com.maorui.telephoneintercept.ui;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maorui.telephoneintercept.R;
import com.maorui.telephoneintercept.bean.ContactsBean;
import com.maorui.telephoneintercept.engine.ContactsEngine;
import com.maorui.telephoneintercept.ui.base.BaseActivity;

public class ContactInfoActivity extends BaseActivity {

	private ContactsBean bean;

	private ImageView iv_photo;
	private TextView tv_name;
	private TextView tv_number;
	private Button btn_cancel_intercepter;
	private Button btn_intercepter;

	private ContactsEngine engine;

	@Override
	protected void setContentView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dialog_contact_info);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	}

	@Override
	protected void init() {
		bean = (ContactsBean) getIntent().getSerializableExtra("bean");

		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_number = (TextView) findViewById(R.id.tv_number);

		btn_cancel_intercepter = (Button) findViewById(R.id.btn_cancel_intercepter);
		btn_intercepter = (Button) findViewById(R.id.btn_intercepter);

		engine = ContactsEngine.getInstance(this);
	}

	@Override
	protected void setListener() {
		btn_intercepter.setOnClickListener(this);
		btn_cancel_intercepter.setOnClickListener(this);
	}

	@Override
	protected void fillData() {
		tv_name.setText(bean.getName());
		tv_number.setText(bean.getNumber());

		if (bean.isIntercepter()) {
			btn_cancel_intercepter.setVisibility(View.VISIBLE);
			btn_intercepter.setVisibility(View.GONE);
		} else {
			btn_cancel_intercepter.setVisibility(View.GONE);
			btn_intercepter.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onClick(View view) {

		if (view.getId() == btn_cancel_intercepter.getId()) { // 取消拦截
			boolean updataSuccess = engine.updateIntercepter(bean, false);
			Toast.makeText(this, updataSuccess ? "取消成功" : "取消失败", Toast.LENGTH_SHORT).show();
			if (updataSuccess) {
				Intent i = new Intent();
				i.putExtra("beanId", bean.getId());
				setResult(1000, i);
			}
			this.finish();
		} else if (view.getId() == btn_intercepter.getId()) {// 拦截
			boolean updataSuccess = engine.updateIntercepter(bean, true);
			Toast.makeText(this, updataSuccess ? "拦截成功" : "拦截失败", Toast.LENGTH_SHORT).show();
			if (updataSuccess) {
				Intent i = new Intent();
				i.putExtra("beanId", bean.getId());
				setResult(2000, i);
			}
			this.finish();
		}

	}

}
