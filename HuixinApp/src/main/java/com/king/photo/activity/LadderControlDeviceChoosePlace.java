package com.king.photo.activity;


import java.util.ArrayList;

import com.hc.android.huixin.MyApplication;
import com.hc.android.huixin.R;
import com.hc.android.huixin.TakePhoto2Activity;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.DefaultDialog;
import com.king.photo.model.DriverModel;
import com.king.photo.model.JsonModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class LadderControlDeviceChoosePlace extends Activity implements OnClickListener {
	ImageButton regulatory_back;
	TextView txtDriverName,txtDriverMac,txtDriverProject;
	EditText edtDriverInstallSpace;
	Button btnBindDriver;
	ProgressDialog dialog;
	String driverName,deriverMAC,projectName,projectID;
	ArrayList<DriverModel> driverList = new ArrayList<DriverModel>();
	Spinner spinnerChooseDriverName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ladder_control_device_choose_place);
		MyApplication myApplication = (MyApplication) this.getApplication();
		myApplication.addHumanActivity(this);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		driverName = getIntent().getStringExtra("driverName");
		deriverMAC = getIntent().getStringExtra("deriverMAC");
		projectName = getIntent().getStringExtra("projectName");
		projectID = getIntent().getStringExtra("projectId");
		regulatory_back = (ImageButton)findViewById(R.id.regulatory_back);
		regulatory_back.setOnClickListener(this);
		txtDriverName = (TextView) findViewById(R.id.text_driver_name);
		txtDriverName.setText(driverName);
		txtDriverMac = (TextView) findViewById(R.id.text_driver_mac);
		txtDriverMac.setText(deriverMAC);
		txtDriverProject = (TextView) findViewById(R.id.text_driver_project);
		txtDriverProject.setText(projectName);
		edtDriverInstallSpace = (EditText) findViewById(R.id.edit_driver_install_space);
		btnBindDriver = (Button) findViewById(R.id.btn_bind_driver);
		btnBindDriver.setOnClickListener(this);
		spinnerChooseDriverName = (Spinner) findViewById(R.id.spinner_choose_driver_name);
		new GetDriverAsyncTask().execute();
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
			if(!TextUtils.isEmpty(edtDriverInstallSpace.getText().toString())){
				dialog = DialogUtil.createProgressDialog(this, "正在绑定...");
				dialog.show();
				new BindDriverAsyncTask().execute();
			}else{
				ToastHelp.showToast(this, "设备的安装不能为空！");
			}
			break;
		
		default:
			break;
		}
	}
	
	// 搜索设备
		public class BindDriverAsyncTask extends AsyncTask<Void, Void, JsonModel> {
			@Override
			protected JsonModel doInBackground(Void... params) {
				// TODO Au	to-generated method stub
				String camId = driverList.get(spinnerChooseDriverName.getSelectedItemPosition()).getCamId();
				//等老黄
				String userName =PreferenceUtil.getUserName(); //用户名
				String userId = PreferenceUtil.getUserId();//用户id
				String userAccount = PreferenceUtil.getName();//用户账户
				return new NetworkApi().OpenLadderControlLock(driverName,userId,userName,userAccount,deriverMAC,projectID,edtDriverInstallSpace.getText().toString(),camId);

			}

			@Override
			protected void onPostExecute(JsonModel data) {
				dialog.dismiss();
				if(data != null){
					if(data.getResult().equals("1")){
						
						DefaultDialog.showDialogRemoveActivity(LadderControlDeviceChoosePlace.this, data.getMsg());
					}else{
						Log.e("errcode",(data.getMsg()));
						Log.e("errcode",(data.getMsg().indexOf(":")+1+""));
						Log.e("errcode",(data.getMsg().indexOf("！")+""));
						Log.e("errcode",(data.getMsg().lastIndexOf("！")+""));
						Log.e("errcode", data.getMsg().substring(data.getMsg().indexOf(":")+1, data.getMsg().indexOf(":")+6));
						DefaultDialog.showDialogIntentErr(LadderControlDeviceChoosePlace.this, data.getMsg(),data.getMsg().substring(data.getMsg().indexOf(":")+1, data.getMsg().indexOf(":")+6));
					}
				}else{
					DefaultDialog.showDialog(LadderControlDeviceChoosePlace.this, "网络异常，请重试！");
				}
			}


			
		}
		public class GetDriverAsyncTask extends AsyncTask<Void, Void, ArrayList<DriverModel>> {
			@Override
			protected ArrayList<DriverModel> doInBackground(Void... params) {
				// TODO Auto-generated method stub
				return new NetworkApi().getLadderControlNameList(projectID);

			}

			@Override
			protected void onPostExecute(ArrayList<DriverModel> data) {
				driverList = data;
				String[] dataList = new String[driverList.size()];
				for (int i = 0; i < driverList.size(); i++) {
					dataList[i] = driverList.get(i).getCamId()+"_"+driverList.get(i).getCamName();
				}
				final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						LadderControlDeviceChoosePlace.this, R.layout.simple_spinner_item_samll, dataList);
				adapter.setDropDownViewResource(R.layout.simple_spinner_item_samll);	
				spinnerChooseDriverName.setAdapter(adapter);
			}


			
		}
}
