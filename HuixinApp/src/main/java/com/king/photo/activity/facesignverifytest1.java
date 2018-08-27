package com.king.photo.activity;

import com.hc.android.huixin.R;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class facesignverifytest1 extends Activity {

	ImageView iv;
	boolean isOpen = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_face_verify_test_1);
		findViewById(R.id.regulatory_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		iv = (ImageView) findViewById(R.id.imageView1);
		if (isOpen) {
			iv.setBackgroundResource(R.drawable.account_manager_open);
		} else {
			iv.setBackgroundResource(R.drawable.account_manager_close);
		}
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isOpen) {
					iv.setBackgroundResource(R.drawable.account_manager_close);
					isOpen = false;
					ToastHelp.showCurrentToast(facesignverifytest1.this, "门已经关闭");
				} else {
					iv.setBackgroundResource(R.drawable.account_manager_open);
					isOpen = true;
					ToastHelp.showCurrentToast(facesignverifytest1.this, "门已经打开");

				}
			}
		});
	}
}
