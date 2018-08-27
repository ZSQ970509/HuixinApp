package com.king.photo.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hc.android.huixin.R;
import com.king.photo.adapter.SettingBaseadapter2.ViewHolder;
import com.king.photo.model.PowerDevBindModel;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PowerFailDeviceBindAdapter extends BaseAdapter {

	Context Context;
	private static ArrayList<PowerDevBindModel> list;
	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> isSelected;

	public PowerFailDeviceBindAdapter(Context Context, ArrayList<PowerDevBindModel> list) {

		this.Context = Context;
		this.list = list;
		isSelected = new HashMap<Integer, Boolean>();
		// 初始化数据
		initDate();
	}

	// 初始化isSelected的数据
	public static void initDate() {
		getIsSelected().clear();
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(i, false);
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
		public CheckBox mCheckBox;
		public TextView mTextView;
		public TextView mTextLocation;
		public TextView mTextSN;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {

			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_powerfail_bind, null);
			viewHolder.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll);
			viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.item_cb);
			viewHolder.mTextView = (TextView) convertView.findViewById(R.id.item_content);
			viewHolder.mTextLocation = (TextView) convertView.findViewById(R.id.item_location);
			viewHolder.mTextSN = (TextView) convertView.findViewById(R.id.item_sn);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		String DevSeqID = list.get(position).getDevSeqID();

		if ("null".equals(DevSeqID) || "".equals(DevSeqID) || DevSeqID == null) {
			viewHolder.mTextSN.setText("");
			viewHolder.mTextSN.setVisibility(View.GONE);
		} else {
			viewHolder.mTextSN.setText(DevSeqID);

			viewHolder.mTextSN.setVisibility(View.VISIBLE);
		}
		
		String InstalPlace = list.get(position).getCamInstalPlace();

		if ("null".equals(InstalPlace) || "".equals(InstalPlace) || InstalPlace == null) {
			viewHolder.mTextLocation.setText("");
			viewHolder.mTextLocation.setVisibility(View.GONE);
		}else {
			viewHolder.mTextLocation.setText(InstalPlace);
			viewHolder.mTextLocation.setVisibility(View.VISIBLE);
		}
		
		
		
		
		viewHolder.mTextSN.setTextColor(Color.parseColor("#398de3"));
		viewHolder.mTextView.setText(list.get(position).getCamName());
		// 根据isSelected来设置checkbox的选中状况
		viewHolder.mCheckBox.setChecked(getIsSelected().get(position));
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		PowerFailDeviceBindAdapter.isSelected = isSelected;
	}
	public static int GetCamCount() {
		int camCount = 0;
		
		for (int i = 0; i < isSelected.size(); i++) {
			Log.e("isSelected", i+"");
			if (isSelected.get(i)) {
				camCount++;
			}
		}
		
		return camCount;
	}
	public static String GetCamIds() {
		String CamIds = "";
		
		for (int i = 0; i < isSelected.size(); i++) {
			Log.e("isSelected", i+"");
			if (isSelected.get(i)) {
				if (!TextUtils.isEmpty(CamIds)) {
					CamIds += ",";
				}
				Log.e("isSelected", list.get(i).getCamId()+"");
				CamIds += list.get(i).getCamId();
			}
		}
		Log.e("isSelected", CamIds+"");
		return CamIds;
	}

	public static JSONArray GetCamIdsAndSnList() {
		try {

			JSONArray arr = new JSONArray();
			for (int i = 0; i < isSelected.size(); i++) {
				if (isSelected.get(i)) {
					JSONObject item = new JSONObject();
					item.put("CamId", list.get(i).getCamId());
					item.put("DevPowerSn", list.get(i).getDevSeqID());
					arr.put(item);
				}
			}

			return arr;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
