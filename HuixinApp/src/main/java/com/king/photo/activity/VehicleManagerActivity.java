package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.DriverManageTypeItem;
import com.hc.android.huixin.network.NetworkApi;

import com.king.photo.adapter.DriverManageAdapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 读取车辆状态 时间：2016年4月14日09:17:21
 * 
 * @author xiao
 */
public class VehicleManagerActivity extends Activity implements OnClickListener {

	ListView mCarList;
	Handler mHandler = new Handler();
	ArrayList<DriverManageTypeItem> mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_manager);
		initView();
	}

	void initView() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);
		findViewById(R.id.vehicle_back).setOnClickListener(this);
		mCarList = (ListView) findViewById(R.id.car_list);
		new GetCarAsyncTask().execute();
	}

	private class GetCarAsyncTask extends AsyncTask<Void, Void, ArrayList<DriverManageTypeItem>> {

		@Override
		protected ArrayList<DriverManageTypeItem> doInBackground(Void... params) {
			return new NetworkApi().GetDriverState();
		}

		@Override
		protected void onPostExecute(final ArrayList<DriverManageTypeItem> data) {
			if (data == null) {
				return;
			}

			if (data.size() == 0) {
				return;
			}
			DriverManageAdapter adapter = new DriverManageAdapter(VehicleManagerActivity.this, mHandler, data,
					getIntent().getStringExtra("title"));
			mCarList.setAdapter(adapter);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.vehicle_back:
			back();
			break;
		default:
			break;
		}
	}

	private void back() {
		finish();
	}
}
