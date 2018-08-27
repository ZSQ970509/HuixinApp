package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.activity.FullImageNodeHistoryActivity.GetPositionImgHistory;
import com.king.photo.adapter.FullImagePositionBaseAdapter;
import com.king.photo.adapter.FullImageSupervisionBaseAdapter;
import com.king.photo.model.FullImageNodeModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

public class FillImageSupervisionActivity extends Activity {

	String RecordId;
	String DistanceRang;
	String startDate;
	String endDate;

	protected ImageLoader imageLoader;

	DisplayImageOptions options;
	private ArrayList<FullImageNodeModel> _imgDrawables = new ArrayList<FullImageNodeModel>();

	GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image_supervision);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		mGridView = (GridView) findViewById(R.id.supervision_gridView);
		RecordId = getIntent().getStringExtra("RecordId");
		DistanceRang = getIntent().getStringExtra("DistanceRang");
		startDate = getIntent().getStringExtra("startDate");
		endDate = getIntent().getStringExtra("endDate");

		new GetSupervisionImgHistory().execute(RecordId, DistanceRang, startDate, endDate);

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	class GetSupervisionImgHistory extends AsyncTask<String, String, ArrayList<FullImageNodeModel>> {

		@Override
		protected ArrayList<FullImageNodeModel> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new NetworkApi().GetNodeImgHistoryForFullImage(params[0], params[1], params[2], params[3], "");
		}

		@Override
		protected void onPostExecute(final ArrayList<FullImageNodeModel> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

			FullImageSupervisionBaseAdapter adapter = new FullImageSupervisionBaseAdapter(
					FillImageSupervisionActivity.this, mHandler, result);
			mGridView.setAdapter(adapter);

		}

	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
	};
}
