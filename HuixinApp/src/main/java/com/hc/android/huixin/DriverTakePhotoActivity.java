package com.hc.android.huixin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.hc.android.huixin.network.CarItem;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectItem;
import com.hc.android.huixin.network.SendDataBean;
import com.hc.android.huixin.network.UsersItem;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;

/**
 * 驾驶员专用
 */
public class DriverTakePhotoActivity extends Activity implements OnClickListener {

	private View mTakePhotoView;
	private View mShowPhotoView;
	private ImageView mPhotoImage;
	private Handler mHandler = new Handler();
	private Spinner mProjectNameList;
	private Spinner mCarSpinner;
	private String mPhotoUri;
	private ArrayList<ProjectItem> mData = new ArrayList<ProjectItem>();

	// 工程名称
	private TextView mProjectName;
	private TextView mLocalPostion;
	private TextView mTextTime;
	private EditText mKeyWork;
	private EditText mEditBuildNum;
	private String mProjcLat = "0.0";
	private String mProjcLng = "0.0";

	private String mUsers;
	// 车牌号
	private String mCar = "";
	private String mTypeName = "出发";
	private String mBuildNum;
	// 随行人员
	private EditText mEditUsers;
	// 车辆公里数
	private TextView mTxtBuildNum;
	// 工作类型
	private TextView mTxtTypeName;
	// 随行人员(展示界面)
	private TextView mTextUsers;
	// 车牌号
	private TextView mTextCar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo_driver);
		initView();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void back() {
		finish();
	}

	private void initView() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		findViewById(R.id.regulatory_back).setOnClickListener(this);
		findViewById(R.id.send_photo).setOnClickListener(this);
		findViewById(R.id.search).setOnClickListener(this);
		mTakePhotoView = findViewById(R.id.send_photo_view);
		mShowPhotoView = findViewById(R.id.show_photo_view);

		findViewById(R.id.take_photo).setOnClickListener(this);

		mKeyWork = (EditText) findViewById(R.id.edit_project_name);
		mProjectNameList = (Spinner) findViewById(R.id.spinner_project_name);
		mCarSpinner = (Spinner) findViewById(R.id.spinner_car);
		mEditBuildNum = (EditText) findViewById(R.id.edit_BuildNum);

		mProjectName = (TextView) findViewById(R.id.txt_project_name);
		mEditUsers = (EditText) findViewById(R.id.edit_users);// 随行人员
		mLocalPostion = (TextView) findViewById(R.id.txt_local_position);
		mTextTime = (TextView) findViewById(R.id.txt_time);
		mPhotoImage = (ImageView) findViewById(R.id.photo_image);
		mTxtBuildNum = (TextView) findViewById(R.id.txt_BuildNum);
		mTxtTypeName = (TextView) findViewById(R.id.txt_type_name);
		mTextUsers = (TextView) findViewById(R.id.txt_note);
		mTextCar = (TextView) findViewById(R.id.txt_car);

		new UsersAsyncTask().execute();
		new CarAsyncTask().execute();
		// 工作类型
		RadioGroup postionRadio = (RadioGroup) this.findViewById(R.id.PlaceType);
		postionRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				if (arg1 == R.id.PlaceType_start) {
					mTypeName = "出发";
				} else {
					mTypeName = "到达 ";
				}
			}
		});
	}

	private ListView user_multiChoiceListView;

	// 随行人员
	private class UsersAsyncTask extends AsyncTask<Void, Void, ArrayList<UsersItem>> {

		@Override
		protected ArrayList<UsersItem> doInBackground(Void... params) {
			return new NetworkApi().GetUsers();
		}

		@Override
		protected void onPostExecute(final ArrayList<UsersItem> data) {
			if (data == null) {
				return;
			}
			final String[] user_items = new String[data.size()];
			final boolean[] user_checkedItems = new boolean[data.size()];
			for (int i = 0; i < data.size(); i++) {
				user_items[i] = data.get(i).UserName;
				user_checkedItems[i] = false;
			}
			findViewById(R.id.btn_users).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(DriverTakePhotoActivity.this);
					builder.setMultiChoiceItems(user_items, user_checkedItems, null);
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							String msg = "";
							mUsers = "";
							for (int i = 0; i < user_items.length; i++) {
								if (user_multiChoiceListView.getCheckedItemPositions().get(i)) {
									if (!TextUtils.isEmpty(msg)) {
										msg += "|";
										mUsers += "|";
									}
									msg += data.get(i).UserName;
									mUsers += data.get(i).UserName;
								}
							}
							mEditUsers.setText(msg);
						}
					});
					AlertDialog dialog = builder.create();
					user_multiChoiceListView = dialog.getListView();
					dialog.show();
				}
			});
		}
	}

	// 车牌号
	private class CarAsyncTask extends AsyncTask<Void, Void, ArrayList<CarItem>> {

		@Override
		protected ArrayList<CarItem> doInBackground(Void... params) {
			return new NetworkApi().GetDictByDirType();
		}

		@Override
		protected void onPostExecute(final ArrayList<CarItem> data) {
			if (data == null) {
				return;
			}
			int count = data.size();
			final String[] dataList = new String[count];
			for (int i = 0; i < count; i++) {
				dataList[i] = data.get(i).text;
			}
			mCar = dataList[0];
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DriverTakePhotoActivity.this,
					R.layout.simple_spinner_item, dataList);
			adapter.setDropDownViewResource(R.layout.simple_spinner_item);
			mCarSpinner.setAdapter(adapter);
			mCarSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					mCar = dataList[arg2];
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regulatory_back:
			back();
			break;
		case R.id.take_photo:
			takePhoto();
			break;
		case R.id.send_photo:
			sendPhoto();
			break;
		case R.id.search:
			searchProject();
			break;
		default:
			break;
		}
	}

	private void sendPhoto() {
		if (!HttpUtil.networkIsAvailable(this)) {
			ToastHelp.showToast(this, "手机没有网络连接！");
			return;
		}
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送图片...");
		dialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				ProjectItem item = mData.get(mProjectNameList.getSelectedItemPosition());
				Bitmap bitmap = ImageUtil.scaleImage(mPhotoUri, 500f, 500f);
				if (bitmap == null) {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							ToastHelp.showToast(DriverTakePhotoActivity.this, "发送失败！");
							dialog.cancel();
						}
					});
					return;
				}
				String imgStr = ImageUtil.convertIconToString(bitmap);
				bitmap.recycle();
				bitmap = null;

				SendDataBean data = new SendDataBean();
				data.ImgStr = imgStr;
				data.Note = mUsers;
				data.ProjectId = item.projectId;
				data.ProjectName = item.ProjectName;
				data.UserName = PreferenceUtil.getName();
				data.ProjcLat = mProjcLat;
				data.ProjcLng = mProjcLng;
				data.BuildNum = mBuildNum;
				data.PlaceType = mTypeName;
				data.CameraId = mCar;
				NetworkApi.sendDataDriver(DriverTakePhotoActivity.this, data, new INetCallback() {
					@Override
					public void onCallback(boolean value, String result) {
						LogUtil.logD("sendData onCallback" + result);
						if (value) {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									mPhotoImage.setImageBitmap(null);
									mShowPhotoView.setVisibility(View.GONE);
									mTakePhotoView.setVisibility(View.VISIBLE);
									ToastHelp.showToast(DriverTakePhotoActivity.this, "发送成功！");
								}
							});
						} else {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									ToastHelp.showToast(DriverTakePhotoActivity.this, "发送失败！");
								}
							});
						}
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								dialog.cancel();
							}
						});
					}
				});
			}
		}).start();
	}

	private void takePhoto() {
		getLocalPosition();
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			ToastHelp.showToast(this, "手机没有安装SD卡，不能拍照");
			return;
		}
		if (mData.size() <= 0) {
			ToastHelp.showToast(this, "项目名称为空！");
			return;
		}

		mBuildNum = mEditBuildNum.getText().toString().trim();
		if (TextUtils.isEmpty(mBuildNum)) {
			ToastHelp.showToast(this, "车辆公里数为空！");
			return;
		}

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String dirName = Environment.getExternalStorageDirectory() + "/huixin";
		File f = new File(dirName);
		if (!f.exists()) {
			f.mkdir();
		}
		String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance().getTime()) + ".jpg";
		mPhotoUri = dirName + "/" + name;
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(dirName, name)));
		startActivityForResult(intent, 0);
	}

	private void getLocalPosition() {
		mProjcLat = PreferenceUtil.getProjectLat();
		mProjcLng = PreferenceUtil.getProjectLng();
		String addr = PreferenceUtil.getProjectAddrStr();
		mLocalPostion.setText("经度：" + mProjcLng + " 纬度：" + mProjcLat + "\n地址：" + addr);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (!(new File(mPhotoUri)).exists()) {
				Log.e("onActivityResult", "mPhotoUri not exists!");
				ToastHelp.showToast(this, "拍照失败！");
				return;
			}
			try {
				mShowPhotoView.setVisibility(View.VISIBLE);
				mTakePhotoView.setVisibility(View.GONE);
				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.ARGB_8888)
						.cacheInMemory(false).cacheOnDisc(true).build();

				ImageLoader.getInstance().displayImage("file://" + mPhotoUri, mPhotoImage, options);
				ProjectItem item = mData.get(mProjectNameList.getSelectedItemPosition());
				mProjectName.setText(item.ProjectName);
				mTxtBuildNum.setText(mBuildNum);
				mTxtTypeName.setText(mTypeName);
				SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
				String time = df.format(new Date());
				mTextTime.setText(time);
				mTextUsers.setText(mUsers);
				mTextCar.setText(mCar);
			} catch (Exception e) {
				e.printStackTrace();
				ToastHelp.showToast(this, "拍照失败，请重新拍照！");
			}
		} else {
			ToastHelp.showToast(this, "拍照失败，请重新拍照！");
		}
	}

	private void searchProject() {
		final String name = mKeyWork.getText().toString();
		if (TextUtils.isEmpty(name)) {
			return;
		}
		mData.clear();
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在获取工程名称...");
		dialog.show();
		dialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				return true;
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				NetworkApi.queryProjectData(DriverTakePhotoActivity.this, name, "0", new INetCallback() {
					@Override
					public void onCallback(boolean value, String result) {
						if (value) {
							mData.addAll(NetworkApi.parstToProjectList(result));
							int count = mData.size();
							if (count == 0) {
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										dialog.cancel();
										ToastHelp.showToast(getApplicationContext(), "搜索为空！");
									}
								});
								return;
							}
							String[] dataList = new String[count];
							for (int i = 0; i < count; i++) {
								dataList[i] = mData.get(i).ProjectName;
							}
							final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DriverTakePhotoActivity.this,
									R.layout.simple_spinner_item, dataList);
							adapter.setDropDownViewResource(R.layout.simple_spinner_item);
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									dialog.cancel();
									mProjectNameList.setAdapter(adapter);
								}
							});
						} else {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									dialog.cancel();
									ToastHelp.showToast(DriverTakePhotoActivity.this, "获取工程名称失败！");
								}
							});
						}
					}
				});
			}
		}).start();
	}
}
