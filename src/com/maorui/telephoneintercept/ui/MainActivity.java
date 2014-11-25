package com.maorui.telephoneintercept.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.maorui.telephoneintercept.R;
import com.maorui.telephoneintercept.adapter.ContactsAdapter;
import com.maorui.telephoneintercept.bean.ContactsBean;
import com.maorui.telephoneintercept.engine.ContactsEngine;
import com.maorui.telephoneintercept.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

	private TextView tv_yes;
	private TextView tv_no;

	private ListView lv_yes;
	private ListView lv_no;

	private ContactsAdapter yes_adapter;
	private ContactsAdapter no_adapter;

	private ContactsEngine engine;

	public Map<Integer, Boolean> recodeStatu = new HashMap<Integer, Boolean>();
	private int count;
	private LinearLayout ll_del;
	private Button btn_cancel;
	private Button btn_confirm;

	private ArrayList<ContactsBean> checkBeans = new ArrayList<ContactsBean>();

//	private LinkedList<ContactsBean> yes_beans;
	private LinkedList<ContactsBean> no_beans;

	private boolean isRef = false; // 默认是不需要刷新数据，为解决排序问题

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void init() {
		tv_yes = (TextView) findViewById(R.id.tv_yes);
		tv_no = (TextView) findViewById(R.id.tv_no);

		lv_yes = (ListView) findViewById(R.id.lv_yes);
		lv_no = (ListView) findViewById(R.id.lv_no);

		ll_del = (LinearLayout) findViewById(R.id.ll_del);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);

		engine = ContactsEngine.getInstance(this);

	}

	@Override
	protected void setListener() {
		tv_yes.setOnClickListener(this);
		tv_no.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
	}

	@Override
	protected void fillData() {

		app.intercepterContacts = engine.getContactsByIntercepter(true);
		yes_adapter = new ContactsAdapter(this, app.intercepterContacts);
		lv_yes.setAdapter(yes_adapter);

		System.out.println("yes_beans == " + app.intercepterContacts.size());
		lv_yes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 弹出一个dailog activity
				Intent i = new Intent(MainActivity.this, ContactInfoActivity2.class);
				i.putExtra("bean", (ContactsBean) parent.getItemAtPosition(position));
				startActivityForResult(i, 1000);
			}

		});

		no_beans = engine.getContactsByIntercepter(false);
		no_adapter = new ContactsAdapter(this, no_beans);
		lv_no.setAdapter(no_adapter);
		System.out.println("no_beans == " + no_beans.size());

		lv_no.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				no_adapter.isShow = true;
				no_adapter.notifyDataSetChanged();
				ll_del.setVisibility(View.VISIBLE);
				return true;
			}
		});

		lv_no.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (no_adapter.isShow) {
					CheckBox cb = (CheckBox) view.findViewById(R.id.cb);
					boolean isCheck = !cb.isChecked();
					if (isCheck) {
						count++;
						checkBeans.add((ContactsBean) parent.getItemAtPosition(position));
					} else {
						checkBeans.remove((ContactsBean) parent.getItemAtPosition(position));
						count--;
					}
					btn_confirm.setText("拦截(" + count + ")");
					recodeStatu.put(position, isCheck);
					cb.setChecked(isCheck);
				} else {
					// 弹出一个dailog activity
					Intent i = new Intent(MainActivity.this, ContactInfoActivity2.class);
					i.putExtra("bean", (ContactsBean) parent.getItemAtPosition(position));
					startActivityForResult(i, 1000);
				}

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1000) {
			if (resultCode == 1000) { // 取消拦截成功，需要更新lv_yes 列表数据
				String beanId = data.getStringExtra("beanId");
				for (ContactsBean bean : app.intercepterContacts) {
					if (beanId.equals(bean.getId())) {
						if (app.intercepterContacts.remove(bean)) {
							isRef = true;
						}
						break;
					}
				}
				yes_adapter.notifyDataSetChanged();
			} else if (resultCode == 2000) {
				String beanId = data.getStringExtra("beanId");
				for (ContactsBean bean : no_beans) {
					if (beanId.equals(bean.getId())) {
						if (no_beans.remove(bean)) {
							isRef = true;
						}
						break;
					}
				}
				no_adapter.notifyDataSetChanged();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View view) {
		if (tv_yes.getId() == view.getId()) {

			tv_yes.setBackgroundResource(R.drawable.bg_color_white_corners_left);
			tv_yes.setTextColor(getResources().getColor(R.color.bgColor));

			tv_no.setBackgroundResource(R.drawable.bg_color_title_corners_right);
			tv_no.setTextColor(getResources().getColor(R.color.white));

			lv_yes.setVisibility(View.VISIBLE);
			lv_no.setVisibility(View.GONE);

			ll_del.setVisibility(View.GONE);

			if (isRef) {
				app.intercepterContacts = engine.getContactsByIntercepter(true);
				yes_adapter.setData(app.intercepterContacts);
				isRef = false;
			}

		} else if (tv_no.getId() == view.getId()) {

			tv_yes.setBackgroundResource(R.drawable.bg_color_title_corners_left);
			tv_yes.setTextColor(getResources().getColor(R.color.white));

			tv_no.setBackgroundResource(R.drawable.bg_color_white_corners_right);
			tv_no.setTextColor(getResources().getColor(R.color.bgColor));

			lv_no.setVisibility(View.VISIBLE);
			lv_yes.setVisibility(View.GONE);

			if (no_adapter.isShow) {
				ll_del.setVisibility(View.VISIBLE);
			} else {
				ll_del.setVisibility(View.GONE);
			}

			if (isRef) {
				no_beans = engine.getContactsByIntercepter(false);
				no_adapter.setData(no_beans);
				isRef = false;
			}

		} else if (btn_cancel.getId() == view.getId()) {
			if (no_adapter.isShow) {
				cancle();
			}
		} else if (btn_confirm.getId() == view.getId()) {
			int i = 0;
			for (ContactsBean bean : checkBeans) {
				boolean isSuccess = engine.updateIntercepter(bean, true);
				if (isSuccess) {
					++i;
					no_beans.remove(bean);
				}
			}
			if (i > 0) {
				isRef = true;
			}
			cancle();
		}
	}

	private void cancle() {
		ll_del.setVisibility(View.GONE);
		no_adapter.isShow = false;
		recodeStatu.clear();
		no_adapter.notifyDataSetChanged();
		count = 0;
		btn_confirm.setText("拦截(" + count + ")");
		checkBeans.clear();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
			if (no_adapter != null && no_adapter.isShow) {
				cancle();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
