package com.king.photo.activity;


import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.view.DefaultDialog;
import com.king.photo.model.JsonModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class LadderControlDeviceDebind extends Activity implements OnClickListener {
	ImageButton regulatory_back;
	TextView txtDriverName,txtDriverMac,txtDriverProject;
	TextView edtDriverInstallSpace,txtDriverStatic,txtDriverCamName;
	Button btnBindDriver;
	ProgressDialog dialog;
	String driverName,deriverMAC,projectName,driverStatic,driverInstallSpace,camName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ladder_control_device_debind);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		driverName = getIntent().getStringExtra("driverName");
		deriverMAC = getIntent().getStringExtra("deriverMAC");
		projectName = getIntent().getStringExtra("projectName");
		driverStatic = getIntent().getStringExtra("deriverStatc");
		if(getIntent().getStringExtra("deriverStatc").equals("1")){
			driverStatic="已绑定";
		}else{
			driverStatic="未绑定";
		}
		camName = getIntent().getStringExtra("CamName");
		driverInstallSpace = getIntent().getStringExtra("deriverInstallPlace");
		regulatory_back = (ImageButton)findViewById(R.id.regulatory_back);
		regulatory_back.setOnClickListener(this);
		txtDriverName = (TextView) findViewById(R.id.text_driver_name);
		txtDriverName.setText(driverName);
		txtDriverMac = (TextView) findViewById(R.id.text_driver_mac);
		txtDriverMac.setText(deriverMAC);
		txtDriverProject = (TextView) findViewById(R.id.text_driver_project);
		txtDriverProject.setText(projectName);
		edtDriverInstallSpace = (TextView) findViewById(R.id.text_driver_install_place);
		edtDriverInstallSpace.setText(driverInstallSpace);
		txtDriverStatic = (TextView) findViewById(R.id.text_driver_static);
		txtDriverCamName = (TextView) findViewById(R.id.text_driver_camname);
		txtDriverCamName.setText(camName);
		txtDriverStatic.setText(driverStatic);
		btnBindDriver = (Button) findViewById(R.id.btn_bind_driver);
		btnBindDriver.setOnClickListener(this);

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
		case R.id.regulatory_back:
			back();
			break;
		case R.id.btn_bind_driver:
			dialog = DialogUtil.createProgressDialog(this, "正在解绑...");
			dialog.show();
			new DebindDriverAsyncTask().execute();
			break;
		
		default:
			break;
		}
	}
	
	// 搜索设备
		public class DebindDriverAsyncTask extends AsyncTask<Void, Void, JsonModel> {
			@Override
			protected JsonModel doInBackground(Void... params) {
				// TODO Auto-generated method stub
				return new NetworkApi().debindDriver(driverName);

			}

			@Override
			protected void onPostExecute(JsonModel data) {
				dialog.dismiss();
				if(data != null){
					if(data.getResult().equals("1")){
						
						DefaultDialog.showDialogIsFinish(LadderControlDeviceDebind.this, data.getMsg());
					}else{
						Log.e("errcode",(data.getMsg()));
						Log.e("errcode",(data.getMsg().indexOf(":")+1+""));
						Log.e("errcode",(data.getMsg().indexOf("！")+""));
						Log.e("errcode",(data.getMsg().lastIndexOf("！")+""));
						Log.e("errcode", data.getMsg().substring(data.getMsg().indexOf(":")+1, data.getMsg().indexOf(":")+6));
						DefaultDialog.showDialogIntentErr(LadderControlDeviceDebind.this, data.getMsg(),data.getMsg().substring(data.getMsg().indexOf(":")+1, data.getMsg().indexOf(":")+6));
						//DefaultDialog.showDialog(LadderControlDeviceDebind.this, data.getMsg());
					}
				}else{
					DefaultDialog.showDialog(LadderControlDeviceDebind.this, "网络异常，请重试！");
				}
			}


			
		}
}
