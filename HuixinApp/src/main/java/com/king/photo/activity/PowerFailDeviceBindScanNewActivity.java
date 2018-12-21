package com.king.photo.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hc.andorid.zxing.app.CaptureActivity;
import com.hc.android.huixin.R;
import com.hc.android.huixin.base.*;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.view.CustomDialog;
import com.hc.android.huixin.view.DefaultDialog;
import com.hc.android.huixin.view.LoadingDialog;
import com.hc.android.huixin.view.SimgleSelectDialog.Builder;
import com.hc.android.huixin.base.BaseActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class PowerFailDeviceBindScanNewActivity extends BaseActivity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	@BindView(R.id.SrlInStallOpenSelectPro)
	SwipeRefreshLayout mSwipeRefreshLayout;
	@BindView(R.id.RvInStallOpenSelectDriver)
	RecyclerView mRecyclerView;
	@BindView(R.id.edit_sn)
	EditText mEditSn;
	@BindView(R.id.btn_qr)
	ImageView mBtnQR;
	@BindView(R.id.btn_certain)
	Button mBtnCertain;
	LoadingDialog loadingDialog = null;
	String CamIds = "";
	Handler mHandler = new Handler();
	String DevPowerSn = "";
	String showContent = "";

	@Override
	protected int getLayoutId() {
		return R.layout.activity_powerfaildevice_bind_scan_new;
	}

	@Override
	protected void initView() {

	}
	@Override
	protected void initData(Intent intent) {

		//CamIds = getIntent().getStringExtra("CamIds");
	}
	@OnClick({R.id.regulatory_back,R.id.btn_certain,R.id.btn_qr})
	void onClick(View v) {
		switch (v.getId()) {
			case R.id.regulatory_back:
				setResult(RESULT_OK, getIntent());
				finish();
				break;
			case R.id.btn_certain:
				final CustomDialog.Builder builder = new CustomDialog.Builder(PowerFailDeviceBindScanNewActivity.this);
				builder.setMessage("你确定要绑定？");
				builder.setTitle("提示");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, int which) {
						// 设置你的操作事项
						setbind(dialog);
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
				break;
			case R.id.btn_qr:
				Intent intent = new Intent();
				intent.setClass(PowerFailDeviceBindScanNewActivity.this, CaptureActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			break;
		}
	}

	private void setbind(final DialogInterface dialog) {
		DevPowerSn = mEditSn.getText().toString().trim();
		if ("".equals(DevPowerSn) || DevPowerSn == null) {
			DefaultDialog.showDialog(PowerFailDeviceBindScanNewActivity.this, "请输入掉电设备号");
			return;
		}
		// DevPowerSn = "862631036827425";// 测试用
		dialog.dismiss();
		loadingDialog = new LoadingDialog(PowerFailDeviceBindScanNewActivity.this);
		loadingDialog.setMessage("正在绑定中...").show();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				NetworkApi.sendBindDevPowerSnToCamIds(CamIds, DevPowerSn, PowerFailDeviceBindScanNewActivity.this,
						new INetCallback() {

							@Override
							public void onCallback(boolean value, final String result) {
								// TODO Auto-generated method stub
								if (value) {
									mHandler.post(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated
											// method stub

											DefaultDialog.showDialog(PowerFailDeviceBindScanNewActivity.this,
													"绑定断电设备成功");
										}
									});
								} else {
									try {
										int flag = Integer.parseInt(new JSONObject(result).getString("result"));

										switch (flag) {
										case -1:
											showContent = "设备号无效";
											break;
										case 0:
											showContent = "绑定断电设备失败";
											break;
										case -2:
											showContent = "绑定断电设备失败";
											break;
										case 101:

											mHandler.post(new Runnable() {

												@Override
												public void run() {
													// TODO
													// Auto-generated
													// method
													// stub
													loadingDialog.cancel();

																try {

														// 指定下拉列表的显示数据
														JSONArray arr;
														arr = new JSONObject(result).getJSONArray("data");

														final String[] list = new String[arr.length()];
														for (int i = 0; i < arr.length(); i++) {
															list[i] = arr.getJSONObject(i)
																	.getString("CamSeqID");
														}

														Builder builder = new Builder(PowerFailDeviceBindScanNewActivity.this);
														builder.setItemMessage(list);
														
														builder.setTitle("请选择一个设备号");
														
														
														builder.setOnItemClick(new OnItemClickListener() {

															@Override
															public void onItemClick(AdapterView<?> arg0, View arg1,
																	int arg2, long arg3) {
																// TODO Auto-generated method stub
																mEditSn.setText(list[arg2]);
																
															}

														});
														// 设置一个下拉的列表选择项
													
														/*builder.setItems(list,
																new DialogInterface.OnClickListener() {
																	@Override
																	public void onClick(DialogInterface dialog,
																			int which) {
																		mEditSn.setText(list[which]);

																	}
																});*/
														builder.create().show();
														return;
													} catch (JSONException e) {
														// TODO
														// Auto-generated
														// catch block
														e.printStackTrace();
														showContent = "绑定断电设备失败";

														DefaultDialog.showDialog(
																PowerFailDeviceBindScanNewActivity.this,
																showContent);

													}
												}
											});

											return;
										case 102:

											showContent = "断电设备掉电,绑定失败";

											break;
										default:
											showContent = "绑定断电设备失败";
											break;
										}
										mHandler.post(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated
												// method stub
												DefaultDialog.showDialog(PowerFailDeviceBindScanNewActivity.this,
														showContent);
											}
										});

									} catch (NumberFormatException e) {
										// TODO Auto-generated catch
										// block
										e.printStackTrace();
									} catch (JSONException e) {
										// TODO Auto-generated catch
										// block
										e.printStackTrace();
									}

								}
								mHandler.post(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method
										// stub
										
										loadingDialog.cancel();
										
									
									}
								});

							}
						});
			}
		}).start();
	}

	
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		setResult(RESULT_OK, getIntent());
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				try {

					String result = data.getStringExtra("SCAN_RESULT");
					Log.e("result", result);
					String[] split = result.split(";");
					Log.e("split", split[0]);
					/*Log.e("split", split[1]);
					Log.e("split", split[2]);*/
					String SN = split[0].replace("IMEI:", "");
					mEditSn.setText(SN);
				} catch (Exception e) {
					mEditSn.setText("");

					DefaultDialog.showDialog(PowerFailDeviceBindScanNewActivity.this, "二维码异常！");
					e.getStackTrace();
				}

			}
			break;
		}
	}

}
