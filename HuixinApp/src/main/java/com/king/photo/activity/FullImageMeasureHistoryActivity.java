package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.MyHorizontalScrollView;
import com.hc.android.huixin.view.MyHorizontalScrollView.CurrentImageChangeListener;
import com.hc.android.huixin.view.MyHorizontalScrollView.OnItemClickListener;
import com.king.photo.adapter.MeasureImgHorizontalScrollViewAdapter;
import com.king.photo.model.FullImageMeasureModel;
import com.king.photo.model.FullImageNodeModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import uk.co.senab.photoview.PhotoView;

public class FullImageMeasureHistoryActivity extends Activity {

	String RecordId;
	String Imgtype;
	String startDate;
	String endDate;
	PhotoView mImg;
	private MyHorizontalScrollView mHorizontalScrollView;
	private MeasureImgHorizontalScrollViewAdapter mAdapter;
	protected ImageLoader imageLoader;

	DisplayImageOptions options;

	BitmapDrawable bt;

	private static final int MSG_SUCCESS = 0;// 获取图片成功的标识
	private LinearLayout mGallery;
	private int[] mImgIds;
	private LayoutInflater mInflater;
	Button mBtnSupervision;
	Button mBtnMeasure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image_measure_second);

		RecordId = getIntent().getStringExtra("RecordId");
		Imgtype = getIntent().getStringExtra("Imgtype");

		mImg = (PhotoView) findViewById(R.id.position_image);
		mHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.id_horizontalScrollView);

		mGallery = (LinearLayout) findViewById(R.id.id_gallery);
		mBtnSupervision = (Button) findViewById(R.id.btn_supervision);
		mBtnMeasure = (Button) findViewById(R.id.btn_measure);
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon).showImageForEmptyUri(R.drawable.icon)
				.showImageOnFail(R.drawable.icon).cacheInMemory(true).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		new GetMeasureImgHistory().execute(RecordId, Imgtype);

		mBtnSupervision.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FullImageMeasureHistoryActivity.this, FillImageSupervisionActivity.class);
				intent.putExtra("RecordId", RecordId);// 是否有必要最新？
				intent.putExtra("DistanceRang", "0.5");
				intent.putExtra("startDate", "");
				intent.putExtra("endDate", "");
				startActivity(intent);
			}
		});

		mBtnMeasure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FullImageMeasureHistoryActivity.this, FillImageMeasureActivity.class);
				intent.putExtra("RecordId", RecordId);// 是否有必要最新？
				intent.putExtra("Imgtype", "2");
				startActivity(intent);
			}
		});
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

			mHandler.obtainMessage(MSG_SUCCESS, result).sendToTarget();

		}

	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SUCCESS:
				final ArrayList<FullImageMeasureModel> data = (ArrayList<FullImageMeasureModel>) msg.obj;
				imageLoader.displayImage(data.get(0).getPmgPath(), mImg);
				RecordId = data.get(0).getPmgPuzzleId();
				mAdapter = new MeasureImgHorizontalScrollViewAdapter(FullImageMeasureHistoryActivity.this, data);
				// 添加滚动回调
				// mHorizontalScrollView
				// .setCurrentImageChangeListener(new
				// CurrentImageChangeListener()
				// {
				// @Override
				// public void onCurrentImgChanged(int position,
				// View viewIndicator)
				// {
				// mImg.setImageBitmap(_imgDrawables.get(position));
				// viewIndicator.setBackgroundColor(Color
				// .parseColor("#AA024DA4"));
				// }
				// });
				// 添加点击回调
				mHorizontalScrollView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onClick(View view, int position) {
						RecordId = data.get(position).getPmgPuzzleId();
						imageLoader.displayImage(data.get(position).getPmgPath(), mImg);
						mHorizontalScrollView.setAllImgViewBackground();
						view.findViewById(R.id.id_index_gallery_item_image)
								.setBackgroundResource(R.drawable.bg_shadow_node_img);
					}
				});
				// 设置适配器
				mHorizontalScrollView.initDatas(mAdapter);
				mHorizontalScrollView.setImgViewBackground(0);

				break;

			default:
				break;
			}
		}

	};

}
