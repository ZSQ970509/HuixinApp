package com.king.photo.activity;


import android.content.Intent;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.hc.android.huixin.R;

import com.hc.android.huixin.base.BaseActivity;
import com.hc.android.huixin.bean.json.DisassembleGetProjectListJs;

import com.hc.android.huixin.network.NetworkApi;

import com.hc.android.huixin.util.JumpAc;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择项目列表页面（拆机）
 */
public class DisassembleSelectProActivityOld extends BaseActivity {
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
    private List<DisassembleGetProjectListJs.DataBean> mProjectListDataSum = new ArrayList<>();
    private List<DisassembleGetProjectListJs.DataBean> mProjectListData = new ArrayList<>();//列表中显示的数据
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
        setToolBar(mProTypeName);
        mAdapter = new BaseItemDraggableAdapter<DisassembleGetProjectListJs.DataBean, BaseViewHolder>(R.layout.item_disassemble_select_pro, mProjectListData) {
            @Override
            protected void convert(BaseViewHolder helper, DisassembleGetProjectListJs.DataBean item) {
                TextView tv = helper.getView(R.id.tvItemDisassembleProjectName);
                tv.setSelected(true);
                tv.setText(item.getProjectName());
                helper.setText(R.id.tvItemDisassembleNum, "未拆机数量：" + item.getRemoveNum());
                helper.setText(R.id.tvItemDisassembleDistance, item.getActualDistance() + getResource(R.string.actual_distance));
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
                JumpAc.toDisassembleAc(getActivity(), mProjectListData.get(position), mProTypeId, mProTypeName);
            }
        });

    }

    @Override
    protected void initData(Intent intent) {
        mProTypeName = getIntent().getStringExtra("title");
        mProjectType = getIntent().getStringExtra("ProjectType");
        mProTypeId = getIntent().getIntExtra("ProjectTypeId", 0) + "";
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
                DisassembleGetProjectListJs js = new Gson().fromJson(result, DisassembleGetProjectListJs.class);
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