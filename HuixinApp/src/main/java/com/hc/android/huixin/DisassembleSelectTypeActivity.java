package com.hc.android.huixin;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.hc.android.huixin.base.BaseActivity;
import com.hc.android.huixin.bean.json.DisassembleGetProjectListJs;
import com.hc.android.huixin.bean.json.GetIsPlayByProjIdJs;
import com.hc.android.huixin.bean.json.InstallOpenGetProjectListJs;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.JumpAc;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 拆机选择类型页面
 */
public class DisassembleSelectTypeActivity extends BaseActivity {
    @BindView(R.id.SelectTypeInstallOpenBtn)
    Button btn;
    @BindView(R.id.SelectTypeUploadBtn)
    Button btnWordOrder;
    private DisassembleGetProjectListJs.DataBean mProData;
    private String mProTypeName = "";//安装开通页面的标题
    private String mProTypeId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_take_photo_install_open_select_type;
    }

    @Override
    protected void initView() {
        setToolBar("选择类型");
        btn.setText("拆机");
        btnWordOrder.setText("上传拆机单");
    }

    @Override
    protected void initData(Intent intent) {
        if (intent != null) {
            mProData = (DisassembleGetProjectListJs.DataBean) intent.getSerializableExtra(JumpAc.KEY_PRO_DATA);
            mProTypeId = intent.getStringExtra(JumpAc.KEY_PRO_TYPE_ID);
            mProTypeName = intent.getStringExtra(JumpAc.KEY_PRO_TYPE_NAME);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIsPlayByProjId();
    }

    private void getIsPlayByProjId() {
        showLoadDialog("加载中...");
        NetworkApi.getIsPlayByProjId(getActivity(), mProData.getProjectId(), mProTypeId, new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                dismissLoadDialog();
                GetIsPlayByProjIdJs js = new Gson().fromJson(result, GetIsPlayByProjIdJs.class);
                if ("1".equals(js.getResult())) {
                    btn.setClickable(true);
                    btn.setBackgroundResource(R.drawable.selector_login_btn_bg);
                } else {
                    btn.setClickable(false);
                    btn.setBackgroundResource(R.drawable.btn_bg_click_no);
                }
            }

            @Override
            public void onFail(String msg) {
                dismissLoadDialog();
                showToast(msg);
            }
        });
    }

    @OnClick({R.id.SelectTypeInstallOpenBtn, R.id.SelectTypeUploadBtn})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.SelectTypeInstallOpenBtn:
                JumpAc.toDisassembleAc(getActivity(), mProData, mProTypeId, mProTypeName);
                break;
            case R.id.SelectTypeUploadBtn:
                JumpAc.toDisassembleUploadAc(getActivity(), mProData, 0, null, mProTypeId, mProTypeName);
                break;
        }
    }
}