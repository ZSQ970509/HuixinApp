package com.king.photo.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hc.android.huixin.R;
import com.hc.android.huixin.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AutoCheckBeforeAcceptanceHistoryActivity extends BaseActivity {

	@BindView(R.id.rv_Auto_Submit_History_List)
	RecyclerView rvAutoSubmitHistoryList;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_auto_check_before_acceptance_history;
	}

	@Override
	protected void initView() {
		setToolBar("历史记录");
	}
	@Override
	protected void initData(Intent intent) {


	}




}
