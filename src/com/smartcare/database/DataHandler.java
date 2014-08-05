package com.smartcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHandler {
	
	public static final String ID = "id";
	public static final String TIME = "time";
	public static final String VALUE = "value";
	public static final String TYPE = "type";
	public static final String CATEGORY = "category";
	public static final String TABLE_NAME = "value";
	public static final String DATABASE_NAME = "bpmdatabase";
	public static final String TABLE_CREATE = "CREATE TABLE `"+TABLE_NAME+"` ( 	`id` INTEGER PRIMARY KEY AUTOINCREMENT,	`time`	INTEGER NOT NULL,	`value`	NUMERIC,	`type`	INTEGER,	`category`	TEXT);";
	
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
			
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
			onCreate(db);
			
		}		
		
	}
	
	public DataHandler open(){
		db = dbhelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		dbhelper.close();
	}
	
	public long insertData(float value, int type, String category){
		ContentValues content = new ContentValues(); 
		int unixTime = (int)(System.currentTimeMillis() / 1000L);
		content.put(TIME, unixTime);
		content.put(VALUE, value);
		content.put(TYPE, type);
		content.put(CATEGORY, category);
		return db.insertOrThrow(TABLE_NAME, null, content);
	}
	
	public Cursor returnData(){
		return db.query(TABLE_NAME, new String[]{ID, TIME, VALUE,TYPE,CATEGORY}, null, null, null, null, "id desc");
	}

}
