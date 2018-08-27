package com.king.photo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class SQLiteDb {

	private SQLiteDatabase mDB;
	private DatabaseHelper mDbHelper;
	/**
	 * 实例
	 */
	private static SQLiteDb mInstance;


	/*
	 *  打开数据库的操作。 在某些情况下我们会关闭数据库，所以我们先判断数据库是否被关闭了， 如果关闭则重新打开，否则直接使用数据库
	 */
	private SQLiteDb(Context context) {

		try {
			// db helper为空则创建一个，避免重复创建
			if (mDbHelper == null) {
				mDbHelper = new DatabaseHelper(context);
				Log.i("TAG", "创建数据库！");
			}
			if(mDB == null) {
				mDB = mDbHelper.getWritableDatabase();
			}
			
			// 数据库存在并处于打开状态那么直接使用
			if (mDB != null && mDB.isOpen()) {
				return;
			}
			
		} catch (SQLiteException e) {
			Log.w("SQLiteDb", "打开失败");

		}
	}
	//单例模式。synchronized同步代码块时，一个时间内只能有一个线程得到执行。另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。
	public synchronized static SQLiteDb getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new SQLiteDb(context);
		}
		return mInstance;
	}

	/**
	 * 获取DB对象。
	 * 
	 * @return
	 */
	public SQLiteDatabase getDB() {
		return mDB;
	}

	/**
	 * 关闭数据库的操作
	 */
	public void close() {
		Log.i("TAG", "关闭数据库！！");
		mDB.close();
		mDbHelper.close();
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	/**
	 * 清除数据库中的数。
	 */
	public void clearDataBase() {
		
	}
}