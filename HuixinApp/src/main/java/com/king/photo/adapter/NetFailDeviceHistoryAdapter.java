package com.king.photo.adapter;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.king.photo.adapter.PowerFailDeviceBindAdapter.ViewHolder;
import com.king.photo.model.PowerDevHistoryModel;
import com.king.photo.model.PowerNetHistoryModel;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NetFailDeviceHistoryAdapter extends BaseAdapter {

	Context Context;
	private static ArrayList<Model> list;

	public NetFailDeviceHistoryAdapter(Context Context, ArrayList<PowerNetHistoryModel> data) {
		this.Context = Context;
		
		
		list = new ArrayList<Model>();
		int i = 1;
		for (PowerNetHistoryModel powerNetHistoryModel : data) {

			if (powerNetHistoryModel.getNetOnTime()!=null) {
				Model model =new Model();
				model.setNum(i+++"");
				model.setRecord("通网:"+powerNetHistoryModel.getNetOnTime());
				model.setColor("#398de3");
				list.add(model);
			}

			if (powerNetHistoryModel.getNetDownTime()!=null) {
				Model model =new Model();
				model.setNum(i+++"");
				model.setRecord("断网:"+powerNetHistoryModel.getNetDownTime());
				model.setColor("#dc5562");
				list.add(model);
			}

		}
	

		
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
		public LinearLayout mLinearLayout;
		public TextView mTextNum;
		public TextView mTextRecord;
	}

	
	private class Model{
		
		private String num;
		private String record;
		private String color;
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getRecord() {
			return record;
		}
		public void setRecord(String record) {
			this.record = record;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		
		
	}
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder viewHolder = null;
		if (convertView == null) {
			
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_powerfail_history, null);
			viewHolder.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll);
			viewHolder.mTextNum = (TextView) convertView.findViewById(R.id.item_num);
			viewHolder.mTextRecord = (TextView) convertView.findViewById(R.id.item_record);

			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		
		viewHolder.mTextNum.setText(list.get(position).getNum());
		viewHolder.mTextRecord.setText(list.get(position).getRecord());
		viewHolder.mTextRecord.setTextColor(Color.parseColor(list.get(position).getColor()));
		return convertView;
	}

}
