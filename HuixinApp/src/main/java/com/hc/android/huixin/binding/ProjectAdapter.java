package com.hc.android.huixin.binding;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.BindingProjectItem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProjectAdapter extends BaseAdapter {
	private LayoutInflater listContainer; // 视图容器工厂
	private ArrayList<BindingProjectItem> mProjectList;
	private Context mContext;

	public ProjectAdapter(ArrayList<BindingProjectItem> projectList, Context c) {
		mProjectList = projectList;
		mContext = c;
		listContainer = LayoutInflater.from(mContext); // 创建视图容器工厂并设置上下文
	}

	@Override
	public int getCount() {
		return mProjectList.size();
	}

	@Override
	public Object getItem(int position) {
		return mProjectList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = listContainer.inflate(R.layout.bingding_projectitem, null);
		}
		TextView text = (TextView) convertView.findViewById(R.id.item_title);
		text.setText(mProjectList.get(position).ProjectName);
		text.setTextSize(18);
		text.setTextColor(Color.BLACK);
		return convertView;
	}

}
