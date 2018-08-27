package com.hc.android.huixin.view;

import java.util.ArrayList;

import com.hc.android.huixin.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Spinner自定义数据提供器类 2016年3月25日11:02:56
 * 
 */
public class SpinnerListviewAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> list;

	public SpinnerListviewAdapter(Context context, ArrayList<String> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public class ViewHolder {
		public TextView textView;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (arg1 == null && list.size() != 0) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			arg1 = inflater.inflate(R.layout.spinneritem, null);
			viewHolder.textView = (TextView) arg1.findViewById(R.id.itemText);
			arg1.setTag(viewHolder);
		} else
			viewHolder = (ViewHolder) arg1.getTag();
		viewHolder.textView.setText(list.get(arg0));
		return arg1;
	}

}
