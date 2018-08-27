package com.hc.android.laddercontrolcamera;




import com.hc.android.huixin.MyApplication;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.view.DefaultDialog;
import com.king.photo.activity.LadderControlDeviceDebind;
import com.king.photo.activity.LadderControlHumenMessage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import uk.co.senab.photoview.PhotoView;



public class ShowPhotoActivity extends Activity {
	ImageButton mBtnBack;
	Button mBtnCertain;
	PhotoView mImg;
	String strFileUrl = "";
	ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_photo);
		MyApplication myApplication = (MyApplication) this.getApplication();
		myApplication.addHumanActivity(this);
		mImg = (PhotoView) findViewById(R.id.img_camera_photo);
		mBtnBack = (ImageButton) findViewById(R.id.regulatory_back);
		mBtnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		mBtnCertain = (Button) findViewById(R.id.btn_show_photo_certain);
		mBtnCertain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = DialogUtil.createProgressDialog(ShowPhotoActivity.this, "正在上传...");
				dialog.show();
				new UpLoadImageAsyncTask().execute();
			}
		});
		mImg.setImageBitmap(ImageUtil.getLoacalBitmap(getIntent().getStringExtra("IMGFileUrl")));
	}
	public class UpLoadImageAsyncTask extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Intent intent = getIntent();

			return new NetworkApi().uploadImage(ImageUtil.convertIconToStringJPGE(ImageUtil.getLoacalBitmap(getIntent().getStringExtra("IMGFileUrl"))),intent.getStringExtra("num"));
		}

		@Override
		protected void onPostExecute(String data) {
			dialog.dismiss();
			if(!data.equals("")){
				Intent intent = getIntent();
				if(intent.getStringExtra("num").equals("1")){
					intent.putExtra("image_1", data);
					intent.putExtra("camera_num", "第二次采样");
					intent.putExtra("num","2");
					intent.putExtra("IMGFileUrl","/sdcard/tempphoto2.jpg");
					intent.setClass(ShowPhotoActivity.this, TakeCameraActivity.class);
				}else if(intent.getStringExtra("num").equals("2")){
					intent.putExtra("image_2", data);
					intent.putExtra("camera_num", "第三次采样");
					intent.putExtra("num","3");
					intent.putExtra("IMGFileUrl","/sdcard/tempphoto3.jpg");
					intent.setClass(ShowPhotoActivity.this, TakeCameraActivity.class);
				}else if(intent.getStringExtra("num").equals("3")){
					intent.putExtra("image_3", data);
					intent.setClass(ShowPhotoActivity.this, LadderControlHumenMessage.class);
				}
				DefaultDialog.showDialogIntent(ShowPhotoActivity.this, "上传照片成功！",intent);
			}else{
				DefaultDialog.showDialog(ShowPhotoActivity.this, "网络连接异常，上传失败！");
			}
		}


		
	}
	

}
