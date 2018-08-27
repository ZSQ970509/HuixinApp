package com.king.photo.activity;


import com.hc.andorid.zxing.app.CaptureActivity;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

public class ShowImageMessageActivity extends Activity implements OnClickListener {
	PhotoView photoView;
	RelativeLayout relativeLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_message);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		photoView = (PhotoView) findViewById(R.id.img_camera_photo);
		relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout_camera_photo);
		photoView.setImageBitmap(ImageUtil.getLoacalBitmap(getIntent().getStringExtra("imageUrl")));
		photoView.setOnPhotoTapListener(new OnPhotoTapListener() {  
            
            @Override  
            public void onPhotoTap(View arg0, float arg1, float arg2) {  
                finish();  
            }  
        });  
		relativeLayout.setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_camera_photo:
			finish();
			break;
		case R.id.relativelayout_camera_photo:
			finish();
			break;
		default:
			break;
		}
	}
	 
}
