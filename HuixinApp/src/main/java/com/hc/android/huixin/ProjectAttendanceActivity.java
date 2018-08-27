package com.hc.android.huixin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.BaiduSdkHelper;
import com.hc.android.huixin.view.CustomLoadMoreView;
import com.king.photo.adapter.AttendanceProjectAdapter;
import com.king.photo.model.AttendanceProjectModel;

import java.util.ArrayList;

/**
 * 选择项目列表（考勤）
 */
public class ProjectAttendanceActivity extends Activity implements View.OnClickListener {
    RecyclerView rvSelectProject;
    ImageButton ibBack;
    TextView tvTitle;
    EditText etSelectProject;
    Button btnSearch;
    ArrayList<AttendanceProjectModel.DataBean> projectSumList = new ArrayList<AttendanceProjectModel.DataBean>();
    ArrayList<AttendanceProjectModel.DataBean> projectList = new ArrayList<AttendanceProjectModel.DataBean>();
    int pageIndex = 0;
    int sumPage;
    AttendanceProjectAdapter AttendanceProjectAdapter;
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
        rvSelectProject = (RecyclerView) findViewById(R.id.rvSelectProject);
        rvSelectProject.setLayoutManager(new LinearLayoutManager(ProjectAttendanceActivity.this));
        AttendanceProjectAdapter = new AttendanceProjectAdapter(R.layout.item_select_project_attendance, projectList);
        rvSelectProject.setAdapter(AttendanceProjectAdapter);
        AttendanceProjectAdapter.setLoadMoreView(new CustomLoadMoreView());
        AttendanceProjectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                intent = getIntent();
                intent.putExtra("dataBean", projectList.get(position));
                intent.putExtra("toGongJian", true);
                intent.setClass(ProjectAttendanceActivity.this, AttendancePathProjectHumenActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AttendanceProjectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rvSelectProject.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pageIndex > sumPage) {
                            Toast.makeText(ProjectAttendanceActivity.this, "已经是最后一页了！", Toast.LENGTH_SHORT).show();
                            AttendanceProjectAdapter.loadMoreEnd();
                        } else {
                            Log.e("111111", "11111");
                            //new AttendanceProjectAsyncTask().execute(etSelectProject.getText().toString());
                            int pageNum = pageIndex * 10 + 10;
                            if (pageIndex * 10 + 10 > projectSumList.size()) {
                                pageNum = projectSumList.size();
                            }
                            for (int i = pageIndex * 10; i < pageNum; i++) {
                                projectList.add(projectSumList.get(i));
                            }
                            AttendanceProjectAdapter.notifyDataSetChanged();
                            if (pageIndex <= sumPage) {
                                pageIndex++;
                            }
                            AttendanceProjectAdapter.loadMoreComplete();
                        }

                    }

                }, 3000);
            }
        }, rvSelectProject);
        AttendanceProjectAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tvWeiHuLong) {
                    BaiduSdkHelper.openBaiduNavi(ProjectAttendanceActivity.this, projectList.get(position).getProjectLng(), projectList.get(position).getProjectLat(), null);
                }
            }
        });
        //AttendanceProjectAdapter.disableLoadMoreIfNotFullPage(rvSelectProject);
        ibBack = (ImageButton) findViewById(R.id.regulatory_back);
        tvTitle = (TextView) findViewById(R.id.title);
        ibBack.setOnClickListener(this);
        etSelectProject = (EditText) findViewById(R.id.etSelectProject);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
    }

    private boolean isUploadLocation = true;//是否上传经纬度

    public void initData() {
        isUploadLocation = true;
        new AttendanceProjectAsyncTask().execute("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                projectSumList.clear();
                projectList.clear();
                AttendanceProjectAdapter.notifyDataSetChanged();
                isUploadLocation = false;
                new AttendanceProjectAsyncTask().execute(etSelectProject.getText().toString());
                break;
            case R.id.regulatory_back:
                finish();
                break;
            default:
                break;
        }
    }


    private class AttendanceProjectAsyncTask extends AsyncTask<String, Void, AttendanceProjectModel> {

        @Override
        protected AttendanceProjectModel doInBackground(String... params) {
            return new NetworkApi().getPathProject(ProjectAttendanceActivity.this, params[0], pageIndex + "", "10", isUploadLocation);
        }

        @Override
        protected void onPostExecute(final AttendanceProjectModel data) {
            if (data == null) {
                Toast.makeText(ProjectAttendanceActivity.this, "暂无数据！", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(ProjectAttendanceActivity.this, "数据加载成功！", Toast.LENGTH_SHORT).show();
            }
            projectSumList.addAll(data.getData());


            int pageNum = 10;
            if (pageNum > projectSumList.size()) {
                pageNum = projectSumList.size();
            }
            for (int i = 0; i < pageNum; i++) {
                projectList.add(projectSumList.get(i));
            }
            AttendanceProjectAdapter.notifyDataSetChanged();
            sumPage = (Integer.parseInt(data.getTotalcount()) + 10 - 1) / 10;
            if (pageIndex <= sumPage) {
                pageIndex++;
            }

        }
    }
}
