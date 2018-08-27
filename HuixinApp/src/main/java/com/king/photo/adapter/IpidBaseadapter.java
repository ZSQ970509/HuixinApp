package com.king.photo.adapter;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.IpIdModel;
import com.king.photo.adapter.SettingBaseadapter1.ViewHolder;
import com.onesafe.util.CPResourceUtil;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IpidBaseadapter extends BaseAdapter {
	Handler mHandler = new Handler();
	Context context;
	ArrayList<IpIdModel> list;

	public IpidBaseadapter(Context context, ArrayList<IpIdModel> list) {
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
		TextView mConcode;
		TextView mIPID;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;

		viewHolder = new ViewHolder();
		// LayoutInflater inflater = (LayoutInflater)
		// context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// convertView = inflater.inflate(R.layout.item_ipid, null);

		// viewHolder.mConcode = (TextView)
		// convertView.findViewById(R.id.concode);
		// viewHolder.mIPID = (TextView) convertView.findViewById(R.id.ipid);
		// viewHolder.mConcode.setText("合同号：" + list.get(position).getConCode()
		// + "");
		// viewHolder.mIPID.setText("" + list.get(position).getConSet_IpId() +
		// "");

		LinearLayout layout = new LinearLayout(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// layout.setLayoutParams(layoutParams);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(R.drawable.textview_style);
		TextView tvName = new TextView(context);
		LinearLayout.LayoutParams tvNameParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		tvNameParams.topMargin = 50;
		tvNameParams.leftMargin = 50;
		tvName.setLayoutParams(tvNameParams);
		String ConCode = list.get(position).getConCode();
		if (ConCode == null || "null".equals(ConCode) || "".equals(ConCode)) {
			ConCode = "空";
		}
		tvName.setText(" 合同号：" + ConCode + "");
		tvName.setTextSize(14);
		Drawable drawable_file = context.getResources().getDrawable(R.drawable.ipid_file);
		drawable_file.setBounds(0, 0, 50, 62);

		tvName.setCompoundDrawables(drawable_file, null, null, null);
		layout.addView(tvName);

		String ConSet_IpId = list.get(position).getConSet_IpId();
		if (ConSet_IpId == null || "null".equals(ConSet_IpId) || "".equals(ConSet_IpId)) {
			ConSet_IpId = "空";
		}
		String[] IPIDArray = ConSet_IpId.split("\n");
		for (int i = 0; i < IPIDArray.length; i++) {
			TextView tvID = new TextView(context);
			LinearLayout.LayoutParams tvIDParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			tvIDParams.topMargin = 20;
			tvIDParams.leftMargin = 100;
			if (i + 1 == IPIDArray.length) {
				tvIDParams.bottomMargin = 60;
			}
			tvID.setLayoutParams(tvIDParams);
			// tvID.setText(" "+IPIDArray[i].replace("：", "").replace("ID",
			// "ID:").replace("号码", "\n Num:")+ "");
			tvID.setText(" " + IPIDArray[i] + "");
			tvID.setTextSize(14);
			Drawable drawable = context.getResources().getDrawable(R.drawable.ipid_id);
			drawable.setBounds(0, -10, 30, 30);
			tvID.setCompoundDrawables(drawable, null, null, null);

			layout.addView(tvID);

		}

		return layout;

	}

}
