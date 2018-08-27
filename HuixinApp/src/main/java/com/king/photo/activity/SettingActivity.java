package com.king.photo.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.ffcs.surfingscene.sdk.MainActivity;
import com.hc.android.huixin.LoginActivity;
import com.hc.android.huixin.MyApplication;
import com.hc.android.huixin.R;
import com.hc.android.huixin.WelcomeActivity;
import com.hc.android.huixin.network.SettingItem;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.adapter.SettingBaseadapter1;
import com.king.photo.adapter.SettingBaseadapter2;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SettingActivity extends BaseActivity {

	ListView mListView1;
	ListView mListView2;

	ArrayList<SettingItem> list1 = new ArrayList<SettingItem>();
	ArrayList<SettingItem> list2 = new ArrayList<SettingItem>();

	SettingBaseadapter1 mSettingActivityTemp1;
	SettingBaseadapter2 mSettingActivityTemp2;

	LinearLayout mTextQuit;
	LinearLayout mTextCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initData();
		initView();

	}

	private void initData() {
		// TODO Auto-generated method stub
		list1.clear();
		list2.clear();

		SettingItem item1 = new SettingItem();
		item1.imageId = R.drawable.setting_arrow;
		item1.name = "账户管理";
		item1.id = 10; // 10 账户管理
		list1.add(item1);

		SettingItem item2 = new SettingItem();
		item2.name = "在线更新";
		item2.id = 1236; // 20 在线更新
		list2.add(item2);

		SettingItem item3 = new SettingItem();
		item3.name = "关于我们";
		item3.id = 1237; // 21 在线更新
		list2.add(item3);

		SettingItem item4 = new SettingItem();
		item4.name = "权限设置";
		item4.id = 1238; // 21 在线更新
		list2.add(item4);

		// SettingItem item4 = new SettingItem();
		// item4.name = "关于我们";
		// item4.id = 8001; // 21 在线更新

	}

	private void initView() {
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mTextQuit = (LinearLayout) findViewById(R.id.setting_quit);
		mTextCancel = (LinearLayout) findViewById(R.id.setting_cancel);

		mListView1 = (ListView) findViewById(R.id.listView1);
		mListView2 = (ListView) findViewById(R.id.listView2);

		mSettingActivityTemp1 = new SettingBaseadapter1(SettingActivity.this, list1);
		mListView1.setAdapter(mSettingActivityTemp1);

		mSettingActivityTemp2 = new SettingBaseadapter2(SettingActivity.this, list2);
		mListView2.setAdapter(mSettingActivityTemp2);

		mListView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

			}
		});
		setListViewHeight(mListView1);
		setListViewHeight(mListView2);

		mTextQuit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				MyApplication.getInstance().exit(MyApplication.ZCEXIT);

			}
		});

		mTextCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PreferenceUtil.setAutoLogin(false);
				Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
				intent.putExtra("title", "");
				startActivity(intent);
				SettingActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void back() {
		finish();
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
