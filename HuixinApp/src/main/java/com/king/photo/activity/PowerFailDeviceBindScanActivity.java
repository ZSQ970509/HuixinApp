package com.king.photo.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hc.andorid.zxing.app.CaptureActivity;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.view.CustomDialog;
import com.hc.android.huixin.view.DefaultDialog;
import com.hc.android.huixin.view.LoadingDialog;
import com.hc.android.huixin.view.SimgleSelectDialog;
import com.hc.android.huixin.view.SimgleSelectDialog.Builder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PowerFailDeviceBindScanActivity extends Activity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	EditText mEditSn;
	Button mBtnQR;
	Button mBtnCertain;
	LoadingDialog loadingDialog = null;
	String CamIds = "";
	Handler mHandler = new Handler();
	String DevPowerSn = "";
	String showContent = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_powerfaildevice_bind_scan);
		mEditSn = (EditText) findViewById(R.id.edit_sn);
		mBtnQR = (Button) findViewById(R.id.btn_qr);
		mBtnCertain = (Button) findViewById(R.id.btn_certain);

		CamIds = getIntent().getStringExtra("CamIds");

		findViewById(R.id.regulatory_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(RESULT_OK, getIntent());
				finish();
			}
		});

		mBtnCertain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				final CustomDialog.Builder builder = new CustomDialog.Builder(PowerFailDeviceBindScanActivity.this);
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
			}
		});

		/**
		 * 扫描二维码
		 */
		mBtnQR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PowerFailDeviceBindScanActivity.this, CaptureActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});

	}


	private void setbind(final DialogInterface dialog) {
		DevPowerSn = mEditSn.getText().toString().trim();
		if ("".equals(DevPowerSn) || DevPowerSn == null) {
			DefaultDialog.showDialog(PowerFailDeviceBindScanActivity.this, "请输入掉电设备号");
			return;
		}
		// DevPowerSn = "862631036827425";// 测试用
		dialog.dismiss();
		loadingDialog = new LoadingDialog(PowerFailDeviceBindScanActivity.this);
		loadingDialog.setMessage("正在绑定中...").show();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				NetworkApi.sendBindDevPowerSnToCamIds(CamIds, DevPowerSn, PowerFailDeviceBindScanActivity.this,
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

											DefaultDialog.showDialog(PowerFailDeviceBindScanActivity.this,
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

														SimgleSelectDialog.Builder builder = new Builder(PowerFailDeviceBindScanActivity.this);
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
																PowerFailDeviceBindScanActivity.this,
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
												DefaultDialog.showDialog(PowerFailDeviceBindScanActivity.this,
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

					DefaultDialog.showDialog(PowerFailDeviceBindScanActivity.this, "二维码异常！");
					e.getStackTrace();
				}

			}
			break;
		}
	}

}
