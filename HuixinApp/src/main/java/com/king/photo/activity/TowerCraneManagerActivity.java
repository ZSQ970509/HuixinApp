package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.PreferenceUtil;
import com.king.photo.adapter.TowerCraneManagerBaseadapter;
import com.king.photo.model.TowerCraneManagerModel;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class TowerCraneManagerActivity extends Activity {

	TextView mTextTitle;

	ListView mListView;
	TowerCraneManagerBaseadapter baseadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_towercrane_manager);

		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mTextTitle = (TextView) findViewById(R.id.txt_title);

		mListView = (ListView) findViewById(R.id.listview);

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		new GetWrongRecordListByAdminId().execute();
	}

	class GetWrongRecordListByAdminId extends AsyncTask<String, String, ArrayList<TowerCraneManagerModel>> {

		@Override
		protected ArrayList<TowerCraneManagerModel> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return NetworkApi.GetWrongRecordListByAdminIdForTowerCrane(
					PreferenceUtil.getName(), "0");
		}

		@Override
		protected void onPostExecute(ArrayList<TowerCraneManagerModel> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result == null) {
				return;
			}

			baseadapter = new TowerCraneManagerBaseadapter(TowerCraneManagerActivity.this, mHandler, result);
			mListView.setAdapter(baseadapter);

		}

	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {

			}
		}
	};

}
