package com.king.photo.activity;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.NetworkApi;
import com.king.photo.model.FullImageMainModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.onesafe.util.BaseImageDecoder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class FullImageMainActivity extends Activity {

	PhotoView mImg;
	PhotoView mPhotoImg;
	LinearLayout mLLimg;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;

	FullImageMainModel data;
	String ImageUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_image_main);

		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		mImg = (PhotoView) findViewById(R.id.full_image);
		mLLimg = (LinearLayout) findViewById(R.id.Ll_img);
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		this.options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon)
				.showImageForEmptyUri(R.drawable.icon).showImageOnFail(R.drawable.icon).cacheInMemory(true)
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		new GetFullImageForCamId().execute(getIntent().getStringExtra("CamId"));

		mImg.setOnPhotoTapListener(new OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View arg0, final float arg1, final float arg2) {
				// TODO Auto-generated method stub
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// mImg.setDrawingCacheEnabled(false);
						// mImg.setDrawingCacheEnabled(true);
						// mPhotoImg.setImageBitmap(mImg.getDrawingCache());

						float lng = arg1;
						float lat = arg2;

						if (lng > 0.5 && lat < 0.5) {
							lng = 1 - arg1;
							lat = arg2;
						} else if (lng < 0.5 && lat < 0.5) {
							lng = arg1;
							lat = 1 - arg2;
						} else if (lng > 0.5 && lat > 0.5) {
							lng = arg1;
							lat = 1 - arg2;
						} else if (lng < 0.5 && lat > 0.5) {
							lng = 1 - arg1;
							lat = arg2;
						}

						ImageSize imageSize = new BaseImageDecoder(true).getOriginSize(ImageUrl);
						imageSize.getHeight();
						imageSize.getWidth();

						Intent intent = new Intent(FullImageMainActivity.this, FullImagePositionActivity.class);
						intent.putExtra("CamId", data.getCamId());
						intent.putExtra("RecordId", data.getRecordId());
						intent.putExtra("Lng", lng * imageSize.getWidth() + "");
						intent.putExtra("Lat", lat * imageSize.getHeight() + "");
						intent.putExtra("ImageTimes", data.getImageTimes() + "");
						startActivity(intent);
					}
				});
			}
		});

	}

	private Builder bitmapConfig(Config rgb565) {
		// TODO Auto-generated method stub
		return null;
	}

	class GetFullImageForCamId extends AsyncTask<String, String, FullImageMainModel> {

		@Override
		protected FullImageMainModel doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new NetworkApi().getProjectForFullImage(params[0]);
		}

		@Override
		protected void onPostExecute(FullImageMainModel result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result == null) {
					return;
			}
			data = result;
		
			imageLoader.displayImage(result.getPuzzleImg(), mImg, options, new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					// TODO Auto-generated method stub
					mImg.setScale((1.0f * arg1.getHeight()) / arg1.getWidth());
				}

				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
					// TODO Auto-generated method stub

					// mImg.setScale(arg1.getHeight()/arg2.getHeight());
			
					ImageUrl = arg0;
					mImg.setMaxScale(500);

					mImg.setScale((1.0f * arg1.getHeight()) / arg1.getWidth());
					mImg.setPhotoViewRotation(270);

					// loadedImage.getWeight() will not return the original
					// dimensions of the image if it has been scaled down

				}

				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					// TODO Auto-generated method stub

				}
			});

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
