package com.hc.android.huixin.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.baidu.android.bbalbs.common.a.d;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navisdk.adapter.BNCommonSettingParam;
import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;
import com.hc.android.huixin.BaiduNaviActivity;
import com.igexin.push.c.r;
import com.nostra13.universalimageloader.utils.L;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import junit.framework.Test;

public class BaiduSdkHelper {
	private static String TAG = "百度导航";
	private static String appId = "10618060";
	public static CoordinateType BAIDU_SDK_COORDINATE_TYPE = CoordinateType.BD09LL;// 坐标系
	public static final double MIN_DISTANCE = 60;//最小距离，小于60米不进行导航
	/**
	 * 计算两个经纬度间的距离
	 */
	public static double distanceLatLng(double latX, double lngX, double latY, double lngY) {
		LatLng x = new LatLng(latX, lngX);
		LatLng y = new LatLng(latY, lngY);
//		Log.e(TAG, "距离"+DistanceUtil.getDistance(x, y));
		return DistanceUtil.getDistance(x, y);
	}

	/**
	 * 初始化百度导航SDK
	 */
	public static void initNavi(Activity activity) {
		String mSDCardPath = Environment.getExternalStorageDirectory().toString();
		/*
		 * activity: 建议是应用的主Activity sdcardRootPath: 系统SD卡根目录路径 appFolderName:
		 * 应用在SD卡中的目录名 naviInitListener: 百度导航初始化监听器 ttsCallback:
		 * 外部TTS能力回调接口，若使用百度内置TTS能力，传入null即可 ttsHandler: 异步获取百度内部TTS播报状态
		 * ttsStateListener: 同步获取百度内部TTS播报状态
		 */
		BaiduNaviManager.getInstance().init(activity, mSDCardPath, "BNSDKSimpleDemo", new NaviInitListener() {
			@Override
			public void onAuthResult(int status, String msg) {
				String authinfo;
				if (0 == status) {
					authinfo = "key校验成功!";
				} else {
					authinfo = "key校验失败, " + msg;
				}
				Log.e(TAG, authinfo);
			}

			public void initSuccess() {
				Log.e(TAG, "百度导航引擎初始化成功");
				initSetting();
			}

			public void initStart() {
				Log.e(TAG, "百度导航引擎初始化开始");
			}

			public void initFailed() {
				Log.e(TAG, "百度导航引擎初始化失败");
			}
			// },null,null,null);
		}, null, ttsHandler, ttsPlayStateListener);
	}

	/**
	 * 初始化百度导航里语音播报
	 */
	private static void initSetting() {
		// 设置日夜模式 自动模式(DAY_NIGHT_MODE_AUTO) 白天模式( DAY_NIGHT_MODE_DAY) 夜晚模式(
		// DAY_NIGHT_MODE_NIGHT)
		BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_AUTO);
		// 设置显示路况条
		BNaviSettingManager
				.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
		// 设置语音播报模式 Novice新手模式 Quite静音 Veteran老手模式
		BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
		// 省电模式 自动(AUTO_MODE) 关闭模式(DISABLE_MODE) 开启模式(ENABLE_MODE)
		BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
		// 设置实时路况条 关(NAVI_ITS_OFF) 开(NAVI_ITS_ON )
		BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);

		// 必须设置APPID，否则会静音
		Bundle bundle = new Bundle();
		bundle.putString(BNCommonSettingParam.TTS_APP_ID, appId);
		BNaviSettingManager.setNaviSdkParam(bundle);
	}

	/**
	 * 内部TTS播报状态回传handler
	 */
	private static Handler ttsHandler = new Handler() {
		public void handleMessage(Message msg) {
			int type = msg.what;
			switch (type) {
			case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
				// showToastMsg("Handler : TTS play start");
				break;
			}
			case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
				// showToastMsg("Handler : TTS play end");
				break;
			}
			default:
				break;
			}
		}
	};
	/**
	 * 内部TTS播报状态回调接口
	 */
	private static BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

		@Override
		public void playEnd() {
			// showToastMsg("TTSPlayStateListener : TTS play end");
		}

		@Override
		public void playStart() {
			// showToastMsg("TTSPlayStateListener : TTS play start");
		}
	};

	/**
	 * 开启导航页面
	 */
	public static void openBaiduNavi(Activity activity, String endLng, String endLat, String endAddr) {
		String startLng = PreferenceUtil.getProjectLng();
		String startLat = PreferenceUtil.getProjectLat();
		String startAddr = PreferenceUtil.getProjectAddrStr();
		openBaiduNavi(activity, startLng, startLat, startAddr, endLng, endLat, endAddr);
	}

	/**
	 * 开启导航页面
	 */
	public static void openBaiduNavi(Activity activity, String startLng, String startLat, String startAddr,
			String endLng, String endLat, String endAddr) {
		if (TextUtils.isEmpty(startLng) || TextUtils.isEmpty(startLat)) {
			Toast.makeText(activity, "启动导航失败，获取不到当前位置经纬度信息", Toast.LENGTH_LONG).show();
			return;
		}
		if (TextUtils.isEmpty(endLng) || TextUtils.isEmpty(endLat)) {
			Toast.makeText(activity, "启动导航失败，无此项目经纬度信息", Toast.LENGTH_LONG).show();
			return;
		}
		try {
			Double sLat = Double.parseDouble(startLat);
			Double sLng = Double.parseDouble(startLng);
			Double eLat = Double.parseDouble(endLat);
			Double eLng = Double.parseDouble(endLng);
			if (distanceLatLng(sLat, sLng, eLat, eLng) < MIN_DISTANCE) {
				Toast.makeText(activity, "已到达目的地附近", Toast.LENGTH_LONG).show();
				return;
			}
			BNRoutePlanNode sNode = new BNRoutePlanNode(sLng, sLat, startAddr, null,
					BaiduSdkHelper.BAIDU_SDK_COORDINATE_TYPE);
			BNRoutePlanNode eNode = new BNRoutePlanNode(eLng, eLat, endAddr, null,
					BaiduSdkHelper.BAIDU_SDK_COORDINATE_TYPE);
			openBaiduNavi(activity, sNode, eNode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 开启导航页面
	 * 
	 * @param startNode
	 *            起始地坐标
	 * @param endNode
	 *            目的地坐标
	 */
	private static void openBaiduNavi(final Activity activity, final BNRoutePlanNode startNode,
			final BNRoutePlanNode endNode) {
		List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
		list.add(startNode);
		list.add(endNode);
		Log.e(TAG, "开始算路");
		BaiduNaviManager.getInstance().launchNavigator(activity, list, 1, true, new RoutePlanListener() {
			@Override
			public void onJumpToNavigator() {
				Log.e(TAG, "启动导航页面");
				Intent intent = new Intent(activity, BaiduNaviActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("routePlanStartNode", startNode);
				bundle.putSerializable("routePlanEndNode", endNode);
				intent.putExtras(bundle);
				activity.startActivity(intent);
			}

			@Override
			public void onRoutePlanFailed() {
				Log.e(TAG, "算路失败");
			}
		});
	}
}
