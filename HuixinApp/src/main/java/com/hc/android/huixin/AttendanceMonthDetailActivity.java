package com.hc.android.huixin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hc.android.huixin.network.BeanAttendMonthReport;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.PreferenceUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

public class AttendanceMonthDetailActivity extends Activity implements OnClickListener {

	private EditText mEditDate1;
	private EditText mEditDate2;

	private List<BeanAttendMonthReport> mData = new ArrayList<BeanAttendMonthReport>();
	private MyAdapter mDdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance_detail_month);
		initView();
	}

	private void initView() {
		findViewById(R.id.attendance_back).setOnClickListener(this);
		findViewById(R.id.btn_search).setOnClickListener(this);
		mEditDate1 = (EditText) findViewById(R.id.edit_date1);
		mEditDate1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Calendar c = Calendar.getInstance();
					int year = c.get(Calendar.YEAR);
					int month = c.get(Calendar.MONTH);
					if (month == 1) {
						month = 12;
						year--;
					} else {
						month--;
					}
					int day = c.get(Calendar.DAY_OF_MONTH);
					new DatePickerDialog(AttendanceMonthDetailActivity.this, new OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
							String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
							mEditDate1.setText(date);
						}
					}, year, month, day).show();
					return true;
				}
				return false;
			}
		});
		mEditDate2 = (EditText) findViewById(R.id.edit_date2);
		mEditDate2.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Calendar c = Calendar.getInstance();
					int year = c.get(Calendar.YEAR);
					int month = c.get(Calendar.MONTH);
					int day = c.get(Calendar.DAY_OF_MONTH);
					new DatePickerDialog(AttendanceMonthDetailActivity.this, new OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
							String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
							mEditDate2.setText(date);
						}
					}, year, month, day).show();
					return true;
				}
				return false;
			}
		});

		ListView listView = (ListView) findViewById(R.id.attendance_list);
		mDdapter = new MyAdapter();
		listView.setAdapter(mDdapter);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Calendar cday = Calendar.getInstance();
		int year = cday.get(Calendar.YEAR);
		int month = cday.get(Calendar.MONTH);
		int day = cday.get(Calendar.DAY_OF_MONTH);
		String date = year - 1 + "-" + month + 1 +  "-" +day;
		mEditDate1.setText(date);
		mEditDate2.setText(df.format(new Date()));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.attendance_back:
			finish();
			break;
		case R.id.btn_search:
			getData();
			break;
		default:
			break;
		}
	}

	private void getData() {
		String date1 = mEditDate1.getText().toString();
		String date2 = mEditDate2.getText().toString();
		String UserAccount = PreferenceUtil.getName();
		NetworkApi.AttendMonthReport(new INetCallback() {
			@Override
			public void onCallback(boolean value, String result) {
				List<BeanAttendMonthReport> timeList = NetworkApi.parstToAttendMonthReport(result);
				mData.clear();
				mData.addAll(timeList);

				runOnUiThread(new Runnable() {
					public void run() {
						mDdapter.notifyDataSetChanged();
					}
				});

			}
		}, UserAccount, date1, date2);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance_time, null);
				holder = new ViewHolder();
				holder.content = (TextView) convertView.findViewById(R.id.item_text2);
				holder.time = (TextView) convertView.findViewById(R.id.item_text1);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.content.setText(mData.get(position).RepContent);
			holder.time.setText(mData.get(position).AttendMonth);
			return convertView;
		}

	}

	static class ViewHolder {
		TextView content;
		TextView time;
	}

}
