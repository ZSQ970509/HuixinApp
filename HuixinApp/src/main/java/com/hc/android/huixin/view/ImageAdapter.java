package com.hc.android.huixin.view;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hc.android.huixin.MainActivity;
import com.hc.android.huixin.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private List<String> imageUrls = new ArrayList<String>();
	private Context context;
	private ImageAdapter self;
	private ImageView imageView;
	public static Integer[] imgs = { R.drawable.about_1, R.drawable.about_2, R.drawable.about_3 };

	public HashMap<String, Bitmap> imagesCache = new HashMap<String, Bitmap>();

	public ImageAdapter(Context context) {
		this.context = context;
		this.self = this;
	}

	public ImageAdapter(List<String> imageUrls, Context context) {
		this.imageUrls = imageUrls;
		this.context = context;
		this.self = this;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		return imageUrls.get(position % imgs.length);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
				case 0: {
					self.notifyDataSetChanged();
				}
					break;
				}
				super.handleMessage(msg);
			} catch (Exception e) {
			}
		}
	};

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Bitmap image = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_shuffling_image, null);
			Gallery.LayoutParams params = new Gallery.LayoutParams(Gallery.LayoutParams.WRAP_CONTENT,
					Gallery.LayoutParams.WRAP_CONTENT);
			convertView.setLayoutParams(params);
			// image = ((ImageActivity) context).imagesCache.get(imageUrls
			// .get(position % imageUrls.size())); // 从缓存中读取图片
			if (image == null) { // 当缓存中没有要使用的图片时，先显示默认的图片
				image = imagesCache.get("background_non_load");
				// 异步加载图片
				// LoadImageTask task = new LoadImageTask(convertView);
				// task.execute(imageUrls.get(position % imageUrls.size()));
			}
			convertView.setTag(imgs);

		} else {
			image = (Bitmap) convertView.getTag();
		}
		imageView = (ImageView) convertView.findViewById(R.id.gallery_image);
		imageView.setImageResource(imgs[position % imgs.length]);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		((MainActivity) context).changePointView(position % imgs.length);
		return convertView;

	}

	// 加载图片的异步任务
	class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
		private View resultView;

		LoadImageTask(View resultView) {
			this.resultView = resultView;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			resultView.setTag(bitmap);
		}

		// 从网上下载图片
		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap image = null;
			try {
				URL url = new URL(params[0]);
				URLConnection conn = url.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
				image = BitmapFactory.decodeStream(is);
				is.close();
				imagesCache.put(params[0], image); // 把下载好的图片保存到缓存中
				Message m = new Message();
				m.what = 0;
				mHandler.sendMessage(m);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return image;
		}
	}
}
