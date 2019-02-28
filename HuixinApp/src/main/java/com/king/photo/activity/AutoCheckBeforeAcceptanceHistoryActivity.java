package com.king.photo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hc.android.huixin.R;
import com.hc.android.huixin.base.BaseActivity;
import com.hc.android.huixin.bean.json.InstallOpenGetProjectListJs;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.model.AutoDriviceModel;
import com.king.photo.model.AutoHistroyModel;
import com.onesafe.util.RecyclerViewUnderLineUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AutoCheckBeforeAcceptanceHistoryActivity extends BaseActivity {

	@BindView(R.id.rv_Auto_Submit_History_List)
	RecyclerView rvAutoSubmitHistoryList;

	BaseItemDraggableAdapter mAdapter;
	private List<AutoHistroyModel> mDataList = new ArrayList<>();

	private String token;
	private static final String TOKEN = "token";
	private InstallOpenGetProjectListJs.DataBean dataBean;
	private static final String PRO_BEAN = "projBean";

	public static void newInstance(Activity activity, String token, InstallOpenGetProjectListJs.DataBean projBean) {
		Intent intent = new Intent(activity, AutoCheckBeforeAcceptanceHistoryActivity.class);
		intent.putExtra(TOKEN, token);
		intent.putExtra(PRO_BEAN, projBean);
		activity.startActivity(intent);
	}
	@Override
	protected int getLayoutId() {
		return R.layout.activity_auto_check_before_acceptance_history;
	}

	@Override
	protected void initView() {
		setToolBar("历史记录");
		mAdapter = new BaseItemDraggableAdapter<AutoHistroyModel, BaseViewHolder>(R.layout.item_auto_history, mDataList) {
			@Override
			protected void convert(BaseViewHolder helper, AutoHistroyModel item) {
				helper.setText(R.id.textView_Auto_Histroy_Project, dataBean.getProjectName());
				helper.getView(R.id.textView_Auto_Histroy_Project).setSelected(true);
				helper.setText(R.id.textView_Auto_Histroy_Install, "监测时间："+item.getCreateTime());
				switch (item.getStatus()){
					case 0:
						helper.setText(R.id.textView_Auto_Histroy_State, "验收中")
								.setTextColor(R.id.textView_Auto_Histroy_State, Color.rgb(73,157,242));;
						break;
					case 1:
						helper.setText(R.id.textView_Auto_Histroy_State, "完成")
								.setTextColor(R.id.textView_Auto_Histroy_State, Color.rgb(0,128,0));;
						break;
					case 2:
						helper.setText(R.id.textView_Auto_Histroy_State, "失败")
								.setTextColor(R.id.textView_Auto_Histroy_State, Color.rgb(255,0,0));
						break;
					case 3:
						helper.setText(R.id.textView_Auto_Histroy_State, "超时")
								.setTextColor(R.id.textView_Auto_Histroy_State, Color.rgb(255,0,0));;
						break;
				}
			}
		};
		rvAutoSubmitHistoryList.addItemDecoration(new RecyclerViewUnderLineUtil(getActivity()));
		rvAutoSubmitHistoryList.setAdapter(mAdapter);
		rvAutoSubmitHistoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {


				AutoCheckBeforeAcceptanceResultActivity.newInstance(AutoCheckBeforeAcceptanceHistoryActivity.this,token,	mDataList.get(position).getId()+"",dataBean);
			}
		});
	}
	@Override
	protected void initData(Intent intent) {
		token = getIntent().getStringExtra(TOKEN);
		dataBean = (InstallOpenGetProjectListJs.DataBean) getIntent().getSerializableExtra(PRO_BEAN);
		getHistoryList();
	}

	private void getHistoryList() {
		showLoadDialog("正在加载中...");
		NetworkApi.getAutoHistoryList(getActivity(), dataBean.getProjectId(), token, new NetworkApi.NetCall() {
			@Override
			public void onSuccess(String result) {
				dismissLoadDialog();
				if (!result.equals("") && result != null) {
					Gson gson = new Gson();
					List<AutoHistroyModel> jsonList = gson.fromJson(result, new TypeToken<List<AutoHistroyModel>>() {
					}.getType());

					mDataList.clear();
					mDataList.addAll(jsonList);
					mAdapter.notifyDataSetChanged();
				}

			}
			@Override
			public void onFail(String msg) {
				dismissLoadDialog();
				ToastHelp.showToast(AutoCheckBeforeAcceptanceHistoryActivity.this, msg);
			}
		});
	}



}
