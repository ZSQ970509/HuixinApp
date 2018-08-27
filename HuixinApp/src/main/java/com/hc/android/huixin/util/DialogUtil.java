package com.hc.android.huixin.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

public class DialogUtil {

	public static void showDialog(Context context, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg);
		builder.create().show();
	}

	public static ProgressDialog createProgressDialog(Context context, String msg) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setMessage(msg);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		return dialog;
	}
	public static void showInstallOpenDialog(final Context context, final ProgressDialog progressDialog) {
		new AlertDialog.Builder(context).setTitle("提示").setMessage("省站信息不全，是否继续配置？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						progressDialog.show();
					
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).setCancelable(false).create().show();
	}
	public static void showNfcSettingDialog(final Context context) {
		new AlertDialog.Builder(context).setTitle("提示").setMessage("您的手机未开启NFC功能，是否跳转到设置界面去开启？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						Intent intent = new Intent(Settings.ACTION_SETTINGS);
						context.startActivity(intent);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).setCancelable(false).create().show();
	}

}
