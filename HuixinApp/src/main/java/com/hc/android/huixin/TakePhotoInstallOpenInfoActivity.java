package com.hc.android.huixin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hc.android.huixin.base.BaseActivity;
import com.hc.android.huixin.bean.json.DevInstallJobCodeListJs;
import com.hc.android.huixin.bean.json.InstallOpenGetProjectListJs;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.SendDataBean;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.JumpAc;
import com.hc.android.huixin.view.CustomDialog;
import com.hc.android.huixin.view.DefaultDialog;
import com.king.photo.model.JsonModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 安装开通上传信息页面
 */
public class TakePhotoInstallOpenInfoActivity extends BaseActivity {
    @BindView(R.id.photo_image)
    ImageView ivShowImg;
    @BindView(R.id.txt_local_position)
    TextView tvLocal;
    @BindView(R.id.txt_time)
    TextView tvTime;
    @BindView(R.id.txt_project_name)
    TextView tvProName;
    @BindView(R.id.tvWordOrderName)
    TextView tvWordOrderName;
    @BindView(R.id.txt_select_camera)
    TextView tvCameraName;
    @BindView(R.id.txt_project_type)
    TextView tvProType;
    @BindView(R.id.txt_driver_num)
    TextView tvDriverNum;
    @BindView(R.id.llDriverNum)
    LinearLayout llDriverNum;
    @BindView(R.id.txt_install_space)
    TextView tvInstallSpace;
    @BindView(R.id.txt_unit_project)
    TextView tvUnitProject;
    @BindView(R.id.txt_progress_note)
    TextView tvProgressNote;
    private int REQUEST_CODE = 1001;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_show_info;
    }

    String pictureName;
    SendDataBean mData;
    private InstallOpenGetProjectListJs.DataBean mProData;
    private List<DevInstallJobCodeListJs.DataBean> mWordOrder;
    private int mSelectWordOrder;

    @Override
    protected void initView() {
        setToolBar("上传安装开通信息");
        mData = (SendDataBean) getIntent().getSerializableExtra(JumpAc.KEY_SHOW_INFO_DATA);
        pictureName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance().getTime()) + ".jpg";
        mProData = (InstallOpenGetProjectListJs.DataBean) getIntent().getSerializableExtra(JumpAc.KEY_PRO_DATA);
        mWordOrder = (List<DevInstallJobCodeListJs.DataBean>) getIntent().getSerializableExtra(JumpAc.KEY_UPLOAD_WORD_ORDER_DATA);
        mSelectWordOrder = getIntent().getIntExtra(JumpAc.KEY_UPLOAD_WORD_ORDER_SELECT, 0);
        ImageUtil.takePicture(getActivity(), pictureName, REQUEST_CODE);

        tvLocal.setText(mData.addr + "");
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String time = df.format(new Date());
        tvTime.setText(time);
        tvProName.setText(mData.ProjectName);
        tvWordOrderName.setText(mData.jobName);
        tvCameraName.setText(mData.CamName);
        tvProType.setText(mData.proTypeName);
        if (TextUtils.isEmpty(mData.DriverNum)) {
            llDriverNum.setVisibility(View.GONE);
        } else {
            llDriverNum.setVisibility(View.VISIBLE);
            tvDriverNum.setText(mData.DriverNum);
        }
        tvInstallSpace.setText(mData.InstallPlace);
        tvUnitProject.setText(mData.UnitProjectName);
        tvProgressNote.setText(mData.Note);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri imageUri = null;
                if (data != null) {
                    if (data.hasExtra("data")) {
                        Bitmap bitmap = data.getParcelableExtra("data");//处理缩略图
                        ivShowImg.setImageBitmap(bitmap);
                    }
                } else {
                    Log.e("照相", "data为空！");
                    new Handler().postDelayed(new Runnable() {//延迟0.3m秒 防止照片未完成保存，上传图片时空指针异常
                        @Override
                        public void run() {
//                            ivShowImg.setImageURI(Uri.fromFile(new File(ImageUtil.SAVE_PICTURE_PATH + File.separator + pictureName)));
                            ImageUtil.showImg(ImageUtil.SAVE_PICTURE_PATH + File.separator + pictureName, ivShowImg);
                            mData.ImgStr = ImageUtil.imgPathToStr(ImageUtil.SAVE_PICTURE_PATH + File.separator + pictureName);
                        }
                    }, 300);
                }
            } else {
                showToast("拍照失败");
            }
        }
    }


    private void sendInstallOpenData() {
        final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送数据...");
        dialog.show();
        NetworkApi.sendIntallOpenData(getActivity(), mData, new NetworkApi.NetCall() {
            @Override
            public void onSuccess(String result) {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
//                SendInstallOpenDataJs dataJs = new Gson().fromJson(result, SendInstallOpenDataJs.class);
                JSONObject dataJson = null;
                try {
                    dataJson = new JSONObject(result);
                    JsonModel jsonModel = new JsonModel(dataJson.getString("result"), dataJson.getString("msg"));
                    fun(jsonModel, mData);
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
            DefaultDialog.showDialogIntentErr(getActivity(), result.getMsg(), result.getMsg()
                    .substring(result.getMsg().indexOf(":") + 1, result.getMsg().indexOf(":") + 6));
        } else if (result.getResult().equals("1")) {
            showDialogIsToUploadWordOrderAc(result.getMsg());
        } else if (result.getResult().equals("2")) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("提示")
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
                                                Log.e("errcode",
                                                        result.getMsg().substring(
                                                                result.getMsg().indexOf(":") + 1, result
                                                                        .getMsg().lastIndexOf("!")));
                                                DefaultDialog.showDialogIntentErr(
                                                        getActivity(), result.getMsg(),
                                                        result.getMsg().substring(
                                                                result.getMsg().indexOf(":") + 1, result
                                                                        .getMsg().lastIndexOf("!")));
                                            } else if (result.getResult().equals("1")) {
                                                showDialogIsToUploadWordOrderAc(result.getMsg());
                                                // DefaultDialog.showDialogIsFinish(TakePhotoInstallOpenActivity.this,
                                                // result   .getMsg());
                                            } else {
                                                DefaultDialog.showDialog(getActivity(),
                                                        result.getMsg());
                                            }

                                        }
                                    });

                                }
                            }.start();
                        }
                    }).setCancelable(false)
                    .create()
                    .show();

        } else if (result.getResult().equals("3")) {
            showDialogIsToUploadWordOrderAc("安装成功，但未开通设备！");
            // DefaultDialog.showDialogIsFinish(TakePhotoInstallOpenActivity.this,
            // "安装成功，但未开通设备！");
        } else {
            DefaultDialog.showDialog(getActivity(), "网络超时，请重新上传！");
        }

    }

    /**
     * 提示用户上传安装验收单
     */
    private void showDialogIsToUploadWordOrderAc(String msg) {
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage(msg);
        builder.setTitle("提示");
        builder.setPositiveButton("上传安装验收单", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                JumpAc.toTakePhotoInstallOpenUploadAc(getActivity(), mProData, mSelectWordOrder, mWordOrder, mData.projectTypeId + "", mData.proTypeName);
                getActivity().setResult(JumpAc.KEY_SHOW_INFO_REQUEST_CODE);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().setResult(JumpAc.KEY_SHOW_INFO_REQUEST_CODE);
                finish();
            }
        });
        builder.create().show();
    }

    @OnClick({R.id.send_photo})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_photo:
                sendInstallOpenData();
                break;
        }
    }
}