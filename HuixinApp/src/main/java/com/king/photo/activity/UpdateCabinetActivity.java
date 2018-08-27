package com.king.photo.activity;


import com.hc.andorid.zxing.app.CaptureActivity;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.DefaultDialog;
import com.king.photo.model.CabineModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateCabinetActivity extends Activity implements OnClickListener {
	private EditText editDriverPoinPlace, editDerverInstallPlace;
	private Button btnInsertCabinet;
	private ProgressDialog dialog;
	private TextView textProjectName;
	private String camId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_cabinet);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		findViewById(R.id.regulatory_back).setOnClickListener(this);
		camId = getIntent().getStringExtra("camId");
		editDriverPoinPlace = (EditText) findViewById(R.id.edit_driver_point_place);
		editDerverInstallPlace = (EditText) findViewById(R.id.edit_driver_install_space);
		btnInsertCabinet = (Button) findViewById(R.id.btn_insert_cabinet);
		btnInsertCabinet.setOnClickListener(this);
		new GetCamAsyncTask().execute();
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
		case R.id.btn_insert_cabinet:
			/*if (TextUtils.isEmpty(editDriverPoinPlace.getText().toString())) {
				ToastHelp.showToast(UpdateCabinetActivity.this, "设备点位不能为空！");
				return;
			}*/
			if (TextUtils.isEmpty(editDerverInstallPlace.getText().toString())) {
				ToastHelp.showToast(UpdateCabinetActivity.this, "安装位置不能为空！");
				return;
			}
			dialog = DialogUtil.createProgressDialog(this, "正在添加...");
			dialog.show();
			new GetIsBindAsyncTask().execute();
			break;
		default:
			break;
		}
	}
	private class GetCamAsyncTask extends AsyncTask<Void, Void, CabineModel> {

		@Override
		protected CabineModel doInBackground(Void... params) {
			return new NetworkApi().getCabinet(camId);
		}

		@Override
		protected void onPostExecute(CabineModel data) {
			if (data != null) {
				//editDriverPoinPlace.setText(data.getCamName());
				editDerverInstallPlace.setText(data.getCamInstalPlace());
			} else {
				DefaultDialog.showDialogIsFinish(UpdateCabinetActivity.this, "信息获取失败！");
			}

		}
	}
	private class GetIsBindAsyncTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			return new NetworkApi().updateCabinet(camId,editDerverInstallPlace.getText().toString(), editDerverInstallPlace.getText().toString());
		}

		@Override
		protected void onPostExecute(Boolean data) {
			dialog.dismiss();
			if (data) {
				DefaultDialog.showDialogIsFinish(UpdateCabinetActivity.this, "修改成功！");
			} else {
				DefaultDialog.showDialog(UpdateCabinetActivity.this, "修改失败！");
			}

		}
	}
}
