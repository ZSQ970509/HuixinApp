package com.king.photo.activity;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.Toast;

import com.hc.android.huixin.MyApplication;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		((MyApplication) getApplication()).addActivity(this);
		super.onCreate(savedInstanceState);
	}

	protected void openActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		intent.putExtras(bundle);
		startActivityForResult(intent, 1);
	}

	// Activity跳转
	protected void openActivity(Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		startActivityForResult(intent, 1);
	}

	// Activity跳转
	protected void openActivity(Class<?> cls, Boolean flags) {
		openActivity(cls);
		if (flags) {
			this.finish();
		}
	}

	/**
	 * 带返回值和结果码的 关闭Activity
	 * 
	 * @param data
	 * @param resultCode
	 */
	protected void finishActivity(Intent data, int resultCode) {
		setResult(resultCode, data);
		this.finish();
	}

	/**
	 * 得到屏幕的宽度
	 */
	protected int getWindowsWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		return width;
	}

	/**
	 * 得到屏幕的高度
	 */
	protected int getWindowsHeight() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int height = dm.heightPixels;
		return height;
	}

	/**
	 * 得到通知栏的高度
	 */
	public int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		((MyApplication) getApplication()).removeActivity(this);

	}

}
