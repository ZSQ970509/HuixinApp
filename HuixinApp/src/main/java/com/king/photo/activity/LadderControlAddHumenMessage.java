package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.andorid.zxing.app.CaptureActivity;
import com.hc.android.huixin.MyApplication;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.MaintenanceTypeItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectItem;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.RegexUtils;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.ActionItem;
import com.hc.android.huixin.view.DefaultDialog;
import com.hc.android.huixin.view.TitlePopup;
import com.hc.android.huixin.view.TitlePopup.OnItemOnClickListener;
import com.hc.android.laddercontrolcamera.TakeCameraActivity;
import com.king.photo.model.HumanModel;
import com.king.photo.model.LockDriverModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LadderControlAddHumenMessage extends Activity implements OnClickListener {
	TextView TxTbindDriverTitle;
	ImageButton regulatory_back;
	Button btnAddHumen;
	String title = "";
	String buttonName = "";
	EditText editName,editIDNum,editQualificationCertificate,editPhone,edLoginPass,edOpenPass;
	LinearLayout linearOpenPass;
	ProgressDialog dialog;
	HumanModel humanModel;
	
	public static String photo1 = "/sdcard/tempphoto1.jpg";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ladder_control_add_humen_message);
		MyApplication myApplication = (MyApplication)this.getApplication();
		myApplication.addHumanActivity(this);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		title = getIntent().getStringExtra("title");
		buttonName = getIntent().getStringExtra("buttonName");
		TxTbindDriverTitle = (TextView) findViewById(R.id.bind_driver_title);
		TxTbindDriverTitle.setText(title);
		regulatory_back = (ImageButton)findViewById(R.id.regulatory_back);
		regulatory_back.setOnClickListener(this);
		btnAddHumen = (Button) findViewById(R.id.btn_add_humen);
		btnAddHumen.setText(buttonName);
		btnAddHumen.setOnClickListener(this);
		editName = (EditText) findViewById(R.id.edit_humen_name);
		editIDNum = (EditText) findViewById(R.id.edit_humen_idnum);
		editQualificationCertificate = (EditText) findViewById(R.id.edit_humen_qualification_certificate);
		editPhone = (EditText) findViewById(R.id.edit_humen_phone);
		edLoginPass = (EditText) findViewById(R.id.edit_login_pass);
		edOpenPass = (EditText) findViewById(R.id.edit_open_pass);
		linearOpenPass = (LinearLayout) findViewById(R.id.edit_open_linearlayout);
		if(buttonName.equals("上传")){
			linearOpenPass.setVisibility(View.GONE);
		}
		
	}
	public class checkHumanAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new NetworkApi().checkHuman(params[0]);

		}

		@Override
		protected void onPostExecute(String data) {
			dialog.dismiss();
			if(data.equals("")){
				Intent intent= new Intent(LadderControlAddHumenMessage.this,TakeCameraActivity.class);
				intent.putExtra("camera_num", "第一次采样");
				intent.putExtra("humanModel", humanModel);
				intent.putExtra("IMGFileUrl", photo1);
				intent.putExtra("num", "1");
				startActivity(intent);
			}else{
				DefaultDialog.showDialog(LadderControlAddHumenMessage.this, data);
			}
		}
	}
	public class AddHumanAsyncTask extends AsyncTask<HumanModel, Void, String> {
		@Override
		protected String doInBackground(HumanModel... params) {
			// TODO Auto-generated method stub
			//等老黄
			return new NetworkApi().addHuman(params[0]);

		}

		@Override
		protected void onPostExecute(String data) {
			dialog.dismiss();
			if(data.equals("添加安全员成功")){
				DefaultDialog.showDialogRemoveActivity(LadderControlAddHumenMessage.this, data);
			}else{
				DefaultDialog.showDialog(LadderControlAddHumenMessage.this, data);
			}
		}
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
		case R.id.btn_add_humen:
			if(TextUtils.isEmpty(editName.getText().toString())){
				ToastHelp.showToast(this, "人员姓名不能为空！");
				return;
			}
			if(TextUtils.isEmpty(editIDNum.getText().toString())){
				ToastHelp.showToast(this, "人员身份证不能为空！");
				return;
			}
			if(!RegexUtils.checkIdCard(editIDNum.getText().toString())){
				ToastHelp.showToast(this, "请输入正确的身份证号！");
				return;
			}
			if(TextUtils.isEmpty(editQualificationCertificate.getText().toString())){
				ToastHelp.showToast(this, "人员资格证不能为空！");
				return;
			}
			if(!RegexUtils.checkMobile(editPhone.getText().toString())){
				ToastHelp.showToast(this, "请输入正确的手机号！");
				return;
			}
			if(TextUtils.isEmpty(editPhone.getText().toString())){
				ToastHelp.showToast(this, "人员手机不能为空！");
				return;
			}
			if(TextUtils.isEmpty(edLoginPass.getText().toString())){
				ToastHelp.showToast(this, "登录密码不能为空！");
				return;
			}
			if(buttonName.equals("下一步")){
				if(TextUtils.isEmpty(edOpenPass.getText().toString())){
					ToastHelp.showToast(this, "开锁密码不能为空！");
					return;
				}
				humanModel = new HumanModel(editName.getText().toString(), editPhone.getText().toString(), editIDNum.getText().toString(), editQualificationCertificate.getText().toString(), getIntent().getStringExtra("projectId"), getIntent().getStringExtra("projectName"), getIntent().getStringExtra("type"),edLoginPass.getText().toString(),edOpenPass.getText().toString());
				dialog = DialogUtil.createProgressDialog(this, "正在校验数据...");
				dialog.show();
				new checkHumanAsyncTask().execute(humanModel.getUserIDNum());
			}else if(buttonName.equals("上传")){
				humanModel = new HumanModel(editName.getText().toString(), editPhone.getText().toString(), editIDNum.getText().toString(), editQualificationCertificate.getText().toString(), getIntent().getStringExtra("projectId"), getIntent().getStringExtra("projectName"), getIntent().getStringExtra("type"),edLoginPass.getText().toString());
				dialog = DialogUtil.createProgressDialog(this, "正在绑定...");
				dialog.show();
				new AddHumanAsyncTask().execute(humanModel);
			}
			break;
		default:
			break;
		}
	}
	

}
