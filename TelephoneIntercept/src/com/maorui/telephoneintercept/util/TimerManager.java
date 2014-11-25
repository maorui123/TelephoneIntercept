package com.maorui.telephoneintercept.util;

import com.maorui.telephoneintercept.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class TimerManager {
	
	private View view;

	public View createView(Context context, ViewGroup root) {
		view = View.inflate(context, R.layout.util_add_timer, root);
		return view;
	}
	
	
	
	

}
