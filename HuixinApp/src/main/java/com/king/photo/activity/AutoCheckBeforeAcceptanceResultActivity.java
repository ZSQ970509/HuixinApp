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

public class AutoCheckBeforeAcceptanceResultActivity extends BaseActivity {
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
