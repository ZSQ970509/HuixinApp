package com.king.photo.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.hc.android.huixin.R;

import com.king.photo.activity.ActivityAdjustProject;

import com.king.photo.adapter.DriverManageAdapter.ViewHolder;

import android.R.layout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PagingBaseadapter extends BaseAdapter {

	Handler mHandler = new Handler();
	Context context;
	JSONArray josnArray;
	String mTitle;

	public PagingBaseadapter(Context context, String title, JSONArray josnArray) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.josnArray = josnArray;
		this.mTitle = title;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return josnArray.length();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return josnArray.getJSONObject(position).get("ProjName");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder {
		TextView mText;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_page, null);
			viewHolder.mText = (TextView) convertView.findViewById(R.id.text);
			viewHolder.mText.setGravity(Gravity.CENTER);
			try {
				viewHolder.mText.setText("　" + josnArray.getJSONObject(position).get("ProjName").toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			viewHolder.mText.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						Intent intent = new Intent(context, ActivityAdjustProject.class);
						intent.putExtra("ProjId", josnArray.getJSONObject(position).get("ProjId").toString());
						intent.putExtra("ProjName", josnArray.getJSONObject(position).get("ProjName").toString());
						intent.putExtra("title", mTitle);
						context.startActivity(intent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

}
