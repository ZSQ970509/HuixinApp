package com.king.photo.activity;


import com.hc.andorid.zxing.app.CaptureActivity;
import com.hc.android.huixin.MyApplication;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.DefaultDialog;
import com.king.photo.activity.LadderControlDeviceDeBindSearch.GetMessageAsyncTask;
import com.king.photo.model.LockDriverModel;

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

public class LadderControlDeviceScanMessage extends Activity implements OnClickListener {
	ImageButton regulatory_back;
	EditText et_driverSixCode;
	Button driverSearchButton;
	Button driverScanButton;
	String ResultCode;
	String deriverMAC="";
	ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ladder_control_device_scan_message);
		MyApplication maApplication = (MyApplication) this.getApplication();
		maApplication.addHumanActivity(this);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		regulatory_back = (ImageButton)findViewById(R.id.regulatory_back);
		regulatory_back.setOnClickListener(this);
		et_driverSixCode = (EditText) findViewById(R.id.text_six_code);
		driverSearchButton = (Button) findViewById(R.id.driver_search_button);
		driverSearchButton.setOnClickListener(this);
		driverScanButton = (Button) findViewById(R.id.driver_scan_button);
		driverScanButton.setOnClickListener(this);
		

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
		case R.id.driver_search_button:
			String code = et_driverSixCode.getText().toString();
			if(!code.equals("")){
				dialog = DialogUtil.createProgressDialog(this, "正在搜索...");
				dialog.show();
			    ResultCode = et_driverSixCode.getText().toString();
				new GetDriverAsyncTask().execute(ResultCode);
			}else{
				ToastHelp.showToast(this, "编号不能为空！");
			}
			break;
		case R.id.driver_scan_button:
			Intent intent = new Intent(this,CaptureActivity.class);
			startActivityForResult(intent, 0x11);
			break;
		default:
			break;
		}
	}
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        super.onActivityResult(requestCode, resultCode, intent);
	        if(intent != null){
	        	  ResultCode = intent.getStringExtra("SCAN_RESULT");
	        	  et_driverSixCode.setText(ResultCode);
	        	  dialog = DialogUtil.createProgressDialog(this, "正在搜索...");
				  dialog.show();
				  new GetDriverAsyncTask().execute(ResultCode);
	        }
	    }
	// 搜索设备
		private class GetDriverAsyncTask extends AsyncTask<String, Void, String> {
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return new NetworkApi().QueryDriverExit(params[0]);

			}

			@Override
			protected void onPostExecute(String data) {
				if(data.equals("1")){
					//等接口调用新石器查询绑定状态
					new GetMessageAsyncTask().execute();
					
				}else if(data.equals("0")){
					dialog.dismiss();
					DefaultDialog.showDialog(LadderControlDeviceScanMessage.this, "该设备未入库！");
				}else if(data.equals("-1")){
					dialog.dismiss();
					ToastHelp.showToast(LadderControlDeviceScanMessage.this, "服务器出现异常！");
				}

			}

			
		}
		public class GetMessageAsyncTask extends AsyncTask<Void, Void, LockDriverModel> {

			@Override
			protected LockDriverModel doInBackground(Void... params) {
				// TODO Auto-generated method stub
				return new NetworkApi().getDriverMessage(ResultCode);
			}

			@Override
			protected void onPostExecute(LockDriverModel result) {
				dialog.dismiss();
				// TODO Auto-generated method stub
				if(result!=null){
					deriverMAC = result.getDriverMac();
					String deriverStatc = result.getDriverStatic();
					if(deriverStatc.equals("0")){
						Intent intent = new Intent(LadderControlDeviceScanMessage.this,LadderControlDeviceChoosePlace.class);
						intent.putExtra("projectId", getIntent().getStringExtra("projectId"));
						intent.putExtra("projectName",getIntent().getStringExtra("projectName"));
						intent.putExtra("driverName", ResultCode);
						intent.putExtra("deriverMAC", deriverMAC);
						startActivity(intent);
					}else{
						DefaultDialog.showDialog(LadderControlDeviceScanMessage.this, "该设备已被绑定");
					}
					
				}else{
					DefaultDialog.showDialog(LadderControlDeviceScanMessage.this, "该设备不存在");
				}
			}
			
		}
}
