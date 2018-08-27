package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.MaintenanceTypeItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectItem;
import com.hc.android.huixin.util.BaiduSdkHelper;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.ActionItem;
import com.hc.android.huixin.view.TitlePopup;
import com.hc.android.huixin.view.TitlePopup.OnItemOnClickListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchAddress extends Activity implements OnClickListener {

	private Handler mHandler = new Handler();
	private ListView mProjectNameList;
	private ArrayList<ProjectItem> mData = new ArrayList<ProjectItem>();
	private String mKeytype;
	private EditText mEditKeyWork;
	Button safeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_powerfaildevice_bind_search);
		BaiduSdkHelper.initNavi(this);
		initView();
	}

	private void initView() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);
		findViewById(R.id.regulatory_back).setOnClickListener(this);
		findViewById(R.id.search).setOnClickListener(this);
		mEditKeyWork = (EditText) findViewById(R.id.edit_project_name);
		safeSpinner = (Button) findViewById(R.id.safeSpinner);
		mProjectNameList = (ListView) findViewById(R.id.yxcj_listview);
		mProjectNameList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				BaiduSdkHelper.openBaiduNavi(SearchAddress.this, mData.get(arg2).ProjectLng, mData.get(arg2).ProjectLat,null);

//				Intent intent = new Intent(SearchAddress.this,
//						LadderControlDeviceScanMessage.class);
//				
//				intent.putExtra("projectId", mData.get(arg2).projectId);
//				intent.putExtra("projectName", mData.get(arg2).ProjectName);
//				startActivity(intent);
			}
		});

		new GetDictByDirTypeAsyncTask().execute();

	}

	TitlePopup popup;

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
			final ArrayList<String> list = new ArrayList<String>();

			try {
				popup = new TitlePopup(SearchAddress.this, safeSpinner.getWidth(),
						LayoutParams.WRAP_CONTENT);

			} catch (Exception e) {
				// TODO: handle exception

				return;
			}

			for (int i = 0; i < data.size(); i++) {
				String text = data.get(i).text;
				list.add(data.get(i).text);
				if (i == 0) {
					mKeytype = data.get(0).text;
					safeSpinner.setText(list.get(0).toString());

				}
				popup.addAction(new ActionItem(SearchAddress.this, list.get(i).toString(),
						R.drawable.account));

			}
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
					R.layout.simple_spinner_item, list);

			popup.setItemOnClickListener(new OnItemOnClickListener() {

				@Override
				public void onItemClick(ActionItem item, int position) {
					// TODO Auto-generated method stub
					safeSpinner.setText(list.get(position));
					mKeytype = list.get(position);
				}
			});

			safeSpinner.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popup.show(v);
				}
			});

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
			ToastHelp.showToast(SearchAddress.this, "关键字不能为空！");
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
				/*NetworkApi.getProjectListForBindFailSev(LadderControlDeviceSearchOld.this, name, mKeytype,
						new INetCallback() {
							@Override
							public void onCallback(boolean value, String result) {
								if (value) {
									mData.addAll(NetworkApi.parstToFailSevProjectList(result));
									int count = mData.size();
									if (count == 0) {
										mHandler.post(new Runnable() {
											@Override
											public void run() {
												dialog.cancel();
												ToastHelp.showToast(LadderControlDeviceSearchOld.this,
														"未搜索到工程数据！");
											}
										});
										return;
									}
									String[] dataList = new String[count];
									for (int i = 0; i < count; i++) {
										dataList[i] = mData.get(i).projectId+"_"+mData.get(i).ProjectName;
									}
									final ArrayAdapter<String> mWorkOrderAdapter = new ArrayAdapter<String>(
											LadderControlDeviceSearchOld.this, R.layout.simple_spinner_item,
											dataList);
									mWorkOrderAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											dialog.cancel();
											mProjectNameList.setAdapter(mWorkOrderAdapter);
										}
									});
								} else {
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											dialog.cancel();
											ToastHelp.showToast(LadderControlDeviceSearchOld.this, "获取工程名称失败！");
										}
									});
								}
							}
						});*/
				NetworkApi.queryProjectData(SearchAddress.this, name,
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
												ToastHelp.showToast(SearchAddress.this, "未搜索到工程数据！");
											}
										});
										return;
									}
									String[] dataList = new String[count];
									for (int i = 0; i < count; i++) {
										dataList[i] = mData.get(i).ProjectName;
									}
									final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
											SearchAddress.this, R.layout.simple_spinner_item, dataList);
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
											ToastHelp.showToast(SearchAddress.this, "获取工程名称失败！");
										}
									});
								}
							}
						});
			}
		}).start();
	}
}
