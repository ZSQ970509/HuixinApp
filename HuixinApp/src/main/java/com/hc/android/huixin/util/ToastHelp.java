package com.hc.android.huixin.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastHelp {

	public static void showToast(Context context, String text) {
		if (context == null) {
			return;
		}
		if (TextUtils.isEmpty(text)) {
			return;
		}
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	private static String oldMsg;
	protected static Toast toast = null;
	private static long oneTime = 0;
	private static long twoTime = 0;

	public static void showCurrentToast(Context context, String string) {
		// TODO Auto-generated method stub
		if (toast == null) {
			toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (string.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.show();
				}
			} else {
				oldMsg = string;
				toast.setText(string);
				toast.show();
			}
		}
		oneTime = twoTime;
	}
}
