package com.hc.android.huixin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hc.android.huixin.util.DeviceUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.graphics.Bitmap;

/**
 * 历史照片
 */
public class PhotoActivity extends Activity {

	private GridView mGridView;
	private Gallery mGallery;
	private GridAdapter mGridAapter;
	private GalleryAdapter mGalleryAapter;
	private DisplayImageOptions options;
	private List<String> imageData = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		initView();

		options = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		new LoadPhotoTask().execute();
	}

	private void initView() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		mGridView = (GridView) findViewById(R.id.grid_view);
		mGallery = (Gallery) findViewById(R.id.gallery_view);
		mGallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				mGallery.setVisibility(View.GONE);
				mGridView.setVisibility(View.VISIBLE);
			}
		});

		findViewById(R.id.photo_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				keyBack();
			}
		});
	}

	private void keyBack() {
		if (mGallery.getVisibility() == View.VISIBLE) {
			mGallery.setVisibility(View.GONE);
			mGridView.setVisibility(View.VISIBLE);
		} else {
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			keyBack();
		}
		return false;
	}

	class LoadPhotoTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			String dirName = Environment.getExternalStorageDirectory() + "/huixin/";
			File file = new File(dirName);
			if (file.exists()) {
				String[] paths = file.list();
				for (String path : paths) {
					imageData.add(dirName + path);
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mGridAapter = new GridAdapter();
			mGridView.setAdapter(mGridAapter);
			mGalleryAapter = new GalleryAdapter();
			mGallery.setAdapter(mGalleryAapter);
		}

	}

	class GridAdapter extends BaseAdapter {

		class ViewHolder {
			ImageView image;
			Button btnDelete;
		}

		@Override
		public int getCount() {
			return imageData.size();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_grid_item, null);
				int width = DeviceUtil.getDeviceWidth(PhotoActivity.this) / 3;
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.ItemImage);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width);
				holder.image.setLayoutParams(params);
				holder.btnDelete = (Button) convertView.findViewById(R.id.btn_delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ImageLoader.getInstance().displayImage("file://" + imageData.get(position), holder.image, options);
			holder.image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mGallery.setVisibility(View.VISIBLE);
					mGallery.setSelection(position);
					mGridView.setVisibility(View.GONE);
				}
			});
			holder.btnDelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String filePath = imageData.get(position);
					imageData.remove(position);
					mGridAapter.notifyDataSetChanged();
					mGalleryAapter.notifyDataSetChanged();
					new File(filePath).delete();
				}
			});
			return convertView;
		}

		@Override
		public Object getItem(int position) {
			return imageData.get(position);
		}

	}

	class GalleryAdapter extends BaseAdapter {

		class ViewHolder {
			ImageView image;
		}

		@Override
		public int getCount() {
			return imageData.size();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_grid_item, null);
				convertView
						.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.ItemImage);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ImageLoader.getInstance().displayImage("file://" + imageData.get(position), holder.image, options);
			return convertView;
		}

		@Override
		public Object getItem(int position) {
			return imageData.get(position);
		}

	}

}
