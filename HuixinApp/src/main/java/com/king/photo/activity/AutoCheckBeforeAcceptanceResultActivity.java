package com.king.photo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.king.photo.model.AutoResultModel;
import com.onesafe.util.RecyclerViewUnderLineUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AutoCheckBeforeAcceptanceResultActivity extends BaseActivity {
	@BindView(R.id.textView_Auto_Result_Project)
	TextView textViewAutoResultProject;
	@BindView(R.id.textView_Auto_Result_Install)
	TextView textViewAutoResultInstall;
	@BindView(R.id.textView_Auto_Result_Acceptance)
	TextView textViewAutoResultAcceptance;
	@BindView(R.id.textView_Auto_Result_Distance)
	TextView textViewAutoResultDistance;
	@BindView(R.id.textView_Auto_Result_Time)
	TextView textViewAutoResultTime;
	@BindView(R.id.textView_Auto_Result_Call)
	TextView textViewAutoResultCall;
	@BindView(R.id.rv_Auto_Result_Driver_List)
	RecyclerView rvAutoResultDriverList;

	private List<AutoResultModel.CheckAcceptDevicesBean> mDataList = new ArrayList<>();
	BaseItemDraggableAdapter mAdapter;

	boolean isLooper = true;
	boolean isFrist = true;

	private String id;
	private static final String ID = "id";
	private String token;
	private static final String TOKEN = "token";
	private InstallOpenGetProjectListJs.DataBean dataBean;
	private static final String PRO_BEAN = "projBean";

	public static void newInstance(Activity activity, String token, String id,InstallOpenGetProjectListJs.DataBean projBean) {
		Intent intent = new Intent(activity, AutoCheckBeforeAcceptanceResultActivity.class);
		intent.putExtra(TOKEN, token);
		intent.putExtra(PRO_BEAN, projBean);
		intent.putExtra(ID, id);
		activity.startActivity(intent);
	}
	@Override
	protected int getLayoutId() {
		return R.layout.activity_auto_check_before_acceptance_result;
	}

	@Override
	protected void initView() {
		setToolBar("自动验收");
		mAdapter = new BaseItemDraggableAdapter<AutoResultModel.CheckAcceptDevicesBean, BaseViewHolder>(R.layout.item_auto_result, mDataList) {
			@Override
			protected void convert(BaseViewHolder helper, AutoResultModel.CheckAcceptDevicesBean item) {
				//需改成名称
				helper.setText(R.id.textView_Auto_Result_Name, item.getDeviceName());
				setStateImage(item.getVideoStatus(),helper,R.id.ImageView_Auto_Result_Video);
				setStateImage(item.getRangingStatus(),helper,R.id.ImageView_Auto_Result_JiGuang);
				setStateImage(item.getControllerStatus(),helper,R.id.ImageView_Auto_Result_control);
			}
		};
		rvAutoResultDriverList.addItemDecoration(new RecyclerViewUnderLineUtil(getActivity()));
		rvAutoResultDriverList.setAdapter(mAdapter);
		rvAutoResultDriverList.setLayoutManager(new LinearLayoutManager(getActivity()));
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {

			}
		});
	}
	@Override
	protected void initData(Intent intent) {
		token = getIntent().getStringExtra(TOKEN);
		dataBean = (InstallOpenGetProjectListJs.DataBean) getIntent().getSerializableExtra(PRO_BEAN);
		id = getIntent().getStringExtra(ID);
		textViewAutoResultProject.setText(dataBean.getProjectName());
		textViewAutoResultProject.setSelected(true);
		textViewAutoResultInstall.setText("安装情况：" + dataBean.getInstallNum());
		textViewAutoResultDistance.setText(dataBean.getActualDistance() + "KM");

		getResultList();
	}



	@OnClick({R.id.textView_Auto_Result_Call})
	void onClick(View v) {
		switch (v.getId()) {
			case R.id.textView_Auto_Result_Call:
				Intent intent = new Intent(Intent.ACTION_DIAL);
				Uri data = Uri.parse("tel:" + "15659432318");
				intent.setData(data);
				startActivity(intent);
				break;

		}
	}
	int TIME = 10000; //每隔1s执行一次.

	Handler handler = new Handler();
	private void getResultList() {
		if(isFrist){
			showLoadDialog("正在加载中...");
		}
		NetworkApi.getAutoRecordList(getActivity(), id, token, new NetworkApi.NetCall() {
			@Override
			public void onSuccess(String result) {
				if(isFrist){
					dismissLoadDialog();
					isFrist = false;
				}
				if (!result.equals("") && result != null) {
					Gson gson = new Gson();
					AutoResultModel autoResultModel = gson.fromJson(result, new TypeToken<AutoResultModel>() {
					}.getType());
					mDataList.clear();
					mDataList.addAll(autoResultModel.getCheckAcceptDevices());
					textViewAutoResultTime.setText("验收时间："+autoResultModel.getCreateTime());
					mAdapter.notifyDataSetChanged();
				}
				handler.postDelayed(runnable, TIME); // 在初始化方法里.
			}
			@Override
			public void onFail(String msg) {
				if(isFrist){
					dismissLoadDialog();
					isFrist = false;
				}
				ToastHelp.showToast(AutoCheckBeforeAcceptanceResultActivity.this, msg);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		isLooper = false;
	}

	private void setStateImage(int status, BaseViewHolder helper, int resId){
		switch (status){
			case 0:

				helper.setBackgroundRes(resId, R.drawable.autolose);
				break;
			case 1:
				helper.setBackgroundRes(resId, R.drawable.autobingo);
				break;
			case 2:
				helper.setBackgroundRes(resId, R.drawable.autounsupper);
				break;
			case 3:
				helper.setBackgroundRes(resId, R.drawable.autoload);
				break;

		}
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			try {
				if(isLooper) {
					getResultList();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

}
