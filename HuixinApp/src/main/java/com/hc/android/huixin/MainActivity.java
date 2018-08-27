package com.hc.android.huixin;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.hc.android.huixin.binding.BindingMainActivity;
import com.hc.android.huixin.network.ModuleItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.BaiduSdkHelper;
import com.hc.android.huixin.util.DeviceUtil;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.view.DefaultDialog;
import com.hc.android.huixin.view.GuideGallery;
import com.hc.android.huixin.view.ImageAdapter;
import com.hc.android.huixin.view.LineGridView;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.king.photo.activity.BaseActivity;
import com.king.photo.activity.DriverMainActivity;
import com.king.photo.activity.PhotoMainActivity;
import com.king.photo.activity.SearchAddress;
import com.king.photo.activity.SettingActivity;
import com.king.photo.activity.TakeManagerMainActivity;
import com.king.photo.activity.TakePhoto2MainActivity;
import com.king.photo.activity.TakePhotoMainActivity;
import com.king.photo.activity.TowerCraneManagerActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class MainActivity extends BaseActivity {

	private GuideGallery images_ga;
	private int positon = 0;
	private Thread timeThread = null;
	public boolean timeFlag = true;
	private boolean isExit = false;
	public ImageTimerTask timeTaks = null;
	private int gallerypisition = 0;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if(PreferenceUtil.getIsBattery() && PreferenceUtil.getIsAutoStart()){

		}else{
			Intent intent = new Intent(MainActivity.this,PermissionsSettingsActivity.class);
			startActivity(intent);
		}
		initView();
		initShuffling();
		startShuffling();
		// 开启定位
		//((MyApplication) getApplication()).startLocation();
		//绑定个推,设置别名
		bindAliasPushManager(MainActivity.this, PreferenceUtil.getName() + "");
		isAttendance();
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		if(PreferenceUtil.getIsBattery() && PreferenceUtil.getIsAutoStart()){

		}else{
			Intent intent = new Intent(MainActivity.this,PermissionsSettingsActivity.class);
			startActivity(intent);
		}
	}
	private void isAttendance(){
		if( PreferenceUtil.getAttendandcePath()) {
			DefaultDialog.showDialogIntentAttend(MainActivity.this,"您有一段考勤路径还未上传是否前往查看？" ,AttendancePathActivity.class);
		}else if(PreferenceUtil.getAttendandcePathProjectHumen()){
			DefaultDialog.showDialogIntentAttend(MainActivity.this,"您有一段考勤路径还未上传是否前往查看？" ,AttendancePathProjectHumenActivity.class);
		}
	};
	/**
	 * 绑定个推别名和标签
	 * @return
	 */
	private void bindAliasPushManager(Context context, String name) {

		PushManager pushManager = PushManager.getInstance();
		pushManager.initialize(context);
		Tag[] tagParam = new Tag[1];
		Tag tag = new Tag();
		tag.setName(name);
		tagParam[0] = tag;
		int flag = pushManager.setTag(context, tagParam, name);

		// 绑定别名
		pushManager.bindAlias(context, name);
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		//((MyApplication) getApplication()).closeLocation();
		super.onDestroy();
	}

	private void initView() {
		findViewById(R.id.btn_setting).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				jumpActivity(SettingActivity.class, "");
			}
		});

		new MyAsyncTask().execute();
	}
	/**
	 * 获取用户列表
	 * @return
	 */
	private class MyAsyncTask extends AsyncTask<Void, Void, ArrayList<ModuleItem>> {

		@Override
		protected ArrayList<ModuleItem> doInBackground(Void... params) {
			String name = PreferenceUtil.getName();
			String password = PreferenceUtil.getPassword();
			return new NetworkApi().GetMDJsonByUserAndSysId(name, password);
		}

		@Override
		protected void onPostExecute(ArrayList<ModuleItem> result) {
			LineGridView grid = (LineGridView) findViewById(R.id.moduleGrid);
			ArrayList<ModuleItem> moduleList = new ArrayList<ModuleItem>();
			for (ModuleItem item : result) {

				switch (item.id) {
				case 1232:
					// 网络考勤
					item.imageId = R.drawable.btn_attendance_normal;
					item.jumpActivity = AttendanceManagerActivity.class;
					moduleList.add(item);
					break;
				case 1233:
					// 工作影像采集
					item.imageId = R.drawable.btn_regulatory_normal;
					item.jumpActivity = TakePhoto2MainActivity.class;
					moduleList.add(item);
					break;
				case 1234:
					// 试件跟踪
					item.imageId = R.drawable.btn_specimen_track;
					item.jumpActivity = BindingMainActivity.class;
					moduleList.add(item);

					break;
				case 1235:
					// 视频查看
					item.imageId = R.drawable.btn_video_normal;
					item.jumpActivity = VideoActivity.class;
					moduleList.add(item);
					break;
				case 1236:
					// 在线更新
					item.imageId = R.drawable.btn_update_normal;
					item.jumpActivity = AppUpdateActivity.class;
					moduleList.add(item);
					break;
				// case 1237:
				// // 关于
				// item.imageId = R.drawable.btn_about_normal;
				// item.jumpActivity = AboutActivity.class;
				// moduleList.add(item);
				// break;
				case 1238:
					// 历史照片
					item.imageId = R.drawable.btn_photo_history;
					item.jumpActivity = PhotoActivity.class;
					//item.jumpActivity = AttendancePathActivity.class;
					moduleList.add(item);
					break;
				case 1239:
					// 标签写入
					item.imageId = R.drawable.btn_nfc_normal;
					item.jumpActivity = WriteNfcActivity.class;
					moduleList.add(item);
					break;
				case 1243:
					// 监管影像采集
					item.imageId = R.drawable.btn_supervised;
					item.jumpActivity = TakePhotoMainActivity.class; 
					moduleList.add(item);
					break;
				case 1250:
					// 驾驶员管理
					item.imageId = R.drawable.btn_driver;
					// item.jumpActivity = DriverTakePhotoActivity.class;
					item.jumpActivity = DriverMainActivity.class;
					moduleList.add(item);
					break;
				case 1257:
					// 本地上传
					item.imageId = R.drawable.btn_photo_noamal;
					item.jumpActivity = PhotoMainActivity.class;
					moduleList.add(item);
					break;
				case 1256:
					// 项目定位
					item.imageId = R.drawable.btn_binding_normal;
					item.jumpActivity = ProjectPositionActivity.class;
					moduleList.add(item);
					break;
				case 1277:
					// 试件检测  该功能暂时使用
//					item.imageId = R.drawable.btn_specimen_detection;
//					item.jumpActivity = ValidateActivity.class;
//					moduleList.add(item);
					break;
				case 1362:
					item.imageId = R.drawable.btn_baidu_navi;
					item.jumpActivity = SearchAddress.class;
					moduleList.add(item);
					break;
				case 1287:
					// 人员管理
					item.imageId = R.drawable.btn_office_manager;
					// item.jumpActivity = ActivityAdjustProjectMain.class;
					item.jumpActivity = TakeManagerMainActivity.class;
					moduleList.add(item);
					break;
				case 1307:
					// 人员管理
					item.imageId = R.drawable.btn_device_management;
					// item.jumpActivity = ActivityAdjustProjectMain.class;
					item.jumpActivity = TowerCraneManagerActivity.class;
					moduleList.add(item);
					break;
				default:
					break;
				}
			}

			// 增加。。。
			ModuleItem tempItem = new ModuleItem();
			tempItem.imageId = R.drawable.btn_more;
			tempItem.jumpActivity = null;
			moduleList.add(tempItem);

			grid.setAdapter(new MyAdapter(moduleList));
		}

	}

	private class MyAdapter extends BaseAdapter {

		private ArrayList<ModuleItem> moduleList = new ArrayList<ModuleItem>();

		public MyAdapter(List<ModuleItem> list) {
			if (list != null && list.size() > 0) {
				moduleList.clear();
				moduleList.addAll(list);
			}
		}

		@Override
		public int getCount() {
			return moduleList.size();
		}

		@Override
		public Object getItem(int position) {
			return moduleList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_item, null);
			final ModuleItem item = moduleList.get(position);
			((ImageView) itemView.findViewById(R.id.module_img)).setBackgroundResource(item.imageId);
			((TextView) itemView.findViewById(R.id.module_text)).setText(item.name);
			itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (item.jumpActivity == null) {

						return;
					}
					/*if(item.id == 1238){
						if(MyApplication.isAttendancePathProjectHumen){
							DialogUtil.showDialog(MainActivity.this,"工程人员考勤路径已开启，请先结束考勤！");
							return;
						}
					}*/
					jumpActivity(item.jumpActivity, item.name);
				}
			});
			return itemView;
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		timeFlag = false;
	}

	@Override
	protected void onPause() {
		timeTaks.timeCondition = false;
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showExitDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showExitDialog() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage("确认退出吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				MainActivity.this.finish();
			}
		}).setNegativeButton("取消", null);
		builder.show();
	}
	
	/**
	 * 图片选择
	 * 已经没有使用，控件被隐藏
	 * @param cur
	 */
	public void changePointView(int cur) {
		LinearLayout pointLinear = (LinearLayout) findViewById(R.id.gallery_point_linear);
		View view = pointLinear.getChildAt(positon);
		View curView = pointLinear.getChildAt(cur);
		if (view != null && curView != null) {
			ImageView pointView = (ImageView) view;
			ImageView curPointView = (ImageView) curView;
			pointView.setBackgroundResource(R.drawable.feature_point);
			curPointView.setBackgroundResource(R.drawable.feature_point_cur);
			positon = cur;
		}
	}

	private void jumpActivity(Class<?> cls, String title) {
		Intent intent = new Intent(this, cls);
		intent.putExtra("title", title);
		startActivity(intent);
	}

	/**
	 * 开始图片周期性滑动
	 */
	private void startShuffling() {
		timeTaks = new ImageTimerTask();
		autoGallery.scheduleAtFixedRate(timeTaks, 5000, 5000);
		timeThread = new Thread() {
			public void run() {
				while (!isExit) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (timeTaks) {
						if (!timeFlag) {
							timeTaks.timeCondition = true;
							timeTaks.notifyAll();
						}
					}
					timeFlag = true;
				}
			};
		};
		timeThread.start();
	}

	/**
	 * 加载滑动图片
	 */
	private void initShuffling() {
		images_ga = (GuideGallery) findViewById(R.id.image_wall_gallery);
		images_ga.setMainActivity(this);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.about_1);
		int height = bmp.getWidth() / bmp.getHeight() * DeviceUtil.getDeviceWidth(this);
		images_ga.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, height));

		ImageAdapter imageAdapter = new ImageAdapter(this);
		images_ga.setAdapter(imageAdapter);
		images_ga.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Uri uri = Uri.parse("http://www.baidu.com/");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		LinearLayout pointLinear = (LinearLayout) findViewById(R.id.gallery_point_linear);
		pointLinear.setBackgroundColor(Color.argb(200, 135, 135, 152));
		for (int i = 0; i < 3; i++) {
			ImageView pointView = new ImageView(this);
			if (i == 0) {
				pointView.setBackgroundResource(R.drawable.feature_point_cur);
			} else
				pointView.setBackgroundResource(R.drawable.feature_point);
			pointLinear.addView(pointView);
		}
	}

	private Timer autoGallery = new Timer();
	private final Handler autoGalleryHandler = new Handler() {
		public void handleMessage(Message message) {
			super.handleMessage(message);
			switch (message.what) {
			case 1:
				images_ga.setSelection(message.getData().getInt("pos"));
				break;
			}
		}
	};

	public class ImageTimerTask extends TimerTask {
		public volatile boolean timeCondition = true;

		public void run() {
			synchronized (this) {
				while (!timeCondition) {
					try {
						Thread.sleep(100);
						wait();
					} catch (InterruptedException e) {
						Thread.interrupted();
					}
				}
			}
			try {
				gallerypisition = images_ga.getSelectedItemPosition() + 1;
				Message msg = new Message();
				Bundle date = new Bundle();
				date.putInt("pos", gallerypisition);
				msg.setData(date);
				msg.what = 1;
				autoGalleryHandler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
