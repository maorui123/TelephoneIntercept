package com.maorui.telephoneintercept.engine;

import java.util.ArrayList;
import java.util.LinkedList;

import android.content.Context;
import android.database.Cursor;

import com.maorui.telephoneintercept.bean.ContactsBean;
import com.maorui.telephoneintercept.util.DBHelper;

public class ContactsEngine {

	private DBHelper dbHelper;
	private static ContactsEngine engine;

	private ContactsEngine(Context ctx) {
		dbHelper = new DBHelper(ctx);
		dbHelper.open();
	}

	public static ContactsEngine getInstance(Context ctx) {
		if (engine == null) {
			engine = new ContactsEngine(ctx);
		}
		return engine;
	}

	public ArrayList<ContactsBean> getAllContacts() {

		ArrayList<ContactsBean> al = new ArrayList<ContactsBean>();
		Cursor cursor = dbHelper.getAllContacts();
		while (cursor.moveToNext()) {

			ContactsBean node = new ContactsBean();
			String contactId = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_CONTACTID));
			String number = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NUMBER));
			String name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME));
			String photoId = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHOTO));
			String intercepter = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_INTERCEPTER));

			node.setId(contactId);
			node.setName(name);
			node.setNumber(number);
			node.setPhoto(photoId);
			node.setIntercepter(Boolean.parseBoolean(intercepter));
			al.add(node);
		}
		cursor.close();

		return al;
	}

	public LinkedList<ContactsBean> getContactsByIntercepter(boolean isIntercepter) {

		LinkedList<ContactsBean> al = new LinkedList<ContactsBean>();
		Cursor cursor = dbHelper.getContactsByIntercepter("" + isIntercepter + "");
		while (cursor.moveToNext()) {

			ContactsBean node = new ContactsBean();
			String contactId = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_CONTACTID));
			String number = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NUMBER));
			String name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME));
			String photoId = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHOTO));
			String intercepter = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_INTERCEPTER));

			node.setId(contactId);
			node.setName(name);
			node.setNumber(number);
			node.setPhoto(photoId);
			node.setIntercepter(Boolean.parseBoolean(intercepter));
			al.add(node);
		}
		cursor.close();

		return al;
	}

	public ContactsBean getContactsById(String id) {
		Cursor cursor = dbHelper.getContact(id);
		ContactsBean node = null;
		if (cursor.moveToFirst()) {
			node = new ContactsBean();
			String contactId = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_CONTACTID));
			String number = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NUMBER));
			String name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME));
			String photoId = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHOTO));
			String intercepter = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_INTERCEPTER));
			node.setId(contactId);
			node.setName(name);
			node.setNumber(number);
			node.setPhoto(photoId);
			node.setIntercepter(Boolean.parseBoolean(intercepter));
		}

		return node;
	}

	public boolean getContactsByNumber(String number) {
		Cursor cursor = dbHelper.getContactsByNumber(number);
		boolean result = false;
		if (cursor.moveToFirst()) {
			result = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_INTERCEPTER)));
		}

		return result;
	}

	public long insert(ContactsBean bean) {
		return dbHelper.insertContact(bean);
	}

	public boolean delete(String id) {
		return dbHelper.deleteContact(id);
	}

	public boolean update(String id, ContactsBean bean) {
		return dbHelper.updateContact(id, bean);
	}

	public boolean updateIntercepter(ContactsBean bean, boolean intercepter) {
		return dbHelper.updateContactIntercepter(bean, "" + intercepter + "");
	}

	public void onDestroy() {
		if (dbHelper != null) {
			dbHelper.close();
			dbHelper = null;
			engine = null;
			System.gc();
		}
	}
}
