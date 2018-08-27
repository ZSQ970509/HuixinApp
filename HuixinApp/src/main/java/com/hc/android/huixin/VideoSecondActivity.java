package com.hc.android.huixin;

import java.util.ArrayList;

import com.ffcs.surfingscene.function.GeyeUserLogin;
import com.ffcs.surfingscene.http.HttpCallBack;
import com.ffcs.surfingscene.response.BaseResponse;
import com.ffcs.surfingscene.util.PublicUtils;
import com.hc.android.huixin.network.CameraItem;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.ToastHelp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class VideoSecondActivity extends Activity {

	private ListView search_result;
	private ArrayList<CameraItem> searchlist = new ArrayList<CameraItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_second);
		initView();
		String projectId = getIntent().getStringExtra("ProjectId");
		initVideoSDK(projectId);
	}

	private void initView() {
		((TextView) findViewById(R.id.txt_projectname)).setText(getIntent().getStringExtra("ProjectName") + "");

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		search_result = (ListView) findViewById(R.id.search_result);
	}

	/**
	 * 加载视频播放SDK
	 * @param projectId
	 */
	private void initVideoSDK(final String projectId) {
		PublicUtils.savekey(this, "fzhc", "fzhc1234");//设置appkey和appSecret
		//100  代表 在用中的设备（监控+维护）
		NetworkApi.queryProjectCameraBy(projectId, "100", new INetCallback() {
			@Override
			public void onCallback(boolean value, String result) {
				ArrayList<CameraItem> list = NetworkApi.parstToCameraList(result);
				searchlist.addAll(list);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						
						search_result.setAdapter(new CameraAdapter(searchlist));
						search_result.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
								final CameraItem item = searchlist.get(position);
								if (!item.IsOnline || item.VideoId.equals("")) {
									ToastHelp.showToast(VideoSecondActivity.this, "该摄像头不在线，请稍后再试。。");
									return;
								}

								final ProgressDialog dialog = new ProgressDialog(VideoSecondActivity.this);
								dialog.show();

								final String username = item.Cam_LoginName;
								final Long password = item.Cam_LoginPsw;
								final Integer areaCode = item.areaCode;
								//设置登录账号和密码，获取该项目下摄像头的相关视频播放信息
								GeyeUserLogin.getInstance(VideoSecondActivity.this).userLogin(username, password,
										areaCode, new HttpCallBack<BaseResponse>() {
											@Override
											public void callBack(BaseResponse arg0, String arg1) {

												if ("1".equals(arg0.getReturnCode())) {
													Intent intent = new Intent(VideoSecondActivity.this,
															PuIdPlayerActivity.class);
													intent.putExtra("puName", item.CamName);
													intent.putExtra("puId", item.VideoId);
													intent.putExtra("camId", item.CamId);
													intent.putExtra("userId", item.Cam_LoginName);
													intent.putExtra("ptzlag", item.ptzlag);
													intent.putExtra("areaCode", item.areaCode);
													System.out.println("1111111111111111111111111111111111111111");
													startActivity(intent);
													dialog.cancel();
												} else {
													dialog.cancel();
													new AlertDialog.Builder(VideoSecondActivity.this).setTitle("播放提示")
															.setMessage("播放失败："+arg0.getMsg()).setPositiveButton("确定",
																	new DialogInterface.OnClickListener() {
																		@Override
																		public void onClick(DialogInterface dialog,
																				int which) {
																		}
																	})
															.setCancelable(false).create().show();
											
												}
											}
										});

							}
						});
					}
				});
			}
		});
	}

	private class CameraAdapter extends BaseAdapter {

		private ArrayList<CameraItem> mDataList;

		CameraAdapter(ArrayList<CameraItem> projectList) {
			mDataList = projectList;
		}

		@Override
		public int getCount() {
			return mDataList.size();
		}

		@Override
		public Object getItem(int position) {
			return mDataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bingding_cameraitem, null);
			}
			TextView text = (TextView) convertView.findViewById(R.id.item_title);
			text.setText(mDataList.get(position).CamName);
			text.setTextSize(18);
			text.setTextColor(Color.BLACK);

			ImageView icon = (ImageView) convertView.findViewById(R.id.item_icon);
		

			if (mDataList.get(position).IsOnline && !mDataList.get(position).VideoId.equals("")) {
				icon.setImageResource(R.drawable.icon_camera_on);
			} else {
				icon.setImageResource(R.drawable.icon_camera_off);
			}

			return convertView;
		}

	}

}
