package com.QuantumFinance.db;

import java.util.ArrayList;
import java.util.List;

import com.QuantumFinance.net.base.PaperBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CollectDAO {

	private DBHelper dbOpenHelper;

	public CollectDAO(Context context) {
		DBHelper.init(context);
		this.dbOpenHelper = DBHelper.dbHelper();
	}

	public void save(PaperBase paper) {
		if (!isExist(paper.getId() + "")) {
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("paper_id", paper.getId());
			if (paper.getTitle() != null) {
				values.put("title", paper.getTitle());
			}
			if (paper.getLable() != null) {
				values.put("lable", paper.getLable());
			}
			if (paper.getContent() != null) {
				if(paper.getContent().length()>255)
					values.put("content", paper.getContent().substring(0,254));
				else
					values.put("content", paper.getContent());
			}
			values.put("view_count", paper.getView_count());
			values.put("comments", paper.getComments());
			if (paper.getLogo() != null) {
				values.put("logo", paper.getLogo());
			}
			db.insert("collect", null, values);
		}
	}

	public boolean isExist(String paper_id) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from collect where paper_id=? limit 1", new String[] { paper_id });
		boolean result = false;
		if (cursor.moveToFirst()) {
			result = true;
		}
		cursor.close();
		return result;
	}

	public List<PaperBase> getAll() {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from collect", new String[] {});
		List<PaperBase> papers = new ArrayList<PaperBase>();
		while (cursor.moveToNext()) {
			PaperBase paper = new PaperBase();
			paper.setId(cursor.getInt(1));
			paper.setTitle(cursor.getString(2));
			paper.setLable(cursor.getString(3));
			paper.setContent(cursor.getString(4));
			paper.setView_count(cursor.getInt(5));
			paper.setComments(cursor.getInt(6));
			paper.setLogo(cursor.getString(7));
			papers.add(paper);
		}

		cursor.close();
		return papers;
	}

	public void delete(String paper_id) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from collect where paper_id=?", new String[] { paper_id });
	}

}
