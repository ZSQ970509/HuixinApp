package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.TakePhoto2Activity;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectAdjustUsersItem;
import com.hc.android.huixin.network.UsersItem;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityAdjustProject extends Activity implements OnClickListener {

	String mUsers;
	String MType;
	String ProjectName;
	String ProjectNumberr;
	String Mid;
	private EditText mEditUsers;
	private EditText mProjectNumber;
	Button mBtnAdjust;
	Button mBtnProject;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adjust_project);
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub

		findViewById(R.id.regulatory_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		findViewById(R.id.btn_search).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityAdjustProject.this, ActivityAdjustProjectMain.class);
				startActivity(intent);
				finish();
			}
		});
		mProjectNumber = (EditText) findViewById(R.id.edit_project_name);
		mEditUsers = (EditText) findViewById(R.id.edit_users);
		mBtnAdjust = (Button) findViewById(R.id.take_adjust);
		mBtnAdjust.setOnClickListener(this);
		mBtnProject = (Button) findViewById(R.id.btn_project);
		mBtnProject.setOnClickListener(this);
		ProjectName = getIntent().getStringExtra("ProjName");
		((TextView) findViewById(R.id.text_project_name)).setText(ProjectName);
		new UsersAsyncTask().execute();
		new ProjectAsyncTask().execute(getIntent().getStringExtra("ProjId"));

	}

	private ListView user_multiChoiceListView;
	String mTempProjectNumberr;
	String mTempUsers;

	// 随行人员
	private class UsersAsyncTask extends AsyncTask<Void, ArrayList<UsersItem>, ArrayList<UsersItem>> {

		@Override
		protected ArrayList<UsersItem> doInBackground(Void... params) {
			return new NetworkApi().GetBuilders();
		}

		@Override
		protected void onPostExecute(final ArrayList<UsersItem> data) {
			if (data == null) {
				return;
			}

			final String[] user_items = new String[data.size()];
			final boolean[] user_checkedItems = new boolean[data.size()];
			for (int i = 0; i < data.size(); i++) {
				user_items[i] = data.get(i).UserName;
				user_checkedItems[i] = false;
			}
			findViewById(R.id.btn_users).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdjustProject.this);
					builder.setTitle("请选择随行人员");
					builder.setMultiChoiceItems(user_items, user_checkedItems, new OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							// TODO Auto-generated method stub
							// user_checkedItems[which]=isChecked;
						}
					});
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							mTempUsers = "";
							mTempProjectNumberr = "";
							for (int i = 0; i < user_items.length; i++) {
								if (user_multiChoiceListView.getCheckedItemPositions().get(i)) {
									if (!TextUtils.isEmpty(mTempUsers)) {

										mTempUsers += "|";
										mTempProjectNumberr += ",";
									}
									mTempUsers += data.get(i).UserName;
									mTempProjectNumberr += data.get(i).UserAccount;
								}
							}
							mUsers = mTempUsers;
							ProjectNumberr = mTempProjectNumberr;
							mEditUsers.setText(mUsers);

						
						}
					});
					AlertDialog dialog = builder.create();
					user_multiChoiceListView = dialog.getListView();
					dialog.show();
				}
			});

		}

	}

	// 工单号
	private class ProjectAsyncTask
			extends AsyncTask<String, ArrayList<ProjectAdjustUsersItem>, ArrayList<ProjectAdjustUsersItem>> {

		@Override
		protected ArrayList<ProjectAdjustUsersItem> doInBackground(String... params) {
			return new NetworkApi().GetJobNumber(params[0]);
		}

		@Override
		protected void onPostExecute(final ArrayList<ProjectAdjustUsersItem> data) {
			if (data == null || data.size() == 0) {
				return;
			}
			final String[] user_items = new String[data.size()];
			final boolean[] user_checkedItems = new boolean[data.size()];
			for (int i = 0; i < data.size(); i++) {
				user_items[i] = data.get(i).MType + "(" + data.get(i).Mid + ")";
				user_checkedItems[i] = false;
			}
			ProjectName = user_items[0];
			Mid = data.get(0).Mid;
			MType = data.get(0).MType;
			mUsers = data.get(0).MManList;
			ProjectNumberr = data.get(0).MManListID;
			mProjectNumber.setText(ProjectName);
			mUsers = mUsers.substring(0, mUsers.length() - 1).replace(",", "|");
			mEditUsers.setText(mUsers);
			findViewById(R.id.btn_project).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAdjustProject.this);
					builder.setTitle("请选择工单号");
					builder.setSingleChoiceItems(user_items, 0, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							ProjectName = user_items[which];
							Mid = data.get(which).Mid;
							mUsers = data.get(which).MManList;
							mUsers = mUsers.substring(0, mUsers.length() - 1).replace(",", "|");
							MType = data.get(which).MType;
							mProjectNumber.setText(ProjectName);
							mEditUsers.setText(mUsers);
						}
					});
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();

						}
					});
					AlertDialog dialog = builder.create();
					user_multiChoiceListView = dialog.getListView();
					dialog.show();
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
		case R.id.take_adjust:
			TakeAdjust();
			break;

		default:
			break;
		}
	}

	private void TakeAdjust() {
		// TODO Auto-generated method stub
		if (TextUtils.isEmpty(mUsers)) {
			ToastHelp.showToast(ActivityAdjustProject.this, "随行人员不能为空！");
			return;
		}
		if (TextUtils.isEmpty(ProjectNumberr)) {
			ToastHelp.showToast(ActivityAdjustProject.this, "工单号不能为空！");
			return;
		}
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在修改...");
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
				// TODO Auto-generated method stub
				NetworkApi.updateAdjustProject(Mid, MType, ProjectNumberr,
						PreferenceUtil.getName(), new INetCallback() {

							@Override
							public void onCallback(boolean value, String result) {
								// TODO Auto-generated method stub
								if (value) {
									mHandler.post(new Runnable() {
										@Override
										public void run() {
											ToastHelp.showToast(ActivityAdjustProject.this, "发送成功！");
										}
									});
									// mHandler.postDelayed(new Runnable() {
									// @Override
									// public void run() {
									// finish();
									// }
									// }, 2000);
								} else {
									mHandler.post(new Runnable() {
										@Override
										public void run() {

											ToastHelp.showToast(ActivityAdjustProject.this, "发送失败！");
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
