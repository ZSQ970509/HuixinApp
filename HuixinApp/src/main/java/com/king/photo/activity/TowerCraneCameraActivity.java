package com.king.photo.activity;

import com.hc.android.huixin.R;
import com.hc.android.huixin.view.CameraSurfaceView;
import com.hc.android.huixin.view.CustomDialog;
import com.hc.android.huixin.view.LoadingDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class TowerCraneCameraActivity extends Activity {
	CameraSurfaceView mySurfaceView;// surfaceView声明
	SurfaceHolder holder;// surfaceHolder声明
	Camera myCamera;// 相机声明
	String filePath = "/sdcard/wjh.jpg";// 照片保存路径
	boolean isClicked = false;// 是否点击标识
	private Button takePicBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏显示
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_towercrane_camera);

		mySurfaceView = (CameraSurfaceView) findViewById(R.id.cameraSurfaceView);
		//
		takePicBtn = (Button) findViewById(R.id.btn_take_photo);
		//
		takePicBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					mySurfaceView.takePicture();
				} catch (Exception e) {
					// TODO: handle exception
					showDialog("开锁失败!");// 识别失败
				}
			}
		});

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
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
}
