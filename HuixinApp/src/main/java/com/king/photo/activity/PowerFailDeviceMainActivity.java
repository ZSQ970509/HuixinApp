package com.king.photo.activity;

import java.io.File;
import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.king.photo.adapter.PowerFailDeviceAdapter;
import com.king.photo.model.PowerDevModel;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class PowerFailDeviceMainActivity extends Activity {

	
	PowerFailDeviceAdapter adapter =null;
	ListView mListView ;
	TextView mTextProjectName;
	TextView mTextName;
	TextView mTextAddress;
	TextView mTextPhone;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_powerfaildevice_main);
		
		findViewById(R.id.regulatory_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mListView = (ListView) findViewById(R.id.project_Content);
	

		mTextProjectName = (TextView) findViewById(R.id.project_name);
		mTextName  = (TextView) findViewById(R.id.name);
		mTextAddress = (TextView) findViewById(R.id.address);
		mTextPhone =  (TextView) findViewById(R.id.phone);
		String projectId = getIntent().getStringExtra("projectId");
		new getDevMsgForPowerDevAsync().execute(projectId);
	}

	class getDevMsgForPowerDevAsync extends AsyncTask<String, String, ArrayList<PowerDevModel>> {

		@Override
		protected ArrayList<PowerDevModel> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new NetworkApi().getDevMsgForPowerDev(params[0]);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(final ArrayList<PowerDevModel> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result == null) {
				System.out.println("获取值失败");
				return;
			}

			 mTextProjectName.setText(result.get(0).getProjName());
		 	 mTextName.setText("姓名:"+result.get(0).getProjChargePerson());
			 mTextAddress.setText("地址:"+result.get(0).getProjAddress());
			 mTextPhone.setText("电话:"+result.get(0).getProjChargePersonPhone());
			 adapter = new PowerFailDeviceAdapter(PowerFailDeviceMainActivity.this, result);
			 mListView.setAdapter(adapter);
			 mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(PowerFailDeviceMainActivity.this,PowerFailDeviceHistoryActivity.class);
					intent.putExtra("CamseqName", result.get(arg2).getCamName());
					intent.putExtra("CamseqID", result.get(arg2).getCamSeqID());
					startActivity(intent);
				
				}
			});
			 		
		}

	}

}
