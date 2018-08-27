package com.king.photo.adapter;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.activity.FullImageMainActivity;
import com.king.photo.model.FullImageDevModel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FullImageDevListAdapter extends BaseAdapter {

	Context context;
	ArrayList<FullImageDevModel> list;

	public FullImageDevListAdapter(Context context, ArrayList<FullImageDevModel> data) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = data;
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
		ImageView mImageView;
		TextView mContent;
		TextView mExpant;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.fullimage_item, null);
			viewHolder = new ViewHolder();

			// viewHolder.mImageView = (ImageView)
			// convertView.findViewById(R.id.dialog_iamge);
			viewHolder.mContent = (TextView) convertView.findViewById(R.id.dialog_text);
			viewHolder.mExpant = (TextView) convertView.findViewById(R.id.dialog_button);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.mContent.setText(list.get(position).getCamName());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(context, FullImageMainActivity.class);
				intent.putExtra("CamId", list.get(position).getCamId());
				context.startActivity(intent);
				ToastHelp.showCurrentToast(context, list.get(position).getCamId());



			}
		});

		return convertView;
	}

}
