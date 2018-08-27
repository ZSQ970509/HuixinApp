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
import com.hc.android.huixin.view.TitlePopup;
import com.hc.android.huixin.view.TitlePopup.OnItemOnClickListener;
import com.king.photo.adapter.FullImageDevListAdapter;
import com.king.photo.model.FullImageDevModel;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class imageFragment extends Fragment implements OnClickListener {

	private Handler mHandler = new Handler();
	private ListView mProjectNameList;
	private ArrayList<ProjectItem> mData = new ArrayList<ProjectItem>();
	private String mKeytype;
	private EditText mEditKeyWork;
	Button safeSpinner;
	AlertDialog mAlertDialog = null;

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

				// Intent intent = new Intent(getActivity(),
				// TakeSafeActivity.class);
				// intent.putExtra("title", "影像日志");
				// intent.putExtra("projectId", mData.get(arg2).projectId);
				// intent.putExtra("projectName", mData.get(arg2).ProjectName);

				new GetFullImageDevListTypeAsyncTask().execute(mData.get(arg2).projectId);

			}
		});

		new GetDictByDirTypeAsyncTask().execute();

		return rootView;

	}

	private class GetFullImageDevListTypeAsyncTask extends AsyncTask<String, Void, ArrayList<FullImageDevModel>> {

		@Override
		protected ArrayList<FullImageDevModel> doInBackground(String... params) {
			return new NetworkApi().getProjectListForFullImage(params[0], "30");//全景拼图要设置为30
		}

		@Override
		protected void onPostExecute(final ArrayList<FullImageDevModel> data) {
			if (data == null) {
				return;
			}
			// final ArrayList<String> list = new ArrayList<String>();

			showAlertDialog(getActivity(), data);
		}
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

	public boolean showAlertDialog(final Context context, final ArrayList<FullImageDevModel> data) {

		FullImageDevListAdapter baseAdapter = new FullImageDevListAdapter(context, data);

		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.fullimage_alertdialog, null);

		TextView titleView = (TextView) view.findViewById(R.id.titleView);
		titleView.setText("  设备列表");
		ListView listview = (ListView) view.findViewById(R.id.listView);

		listview.setAdapter(baseAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		mAlertDialog = builder.create();
		mAlertDialog.setCanceledOnTouchOutside(false);
		mAlertDialog.show();
		mAlertDialog.getWindow().setContentView(view);
		// mAlertDialog.getWindow().setLayout(150, 320);
		return false;

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
