package com.king.photo.activity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.hc.android.huixin.MainActivity;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.SendInVehicleDataBean;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * 车辆借车登记和车辆归还登记Activity
 * 
 * @author xiao
 *
 */
public class VehicleDebitActivity extends Activity implements OnClickListener {

	private ImageView mPhotoImage;
	private TextView mEditVehicleSite;
	private EditText mEditDebitName;
	private EditText mEditLicenseNum;
	private EditText mEditIdCard;
	private EditText mEditDebitTel;
	private TextView mTxtCarNum;
	private EditText mEditOutkm;
	private EditText mEditOutOil;
	private EditText mEditOutSituation;

	private Button mTakePhoto;
	private Button mGoOn;
	private Button mSendPhoto;
	private Button mSendVehicle;
	private TextView mTextVehicleSite;
	private TextView mTextDebitName;
	private TextView mTextLicenseNum;
	private TextView mTextIdCard;
	private TextView mTextDebitTel;
	private TextView mTextCarNum;
	private TextView mTextOutkm;
	private TextView mTextOutOil;
	private TextView mTextOutSituation;
	private TextView mTextOutTime;

	private TextView mTextKm;
	private TextView mTextOil;
	private TextView mTextSituation;
	private TextView mTxtKm;
	private TextView mTxtOil;
	private TextView mTxtSituation;
	private TextView mTxtOutTime;
	private TextView mTextTime;
	private Button mBtnOutTime;

	private ScrollView mSendPhotoView;
	private ScrollView mShowPhotoView;

	private String mProjcLat = "0.0";
	private String mProjcLng = "0.0";
	private String mVehicleSite;
	private String mDebitName;
	private String mLicenseNum;
	private String mIdCard;
	private String mDebitTel;
	private String mCarNum;

	private String mOutkm;
	private String mOutOil;
	private String mOutSituation;
	private String mOutTime;
	private ArrayList<String> Imgstr = new ArrayList<String>();
	private Handler mHandler = new Handler();
	private String mPhotoUri;
	private String mCarDebitId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vehicle_debit);
		initView();
	}

	private void initView() {

		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);
		Imgstr.clear();
		mBtnOutTime = (Button) findViewById(R.id.selecttime);
		mPhotoImage = (ImageView) findViewById(R.id.photo_image);
		mEditVehicleSite = (TextView) findViewById(R.id.vehiclesite);
		mEditDebitName = (EditText) findViewById(R.id.debitname);
		mEditLicenseNum = (EditText) findViewById(R.id.licensenum);
		mEditIdCard = (EditText) findViewById(R.id.idcard);
		mEditDebitTel = (EditText) findViewById(R.id.debittel);
		mEditOutkm = (EditText) findViewById(R.id.outkm);
		mEditOutkm.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		mEditOutOil = (EditText) findViewById(R.id.outoil);
		mEditOutOil.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		mEditOutSituation = (EditText) findViewById(R.id.outsituation);
		mTxtCarNum = (TextView) findViewById(R.id.carnum);
		mTextVehicleSite = (TextView) findViewById(R.id.txt_vehiclesite);
		mTextDebitName = (TextView) findViewById(R.id.txt_debitname);
		mTextLicenseNum = (TextView) findViewById(R.id.txt_licensenum);
		mTextIdCard = (TextView) findViewById(R.id.txt_idcard);
		mTextDebitTel = (TextView) findViewById(R.id.txt_debittel);
		mTextCarNum = (TextView) findViewById(R.id.txt_carnum);
		mTextOutkm = (TextView) findViewById(R.id.txt_outkm);
		mTextOutOil = (TextView) findViewById(R.id.txt_outoil);
		mTextOutSituation = (TextView) findViewById(R.id.txt_Out_Situation);
		mTextOutTime = (TextView) findViewById(R.id.txt_outtime);
		mSendPhotoView = (ScrollView) findViewById(R.id.send_Vehicle_view);
		mShowPhotoView = (ScrollView) findViewById(R.id.show_Vehicle_view);

		mTextKm = (TextView) findViewById(R.id.text_km);
		mTextSituation = (TextView) findViewById(R.id.text_situation);
		mTextOil = (TextView) findViewById(R.id.text_oil);

		mTxtKm = (TextView) findViewById(R.id.Text_outkm);
		mTxtSituation = (TextView) findViewById(R.id.Text_outsituation);
		mTxtOil = (TextView) findViewById(R.id.Text_outoil);
		mTxtOutTime = (TextView) findViewById(R.id.Text_outTime);
		mTextTime = (TextView) findViewById(R.id.text_time);

		findViewById(R.id.vehicle_back).setOnClickListener(this);
		mTakePhoto = (Button) findViewById(R.id.takephoto);
		mTakePhoto.setOnClickListener(this);
		mSendVehicle = (Button) findViewById(R.id.send_vehicle);
		mSendVehicle.setOnClickListener(this);
		mGoOn = (Button) findViewById(R.id.go_on);
		mGoOn.setOnClickListener(this);
		mSendPhoto = (Button) findViewById(R.id.send_photo);
		mSendPhoto.setOnClickListener(this);

		mOutTime = new SimpleDateFormat("yyyy-MM-dd HH:mm ").format(new Date());
		mBtnOutTime.setText(mOutTime);
		// 隐藏软键盘弹出
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getLocalPosition();
		mCarNum = getIntent().getStringExtra("CarNum");
		mTxtCarNum.setText(mCarNum);
		mBtnOutTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar currentTime = Calendar.getInstance();
				// 创建一个TimePickerDialog实例，并把它显示出来
				new TimePickerDialog(VehicleDebitActivity.this, 0, new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
						// TODO Auto-generated method stub
						mOutTime = new SimpleDateFormat("yyyy-MM-dd ").format(new Date()) + hourOfDay + ":" + minute
								+ "";
						try {
							mOutTime = new SimpleDateFormat("yyyy-MM-dd HH:mm ")
									.format(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(mOutTime));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mBtnOutTime.setText(mOutTime);
					}
				}, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), true).show();
			}
		});

		SendInVehicleDataBean data = (SendInVehicleDataBean) getIntent().getSerializableExtra("data");

		switch (Integer.parseInt(getIntent().getStringExtra("Status"))) {
		case 0:
			break;
		case 1:
			getLocalPosition();
			mVehicleSite = mEditVehicleSite.getText().toString().trim();
			mDebitName = data.DebitName;
			mLicenseNum = data.licenseNum;
			mIdCard = data.idCard;
			mDebitTel = data.TEL;
			mCarNum = data.CarNum;
			mOutkm = data.OutKm;
			mOutOil = data.OutOil;
			mOutSituation = data.OutSituation;
			mOutTime = converDate(data.CarOutsideTime);
			mCarDebitId = data.CarDebitId;
			mTxtCarNum.setText(mCarNum);
			mEditVehicleSite.setText(mVehicleSite);
			mEditDebitName.setText(mDebitName);
			mEditLicenseNum.setText(mLicenseNum);
			mEditIdCard.setText(mIdCard);
			mEditDebitTel.setText(mDebitTel);
			mEditOutkm.setText(mOutkm);
			mEditOutOil.setText(mOutOil);
			mEditOutSituation.setText(null);
			mBtnOutTime.setText(mOutTime);
			mTextVehicleSite.setText(mVehicleSite);
			mTextDebitName.setText(mDebitName);
			mTextLicenseNum.setText(mLicenseNum);
			mTextIdCard.setText(mIdCard);
			mTextDebitTel.setText(mDebitTel);
			mTextCarNum.setText(mCarNum);
			mTextOutkm.setText(mOutkm);
			mTextOutOil.setText(mOutOil);
			mTextOutSituation.setText(null);
			mTextOutTime.setText(mOutTime);
			mGoOn.setText("请拍照");
			mSendPhoto.setEnabled(false);
			mEditOutSituation.requestFocus();
			mSendPhotoView.post(new Runnable() {
				@Override
				public void run() {
					mSendPhotoView.fullScroll(ScrollView.FOCUS_DOWN);

				}
			});

			break;
		case 2:
			getLocalPosition();
			mVehicleSite = mEditVehicleSite.getText().toString().trim();
			mDebitName = data.DebitName;
			mLicenseNum = data.licenseNum;
			mIdCard = data.idCard;
			mDebitTel = data.TEL;
			mCarNum = data.CarNum;
			mCarDebitId = data.CarDebitId;
			mEditVehicleSite.setText(mVehicleSite);
			mEditDebitName.setText(mDebitName);
			mEditLicenseNum.setText(mLicenseNum);
			mEditIdCard.setText(mIdCard);
			mEditDebitTel.setText(mDebitTel);
			mBtnOutTime.setText(mOutTime);
			mEditDebitName.setEnabled(false);
			mEditLicenseNum.setEnabled(false);
			mEditIdCard.setEnabled(false);
			mEditDebitTel.setEnabled(false);

			mTextKm.setText("归还时公里数：");
			mTextSituation.setText("归还时车辆情况：");
			mTextOil.setText("归还时车辆油表：");
			mTxtKm.setText("归还时公里数：");
			mTxtSituation.setText("归还时车辆情况：");
			mTxtOil.setText("归还时车辆油表：");
			mTxtOutTime.setText("归还时间：");
			mTextTime.setText("归还时间：");

			mEditOutkm.requestFocus();
			mSendPhotoView.post(new Runnable() {
				@Override
				public void run() {
					mSendPhotoView.fullScroll(ScrollView.FOCUS_DOWN);

				}
			});

			/*
			 * mTxtCarNum.setText(mCarNum);
			 * mTextVehicleSite.setText(mVehicleSite);
			 * mTextDebitName.setText(mDebitName);
			 * mTextLicenseNum.setText(mLicenseNum);
			 * mTextIdCard.setText(mIdCard); mTextDebitTel.setText(mDebitTel);
			 * mTextCarNum.setText(mCarNum); mTextOutkm.setText(mOutkm);
			 * mTextOutOil.setText(mOutOil);
			 * mTextOutSituation.setText(mOutSituation);
			 * mTextOutTime.setText(mOutTime);
			 */
			break;
		default:
			break;

		}
	}

	/**
	 * 将json序列化日期的字符串转换为yyyy-MM-dd HH:mm格式
	 * 
	 * @param str
	 *            /Date(1384171247000+0800)/
	 * @return 2016-04-14 02：02
	 */
	String converDate(String str) {
		if (str == null || str.equals("null")) {
			return "null";
		}
		
		// final String str = "1384171247000+0800";
		String time = str.substring(6, str.length() - 7);
	
		Date date = new Date(Long.parseLong(time));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return format.format(date);
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

	private void getLocalPosition() {
		mProjcLat = PreferenceUtil.getProjectLat();
		mProjcLng = PreferenceUtil.getProjectLng();
		String addr = PreferenceUtil.getProjectAddrStr();
		mEditVehicleSite.setText("经度：" + mProjcLng + " 纬度：" + mProjcLat + "\n地址：" + addr);
	}

	/**
	 * 点击拍照按钮，开启拍照功能，执行完毕跳转到确认界面
	 */
	private void takePhoto() {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			ToastHelp.showToast(this, "手机没有安装SD卡，不能拍照");
			return;
		}
		// Pattern p=Pattern.compile("ab");
		if (mEditDebitName.getText().toString().length() <= 0) {
			ToastHelp.showToast(this, "借车人姓名为空！");
			return;
		}
		if (mEditLicenseNum.getText().toString().length() <= 0) {
			ToastHelp.showToast(this, "借车人驾驶证号为空");
			return;
		}
		if (mEditIdCard.getText().toString().length() <= 0) {
			ToastHelp.showToast(this, "身份证号为空！");
			return;
		}
		if (mEditDebitTel.getText().toString().length() <= 0) {
			ToastHelp.showToast(this, "电话号码为空！");
			return;
		}
		if (mEditOutkm.getText().toString().length() <= 0 || mEditOutkm.getText().toString().trim().endsWith(".")
				|| RegexValidate(mEditOutkm.getText().toString())) {
			ToastHelp.showToast(this, mTextKm.getText().toString().replace("：", "").trim() + "输入错误");
			return;
		}
		if (mEditOutOil.getText().toString().length() <= 0 || mEditOutOil.getText().toString().trim().endsWith(".")
				|| RegexValidate(mEditOutOil.getText().toString())) {
			ToastHelp.showToast(this, mTextOil.getText().toString().replace("：", "").trim() + "输入错误");
			return;
		}
		if (mEditOutSituation.getText().toString().length() <= 0) {
			ToastHelp.showToast(this, "归还时车辆情况为空！");
			return;
		}

		if (getIntent().getStringExtra("Status").equals("2")) {
			SendInVehicleDataBean data = (SendInVehicleDataBean) getIntent().getSerializableExtra("data");
			if (Double.parseDouble(mEditOutkm.getText().toString()) < Double.parseDouble(data.OutKm)) {
			
				ToastHelp.showToast(this, "归还时公里数小于借出时公里数");
				return;
			}
		}

		// mProjcLat = "0.0";
		// mProjcLng = "0.0";
		// 开始拍照
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

	public static boolean RegexValidate(String validateString) {
		String reg = "^[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?$";
		return !Pattern.compile(reg).matcher(validateString).find();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (!(new File(mPhotoUri)).exists()) {
				Log.e("onActivityResult", "mPhotoUri not exists!");
				ToastHelp.showToast(this, "拍照失败！");
				return;
			}

			try {
				mSendPhoto.setEnabled(true);
				mSendVehicle.setText("确认" + mTextTime.getText().toString().substring(0, 2) + "车辆");
				mGoOn.setText("重新拍照");
				mShowPhotoView.setVisibility(View.VISIBLE);
				mSendPhotoView.setVisibility(View.GONE);
				mVehicleSite = mEditVehicleSite.getText().toString().trim();
				mDebitName = mEditDebitName.getText().toString().trim();
				mLicenseNum = mEditLicenseNum.getText().toString().trim();
				mIdCard = mEditIdCard.getText().toString().trim();
				mDebitTel = mEditDebitTel.getText().toString().trim();
				mOutkm = mEditOutkm.getText().toString().trim();
				mOutOil = mEditOutOil.getText().toString().trim();
				mOutSituation = mEditOutSituation.getText().toString().trim();
				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.ARGB_8888)
						.cacheInMemory(false).cacheOnDisc(true).build();
				ImageLoader.getInstance().displayImage("file://" + mPhotoUri, mPhotoImage, options);
				mTextVehicleSite.setText(mVehicleSite);
				mTextDebitName.setText(mDebitName);
				mTextLicenseNum.setText(mLicenseNum);
				mTextIdCard.setText(mIdCard);
				mTextDebitTel.setText(mDebitTel);
				mTextCarNum.setText(mCarNum);
				mTextOutkm.setText(mOutkm);
				mTextOutOil.setText(mOutOil);
				mTextOutSituation.setText(mOutSituation);
				mTextOutTime.setText(mOutTime);

			} catch (Exception e) {
				e.printStackTrace();
				ToastHelp.showToast(this, "拍照失败，请重新拍照！");
			}
		} else {
			ToastHelp.showToast(this, "拍照失败，请重新拍照！");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		getLocalPosition();
		mVehicleSite = mEditVehicleSite.getText().toString();
		switch (v.getId()) {
		case R.id.vehicle_back:
			back();
			break;
		case R.id.takephoto:
			takePhoto();
			break;
		case R.id.send_vehicle:
			
			switch (Integer.parseInt(getIntent().getStringExtra("Status"))) {
			case 0:
				sendMsg();
				break;
			case 1:
				sendReserveMsg();
				break;
			case 2:
				sendRevertMsg();
				break;
			default:
				break;
			}
			break;
		case R.id.go_on:
			takePhoto();
			break;
		case R.id.send_photo:
			sendPhoto();

			break;
		default:
			break;
		}
	}

	private void sendPhoto() {
		// TODO Auto-generated method stub

		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送...");
		dialog.show();
		Bitmap bitmap = ImageUtil.scaleImage(mPhotoUri, 500f, 500f);
		if (bitmap == null) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					ToastHelp.showToast(VehicleDebitActivity.this, "发送失败！");

					dialog.cancel();
				}
			});
			return;
		}
		final String imgStr = ImageUtil.convertIconToString(bitmap);
		bitmap.recycle();
		bitmap = null;

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				String LocalPostion = PreferenceUtil.getProjectAddrStr();
				NetworkApi.sendVehicleImgData(VehicleDebitActivity.this, imgStr, mProjcLat, mProjcLng, mCarNum,
						LocalPostion, new INetCallback() {

							@Override
							public void onCallback(boolean value, String result) {
								// TODO Auto-generated method stub
								if (value) {
									try {
										Imgstr.add(new JSONObject(result).get("fileId").toString());
										mHandler.post(new Runnable() {
											@Override
											public void run() {
												dialog.cancel();
												ToastHelp.showToast(VehicleDebitActivity.this, "发送成功！");
												mSendPhoto.setEnabled(false);
												mGoOn.setText("继续拍照");
											}
										});
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											ToastHelp.showToast(VehicleDebitActivity.this, "发送失败！");
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

	private void sendReserveMsg() {
		if (!HttpUtil.networkIsAvailable(this)) {
			ToastHelp.showToast(this, "手机没有网络连接！");
			return;
		}

		if (Imgstr.size() < 3) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					ToastHelp.showToast(VehicleDebitActivity.this, "请至少拍三张照片！已经拍" + Imgstr.size() + "张");
				}
			});
			return;
		}
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送消息...");
		dialog.show();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				getLocalPosition();
				SendInVehicleDataBean data = new SendInVehicleDataBean();
				data.CarDebitId = mCarDebitId;
				data.ImgStr = Imgstr.toString().substring(1, Imgstr.toString().length() - 1).replace(" ", "");
				data.OutProjcLat = mProjcLat;
				data.OutProjcLng = mProjcLng;
				data.LocalPostion = PreferenceUtil.getProjectAddrStr();
				;

				NetworkApi.sendVehicleDebitReserveData(VehicleDebitActivity.this, mCarDebitId, data,
						new INetCallback() {
							@Override
							public void onCallback(boolean value, String result) {
								LogUtil.logD("sendData onCallback" + result);
								if (value) {
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											mPhotoImage.setImageBitmap(null);
											Intent intent = new Intent(VehicleDebitActivity.this,
													VehicleManagerActivity.class);
											intent.putExtra("title", getIntent().getStringExtra("title"));
											intent.putExtra("projectId", getIntent().getStringExtra("projectId"));
											intent.putExtra("projectName", getIntent().getStringExtra("projectName"));
											startActivity(intent);
											finish();
											ToastHelp.showToast(VehicleDebitActivity.this, "发送成功！");
										}
									});
								} else {
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											ToastHelp.showToast(VehicleDebitActivity.this, "发送失败！");
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

	private void sendMsg() {
		// TODO Auto-generated method stub
		if (!HttpUtil.networkIsAvailable(this)) {
			ToastHelp.showToast(this, "手机没有网络连接！");
			return;
		}

		if (Imgstr.size() < 3) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					ToastHelp.showToast(VehicleDebitActivity.this, "请至少拍三张照片！已经拍" + Imgstr.size() + "张");
				}
			});
			return;
		}
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送消息...");
		dialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SendInVehicleDataBean data = new SendInVehicleDataBean();
				mVehicleSite = mEditVehicleSite.getText().toString().trim();
				mDebitName = mEditDebitName.getText().toString().trim();
				mLicenseNum = mEditLicenseNum.getText().toString().trim();
				mIdCard = mEditIdCard.getText().toString().trim();
				mDebitTel = mEditDebitTel.getText().toString().trim();
				mOutkm = mEditOutkm.getText().toString().trim();
				mOutOil = mEditOutOil.getText().toString().trim();
				mOutSituation = mEditOutSituation.getText().toString().trim();
				data.DebitName = mDebitName;
				data.LocalPostion = PreferenceUtil.getProjectAddrStr();
				data.licenseNum = mLicenseNum;
				data.idCard = mIdCard;
				data.TEL = mDebitTel;
				data.CarNum = mCarNum;
				data.OutKm = mOutkm;
				data.OutOil = mOutOil;
				data.OutSituation = mOutSituation;
				data.CarOutsideTime = mOutTime;
				data.ImgStr = Imgstr.toString().substring(1, Imgstr.toString().length() - 1).replace(" ", "");
				data.OutProjcLat = mProjcLat;
				data.OutProjcLng = mProjcLng;
				data.VehicleUser = PreferenceUtil.getName();

				NetworkApi.sendVehicleDebitData(VehicleDebitActivity.this, data, new INetCallback() {
					@Override
					public void onCallback(boolean value, String result) {
						LogUtil.logD("sendData onCallback" + result);
						if (value) {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									mPhotoImage.setImageBitmap(null);
									Intent intent = new Intent(VehicleDebitActivity.this, VehicleManagerActivity.class);
									intent.putExtra("title", getIntent().getStringExtra("title"));
									intent.putExtra("projectId", getIntent().getStringExtra("projectId"));
									intent.putExtra("projectName", getIntent().getStringExtra("projectName"));
									startActivity(intent);
									finish();
									ToastHelp.showToast(VehicleDebitActivity.this, "发送成功！");

								}
							});
						} else {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									ToastHelp.showToast(VehicleDebitActivity.this, "发送失败！");
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

	private void sendRevertMsg() {
		// TODO Auto-generated method stub
		if (!HttpUtil.networkIsAvailable(this)) {
			ToastHelp.showToast(this, "手机没有网络连接！");
			return;
		}
		if (Imgstr.size() < 3) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					ToastHelp.showToast(VehicleDebitActivity.this, "请至少拍三张照片！已经拍" + Imgstr.size() + "张");
				}
			});
			return;
		}
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送...");
		dialog.show();
		Bitmap bitmap = ImageUtil.scaleImage(mPhotoUri, 500f, 500f);
		if (bitmap == null) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					ToastHelp.showToast(VehicleDebitActivity.this, "发送失败！");
					dialog.cancel();
				}
			});
			return;
		}

		final SendInVehicleDataBean data = new SendInVehicleDataBean();
		data.CarDebitId = mCarDebitId;
		data.CarInsideTime = mOutTime;
		data.CarInKm = mEditOutkm.getText().toString().trim();
		data.CarInOil = mEditOutOil.getText().toString().trim();
		data.CarInSituation = mEditOutSituation.getText().toString().trim();
		data.CarInsideAddress = PreferenceUtil.getProjectAddrStr();
		data.CarProjcLngIn = mProjcLng;
		data.CarProjcLatIn = mProjcLat;
		data.CarImgStrIn = Imgstr.toString().substring(1, Imgstr.toString().length() - 1).replace(" ", "");
		data.LocalPostion = PreferenceUtil.getProjectAddrStr();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				NetworkApi.sendVehicleDebitRevertData(VehicleDebitActivity.this, data, new INetCallback() {

					@Override
					public void onCallback(boolean value, String result) {
						// TODO Auto-generated method stub
						if (value) {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									mPhotoImage.setImageBitmap(null);
									Intent intent = new Intent(VehicleDebitActivity.this, VehicleManagerActivity.class);
									intent.putExtra("title", getIntent().getStringExtra("title"));
									intent.putExtra("projectId", getIntent().getStringExtra("projectId"));
									intent.putExtra("projectName", getIntent().getStringExtra("projectName"));
									startActivity(intent);
									finish();
									ToastHelp.showToast(VehicleDebitActivity.this, "发送成功！");

								}
							});
						} else {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									ToastHelp.showToast(VehicleDebitActivity.this, "发送失败！");
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
