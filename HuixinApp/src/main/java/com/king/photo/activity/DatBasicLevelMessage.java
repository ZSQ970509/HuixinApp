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

public class DatBasicLevelMessage extends Activity implements OnClickListener {
	private EditText editDriverDescNum, editDatbasicLeveHigh;
	private Button btnDriverScan, btnDatbasicLeveBind;
	private ProgressDialog dialog;
	private TextView textProjectName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datbasic_level_message);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		findViewById(R.id.regulatory_back).setOnClickListener(this);
		textProjectName = (TextView) findViewById(R.id.text_project_name);
		textProjectName.setText(getIntent().getStringExtra("projectName"));
		editDriverDescNum = (EditText) findViewById(R.id.edit_driver_desc_num);
		editDatbasicLeveHigh = (EditText) findViewById(R.id.edit_datbasicleve_high);
		btnDriverScan = (Button) findViewById(R.id.button_driver_scan);
		btnDatbasicLeveBind = (Button) findViewById(R.id.btn_datbasicleve_bind);
		btnDriverScan.setOnClickListener(this);
		btnDatbasicLeveBind.setOnClickListener(this);
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
		case R.id.button_driver_scan:
			Intent intent = new Intent(this, CaptureActivity.class);
			startActivityForResult(intent, 0x11);
			break;
		case R.id.btn_datbasicleve_bind:
			if (TextUtils.isEmpty(editDriverDescNum.getText().toString())) {
				ToastHelp.showToast(DatBasicLevelMessage.this, "设备序列号不能为空！");
				return;
			}
			if (TextUtils.isEmpty(editDatbasicLeveHigh.getText().toString())) {
				ToastHelp.showToast(DatBasicLevelMessage.this, "基准高度不能为空！");
				return;
			}
			dialog = DialogUtil.createProgressDialog(this, "正在绑定...");
			dialog.show();
			new GetIsBindAsyncTask().execute();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (intent != null) {
			editDriverDescNum.setText(intent.getStringExtra("SCAN_RESULT"));
		}
	}

	private class GetIsBindAsyncTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			return new NetworkApi().bindDatbasicLeve(getIntent().getStringExtra("projectId"),
					editDriverDescNum.getText().toString(), editDatbasicLeveHigh.getText().toString());
		}

		@Override
		protected void onPostExecute(Boolean data) {
			dialog.dismiss();
			if (data) {
				DefaultDialog.showDialogIsFinish(DatBasicLevelMessage.this, "绑定成功！");
			} else {
				DefaultDialog.showDialog(DatBasicLevelMessage.this, "绑定失败！");
			}

		}
	}
}
