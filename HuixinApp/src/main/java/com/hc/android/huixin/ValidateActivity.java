package com.hc.android.huixin;

import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.NfcHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * 试件检测
 */
public class ValidateActivity extends Activity {

	private TextView mTextTagId;
	private TextView mTextTagContent;
	private String mTagId = "";
	private String mTagContent = "";
	private WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_validate);
		initUI();
		NfcHelper.getInstance().init(this);
		if (NfcHelper.getInstance().isSupportNfc()) {
			if (!NfcHelper.getInstance().isEnableNfc()) {
				DialogUtil.showNfcSettingDialog(this);
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		NfcHelper.getInstance().onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		NfcHelper.getInstance().onPause();
	}

	@Override
	public void onNewIntent(Intent intent) {
		mTagContent = NfcHelper.getInstance().readNfcTag(intent);
		mTagId = NfcHelper.getInstance().readNFCTagId(intent);
		if (!TextUtils.isEmpty(mTagContent)) {
			mTextTagId.setText("标签ID：" + mTagId);
			mTextTagContent.setText("描    述：" + mTagContent);
		}
	}

	private Handler mHandler = new Handler();

	private void initUI() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		findViewById(R.id.abnormal_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mTextTagId = (TextView) findViewById(R.id.text_tagid);
		mTextTagContent = (TextView) findViewById(R.id.text_tagcontent);
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		findViewById(R.id.btn_validate).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(mTagContent)) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							final String result = NetworkApi.JZQYValidateAndGetUrl(mTagId, mTagContent);
							if (!TextUtils.isEmpty(result)) {
								if (!result.equals("False")) {
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											mWebView.loadUrl(result);
										}
									});
								}
							}
						}
					}).start();
				}
			}
		});

	}

}
