package com.hc.android.huixin;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.google.gson.Gson;
import com.hc.android.huixin.base.BaseActivity;
import com.hc.android.huixin.bean.MultiChoiceDialogBean;
import com.hc.android.huixin.bean.json.DisassembleGetProjectListJs;
import com.hc.android.huixin.bean.json.GetRemoveDatCameraCamNameListJs;
import com.hc.android.huixin.bean.json.RemoveJobCodeListJs;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.SendDataBean;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.JumpAc;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.CustomDialog;
import com.hc.android.huixin.view.DefaultDialog;
import com.king.photo.model.JsonModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 拆机
 */
public class DisassembleActivity extends BaseActivity {
    @BindView(R.id.tvProjectName)
    TextView mTvProName;
    @BindView(R.id.spDisassembleWordOrder)
    Spinner mSpWordOrder;

    @BindView(R.id.edtDisassembleCameraBy)
    EditText mEdtCameraBy;

    @BindView(R.id.edtDisassembleStatus)
    EditText mEdtStatus;

    @BindView(R.id.rgIsPay)
    RadioGroup mRgIsPay;
    @BindView(R.id.rdbPayFalse)
    RadioButton mRdbPayFalse;
    @BindView(R.id.rdbPayTrue)
    RadioButton mRdbPayTrue;

    @BindView(R.id.rgIsPhotoDev)
    RadioGroup mRgPhotoDev;
    @BindView(R.id.rdbPhotoDevFalse)
    RadioButton mRdbPhotoDevFalse;
    @BindView(R.id.rdbPhotoDevTrue)
    RadioButton mRdbPhotoDevTrue;

    @BindView(R.id.btnDisassembleTakePhoto)
    Button mBtnTakePhoto;

    private DisassembleGetProjectListJs.DataBean mProData;

    private String mProTypeId;//工程类型id
    private String mProTypeName;//工程类型名（安装开通）
    private CommonAdapter<RemoveJobCodeListJs.DataBean> mAdapterWordOder;

    private List<GetRemoveDatCameraCamNameListJs.DataBean> mCameraByData = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_disassemble;
    }

    @Override
    protected void initView() {
        mProData = (DisassembleGetProjectListJs.DataBean) getIntent().getSerializableExtra(JumpAc.KEY_PRO_DATA);
        mProTypeId = getIntent().getStringExtra(JumpAc.KEY_PRO_TYPE_ID);
        mProTypeName = getIntent().getStringExtra(JumpAc.KEY_PRO_TYPE_NAME) + "";
        mTvProName.setText(mProData.getProjectName());
        setToolBar(mProTypeName);
        mAdapterWordOder = new CommonAdapter<RemoveJobCodeListJs.DataBean>(getActivity(), R.layout.item_spinner) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, RemoveJobCodeListJs.DataBean item, int position) {
                helper.setText(R.id.tvItemSpinner, item.getRemoveJobCode());
            }
        };
        mSpWordOrder.setAdapter(mAdapterWordOder);
        mSpWordOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapterWordOder.getData() != null && mAdapterWordOder.getData().size() > position) {
                    String removeId = mAdapterWordOder.getItem(position).getRemoveId();
                    refreshCameraBy();
                    getRemoveDatCameraCamNameList(removeId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    protected void initData(Intent intent) {
        removeJobCodeList();
    }

    /**
     * 获取工单列表数据
     */
    private void removeJobCodeList() {
        NetworkApi.removeJobCodeList(getActivity(), mProData.getProjectId(), "0",new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                RemoveJobCodeListJs json = new Gson().fromJson(result, RemoveJobCodeListJs.class);
                mAdapterWordOder.clear();
                mAdapterWordOder.addAll(json.getData());
                mAdapterWordOder.notifyDataSetChanged();
                if (!mAdapterWordOder.getData().isEmpty()) {
                    mSpWordOrder.setAdapter(mAdapterWordOder);
                }
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }

    /**
     * 获取设备名称(以前的施工点位)列表
     */
    private void getRemoveDatCameraCamNameList(String jobId) {
        NetworkApi.getRemoveDatCameraCamNameList(getActivity(), mProData.getProjectId(), jobId, new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                GetRemoveDatCameraCamNameListJs json = new Gson().fromJson(result, GetRemoveDatCameraCamNameListJs.class);
                mCameraByData.clear();
                mCameraByData.addAll(json.getData());
                refreshCameraBy();
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }

    /**
     * 刷新设备名称下拉框
     */
    private void refreshCameraBy() {
        mEdtCameraBy.setText("");
        if (mCameraByData != null && mCameraByData.size() > 0) {
            String[] tempData = new String[mCameraByData.size()];
            for (int i = 0; i < mCameraByData.size(); i++) {
                tempData[i] = mCameraByData.get(i).getCamName();
            }
            mCameraDialogData.setData(tempData);
        }
    }

    MultiChoiceDialogBean mCameraDialogData = new MultiChoiceDialogBean();
    AlertDialog mCameraDialog;//

    /**
     * 显示选择设备名称对话框（施工点位）
     */
    private void showCameraSelectDialog() {
        if (mCameraDialog != null && mCameraDialog.isShowing())
            mCameraDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMultiChoiceItems(mCameraDialogData.getData(), mCameraDialogData.getIsSelect(), null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (mCameraDialogData == null || mCameraDialogData.getData() == null || mCameraDialogData.getData().length <= 0)
                    return;
                mCameraDialogData.clearSelectData();
                mCameraDialogData.clearSelectDataId();
                for (int i = 0; i < mCameraDialogData.getData().length; i++) {
                    if (mCameraDialog.getListView().getCheckedItemPositions().get(i)) {
                        mCameraDialogData.addSelectData(mCameraByData.get(i).getCamName());
                        mCameraDialogData.addSelectDataId(mCameraByData.get(i).getRCameraId());
                        mCameraDialogData.getIsSelect()[i] = true;
                    } else {
                        mCameraDialogData.getIsSelect()[i] = false;
                    }
                }
                mEdtCameraBy.setText(mCameraDialogData.getSelectData());
            }
        });
        mCameraDialog = builder.create();
        mCameraDialog.show();
    }

    String mProLat;
    String mProLng;
    String mAddr;

    private void checkInfo() {
        mProLat = PreferenceUtil.getProjectLat();
        mProLng = PreferenceUtil.getProjectLng();
        mAddr = PreferenceUtil.getProjectAddrStr();
        //判断工程是否需要绑定经纬度信息
        if ("230".equals(mProTypeId) && (TextUtils.isEmpty(mProData.getProjectLat()) || TextUtils.isEmpty(mProData.getProjectLng()))) {
            showBindProjectLocationDialog();
        } else {
            takePhoto();
        }
    }

    private void showBindProjectLocationDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage("发现当前工程还未绑定经纬度信息，是否绑定？" + "\n当前位置:" + PreferenceUtil.getProjectAddrStr());
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置你的操作事项
                // 绑定 项目定位
                uploadDataForSavePosition();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                takePhoto();
            }
        });
        builder.create().show();
    }

    private void uploadDataForSavePosition() {
        final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在保存...");
        NetworkApi.uploadDataForSavePosition(getActivity(), mProData.getProjectId(), mProLat, mProLng, new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                showToast("发送成功");
                dialog.cancel();
                takePhoto();
            }

            @Override
            public void onFail(String msg) {
                showToast("发送失败");
                dialog.cancel();
                takePhoto();
            }
        });
    }

    private void takePhoto() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastHelp.showToast(this, "手机没有安装SD卡，不能拍照");
            return;
        }
        if (mAdapterWordOder == null || mAdapterWordOder.getData().isEmpty() || TextUtils.isEmpty(mAdapterWordOder.getItem(mSpWordOrder.getSelectedItemPosition()).getRemoveId())) {
            ToastHelp.showToast(this, "工单不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mProData.getProjectName())) {
            ToastHelp.showToast(this, "工程名称不能为空！");
            return;
        }
        if (mCameraByData == null || mCameraByData.size() == 0) {
            showDialogIsToUploadWordOrderAc("没有未拆机设备，是否上传拆机单？");
            return;
        }
        if (TextUtils.isEmpty(mEdtCameraBy.getText().toString())) {
            ToastHelp.showToast(this, "设备名称不能为空！");
            return;
        }
        uploadInfo();
    }

    private void uploadInfo() {
        SendDataBean data = new SendDataBean();
        data.ResponsibilitySubject = "";
        data.ImgstrDev = "";//设备照片（用户选中否则为空）
        //工单照片
        data.ImgstrAculvert = "";//工单照片（弃用，统一在上传工单页面上传）
        data.jobName = mAdapterWordOder.getItem(mSpWordOrder.getSelectedItemPosition()).getRemoveJobCode();//工单名
        data.jobId = mAdapterWordOder.getItem(mSpWordOrder.getSelectedItemPosition()).getRemoveId();//工单id
        data.Note = mEdtStatus.getText().toString();//情况备注
        data.ProjectId = mProData.getProjectId();
        data.ProjectName = mProData.getProjectName();
        data.UserName = PreferenceUtil.getName();
        data.ProjcLat = mProLat;
        data.ProjcLng = mProLng;
        data.addr = mAddr;
        data.Type = mProTypeName;//类型名 拆机
        data.proTypeName = mProTypeName;//类型名 拆机
        data.projectTypeId = Integer.parseInt(mProTypeId);//类型id 拆机为171
        data.CameraId = mCameraDialogData.getSelectDataId();//选中的设备id（多个用'|'区分）
        data.CamName = mCameraDialogData.getSelectData();//选中的设备名（多个用'|'区分）
        data.Progress = "1";//世标要求隐藏"是否拆除",固定传1
        data.IsSaveLocation = 0;//默认0
        data.ispay = mRdbPayFalse.isSelected() ? 0 : 1;//是否赔偿 0否 1是


        data.payId = 194;
        data.removeId = mAdapterWordOder.getItem(mSpWordOrder.getSelectedItemPosition()).getRemoveId();//拆机单id
        if (mRdbPhotoDevTrue.isChecked()) {
            JumpAc.toDisassembleShowInfoAc(getActivity(), data, mProData, mSpWordOrder.getSelectedItemPosition(), mAdapterWordOder.getData());
        } else {
            sendDisassembleData(data);
        }

    }

    private void sendDisassembleData(final SendDataBean data) {
        showLoadDialog("正在发送数据...");
        NetworkApi.uploadRemoveDataAndImage(getActivity(), data, new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                dismissLoadDialog();
                try {
                    JSONObject dataJson = new JSONObject(result);
                    JsonModel jsonModel = new JsonModel(dataJson.getString("result"), dataJson.getString("msg"));
                    showDialogIsToUploadWordOrderAc(jsonModel.getMsg());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String msg) {
                dismissLoadDialog();
                if (TextUtils.isEmpty(msg)) {
                    DefaultDialog.showDialog(getActivity(), "网络异常，请重试！");
                } else {
                    DefaultDialog.showDialogIntentErr(getActivity(), msg, msg.substring(msg.indexOf(":") + 1, msg.indexOf(":") + 6));
                }
            }
        });
    }

    /**
     * 提示用户上传拆机单
     */
    private void showDialogIsToUploadWordOrderAc(String msg) {
        initOnFinish();
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage(msg);
        builder.setTitle("提示");
        builder.setPositiveButton("上传拆机单", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                JumpAc.toDisassembleUploadAc(getActivity(), mProData, mSpWordOrder.getSelectedItemPosition(), mAdapterWordOder.getData(), mProTypeId, mProTypeName);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void initOnFinish() {
        refreshCameraBy();
        mEdtStatus.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == JumpAc.KEY_SHOW_INFO_REQUEST_CODE) {
            initOnFinish();
        }
    }

    @OnClick({R.id.btnDisassembleCameraBy, R.id.btnDisassembleTakePhoto})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDisassembleCameraBy:
                showCameraSelectDialog();
                break;
            case R.id.btnDisassembleTakePhoto:
                CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                builder.setMessage("是否上传");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        checkInfo();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
        }
    }
}