package com.hc.android.huixin.view;

import com.hc.android.huixin.AttendancePathActivity;
import com.hc.android.huixin.MyApplication;
import com.hc.android.laddercontrolcamera.ShowPhotoActivity;
import com.king.photo.activity.CheckErrorCodeActivity;
import com.king.photo.activity.LadderControlDeviceChoosePlace;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

public class DefaultDialog {
	public static void showDialogIntent(final Context context, String text,final Intent intent) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(text);
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
				context.startActivity(intent);
				((Activity) context).finish();
			}
		});

		builder.create().show();
	}
	public static void showDialog(Context context, String text) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(text);
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
			}
		});

		builder.create().show();
	}
	public static void showDialogIntentAttend(final Context context, String text, final Class<?> cls) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(text);
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
				Intent intent = new Intent(context,cls);
				context.startActivity(intent);

			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		builder.create().show();
	}
	public static void showDialogIntentErr(final Context context, String text,final String errCode) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(text);
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
				
			}
		});
		builder.setNegativeButton("查询错误代码", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,CheckErrorCodeActivity.class);
				intent.putExtra("errCode", errCode);
				context.startActivity(intent);
			}
		});

		builder.create().show();
	}
	public static void showDialogIsFinish(final Context context, String text) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(text);
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
				((Activity) context).finish();
			}
		});

		builder.create().show();
	}
	public static void showDialogRemoveActivity(final Context context, String text) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(text);
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
				MyApplication myApplication = (MyApplication) ((Activity)context).getApplication();
				myApplication.removeHumanActivity();
			}
		});

		builder.create().show();
	}
	
}
