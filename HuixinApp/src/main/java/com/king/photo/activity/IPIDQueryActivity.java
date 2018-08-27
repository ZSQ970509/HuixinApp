package com.king.photo.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.IpIdModel;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.adapter.IpidBaseadapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ip/id
 */
public class IPIDQueryActivity extends Activity {

	TextView mTextName;
	ListView mListView;

	TextView mName;
	TextView mAddress;
	TextView mPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ipid_query);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		final String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		mTextName = (TextView) findViewById(R.id.project_name);
		mListView = (ListView) findViewById(R.id.project_Content);

		mName = (TextView) findViewById(R.id.name);
		mAddress = (TextView) findViewById(R.id.address);
		mPhone = (TextView) findViewById(R.id.phone);

		findViewById(R.id.regulatory_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mTextName.setText(getIntent().getStringExtra("projectName"));

		new GetProjectContentAsyncTask().execute(getIntent().getStringExtra("projectId"));

	}

	private class GetProjectContentAsyncTask extends AsyncTask<String, String, ArrayList<IpIdModel>> {

		@Override
		protected ArrayList<IpIdModel> doInBackground(String... params) {
			// TODO Auto-generated method stub

			return new NetworkApi().getProjectContent(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<IpIdModel> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				ToastHelp.showCurrentToast(IPIDQueryActivity.this, "没有查询到数据");
				return;
			}
			String ProjSiteChaPerson = result.get(0).getProjSiteChaPerson();
			String ProjAddress = result.get(0).getProjAddress();
			String ProjSiteChaPersonPhone = result.get(0).getProjSiteChaPersonPhone();

			if (ProjSiteChaPerson == null || "null".equals(ProjSiteChaPerson)) {
				ProjSiteChaPerson = "空";
			}
			if (ProjAddress == null || "null".equals(ProjAddress)) {
				ProjAddress = "空";
			}

			if (ProjSiteChaPersonPhone == null || "null".equals(ProjSiteChaPersonPhone)) {
				ProjSiteChaPersonPhone = "空";

			}
			mName.setText(ProjSiteChaPerson);
			mAddress.setText(ProjAddress);
			mPhone.setText(ProjSiteChaPersonPhone);

			IpidBaseadapter adapter = new IpidBaseadapter(IPIDQueryActivity.this, result);

			mListView.setAdapter(adapter);
		}

	}

}
