package com.king.photo.activity;

import java.util.ArrayList;
import java.util.List;

import com.hc.android.huixin.DriverTakePhotoActivity;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.ConstrucTypeItem;
import com.hc.android.huixin.network.ModuleItem;
import com.hc.android.huixin.view.LineGridView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 时间：2016年4月14日09:16:43
 * 
 * @author 
 */
public class DriverMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_main);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		findViewById(R.id.vehicle_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
//改版之前的控件已经隐藏 -可以删除-
//		findViewById(R.id.btn_driver_take_photo).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				String safeTitle = ((TextView) findViewById(R.id.title_take)).getText().toString();
//				jumpActivity(DriverTakePhotoActivity.class, safeTitle);
//			}
//		});
//
//		findViewById(R.id.btn_driver_vehicle_photo).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				String safeTitle = ((TextView) findViewById(R.id.title_vehicle)).getText().toString();
//				jumpActivity(VehicleManagerActivity.class, safeTitle);
//			}
//		});
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		 ArrayList<ConstrucTypeItem> data = new ArrayList<ConstrucTypeItem>();
		 ConstrucTypeItem item1 = new ConstrucTypeItem();
		 item1.id=801;
		 item1.text="驾驶员管理";
		 data.add(item1);
		
		 ConstrucTypeItem item2 = new ConstrucTypeItem();
		 item2.id=802;
		 item2.text="车辆借车登记";
		 data.add(item2);


		LineGridView grid = (LineGridView) findViewById(R.id.moduleGrid);
		ArrayList<ModuleItem> moduleList = new ArrayList<ModuleItem>();
		String[] nameData = new String[data.size()];
		for (int i = 0; i < nameData.length; i++) {
			nameData[i] = data.get(i).text;

			ModuleItem item = new ModuleItem();
			switch (data.get(i).id) {
			case 801://驾驶员专用
				item.id = data.get(i).id;
				item.name = data.get(i).text;
				item.imageId = R.drawable.icon_special_driver;
				item.jumpActivity = DriverTakePhotoActivity.class;
				moduleList.add(item);
				break;
			case 802://车辆借车登记
				item.id = data.get(i).id;
				item.name = data.get(i).text;
				item.imageId = R.drawable.icon_car_registration;
				item.jumpActivity = VehicleManagerActivity.class;
				moduleList.add(item);
				break;
			default:
			
				break;
			}
		}

		grid.setAdapter(new MyAdapter(moduleList));
	}


	private class MyAdapter extends BaseAdapter {

		private ArrayList<ModuleItem> moduleList = new ArrayList<ModuleItem>();

		public MyAdapter(List<ModuleItem> list) {
			if (list != null && list.size() > 0) {
				moduleList.clear();
				moduleList.addAll(list);
			}
		}

		@Override
		public int getCount() {
			return moduleList.size();
		}

		@Override
		public Object getItem(int position) {
			return moduleList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_item, null);
			final ModuleItem item = moduleList.get(position);
			((ImageView) itemView.findViewById(R.id.module_img)).setBackgroundResource(item.imageId);
			((TextView) itemView.findViewById(R.id.module_text)).setText(item.name);
			itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (item.jumpActivity == null) {

						return;
					}
					jumpActivity(item.jumpActivity, item.name);
				}
			});
			return itemView;
		}

	}

	private void jumpActivity(Class<?> cls, String title) {
		Intent intent = new Intent(this, cls);
		intent.putExtra("title", title);
		startActivity(intent);
	}

}
