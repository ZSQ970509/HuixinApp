package com.king.photo.activity;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.FaceModel;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.CustomDialog;
import com.hc.android.huixin.view.LoadingDialog;
import com.king.photo.model.ImageInfoModel;
import com.king.photo.util.EXIFFile;
import com.king.photo.zoom.PhotoView;
import com.onesafe.util.AssetUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TowerCraneMainActivity extends Activity {

	TextView mTextTitle;
	Button mTextCertain;
	ImageView mPhotoImage;

	String token;
	String bmpInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_towercrane_main);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mTextTitle = (TextView) findViewById(R.id.txt_title);
		mTextCertain = (Button) findViewById(R.id.btn_certain);
		mPhotoImage = (ImageView) findViewById(R.id.img_face_photo);

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		mTextCertain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				takePhoto2();

			}
		});

		mTextCertain.setVisibility(View.VISIBLE);
		new IsHasBaseDataAsy().execute();
	}

	class IsHasBaseDataAsy extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return NetworkApi.IsHasBaseDataForTowerCrane(PreferenceUtil.getName());
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result == null) {
				return;
			}
			try {
				if ("0".equals(new JSONObject(result).getString("result"))) {
					// showDialog(new JSONObject(result).getString("msg"));
					mTextCertain.setVisibility(View.GONE);
					showDialog("找不到有效的底库图片！,请录入人脸照片");

					return;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			new IsLockAndGetSeconds().execute();
		}

	}

	class IsLockAndGetSeconds extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return NetworkApi.IsLockAndGetSecondsForTowerCrane(PreferenceUtil.getName());
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result == null) {
				return;
			}
			try {
				String flag = new JSONObject(result).getString("result");
				switch (Integer.parseInt(flag)) {
				case 0:
					showDialog("用户名无效");
					break;
				case 1:
					showDialog("您账号已被冻结，请通知管理员进行开锁，" + new JSONObject(result).getString("msg") + "秒后可再次进行识别");

					break;
				case 2:

					break;
				default:
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static final String PICTURE_FILE = "tempface.jpg";

	protected void takePhoto2() {
		Intent intent = new Intent(TowerCraneMainActivity.this, TowerCraneCameraActivity.class);
		startActivityForResult(intent, 0);
	}

	protected void takePhoto() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PICTURE_FILE));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

		intent.putExtra("camerasensortype", 1); // 调用前置摄像头
		intent.putExtra("autofocus", true); // 自动对焦
		intent.putExtra("fullScreen", false); // 全屏
		intent.putExtra("showActionIcons", false);
		startActivityForResult(intent, 0);

	}

	LoadingDialog loadingDialog = null;

	private void Distinguish(Bitmap photo) {
		// TODO Auto-generated method stub
		loadingDialog = new LoadingDialog(TowerCraneMainActivity.this);
		loadingDialog.setMessage("正在识别中...").show();
		new GetTokenAsy().execute(PreferenceUtil.getName(),
				ImageUtil.convertIconToString(photo), "识别测试");
	}

	class GetTokenAsy extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return NetworkApi.InsertConstantDataForTowerCrane(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			loadingDialog.cancel();
			try {
				System.out.println(result + "-");
				
				if (result.contains("SocketTimeoutException")) {

					showDialog("服务器连接超时");
					return;
				}

				switch (Integer.parseInt(new JSONObject(result).getString("result"))) {
				case 0:
					showDialog("开锁失败");// 识别失败
					break;
				case 1:
					showDialog("开锁成功");// 是本人
					break;
				case -1:
					showDialog("开锁失败");// 不是本人
					break;
				case 5:
					showDialog("您账号已被冻结，请通知管理员进行开锁，" + new JSONObject(result).getString("msg") + "秒后可再次进行识别");
					break;
				default:
					showDialog("开锁失败");// 识别失败
					break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				showDialog("识别失败");
				e.printStackTrace();
			}

			// //连续失败三次 返回2
			// if ("2".equals(new JSONObject(result).getString("result"))) {
			// return 2;
			// }
			//
			// //识别失败返回0
			// if (!"1".equals(new JSONObject(result).getString("result"))) {
			// return 0;
			// }
			// //识别失败返回1
			// return 1;
			//
			// if (result==1) {
			// }else if (result==2) {
			// ToastHelp.showCurrentToast(TowerCraneMainActivity.this,
			// "连续失败三次");
			// }else{
			// ToastHelp.showCurrentToast(TowerCraneMainActivity.this, "识别失败");
			// }

		}

	}

	public void showDialog(String text) {
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setMessage(text);
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
			}
		});

		builder.create().show();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK && requestCode == 0) {
			if (!HttpUtil.networkIsAvailable(TowerCraneMainActivity.this)) {
				ToastHelp.showCurrentToast(TowerCraneMainActivity.this, "手机没有网络连接！");
				return;
			}

			
			// Bundle extras = data.getExtras();
			// if (extras != null) {
			// Bitmap photo = extras.getParcelable("data");
			//
			// Log.d("TAGBitmapSIZE1", photo.getByteCount() + "!!!!");
			// mPhotoImage.setImageBitmap(photo);
			// Distinguish(photo);
			// }

			try {
				File f = new File(Environment.getExternalStorageDirectory() + "/" + PICTURE_FILE);
			
				ImageInfoModel model = new EXIFFile()
						.GetIMGWidthAndHeigh(Environment.getExternalStorageDirectory() + "/" + PICTURE_FILE);
				bmpInfo = "";
				bmpInfo += "原始图片，高：" + model.getImgHeight() + "宽:" + model.getImgWidth();
				if (f.exists()) {
					Bitmap bm = ImageUtil.imageZoom(ImageUtil.scaleImage(f.getAbsolutePath(), 500f, 240f));
					bmpInfo += "\r\n位图图片，高：" + bm.getHeight() + "宽:" + bm.getWidth();
					((TextView) findViewById(R.id.txt_imginfo)).setText(bmpInfo);
					mPhotoImage.setImageBitmap(bm);
					Distinguish(bm);
				}
			} catch (Exception e) {
				// TODO: handle exception

			}

		}

		// if (resultCode == Activity.RESULT_OK) {
		// if (!(new File(mPhotoUri)).exists()) {
		// Log.e("onActivityResult", "mPhotoUri not exists!");
		// ToastHelp.showToast(this, "拍照失败！");
		// return;
		// }
		// DisplayImageOptions options = new DisplayImageOptions.Builder()
		// .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.ARGB_8888)
		// .cacheInMemory(false).cacheOnDisc(true).build();
		// ImageLoader.getInstance().displayImage("file://" + mPhotoUri,
		// mPhotoImage, options);
		//
		// Distinguish(new File(mPhotoUri));
		// }
	}

}
