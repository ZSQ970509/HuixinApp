package com.king.photo.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.king.photo.model.ErrorMsgModel;

/**
 * 这个是显示一个文件夹里面的所有图片时的界面
 */
public class CheckErrorCodeActivity extends Activity {
	ImageButton regulatoryback;
	TextView checkErrorCodeCode;
	TextView checkErrorCodeReason;
	TextView checkErrorCodeSolution;
	Intent intent;
	ProgressDialog progressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_error_code);
		intent = getIntent();
		regulatoryback = (ImageButton) findViewById(R.id.regulatory_back);
		regulatoryback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		checkErrorCodeCode = (TextView) findViewById(R.id.check_error_code_code);
		checkErrorCodeReason = (TextView) findViewById(R.id.check_error_code_reason);
		checkErrorCodeSolution = (TextView) findViewById(R.id.check_error_code_solution);
		progressDialog = DialogUtil.createProgressDialog(CheckErrorCodeActivity.this, "正在获取数据中...");
		progressDialog.show();
		new GetErrMsgAsyncTask().execute();
	}
	// 获取进度描述
		private class GetErrMsgAsyncTask extends AsyncTask<Void, Void, ErrorMsgModel> {

			@Override
			protected ErrorMsgModel doInBackground(Void... params) {
				return new NetworkApi().getErrMsg(intent.getStringExtra("errCode"));
			}

			@Override
			protected void onPostExecute(ErrorMsgModel errorMsgModel) {
				progressDialog.dismiss();
				if(!(errorMsgModel ==null)){
					checkErrorCodeCode.setText(errorMsgModel.getCode());
					checkErrorCodeReason.setText(errorMsgModel.getReason());
					checkErrorCodeSolution.setText(errorMsgModel.getSolution());
				}else{
					Toast.makeText(CheckErrorCodeActivity.this, "网络异常，请重试！", 0).show();
				}
				
			}
		}
	

}
