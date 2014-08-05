package com.smartcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler {
	
	public static final String ID = "id";
	public static final String TIME = "time";
	public static final String BPM = "bpm";
	public static final String TABLE_NAME = "mytable";
	public static final String DATABASE_NAME = "bpmdatabase";
	public static final String TABLE_CREATE = "create table mytable";
	
	public static final int DATABASE_VERSION = 1;
	
	SQLiteDatabase db;
	DataBaseHelper dbhelper;
	Context ctx;
	public DataHandler(Context ctx){
		this.ctx = ctx;
		dbhelper = new DataBaseHelper(ctx);
	}
	
	private static class DataBaseHelper extends SQLiteOpenHelper {

		public DataBaseHelper(Context ctx) {
			super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(TABLE_CREATE);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
			db.execSQL("DROP TABLE IF EXISTS mytable");
			onCreate(db);
			
		}		
		
	}
	
	public DataHandler open(){
		db = dbhelper.getWritableDatabase();
		return this;
	}
	
	public void colse(){
		dbhelper.close();
	}
	
	public long insertData(String id, String time, String bpm){
		ContentValues content = new ContentValues();
		content.put(ID, id);
		content.put(TIME, time);
		content.put(BPM, bpm);
		return db.insertOrThrow(TABLE_NAME, null, content);
	}
	
	public Cursor returnData(){
		return db.query(TABLE_NAME, new String[]{ID, TIME, BPM}, null, null, null, null, null);
	}

}
