package com.maorui.telephoneintercept.adapter;

import java.util.LinkedList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.maorui.telephoneintercept.R;
import com.maorui.telephoneintercept.bean.ContactsBean;
import com.maorui.telephoneintercept.ui.MainActivity;
import com.maorui.telephoneintercept.util.ContactsUtils;

public class ContactsAdapter extends BaseAdapter {

	private MainActivity ctx;
	private LinkedList<ContactsBean> beans;
	public boolean isShow;

	public ContactsAdapter(MainActivity ctx, LinkedList<ContactsBean> beans) {
		this.ctx = ctx;
		this.beans = beans;
	}

	@Override
	public int getCount() {
		return beans.size();
	}

	@Override
	public Object getItem(int position) {
		return beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void setData(LinkedList<ContactsBean> beans){
		this.beans = beans;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder holder;

		if (convertView == null) {
			view = View.inflate(ctx, R.layout.itme, null);
			holder = new ViewHolder();

			holder.iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
			holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
			holder.tv_number = (TextView) view.findViewById(R.id.tv_number);
			holder.cb = (CheckBox) view.findViewById(R.id.cb);
			
			view.setTag(holder);

		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		ContactsBean bean = beans.get(position);
		
		holder.tv_name.setText(bean.getName());
		holder.tv_number.setText(bean.getNumber());
		if(bean.getPhoto() != null && !"0".equals(bean.getPhoto())){
			ContactsUtils.getPhoto(ctx, bean.getPhoto());
		}
		
		if (isShow) {
			holder.cb.setVisibility(View.VISIBLE);
			Boolean flag = ctx.recodeStatu.get(position);
			if (flag == null) {
				holder.cb.setChecked(false);
			} else {
				holder.cb.setChecked(flag);
			}
		} else {
			holder.cb.setVisibility(View.GONE);
		}
		
		
		return view;
	}

	static class ViewHolder {
		ImageView iv_photo;
		TextView tv_name;
		TextView tv_number;
		CheckBox cb;
	}

}
