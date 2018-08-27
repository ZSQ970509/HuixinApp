package com.king.photo.adapter;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.DriverManageTypeItem;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.SendInVehicleDataBean;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.activity.VehicleDebitActivity;
import com.king.photo.activity.VehicleManagerActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 车辆状态数据适配器 时间：2016年4月14日09:17:21
 * 
 * @author xiao
 */
public class DriverManageAdapter extends BaseAdapter {
	Context context;
	ArrayList<DriverManageTypeItem> list;
	String title;
	Handler mHandler;

	public DriverManageAdapter(Context context, Handler handler, ArrayList<DriverManageTypeItem> list, String title) {
		this.context = context;
		this.list = list;
		this.title = title;
		this.mHandler = handler;
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
		TextView CarName;
		TextView CarStatus;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.driver_manage_item, null);
			viewHolder.CarName = (TextView) convertView.findViewById(R.id.text);
			viewHolder.CarName.setText("    " + list.get(position).CarNum);
			viewHolder.CarStatus = (TextView) convertView.findViewById(R.id.state);
			switch (list.get(position).Status) {

			case 0:
				viewHolder.CarStatus.setText("待借中");
				final String CarNum = viewHolder.CarName.getText().toString().trim();
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context, VehicleDebitActivity.class);
						intent.putExtra("Status", "0");
						intent.putExtra("CarNum", CarNum);
						intent.putExtra("title", title);
						context.startActivity(intent);
						((VehicleManagerActivity) context).finish();
					}
				});
				break;
			case 1:
				viewHolder.CarStatus.setText("已预订");
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						new GetCarDataAsyncTask().execute(list.get(position).DebitId);
					}
				});

				break;
			case 2:
				viewHolder.CarStatus.setText("待归还");
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new GetCarDataAsyncTask().execute(list.get(position).DebitId);
					}
				});
				break;
			case 4:
				viewHolder.CarStatus.setText("车辆借出待确认");
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ToastHelp.showToast(context, "车辆借出待确认,请通知管理员审核");
					}
				});
				break;
			case 5:
				viewHolder.CarStatus.setText("车辆归还待确认");
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ToastHelp.showToast(context, "车辆归还待确认,请通知管理员审核");
					}
				});
				break;
			default:
				break;
			}
			// convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

	private class GetCarDataAsyncTask extends AsyncTask<Integer, Void, SendInVehicleDataBean> {

		@Override
		protected SendInVehicleDataBean doInBackground(Integer... params) {
			return new NetworkApi().GetVehicleDebitData(params[0]);
		}

		@Override
		protected void onPostExecute(final SendInVehicleDataBean data) {
			if (data == null) {
				return;
			}

			Intent intent = new Intent(context, VehicleDebitActivity.class);
			intent.putExtra("Status", data.CarFormStauts);
			intent.putExtra("data", data);
			intent.putExtra("title", title);
			context.startActivity(intent);
			((VehicleManagerActivity) context).finish();

		}
	}
}
