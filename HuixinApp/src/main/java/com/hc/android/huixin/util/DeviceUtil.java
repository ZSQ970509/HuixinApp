package com.hc.android.huixin.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

public class DeviceUtil {

	public static int getDeviceWidth(Activity act) {
		return act.getWindowManager().getDefaultDisplay().getWidth();
	}

	public static int getVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			int versionCode = info.versionCode;
			return versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static String getVersionName(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "1.0.0";
		}
	}

	public static String getImei(Context context) {
		TelephonyManager manager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
		return manager.getDeviceId();
	}

}
