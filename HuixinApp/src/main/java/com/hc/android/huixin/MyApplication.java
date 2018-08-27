package com.hc.android.huixin;

import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.hc.android.huixin.util.CrashHanlder;
import com.hc.android.huixin.util.PreferenceUtil;
import com.king.photo.db.AttendancePathDao;
import com.king.photo.db.AttendancePathlmpl;
import com.king.photo.model.AttendancePathModel;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.onesafe.util.BaseImageDecoder;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class MyApplication extends Application  {

	private static final String TAG = "MyApplication";

	public static final String YCEXIT = "YCEXIT";
	public static final String ZCEXIT = "ZCEXIT";

	// Context对象
	private Context mContext;
	// Activity列表
	private List<Activity> list = new LinkedList<Activity>();
	private ArrayList<Activity> addHumanList = new ArrayList<Activity>();
	private Throwable ex;
	private List<String> videoList;
	public static AttendancePathDao attendancePathDao;
	private static MyApplication mInstance;

	private String packName;

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public List<String> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<String> videoList) {
		this.videoList = videoList;
	}

	private List<String> recordList;

	public List<String> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<String> recordList) {
		this.recordList = recordList;
	}
	public void addHumanActivity(Activity activity) {
		Log.e("addHumanList", "111111");

		addHumanList.add(activity);
		Log.e("addHumanList", addHumanList.size()+"");
	}
	public void removeHumanActivity() {
		Log.e("addHumanList", addHumanList.size()+"");
		 for (Activity activityList : addHumanList)
		    {
			 Log.e("addHumanList", activityList.getLocalClassName());
			 activityList.finish();
		    }
		    if (addHumanList.size() == 0)
		    	addHumanList.clear();

	}
	// 把Activity加入List栈管理
	public void addActivity(Activity activity) {
		list.add(activity);

	}

	// 把Activit移出List栈管理
	public void removeActivity(Activity activity) {
		if (activity != null) {
			list.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	// 退出应用
	public void exit(String flag) {
		// 键值对数据临时不处理
		// SharedPreferenceUtil.sharedPreferenceUtil
		// .removeKey(SharedPreferenceUtil.USER_SPORGID);
		try {
			for (Activity activity : list) {
				if (activity != null) {
					activity.finish();
				}
			}
		} catch (Exception e) {
		} finally {
			if (flag.equals(ZCEXIT)) {
				System.exit(0);
			} else {
				// if (isNetWorkAvailable(mContext)) {
				// Intent logRecordSaveService = new Intent(this,
				// LogRecordSaveService.class);
				// logRecordSaveService.putExtra(
				// LogRecordSaveService.CLASS_NAME, ex.getClass()
				// .getSimpleName());
				// logRecordSaveService.putExtra(
				// LogRecordSaveService.ERR_INFO, ex.getMessage());
				// logRecordSaveService.putExtra(
				// LogRecordSaveService.METHOD_NAME,
				// String.valueOf(ex.getCause()));
				// logRecordSaveService.putExtra(
				// LogRecordSaveService.EXIT_FLAG, true);
				// startService(logRecordSaveService);
				//
				// } else {
				// android.os.Process.killProcess(android.os.Process.myPid());
				// System.exit(0);
				// }
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}
		}

	}

	/**
//	 * 异常处理方法
//	 */
//	@Override
//	public void uncaughtException(Thread thread, Throwable ex) {
//		// 提交
//		doCrashReport(thread, ex);
//	}

//	/**
//	 * 上报错误信息
//	 *
//	 * @param report
//	 */
//	private void doCrashReport(Thread thread, Throwable ex) {
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			Log.e(TAG, e.getMessage());
//		}
//		// 退出程序
//		exit(YCEXIT);
//	}


	
	public static MyApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

	public Boolean isNetWorkAvailable(Context mContext) {
		boolean flag = false;
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager.getActiveNetworkInfo() != null) {
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		return flag;

	}

	// 是否启动了定位API
	private boolean isOpenLocation = false;
	// 定位相关
	LocationClient mLocClient;
	
	public MyLocationListenner myListener = new MyLocationListenner();
	/**
	 * 获取线程名
	 * @return
	 */
	private String getCurProcessName() {
		int pid = android.os.Process.myPid();
		ActivityManager activityManager = (ActivityManager) mInstance.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> runningApps = activityManager.getRunningAppProcesses();
		if (runningApps == null) {
			return "";
		}
		for (ActivityManager.RunningAppProcessInfo appProcess : runningApps) {
			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return "";
	}
	@Override
	public void onCreate() {
		super.onCreate();
		CrashHanlder.getInstance().init(this);
		mContext = getApplicationContext();
        mInstance = this;
		if (getCurProcessName().equals(getPackageName())) {
			startLocation();
		}

		attendancePathDao = new AttendancePathlmpl();
		SDKInitializer.initialize(getApplicationContext());//初始化百度计算工具SDK 
		initImageLoader(getApplicationContext());
	}
	@Override
	public void onTerminate() {
		// 程序终止的时候执行

		super.onTerminate();
		closeLocation();
	}

	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs().imageDecoder(new BaseImageDecoder(true)) // default
				.build();
		ImageLoader.getInstance().init(config);
	}

	/**
	 * start定位
	 */
	public void startLocation() {
		try {
			if (!isOpenLocation) // 如果没有打开
			{
				if(mLocClient!=null ) {
					if(!mLocClient.isStarted()){
						mLocClient.start();// 定位SDK
						// start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
						mLocClient.requestLocation();
						isOpenLocation = true; // 标识为已经打开了定位
					}
					return;
				}
				// 定位初始化
				mLocClient = new LocationClient(this);

				mLocClient.registerLocationListener(myListener);
				LocationClientOption option = new LocationClientOption();
				option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
				option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系，
				option.setScanSpan(30 * 1000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
				option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
				option.setOpenGps(true);// //可选，默认false,设置是否使用gps
				option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
				option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
				option.setIsNeedLocationDescribe(true);
				option.setAddrType("all");
				mLocClient.setLocOption(option);
				mLocClient.start();// 定位SDK
				// start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
				mLocClient.requestLocation();
				isOpenLocation = true; // 标识为已经打开了定位
			}

		} catch (Exception e) {
			Log.i(TAG, "打开定位异常" + e.toString());
		}
	}

	/**
	 * end 定位
	 */
	public void  closeLocation() {
		try {
			mLocClient.stop(); // 结束定位
			isOpenLocation = false; // 标识为已经结束了定位
		} catch (Exception e) {
			Log.i(TAG, "结束定位异常" + e.toString());
		}
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public synchronized  void  onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			Log.d("百度地图","  "+location.getLatitude());
			Log.d("百度地图","  "+location.getLongitude());
			Log.d("百度地图","  "+location.getAddrStr());
			PreferenceUtil.saveProjectLat( location.getLatitude() + "");
			PreferenceUtil.saveProjectLng( location.getLongitude() + "");
			PreferenceUtil.saveProjectAddrStr( location.getAddrStr());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(System.currentTimeMillis());
			try {
				Calendar currentDate = Calendar.getInstance();

				if (!PreferenceUtil.getTime().equals("") && (currentDate.getTime().getTime() - Long.parseLong(PreferenceUtil.getTime())) < 180000) {
					return;
				}
				if( PreferenceUtil.getAttendandcePathProjectHumen() ||  PreferenceUtil.getAttendandcePath()){
					PreferenceUtil.saveTime(currentDate.getTime().getTime()+"");
					if(location.getLatitude() == 4.9E-324 ||location.getLongitude() ==  4.9E-324){
						if (attendancePathDao.loadPhysiological().size() != 0) {
							attendancePathDao.addPhysiological(new AttendancePathModel(simpleDateFormat.format(date), attendancePathDao.loadPhysiological().get(attendancePathDao.loadPhysiological().size() - 1).getAttendancePathLng(), attendancePathDao.loadPhysiological().get(attendancePathDao.loadPhysiological().size() - 1).getAttendancePathLat()));
						}
					}else{
						if( location.getLongitude()<location.getLatitude()){
							attendancePathDao.addPhysiological(new AttendancePathModel(simpleDateFormat.format(date), location.getLatitude() + "", location.getLongitude() + ""));
						}else{
							attendancePathDao.addPhysiological(new AttendancePathModel(simpleDateFormat.format(date), location.getLongitude() + "", location.getLatitude() + ""));
						}
					}

					Log.e("11111",attendancePathDao.loadPhysiological().get(attendancePathDao.loadPhysiological().size()-1).toString());
				}
			}catch (Exception e){
				try {
					attendancePathDao.addPhysiological(new AttendancePathModel(simpleDateFormat.format(date), attendancePathDao.loadPhysiological().get(attendancePathDao.loadPhysiological().size() - 1).getAttendancePathLng(), attendancePathDao.loadPhysiological().get(attendancePathDao.loadPhysiological().size() - 1).getAttendancePathLat()));
				}catch (Exception e1){
					e1.printStackTrace();
				}

			}

		}

		public void onReceivePoi(BDLocation poiLocation) {

		}
	}

}
