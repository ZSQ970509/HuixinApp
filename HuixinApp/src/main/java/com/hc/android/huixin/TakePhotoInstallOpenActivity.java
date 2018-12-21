package com.hc.android.huixin;


import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.google.gson.Gson;
import com.hc.andorid.zxing.app.CaptureActivity;
import com.hc.android.huixin.base.BaseActivity;
import com.hc.android.huixin.bean.json.DevInstallJobCodeListJs;
import com.hc.android.huixin.bean.json.GetMaintenanceTypeJs;
import com.hc.android.huixin.bean.json.GetTotalTypeByCamTypeIdJs;
import com.hc.android.huixin.bean.json.InstallOpenGetProjectListJs;
import com.hc.android.huixin.bean.json.GetInstallDatCameraCamNameListJs;
import com.hc.android.huixin.bean.json.QueryUnitProjectJs;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.SendDataBean;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.JumpAc;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.CommonSelectDialog;
import com.hc.android.huixin.view.CustomDialog;
import com.hc.android.huixin.view.DefaultDialog;
import com.king.photo.model.JsonModel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TakePhotoInstallOpenActivity extends BaseActivity {
    @BindView(R.id.tvInstallOpenProName)
    TextView mTvProName;
    @BindView(R.id.spInstallOpenWordOrder)
    Spinner mSpWordOrder;

    @BindView(R.id.edtInstallOpenCameraBy)
    EditText mEdtCameraBy;

    @BindView(R.id.llInstallOpenDriverNum)
    LinearLayout mLlDriverNum;
    @BindView(R.id.edtInstallOpenDriverNum)
    EditText mEdtDriverNum;
    @BindView(R.id.btnInstallOpenDriverNumScan)
    Button mBtnDriverNumScan;

    @BindView(R.id.edtInstallOpenSpace)
    EditText mEdtSpace;

    @BindView(R.id.spInstallOpenUnitProject)
    Spinner mSpUnitProject;
    @BindView(R.id.spInstallOpenStatus)
    Spinner mSpInstallOpenStatus;

    @BindView(R.id.rgInstallOpenAccess)
    RadioGroup mRgAccess;
    @BindView(R.id.rdbInstallOpenAccessFalse)
    RadioButton mRdbAccessFalse;
    @BindView(R.id.rdbInstallOpenAccessTrue)
    RadioButton mRdbAccessTrue;

    @BindView(R.id.rgInstallOpenTakePhotoDevice)
    RadioGroup mRgTakePhotoDevice;
    @BindView(R.id.rdbInstallOpenTakePhotoDeviceFalse)
    RadioButton mRdbTakePhotoDeviceFalse;
    @BindView(R.id.rdbInstallOpenTakePhotoDeviceTrue)
    RadioButton mRdbTakePhotoDeviceTrue;

    @BindView(R.id.btnInstallOpenTakePhoto)
    Button mBtnTakePhoto;

    private InstallOpenGetProjectListJs.DataBean mProData;

    private String mProTypeId;//工程类型id
    private String mProTypeName;//工程类型名（安装开通）
    private CommonAdapter<DevInstallJobCodeListJs.DataBean> mAdapterWordOder;
    private CommonAdapter<QueryUnitProjectJs.DataBean> mAdapterUnitProject;
    private CommonAdapter<GetMaintenanceTypeJs.RowsBean> mAdapterInstallOpenStatus;

    private List<GetInstallDatCameraCamNameListJs.DataBean> mCameraByData = new ArrayList<>();
    private GetInstallDatCameraCamNameListJs.DataBean mCameraBySelect;//选中的设备名称（施工点位）

    @Override
    protected int getLayoutId() {
        return R.layout.activity_take_photo_install_open;
    }

    @Override
    protected void initView() {
        mAdapterWordOder = new CommonAdapter<DevInstallJobCodeListJs.DataBean>(getActivity(), R.layout.item_spinner) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, DevInstallJobCodeListJs.DataBean item, int position) {
                helper.setText(R.id.tvItemSpinner, item.getJobCode());
            }
        };
        mSpWordOrder.setAdapter(mAdapterWordOder);
        mSpWordOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapterWordOder.getData() != null && mAdapterWordOder.getData().size() > position) {
                    String jobId = mAdapterWordOder.getItem(position).getJobId();
                    String jobCodeName = mAdapterWordOder.getItem(position).getJobCodeName();
                    refreshCameraBy(null);
                    queryProjectCameraBy(jobId, jobCodeName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mAdapterUnitProject = new CommonAdapter<QueryUnitProjectJs.DataBean>(getActivity(), R.layout.item_spinner) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, QueryUnitProjectJs.DataBean item, int position) {
                helper.setText(R.id.tvItemSpinner, item.getItemName());
            }
        };
        mSpUnitProject.setAdapter(mAdapterUnitProject);
        mAdapterInstallOpenStatus = new CommonAdapter<GetMaintenanceTypeJs.RowsBean>(getActivity(), R.layout.item_spinner) {
            @Override
            public void onUpdate(BaseAdapterHelper helper, GetMaintenanceTypeJs.RowsBean item, int position) {
                helper.setText(R.id.tvItemSpinner, item.getText());
            }
        };
        mSpInstallOpenStatus.setAdapter(mAdapterInstallOpenStatus);
    }

    @Override
    protected void initData(Intent intent) {
        mProData = (InstallOpenGetProjectListJs.DataBean) intent.getSerializableExtra(JumpAc.KEY_PRO_DATA);
        mProTypeId = intent.getStringExtra(JumpAc.KEY_PRO_TYPE_ID);
        mProTypeName = intent.getStringExtra(JumpAc.KEY_PRO_TYPE_NAME) + "";
        setToolBar(mProTypeName);
        mTvProName.setText(mProData.getProjectName());
        devInstallJobCodeList();
        queryUnitProject();
        getInstallOpenStatus();
    }

    /**
     * 获取工单列表数据
     */
    private void devInstallJobCodeList() {
        NetworkApi.devInstallJobCodeList(getActivity(), mProData.getProjectId(),"0", new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                DevInstallJobCodeListJs json = new Gson().fromJson(result, DevInstallJobCodeListJs.class);
                mAdapterWordOder.clear();
                mAdapterWordOder.addAll(json.getData());
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
    private void queryProjectCameraBy(String jobId, String jobCodeName) {
        NetworkApi.getInstallDatCameraCamNameList(getActivity(), mProData.getProjectId(), jobId, jobCodeName, new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                GetInstallDatCameraCamNameListJs json = new Gson().fromJson(result, GetInstallDatCameraCamNameListJs.class);
                refreshCameraBy(json.getData());
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }

    /**
     * 获取单位工程列表
     */
    private void queryUnitProject() {
        NetworkApi.queryUnitProject(getActivity(), mProData.getProjectId(), new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                QueryUnitProjectJs json = new Gson().fromJson(result, QueryUnitProjectJs.class);
                refreshUnitPro(json.getData());
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
                refreshUnitPro(null);
            }
        });
    }


    /**
     * 获取安装接入情况列表
     */
    private void getInstallOpenStatus() {
        NetworkApi.getInstallOpenStatus(getActivity(), mProTypeId, new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                GetMaintenanceTypeJs json = new Gson().fromJson(result, GetMaintenanceTypeJs.class);
                refreshInstallOpenStatus(json.getRows());
            }

            @Override
            public void onFail(String msg) {
                refreshInstallOpenStatus(null);
                showToast(msg);
            }
        });
    }

    /**
     * 刷新安装接入情况列表
     *
     * @param dataBeans
     */
    private void refreshInstallOpenStatus(List<GetMaintenanceTypeJs.RowsBean> dataBeans) {
        mAdapterInstallOpenStatus.clear();
        GetMaintenanceTypeJs.RowsBean zero = new GetMaintenanceTypeJs.RowsBean();
        zero.setText("请选择");
        zero.setId("0");
        GetMaintenanceTypeJs.RowsBean one = new GetMaintenanceTypeJs.RowsBean();
        one.setText("正常");
        one.setId("-1");


        mAdapterInstallOpenStatus.add(zero);
        mAdapterInstallOpenStatus.add(one);
        if (dataBeans != null)
            mAdapterInstallOpenStatus.addAll(dataBeans);
        mAdapterInstallOpenStatus.notifyDataSetChanged();
    }

    /**
     * 刷新单位工程下拉框
     *
     * @param dataBeans
     */
    private void refreshUnitPro(List<QueryUnitProjectJs.DataBean> dataBeans) {
        mAdapterUnitProject.clear();
        QueryUnitProjectJs.DataBean zero = new QueryUnitProjectJs.DataBean();
        zero.setItemName("公共区域");
        zero.setItemId("");
        mAdapterUnitProject.add(zero);
        if (dataBeans != null)
            mAdapterUnitProject.addAll(dataBeans);
        mAdapterUnitProject.notifyDataSetChanged();
    }

    /**
     * 刷新单位工程下拉框
     *
     * @param dataBeans
     */
    private void refreshCameraBy(List<GetInstallDatCameraCamNameListJs.DataBean> dataBeans) {
        mCameraBySelect = null;
        mCameraByData.clear();
        mEdtCameraBy.setText("");
        mEdtDriverNum.setText("");
        mLlDriverNum.setVisibility(View.GONE);
        if (dataBeans != null)
            mCameraByData.addAll(dataBeans);

    }

    /**
     * 显示选择设备名称对话框（施工点位）
     */
    private void showSelectDialog() {
        CommonSelectDialog selectDialog = new CommonSelectDialog<GetInstallDatCameraCamNameListJs.DataBean>(getActivity(), mCameraByData) {
            @Override
            public void onCall(GetInstallDatCameraCamNameListJs.DataBean item) {
                if (item != null) {
                    mEdtCameraBy.setText(item.getNewCamName());
                    mCameraBySelect = item;
                    getTotalTypeByCamTypeId(item.getCamTypeId());
                }
            }

            @Override
            public String getItemName(GetInstallDatCameraCamNameListJs.DataBean item) {
                return item.getNewCamName();
            }
        };
        selectDialog.show();
    }

    /**
     * 根据设备名称(施工点位) 里的camTypeId 获取是否显示一体机编号
     */
    private void getTotalTypeByCamTypeId(String camTypeId) {
        NetworkApi.getTotalTypeByCamTypeId(getActivity(), camTypeId, new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                GetTotalTypeByCamTypeIdJs json = new Gson().fromJson(result, GetTotalTypeByCamTypeIdJs.class);
                if (json != null && "1".equals(json.getData())) {
                    mLlDriverNum.setVisibility(View.VISIBLE);
                    mEdtDriverNum.setText("");
                } else {
                    mLlDriverNum.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(String msg) {
                showToast(msg);
                mLlDriverNum.setVisibility(View.GONE);
            }
        });
    }

    String proLat;
    String proLng;
    String addr;

    private void checkInfo() {
        proLat = PreferenceUtil.getProjectLat();
        proLng = PreferenceUtil.getProjectLng();
        addr = PreferenceUtil.getProjectAddrStr();
        String payId = mAdapterInstallOpenStatus.getData().get(mSpInstallOpenStatus.getSelectedItemPosition()).getId();
        //判断工程是否需要绑定经纬度信息
        if ("233".equals(payId) && "230".equals(mProTypeId) && (TextUtils.isEmpty(mProData.getProjectLat()) || TextUtils.isEmpty(mProData.getProjectLng()))) {
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
        NetworkApi.uploadDataForSavePosition(getActivity(), mProData.getProjectId(), proLat, proLng, new NetworkApi.NetCall() {
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
        if (mAdapterWordOder == null || mAdapterWordOder.getData().isEmpty() || TextUtils.isEmpty(mAdapterWordOder.getItem(mSpWordOrder.getSelectedItemPosition()).getJobCode())) {
            ToastHelp.showToast(this, "工单不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mProData.getProjectName())) {
            ToastHelp.showToast(this, "工程名称不能为空！");
            return;
        }
        if (mCameraByData == null || mCameraByData.size() == 0) {
            showDialogIsToUploadWordOrderAc("没有未安装设备，是否上传安装验收单？", false);
            return;
        }
        if (mCameraBySelect == null) {
            ToastHelp.showToast(this, "请选择设备名称！");
            return;
        }
        if (TextUtils.isEmpty(mEdtCameraBy.getText().toString())) {
            ToastHelp.showToast(this, "设备名称不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mEdtSpace.getText().toString())) {
            ToastHelp.showToast(this, "安装位置不能为空！");
            return;
        }

        if (mAdapterUnitProject.getCount() > 1 && mCameraBySelect != null && "1".equals(mCameraBySelect.getFlag())) {
            if (mSpUnitProject.getSelectedItemPosition() == 0) {//当选中设备的flag为1 且 单位工程列表里的数量大于1时，单位工程不能选中公共区域(即第0个)
                showToast("该设备的单位工程不能是公共区域，请重新选择");
                return;
            }
        }
        if (mRdbAccessFalse.isChecked() && mSpInstallOpenStatus.getSelectedItemPosition() == 0) {
            showToast("请选择安装接入情况,不能是" + mAdapterInstallOpenStatus.getItem(mSpInstallOpenStatus.getSelectedItemPosition()).getText());
            return;
        }
        if (mLlDriverNum.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(mEdtDriverNum.getText().toString())) {
                ToastHelp.showToast(this, "设备序列号不能为空！");
                return;
            }
        }
        GetMaintenanceTypeJs.RowsBean status = mAdapterInstallOpenStatus.getItem(mSpInstallOpenStatus.getSelectedItemPosition());
        if(status.getId().equals("0")){
            ToastHelp.showToast(this, "请选择安装接入情况！");
            return;
        }
        uploadInfo();
    }


    private void uploadInfo() {

        final SendDataBean data = new SendDataBean();
        data.ProjectId = mProData.getProjectId();
        data.ProjectName = mProData.getProjectName();
        data.proTypeName = mProTypeName;
        data.UserName = PreferenceUtil.getName();
        data.UserId = PreferenceUtil.getUserId();
        data.ProjcLat = proLat;
        data.ProjcLng = proLng;
        data.addr = addr;
        data.Type = mProTypeName;
        data.CameraId = mCameraBySelect.getCamId();//设备id（施工点位id）
        data.CamName = mEdtCameraBy.getText().toString();//设备名（施工点位名）用户可以修改

        data.Progress = mRdbAccessTrue.isChecked() ? "1" : "0";//是否接入
        data.IsSaveLocation = 0;//默认0
        data.ispay = 0;//默认0
        data.projectTypeId = Integer.parseInt(mProTypeId);

        GetMaintenanceTypeJs.RowsBean status = mAdapterInstallOpenStatus.getItem(mSpInstallOpenStatus.getSelectedItemPosition());
        data.payId = Integer.parseInt(status.getId()); //安装接入情况id
        data.Note = status.getText();//安装接入情况名

        data.DriverNum = mEdtDriverNum.getText().toString();//一体机编号

        //单位工程
        QueryUnitProjectJs.DataBean unitPro = mAdapterUnitProject.getItem(mSpUnitProject.getSelectedItemPosition());
        data.UnitProjectId = unitPro.getItemId();
        data.UnitProjectName = unitPro.getItemName();
        data.InstallPlace = mEdtSpace.getText().toString();//安装位置

        data.DriverJingDu = "";
        data.DriverWeiDu = "";
        data.DriverAlpha = "";
        data.ImgStr = "";
        data.jobId = mAdapterWordOder.getItem(mSpWordOrder.getSelectedItemPosition()).getJobId();
        data.jobName = mAdapterWordOder.getItem(mSpWordOrder.getSelectedItemPosition()).getJobCode();
        if (mRdbTakePhotoDeviceTrue.isChecked()) {
            JumpAc.toCommonShowInfoAc(getActivity(), data, mProData, mSpWordOrder.getSelectedItemPosition(), mAdapterWordOder.getData());
        } else {
            sendInstallOpenData(data);
        }

    }

    private void sendInstallOpenData(final SendDataBean data) {
        final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送数据...");
        dialog.show();
        NetworkApi.sendIntallOpenData(getActivity(), data, new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
                try {
                    JSONObject dataJson = new JSONObject(result);
                    JsonModel jsonModel = new JsonModel(dataJson.getString("result"), dataJson.getString("msg"));
                    fun(jsonModel, data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String msg) {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (TextUtils.isEmpty(msg)) {
                    DefaultDialog.showDialog(getActivity(), "网络异常，请重试！");
                } else {
                    DefaultDialog.showDialogIntentErr(getActivity(), msg, msg.substring(msg.indexOf(":") + 1, msg.indexOf(":") + 6));
                }

            }
        });
    }

    private void fun(JsonModel result, final SendDataBean data) {
        final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送数据...");
        if (result.getResult().equals("0")) {
            Log.e("errcode", (result.getMsg()));
            Log.e("errcode", (result.getMsg().indexOf(":") + 1 + ""));
            Log.e("errcode", (result.getMsg().indexOf("！") + ""));
            Log.e("errcode", (result.getMsg().lastIndexOf("！") + ""));
            Log.e("errcode", result.getMsg().substring(result.getMsg().indexOf(":") + 1, result.getMsg().indexOf(":") + 6));
            DefaultDialog.showDialogIntentErr(getActivity(), result.getMsg(), result.getMsg().substring(result.getMsg().indexOf(":") + 1, result.getMsg().indexOf(":") + 6));
        } else if (result.getResult().equals("1")) {
            showDialogIsToUploadWordOrderAc(result.getMsg(), true);
        } else if (result.getResult().equals("2")) {
            new AlertDialog.Builder(getActivity()).setTitle("提示")
                    .setMessage("省站信息不全仅保存到HMS平台，是否继续配置？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialog.show();
                            new Thread() {
                                public void run() {
                                    final JsonModel result = NetworkApi.sendIntallOpenDataToSZ(getActivity(), data);
                                    if (result == null) {
                                        Looper.prepare();
                                        DefaultDialog.showDialog(getActivity(), "网络异常，请重试！");
                                        if (null != dialog && dialog.isShowing())
                                            dialog.dismiss();
                                        Looper.loop();
                                        return;
                                    }
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.cancel();
                                            if (result.getResult().equals("0")) {
                                                Log.e("errcode", result.getMsg().substring(result.getMsg().indexOf(":") + 1, result.getMsg().lastIndexOf("!")));
                                                DefaultDialog.showDialogIntentErr(getActivity(), result.getMsg(), result.getMsg().substring(result.getMsg().indexOf(":") + 1, result.getMsg().lastIndexOf("!")));
                                            } else if (result.getResult().equals("1")) {
                                                showDialogIsToUploadWordOrderAc(result.getMsg(), true);
                                            } else {
                                                showDialogIsToUploadWordOrderAc(result.getMsg(), true);
                                            }
                                        }
                                    });

                                }
                            }.start();
                        }
                    }).setCancelable(false).create().show();

        } else if (result.getResult().equals("3")) {
            showDialogIsToUploadWordOrderAc("安装成功，但未开通设备！", true);
        } else {
            DefaultDialog.showDialog(getActivity(), "网络超时，请重新上传！");
        }

    }

    /**
     * 提示用户上传安装验收单
     */
    private void showDialogIsToUploadWordOrderAc(String msg, boolean isInit) {
        if (isInit)//是否初始化页面
            initOnFinish();
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage(msg);
        builder.setTitle("提示");
        builder.setPositiveButton("上传安装验收单", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                JumpAc.toTakePhotoInstallOpenUploadAc(getActivity(), mProData, mSpWordOrder.getSelectedItemPosition(), mAdapterWordOder.getData(), mProTypeId, mProTypeName);
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
        refreshCameraBy(null);
        refreshUnitPro(null);
        refreshInstallOpenStatus(null);
        mEdtSpace.setText("");
        devInstallJobCodeList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x11) {//扫描二维码结果
            if (data != null) {
                mEdtDriverNum.setText(data.getStringExtra("SCAN_RESULT"));
            }
        } else if (requestCode == JumpAc.KEY_SHOW_INFO_REQUEST_CODE) {
            initOnFinish();
        }
    }

    @OnClick({R.id.btnInstallOpenCameraBy, R.id.btnInstallOpenTakePhoto, R.id.btnInstallOpenDriverNumScan})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInstallOpenCameraBy:
                showSelectDialog();
                break;
            case R.id.btnInstallOpenTakePhoto:
                CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                builder.setMessage("是否提交");
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
            case R.id.btnInstallOpenDriverNumScan:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, 0x11);
                break;
        }
    }
}