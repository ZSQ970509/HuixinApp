package com.hc.android.huixin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.hc.android.huixin.network.CameraItem;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.MaintenanceTypeItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ResponsibilitySubjectItem;
import com.hc.android.huixin.network.SendDataBean;
import com.hc.android.huixin.network.UsersItem;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.CustomDialog;
import com.king.photo.model.WeiHuProjectModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MaintenanceActivity extends Activity implements OnClickListener {

	private View mTakePhotoView;
	private View mShowPhotoView;
	private ImageView mPhotoImage;
	private Handler mHandler = new Handler();
	private Spinner sResponsibilitySubject;
	private EditText mProjectCameraBy;
	private String mPhotoUri;
	WeiHuProjectModel.DataBean  dataBean  ;
	ArrayList<ResponsibilitySubjectItem> ResponsibilitySubjectList = new ArrayList<ResponsibilitySubjectItem>();
	private TextView mTextViewProjectName;
	private TextView mTextViewProjectType;
	private TextView mTextViewProgressNote;
	private TextView mTextViewLocalPostion;
	private TextView mTextViewTime;
	private TextView mTextViewSelectCamera;
	private TextView mTextViewUsers;
	private TextView txt_responsibility_subject;
	private TextView tvWeiHuProjectName;
//	private EditText mEditUsers;
	private EditText mKeyWork;
	private String mProjcLat = "0.0";
	private String mProjcLng = "0.0";

	private String mDeviceName = "";
	private String mProjectType = "";
	private int mProjectTypeId = 0;
	private String mProgressNote = "";
	private String mUsers = "";
	private String sResponsibilitySubjectText = "";
	private String sResponsibilitySubjectId = "";
	// 设备名称
	private String mSelectCamera = "";
	private int mPayId = 0;
	private String mProgress = "1";
	private int isPay = 0;
	private int mSavePostion = 0;
	// private String MaintenanceSituation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wei_hu);
		initView();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
		mProjectType = getIntent().getStringExtra("ProjectType");
		mProjectTypeId = getIntent().getIntExtra("ProjectTypeId", 0);
		dataBean = (WeiHuProjectModel.DataBean)getIntent().getSerializableExtra("dataBean");
		Log.e("dataBean",dataBean.getProjectName());
		Log.e("dataBean",dataBean.getProjectId());
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(mProjectType);

		findViewById(R.id.regulatory_back).setOnClickListener(this);
		findViewById(R.id.send_photo).setOnClickListener(this);
		mTakePhotoView = findViewById(R.id.send_photo_view);
		mShowPhotoView = findViewById(R.id.show_photo_view);
		tvWeiHuProjectName = (TextView) findViewById(R.id.tvWeiHuProjectName);
		tvWeiHuProjectName.setText(dataBean.getProjectName());
		findViewById(R.id.take_photo).setOnClickListener(this);

		mPhotoImage = (ImageView) findViewById(R.id.photo_image);
		sResponsibilitySubject = (Spinner) findViewById(R.id.spinner_responsibility_subject);
		mProjectCameraBy = (EditText) findViewById(R.id.edit_project_camera_by);

		mKeyWork = (EditText) findViewById(R.id.edit_project_name);

		mTextViewProjectName = (TextView) findViewById(R.id.txt_project_name);
		mTextViewProjectType = (TextView) findViewById(R.id.txt_project_type);
		mTextViewProjectType.setText(mProjectType);
		mTextViewProgressNote = (TextView) findViewById(R.id.txt_progress_note);
		mTextViewLocalPostion = (TextView) findViewById(R.id.txt_local_position);
		mTextViewTime = (TextView) findViewById(R.id.txt_time);
//		mEditUsers = (EditText) findViewById(R.id.edit_users);
		mTextViewSelectCamera = (TextView) findViewById(R.id.txt_select_camera);
		mTextViewUsers = (TextView) findViewById(R.id.txt_usesr);
		txt_responsibility_subject = (TextView) findViewById(R.id.txt_responsibility_subject);
		RadioGroup group = (RadioGroup) this.findViewById(R.id.progress);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				if (arg1 == R.id.progress_no_over) {
					mProgress = "0";
				} else {
					mProgress = "1";
				}
			}
		});

		RadioGroup payRadioGroup = (RadioGroup) this.findViewById(R.id.ispay);
		payRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				if (arg1 == R.id.ispay_no_over) {
					isPay = 0;
				} else {
					isPay = 1;
				}
			}
		});
		initResponsibilitySubject();
		initProgressNote();
		initProjectCameraBy();
		initProjectName();
		NetworkApi.queryProjectCameraBy(dataBean.getProjectId(), String.valueOf(mProjectTypeId), new INetCallback() {
			@Override
			public void onCallback(boolean value, String result) {
				if (value) {
					mCameraList.clear();
					mCameraList.addAll(NetworkApi.parstToCameraList(result));
					items = new String[mCameraList.size()];
					checkedItems = new boolean[mCameraList.size()];
					for (int i = 0; i < mCameraList.size(); i++) {
						items[i] = mCameraList.get(i).CamName;
						checkedItems[i] = false;
					}

					mHandler.post(new Runnable() {
						@Override
						public void run() {
							mProjectCameraBy.setText("");
							mSelectCameraId = "";
						}
					});
				}
			}
		});
//		new UsersAsyncTask().execute();
	}

	private void initProgressNote() {
		new ProgressNoteAsyncTask().execute();
	}

	// 获取进度描述
	private class ProgressNoteAsyncTask extends AsyncTask<Void, Void, ArrayList<MaintenanceTypeItem>> {

		@Override
		protected ArrayList<MaintenanceTypeItem> doInBackground(Void... params) {
			return new NetworkApi().GetMaintenanceType(String.valueOf(mProjectTypeId));
		}

		@Override
		protected void onPostExecute(final ArrayList<MaintenanceTypeItem> data) {
			if (data == null) {
				return;
			}

			// --给维护类型加一个其他选项
			if (mProjectTypeId == 169) {
				MaintenanceTypeItem item = new MaintenanceTypeItem();
				item.id = 0;
				item.text = "其他";
				data.add(item);
			}
			String[] nameData = new String[data.size()]; // 在维护里加一个其他
			for (int i = 0; i < nameData.length; i++) {
				nameData[i] = data.get(i).text;
			}

			Spinner spinner = (Spinner) findViewById(R.id.spinner_progress_note);
			mProgressNote = data.get(0).text;
			mPayId = data.get(0).id;
			mTextViewProgressNote.setText(mProgressNote);

			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MaintenanceActivity.this,
					android.R.layout.simple_spinner_item, nameData);
			spinner.setAdapter(adapter);
			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

					mPayId = data.get(position).id;
					mProgressNote = data.get(position).text;
					mTextViewProgressNote.setText(mProgressNote);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
		}
	}

	private String[] items = new String[] {};
	private boolean[] checkedItems = new boolean[] {};
	private ListView multiChoiceListView;
	private String mSelectCameraId = "";

	// 点位选择
	private void initProjectCameraBy() {
		findViewById(R.id.camera_by).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MaintenanceActivity.this);
				builder.setMultiChoiceItems(items, checkedItems, null);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						String msg = "";
						mSelectCamera = "";
						mSelectCameraId = "";
						for (int i = 0; i < items.length; i++) {
							if (multiChoiceListView.getCheckedItemPositions().get(i)) {
								if (!TextUtils.isEmpty(msg)) {
									msg += "|";
									mSelectCameraId += "|";
								}
								msg += mCameraList.get(i).CamName;
								mSelectCameraId += mCameraList.get(i).CamId;
							}
						}
						mSelectCamera = msg;
						mProjectCameraBy.setText(msg);
					}
				});
				AlertDialog dialog = builder.create();
				multiChoiceListView = dialog.getListView();
				dialog.show();
			}
		});
	}

	private ArrayList<CameraItem> mCameraList = new ArrayList<CameraItem>();
	private boolean isBind = false;

	private void initResponsibilitySubject() {
		ResponsibilitySubjectList.add(new ResponsibilitySubjectItem("-1", "待定"));
		ResponsibilitySubjectList.add(new ResponsibilitySubjectItem("2", "汇川"));
		ResponsibilitySubjectList.add(new ResponsibilitySubjectItem("1", "运营商"));
		ResponsibilitySubjectList.add(new ResponsibilitySubjectItem("0", "用户"));
		String[] nameData = new String[ResponsibilitySubjectList.size()]; // 在维护里加一个其他
		for (int i = 0; i < nameData.length; i++) {
			nameData[i] = ResponsibilitySubjectList.get(i).text;
		}
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MaintenanceActivity.this,
				R.layout.simple_spinner_item, nameData);
		sResponsibilitySubject.setAdapter(adapter);
		sResponsibilitySubjectText = ResponsibilitySubjectList.get(0).text;
		sResponsibilitySubjectId = ResponsibilitySubjectList.get(0).id;
		txt_responsibility_subject.setText(sResponsibilitySubjectText);
		sResponsibilitySubject.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				sResponsibilitySubjectId = ResponsibilitySubjectList.get(position).id;
				sResponsibilitySubjectText = ResponsibilitySubjectList.get(position).text;
				txt_responsibility_subject.setText(sResponsibilitySubjectText);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	private void initProjectName() {
		if (mProjectTypeId == 230
				&& (TextUtils.isEmpty(dataBean.getProjectLat()) || TextUtils.isEmpty(dataBean.getProjectLng()))) {
			isBind = true;
		} else {
			isBind = false;
		}
	}

	private ListView user_multiChoiceListView;

	// 随行人员
//	private class UsersAsyncTask extends AsyncTask<Void, Void, ArrayList<UsersItem>> {
//
//		@Override
//		protected ArrayList<UsersItem> doInBackground(Void... params) {
//			return new NetworkApi().GetUsers();
//		}
//
//		@Override
//		protected void onPostExecute(final ArrayList<UsersItem> data) {
//			if (data == null) {
//				return;
//			}
//			final String[] user_items = new String[data.size()];
//			final boolean[] user_checkedItems = new boolean[data.size()];
//			for (int i = 0; i < data.size(); i++) {
//				user_items[i] = data.get(i).UserName;
//				user_checkedItems[i] = false;
//			}
//			findViewById(R.id.btn_users).setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					AlertDialog.Builder builder = new AlertDialog.Builder(MaintenanceActivity.this);
//					builder.setMultiChoiceItems(user_items, user_checkedItems, null);
//					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							dialog.dismiss();
//							String msg = "";
//							mUsers = "";
//							for (int i = 0; i < user_items.length; i++) {
//								if (user_multiChoiceListView.getCheckedItemPositions().get(i)) {
//									if (!TextUtils.isEmpty(msg)) {
//										msg += "|";
//										mUsers += "|";
//									}
//									msg += data.get(i).UserName;
//									mUsers += data.get(i).UserName;
//								}
//							}
//							mEditUsers.setText(msg);
//						}
//					});
//					AlertDialog dialog = builder.create();
//					user_multiChoiceListView = dialog.getListView();
//					dialog.show();
//				}
//			});
//		}
//	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regulatory_back:
			back();
			break;
		case R.id.take_photo:

			// 先判断是否项目定位，没有就绑定定位，然后在takePhoto();
			Update();
			break;
		case R.id.send_photo:
			sendPhoto();
			break;
		default:
			break;
		}
	}

	/**
	 * 判断该项目原来是否绑定、是否为已经到达、定位是否开启
	 */
	private void Update() {
		getLocalPosition();

		// 已经绑定、已经到达现场
		if (isBind && mPayId == 233) {
			showBindProjectLocationDialog();

		} else {// 未绑定

			takePhoto();
		}
	}

	private void showBindProjectLocationDialog() {
		CustomDialog.Builder builder = new CustomDialog.Builder(MaintenanceActivity.this);
		builder.setMessage(
				"发现当前工程还未绑定经纬度信息，是否绑定？" + "\n当前位置:" + PreferenceUtil.getProjectAddrStr());
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
				// 绑定 项目定位
				savePosition();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				takePhoto();
			}
		});
		builder.create().show();
	}

	private void savePosition() {
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在保存...");
		dialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				String projid = dataBean.getProjectId();
				NetworkApi.uploadDataForSavePosition(MaintenanceActivity.this, projid, mProjcLat, mProjcLng,
						new INetCallback() {
							@Override
							public void onCallback(boolean value, String result) {
								LogUtil.logD("sendData onCallback" + result);
								if (value) {
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											ToastHelp.showToast(MaintenanceActivity.this, "发送成功！");
										}
									});
								} else {
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											ToastHelp.showToast(MaintenanceActivity.this, "发送失败！");
										}
									});
								}
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										dialog.cancel();
										// 进行拍照
										takePhoto();
									}
								});
							}
						});
			}
		}).start();
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
				ResponsibilitySubjectItem item_ResponsibilitySubject = ResponsibilitySubjectList
						.get(sResponsibilitySubject.getSelectedItemPosition());

				Bitmap bitmap = ImageUtil.scaleImage(mPhotoUri, 500f, 500f);
				if (bitmap == null) {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							ToastHelp.showToast(MaintenanceActivity.this, "发送失败！");
							dialog.cancel();
						}
					});
					return;
				}
				String imgStr = ImageUtil.convertIconToString(bitmap);
				bitmap.recycle();
				bitmap = null;

				SendDataBean data = new SendDataBean();
				data.ResponsibilitySubject = item_ResponsibilitySubject.id;
				data.ImgStr = imgStr;
				data.Note = mProgressNote;
				data.ProjectId = dataBean.getProjectId();
				data.ProjectName = dataBean.getProjectName();
				data.UserName = PreferenceUtil.getName();
				data.ProjcLat = mProjcLat;
				data.ProjcLng = mProjcLng;
				data.Type = mProjectType;
				data.CameraId = mSelectCameraId;
				data.Progress = mProgress;
				data.IsSaveLocation = mSavePostion;
				data.ispay = isPay;
				data.projectTypeId = mProjectTypeId;
				data.payId = mPayId;

				NetworkApi.sendInsideData(MaintenanceActivity.this, data, new INetCallback() {
					@Override
					public void onCallback(boolean value, String result) {
						LogUtil.logD("sendData onCallback" + result);
						if (value) {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									ToastHelp.showToast(MaintenanceActivity.this, "发送成功！");
								}
							});
							mHandler.postDelayed(new Runnable() {
								@Override
								public void run() {
									finish();
								}
							}, 2000);
						} else {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									mPhotoImage.setImageBitmap(null);
									mShowPhotoView.setVisibility(View.GONE);
									mTakePhotoView.setVisibility(View.VISIBLE);
									ToastHelp.showToast(MaintenanceActivity.this, "发送失败！");
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
		if (TextUtils.isEmpty(mSelectCamera) && mProjectTypeId != 230) {
			ToastHelp.showToast(this, "设备名称不能为空！");
			return;
		}
		if (sResponsibilitySubjectText.equals("待定") && mProjectTypeId == 169) {
			ToastHelp.showToast(this, "责任主体不能为待定！");
			return;
		}
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String dirName = Environment.getExternalStorageDirectory() + "/huixin";
		File f = new File(dirName);
		if (!f.exists()) {
			f.mkdir();
		}
		if (mPayId == 0) {
			if (mProgressNote == null || "".equals(mProgressNote)) {
				ToastHelp.showToast(this, "请填写维护情况");
				return;
			}
			mTextViewProgressNote.setText(mProgressNote);
		}
		String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance().getTime()) + ".jpg";
		mPhotoUri = dirName + "/" + name;
		File file = new File(dirName, name);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		startActivityForResult(intent, 0);
	}

	private void getLocalPosition() {
		mProjcLat = PreferenceUtil.getProjectLat();
		mProjcLng = PreferenceUtil.getProjectLng();
		String addr = PreferenceUtil.getProjectAddrStr();
		mTextViewLocalPostion.setText("经度：" + mProjcLng + " 纬度：" + mProjcLat + "\n地址：" + addr);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		/*
		 * savedInstanceState.putString("msg_con", htmlsource);
		 * savedInstanceState.putString("msg_camera_picname", camera_picname);
		 */Log.e("huixin", "走了save");					
		
		super.onSaveInstanceState(savedInstanceState); // 实现父类方法 放在最后
							// 防止拍照后无法返回当前activity
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.e("huixin", "走了return");
		super.onRestoreInstanceState(savedInstanceState);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, mData);
		if (resultCode == Activity.RESULT_OK) {

			try {
				if (!(new File(mPhotoUri)).exists()) {
					Log.e("onActivityResult", "mPhotoUri not exists!");
					ToastHelp.showToast(this, "拍照失败！");
					return;
				}

				mShowPhotoView.setVisibility(View.VISIBLE);
				mTakePhotoView.setVisibility(View.GONE);
				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.ARGB_8888)
						.cacheInMemory(false).cacheOnDisc(true).build();
				ImageLoader.getInstance().displayImage("file://" + mPhotoUri, mPhotoImage, options);
				mTextViewProjectName.setText(dataBean.getProjectName());
				SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
				String time = df.format(new Date());
				mTextViewTime.setText(time);
				mTextViewUsers.setText(mUsers);
				mTextViewSelectCamera.setText(mSelectCamera);

				if (mProjectTypeId == 230) {
					File file = new File(mPhotoUri);
					// 人脸识别。
					// Distinguish(new File(mPhotoUri));
				}

			} catch (Exception e) {
				e.printStackTrace();
				ToastHelp.showToast(this, "拍照失败，请重新拍照！");
			}
		} else {
			ToastHelp.showToast(this, "拍照失败，请重新拍照！");
		}
	}



}
