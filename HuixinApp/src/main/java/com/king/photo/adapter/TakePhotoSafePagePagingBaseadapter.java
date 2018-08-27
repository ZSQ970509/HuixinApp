package com.king.photo.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.hc.android.huixin.R;

import com.king.photo.activity.ListviewpageDiologActivity;
import com.king.photo.activity.TakeDetailsActivity;
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

public class TakePhotoSafePagePagingBaseadapter extends BaseAdapter {

	Handler mHandler = new Handler();
	Context context;
	JSONArray josnArray;
	String mTitle;

	public TakePhotoSafePagePagingBaseadapter(Context context, String title, JSONArray josnArray) {
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
			return josnArray.getJSONObject(position).get("DirValue");
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
		TextView mExplan;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.takephotosafe_page_item, null);
			viewHolder.mText = (TextView) convertView.findViewById(R.id.dialog_text);
			viewHolder.mExplan = (TextView) convertView.findViewById(R.id.dialog_button);

			try {
				viewHolder.mText.setText("　" + josnArray.getJSONObject(position).get("DirValue").toString());

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			viewHolder.mExplan.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						Intent intent = new Intent(context, TakeDetailsActivity.class);
						intent.putExtra("text", josnArray.getJSONObject(position).get("DirValue").toString());
						intent.putExtra("detail", josnArray.getJSONObject(position).get("DirDesc").toString());
						intent.putExtra("title", mTitle);
						intent.putExtra("id", josnArray.getJSONObject(position).get("DirID").toString());
						((ListviewpageDiologActivity) context).startActivityForResult(intent, 0);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			viewHolder.mText.setGravity(Gravity.LEFT);
			viewHolder.mText.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						// Toast.makeText(context,josnArray.getJSONObject(position).get("DirValue").toString()
						// + "", Toast.LENGTH_SHORT).show();

						Intent intent = ((Activity) context).getIntent();
						intent.putExtra("NormRule", josnArray.getJSONObject(position).get("DirValue").toString());
						intent.putExtra("DirNormRules", josnArray.getJSONObject(position).get("DirID").toString());

						((Activity) context).setResult(0x125, intent);
						((Activity) context).finish();

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

}
