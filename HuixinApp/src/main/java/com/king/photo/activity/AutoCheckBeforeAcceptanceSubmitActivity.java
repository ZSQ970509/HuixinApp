package com.king.photo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
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
import com.king.photo.model.AutoSubmitModel;
import com.king.photo.model.AutoResultModel;
import com.onesafe.util.RecyclerViewUnderLineUtil;

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

    BaseItemDraggableAdapter mAdapter;
    List<Boolean> submitDriviceIsChecked = new ArrayList<>();
    private List<AutoDriviceModel> mDataList = new ArrayList<>();

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
                AutoCheckBeforeAcceptanceHistoryActivity.newInstance(AutoCheckBeforeAcceptanceSubmitActivity.this,token,dataBean);
            }
        });
        mAdapter = new BaseItemDraggableAdapter<AutoDriviceModel, BaseViewHolder>(R.layout.item_auto_submit, mDataList) {
            @Override
            protected void convert(BaseViewHolder helper, AutoDriviceModel item) {
                helper.setText(R.id.item_Auto_Submit_TextView, item.getDevName());
                CheckBox checkBoxs = helper.getView(R.id.item_Auto_Submit_CheckBox);
                int position =helper.getLayoutPosition();
                if (submitDriviceIsChecked.size() > position) {
                    checkBoxs.setChecked(submitDriviceIsChecked.get(position));
                } else {
                    checkBoxs.setChecked(false);
                    submitDriviceIsChecked.add(checkBoxs.isChecked());
                }
            }
        };
        rvAutoSubmitDriverList.addItemDecoration(new RecyclerViewUnderLineUtil(getActivity()));
        rvAutoSubmitDriverList.setAdapter(mAdapter);
        rvAutoSubmitDriverList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                submitDriviceIsChecked.set(position, !submitDriviceIsChecked.get(position));
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData(Intent intent) {
        token = getIntent().getStringExtra(TOKEN);
        dataBean = (InstallOpenGetProjectListJs.DataBean) getIntent().getSerializableExtra(PRO_BEAN);
        textViewAutoSubmitProject.setText(dataBean.getProjectName());
        textViewAutoSubmitProject.setSelected(true);
        textViewAutoSubmitInstall.setText("安装情况：" + dataBean.getInstallNum());
        textViewAutoSubmitDistance.setText(dataBean.getActualDistance() + "KM");
        getDrviceList();
    }


    @OnClick({R.id.btn_Auto_Submit_Acceptance})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Auto_Submit_Acceptance:
                List<String> deviceIds = new ArrayList<>();
                for (int i = 0 ; i<submitDriviceIsChecked.size() ; i++){
                    if(submitDriviceIsChecked.get(i)){
                        deviceIds.add(mDataList.get(i).getDevId());
                    }
                }
               if(deviceIds.size() != 0){

                   showLoadDialog("正在加载中...");
                   NetworkApi.submitAuto(getActivity(), new AutoSubmitModel(dataBean.getProjectId(),deviceIds), token, new NetworkApi.NetCall() {
                       @Override
                       public void onSuccess(String result) {
                           dismissLoadDialog();
                           if (!result.equals("") && result != null) {
                               Gson gson = new Gson();
                               AutoResultModel jsonList = gson.fromJson(result, new TypeToken<AutoResultModel>() {
                               }.getType());
                               for (int i = 0; i<submitDriviceIsChecked.size() ; i++){
                                   submitDriviceIsChecked.set(i,false);
                               }
                               mAdapter.notifyDataSetChanged();
                               AutoCheckBeforeAcceptanceResultActivity.newInstance(AutoCheckBeforeAcceptanceSubmitActivity.this,token,jsonList.getId()+"",dataBean);

                           }
                       }

                       @Override
                       public void onFail(String msg) {
                           dismissLoadDialog();
                           ToastHelp.showToast(AutoCheckBeforeAcceptanceSubmitActivity.this, msg);
                       }
                   });
               }else{
                   ToastHelp.showToast(AutoCheckBeforeAcceptanceSubmitActivity.this, "请选择需要验收的设备！");
               }
                break;

        }
    }


    private void getDrviceList() {
        showLoadDialog("正在加载中...");
        NetworkApi.getAutoDeviceList(getActivity(), dataBean.getProjectId(), token, new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                dismissLoadDialog();
                if (!result.equals("") && result != null) {
                    Gson gson = new Gson();
                    List<AutoDriviceModel> jsonList = gson.fromJson(result, new TypeToken<List<AutoDriviceModel>>() {
                    }.getType());
                    submitDriviceIsChecked.clear();
                    mDataList.clear();
                    mDataList.addAll(jsonList);
                    mAdapter.notifyDataSetChanged();



                }
            }
            @Override
            public void onFail(String msg) {
                dismissLoadDialog();
                ToastHelp.showToast(AutoCheckBeforeAcceptanceSubmitActivity.this, msg);
            }
        });
    }




}
