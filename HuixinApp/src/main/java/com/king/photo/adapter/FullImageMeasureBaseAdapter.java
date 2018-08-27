package com.king.photo.adapter;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.activity.FullImageMeasureHistoryActivity;
import com.king.photo.activity.FullImageNodeHistoryActivity;
import com.king.photo.activity.FullImagePositionActivity;
import com.king.photo.adapter.PagingBaseadapter.ViewHolder;
import com.king.photo.model.FullImageMeasureModel;
import com.king.photo.model.FullImageNodeModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;

public class FullImageMeasureBaseAdapter extends BaseAdapter {

	protected ImageLoader imageLoader;
	DisplayImageOptions options;

	Context Context;
	Handler Handler;
	ArrayList<FullImageMeasureModel> list;

	public FullImageMeasureBaseAdapter(Context Context, Handler Handler, ArrayList<FullImageMeasureModel> result) {
		// TODO Auto-generated constructor stub
		this.Context = Context;
		this.Handler = Handler;
		this.list = result;

		imageLoader = ImageLoader.getInstance();

		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon).showImageForEmptyUri(R.drawable.icon)
				.showImageOnFail(R.drawable.icon).cacheInMemory(true).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder {
		PhotoView mImg;
		TextView mTxt;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			convertView = LayoutInflater.from(Context).inflate(R.layout.plugin_gridview_position_imageview, parent,
					false);
			viewHolder.mImg = (PhotoView) convertView.findViewById(R.id.image_view);
			viewHolder.mTxt = (TextView) convertView.findViewById(R.id.text_view);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(list.get(position).getPmgPath(), viewHolder.mImg, options, new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				((PhotoView) arg1).setMaxScale(500);
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub

			}
		});

		viewHolder.mImg.setOnPhotoTapListener(new OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Context, FullImageMeasureHistoryActivity.class);
				intent.putExtra("RecordId", list.get(position).getPmgPuzzleId());
				intent.putExtra("Imgtype", "2");
				Context.startActivity(intent);
			}
		});
		viewHolder.mTxt.setText(list.get(position).getPmgAddtime().replace(" ", "\r\n  "));

		return convertView;
	}

}
