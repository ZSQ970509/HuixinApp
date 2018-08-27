package com.hc.android.huixin;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;
import android.app.Activity;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initView();
	}

	private void initView() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);
		WebView web = (WebView) findViewById(R.id.web_about);
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setUseWideViewPort(false);
		web.getSettings().setLoadWithOverviewMode(true);
		web.getSettings().setSupportZoom(false);
		web.getSettings().setBuiltInZoomControls(false);
		web.loadUrl("file:///android_asset/web/about.html");
		findViewById(R.id.about_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
