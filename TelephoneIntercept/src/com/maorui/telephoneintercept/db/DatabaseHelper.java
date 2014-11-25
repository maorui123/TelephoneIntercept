package com.maorui.telephoneintercept.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String DATABASE_NAME = "MyDB";
	static final String DATABASE_TABLE = "contacts";
	static final int DATABASE_VERSION = 1;

	private static final String sql_contacts = "create table contacts( _id integer primary key autoincrement, "
			+ "contactid text not null,number text not null,name text not null, photoid text not null, intercepter text not null);";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(sql_contacts);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS contacts");
		onCreate(db);
	}

	// open the database
	public SQLiteDatabase open() throws SQLException {
		return this.getWritableDatabase();
	}

	// close the database
	public void close() {
		this.close();
	}

}
