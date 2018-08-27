package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.MaintenanceTypeItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectItem;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ToastHelp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;
import com.hc.android.huixin.view.SpinnerCustomer;

/**
 * 监管影像采集-安全监管搜索界面
 */
public class TakeSafeSearchActivity extends Activity implements OnClickListener {

	private Handler mHandler = new Handler();
	private ListView mProjectNameList;
	private ArrayList<ProjectItem> mData = new ArrayList<ProjectItem>();
	private String mKeytype;
	private EditText mEditKeyWork;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_safe_search);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		final String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);
		findViewById(R.id.regulatory_back).setOnClickListener(this);
		findViewById(R.id.search).setOnClickListener(this);
		mEditKeyWork = (EditText) findViewById(R.id.edit_project_name);

		mProjectNameList = (ListView) findViewById(R.id.list_view);
		mProjectNameList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(TakeSafeSearchActivity.this, TakeSafeActivity.class);
				intent.putExtra("title", title);
				intent.putExtra("projectId", mData.get(arg2).projectId);
				intent.putExtra("projectName", mData.get(arg2).ProjectName);
				startActivity(intent);
			}
		});

		new GetDictByDirTypeAsyncTask().execute();

	}

	// 获取搜索条件
	private class GetDictByDirTypeAsyncTask extends AsyncTask<Void, Void, ArrayList<MaintenanceTypeItem>> {

		@Override
		protected ArrayList<MaintenanceTypeItem> doInBackground(Void... params) {
			return new NetworkApi().GetKeytype();
		}

		@Override
		protected void onPostExecute(final ArrayList<MaintenanceTypeItem> data) {
			if (data == null) {
				return;
			}
			ArrayList<String> list = new ArrayList<String>();
			SpinnerCustomer safeSpinner = (SpinnerCustomer) findViewById(R.id.safeSpinner);

			for (int i = 0; i < data.size(); i++) {
				String text = data.get(i).text;
				list.add(text);
			}
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
					R.layout.simple_spinner_item, list);
			safeSpinner.setList(list);
			safeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					Spinner spinner = (Spinner) parent;

					mKeytype = spinner.getItemAtPosition(position).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
				}
			});
			mKeytype = data.get(0).text;
			safeSpinner.setAdapter(arrayAdapter);
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
		case R.id.search:
			searchProject();
			break;
		default:
			break;
		}
	}

	private void searchProject() {
		final String name = mEditKeyWork.getText().toString();
		if (TextUtils.isEmpty(name)) {
			ToastHelp.showToast(TakeSafeSearchActivity.this, "关键字不能为空！");
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
				NetworkApi.getProjectListForOutSideCapture(TakeSafeSearchActivity.this, name, mKeytype,
						new INetCallback() {
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
												ToastHelp.showToast(TakeSafeSearchActivity.this, "未搜索到工程数据！");
											}
										});
										return;
									}
									String[] dataList = new String[count];
									for (int i = 0; i < count; i++) {
										dataList[i] = mData.get(i).ProjectName;
									}
									final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
											TakeSafeSearchActivity.this, R.layout.simple_spinner_item, dataList);
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
											ToastHelp.showToast(TakeSafeSearchActivity.this, "获取工程名称失败！");
										}
									});
								}
							}
						});
			}
		}).start();
	}
}
