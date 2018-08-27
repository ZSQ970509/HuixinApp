package com.hc.android.huixin.util;

import android.app.Activity;
import android.content.Intent;

import com.hc.android.huixin.TakePhotoInstallOpenInfoActivity;
import com.hc.android.huixin.DisassembleActivity;
import com.hc.android.huixin.DisassembleInfoActivity;
import com.hc.android.huixin.DisassembleSelectTypeActivity;
import com.hc.android.huixin.DisassembleUploadActivity;
import com.hc.android.huixin.TakePhotoInstallOpenActivity;
import com.hc.android.huixin.TakePhotoInstallOpenSelectTypeActivity;
import com.hc.android.huixin.TakePhotoInstallOpenUploadActivity;
import com.hc.android.huixin.bean.json.DevInstallJobCodeListJs;
import com.hc.android.huixin.bean.json.DisassembleGetProjectListJs;
import com.hc.android.huixin.bean.json.InstallOpenGetProjectListJs;
import com.hc.android.huixin.bean.json.RemoveJobCodeListJs;
import com.hc.android.huixin.bean.json.RemovedeviceGetProjectListJs;
import com.hc.android.huixin.network.SendDataBean;
import com.king.photo.activity.TakePhotoRemoveDeviceActivity;

import java.io.Serializable;
import java.util.List;

/**
 * 带参数的页面跳转
 */

public class JumpAc {
    public static final String KEY_PRO_DATA = "key_pro_data";
    public static final String KEY_PRO_TYPE_ID = "key_pro_type_id";//类型id
    public static final String KEY_PRO_TYPE_NAME = "key_pro_type_name";//类型名


    public static void toTakePhotoInstallOpenSelectTypeAc(Activity activity, InstallOpenGetProjectListJs.DataBean proData, String proTypeId, String proTypeName) {
        Intent intent = new Intent(activity, TakePhotoInstallOpenSelectTypeActivity.class);
        intent.putExtra(KEY_PRO_DATA, proData);
        intent.putExtra(KEY_PRO_TYPE_ID, proTypeId);
        intent.putExtra(KEY_PRO_TYPE_NAME, proTypeName);
        activity.startActivity(intent);
    }

    public static void toTakePhotoInstallOpenAc(Activity activity, InstallOpenGetProjectListJs.DataBean proData, String proTypeId, String proTypeName) {
        Intent intent = new Intent(activity, TakePhotoInstallOpenActivity.class);
        intent.putExtra(KEY_PRO_DATA, proData);
        intent.putExtra(KEY_PRO_TYPE_ID, proTypeId);
        intent.putExtra(KEY_PRO_TYPE_NAME, proTypeName);
        activity.startActivity(intent);
    }

    public static final String KEY_UPLOAD_WORD_ORDER_DATA = "key_upload_word_order_data";
    public static final String KEY_UPLOAD_WORD_ORDER_SELECT = "key_upload_word_order_select";

    public static void toTakePhotoInstallOpenUploadAc(Activity activity, InstallOpenGetProjectListJs.DataBean proData, int selectWordOrder, List<DevInstallJobCodeListJs.DataBean> wordOrder, String proTypeId, String proTypeName) {
        Intent intent = new Intent(activity, TakePhotoInstallOpenUploadActivity.class);
        intent.putExtra(KEY_PRO_DATA, proData);
        intent.putExtra(KEY_UPLOAD_WORD_ORDER_SELECT, selectWordOrder);
        intent.putExtra(KEY_UPLOAD_WORD_ORDER_DATA, (Serializable) wordOrder);
        intent.putExtra(KEY_PRO_TYPE_ID, proTypeId);
        intent.putExtra(KEY_PRO_TYPE_NAME, proTypeName);
        activity.startActivity(intent);
    }


    public static final String KEY_SHOW_INFO_DATA = "key_show_info_data";
    public static final int KEY_SHOW_INFO_REQUEST_CODE = 10086;

    public static void toCommonShowInfoAc(Activity activity, SendDataBean data, InstallOpenGetProjectListJs.DataBean proData, int selectWordOrder, List<DevInstallJobCodeListJs.DataBean> wordOrder) {
        Intent intent = new Intent(activity, TakePhotoInstallOpenInfoActivity.class);
        intent.putExtra(KEY_SHOW_INFO_DATA, data);
        intent.putExtra(KEY_PRO_DATA, proData);
        intent.putExtra(KEY_UPLOAD_WORD_ORDER_SELECT, selectWordOrder);
        intent.putExtra(KEY_UPLOAD_WORD_ORDER_DATA, (Serializable) wordOrder);
        activity.startActivityForResult(intent, KEY_SHOW_INFO_REQUEST_CODE);
    }


    public static void toDisassembleSelectTypeAc(Activity activity, DisassembleGetProjectListJs.DataBean proData, String proTypeId, String proTypeName) {
        Intent intent = new Intent(activity, DisassembleSelectTypeActivity.class);
        intent.putExtra(KEY_PRO_DATA, proData);
        intent.putExtra(KEY_PRO_TYPE_ID, proTypeId);
        intent.putExtra(KEY_PRO_TYPE_NAME, proTypeName);
        activity.startActivity(intent);
    }

    public static void toDisassembleAc(Activity activity, DisassembleGetProjectListJs.DataBean proData, String proTypeId, String proTypeName) {
        Intent intent = new Intent(activity, DisassembleActivity.class);
        intent.putExtra(KEY_PRO_DATA, proData);
        intent.putExtra(KEY_PRO_TYPE_ID, proTypeId);
        intent.putExtra(KEY_PRO_TYPE_NAME, proTypeName);
        activity.startActivity(intent);
    }


    public static void toDisassembleUploadAc(Activity activity, DisassembleGetProjectListJs.DataBean proData, int selectWordOrder, List<RemoveJobCodeListJs.DataBean> wordOrder, String proTypeId, String proTypeName) {
        Intent intent = new Intent(activity, DisassembleUploadActivity.class);
        intent.putExtra(KEY_PRO_DATA, proData);
        intent.putExtra(KEY_UPLOAD_WORD_ORDER_SELECT, selectWordOrder);
        intent.putExtra(KEY_UPLOAD_WORD_ORDER_DATA, (Serializable) wordOrder);
        intent.putExtra(KEY_PRO_TYPE_ID, proTypeId);
        intent.putExtra(KEY_PRO_TYPE_NAME, proTypeName);
        activity.startActivity(intent);
    }

    public static void toDisassembleShowInfoAc(Activity activity, SendDataBean data, DisassembleGetProjectListJs.DataBean proData, int selectWordOrder, List<RemoveJobCodeListJs.DataBean> wordOrder) {
        Intent intent = new Intent(activity, DisassembleInfoActivity.class);
        intent.putExtra(KEY_SHOW_INFO_DATA, data);
        intent.putExtra(KEY_PRO_DATA, proData);
        intent.putExtra(KEY_UPLOAD_WORD_ORDER_SELECT, selectWordOrder);
        intent.putExtra(KEY_UPLOAD_WORD_ORDER_DATA, (Serializable) wordOrder);
        activity.startActivityForResult(intent, KEY_SHOW_INFO_REQUEST_CODE);
    }


    public static void toTakePhotoRemoveDeviceAc(Activity activity, RemovedeviceGetProjectListJs.DataBean proData, String proTypeId, String proTypeName) {
        Intent intent = new Intent(activity, TakePhotoRemoveDeviceActivity.class);
        intent.putExtra(KEY_PRO_DATA, proData);
        intent.putExtra(KEY_PRO_TYPE_ID, proTypeId);
        intent.putExtra(KEY_PRO_TYPE_NAME, proTypeName);
        activity.startActivity(intent);
    }
}
