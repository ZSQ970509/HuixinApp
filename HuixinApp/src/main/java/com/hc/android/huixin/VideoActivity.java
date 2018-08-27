package com.hc.android.huixin;

import java.util.ArrayList;

import com.hc.android.huixin.binding.ProjectAdapter;
import com.hc.android.huixin.network.BindingProjectItem;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.ToastHelp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 视频查看
 */
public class VideoActivity extends Activity {

	private EditText mSearchEdit;
	private ProgressDialog progressDialog = null;
	private Handler mHandler = new Handler();
	private ListView search_result;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_main);
		initUI();
	}

	/**
	 * 加载UI
	 */
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
				final String info = mSearchEdit.getText().toString().trim();
				if (TextUtils.isEmpty(info)) {
					ToastHelp.showToast(VideoActivity.this, "请输入工程名称关键字");
					return;
				}

				progressDialog = ProgressDialog.show(VideoActivity.this, "请稍等...", "获取数据中...", false);
				progressDialog.setCancelable(true);
				new Thread() {
					public void run() {
						getInfo(info);
					}
				}.start();
			}
		});
		search_result = (ListView) findViewById(R.id.search_result);
		new Thread() {
			public void run() {
				getInfo("");
			}
		}.start();
	}
	
	/**
	 * 获取工程
	 * @param info
	 */
	private void getInfo(String info) {
		NetworkApi.getVideoProjectList(this, info, new INetCallback() {
			@Override
			public void onCallback(boolean value, String result) {
				if (value) {
					final ArrayList<BindingProjectItem> mProjectItems = NetworkApi.parstToBindingProjectList(result);
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							if (progressDialog != null) {
								progressDialog.dismiss();
							}
							search_result.setAdapter(new ProjectAdapter(mProjectItems, VideoActivity.this));
							search_result.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
									String ProjectId = mProjectItems.get(position).ProjectId;
									Intent intent = new Intent(VideoActivity.this, VideoSecondActivity.class);
									intent.putExtra("ProjectId", ProjectId);
									intent.putExtra("ProjectName", mProjectItems.get(position).ProjectName);
					
									startActivity(intent);
								}
							});
						}
					});
				} else {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							if (progressDialog != null) {
								progressDialog.dismiss();
							}
							Toast.makeText(VideoActivity.this, "搜索失败！", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		});
	}

}
