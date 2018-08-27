package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.view.CustomDialog;
import com.hc.android.huixin.view.DefaultDialog;
import com.hc.android.huixin.view.LoadingDialog;
import com.king.photo.adapter.PowerFailDeviceBindAdapter;
import com.king.photo.adapter.PowerFailDeviceBindAdapter.ViewHolder;
import com.king.photo.model.PowerDevBindModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class PowerFailDeviceBindMainActivity extends Activity {
	String projectId;
	private ProgressDialog dialog;
	ListView mListView;
	Button mBtnBind;
	Button mBtnUnBind;
	Button mBtnInsert;
	Button mBtnUpdate;
	LoadingDialog loadingDialog = null;
	Handler mHandler = new Handler();
	private final static int Bind_GREQUEST_CODE = 0x101;
	ArrayList<PowerDevBindModel> data = new ArrayList<PowerDevBindModel>();
	PowerFailDeviceBindAdapter adapter = null;
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(adapter!=null){
			new getDevMsgForPowerDevAsync().execute(projectId);
			adapter.notifyDataSetChanged();
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_powerfaildevice_bind_main);

		mListView = (ListView) findViewById(R.id.listview);
		mBtnBind = (Button) findViewById(R.id.btn_bind);
		mBtnUnBind = (Button) findViewById(R.id.btn_unbind);
		mBtnInsert = (Button) findViewById(R.id.btn_insert);
		mBtnUpdate = (Button) findViewById(R.id.btn_update);
		projectId = getIntent().getStringExtra("projectId");
		new getDevMsgForPowerDevAsync().execute(projectId);
		adapter = new PowerFailDeviceBindAdapter(PowerFailDeviceBindMainActivity.this, data);
		mListView.setAdapter(adapter);

		// 绑定listView的监听器
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				//PowerFailDeviceBindAdapter.initDate();
				
				// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
				ViewHolder holder = (ViewHolder) arg1.getTag();
				// 改变CheckBox的状态
				holder.mCheckBox.toggle();
				// 将CheckBox的选中状况记录下来
				PowerFailDeviceBindAdapter.getIsSelected().put(arg2, holder.mCheckBox.isChecked());
				//mWorkOrderAdapter.notifyDataSetChanged();
			}
		});

		findViewById(R.id.regulatory_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mBtnInsert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PowerFailDeviceBindMainActivity.this, InsertCabinetActivity.class);
				intent.putExtra("projectId", getIntent().getStringExtra("projectId"));
				startActivity(intent);
			}
		});
		mBtnUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if()
				int CamCount = PowerFailDeviceBindAdapter.GetCamCount();
				if(CamCount != 0){
					if(CamCount == 1){
						dialog = DialogUtil.createProgressDialog(PowerFailDeviceBindMainActivity.this, "正在检验...");
						dialog.show();
						new CheckCabinetAsyncTask().execute();
					}else{
						DefaultDialog.showDialog(PowerFailDeviceBindMainActivity.this, "只能选择一个设备进行修改");
						return;
					}
				}else{
					DefaultDialog.showDialog(PowerFailDeviceBindMainActivity.this, "你没有选择要修改的设备");
					return;
				}
				
			}
		});
		mBtnBind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String CamIds = PowerFailDeviceBindAdapter.GetCamIds();
				if ("".equals(CamIds) || CamIds == null) {
					DefaultDialog.showDialog(PowerFailDeviceBindMainActivity.this, "你没有选择要绑定的设备");
					return;
				}

				Intent intent = new Intent(PowerFailDeviceBindMainActivity.this, PowerFailDeviceBindScanActivity.class);
				intent.putExtra("CamIds", CamIds);
				intent.putExtra("projectId", getIntent().getStringExtra("projectId"));
				startActivityForResult(intent, Bind_GREQUEST_CODE);
			}
		});

		mBtnUnBind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				CustomDialog.Builder builder = new CustomDialog.Builder(PowerFailDeviceBindMainActivity.this);
				builder.setMessage("你确定要解绑？");
				builder.setTitle("提示");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, int which) {

						// 设置你的操作事项
						dialog.dismiss();
						loadingDialog = new LoadingDialog(PowerFailDeviceBindMainActivity.this);
						loadingDialog.setMessage("正在解绑中...").show();

						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								NetworkApi.sendUnBindDevPowerSnToCamIds(PowerFailDeviceBindAdapter.GetCamIdsAndSnList(),
										new INetCallback() {

											@Override
											public void onCallback(boolean value, final String result) {
												// TODO Auto-generated method
												// stub
												if (value) {
													mHandler.post(new Runnable() {

														@Override
														public void run() {
															// TODO
															// Auto-generated
															// method stub
															DefaultDialog.showDialog(
																	PowerFailDeviceBindMainActivity.this, "解绑断电设备成功");
														}
													});
												} else {
													mHandler.post(new Runnable() {

														@Override
														public void run() {
															// TODO
															// Auto-generated
															// method stub
															DefaultDialog.showDialog(
																	PowerFailDeviceBindMainActivity.this,
																	"解绑断电设备失败,请重试");
														}
													});

												}
												mHandler.post(new Runnable() {

													@Override
													public void run() {
														// TODO Auto-generated
														// method
														// stub
														loadingDialog.cancel();

														String projectId = getIntent().getStringExtra("projectId");
														new getDevMsgForPowerDevAsync().execute(projectId);
													}
												});
											}
										});
							}
						}).start();

					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						dialog.dismiss();
					}
				});
				builder.create().show();

			}
		});

	}
	
	class getDevMsgForPowerDevAsync extends AsyncTask<String, String, ArrayList<PowerDevBindModel>> {

		@Override
		protected ArrayList<PowerDevBindModel> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new NetworkApi().getDevMsgForPowerDevBind(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<PowerDevBindModel> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result == null) {
				System.out.println("获取值失败");
				return;
			}

			data.clear();
			data.addAll(result);
			adapter.initDate();
			adapter.notifyDataSetChanged();

		}

	}
	private class CheckCabinetAsyncTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			return new NetworkApi().checkCabinet(PowerFailDeviceBindAdapter.GetCamIds());
		}

		@Override
		protected void onPostExecute(Boolean data) {
			dialog.dismiss();
			if (data) {
				Intent intent = new Intent(PowerFailDeviceBindMainActivity.this, UpdateCabinetActivity.class);
				intent.putExtra("camId", PowerFailDeviceBindAdapter.GetCamIds());
				startActivity(intent);
			} else {
				DefaultDialog.showDialog(PowerFailDeviceBindMainActivity.this, "此设备不是机柜设备无法进行修改！");
			}

		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case Bind_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				String projectId = getIntent().getStringExtra("projectId");
				new getDevMsgForPowerDevAsync().execute(projectId);
			}
			break;

		}

	}

}
