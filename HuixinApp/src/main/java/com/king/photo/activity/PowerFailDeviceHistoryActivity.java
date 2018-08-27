package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.king.photo.adapter.NetFailDeviceHistoryAdapter;
import com.king.photo.adapter.PowerFailDeviceHistoryAdapter;
import com.king.photo.model.PowerDevHistoryModel;
import com.king.photo.model.PowerNetHistoryModel;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PowerFailDeviceHistoryActivity extends Activity{
	
	TextView mCamName;
	TextView mCamId;
	ListView mListView,mNetListView;
	Button btnStatckHistroy,btnNetHistroy;
	LinearLayout linearLayoutBreakStack,linearLayoutBreakNet;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_powerfaildevice_history);
		
		findViewById(R.id.regulatory_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		initView();
		
		
		new getDevHistoryMsgForPowerDevAsync().execute(getIntent().getStringExtra("CamseqID"));
		new getDevNetHistoryMsgForPowerDevAsync().execute(getIntent().getStringExtra("CamseqID"));
		//new getDevHistoryMsgForPowerDevAsync().execute("861694034280543");
		
	}


	private void initView() {
		mCamName = (TextView) findViewById(R.id.cam_name);
		mCamId = (TextView) findViewById(R.id.cam_id);
		mListView  = (ListView) findViewById(R.id.listview);
		mNetListView = (ListView) findViewById(R.id.net_listview);
		linearLayoutBreakStack = (LinearLayout) findViewById(R.id.linearlayout_break_stack);
		linearLayoutBreakNet = (LinearLayout) findViewById(R.id.linearlayout_break_net);
		btnStatckHistroy = (Button) findViewById(R.id.btn_search_histroy);
		btnNetHistroy = (Button) findViewById(R.id.btn_search_net_histroy);
		btnStatckHistroy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				linearLayoutBreakStack.setVisibility(View.VISIBLE);
				linearLayoutBreakNet.setVisibility(View.GONE);
				btnStatckHistroy.setBackgroundResource(R.drawable.bg_button_style_xpfb_xl);
				btnStatckHistroy.setTextColor(Color.rgb(255, 255, 255));  
				btnNetHistroy.setBackgroundResource(R.drawable.bg_button_style_xpfb_xxl);
				btnNetHistroy.setTextColor(Color.rgb(0, 160, 233));  
			}
		});
		btnNetHistroy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				linearLayoutBreakStack.setVisibility(View.GONE);
				linearLayoutBreakNet.setVisibility(View.VISIBLE);
				btnStatckHistroy.setBackgroundResource(R.drawable.bg_button_style_xpfb_xxl);
				btnStatckHistroy.setTextColor(Color.rgb(0, 160, 233));  
				btnNetHistroy.setBackgroundResource(R.drawable.bg_button_style_xpfb_xl);
				btnNetHistroy.setTextColor(Color.rgb(255, 255, 255));  
			}
		});
	}
	
	
	class getDevNetHistoryMsgForPowerDevAsync extends AsyncTask<String, String, ArrayList<PowerNetHistoryModel>>{

		@Override
		protected ArrayList<PowerNetHistoryModel> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new NetworkApi().getPowerNetDevListForDevNum(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<PowerNetHistoryModel> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
			if (result == null) {
				System.out.println("获取值失败");
			
				return;
			}
		
			mCamName.setText(getIntent().getStringExtra("CamseqName"));
			mCamId.setText(getIntent().getStringExtra("CamseqID"));
			
			NetFailDeviceHistoryAdapter adapter = new NetFailDeviceHistoryAdapter(PowerFailDeviceHistoryActivity.this, result);

			mNetListView.setAdapter(adapter);
		}

		
	}
	class getDevHistoryMsgForPowerDevAsync extends AsyncTask<String, String, ArrayList<PowerDevHistoryModel>>{

		@Override
		protected ArrayList<PowerDevHistoryModel> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new NetworkApi().getPowerDevListForDevNum(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<PowerDevHistoryModel> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
			if (result == null) {
				System.out.println("获取值失败");
			
				return;
			}
		
			mCamName.setText(getIntent().getStringExtra("CamseqName"));
			mCamId.setText(getIntent().getStringExtra("CamseqID"));
			
			PowerFailDeviceHistoryAdapter adapter = new PowerFailDeviceHistoryAdapter(PowerFailDeviceHistoryActivity.this, result);

			mListView.setAdapter(adapter);
		}

		
	}
	
	
}
