package com.QuantumFinance.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static DBHelper dbHelper;

	private int openedConnections = 0;

	public synchronized SQLiteDatabase getReadableDatabase() {
		openedConnections++;
		return super.getReadableDatabase();
	}

	public synchronized SQLiteDatabase getWritableDatabase() {
		openedConnections++;
		return super.getWritableDatabase();
	}

	public synchronized void close() {
		openedConnections--;
		if (openedConnections == 0) {
			super.close();
		}
	}

	public static DBHelper dbHelper() {
		return dbHelper;
	}

	public static void init(Context context) {
		if (dbHelper == null) {
			dbHelper = new DBHelper(context);
		}
	}

	private DBHelper(Context context) {
		super(context, "LzFinance.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String account = "create table account"
				+ "(id integer primary key,"
				+ "username varchar(255),"
				+ "token varchar(255),"
				+ "face varchar(255),"
				+ "phone varchar(20),"
				+ "email varchar(255),"
				+ "bind_weibo integer,"  //1表示已经绑定，0表示未绑定
				+ "bind_qq integer);";
		String praise = "create table praise"
				+ "(id integer primary key,"
				+ "paper_id integer);";
		String collect = "create table collect"
				+ "(id integer primary key,"
				+ "paper_id integer,"
				+ "title varchar(55),"
				+ "lable varchar(55),"
				+ "content varchar(255),"
				+ "view_count integer,"  
				+ "comments integer," 
				+ "logo varchar(255),"
				+ "update_at long);";
		db.execSQL(account);
		db.execSQL(praise);
		db.execSQL(collect);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF account");
		db.execSQL("DROP TABLE IF praise");
		db.execSQL("DROP TABLE IF collect");
		onCreate(db);
	}

}
