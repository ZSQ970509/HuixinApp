package com.king.photo.activity;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdatePwdActivity extends Activity {

	EditText mEditOldPwd;
	EditText mEditNewPwd;
	EditText mEditConfirmPwd;
	TextView mBtnSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_pwd);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mEditOldPwd = (EditText) findViewById(R.id.old_pwd);

		mEditNewPwd = (EditText) findViewById(R.id.new_pwd);

		mEditConfirmPwd = (EditText) findViewById(R.id.confirm_pwd);

		mEditOldPwd.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					mEditOldPwd.setBackgroundResource(R.drawable.update_pwd_line2);
				} else {
					mEditOldPwd.setBackgroundResource(R.drawable.update_pwd_line1);

				}
			}
		});

		mEditNewPwd.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					mEditNewPwd.setBackgroundResource(R.drawable.update_pwd_line2);

				} else {
					mEditNewPwd.setBackgroundResource(R.drawable.update_pwd_line1);

				}
			}
		});

		mEditConfirmPwd.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					mEditConfirmPwd.setBackgroundResource(R.drawable.update_pwd_line2);

				} else {
					mEditConfirmPwd.setBackgroundResource(R.drawable.update_pwd_line1);

				}
			}
		});

		mBtnSend = (TextView) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (mEditOldPwd.getText().toString().trim() == null
						|| mEditOldPwd.getText().toString().trim().equals("")) {
					ToastHelp.showCurrentToast(UpdatePwdActivity.this, "原密码不能为空");
					return;
				}
				if (mEditConfirmPwd.getText().toString().trim() == null
						|| mEditConfirmPwd.getText().toString().trim().equals("")) {
					ToastHelp.showCurrentToast(UpdatePwdActivity.this, "新密码不能为空");
					return;
				}
				if (mEditNewPwd.getText().toString().trim() == null
						|| mEditNewPwd.getText().toString().trim().equals("")) {
					ToastHelp.showCurrentToast(UpdatePwdActivity.this, "确认密码不能为空");
					return;
				}
				if (!PreferenceUtil.getPassword()
						.equals(mEditOldPwd.getText().toString().trim())) {
					ToastHelp.showCurrentToast(UpdatePwdActivity.this, "原密码输入错误");
					return;
				}
				if (!mEditConfirmPwd.getText().toString().trim().equals(mEditNewPwd.getText().toString().trim())) {
					ToastHelp.showCurrentToast(UpdatePwdActivity.this, "新密码不一致");
					return;
				}
				final ProgressDialog dialog = DialogUtil.createProgressDialog(UpdatePwdActivity.this, "正在发送...");
				// 执行密码修改
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						mHandler.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								dialog.show();
							}
						});
						new NetworkApi().UpdatePWD(PreferenceUtil.getName(),
								mEditOldPwd.getText().toString().trim(), mEditNewPwd.getText().toString().trim(),
								new INetCallback() {

									@Override
									public void onCallback(boolean value, String result) {
										// TODO Auto-generated method stub
										if (value) {
											if (result == null) {
												mHandler.post(new Runnable() {

													@Override
													public void run() {
														// TODO Auto-generated
														// method stub
														ToastHelp.showCurrentToast(UpdatePwdActivity.this, "密码修改失败");
													}
												});
												return;
											}
											if ("success".equals(result)) {
												mHandler.post(new Runnable() {

													@Override
													public void run() {
														// TODO Auto-generated
														// method stub
														ToastHelp.showCurrentToast(UpdatePwdActivity.this, "密码修改成功");
													}
												});
												return;
											}
										} else {
											mHandler.post(new Runnable() {

												@Override
												public void run() {
													// TODO Auto-generated
													// method stub
													ToastHelp.showCurrentToast(UpdatePwdActivity.this, "密码修改失败");
												}
											});
										}
									}
								});
						mHandler.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						});

					}
				}).start();

			}
		});

	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}

	};

}
