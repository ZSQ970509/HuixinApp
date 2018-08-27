package com.king.photo.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.SendDataBean;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EnvironmentalMonitoringActivity extends Activity {

	EditText project_name, project_Content;
	Button btn_send;
	String StardardValue, MinOpenTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.environmental_monitoring_activity);
		btn_send = (Button) findViewById(R.id.btn_send);
		project_name = (EditText) findViewById(R.id.project_name);
		project_Content = (EditText) findViewById(R.id.project_Content);

		findViewById(R.id.regulatory_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		btn_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				StardardValue = project_name.getText().toString();
				MinOpenTime = project_Content.getText().toString();
				final ProgressDialog dialog = DialogUtil.createProgressDialog(EnvironmentalMonitoringActivity.this, "正在发送消息...");
				dialog.show();

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						sendData(EnvironmentalMonitoringActivity.this, StardardValue, MinOpenTime, new INetCallback() {

							@Override
							public void onCallback(boolean value, String result) {
								// TODO Auto-generated method stub
								if (value) {
									mHandler.post(new Runnable() {
										@Override
										public void run() {

											ToastHelp.showToast(EnvironmentalMonitoringActivity.this, "设置成功！");
										}
									});
								} else {
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											ToastHelp.showToast(EnvironmentalMonitoringActivity.this, "设置失败！");
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
		});
	}

	public static void sendData(Context context, String StardardValue, String MinOpenTime,
			final INetCallback callback) {

		String result = HttpUtil.getFromUrl(
				"http://ad.jsqqy.com/Handler/MobileAppsHandler.ashx?interfaceName=SetEnvironment&StardardValue="
						+ StardardValue + "&MinOpenTime=" + MinOpenTime);
		if (TextUtils.isEmpty(result)) {
			Log.e("sendData", "result null");
			callback.onCallback(false, "result null");
			return;
		}
		try {
			JSONObject json = new JSONObject(result);
			if (json.getString("result").equals("1")) {
				callback.onCallback(true, result);
			} else {
				callback.onCallback(false, result);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			callback.onCallback(false, e.getMessage());
		}
	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}

	};

}
