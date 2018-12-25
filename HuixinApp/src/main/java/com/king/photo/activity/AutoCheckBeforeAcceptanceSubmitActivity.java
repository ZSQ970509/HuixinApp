package com.king.photo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.king.photo.model.FullImageNodeModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AutoCheckBeforeAcceptanceSubmitActivity extends BaseActivity {
    private final static int SCANNIN_GREQUEST_CODE = 1;
    @BindView(R.id.textView_Auto_Submit_Project)
    TextView textViewAutoSubmitProject;
    @BindView(R.id.textView_Auto_Submit_Install)
    TextView textViewAutoSubmitInstall;
    @BindView(R.id.textView_Auto_Submit_Acceptance)
    TextView textViewAutoSubmitAcceptance;
    @BindView(R.id.textView_Auto_Submit_Distance)
    TextView textViewAutoSubmitDistance;
    @BindView(R.id.btn_Auto_Submit_Acceptance)
    Button btnAutoSubmitAcceptance;
    @BindView(R.id.rv_Auto_Submit_Driver_List)
    RecyclerView rvAutoSubmitDriverList;
    private String token;
    private static final String TOKEN = "token";
    private InstallOpenGetProjectListJs.DataBean dataBean;
    private static final String PRO_BEAN = "projBean";

    public static void newInstance(Activity activity, String token, InstallOpenGetProjectListJs.DataBean projBean) {
        Intent intent = new Intent(activity, AutoCheckBeforeAcceptanceSubmitActivity.class);
        intent.putExtra(TOKEN, token);
        intent.putExtra(PRO_BEAN, projBean);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_auto_check_before_acceptance_submit;
    }

    @Override
    protected void initView() {
        setToolBar("自动验收");
        setToolBarRight(R.drawable.autohistory, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void initData(Intent intent) {
        token = getIntent().getStringExtra(TOKEN);
        dataBean = (InstallOpenGetProjectListJs.DataBean) getIntent().getSerializableExtra(PRO_BEAN);
        textViewAutoSubmitProject.setText(dataBean.getProjectName());
        textViewAutoSubmitInstall.setText("安装情况：" + dataBean.getInstallNum());
        textViewAutoSubmitDistance.setText(dataBean.getActualDistance() + "KM");
        getDrviceList();
    }


    @OnClick({R.id.btn_Auto_Submit_Acceptance})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Auto_Submit_Acceptance:

                break;

        }
    }

    private void getDrviceList() {
        NetworkApi.getAutoDeviceList(getActivity(), dataBean.getProjectId(), token, new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                if (!result.equals("")) {
                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<AutoDriviceModel>>() {
//                    }.getType();
                    List<AutoDriviceModel> jsonList = gson.fromJson(result, new TypeToken<List<AutoDriviceModel>>() {}.getType());
                    Log.e("111", jsonList.toString());
                }else{
                    ToastHelp.showToast(AutoCheckBeforeAcceptanceSubmitActivity.this, "暂无数据");
                }


            }

            @Override
            public void onFail(String msg) {
                ToastHelp.showToast(AutoCheckBeforeAcceptanceSubmitActivity.this, msg);
            }
        });
    }
}
