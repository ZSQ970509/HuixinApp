package com.king.photo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hc.android.huixin.R;
import com.hc.android.huixin.base.BaseActivity;
import com.hc.android.huixin.bean.json.InstallOpenGetProjectListJs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AutoCheckBeforeAcceptanceSubmitActivity extends BaseActivity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	@BindView(R.id.textView_Auto_Submit_Project)
	TextView textViewAutoSubmitProject;
	@BindView(R.id.textView_Auto_Submit_Acceptance)
	TextView textViewAutoSubmitAcceptance;
	@BindView(R.id.textView_Auto_Submit_Distance)
	TextView textViewAutoSubmitDistance;
	@BindView(R.id.btn_Auto_Submit_Acceptance)
	Button btnAutoSubmitAcceptance;
	@BindView(R.id.rv_Auto_Submit_Driver_List)
	RecyclerView rvAutoSubmitDriverList;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_auto_check_before_acceptance_submit;
	}

	@Override
	protected void initView() {
		setToolBar("自动验收");
		setToolBarRight(R.drawable.icon, new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}
	@Override
	protected void initData(Intent intent) {


	}



	@OnClick({R.id.btn_Auto_Submit_Acceptance})
	void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_Auto_Submit_Acceptance:

				break;

		}
	}
}
