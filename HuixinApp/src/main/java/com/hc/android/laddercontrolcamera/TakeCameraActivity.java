package com.hc.android.laddercontrolcamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.hc.android.huixin.MyApplication;
import com.hc.android.huixin.R;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TakeCameraActivity extends Activity {
	ImageButton regulatory_back,btn_change;
	CameraSurfaceView mySurfaceView;//
	Button mBtnTakePic;
	private int mScreenWidth;
	private int mScreenHeight;
	private TextView camera_num;
	FrameLayout fl;
	private Camera mCamera;
	public static int muban_width;

	int cameraPosition = 1;
	LinearLayout mLLTakePhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_mobile_camera);
		MyApplication myApplication = (MyApplication) this.getApplication();
	 	myApplication.addHumanActivity(this);
		getScreenMetrix(this);
		mySurfaceView = (CameraSurfaceView) findViewById(R.id.cameraSurfaceView);
		fl = (FrameLayout) findViewById(R.id.fl_photo);
		mBtnTakePic = (Button) findViewById(R.id.btn_take_photo);
		mLLTakePhoto = (LinearLayout) findViewById(R.id.ll_take_photo);
		camera_num = (TextView) findViewById(R.id.camera_num);
		camera_num.setText(getIntent().getStringExtra("camera_num"));
		final LinearLayout.LayoutParams flParams = (LinearLayout.LayoutParams) fl.getLayoutParams();
		flParams.height = (int) (mScreenWidth * 1.33334F);
		flParams.width = mScreenWidth;
		fl.setLayoutParams(flParams);
		btn_change = (ImageButton) findViewById(R.id.camera_change);
		mCamera = mySurfaceView.getCamera();
		btn_change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cameraPosition = mySurfaceView.changeCamera(cameraPosition,fl,flParams);
				
				/* int cameraCount = 0;
	                CameraInfo cameraInfo = new CameraInfo();
	                cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

	                for(int i = 0; i < cameraCount; i++   ) {
	                    Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
	                    if(cameraPosition == 1) {
	                        //现在是后置，变更为前置
	                        if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置  
	                        	mySurfaceView.surfaceDestroyed(mySurfaceView.getHolder());
	                        	mCamera.stopPreview();//停掉原来摄像头的预览
	                        	mCamera.release();//释放资源
	                        	mCamera = null;//取消原来摄像头
	                        	mCamera = Camera.open(i);//打开当前选中的摄像头
	                            try {
	                            	mCamera.setPreviewDisplay(mySurfaceView.getHolder());//通过surfaceview显示取景画面
	                            } catch (IOException e) {
	                                // TODO Auto-generated catch block
	                                e.printStackTrace();
	                            }
	                            mCamera.startPreview();//开始预览
	                            cameraPosition = 0;
	                            break;
	                        }
	                    } else {
	                        //现在是前置， 变更为后置
	                        if(cameraInfo.facing  == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置  
	                        	mySurfaceView.surfaceDestroyed(mySurfaceView.getHolder());
	                        	mCamera.stopPreview();//停掉原来摄像头的预览
	                        	mCamera.release();//释放资源
	                        	mCamera = null;//取消原来摄像头
	                        	mCamera = Camera.open(i);//打开当前选中的摄像头
	                            try {
	                            	mCamera.setPreviewDisplay(mySurfaceView.getHolder());//通过surfaceview显示取景画面
	                            } catch (IOException e) {
	                                // TODO Auto-generated catch block
	                                e.printStackTrace();
	                            }
	                            mCamera.startPreview();//开始预览
	                            cameraPosition = 1;
	                            break;
	                        }
	                    }

	                }*/
			}
		});
		mBtnTakePic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
					mySurfaceView.takePicture();
				} catch (Exception e) {
					// TODO: handle exception
					 
					Toast.makeText(TakeCameraActivity.this, "拍照失败！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		regulatory_back = (ImageButton)findViewById(R.id.regulatory_back);
		regulatory_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}


	private void getScreenMetrix(Context context) {
		WindowManager WM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		WM.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;
		mScreenHeight = outMetrics.heightPixels;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			mySurfaceView.setTorch(false);
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:
			mySurfaceView.setTorch(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
