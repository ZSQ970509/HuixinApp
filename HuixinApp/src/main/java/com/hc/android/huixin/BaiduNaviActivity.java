package com.hc.android.huixin;

import java.util.ArrayList;
import java.util.List;

import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRouteGuideManager.CustomizedLayerItem;
import com.baidu.navisdk.adapter.BNRouteGuideManager.OnNavigationListener;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.hc.android.huixin.util.BaiduSdkHelper;
import com.baidu.navisdk.adapter.BNRoutePlanNode;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class BaiduNaviActivity extends Activity {
	private View mView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置屏幕常亮
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
		}
		mView = BNRouteGuideManager.getInstance().onCreate(this, new OnNavigationListener() {
			// 导航过程监听器
			@Override
			public void onNaviGuideEnd() {// 导航过程结束
				 finish();
			}

			@Override
			public void notifyOtherAction(int actionType, int arg1, int arg2, Object obj) {
				// actionType=0 表示到达目的地，导航自动退出的标志
			}
		});

		if (mView != null) {
			setContentView(mView);
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		BNRouteGuideManager.getInstance().onResume();
	}

	protected void onPause() {
		super.onPause();
		BNRouteGuideManager.getInstance().onPause();
	};

	@Override
	protected void onDestroy() {
		BNRouteGuideManager.getInstance().onDestroy();
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		BNRouteGuideManager.getInstance().onStop();
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		BNRouteGuideManager.getInstance().onBackPressed(true);
	}

	public void onConfigurationChanged(android.content.res.Configuration newConfig) {
		BNRouteGuideManager.getInstance().onConfigurationChanged(newConfig);
		super.onConfigurationChanged(newConfig);
	};
}
