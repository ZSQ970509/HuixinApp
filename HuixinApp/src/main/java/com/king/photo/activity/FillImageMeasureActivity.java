package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.king.photo.adapter.FullImageMeasureBaseAdapter;
import com.king.photo.adapter.FullImageSupervisionBaseAdapter;
import com.king.photo.model.FullImageMeasureModel;
import com.king.photo.model.FullImageNodeModel;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

public class FillImageMeasureActivity extends Activity {

	String RecordId;
	String Imgtype;
	GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image_measure);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mGridView = (GridView) findViewById(R.id.measure_gridView);

		RecordId = getIntent().getStringExtra("RecordId");
		Imgtype = getIntent().getStringExtra("Imgtype");

		new GetMeasureImgHistory().execute(RecordId, Imgtype);

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	class GetMeasureImgHistory extends AsyncTask<String, String, ArrayList<FullImageMeasureModel>> {

		@Override
		protected ArrayList<FullImageMeasureModel> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new NetworkApi().GetMeasureImgForFullImage(params[0], params[1], "");
		}

		@Override
		protected void onPostExecute(final ArrayList<FullImageMeasureModel> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

			FullImageMeasureBaseAdapter adapter = new FullImageMeasureBaseAdapter(FillImageMeasureActivity.this,
					mHandler, result);
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
