package com.maorui.telephoneintercept.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String number;
	private String photo;
	private boolean intercepter = false;
	private String address;  //归属地
	private boolean isWeek;  //是true 选择每天还是  false自定义
	private ArrayList<String>  timeInterval; //时段

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isIntercepter() {
		return intercepter;
	}

	public void setIntercepter(boolean intercepter) {
		this.intercepter = intercepter;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isWeek() {
		return isWeek;
	}

	public void setWeek(boolean isWeek) {
		this.isWeek = isWeek;
	}

	public ArrayList<String> getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(ArrayList<String> timeInterval) {
		this.timeInterval = timeInterval;
	}

	@Override
	public String toString() {
		return "ContactsBean [id=" + id + ", name=" + name + ", number=" + number + ", photo=" + photo + ", intercepter=" + intercepter + "]";
	}

}
