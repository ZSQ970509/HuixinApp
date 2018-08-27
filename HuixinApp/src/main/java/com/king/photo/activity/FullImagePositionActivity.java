package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.adapter.FullImagePositionBaseAdapter;
import com.king.photo.model.PositionImgModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.graphics.Bitmap;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class FullImagePositionActivity extends Activity {

	String CamId;
	String RecordId;
	String Lng;
	String Lat;
	String ImageTimes;
	GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image_position);

		CamId = getIntent().getStringExtra("CamId");
		RecordId = getIntent().getStringExtra("RecordId");
		Lng = getIntent().getStringExtra("Lng");
		Lat = getIntent().getStringExtra("Lat");
		Lat = getIntent().getStringExtra("Lat");
		ImageTimes= getIntent().getStringExtra("ImageTimes");
		mGridView = (GridView) findViewById(R.id.position_gridView);

		new GetPositionImg().execute(CamId, RecordId, Lng, Lat,ImageTimes);
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	class GetPositionImg extends AsyncTask<String, String, ArrayList<PositionImgModel>> {

		@Override
		protected ArrayList<PositionImgModel> doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new NetworkApi().GetPositionImgForFullImage(params[0], params[1], params[2], params[3], "",params[4]);
		}

		@Override
		protected void onPostExecute(final ArrayList<PositionImgModel> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				return;
			}

			FullImagePositionBaseAdapter adapter = new FullImagePositionBaseAdapter(FullImagePositionActivity.this,
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
