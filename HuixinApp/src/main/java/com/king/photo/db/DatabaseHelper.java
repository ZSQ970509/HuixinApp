package com.king.photo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
/**
 * SQLiteOpenHelper的帮助类：创建数据库，创建表，更新表功能
 */
public class DatabaseHelper extends SQLiteOpenHelper{
	/**
	 * 数据库名
	 */
	public static final String DB_NAME="attendancepathdb";
	/**
	 * 创建数据库
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 2);
		Log.i("db", "创建数据库");
	}
	public DatabaseHelper(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
	}
	/**
	 * 创建表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(HealthyValuationTable.SQL_TABLE_CREATE);
		db.execSQL(HealthyValuationTable.SQL_APP_DATA_TABLE_CREATE);
//		db.execSQL(ABCTable.SQL_TABLE_CREATE);
		Log.i("db", "创建表");
	}
	
	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		return super.getWritableDatabase();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(HealthyValuationTable.SQL_TABLE_CREATE);
		db.execSQL(HealthyValuationTable.SQL_APP_DATA_TABLE_CREATE);
//		db.execSQL(ABCTable.SQL_TABLE_CREATE);
		Log.i("db", "创建表");
	}
}