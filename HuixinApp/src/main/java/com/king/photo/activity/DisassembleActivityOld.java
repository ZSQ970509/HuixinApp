package com.king.photo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.hc.android.huixin.R;
import com.hc.android.huixin.bean.json.DisassembleGetProjectListJs;
import com.hc.android.huixin.network.CameraItem;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectItem;
import com.hc.android.huixin.network.SendDataBean;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.JumpAc;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.CustomDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DisassembleActivityOld extends Activity implements View.OnClickListener {
    private View mTakePhotoView;
    private View mShowPhotoView;
    private ImageView mPhotoImage;
    private Handler mHandler = new Handler();
    private Spinner mSpWorkOrderList;
    private EditText mProjectCameraBy;
    private String mPhotoUri, mWorkOrderPhotoUri;
    private ArrayList<ProjectItem> wordOrderData = new ArrayList<ProjectItem>();
    DisassembleGetProjectListJs.DataBean mProData;
    String mProTypeId;
    private TextView mTextViewProjectName;
    private TextView mTextViewProjectType;
    private TextView mTextViewProgressNote;
    private TextView mTextViewLocalPostion;
    private TextView mTextViewTime;
    private TextView mTextViewSelectCamera;
    private TextView mTextViewUsers;
    private EditText mEditProgressNote;
    private Button btnTakePhoto;
    private String mProjcLat = "0.0";
    private String mProjcLng = "0.0";
    private String mUsers = "";
    // 设备名称
    private String mSelectCamera = "";
    private String mProgress = "1";
    private int isPay = 0;
    private int mSavePostion = 0;
    private int projectTypeId = 171;
    private String removeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disassemble_old);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        finish();
    }

    private boolean isTakePicDevice = false;

    private void initView() {
        mProData = (DisassembleGetProjectListJs.DataBean) getIntent().getSerializableExtra(JumpAc.KEY_PRO_DATA);
        mProTypeId = getIntent().getStringExtra(JumpAc.KEY_PRO_TYPE_ID);
        String title = getIntent().getStringExtra(JumpAc.KEY_PRO_TYPE_NAME);
        ((TextView) findViewById(R.id.title)).setText(title + "");
        findViewById(R.id.regulatory_back).setOnClickListener(this);
        findViewById(R.id.send_photo).setOnClickListener(this);
        mTakePhotoView = findViewById(R.id.send_photo_view);
        mShowPhotoView = findViewById(R.id.show_photo_view);
        btnTakePhoto = (Button) findViewById(R.id.take_photo);
        btnTakePhoto.setOnClickListener(this);
        mPhotoImage = (ImageView) findViewById(R.id.photo_image);
        mSpWorkOrderList = (Spinner) findViewById(R.id.spinner_work_order);
        mProjectCameraBy = (EditText) findViewById(R.id.edit_project_camera_by);
        mEditProgressNote = (EditText) findViewById(R.id.edittext_progress_note);
        mTextViewProjectName = (TextView) findViewById(R.id.txt_project_name);
        mTextViewProjectType = (TextView) findViewById(R.id.txt_project_type);
        mTextViewProjectType.setText("拆机");
        mTextViewProgressNote = (TextView) findViewById(R.id.txt_progress_note);
        mTextViewLocalPostion = (TextView) findViewById(R.id.txt_local_position);
        mTextViewTime = (TextView) findViewById(R.id.txt_time);
        mTextViewSelectCamera = (TextView) findViewById(R.id.txt_select_camera);
        mTextViewUsers = (TextView) findViewById(R.id.txt_usesr);
        RadioGroup group = (RadioGroup) this.findViewById(R.id.progress);
        RadioGroup isPhotoGroup = (RadioGroup) this.findViewById(R.id.isphoto);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                if (arg1 == R.id.progress_no_over) {
                    mProgress = "0";
                } else {
                    mProgress = "1";
                }
            }
        });
        isPhotoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.isphoto_no_over) {
                    isTakePicDevice = false;
                    btnTakePhoto.setText("上传信息");
                } else {
                    isTakePicDevice = true;
                    btnTakePhoto.setText("点击拍照");
                }
            }
        });
        RadioGroup payRadioGroup = (RadioGroup) this.findViewById(R.id.ispay);
        payRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                if (arg1 == R.id.ispay_no_over) {
                    isPay = 0;
                } else {
                    isPay = 1;
                }
            }
        });
        initProjectCameraBy();
        if (projectTypeId == 230 && (TextUtils.isEmpty(mProData.getProjectLat()) || TextUtils.isEmpty(mProData.getProjectLng()))) {
            isBind = true;
        } else {
            isBind = false;
        }
        initWorkOrder();
        initCameraByData();
        initWordOrderData();
    }

//    private void searchProject() {
//        final String name = mKeyWork.getText().toString();
//        if (TextUtils.isEmpty(name)) {
//            ToastHelp.showToast(DisassembleActivityOld.this, "关键字不能为空！");
//            return;
//        }
//        mProjectData.clear();
//
//        final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在获取工程名称...");
//        dialog.show();
//        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                return true;
//            }
//        });
//
//        NetworkApi.queryProjectData(DisassembleActivityOld.this, name, String.valueOf(projectTypeId),
//                new INetCallback() {
//                    @Override
//                    public void onCallback(final boolean value, final String result) {
//                        dialog.cancel();
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (value) {
//                                    mProjectData.addAll(NetworkApi.parstToProjectList(result));
//                                    final int count = mProjectData.size();
//                                    String[] dataList = new String[count];
//                                    for (int i = 0; i < count; i++) {
//                                        dataList[i] = mProjectData.get(i).ProjectName;
//                                    }
//                                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DisassembleActivityOld.this, R.layout.simple_spinner_item, dataList);
//                                    adapter.setDropDownViewResource(R.layout.simple_spinner_item);
//
//                                    initCameraByData();
//                                    initWordOrderData();
//                                    mSpProjectNameList.setAdapter(adapter);
//                                    if (count == 0) {
//                                        ToastHelp.showToast(DisassembleActivityOld.this, "未搜索到工程数据！");
//                                    }
//
//                                } else {
//
//                                    initCameraByData();
//                                    initWordOrderData();
//                                    ToastHelp.showToast(DisassembleActivityOld.this, "获取工程名称失败！");
//                                }
//                            }
//                        });
//
//                    }
//                });
//    }

    /**
     * 点位数据
     */
    private String[] items = new String[]{};
    private boolean[] checkedItems = new boolean[]{};
    private ListView multiChoiceListView;
    private String mSelectCameraId = "";

    //工单选择
    private void initWorkOrder() {
        mSpWorkOrderList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final ProjectItem item = wordOrderData.get(mSpWorkOrderList.getSelectedItemPosition());
                //需要修改接口
                removeId = item.RemoveId + "";
                NetworkApi.queryProjectCameraByWorkOrder(mProData.getProjectId(), item.RemoveId + "", new INetCallback() {
                    @Override
                    public void onCallback(boolean value, String result) {
                        if (value) {
                            mCameraByList.clear();
                            try {
                                JSONObject json = new JSONObject(result);
                                String data = json.getString("result");
                                if (data.equals("1")) {
                                    JSONArray camList = json.getJSONArray("data");
                                    for (int i = 0; i < camList.length(); i++) {
                                        CameraItem cameraItem = new CameraItem();
                                        cameraItem.CamId = camList.getJSONObject(i).getString("RCameraId");
                                        cameraItem.CamName = camList.getJSONObject(i).getString("CamName");
                                        mCameraByList.add(cameraItem);
                                    }
                                } else {
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            mCameraByList.addAll(NetworkApi.parstToCameraList(result));
                            items = new String[mCameraByList.size()];
                            checkedItems = new boolean[mCameraByList.size()];
                            for (int i = 0; i < mCameraByList.size(); i++) {
                                items[i] = mCameraByList.get(i).CamName;
                                checkedItems[i] = false;
                            }
                            Log.e("asd", items.toString());
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mProjectCameraBy.setText("");
                                    mSelectCameraId = "";
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 点位选择
     */
    private void initProjectCameraBy() {
        findViewById(R.id.camera_by).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DisassembleActivityOld.this);
                builder.setMultiChoiceItems(items, checkedItems, null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String msg = "";
                        mSelectCamera = "";
                        mSelectCameraId = "";
                        for (int i = 0; i < items.length; i++) {
                            if (multiChoiceListView.getCheckedItemPositions().get(i)) {
                                if (!TextUtils.isEmpty(msg)) {
                                    msg += "|";
                                    mSelectCameraId += "|";
                                }
                                msg += mCameraByList.get(i).CamName;
                                mSelectCameraId += mCameraByList.get(i).CamId;
                            }
                        }
                        mSelectCamera = msg;
                        mProjectCameraBy.setText(msg);
                    }
                });
                AlertDialog dialog = builder.create();
                multiChoiceListView = dialog.getListView();
                dialog.show();
            }
        });
    }

    private ArrayList<CameraItem> mCameraByList = new ArrayList<CameraItem>();
    private boolean isBind = false;

    ArrayAdapter<String> mWorkOrderAdapter;
    private String[] mWordOrderDataList = new String[]{};

    // 获取拆机工单号
    private class getRemoveJobCodeAsyncTask extends AsyncTask<String, Void, ArrayList<ProjectItem>> {

        @Override
        protected ArrayList<ProjectItem> doInBackground(String... params) {
            return new NetworkApi().removeJobCodeList(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<ProjectItem> data) {
            initProjectCameraBy();
            wordOrderData.clear();
            initCameraByData();

            mWordOrderDataList = new String[wordOrderData.size()];
            mWorkOrderAdapter = new ArrayAdapter<String>(DisassembleActivityOld.this, R.layout.simple_spinner_item, mWordOrderDataList);
            mWorkOrderAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
            if (data == null) {
                mSpWorkOrderList.setAdapter(mWorkOrderAdapter);
                return;
            }
            wordOrderData.addAll(data);
            mWordOrderDataList = new String[wordOrderData.size()];
            for (int i = 0; i < wordOrderData.size(); i++) {
                mWordOrderDataList[i] = wordOrderData.get(i).RemoveJobCode;
            }
            mWorkOrderAdapter = new ArrayAdapter<String>(DisassembleActivityOld.this, R.layout.simple_spinner_item, mWordOrderDataList);
            mSpWorkOrderList.setAdapter(mWorkOrderAdapter);

        }
    }


    /**
     * 判断该项目原来是否绑定、是否为已经到达、定位是否开启
     */
    private void Update() {
        getLocalPosition();
        // 已经绑定、已经到达现场
        if (isBind) {
            showBindProjectLocationDialog();
        } else {// 未绑定
            if (isTakePicDevice) {
                takePhoto(REQUEST_CODE_IMG);
            } else {
                showDialogUpload();
            }
        }
    }

    private void showBindProjectLocationDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(DisassembleActivityOld.this);
        builder.setMessage("发现当前工程还未绑定经纬度信息，是否绑定？" + "\n当前位置:" + PreferenceUtil.getProjectAddrStr());
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置你的操作事项
                // 绑定 项目定位
                savePosition();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                takePhoto(REQUEST_CODE_IMG);
            }
        });
        builder.create().show();
    }

    private void savePosition() {
        final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在保存...");
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkApi.uploadDataForSavePosition(DisassembleActivityOld.this, mProData.getProjectId(), mProjcLat, mProjcLng,
                        new INetCallback() {
                            @Override
                            public void onCallback(boolean value, String result) {
                                LogUtil.logD("sendData onCallback" + result);
                                if (value) {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastHelp.showToast(DisassembleActivityOld.this, "发送成功！");
                                        }
                                    });
                                } else {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastHelp.showToast(DisassembleActivityOld.this, "发送失败！");
                                        }
                                    });
                                }
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.cancel();
                                        // 进行拍照
                                        takePhoto(REQUEST_CODE_IMG);
                                    }
                                });
                            }
                        });
            }
        }).start();
    }

    private void sendPhoto() {
        final String imgWorkOrderStr = ImageUtil.imgPathToStr(mWorkOrderPhotoUri);
        final String imgStr = ImageUtil.imgPathToStr(mPhotoUri);
        if (!HttpUtil.networkIsAvailable(this)) {
            ToastHelp.showToast(this, "手机没有网络连接！");
            return;
        }
        final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送图片...");
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SendDataBean data = new SendDataBean();
                data.ResponsibilitySubject = "";
                data.ImgstrDev = imgStr;
                //工单照片
                data.ImgstrAculvert = imgWorkOrderStr;
                data.Note = mEditProgressNote.getText().toString();
                data.ProjectId = mProData.getProjectId();
                data.ProjectName = mProData.getProjectName();
                data.UserName = PreferenceUtil.getName();
                data.ProjcLat = mProjcLat;
                data.ProjcLng = mProjcLng;
                data.Type = "拆机";
                data.CameraId = mSelectCameraId;
//                data.Progress = mProgress;
                data.Progress = "1";//世标要求隐藏"是否拆除",固定传1
                data.IsSaveLocation = mSavePostion;
                data.ispay = isPay;
                data.projectTypeId = projectTypeId;
                data.payId = 194;
                data.removeId = removeId;
                NetworkApi.sendDisassembleData(DisassembleActivityOld.this, data, new INetCallback() {
                    @Override
                    public void onCallback(boolean value, String result) {
                        LogUtil.logD("sendData onCallback" + result);
                        if (value) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastHelp.showToast(DisassembleActivityOld.this, "发送成功！");
                                }
                            });
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 2000);
                        } else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mPhotoImage.setImageBitmap(null);
                                    mShowPhotoView.setVisibility(View.GONE);
                                    mTakePhotoView.setVisibility(View.VISIBLE);
                                    ToastHelp.showToast(DisassembleActivityOld.this, "发送失败！");
                                }
                            });
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                dialog.cancel();
                            }
                        });
                    }
                });
            }
        }).start();
    }


    private final int REQUEST_CODE_IMG = 1001;//拍照请求码
    private final int REQUEST_CODE_WORK_ORDER_IMG = 1002;//拍工单的请求码

    private void takePhoto(int requestCode) {
        getLocalPosition();

        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            ToastHelp.showToast(this, "手机没有安装SD卡，不能拍照");
            return;
        }
        if (TextUtils.isEmpty(mProData.getProjectName())) {
            ToastHelp.showToast(this, "工程名称不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mSelectCamera) && projectTypeId != 230) {
            ToastHelp.showToast(this, "设备名称不能为空！");
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String dirName = Environment.getExternalStorageDirectory() + "/huixin";
        File f = new File(dirName);
        if (!f.exists()) {
            f.mkdir();
        }
        String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance().getTime()) + ".jpg";
        if (requestCode == REQUEST_CODE_WORK_ORDER_IMG) {
            mWorkOrderPhotoUri = dirName + "/" + name;
        } else {
            mPhotoUri = dirName + "/" + name;
        }
        File file = new File(dirName, name);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, requestCode);
    }

    private void getLocalPosition() {
        mProjcLat = PreferenceUtil.getProjectLat();
        mProjcLng = PreferenceUtil.getProjectLng();
        String addr = PreferenceUtil.getProjectAddrStr();
        mTextViewLocalPostion.setText("经度：" + mProjcLng + " 纬度：" + mProjcLat + "\n地址：" + addr);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.e("huixin", "走了save");
        super.onSaveInstanceState(savedInstanceState); // 实现父类方法 放在最后
        // 防止拍照后无法返回当前activity
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e("huixin", "走了return");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            try {
                if (requestCode == REQUEST_CODE_WORK_ORDER_IMG) {
                    if (!(new File(mWorkOrderPhotoUri)).exists()) {
                        Log.e("onActivityResult", "mPhotoUri not exists!");
                        ToastHelp.showToast(this, "拍照失败！");
                        return;
                    }
                    //发送工单拍照
                    sendPhoto();
                } else if (requestCode == REQUEST_CODE_IMG) {
                    if (!(new File(mPhotoUri)).exists()) {
                        Log.e("onActivityResult", "mPhotoUri not exists!");
                        ToastHelp.showToast(this, "拍照失败！");
                        return;
                    }
                    mShowPhotoView.setVisibility(View.VISIBLE);
                    mTakePhotoView.setVisibility(View.GONE);
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.ARGB_8888)
                            .cacheInMemory(false).cacheOnDisc(true).build();
                    ImageLoader.getInstance().displayImage("file://" + mPhotoUri, mPhotoImage, options);
                    mTextViewProjectName.setText(mProData.getProjectName());
                    SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                    String time = df.format(new Date());
                    mTextViewTime.setText(time);
                    mTextViewUsers.setText(mUsers);
                    mTextViewSelectCamera.setText(mSelectCamera);
                    mTextViewProgressNote.setText(mEditProgressNote.getText().toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastHelp.showToast(this, "拍照失败，请重新拍照！");
            }
        } else {
            ToastHelp.showToast(this, "拍照失败，请重新拍照！");
        }
    }


    /**
     * 重置设备名称数据
     */
    private void initCameraByData() {
        mCameraByList.clear();
        items = new String[]{};
        checkedItems = new boolean[]{};
        mProjectCameraBy.setText("");
    }

    /**
     * 重置工单选择数据
     */
    private void initWordOrderData() {
        wordOrderData.clear();
        mWordOrderDataList = new String[]{};
        if (mWorkOrderAdapter != null) {
            mWorkOrderAdapter = new ArrayAdapter<String>(DisassembleActivityOld.this, R.layout.simple_spinner_item, mWordOrderDataList);
            mSpWorkOrderList.setAdapter(mWorkOrderAdapter);
            mWorkOrderAdapter.notifyDataSetChanged();
        }
        wordOrderData.clear();
    }

    public void showDialogUpload() {
        //拍照工单
        CustomDialog.Builder builder = new CustomDialog.Builder(DisassembleActivityOld.this);
        builder.setMessage("是否上传拆机单？");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                takePhoto(REQUEST_CODE_WORK_ORDER_IMG);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                sendPhoto();
            }
        });

        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regulatory_back:
                back();
                break;
            case R.id.take_photo:
                // 先判断是否项目定位，没有就绑定定位，然后在takePhoto();
                Update();
                break;
            case R.id.send_photo:
                showDialogUpload();
                break;
//            case R.id.search:
//                searchProject();
//                break;
            default:
                break;
        }
    }
}
