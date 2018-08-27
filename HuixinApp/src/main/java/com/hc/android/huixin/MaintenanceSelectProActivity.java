package com.hc.android.huixin;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.BaiduSdkHelper;
import com.hc.android.huixin.view.CustomLoadMoreView;
import com.king.photo.adapter.GetWeiHuProjectAdapter;
import com.king.photo.model.WeiHuProjectModel;

import java.util.ArrayList;

/**
 * 选择项目列表（维护）
 */
public class MaintenanceSelectProActivity extends Activity implements View.OnClickListener {
    RecyclerView rvSelectProject;
    ImageButton ibBack;
    TextView tvTitle;
    EditText etSelectProject;
    Button btnSearch;
    ArrayList<WeiHuProjectModel.DataBean> projectSumList = new ArrayList<WeiHuProjectModel.DataBean>();
    ArrayList<WeiHuProjectModel.DataBean> projectList = new ArrayList<WeiHuProjectModel.DataBean>();
    int pageIndex = 0;
    int sumPage;
    GetWeiHuProjectAdapter getWeiHuProjectAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_take_phote_install_open);
        BaiduSdkHelper.initNavi(this);
        initView();
        initData();

    }

    public void initView() {
        String proTypeName = getIntent().getStringExtra("title");
        ((TextView) findViewById(R.id.title)).setText(proTypeName);
        rvSelectProject = (RecyclerView) findViewById(R.id.rvSelectProject);
        rvSelectProject.setLayoutManager(new LinearLayoutManager(MaintenanceSelectProActivity.this));
        getWeiHuProjectAdapter = new GetWeiHuProjectAdapter(R.layout.item_select_project, projectList);
        rvSelectProject.setAdapter(getWeiHuProjectAdapter);
        getWeiHuProjectAdapter.setLoadMoreView(new CustomLoadMoreView());
        getWeiHuProjectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                intent = getIntent();
                intent.putExtra("dataBean", projectList.get(position));
                intent.setClass(MaintenanceSelectProActivity.this, MaintenanceActivity.class);
                startActivity(intent);
            }
        });
        getWeiHuProjectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rvSelectProject.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pageIndex > sumPage) {
                            Toast.makeText(MaintenanceSelectProActivity.this, "已经是最后一页了！", Toast.LENGTH_SHORT).show();
                            getWeiHuProjectAdapter.loadMoreEnd();
                        } else {
                            Log.e("111111", "11111");
                            //new WeiHuProjectAsyncTask().execute(etSelectProject.getText().toString());
                            int pageNum = pageIndex * 10 + 10;
                            if (pageIndex * 10 + 10 > projectSumList.size()) {
                                pageNum = projectSumList.size();
                            }
                            for (int i = pageIndex * 10; i < pageNum; i++) {
                                projectList.add(projectSumList.get(i));
                            }
                            getWeiHuProjectAdapter.notifyDataSetChanged();
                            if (pageIndex <= sumPage) {
                                pageIndex++;
                            }
                            getWeiHuProjectAdapter.loadMoreComplete();
                        }

                    }

                }, 3000);
            }
        }, rvSelectProject);
        getWeiHuProjectAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tvWeiHuLong) {
                    BaiduSdkHelper.openBaiduNavi(MaintenanceSelectProActivity.this, projectList.get(position).getProjectLng(), projectList.get(position).getProjectLat(), null);
                }
            }
        });
        getWeiHuProjectAdapter.disableLoadMoreIfNotFullPage(rvSelectProject);
        ibBack = (ImageButton) findViewById(R.id.regulatory_back);
        tvTitle = (TextView) findViewById(R.id.title);
        ibBack.setOnClickListener(this);
        etSelectProject = (EditText) findViewById(R.id.etSelectProject);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
    }

    public void initData() {
        new WeiHuProjectAsyncTask().execute("");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                projectSumList.clear();
                projectList.clear();
                getWeiHuProjectAdapter.notifyDataSetChanged();
                new WeiHuProjectAsyncTask().execute(etSelectProject.getText().toString());
                break;
            case R.id.regulatory_back:
                finish();
                break;
            default:
                break;
        }
    }


    private class WeiHuProjectAsyncTask extends AsyncTask<String, Void, WeiHuProjectModel> {

        @Override
        protected WeiHuProjectModel doInBackground(String... params) {
            return new NetworkApi().getWeiHuProject(MaintenanceSelectProActivity.this, params[0], pageIndex + "", "10");
        }

        @Override
        protected void onPostExecute(final WeiHuProjectModel data) {
            if (data == null) {
                Toast.makeText(MaintenanceSelectProActivity.this, "暂无数据！", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(MaintenanceSelectProActivity.this, "数据加载成功！", Toast.LENGTH_SHORT).show();
            }
            projectSumList.addAll(data.getData());


            int pageNum = 10;
            if (pageNum > projectSumList.size()) {
                pageNum = projectSumList.size();
            }
            for (int i = 0; i < pageNum; i++) {
                projectList.add(projectSumList.get(i));
            }
            getWeiHuProjectAdapter.notifyDataSetChanged();
            sumPage = (Integer.parseInt(data.getTotalcount()) + 10 - 1) / 10;
            if (pageIndex <= sumPage) {
                pageIndex++;
            }

        }
    }
}
