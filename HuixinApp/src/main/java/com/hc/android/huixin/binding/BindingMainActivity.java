package com.hc.android.huixin.binding;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.BindingProjectItem;
import com.hc.android.huixin.network.BindingTestItem;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 试件跟踪
 */
public class BindingMainActivity extends Activity {

	private EditText mSearchEdit;
	private ProgressDialog progressDialog = null;
	private Handler mHandler = new Handler();
	private ListView search_result;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.binding_main);
		initUI();
	}

	private void initUI() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		findViewById(R.id.abnormal_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mSearchEdit = (EditText) findViewById(R.id.searchWord);
		Button searchBtn = (Button) findViewById(R.id.searchBtn);
		searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				progressDialog = ProgressDialog.show(BindingMainActivity.this, "请稍等...", "获取数据中...", false);
				progressDialog.setCancelable(true);
				new Thread() {
					public void run() {
						getInfo(mSearchEdit.getText().toString());
					}
				}.start();
			}
		});
		search_result = (ListView) findViewById(R.id.search_result);
		getSampleType();
	}

	private String[] sampleType;

	private void getSampleType() {
		new Thread() {
			public void run() {
				NetworkApi.getBindingSampleTypeList(new INetCallback() {
					@Override
					public void onCallback(boolean value, String result) {
						if (value) {
							ArrayList<BindingTestItem> list = NetworkApi.parstToBindingSampleTypeList(result);
							sampleType = new String[list.size() + 1];
							sampleType[0] = "全部";
							for (int i = 0; i < list.size(); i++) {
								sampleType[i + 1] = list.get(i).SampleType;
							}
						}
					}
				});
			}
		}.start();
	}

	private ArrayList<BindingTestItem> listChooseData = new ArrayList<BindingTestItem>();

	private void getInfo(String info) {
		NetworkApi.getBindingProjectItemList(this, info, new INetCallback() {
			@Override
			public void onCallback(boolean value, String result) {
				if (value) {
					final ArrayList<BindingProjectItem> mProjectItems = NetworkApi.parstToBindingProjectList(result);
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							progressDialog.dismiss();
							search_result.setAdapter(new ProjectAdapter(mProjectItems, BindingMainActivity.this));
							search_result.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
									final AlertDialog dialog = new AlertDialog.Builder(BindingMainActivity.this)
											.create();
									if (sampleType == null) {
										sampleType = new String[] { "全部" };
									}
									ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
											BindingMainActivity.this, android.R.layout.simple_spinner_item, sampleType);

									final ArrayList<BindingTestItem> listData = mProjectItems
											.get(position).SampleAPIlist;

									View view = LayoutInflater.from(BindingMainActivity.this)
											.inflate(R.layout.bingding_choose, null);
									Spinner spinner = (Spinner) view.findViewById(R.id.spinner_choose);
									final ListView listView = (ListView) view.findViewById(R.id.list_choose);
									String[] sampleName = new String[listData.size()];
									for (int i = 0; i < listData.size(); i++) {
										sampleName[i] = listData.get(i).SampleName;
									}
									listChooseData.addAll(listData);
									ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
											BindingMainActivity.this, android.R.layout.simple_list_item_single_choice,
											sampleName);
									listView.setAdapter(listAdapter);
									listView.setOnItemClickListener(new OnItemClickListener() {
										@Override
										public void onItemClick(AdapterView<?> arg0, View arg1, int which, long arg3) {
											BindingTestItem testItem = listChooseData.get(which);
											Intent i = new Intent();
											i.setClass(BindingMainActivity.this, BindingNfcActivity.class);
											Bundle bundle = new Bundle();
											bundle.putString("SampleId", testItem.SampleId + "");
											bundle.putString("ProjectName", mProjectItems.get(position).ProjectName);
											bundle.putString("SampleName", testItem.SampleName);
											bundle.putString("SampleType", testItem.SampleType);
											bundle.putString("SampleEPC", testItem.SampleEPC);
											i.putExtras(bundle);
											startActivity(i);
											dialog.dismiss();
										}
									});
									spinner.setAdapter(spinnerAdapter);
									spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
										@Override
										public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
												long arg3) {
											listChooseData.clear();
											String name = sampleType[arg2];
											for (int i = 0; i < listData.size(); i++) {
												if (arg2 == 0) {
													listChooseData.add(listData.get(i));
												} else {
													if (name.equals(listData.get(i).SampleType)) {
														listChooseData.add(listData.get(i));
													}
												}
											}
											String[] sampleName = new String[listChooseData.size()];
											for (int i = 0; i < listChooseData.size(); i++) {
												sampleName[i] = listChooseData.get(i).SampleName;
											}
											ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
													BindingMainActivity.this,
													android.R.layout.simple_list_item_single_choice, sampleName);
											listView.setAdapter(listAdapter);
										}

										@Override
										public void onNothingSelected(AdapterView<?> arg0) {

										}
									});

									dialog.setView(view);
									dialog.show();
								}
							});
						}
					});
				} else {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							progressDialog.dismiss();
							Toast.makeText(BindingMainActivity.this, "搜索失败！", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		});
	}

}
