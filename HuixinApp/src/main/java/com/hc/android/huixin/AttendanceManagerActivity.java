package com.hc.android.huixin;

import org.json.JSONException;
import org.json.JSONObject;

import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.PreferenceUtil;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

/**
 * 网络考勤
 */
public class AttendanceManagerActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance_query);
		initView();
	}

	private void initView() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		findViewById(R.id.attendance_back).setOnClickListener(this);
		findViewById(R.id.attendance_notice).setOnClickListener(this);
		findViewById(R.id.attendance_detail).setOnClickListener(this);
		findViewById(R.id.attendance_day).setOnClickListener(this);
		findViewById(R.id.attendance_month).setOnClickListener(this);
		findViewById(R.id.attendance_mobile).setOnClickListener(this);
		findViewById(R.id.attendance_path).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.attendance_back:
			finish();
			break;
		case R.id.attendance_notice:
			String name = PreferenceUtil.getName();
			NetworkApi.GetWarnAttendance(new INetCallback() {
				@Override
				public void onCallback(boolean value, final String result) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							try {
								JSONObject json = new JSONObject(result);
								int resultCode = json.getInt("result");
								if (resultCode == 1) {
									String PushContent = json.getString("PushContent");
									PushContent=PushContent.substring(0, 13);
									new AlertDialog.Builder(AttendanceManagerActivity.this).setMessage(PushContent+"\n本次打卡时间:07:47")
											.create().show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}, name);
			break;
		case R.id.attendance_detail:
			startActivity(new Intent(this, AttendanceDetailActivity.class));
			break;
		case R.id.attendance_day:
			startActivity(new Intent(this, AttendanceDayDetailActivity.class));
			break;
		case R.id.attendance_month:
			startActivity(new Intent(this, AttendanceMonthDetailActivity.class));
			break;
		case R.id.attendance_mobile:
			startActivity(new Intent(this, AttendanceMobileActivity.class));
			//startActivity(new Intent(this, ProjectAttendanceActivity.class));

			break;
			case R.id.attendance_path:
				if(PreferenceUtil.getAttendandcePathProjectHumen()){
					DialogUtil.showDialog(AttendanceManagerActivity.this,"工程人员考勤路径已开启，请先结束考勤！");
					return;
				}
				startActivity(new Intent(this, AttendancePathActivity.class));
				break;
		default:
			break;
		}
	}

}
