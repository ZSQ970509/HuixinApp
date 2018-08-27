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
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.ActionItem;
import com.hc.android.huixin.view.DefaultDialog;
import com.hc.android.huixin.view.TitlePopup;
import com.hc.android.huixin.view.TitlePopup.OnItemOnClickListener;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LadderControlAddHumen extends Activity implements OnClickListener {
	TextView TxTbindDriverTitle;
	ImageButton regulatory_back;
	Button btnAddOperator,btnAddSecurityOfficer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ladder_control_add_humen);
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		final String title = getIntent().getStringExtra("title");
		TxTbindDriverTitle = (TextView) findViewById(R.id.bind_driver_title);
		TxTbindDriverTitle.setText(title);
		regulatory_back = (ImageButton)findViewById(R.id.regulatory_back);
		regulatory_back.setOnClickListener(this);
		btnAddOperator = (Button) findViewById(R.id.btn_add_operator);
		btnAddOperator.setOnClickListener(this);
		btnAddSecurityOfficer = (Button) findViewById(R.id.btn_add_securityofficer);
		btnAddSecurityOfficer.setOnClickListener(this);
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
		Intent intent = new Intent(LadderControlAddHumen.this,LadderControlAddHumenSearchProjiect.class);
		
		switch (v.getId()) {
		case R.id.regulatory_back:
			back();
			break;
		case R.id.btn_add_operator:
			intent.putExtra("type", "1");
			intent.putExtra("buttonName", "下一步");
			intent.putExtra("title", "添加操作员");
			intent.putExtra("classtitle", "选择人员所在项目");
			startActivity(intent);
			break;
		case R.id.btn_add_securityofficer:
			intent.putExtra("type", "0");
			intent.putExtra("title", "添加安全员");
			intent.putExtra("buttonName", "上传");
			intent.putExtra("classtitle", "选择人员所在项目");
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	

}
