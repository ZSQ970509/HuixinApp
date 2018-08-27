package com.hc.android.huixin.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.CameraItem;

import android.content.Context;
import android.graphics.Color;
import android.provider.Telephony.Mms;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChoiceDialogAdapt extends BaseAdapter {
	private Context context;
	private List<CameraItem> mMsgs = new ArrayList<CameraItem>();
	private int mSelectRadioIndex = -1;// 记录被选中按钮位置

	public ChoiceDialogAdapt(Context context, List<CameraItem> msgs) {
		super();
		this.context = context;
		mMsgs = msgs;
	}

	@Override
	public int getCount() {
		return mMsgs.size();
	}

	@Override
	public Object getItem(int position) {
		return mMsgs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public int getmSelectRadioIndex() {
		return mSelectRadioIndex;
	}

	public void setmSelectRadioIndex(int mSelectRadioIndex) {
		this.mSelectRadioIndex = mSelectRadioIndex;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_choice, null);
			viewHolder.mLL = (RelativeLayout) convertView.findViewById(R.id.test_rl);
			viewHolder.mMgsTv = (TextView) convertView.findViewById(R.id.test);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final RadioButton radio = (RadioButton) convertView.findViewById(R.id.test_rb);
		viewHolder.mRdBtn = radio;
		viewHolder.mMgsTv.setText(mMsgs.get(position).CamId+"_"+mMsgs.get(position).NewCamNam);
		viewHolder.mLL.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mSelectRadioIndex = position;
				ChoiceDialogAdapt.this.notifyDataSetChanged();
			}
		});
		viewHolder.mRdBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mSelectRadioIndex = position;
				ChoiceDialogAdapt.this.notifyDataSetChanged();
			}
		});
		if (position == mSelectRadioIndex) {
			viewHolder.mRdBtn.setChecked(true);
		} else {
			viewHolder.mRdBtn.setChecked(false);
		}
		if(TextUtils.isEmpty(mMsgs.get(position).CamDateInstall)){
			viewHolder.mLL.setBackgroundColor(Color.parseColor("#ffffff"));
		}else{
			viewHolder.mLL.setBackgroundColor(Color.parseColor("#d2d2d2"));
		}
		return convertView;
	}

	public class ViewHolder {
		RelativeLayout mLL;
		TextView mMgsTv;
		RadioButton mRdBtn;
	}
}
