package com.king.photo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.hc.android.huixin.R;
import com.hc.android.huixin.base.*;
import com.hc.android.huixin.bean.json.RemovedeviceGetProjectListJs;
import com.hc.android.huixin.bean.json.RemovedeviceGetProjectListJs;
import com.hc.android.huixin.network.CameraItemNew;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.MaintenanceTypeItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectMoveItem;
import com.hc.android.huixin.network.ResponsibilitySubjectItem;
import com.hc.android.huixin.network.SendRemoveDataBean;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.JumpAc;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TakePhotoRemoveDeviceSelectProActivity extends com.hc.android.huixin.base.BaseActivity {
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
    private List<RemovedeviceGetProjectListJs.DataBean> mProjectListDataSum = new ArrayList<>();
    private List<RemovedeviceGetProjectListJs.DataBean> mProjectListData = new ArrayList<>();//列表中显示的数据
    /**
     * 工程类型id（不是项目工程id）
     */
    private String mProTypeId;
    private String mProjectType = "";
    private String mProTypeName = "";//安装开通页面的标题
    int pageIndex = 1;
    int pageSize = 10;
    int pageTotal = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_take_photo_install_open_select_pro;
    }

    @Override
    protected void initView() {
        mProTypeName = getIntent().getStringExtra("title");
        mProjectType = getIntent().getStringExtra("ProjectType");
        mProTypeId = getIntent().getIntExtra("ProjectTypeId", 0) + "";
        setToolBar(mProTypeName);
        mAdapter = new BaseItemDraggableAdapter<RemovedeviceGetProjectListJs.DataBean, BaseViewHolder>(R.layout.item_remove_select_pro, mProjectListData) {
            @Override
            protected void convert(BaseViewHolder helper, RemovedeviceGetProjectListJs.DataBean item) {
                TextView tv = helper.getView(R.id.tvItemRemoveProjectName);
                tv.setSelected(true);
                tv.setText(item.getProjectName());
                helper.setText(R.id.tvItemRemoveNum, "未移机数量：" + item.getRemoveNum());
                TextView distance = helper.getView(R.id.tvItemRemoveDistance);
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
                mAdapter.setNewData(mProjectListData);//重新开启下拉加载更多
                searchProAll(false);
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                TakePhotoRemoveDeviceActivity
                JumpAc.toTakePhotoRemoveDeviceAc(getActivity(), mProjectListData.get(position), mProTypeId, mProTypeName);
            }
        });

    }

    @Override
    protected void initData(Intent intent) {
        searchProAll(true);
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
                RemovedeviceGetProjectListJs js = new Gson().fromJson(result, RemovedeviceGetProjectListJs.class);
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