package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.SettingItem;
import com.king.photo.adapter.AccountManagerBaseadapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AccountManagerActivity extends Activity {
	ListView mListView1;
	ListView mListView2;
	AccountManagerBaseadapter accountManagerBaseadapter;
	ArrayList<SettingItem> list1 = new ArrayList<SettingItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_account_manager);
		initData();
		initView();
	}

	private void initData() {
		// TODO Auto-generated method stub
		list1.clear();
		SettingItem item1 = new SettingItem();
		item1.imageId = R.drawable.setting_arrow;
		item1.name = "密码修改";
		item1.id = 101; // 101 密码修改
		list1.add(item1);

		SettingItem item2 = new SettingItem();
		item2.name = "记住密码";

		item2.id = 102; // 102 自动登录 //关闭记住密码 开启自动登录。下次登录照样会开启记住密码
		list1.add(item2);

		SettingItem item3 = new SettingItem();
		item3.name = "自动登录";

		item3.id = 103; // 103 记住密码
		list1.add(item3);
	}

	private void initView() {
		// TODO Auto-generated method stub
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mListView1 = (ListView) findViewById(R.id.listView1);// 间隔5DP

		mListView2 = (ListView) findViewById(R.id.listView2);// 间隔0DP
		accountManagerBaseadapter = new AccountManagerBaseadapter(AccountManagerActivity.this, list1);
		mListView2.setAdapter(accountManagerBaseadapter);
		setListViewHeight(mListView2);
	}

	public static void setListViewHeight(ListView listview) {
		int totalHeight = 0;
		ListAdapter adapter = listview.getAdapter();
		if (null != adapter) {
			for (int i = 0; i < adapter.getCount(); i++) {
				View listItem = adapter.getView(i, null, listview);
				if (null != listItem) {
					listItem.measure(0, 0);// 注意listview子项必须为LinearLayout才能调用该方法
					totalHeight += listItem.getMeasuredHeight();
				}
			}

			ViewGroup.LayoutParams params = listview.getLayoutParams();
			params.height = totalHeight + (listview.getDividerHeight() * (listview.getCount() - 1));
			listview.setLayoutParams(params);
		}
	}
}
