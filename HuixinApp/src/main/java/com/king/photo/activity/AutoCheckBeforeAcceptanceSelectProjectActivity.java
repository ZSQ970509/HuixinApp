package com.king.photo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.hc.andorid.zxing.app.CaptureActivity;
import com.hc.android.huixin.R;
import com.hc.android.huixin.base.BaseActivity;
import com.hc.android.huixin.bean.json.InstallOpenGetProjectListJs;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.JumpAc;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.CustomDialog;
import com.hc.android.huixin.view.DefaultDialog;
import com.hc.android.huixin.view.LoadingDialog;
import com.hc.android.huixin.view.SimgleSelectDialog.Builder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AutoCheckBeforeAcceptanceSelectProjectActivity extends BaseActivity {
	@BindView(R.id.EdtInStallOpenSelectProName)
	EditText mEdtProName;
	@BindView(R.id.SrlInStallOpenSelectPro)
	SwipeRefreshLayout mSwipeRefreshLayout;
	@BindView(R.id.RvInStallOpenSelectPro)
	RecyclerView mRecyclerView;
	BaseItemDraggableAdapter mAdapter;
	/**
	 * 列表里总的数量（服务端表示没法分页，只能取全部数据，android实现假分页）
	 */
	private List<InstallOpenGetProjectListJs.DataBean> mProjectListDataSum = new ArrayList<>();
	private List<InstallOpenGetProjectListJs.DataBean> mProjectListData = new ArrayList<>();//列表中显示的数据
	int pageIndex = 1;
	int pageSize = 10;
	int pageTotal = 1;
	private String mProTypeId;
	String token ;
	@Override
	protected int getLayoutId() {
		return R.layout.activity_auto_check_before_acceptance_select_project;
	}

	@Override
	protected void initView() {
		initToken();
		mProTypeId = getIntent().getIntExtra("ProjectTypeId", 0) + "";
		setToolBar("自动验收");
		mAdapter = new BaseItemDraggableAdapter<InstallOpenGetProjectListJs.DataBean, BaseViewHolder>(R.layout.item_install_open_select_pro, mProjectListData) {
			@Override
			protected void convert(BaseViewHolder helper, InstallOpenGetProjectListJs.DataBean item) {
				TextView tv = helper.getView(R.id.tvItemInstallOpenProjectName);
				tv.setSelected(true);
				tv.setText(item.getProjectName());
				helper.setText(R.id.tvItemInstallOpenNum, "安装情况：" + item.getInstallNum());
				TextView distance = helper.getView(R.id.tvItemInstallOpenDistance);
				distance.setText(item.getActualDistance() + getResource(R.string.actual_distance));
				if ("-1.0".equals(item.getActualDistance())) {
					distance.setTextColor(Color.rgb(255, 0, 0));
				} else {
					distance.setTextColor(Color.rgb(74, 137, 220));
				}
			}
		};
		mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {
				if (pageIndex >= pageTotal) {
					showToast("已经是最后一页了！");
					mAdapter.loadMoreEnd();
				} else {
					pageIndex++;
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							searchPro();
						}
					}, 700);
				}
			}
		}, mRecyclerView);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				mSwipeRefreshLayout.setRefreshing(true);
				mProjectListData.clear();
				mAdapter.setNewData(null);
				mAdapter.setNewData(mProjectListData);//重新开启下拉加载更多
				searchProAll(false);
			}
		});
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				//JumpAc.toTakePhotoInstallOpenSelectTypeAc(getActivity(), mProjectListData.get(position), mProTypeId, mProTypeName);
				AutoCheckBeforeAcceptanceSubmitActivity.newInstance(AutoCheckBeforeAcceptanceSelectProjectActivity.this,token,mProjectListData.get(position));
			}
		});
	}
	@Override
	protected void initData(Intent intent) {

		searchProAll(true);
	}

	private void  initToken(){
		NetworkApi.getAutoLogin(getActivity(), "111111", "admin", new NetworkApi.NetCall() {
			@Override
			public void onSuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					token = json.getString("token");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFail(String msg) {
				ToastHelp.showToast(AutoCheckBeforeAcceptanceSelectProjectActivity.this,msg);
			}
		});
	}
	private void searchProAll(boolean isUploadLocation) {
		pageIndex = 1;
		pageSize = 10;
		pageTotal = 1;
		mProjectListData.clear();
		String proName = mEdtProName.getText().toString();
		showLoadDialog("正在加载中...");
		NetworkApi.getCommonWorkProjectList(getActivity(), mProTypeId, proName, pageIndex, pageSize,isUploadLocation, new NetworkApi.NetCall() {
			@Override
			public void onSuccess(String result) {
				dismissLoadDialog();
				InstallOpenGetProjectListJs js = new Gson().fromJson(result, InstallOpenGetProjectListJs.class);
				mProjectListDataSum = js.getData();
				if (js.getData() != null) {
					for (int i = 0; i < pageSize && i < mProjectListDataSum.size(); i++) {
						mProjectListData.add(mProjectListDataSum.get(i));
					}
				}
				mAdapter.notifyDataSetChanged();
				pageTotal = (Integer.parseInt(js.getTotalcount()) + pageSize - 1) / pageSize;
				finishRefreshAndLoadMore();
			}

			@Override
			public void onFail(String msg) {
				dismissLoadDialog();
				mAdapter.notifyDataSetChanged();
				showToast(msg);
				finishRefreshAndLoadMore();
			}
		});
	}
	private void searchPro() {
		for (int i = (pageIndex - 1) * pageSize; i < pageIndex * pageSize && i < mProjectListDataSum.size(); i++) {
			mProjectListData.add(mProjectListDataSum.get(i));
		}
		mAdapter.notifyDataSetChanged();
		finishRefreshAndLoadMore();
	}

	private void finishRefreshAndLoadMore() {
		if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
			mSwipeRefreshLayout.setRefreshing(false);
		}
		if (mAdapter != null && mAdapter.isLoading()) {
			mAdapter.loadMoreComplete();
		}
	}
	@OnClick({R.id.BtnInStallOpenSelectPro})
	void onClick(View v) {
		switch (v.getId()) {
			case R.id.BtnInStallOpenSelectPro:
				searchProAll(false);
				break;

		}
	}
}
