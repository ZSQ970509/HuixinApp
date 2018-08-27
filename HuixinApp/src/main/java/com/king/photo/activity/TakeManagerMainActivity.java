package com.king.photo.activity;

import java.util.ArrayList;
import java.util.List;

import com.hc.android.huixin.R;
import com.hc.android.huixin.TakePhoto2Activity;
import com.hc.android.huixin.network.ConstrucTypeItem;
import com.hc.android.huixin.network.ModuleItem;
import com.hc.android.huixin.view.LineGridView;
import com.king.photo.activity.GalleryActivity;
import com.king.photo.activity.PhotoMainActivity;

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

public class TakeManagerMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_main);
		initView();
	}

	private void initView() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		findViewById(R.id.regulatory_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
//改版之前的控件已经隐藏 -可以删除-
//改版后没有添加人脸使用率统计模块-FaceCountActivity.class
//		// findViewById(R.id.btn_take_safe).setVisibility(8);
//		findViewById(R.id.btn_take_safe).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				String safeTitle = ((TextView) findViewById(R.id.titleSafe)).getText().toString();
//				jumpActivity(EnvironmentalMonitoringActivity.class, safeTitle);
//			}
//		});
//		// findViewById(R.id.btn_take_photo).setVisibility(8);
//		findViewById(R.id.btn_take_photo).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				String safeTitle = ((TextView) findViewById(R.id.titleQuality)).getText().toString();
//				jumpActivity(ActivityAdjustProjectMain.class, safeTitle);
//			}
//		});
//
//		findViewById(R.id.btn_take_face).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				String safeTitle = ((TextView) findViewById(R.id.titleface)).getText().toString();
//				jumpActivity(TowerCraneAddperson.class, safeTitle);
//			}
//		});
//
//		// 先隐藏
//		findViewById(R.id.btn_take_count_face).setVisibility(8);
//		findViewById(R.id.btn_take_count_face).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				String safeTitle = ((TextView) findViewById(R.id.titlecountface)).getText().toString();
//				jumpActivity(FaceCountActivity.class, safeTitle);
//			}
//		});
		
		initData();
	}
	
	

	private void initData() {
		// TODO Auto-generated method stub
		 ArrayList<ConstrucTypeItem> data = new ArrayList<ConstrucTypeItem>();
		 ConstrucTypeItem item1 = new ConstrucTypeItem();
		 item1.id=701;
		 item1.text="环境监测";
		 data.add(item1);
		
		 ConstrucTypeItem item2 = new ConstrucTypeItem();
		 item2.id=702;
		 item2.text="工程管理";
		 data.add(item2);
		 
		 ConstrucTypeItem item3 = new ConstrucTypeItem();
		 item3.id=703;
		 item3.text="录入人脸";
		 data.add(item3);
		 
		 
//		 ConstrucTypeItem item4 = new ConstrucTypeItem();
//		 item4.id=704;
//		 item4.text="人脸使用率统计";
//		 data.add(item4);
		 

		LineGridView grid = (LineGridView) findViewById(R.id.moduleGrid);
		ArrayList<ModuleItem> moduleList = new ArrayList<ModuleItem>();
		String[] nameData = new String[data.size()];
		for (int i = 0; i < nameData.length; i++) {
			nameData[i] = data.get(i).text;

			ModuleItem item = new ModuleItem();
			switch (data.get(i).id) {
			case 701://环境监测
				item.id = data.get(i).id;
				item.name = data.get(i).text;
				item.imageId = R.drawable.icon_environmental_monitoring;
				item.jumpActivity = EnvironmentalMonitoringActivity.class;
				moduleList.add(item);
				break;
			case 702://工程管理
				item.id = data.get(i).id;
				item.name = data.get(i).text;
				item.imageId = R.drawable.icon_engineering_management;
				item.jumpActivity = ActivityAdjustProjectMain.class;
				moduleList.add(item);
				break;
			case 703://录入人脸
				item.id = data.get(i).id;
				item.name = data.get(i).text;
				item.imageId = R.drawable.icon_input_face;
				item.jumpActivity = TowerCraneAddperson.class;
				moduleList.add(item);
				break;
			case 704://人脸使用率统计
				item.id = data.get(i).id;
				item.name = data.get(i).text;
				item.imageId = R.drawable.icon_take_apart;
				item.jumpActivity = FaceCountActivity.class;
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
