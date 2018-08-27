package com.king.photo.adapter;

import java.util.ArrayList;

import com.hc.android.huixin.AppUpdateActivity;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.SettingItem;
import com.king.photo.activity.AccountManagerActivity;
import com.king.photo.adapter.PagingBaseadapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingBaseadapter1 extends BaseAdapter {
	Handler mHandler = new Handler();
	Context context;
	ArrayList<SettingItem> list;

	public SettingBaseadapter1(Context context, ArrayList<SettingItem> list) {
		this.context = context;
		this.list = list;
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
		LinearLayout mLinearLayout;
		TextView mText;
		ImageView mImageView;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_setting_1, null);
			viewHolder.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll);
			viewHolder.mText = (TextView) convertView.findViewById(R.id.text);
			viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.imageView);

			viewHolder.mText.setText(list.get(position).name + "");
			viewHolder.mImageView.setBackgroundResource(list.get(position).imageId);

			viewHolder.mLinearLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					switch (list.get(position).id) {
					case 10:

						jumpActivity(context, AccountManagerActivity.class, list.get(position).name);
						break;

					default:
						break;
					}
				}
			});
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		return convertView;

	}

	private void jumpActivity(Context context, Class<?> cls, String title) {
		Intent intent = new Intent(context, cls);
		intent.putExtra("title", title);
		context.startActivity(intent);
	}
}
