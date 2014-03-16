package com.QuantumFinance.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PraiseDAO {

	private DBHelper dbOpenHelper;

	public PraiseDAO(Context context) {
		DBHelper.init(context);
		this.dbOpenHelper = DBHelper.dbHelper();
	}

	public void save(String paper_id) {
		if(!isExist(paper_id)){
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("paper_id", paper_id);
			db.insert("praise", null, values);
		}
		
	}

	public boolean isExist(String paper_id) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from praise where paper_id=? limit 1", new String[] { paper_id });
		boolean result = false;
		if (cursor.moveToFirst()) {
			result = true;
		}
		cursor.close();
		return result;
	}

	public void delete(String paper_id) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from praise where paper_id=?", new String[] { paper_id });
	}

}
