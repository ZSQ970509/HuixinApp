package com.hc.android.huixin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.google.gson.Gson;
import com.hc.android.huixin.base.BaseActivity;
import com.hc.android.huixin.bean.UploadWordOrderInfoBean;
import com.hc.android.huixin.bean.json.DevInstallJobCodeListJs;
import com.hc.android.huixin.bean.json.DisassembleGetProjectListJs;
import com.hc.android.huixin.bean.json.InstallOpenGetProjectListJs;
import com.hc.android.huixin.bean.json.RemoveJobCodeListJs;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.JumpAc;
import com.hc.android.huixin.view.CustomDialog;
import com.hc.android.huixin.view.DefaultDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 上传拆机单页面
 */
public class DisassembleUploadActivity extends BaseActivity {
    @BindView(R.id.installOpenUploadProjectNameTv)
    TextView mTvProName;
    @BindView(R.id.installOpenUploadWordOrderSp)
    Spinner mSpWordOrder;
    private DisassembleGetProjectListJs.DataBean mProData;
    private CommonAdapter<RemoveJobCodeListJs.DataBean> mAdapter;
    private String mProTypeName;
    private String mProTypeId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_take_photo_install_open_upload;
    }

    @Override
    protected void initView() {
        setToolBar("上传拆机单");
        mAdapter = new CommonAdapter<RemoveJobCodeListJs.DataBean>(getActivity(), R.layout.item_spinner) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, RemoveJobCodeListJs.DataBean item, int position) {
                helper.setText(R.id.tvItemSpinner, item.getRemoveJobCode());
            }
        };
        mSpWordOrder.setAdapter(mAdapter);
    }

    private List<RemoveJobCodeListJs.DataBean> mWordOrder;
    private int mSelectWordOrder;

    @Override
    protected void initData(Intent intent) {
        mProData = (DisassembleGetProjectListJs.DataBean) intent.getSerializableExtra(JumpAc.KEY_PRO_DATA);
        mTvProName.setText(mProData.getProjectName());
        mWordOrder = (List<RemoveJobCodeListJs.DataBean>) intent.getSerializableExtra(JumpAc.KEY_UPLOAD_WORD_ORDER_DATA);
        mSelectWordOrder = intent.getIntExtra(JumpAc.KEY_UPLOAD_WORD_ORDER_SELECT, 0);
        if (mWordOrder != null && mWordOrder.size() > 0) {
            mAdapter.clear();
            mAdapter.addAll(mWordOrder);
            mAdapter.notifyDataSetChanged();
            mSpWordOrder.setAdapter(mAdapter);
            mSpWordOrder.setSelection(mSelectWordOrder);
        } else {
            devInstallJobCodeList();
        }
        mProTypeId = intent.getStringExtra(JumpAc.KEY_PRO_TYPE_ID);
        mProTypeName = intent.getStringExtra(JumpAc.KEY_PRO_TYPE_NAME);
    }

    /**
     * 获取工单列表数据
     */
    private void devInstallJobCodeList() {
        showLoadDialog("正在加载数据...");
        NetworkApi.removeJobCodeList(getActivity(), mProData.getProjectId(), "1",new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                dismissLoadDialog();
                RemoveJobCodeListJs json = new Gson().fromJson(result, RemoveJobCodeListJs.class);
                mAdapter.clear();
                mAdapter.addAll(json.getData());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                dismissLoadDialog();
                showToast(msg);
            }
        });
    }

    /**
     * 上传
     */
    private void uploadWordOrder() {
        UploadWordOrderInfoBean infoBean = new UploadWordOrderInfoBean();
        infoBean.setProjectId(mProData.getProjectId());
        infoBean.setImgstr(ImageUtil.imgPathToStr(picturePath));
        infoBean.setProjectName(mProData.getProjectName());
        infoBean.setType(mProTypeName);
        infoBean.setTypeId(mProTypeId);
        infoBean.setJobId("");
        if (mAdapter == null || mAdapter.getData() == null || mAdapter.getData().size() <= 0 || mSpWordOrder == null)
            return;
        infoBean.setRemoveId(mAdapter.getItem(mSpWordOrder.getSelectedItemPosition()).getRemoveId());

        NetworkApi.uploadInsideDataAndImage(getActivity(), infoBean, new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                dismissLoadDialog();
                try {
                    JSONObject json = new JSONObject(result);
                    String msg = json.getString("msg");
                    CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                    builder.setMessage(msg);
                    builder.setTitle("提示");
                    builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getActivity().finish();
                        }
                    }).create().show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String msg) {
                dismissLoadDialog();
                DefaultDialog.showDialog(getActivity(), msg);
            }
        });
    }

    String pictureName;
    String picturePath;
    private int REQUEST_CODE = 1001;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                showLoadDialog("正在上传数据...");
                picturePath = ImageUtil.SAVE_PICTURE_PATH + File.separator + pictureName;
                new Handler().postDelayed(new Runnable() {//延迟0.3m秒 防止照片未完成保存，上传图片时空指针异常
                    @Override
                    public void run() {
                        uploadWordOrder();
                    }
                }, 300);
            } else {
                dismissLoadDialog();
                showToast("拍照失败");
            }
        }
    }

    @OnClick({R.id.installOpenUploadBtn})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.installOpenUploadBtn:
                if (mAdapter.getData() == null || mAdapter.getData().size() <= 0) {
                    showToast("没有工单");
                    return;
                }
                pictureName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance().getTime()) + ".jpg";
                ImageUtil.takePicture(getActivity(), pictureName, REQUEST_CODE);
                break;
        }
    }
}