package com.hc.android.huixin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.hc.android.huixin.bean.json.DisassembleGetProjectListJs;
import com.hc.android.huixin.bean.json.InstallOpenGetProjectListJs;
import com.hc.android.huixin.bean.json.RemoveJobCodeListJs;
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
 * 上传拆机信息（有拍设备图）页面
 */
public class DisassembleInfoActivity extends BaseActivity {
    @BindView(R.id.ivDisassemblePhotoImage)
    ImageView ivShowImg;
    @BindView(R.id.tvDisassembleLocation)
    TextView tvLocal;
    @BindView(R.id.tvDisassembleTime)
    TextView tvTime;
    @BindView(R.id.tvDisassembleProjectName)
    TextView tvProName;
    @BindView(R.id.tvDisassembleWordOrderName)
    TextView tvWordOrderName;
    @BindView(R.id.tvDisassembleCameraName)
    TextView tvCameraName;
    @BindView(R.id.tvDisassembleProjectType)
    TextView tvProType;
    @BindView(R.id.tvDisassembleNote)
    TextView tvProgressNote;
    private int REQUEST_CODE = 1001;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_disassemble_show_info;
    }

    String pictureName;
    SendDataBean mData;
    private DisassembleGetProjectListJs.DataBean mProData;
    private List<RemoveJobCodeListJs.DataBean> mWordOrder;
    private int mSelectWordOrder;

    @Override
    protected void initView() {
        setToolBar("上传拆机信息");
        mData = (SendDataBean) getIntent().getSerializableExtra(JumpAc.KEY_SHOW_INFO_DATA);
        mProData = (DisassembleGetProjectListJs.DataBean) getIntent().getSerializableExtra(JumpAc.KEY_PRO_DATA);
        mWordOrder = (List<RemoveJobCodeListJs.DataBean>) getIntent().getSerializableExtra(JumpAc.KEY_UPLOAD_WORD_ORDER_DATA);
        mSelectWordOrder = getIntent().getIntExtra(JumpAc.KEY_UPLOAD_WORD_ORDER_SELECT, 0);

        pictureName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance().getTime()) + ".jpg";
        ImageUtil.takePicture(getActivity(), pictureName, REQUEST_CODE);

        tvLocal.setText(mData.addr + "");
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String time = df.format(new Date());
        tvTime.setText(time);
        tvProName.setText(mData.ProjectName);
        tvWordOrderName.setText(mData.jobName);
        tvCameraName.setText(mData.CamName);
        tvProType.setText(mData.proTypeName);
        tvProgressNote.setText(mData.Note);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                new Handler().postDelayed(new Runnable() {//延迟0.3m秒 防止照片未完成保存，上传图片时空指针异常
                    @Override
                    public void run() {
                        ImageUtil.showImg(ImageUtil.SAVE_PICTURE_PATH + File.separator + pictureName, ivShowImg);
                        mData.ImgstrDev = ImageUtil.imgPathToStr(ImageUtil.SAVE_PICTURE_PATH + File.separator + pictureName);
                    }
                }, 300);
            } else {
                showToast("拍照失败");
            }
        }
    }


    private void sendInstallOpenData() {
        showLoadDialog("正在发送数据...");
        NetworkApi.uploadRemoveDataAndImage(getActivity(), mData, new NetworkApi.NetCall() {
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
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setMessage(msg);
        builder.setTitle("提示");
        builder.setPositiveButton("上传拆机单", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                JumpAc.toDisassembleUploadAc(getActivity(), mProData, mSelectWordOrder, mWordOrder, mData.projectTypeId + "", mData.proTypeName);
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

    @OnClick({R.id.btnDisassembleSend})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDisassembleSend:
                sendInstallOpenData();
                break;
        }
    }
}