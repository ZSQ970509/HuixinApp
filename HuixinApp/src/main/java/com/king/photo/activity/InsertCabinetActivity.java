package com.king.photo.activity;


import com.hc.andorid.zxing.app.CaptureActivity;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.DefaultDialog;
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

public class InsertCabinetActivity extends Activity implements OnClickListener {
	private EditText editDriverPoinPlace, editDerverInstallPlace;
	private Button btnInsertCabinet;
	private ProgressDialog dialog;
	private TextView textProjectName;
	private String projectId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_cabinet);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		findViewById(R.id.regulatory_back).setOnClickListener(this);
		projectId = getIntent().getStringExtra("projectId");
		editDriverPoinPlace = (EditText) findViewById(R.id.edit_driver_point_place);
		editDerverInstallPlace = (EditText) findViewById(R.id.edit_driver_install_space);
		btnInsertCabinet = (Button) findViewById(R.id.btn_insert_cabinet);
		btnInsertCabinet.setOnClickListener(this);

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
				ToastHelp.showToast(InsertCabinetActivity.this, "设备点位不能为空！");
				return;
			}*/
			if (TextUtils.isEmpty(editDerverInstallPlace.getText().toString())) {
				ToastHelp.showToast(InsertCabinetActivity.this, "安装位置不能为空！");
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

	private class GetIsBindAsyncTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			return new NetworkApi().insertCabinet(projectId,editDerverInstallPlace.getText().toString(), editDerverInstallPlace.getText().toString());
		}

		@Override
		protected void onPostExecute(Boolean data) {
			dialog.dismiss();
			if (data) {
				DefaultDialog.showDialogIsFinish(InsertCabinetActivity.this, "添加成功！");
			} else {
				DefaultDialog.showDialog(InsertCabinetActivity.this, "添加失败！");
			}

		}
	}
}
