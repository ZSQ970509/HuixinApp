package com.king.photo.activity;

import java.util.ArrayList;
import java.util.List;

import com.hc.android.huixin.R;
import com.king.photo.fragment.imageFragment;
import com.king.photo.fragment.qualityFragment;
import com.king.photo.fragment.safeFragment;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TakePhotoMainActivity extends FragmentActivity implements OnClickListener {

	private ViewPager yxcj_viewPager;
	private ArrayList<Fragment> fragmentList;// 表示装载滑动的布局
	// private List<String> titleList;// 表示滑动的每一页标题
	private ImageView cursor;// 动画图片
	private LinearLayout yxcj_tab1_title, yxcj_tab2_title, yxcj_tab3_title;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takephptomain_temp);
		InitView();
		InitImageView();
		InitViewPager();
	}

	private void InitView() {
		yxcj_tab1_title = (LinearLayout) findViewById(R.id.yxcj_tab1_title);
		yxcj_tab2_title = (LinearLayout) findViewById(R.id.yxcj_tab2_title);
		yxcj_tab3_title = (LinearLayout) findViewById(R.id.yxcj_tab3_title);

		yxcj_tab1_title.setOnClickListener(new MyOnClickListener(0));
		yxcj_tab2_title.setOnClickListener(new MyOnClickListener(1));
		yxcj_tab3_title.setOnClickListener(new MyOnClickListener(2));
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/**
	 * 加载cursor
	 */
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.yxcj_cursor);

		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.offset_white).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量

		FrameLayout.LayoutParams params = (LayoutParams) cursor.getLayoutParams();
		params.height = 4;
		params.width = screenW / 3;
		cursor.setLayoutParams(params);

		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

	/**
	 * 加载ViewPager
	 */
	private void InitViewPager() {
		yxcj_viewPager = (ViewPager) this.findViewById(R.id.yxcj_viewpager);
		// 动态加载布局
		// View yxcj_tab1 = LayoutInflater.from(this).inflate(null,
		// null);
		// View yxcj_tab2 = LayoutInflater.from(this).inflate(null,
		// null);
		// list = new ArrayList<View>();
		// list.add(yxcj_tab1);
		// list.add(yxcj_tab2);
		fragmentList = new ArrayList<Fragment>();
		Fragment qualityFragment = new qualityFragment();// 质量管理片段
		// Fragment qualityFragment =new safeFragment();

		Fragment safeFragment = new safeFragment();// 安全管理片段

		fragmentList.add(safeFragment);
		fragmentList.add(qualityFragment);
		// 改成全景拼图的

		Fragment ImageFragment = new imageFragment();// 全景拼图片段

		fragmentList.add(ImageFragment);

		yxcj_viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
		yxcj_viewPager.setCurrentItem(0);
		yxcj_viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				// Animation animation = null;
				// switch (arg0) {
				// case 0:
				// if (currIndex == 1) {
				// animation = new TranslateAnimation(one, 0, 0, 0);
				// Log.d("offset", offset + "");
				// Log.d("offsetbmpW", bmpW + "");
				//
				// }
				// break;
				// case 1:
				// if (currIndex == 0) {
				// animation = new TranslateAnimation(offset, one, 0, 0);
				// Log.d("offset", offset + "");
				// Log.d("offsetbmpW", bmpW + "");
				// }
				// break;
				// }
				Animation animation = new TranslateAnimation(one * currIndex, one * arg0, 0, 0);// 显然这个比较简洁，只有一行代码。
				currIndex = arg0;
				animation.setFillAfter(true);// True:图片停在动画结束位置
				animation.setDuration(300);
				cursor.startAnimation(animation);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			yxcj_viewPager.setCurrentItem(index);

		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		ArrayList<Fragment> list;

		public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
			super(fm);
			this.list = list;

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

	}

}
