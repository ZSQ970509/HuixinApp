package com.king.photo.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.hc.android.huixin.R;
import com.hc.android.huixin.bean.json.DisassembleGetProjectListJs;
import com.hc.android.huixin.bean.json.RemovedeviceGetProjectListJs;
import com.hc.android.huixin.network.CameraItemNew;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.MaintenanceTypeItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectMoveItem;
import com.hc.android.huixin.network.ResponsibilitySubjectItem;
import com.hc.android.huixin.network.SendRemoveDataBean;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.JumpAc;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;

public class TakePhotoRemoveDeviceActivity extends Activity implements OnClickListener {

    private ScrollView mTakePhotoSv;// 拍照页面
    private ScrollView mShowPhotoSv;// 显示照片页面

    /*拍照页面*/
    private TextView mTakeProjectNameTv;// 工程名称
    private Spinner mTakePhotoRemoveDeviceSp;
    private Spinner mTakeCameraBySp;//设备名称下拉框
    private EditText mTakePhotNoteEdt;// 备注
    /*拍照页面*/

    /* 显示照片页面*/
    private ImageView mShowPhotoIv;// 拍照的图片
    private TextView mShowPhotoLocalTv;// 当前位置和经纬度

    private TextView mShowPhotoProjectNameTv;
    private TextView mShowPhotoProjectType;
    private TextView mShowPhotoProgressNoteTv;

    private TextView mShowPhotoTimeTv;
    private TextView mShowPhotoSelectCameraTv;
    /*显示照片页面*/

    private Handler mHandler = new Handler();
    private String mPhotoUri;// 图片得相对地址
    ArrayList<ResponsibilitySubjectItem> ResponsibilitySubjectList = new ArrayList<ResponsibilitySubjectItem>();
    private String mRemoveDevice = "";// 移机情况
    private ArrayList<CameraItemNew> mCameraList = new ArrayList<CameraItemNew>();
    private final String COMPLETE = "完成";
    private final String NOT_REMOVE = "不能移机";
    private String mProjectType = "";
    private String mProjectTypeId = "";
    private RemovedeviceGetProjectListJs.DataBean mProData;//项目相关数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo_remove);
        initView();
    }

    private void initView() {
        mProjectType = getIntent().getStringExtra(JumpAc.KEY_PRO_TYPE_NAME) + "";
        mProjectTypeId = getIntent().getStringExtra(JumpAc.KEY_PRO_TYPE_ID);
        mProData = (RemovedeviceGetProjectListJs.DataBean) getIntent().getSerializableExtra(JumpAc.KEY_PRO_DATA);

        ((TextView) findViewById(R.id.title)).setText(mProjectType);
        mTakeProjectNameTv = (TextView) findViewById(R.id.tv_take_photo_project_name);
        mTakeProjectNameTv.setText(mProData.getProjectName() + "");

        findViewById(R.id.regulatory_back).setOnClickListener(this);
        findViewById(R.id.show_photo_btn).setOnClickListener(this);
        findViewById(R.id.take_photo_btn).setOnClickListener(this);

        mTakePhotoSv = (ScrollView) findViewById(R.id.take_photo_sv);
        mTakeCameraBySp = (Spinner) findViewById(R.id.take_photo_project_project_camera_by_sp);
        mTakePhotoRemoveDeviceSp = (Spinner) findViewById(R.id.take_photo_remove_device_sp);

        mTakePhotNoteEdt = (EditText) findViewById(R.id.take_photo_project_note_edt);
        mShowPhotoSv = (ScrollView) findViewById(R.id.show_photo_sv);
        mShowPhotoIv = (ImageView) findViewById(R.id.show_photo_image_img);
        mShowPhotoLocalTv = (TextView) findViewById(R.id.show_photo_local_tv);
        mShowPhotoTimeTv = (TextView) findViewById(R.id.show_photo_time_tv);
        mShowPhotoProjectNameTv = (TextView) findViewById(R.id.show_photo_project_name_tv);

        mShowPhotoSelectCameraTv = (TextView) findViewById(R.id.show_photo_select_camera_tv);
        mShowPhotoProjectType = (TextView) findViewById(R.id.show_photo_project_type);
        mShowPhotoProjectType.setText(mProjectType);
        mShowPhotoProgressNoteTv = (TextView) findViewById(R.id.show_photo_progress_note_tv);
        initTakeProjectNameSp();
        initRemoveStatus();
        initProgressNote();
    }

    private void initProgressNote() {
        new ProgressNoteAsyncTask().execute();
    }

    // 获取进度描述
    private class ProgressNoteAsyncTask extends AsyncTask<Void, Void, ArrayList<MaintenanceTypeItem>> {

        @Override
        protected ArrayList<MaintenanceTypeItem> doInBackground(Void... params) {
            return new NetworkApi().GetMaintenanceType(String.valueOf(mProjectTypeId));
        }

        @Override
        protected void onPostExecute(final ArrayList<MaintenanceTypeItem> data) {
            if (data == null) {
                return;
            }
            String[] nameData = new String[data.size()]; // 在维护里加一个其他
            for (int i = 0; i < nameData.length; i++) {
                nameData[i] = data.get(i).text;
            }
        }
    }

    /**
     * 初始化移机情况下拉框
     */
    private void initRemoveStatus() {
        final String[] REMOVE_STATUS = {COMPLETE, NOT_REMOVE}; // 备注
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(TakePhotoRemoveDeviceActivity.this,
                R.layout.simple_spinner_item, REMOVE_STATUS);
        mTakePhotoRemoveDeviceSp.setAdapter(adapter);
        mTakePhotoRemoveDeviceSp.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < REMOVE_STATUS.length)
                    mRemoveDevice = REMOVE_STATUS[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regulatory_back:
                finish();
                // back();
                break;
            case R.id.take_photo_btn:
                // 先判断是否项目定位，没有就绑定定位，然后在takePhoto();
                Update();
                break;
            case R.id.show_photo_btn:
                sendPhoto();
                break;
        }
    }

    /**
     * 判断该项目原来是否绑定、是否为已经到达、定位是否开启
     */
    private void Update() {
        getLocalPosition();
        takePhoto();
    }

    private void takePhoto() {
        getLocalPosition();

        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            ToastHelp.showToast(this, "手机没有安装SD卡，不能拍照");
            return;
        }
        if (TextUtils.isEmpty(mTakeProjectNameTv.getText().toString())) {
            ToastHelp.showToast(this, "工程名称不能为空！");
            return;
        }

        if (TextUtils.isEmpty(mRemoveDevice)) {
            ToastHelp.showToast(this, "移机情况不能为空！");
            return;
        }
        if (null == mCameraList || mCameraList.isEmpty()) {
            ToastHelp.showToast(this, "设备名称不能为空！");
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String dirName = Environment.getExternalStorageDirectory() + "/huixin";
        File f = new File(dirName);
        if (!f.exists()) {
            f.mkdir();
        }

        // if (mTakePhotProjectSituationEdt.getText().toString() == null
        // || "".equals(mTakePhotProjectSituationEdt.getText().toString())) {
        // ToastHelp.showToast(this, "请填写备注！");
        // return;
        // }
        mShowPhotoProgressNoteTv.setText(mTakePhotNoteEdt.getText());

        String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance().getTime()) + ".jpg";
        mPhotoUri = dirName + "/" + name;
        File file = new File(dirName, name);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, 0);
    }

    private void getLocalPosition() {
        String lat = PreferenceUtil.getProjectLat();
        String lng = PreferenceUtil.getProjectLng();
        String addr = PreferenceUtil.getProjectAddrStr();
        mShowPhotoLocalTv.setText("经度：" + lng + " 纬度：" + lat + "\n地址：" + addr);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        /*
         * Log.e("huixin", "走了save"); savedInstanceState.putString("msg_con",
		 * htmlsource); savedInstanceState.putString("msg_camera_picname",
		 * camera_picname);
		 */

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
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            try {
                if (!(new File(mPhotoUri)).exists()) {
                    Log.e("onActivityResult", "mPhotoUri not exists!");
                    ToastHelp.showToast(this, "拍照失败！");
                    return;
                }

                mTakePhotoSv.setVisibility(View.GONE);
                mShowPhotoSv.setVisibility(View.VISIBLE);
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.ARGB_8888)
                        .cacheInMemory(false).cacheOnDisc(true).build();
                ImageLoader.getInstance().displayImage("file://" + mPhotoUri, mShowPhotoIv, options);
//                ProjectMoveItem item = mProjectData.get(mTakeProjectNameSp.getSelectedItemPosition());
                mShowPhotoProjectNameTv.setText(mProData.getProjectName());
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                String time = df.format(new Date());
                mShowPhotoTimeTv.setText(time);
//				mShowPhotoUsesrTv.setText(mUserNames);
                mShowPhotoSelectCameraTv.setText(mCameraList.get(mTakeCameraBySp.getSelectedItemPosition()).CamName);

            } catch (Exception e) {
                e.printStackTrace();
                ToastHelp.showToast(this, "拍照失败，请重新拍照！");
            }
        } else {
            ToastHelp.showToast(this, "拍照失败，请重新拍照！");
        }
    }


    private void initTakeProjectNameSp() {
        NetworkApi.queryProjectCameraByNew(mProData.getProjectId(), mProData.getDshwId(), new INetCallback() {
            @Override
            public void onCallback(boolean value, String result) {
                if (value) {
                    ArrayList<CameraItemNew> data = NetworkApi.parstToCameraNewList(result);
                    if (data == null || data.isEmpty())
                        return;
                    mCameraList.clear();
                    mCameraList.addAll(data);
                    String[] dataList = new String[mCameraList.size()];
                    for (int i = 0; i < mCameraList.size(); i++) {
                        dataList[i] = mCameraList.get(i).CamName;
                    }
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            TakePhotoRemoveDeviceActivity.this, R.layout.simple_spinner_item, dataList);
                    adapter.setDropDownViewResource(R.layout.simple_spinner_item);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mTakeCameraBySp.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }

    private void sendPhoto() {
        if (!HttpUtil.networkIsAvailable(this)) {
            ToastHelp.showToast(this, "手机没有网络连接！");
            return;
        }
        final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送图片...");
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                ProjectMoveItem item = mProjectData.get(mTakeProjectNameSp.getSelectedItemPosition());
                Bitmap bitmap = ImageUtil.scaleImage(mPhotoUri, 500f, 500f);

                if (bitmap == null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastHelp.showToast(TakePhotoRemoveDeviceActivity.this, "发送失败！");
                            dialog.cancel();
                        }
                    });
                    return;
                }
                String imgStr = ImageUtil.convertIconToString(bitmap);
                bitmap.recycle();
                bitmap = null;

                SendRemoveDataBean data = new SendRemoveDataBean();
                if (mRemoveDevice.equals(COMPLETE)) {
                    data.typeId = "21";
                } else {
                    data.typeId = "20";
                }

                data.userId = PreferenceUtil.getUserId();
                data.note = mTakePhotNoteEdt.getText().toString();

                data.projId = mProData.getProjectId();
                data.dshwId = mProData.getDshwId();
                data.camId = mCameraList.get(mTakeCameraBySp.getSelectedItemPosition()).CamId;
                data.imgstr = imgStr;
                data.type = mProjectType;
                data.projectName = mProData.getProjectName();
                data.userMobile = PreferenceUtil.getName();

                data.projcLng = PreferenceUtil.getProjectLng();
                data.projcLat = PreferenceUtil.getProjectLat();
                data.provinceId = PreferenceUtil.getProjectAddrStr();
                NetworkApi.sendInsideNewData(TakePhotoRemoveDeviceActivity.this, data, new INetCallback() {
                    @Override
                    public void onCallback(boolean value, String result) {
                        LogUtil.logD("sendData onCallback" + result);
                        if (value) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastHelp.showToast(TakePhotoRemoveDeviceActivity.this, "发送成功！");
                                }
                            });
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
//									finish();
                                    mTakePhotoSv.setVisibility(View.VISIBLE);
                                    mShowPhotoSv.setVisibility(View.GONE);
                                    mShowPhotoIv.setImageBitmap(null);
                                    mTakeCameraBySp.setAdapter(null);
                                    mTakePhotNoteEdt.setText("");
                                    initTakeProjectNameSp();
                                }
                            }, 2000);
                        } else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mShowPhotoIv.setImageBitmap(null);
                                    mTakePhotoSv.setVisibility(View.VISIBLE);
                                    mShowPhotoSv.setVisibility(View.GONE);
                                    ToastHelp.showToast(TakePhotoRemoveDeviceActivity.this, "发送失败！");
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
}
