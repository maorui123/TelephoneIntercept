package com.maorui.telephoneintercept.ui;

import java.util.Calendar;

import mirko.android.datetimepicker.time.RadialPickerLayout;
import mirko.android.datetimepicker.time.TimePickerDialog;
import mirko.android.datetimepicker.time.TimePickerDialog.OnTimeSetListener;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.maorui.telephoneintercept.R;
import com.maorui.telephoneintercept.bean.ContactsBean;
import com.maorui.telephoneintercept.ui.base.BaseActivity;

public class ContactInfoActivity2 extends BaseActivity implements OnCheckedChangeListener {

	private ContactsBean bean;
	
	private ImageView iv_photo;
	private TextView tv_name;
	private TextView tv_number;
	
	// 选择星期还是每天的单选框
	private RadioGroup rg;
	private RadioButton rb_everyday, rb_custom;

	// 星期选择
	private LinearLayout ll_select_week; // 星期选择最外层的view
	private LinearLayout ll_week_1, ll_week_2, ll_week_3, ll_week_4, ll_week_5, ll_week_6, ll_week_7;
	private View view1, view2, view3, view4, view5, view6, view7;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
	private boolean c1, c2, c3, c4, c5, c6, c7;

	private TextView tv_start_time, tv_end_time;
	private int checkId;
	private int hour, minute; // 记录第一个时段的时间用于判断

	// 获取当前时间
	private final Calendar mCalendar = Calendar.getInstance();
	private TimePickerDialog timePickerDialog24h;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_contact_info);
	}

	@Override
	protected void init() {
		bean = (ContactsBean) getIntent().getSerializableExtra("bean");
		
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_number = (TextView) findViewById(R.id.tv_number);
		
		rg = (RadioGroup) findViewById(R.id.rg);
		rb_everyday = (RadioButton) findViewById(R.id.rb_everyday);
		rb_custom = (RadioButton) findViewById(R.id.rb_custom);

		ll_select_week = (LinearLayout) findViewById(R.id.ll_select_week);

		ll_week_1 = (LinearLayout) findViewById(R.id.ll_week_1);
		ll_week_2 = (LinearLayout) findViewById(R.id.ll_week_2);
		ll_week_3 = (LinearLayout) findViewById(R.id.ll_week_3);
		ll_week_4 = (LinearLayout) findViewById(R.id.ll_week_4);
		ll_week_5 = (LinearLayout) findViewById(R.id.ll_week_5);
		ll_week_6 = (LinearLayout) findViewById(R.id.ll_week_6);
		ll_week_7 = (LinearLayout) findViewById(R.id.ll_week_7);

		view1 = findViewById(R.id.view1);
		view2 = findViewById(R.id.view2);
		view3 = findViewById(R.id.view3);
		view4 = findViewById(R.id.view4);
		view5 = findViewById(R.id.view5);
		view6 = findViewById(R.id.view6);
		view7 = findViewById(R.id.view7);

		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		tv7 = (TextView) findViewById(R.id.tv7);

		tv_start_time = (TextView) findViewById(R.id.tv_start_time);
		tv_end_time = (TextView) findViewById(R.id.tv_end_time);

	}

	@Override
	protected void setListener() {
		rg.setOnCheckedChangeListener(this);
		ll_week_1.setOnClickListener(this);
		ll_week_2.setOnClickListener(this);
		ll_week_3.setOnClickListener(this);
		ll_week_4.setOnClickListener(this);
		ll_week_5.setOnClickListener(this);
		ll_week_6.setOnClickListener(this);
		ll_week_7.setOnClickListener(this);

		tv_start_time.setOnClickListener(this);
		tv_end_time.setOnClickListener(this);

	}

	@Override
	protected void fillData() {
		
		tv_name.setText(bean.getName());
		tv_number.setText(bean.getNumber()+"(移动)");

		// 记录每个星期的选择状态
		c1 = false;
		c2 = false;
		c3 = false;
		c4 = false;
		c5 = false;
		c6 = false;
		c7 = false;

		checkId = tv_start_time.getId();

		timePickerDialog24h = TimePickerDialog.newInstance(new OnTimeSetListener() {

			@Override
			public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minuteOfDay) {
				// 以开始时间为基准，只要改变了 开始时间 就表示需要更新时段
				if (checkId == tv_start_time.getId()) {
					if(hourOfDay != hour || minuteOfDay != minute){
						hour = hourOfDay;
						minute = minuteOfDay;
						setText(hourOfDay, minuteOfDay);
						// 如果结束时间不为空表示要清空结束时间
						if (!TextUtils.isEmpty(tv_end_time.getText())) {
							tv_end_time.setText("");
							tv_end_time.setBackgroundResource(R.drawable.bg_color_gray_corners);
						}
					}
				} else {
					boolean isSuccess = false;
					if (hourOfDay >= hour) {
						if (hourOfDay == hour) {
							if (minuteOfDay > minute) {
								isSuccess = true;
							}
						} else {
							isSuccess = true;
						}
					}

					if (isSuccess) {
						setText(hourOfDay, minuteOfDay);
					} else {
						// 如果选择失败也需要清空结束时间
						tv_end_time.setText("");
						tv_end_time.setBackgroundResource(R.drawable.bg_color_gray_corners);
						Toast.makeText(ContactInfoActivity2.this, "结束时间要大于开始时间", 0).show();
					}
				}
			}
		}, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (ll_week_1.getId() == id) {
			c1 = !c1;
			updataStatus(tv1, view1, c1);
		} else if (ll_week_2.getId() == id) {
			c2 = !c2;
			updataStatus(tv2, view2, c2);
		} else if (ll_week_3.getId() == id) {
			c3 = !c3;
			updataStatus(tv3, view3, c3);
		} else if (ll_week_4.getId() == id) {
			c4 = !c4;
			updataStatus(tv4, view4, c4);
		} else if (ll_week_5.getId() == id) {
			c5 = !c5;
			updataStatus(tv5, view5, c5);
		} else if (ll_week_6.getId() == id) {
			c6 = !c6;
			updataStatus(tv6, view6, c6);
		} else if (ll_week_7.getId() == id) {
			c7 = !c7;
			updataStatus(tv7, view7, c7);
		} else if (tv_start_time.getId() == id) {
			checkId = tv_start_time.getId();
			timePickerDialog24h.show(getFragmentManager(), null);
		} else if (tv_end_time.getId() == id) {
			if (TextUtils.isEmpty(tv_start_time.getText())) {
				Toast.makeText(ContactInfoActivity2.this, "请先选择开始时间", 0).show();
				return;
			}
			checkId = tv_end_time.getId();
			timePickerDialog24h.show(getFragmentManager(), null);
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (rb_everyday.getId() == checkedId) { // 选择全天
			ll_select_week.setVisibility(View.GONE);

		} else { // 用户自定义
			ll_select_week.setVisibility(View.VISIBLE);

		}
	}

	private void updataStatus(TextView tv, View view, boolean isCheck) {

		if (isCheck) {
//			tv.getPaint().setTypeface(Typeface.create("bold", Typeface.BOLD));
			tv.setTypeface(Typeface.DEFAULT_BOLD);
			tv.setTextColor(getResources().getColor(R.color.bgColor));
			view.setBackgroundColor(getResources().getColor(R.color.bgColor));
		} else {
			tv.setTypeface(Typeface.DEFAULT);
			tv.setTextColor(getResources().getColor(R.color.text_gray));
			view.setBackgroundColor(getResources().getColor(R.color.text_gray));
		}
	}

	private void setText(int hourOfDay, int minute) {
		if (tv_start_time.getId() == checkId) {
			tv_start_time.setTypeface(Typeface.DEFAULT_BOLD);
			tv_start_time.setBackgroundDrawable(null);
			tv_start_time.setTextSize(14);
			tv_start_time.setText(new StringBuilder().append(pad(hourOfDay)).append(":").append(pad(minute)));
			tv_start_time.setTextColor(getResources().getColor(R.color.bgColor));
		} else if (tv_end_time.getId() == checkId) {
			tv_end_time.setTypeface(Typeface.DEFAULT_BOLD);
			tv_end_time.setBackgroundDrawable(null);
			tv_end_time.setTextSize(14);
			tv_end_time.setText(new StringBuilder().append(pad(hourOfDay)).append(":").append(pad(minute)));
			tv_end_time.setTextColor(getResources().getColor(R.color.bgColor));
		}
	}

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
	
	//当Activityfinish 时候保存数据
	@Override
	public void finish() {
		
		
		
		
		
		
		super.finish();
	}
	

}
