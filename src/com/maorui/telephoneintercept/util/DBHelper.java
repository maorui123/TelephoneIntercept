package com.maorui.telephoneintercept.util;

import com.maorui.telephoneintercept.bean.ContactsBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper {
	// static final String KEY_ROWID = "_id";
	public static final String KEY_CONTACTID = "contactid";
	public static final String KEY_NUMBER = "number";
	public static final String KEY_NAME = "name";
	public static final String KEY_PHOTO = "photoid";
	public static final String KEY_INTERCEPTER = "intercepter";

	static final String TAG = "DBHelper";

	static final String DATABASE_NAME = "MyDB";
	static final String DATABASE_TABLE = "contacts";
	static final int DATABASE_VERSION = 1;

	static final String DATABASE_CREATE = "create table contacts( _id integer primary key autoincrement, "
			+ "contactid text not null,number text not null,name text not null, photoid text not null, intercepter text not null);";
	final Context context;

	DatabaseHelper DBHelper;
	SQLiteDatabase db;

	public DBHelper(Context cxt) {
		this.context = cxt;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.wtf(TAG, "Upgrading database from version " + oldVersion + "to " + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS contacts");
			onCreate(db);
		}
	}

	// open the database
	public DBHelper open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// close the database
	public void close() {
		DBHelper.close();
	}

	// insert a contact into the database
	public long insertContact(ContactsBean bean) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_CONTACTID, bean.getId());
		initialValues.put(KEY_NAME, bean.getName());
		initialValues.put(KEY_NUMBER, bean.getNumber());
		initialValues.put(KEY_PHOTO, bean.getPhoto());
		initialValues.put(KEY_INTERCEPTER, "" + bean.isIntercepter() + "");
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// delete a particular contact
	public boolean deleteContact(String id) {
		return db.delete(DATABASE_TABLE, KEY_CONTACTID + "=" + id, null) > 0;
	}

	// retreves all the contacts
	public Cursor getAllContacts() {
		return db.query(DATABASE_TABLE, new String[] { KEY_CONTACTID, KEY_NAME, KEY_NUMBER, KEY_PHOTO, KEY_INTERCEPTER }, null, null, null, null, null);
	}

	// retreves all the contacts
	public Cursor getContactsByIntercepter(String intercepter) {
		return db.query(DATABASE_TABLE, new String[] { KEY_CONTACTID, KEY_NAME, KEY_NUMBER, KEY_PHOTO, KEY_INTERCEPTER }, KEY_INTERCEPTER + "= ?", new String[] { intercepter }, null, null, null, null);
	}
	
	public Cursor getContactsByNumber(String number) {
		return db.query(DATABASE_TABLE, new String[] { KEY_INTERCEPTER }, KEY_NUMBER + "= ?", new String[] { number }, null, null, null, null);
	}

	// retreves a particular contact
	public Cursor getContact(String id) throws SQLException {
		return db.query(true, DATABASE_TABLE, new String[] { KEY_CONTACTID, KEY_NAME, KEY_NUMBER, KEY_PHOTO, KEY_INTERCEPTER }, KEY_CONTACTID + "=" + id, null, null, null, null, null);
	}

	// updates a contact
	public boolean updateContact(String id, ContactsBean bean) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, bean.getName());
		initialValues.put(KEY_NUMBER, bean.getNumber());
		initialValues.put(KEY_PHOTO, bean.getPhoto());
		initialValues.put(KEY_INTERCEPTER, "" + bean.isIntercepter() + "");
		return db.update(DATABASE_TABLE, initialValues, KEY_CONTACTID + "=" + id, null) > 0;
	}

	// updates a contact
	public boolean updateContactIntercepter(ContactsBean bean,String intercepter) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_INTERCEPTER, intercepter);
		return db.update(DATABASE_TABLE, initialValues, KEY_CONTACTID + "=" + bean.getId(), null) > 0;
	}

}
