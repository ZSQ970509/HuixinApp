package com.hc.android.huixin;

import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.NfcHelper;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * 标签写入
 */
public class WriteNfcActivity extends Activity implements OnClickListener {

	private Intent nfcIntent;
	private Button btnWrite;
	private Button btnReadOnly;
	private EditText nfcEdit;
	private TextView txtContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_nfc);
		initView();
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
		nfcIntent = intent;
		btnWrite.setEnabled(true);
		String text = NfcHelper.getInstance().readNfcTag(intent);
		if (!TextUtils.isEmpty(text)) {
			txtContent.setText(text);
			btnReadOnly.setEnabled(true);
		}
	}

	private void initView() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		findViewById(R.id.about_back).setOnClickListener(this);
		findViewById(R.id.btn_nfc_write).setOnClickListener(this);
		findViewById(R.id.btn_write_only).setOnClickListener(this);
		txtContent = (TextView) findViewById(R.id.text_write);
		nfcEdit = (EditText) findViewById(R.id.nfcEdit);
		btnWrite = (Button) findViewById(R.id.btn_nfc_write);
		btnWrite.setEnabled(false);
		btnReadOnly = (Button) findViewById(R.id.btn_write_only);
		btnReadOnly.setEnabled(false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.about_back:
			finish();
			break;
		case R.id.btn_nfc_write:
			writeToNfc();
			break;
		case R.id.btn_write_only:
			new AlertDialog.Builder(WriteNfcActivity.this).setTitle("设置").setMessage("确定标签设为只读？")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							setReadOnlyToNfc();
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					}).setCancelable(false).create().show();
			break;
		default:
			break;
		}
	}

	private void writeToNfc() {
		String text = nfcEdit.getText().toString();
		if (TextUtils.isEmpty(text)) {
			Toast.makeText(this, "写入内容为空", Toast.LENGTH_SHORT).show();
		} else {
			if (nfcIntent != null) {
				boolean result = NfcHelper.getInstance().writeNfcTag(nfcIntent, text, false);
				if (result) {
					Toast.makeText(this, "写入成功！", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "写入失败！", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, "写入失败！", Toast.LENGTH_SHORT).show();
			}
		}
		btnWrite.setEnabled(false);
	}

	private void setReadOnlyToNfc() {
		String text = txtContent.getText().toString();
		if (TextUtils.isEmpty(text)) {
			Toast.makeText(this, "标签内容为空", Toast.LENGTH_SHORT).show();
		} else {
			if (nfcIntent != null) {
				boolean result = NfcHelper.getInstance().writeNfcTag(nfcIntent, text, true);
				if (result) {
					Toast.makeText(this, "设为只读成功！", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "设为只读失败！", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, "设为只读失败！", Toast.LENGTH_SHORT).show();
			}
		}
		btnReadOnly.setEnabled(false);
	}

}
