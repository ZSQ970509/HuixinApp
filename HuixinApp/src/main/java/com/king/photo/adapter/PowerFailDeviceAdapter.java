package com.king.photo.adapter;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.king.photo.adapter.TowerCraneManagerBaseadapter.ViewHolder;
import com.king.photo.model.PowerDevModel;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PowerFailDeviceAdapter extends BaseAdapter {

	Context Context;
	ArrayList<PowerDevModel> list;

	public PowerFailDeviceAdapter(Context Context, ArrayList<PowerDevModel> list) {

		this.Context = Context;
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
		TextView mCamName;
		TextView mCamStatus;
		TextView mPowerFailStatus;
		TextView mNetFailStatus;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_powerfail_status, null);
			viewHolder.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll);
			viewHolder.mCamName = (TextView) convertView.findViewById(R.id.item_position);
			viewHolder.mCamStatus = (TextView) convertView.findViewById(R.id.item_status);
			viewHolder.mPowerFailStatus = (TextView) convertView.findViewById(R.id.item_power_fail_status);
			viewHolder.mNetFailStatus = (TextView) convertView.findViewById(R.id.item_power_net_status);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
}
		
	
		viewHolder.mCamName.setText(list.get(position).getCamName()+"");
		//摄像头CamFlowState  1 监控中  0 维修中 -1 未开通
		//掉电 DevStatus    0断电 1 不断电

		if (list.get(position).getCamFlowState()==0) {
			viewHolder.mCamStatus.setText("故障维护");
			viewHolder.mCamStatus.setTextColor(Color.parseColor("#fa9d4c"));
		}else if(list.get(position).getCamFlowState()==1) {
			viewHolder.mCamStatus.setText("监控中");
			viewHolder.mCamStatus.setTextColor(Color.parseColor("#398de3"));
		}else if(list.get(position).getCamFlowState()==-1) {
			viewHolder.mCamStatus.setText("未开通");
			viewHolder.mCamStatus.setTextColor(Color.parseColor("#dc5562"));
		}else  {
			viewHolder.mCamStatus.setText("已拆机");
			viewHolder.mCamStatus.setTextColor(Color.parseColor("#dc5562"));
		}
		
		if (list.get(position).getDevStatus()==0) {
			viewHolder.mPowerFailStatus.setText("断    电");
			viewHolder.mPowerFailStatus.setTextColor(Color.parseColor("#dc5562"));
		}else {
			viewHolder.mPowerFailStatus.setText("通     电");
			viewHolder.mPowerFailStatus.setTextColor(Color.parseColor("#398de3"));
		}
		if (list.get(position).getDevNetstatus()==0) {
			viewHolder.mNetFailStatus.setText("断    网");
			viewHolder.mNetFailStatus.setTextColor(Color.parseColor("#dc5562"));
		}else if(list.get(position).getDevNetstatus()==1){
			viewHolder.mNetFailStatus.setText("通    网");
			viewHolder.mNetFailStatus.setTextColor(Color.parseColor("#398de3"));
		}else{
			viewHolder.mNetFailStatus.setText("未开通");
			viewHolder.mNetFailStatus.setTextColor(Color.parseColor("#dc5562"));
		}
		return convertView;
	}

}
