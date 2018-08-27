package com.king.photo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hc.android.huixin.R;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.model.FullImageNodeModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NodeImgHorizontalScrollViewAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<FullImageNodeModel> mDatas;

	protected ImageLoader imageLoader;
	DisplayImageOptions options;

	public NodeImgHorizontalScrollViewAdapter(Context context, ArrayList<FullImageNodeModel> mDatas) {
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;


		imageLoader = ImageLoader.getInstance();

		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon).showImageForEmptyUri(R.drawable.icon)
				.showImageOnFail(R.drawable.icon).cacheInMemory(true).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

	}

	public int getCount() {
		return mDatas.size();
	}

	public Object getItem(int position) {
		return mDatas.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_index_gallery, parent, false);
			viewHolder.mImg = (ImageView) convertView.findViewById(R.id.id_index_gallery_item_image);
			viewHolder.mText = (TextView) convertView.findViewById(R.id.id_index_gallery_item_text);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.mText.setText(mDatas.get(position).getAddtime().replace(" ", "\r\n  "));

		imageLoader.displayImage(mDatas.get(position).getPath(), viewHolder.mImg, new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				// TODO Auto-generated method stub
				String str = null;
				switch (failReason.getType()) {
				case IO_ERROR: // 文件I/O错误
					str = "Input/Output error";
					break;
				case DECODING_ERROR: // 解码错误
					str = "Image can't be decoded";
					break;
				case NETWORK_DENIED: // 网络延迟
					str = "Downloads are denied";
					break;
				case OUT_OF_MEMORY: // 内存不足
					str = "Out Of Memory error";
					break;
				case UNKNOWN: // 原因不明
					str = "Unknown error";
					break;
				}

			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
			}

		});

		return convertView;
	}

	private class ViewHolder {
		ImageView mImg;
		TextView mText;
	}

}
