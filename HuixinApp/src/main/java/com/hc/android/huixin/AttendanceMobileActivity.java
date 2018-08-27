package com.hc.android.huixin;

import java.io.File;
import java.util.Calendar;

import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.SendDataBean;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AttendanceMobileActivity extends Activity implements OnClickListener {

	// 考勤说明
	private EditText mEditAttendanceInfo;
	// 拍照
	private Button mBtnTakePhoto;
	private View mView1;

	private View mView2;
	private ImageView mPhotoImage;
	private TextView mTxtAttendanceInfo;
	private TextView mTxtGps;
	private TextView mTxtAddr;
	// 发送
	private Button mBtnSend;

	private String mPhotoUri;
	private String mProjcLat = "0.0";
	private String mProjcLng = "0.0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance_mobile);
		initView();
	}

	private void initView() {
		findViewById(R.id.attendance_back).setOnClickListener(this);
		mEditAttendanceInfo = (EditText) findViewById(R.id.attendance_info);
		mBtnTakePhoto = (Button) findViewById(R.id.btn_take_photo);
		mBtnTakePhoto.setOnClickListener(this);
		mView1 = findViewById(R.id.attendance_view1);

		mView2 = findViewById(R.id.attendance_view2);
		mPhotoImage = (ImageView) findViewById(R.id.img_take_photo);
		mTxtAttendanceInfo = (TextView) findViewById(R.id.txt_attendance_info);
		mTxtGps = (TextView) findViewById(R.id.txt_gps);
		mTxtAddr = (TextView) findViewById(R.id.txt_addr);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.attendance_back:
			finish();
			break;
		case R.id.btn_take_photo:
			takePhoto();
			break;
		case R.id.btn_send:
			sendPhoto();
			break;
		default:
			break;
		}
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
				mView2.setVisibility(View.VISIBLE);
				mView1.setVisibility(View.GONE);
				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.ARGB_8888)
						.cacheInMemory(false).cacheOnDisc(true).build();
				ImageLoader.getInstance().displayImage("file://" + mPhotoUri, mPhotoImage, options);
			} catch (Exception e) {
				e.printStackTrace();
				ToastHelp.showToast(this, "拍照失败，请重新拍照！");
			}
		} else {
			ToastHelp.showToast(this, "拍照失败，请重新拍照！");
		}
	}

	private void takePhoto() {
		String info = mEditAttendanceInfo.getText().toString().trim();
		if (TextUtils.isEmpty(info)) {
			ToastHelp.showToast(this, "请输入考勤说明");
			return;
		} else {
			mTxtAttendanceInfo.setText("考勤说明：" + info);
		}
		getLocalPosition();
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			ToastHelp.showToast(this, "手机没有安装SD卡，不能拍照");
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
		mTxtGps.setText("经度：" + mProjcLng + " 纬度：" + mProjcLat);
		mTxtAddr.setText("地址：" + addr);
	}

	private Handler mHandler = new Handler();

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
				Bitmap bitmap = ImageUtil.scaleImage(mPhotoUri, 500f, 500f);
				if (bitmap == null) {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							ToastHelp.showToast(AttendanceMobileActivity.this, "发送失败！");
							dialog.cancel();
						}
					});
					return;
				}
				String imgStr = ImageUtil.convertIconToString(bitmap);
				bitmap.recycle();
				bitmap = null;
				String note = mEditAttendanceInfo.getText().toString().trim();

				SendDataBean data = new SendDataBean();
				data.ImgStr = imgStr;
				data.Note = note;
				data.UserName = PreferenceUtil.getName();
				data.ProjcLat = mProjcLat;
				data.ProjcLng = mProjcLng;
				data.Province = PreferenceUtil.getProvince();
				NetworkApi.sendAttendanceMobileData(AttendanceMobileActivity.this, data, new INetCallback() {
					@Override
					public void onCallback(boolean value, String result) {
						LogUtil.logD("sendData onCallback" + result);
						if (value) {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									mPhotoImage.setImageBitmap(null);
									mView2.setVisibility(View.GONE);
									mView1.setVisibility(View.VISIBLE);
									ToastHelp.showToast(AttendanceMobileActivity.this, "发送成功！");
								}
							});
						} else {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									ToastHelp.showToast(AttendanceMobileActivity.this, "发送失败！");
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

}
