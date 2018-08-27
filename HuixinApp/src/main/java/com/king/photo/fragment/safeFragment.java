package com.king.photo.fragment;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.MaintenanceTypeItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectItem;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.ActionItem;
import com.hc.android.huixin.view.SpinnerCustomer;
import com.hc.android.huixin.view.TitlePopup;
import com.hc.android.huixin.view.TitlePopup.OnItemOnClickListener;
import com.king.photo.activity.TakeSafeActivity;
import com.king.photo.activity.TakeSafeSearchActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class safeFragment extends Fragment implements OnClickListener {

	private Handler mHandler = new Handler();
	private ListView mProjectNameList;
	private ArrayList<ProjectItem> mData = new ArrayList<ProjectItem>();
	private String mKeytype;
	private EditText mEditKeyWork;
	Button safeSpinner;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.guide_1, container, false);// 关联布局文件

		// 隐藏软键盘弹出
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


		final String title = getActivity().getIntent().getStringExtra("title");
		// TextView titleView = (TextView) rootView.findViewById(R.id.title);
		// titleView.setText(title);

		Button mBtnSearch = (Button) rootView.findViewById(R.id.search);
		mBtnSearch.setOnClickListener(this);
		safeSpinner = (Button) rootView.findViewById(R.id.safeSpinner);
		mEditKeyWork = (EditText) rootView.findViewById(R.id.edit_project_name);

		mProjectNameList = (ListView) rootView.findViewById(R.id.yxcj_listview);
		mProjectNameList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), TakeSafeActivity.class);
				intent.putExtra("title", "安全监管");
				intent.putExtra("projectId", mData.get(arg2).projectId);
				intent.putExtra("projectName", mData.get(arg2).ProjectName);
				startActivity(intent);
			}
		});

		new GetDictByDirTypeAsyncTask().execute();

		return rootView;
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

			
			// popup = new TitlePopup(getActivity());
			// 实例化标题栏弹窗
			try {
				popup = new TitlePopup(getActivity(), safeSpinner.getWidth(), LayoutParams.WRAP_CONTENT);
			} catch (Exception e) {
				// TODO: handle exception
				return;
			}
			for (int i = 0; i < data.size(); i++) {
				list.add(data.get(i).text);
				if (i == 0) {
					mKeytype = data.get(0).text;
					safeSpinner.setText(list.get(0).toString());
				}
				popup.addAction(new ActionItem(getActivity(), list.get(i).toString(), R.drawable.account));
			}

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

	private void searchProject() {
		final String name = mEditKeyWork.getText().toString();
		if (TextUtils.isEmpty(name)) {
			ToastHelp.showToast(getActivity(), "关键字不能为空！");
			return;
		}
		mData.clear();
		final ProgressDialog dialog = DialogUtil.createProgressDialog(getActivity(), "正在获取工程名称...");
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
				NetworkApi.getProjectListForOutSideCapture(getActivity(), name, mKeytype, new INetCallback() {
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
										ToastHelp.showToast(getActivity(), "未搜索到工程数据！");
									}
								});
								return;
							}
							String[] dataList = new String[count];
							for (int i = 0; i < count; i++) {
								dataList[i] = mData.get(i).ProjectName;
							}
							final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
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
									ToastHelp.showToast(getActivity(), "获取工程名称失败！");
								}
							});
						}
					}
				});
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search:
			searchProject();
			break;
		default:
			break;
		}
	}

}
