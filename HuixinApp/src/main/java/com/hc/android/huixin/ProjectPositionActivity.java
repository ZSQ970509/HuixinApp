package com.hc.android.huixin;

import java.util.ArrayList;

import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectItem;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;

/**
 * 项目定位
 */
public class ProjectPositionActivity extends Activity implements OnClickListener {

	private Handler mHandler = new Handler();
	private ArrayList<ProjectItem> mData = new ArrayList<ProjectItem>();
	private EditText mKeyWork;
	private Spinner mProjectNameList;
	private TextView mLocalPostion;

	private String mProjcLat = "0.0";
	private String mProjcLng = "0.0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_position);
		initView();
	}

	private void initView() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		findViewById(R.id.btn_back).setOnClickListener(this);
		findViewById(R.id.btn_save_position).setOnClickListener(this);
		findViewById(R.id.search).setOnClickListener(this);

		mKeyWork = (EditText) findViewById(R.id.edit_project_name);
		mProjectNameList = (Spinner) findViewById(R.id.spinner_project_name);
		mLocalPostion = (TextView) findViewById(R.id.txt_local_position);

		getLocalPosition();
	}

	private void getLocalPosition() {
		mProjcLat = PreferenceUtil.getProjectLat();
		mProjcLng = PreferenceUtil.getProjectLng();
		String addr = PreferenceUtil.getProjectAddrStr();
		mLocalPostion.setText("经度：" + mProjcLng + " 纬度：" + mProjcLat + "\n地址：" + addr);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.search:
			searchProject();
			break;
		case R.id.btn_update:
			getLocalPosition();
			break;
		case R.id.btn_save_position:
			savePosition();
			break;
		default:
			break;
		}
	}

	private void searchProject() {
		final String name = mKeyWork.getText().toString();
		if (TextUtils.isEmpty(name)) {
			return;
		}
		mData.clear();
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在获取工程名称...");
		dialog.show();
		dialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				return true;
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				NetworkApi.queryProjectData(ProjectPositionActivity.this, name, "0", new INetCallback() {
					@Override
					public void onCallback(boolean value, String result) {
						if (value) {
							mData.addAll(NetworkApi.parstToProjectList(result));
							int count = mData.size();
							if (count == 0) {
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										dialog.cancel();
										ToastHelp.showToast(getApplicationContext(), "搜索为空！");
									}
								});
								return;
							}
							String[] dataList = new String[count];
							for (int i = 0; i < count; i++) {
								dataList[i] = mData.get(i).ProjectName;
							}
							final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProjectPositionActivity.this,
									R.layout.simple_spinner_item, dataList);
							adapter.setDropDownViewResource(R.layout.simple_spinner_item);
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									dialog.cancel();
									mProjectNameList.setAdapter(adapter);
								}
							});
						} else {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									dialog.cancel();
									ToastHelp.showToast(ProjectPositionActivity.this, "获取工程名称失败！");
								}
							});
						}
					}
				});
			}
		}).start();
	}

	private void savePosition() {
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在保存...");
		dialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				ProjectItem item = mData.get(mProjectNameList.getSelectedItemPosition());
				String projid = item.projectId;
				NetworkApi.uploadDataForSavePosition(ProjectPositionActivity.this, projid, mProjcLat, mProjcLng,
						new INetCallback() {
							@Override
							public void onCallback(boolean value, String result) {
								LogUtil.logD("sendData onCallback" + result);
								if (value) {
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											ToastHelp.showToast(ProjectPositionActivity.this, "发送成功！");
										}
									});
								} else {
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											ToastHelp.showToast(ProjectPositionActivity.this, "发送失败！");
										}
									});
								}
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										dialog.cancel();
									}
								});
							}
						});
			}
		}).start();
	}

}
