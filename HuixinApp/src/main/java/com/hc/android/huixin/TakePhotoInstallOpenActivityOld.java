package com.hc.android.huixin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hc.andorid.zxing.app.CaptureActivity;
import com.hc.android.huixin.bean.json.DevInstallJobCodeListJs;
import com.hc.android.huixin.network.CameraItem;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.MaintenanceTypeItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.PlaceMessgaeItem;
import com.hc.android.huixin.network.ProjectItem;
import com.hc.android.huixin.network.SendDataBean;
import com.hc.android.huixin.network.UnitProjectItem;
import com.hc.android.huixin.network.UsersItem;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.ChoiceDialog;
import com.hc.android.huixin.view.CustomDialog;
import com.hc.android.huixin.view.DefaultDialog;
import com.king.photo.model.JsonModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TakePhotoInstallOpenActivityOld extends Activity implements OnClickListener {
    private ImageButton regulatoryBack, messageRegulatoryBack;
    private View mTakePhotoView;
    private View mShowPhotoView;
    private ImageView mPhotoImage;
    private Handler mHandler = new Handler();
    private Spinner mProjectNameList;
    private Spinner mUnitProjectList;
    private Spinner mSpWordOrder;

    private EditText mProjectCameraBy;
    private String mPhotoUri;
    private ArrayList<ProjectItem> mData = new ArrayList<ProjectItem>();
    private ArrayList<UnitProjectItem> mUnitList = new ArrayList<UnitProjectItem>();
    private TextView mTextViewProjectName;
    private TextView mTextViewProjectType;
    private TextView mTextViewProgressNote;
    private TextView mTextViewLocalPostion;
    private TextView mTextViewTime;
    private TextView mTextViewSelectCamera;
    private TextView mTextViewUsers;
    private TextView mTextViewDriverJingDu;
    private TextView mTextViewDriverWeiDu;
    private TextView mTextViewDriverAlpha;
    private TextView mTextViewDriverNum;
    private TextView mTextViewInstallSpace;
    private TextView mTextViewUnitProject;

    private TextView mTextViewJingDu;
    private TextView mTextViewWeiDu;
    private TextView mTextViewAlpha;
    private EditText mEditUsers;
    private EditText mKeyWork;
    private EditText mEditInstallSpace;
    private EditText mEditDriverNum;
    private String mProjcLat = "0.0";
    private String mProjcLng = "0.0";
    private String mUnitProjectId = "-1";
    private String mProjectType = "";
    private int mProjectTypeId = 0;
    private String mProgressNote = "";
    private String mUsers = "";
    private String mProgress = "1";
    private int mPayId = 0;
    private String mSelectCamera = "";
    private int isPay = 0;
    private int mSavePostion = 0;
    private String dqEditDriverNum;
    private ProgressDialog progressDialog;
    private LinearLayout linearLayoutGetJingWei, linearLayoutJingDuMessage, linearLayoutWeiDuMessage,
            linearLayoutAphalMessage, linearlayoutDriverNum;
    private boolean isGetJingWei = false;
    private boolean isShipPage = false;
    private boolean isNum = false;

    private Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo_install_open_old);
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShipPage) {
                mShowPhotoView.setVisibility(View.GONE);
                mTakePhotoView.setVisibility(View.VISIBLE);
                messageRegulatoryBack.setVisibility(View.GONE);
                regulatoryBack.setVisibility(View.VISIBLE);
                isShipPage = false;
            } else {
                back();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        finish();
    }

    private void initView() {
        mProjectType = getIntent().getStringExtra("ProjectType");
        mProjectTypeId = getIntent().getIntExtra("ProjectTypeId", 0);
        messageRegulatoryBack = (ImageButton) findViewById(R.id.message_regulatory_back);
        ((TextView) findViewById(R.id.title)).setText(mProjectType);
        regulatoryBack = (ImageButton) findViewById(R.id.regulatory_back);
        messageRegulatoryBack.setOnClickListener(this);
        regulatoryBack.setOnClickListener(this);
        findViewById(R.id.send_photo).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);
        findViewById(R.id.get_jing_wei).setOnClickListener(this);
        findViewById(R.id.btn_driver_num_scan).setOnClickListener(this);
        mTakePhotoView = findViewById(R.id.send_photo_view);
        mShowPhotoView = findViewById(R.id.show_photo_view);
        findViewById(R.id.take_photo).setOnClickListener(this);
        mPhotoImage = (ImageView) findViewById(R.id.photo_image);
        mProjectNameList = (Spinner) findViewById(R.id.spinner_project_name);
        mUnitProjectList = (Spinner) findViewById(R.id.spinner_unit_project);
        mSpWordOrder = (Spinner) findViewById(R.id.spinner_word_order);
        mProjectCameraBy = (EditText) findViewById(R.id.edit_project_camera_by);
        mKeyWork = (EditText) findViewById(R.id.edit_project_name);
        mTextViewProjectName = (TextView) findViewById(R.id.txt_project_name);
        mTextViewProjectType = (TextView) findViewById(R.id.txt_project_type);
        mTextViewProjectType.setText(mProjectType);
        mTextViewProgressNote = (TextView) findViewById(R.id.txt_progress_note);
        mTextViewLocalPostion = (TextView) findViewById(R.id.txt_local_position);
        mTextViewTime = (TextView) findViewById(R.id.txt_time);
        mEditUsers = (EditText) findViewById(R.id.edit_users);
        mTextViewSelectCamera = (TextView) findViewById(R.id.txt_select_camera);
        mTextViewUsers = (TextView) findViewById(R.id.txt_usesr);
        mTextViewDriverJingDu = (TextView) findViewById(R.id.txt_driver_jingdu);
        mTextViewDriverWeiDu = (TextView) findViewById(R.id.txt_driver_weidu);
        mTextViewDriverAlpha = (TextView) findViewById(R.id.txt_driver_alpha);
        mTextViewDriverNum = (TextView) findViewById(R.id.txt_driver_num);
        mTextViewInstallSpace = (TextView) findViewById(R.id.txt_install_space);
        mTextViewUnitProject = (TextView) findViewById(R.id.txt_unit_project);
        mTextViewJingDu = (TextView) findViewById(R.id.txt_project_jingdu);
        mTextViewWeiDu = (TextView) findViewById(R.id.txt_project_weidu);
        mTextViewAlpha = (TextView) findViewById(R.id.txt_project_Alpha);
        mEditInstallSpace = (EditText) findViewById(R.id.edit_install_space);
        mEditDriverNum = (EditText) findViewById(R.id.edit_driver_num);
        linearLayoutGetJingWei = (LinearLayout) findViewById(R.id.linearlayout_getjingwei);
        linearLayoutJingDuMessage = (LinearLayout) findViewById(R.id.linearlayout_jingdu_message);
        linearLayoutWeiDuMessage = (LinearLayout) findViewById(R.id.linearlayout_weidu_message);
        linearLayoutAphalMessage = (LinearLayout) findViewById(R.id.linearlayout_alpha_message);
        linearlayoutDriverNum = (LinearLayout) findViewById(R.id.linearlayout_driver_num);
        RadioGroup group = (RadioGroup) this.findViewById(R.id.progress);
        group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {

                if (arg1 == R.id.progress_no_over) {
                    mProgress = "0";
                } else {
                    mProgress = "1";
                }
            }
        });
        initWordOrderSp();
        initProgressNote();
        initProjectCameraBy();
        initProjectName();
        initUnitProject();
        new UsersAsyncTask().execute();
    }

    private void initProgressNote() {
        new ProgressNoteAsyncTask().execute();
    }

    // 获取进度描述
    private class ProgressNoteAsyncTask extends AsyncTask<Void, Void, ArrayList<MaintenanceTypeItem>> {

        @Override
        protected ArrayList<MaintenanceTypeItem> doInBackground(Void... params) {
            return new NetworkApi().GetMaintenanceType(String.valueOf(getIntent().getIntExtra("ProjectTypeId", 0)));
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

            Spinner spinner = (Spinner) findViewById(R.id.spinner_progress_note);
            mProgressNote = data.get(0).text;
            mPayId = data.get(0).id;
            mTextViewProgressNote.setText(mProgressNote);

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(TakePhotoInstallOpenActivityOld.this,
                    android.R.layout.simple_spinner_item, nameData);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mProgressNote = data.get(position).text;
                    mTextViewProgressNote.setText(mProgressNote);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private String[] items = new String[]{};
    private boolean[] checkedItems = new boolean[]{};
    private ListView multiChoiceListView;
    private String mSelectCameraId = "";
    private String mSelectInstallSpac = "";
    private String mSelectCameraTypeId = "";

    // 点位选择
    private void initProjectCameraBy() {
        findViewById(R.id.camera_by).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoiceDialog choice = new ChoiceDialog(TakePhotoInstallOpenActivityOld.this, mCameraList);
                choice.setConfirmOnclick(new ChoiceDialog.OnDialogListener() {

                    @Override
                    public void onConfirmClick(int selectIndex) {
                        String msg = mCameraList.get(selectIndex).CamId + "_" + mCameraList.get(selectIndex).NewCamNam;
                        mSelectCameraId = mCameraList.get(selectIndex).CamId;
                        mSelectInstallSpac = mCameraList.get(selectIndex).CamInstallPlace;
                        mSelectCameraTypeId = mCameraList.get(selectIndex).CamTypeId;

                        mSelectCamera = msg;
                        mProjectCameraBy.setText(msg);
                        mEditInstallSpace.setText(mSelectInstallSpac);
                        new GetCameraTypeAsyncTask().execute(mSelectCameraTypeId);
                        progressDialog = DialogUtil.createProgressDialog(TakePhotoInstallOpenActivityOld.this, "正在获取经纬度...");
                        progressDialog.show();
                    }
                }).show();
            }
        });
    }

    private class GetCameraTypeAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            return new NetworkApi().getCarmType(params[0]);
        }

        @Override
        protected void onPostExecute(String data) {
            progressDialog.dismiss();
            if (!data.equals("") && data.equals("1")) {
                isNum = true;
                linearlayoutDriverNum.setVisibility(View.VISIBLE);
            } else {
                isNum = false;
                linearlayoutDriverNum.setVisibility(View.GONE);
            }
        }
    }

    private void initUnitProject() {
        mUnitProjectList.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UnitProjectItem item = mUnitList.get(mUnitProjectList.getSelectedItemPosition());
                mUnitProjectId = item.projectId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private ArrayList<CameraItem> mCameraList = new ArrayList<CameraItem>();

    private boolean isBind = false;

    private void searchProject() {
        final String name = mKeyWork.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastHelp.showToast(TakePhotoInstallOpenActivityOld.this, "关键字不能为空！");
            return;
        }
        mData.clear();

        final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在获取工程名称...");
        dialog.show();
        dialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkApi.queryProjectData(TakePhotoInstallOpenActivityOld.this, name, String.valueOf(mProjectTypeId),
                        new INetCallback() {
                            @Override
                            public void onCallback(boolean value, String result) {
                                if (value) {
                                    mData.addAll(NetworkApi.parstToProjectList(result));
                                    int count = mData.size();
                                    String[] dataList = new String[count];
                                    for (int i = 0; i < count; i++) {
                                        dataList[i] = mData.get(i).ProjectName;
                                    }
                                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                            TakePhotoInstallOpenActivityOld.this, R.layout.simple_spinner_item, dataList);
                                    adapter.setDropDownViewResource(R.layout.simple_spinner_item);
                                    if (count == 0) {
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.cancel();
                                                mProjectNameList.setAdapter(adapter);
                                                ToastHelp.showToast(TakePhotoInstallOpenActivityOld.this, "未搜索到工程数据！");
                                            }
                                        });
                                        return;
                                    }
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.cancel();
                                            mProjectNameList.setAdapter(adapter);
                                        }
                                    });
                                } else {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.cancel();
                                            ToastHelp.showToast(TakePhotoInstallOpenActivityOld.this, "获取工程名称失败！");
                                        }
                                    });
                                }
                            }
                        });
            }
        }).start();
    }

    private ArrayAdapter<String> mAdapterWordOrder;
    List<String> mDataListWordOrder = new ArrayList<>();

    private void initProjectName() {
        mProjectNameList.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ProjectItem item = mData.get(mProjectNameList.getSelectedItemPosition());
                // 判断返回回来的经纬度是否为空 判断工程人员到达现场 判断当前工程经纬度是否为空
                if (mProjectTypeId == 230 && (TextUtils.isEmpty(item.ProjectLat) || TextUtils.isEmpty(item.ProjectLng))) {
                    isBind = true;
                } else {
                    isBind = false;
                }
                NetworkApi.devInstallJobCodeList(getActivity(), item.projectId,"1" ,new NetworkApi.NetCall() {
                    @Override
                    public void onSuccess(String result) {
                        DevInstallJobCodeListJs json = new Gson().fromJson(result, DevInstallJobCodeListJs.class);
                        mDataListWordOrder.clear();
                        for (DevInstallJobCodeListJs.DataBean dataBean : json.getData()) {
                            mDataListWordOrder.add(dataBean.getJobId());
                        }
                        mAdapterWordOrder = new ArrayAdapter<String>(TakePhotoInstallOpenActivityOld.this, R.layout.simple_spinner_item, mDataListWordOrder);
                        mAdapterWordOrder.setDropDownViewResource(R.layout.simple_spinner_item);
                        mSpWordOrder.setAdapter(mAdapterWordOrder);
                    }

                    @Override
                    public void onFail(String msg) {
                        ToastHelp.showToast(getActivity(), msg);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 初始化工单选择
     */
    private void initWordOrderSp() {
        mSpWordOrder.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String wordOrderId = mDataListWordOrder.get(mSpWordOrder.getSelectedItemPosition());
                String projectId = mData.get(mProjectNameList.getSelectedItemPosition()).projectId;
//                NetworkApi.getInstallDatCameraCamNameList(getActivity(), projectId, wordOrderId, new NetworkApi.NetCall() {
//                    @Override
//                    public void onSuccess(String result) {
//                        mCameraList.clear();
//                        mCameraList.addAll(NetworkApi.parstToCameraList(result));
//                        items = new String[mCameraList.size()];
//                        checkedItems = new boolean[mCameraList.size()];
//                        for (int i = 0; i < mCameraList.size(); i++) {
//                            items[i] = mCameraList.get(i).CamId + "_" + mCameraList.get(i).NewCamNam;
//                            checkedItems[i] = false;
//                        }
//                        mProjectCameraBy.setText("");
//                        mSelectCameraId = "";
//                    }
//
//                    @Override
//                    public void onFail(String msg) {
//                        ToastHelp.showToast(getActivity(), msg);
//                    }
//                });
                NetworkApi.queryUnitProject(projectId, new INetCallback() {
                    @Override
                    public void onCallback(boolean value, String result) {
                        mUnitList.clear();
                        UnitProjectItem unitProjectItem = new UnitProjectItem();
                        unitProjectItem.projectId = "";
                        unitProjectItem.ProjectName = "公共区域";
                        mUnitList.add(unitProjectItem);

                        if (value) {
                            mUnitList.addAll(NetworkApi.parstToUnitProjectList(result));
                            int count = mUnitList.size();
                            String[] dataList = new String[count];
                            for (int i = 0; i < count; i++) {
                                dataList[i] = mUnitList.get(i).ProjectName;
                            }
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(TakePhotoInstallOpenActivityOld.this,
                                    R.layout.simple_spinner_item, dataList);
                            adapter.setDropDownViewResource(R.layout.simple_spinner_item);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mUnitProjectList.setAdapter(adapter);
                                }
                            });
                        } else {
                            int count = mUnitList.size();
                            String[] dataList = new String[count];
                            for (int i = 0; i < count; i++) {
                                dataList[i] = mUnitList.get(i).ProjectName;
                            }
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(TakePhotoInstallOpenActivityOld.this,
                                    R.layout.simple_spinner_item, dataList);
                            adapter.setDropDownViewResource(R.layout.simple_spinner_item);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mUnitProjectList.setAdapter(adapter);
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
     * 初始化点位数据
     */
    private void initCameraProjectData() {
        if (mData == null)
            return;
        ProjectItem item = mData.get(mProjectNameList.getSelectedItemPosition());
        progressDialog = DialogUtil.createProgressDialog(TakePhotoInstallOpenActivityOld.this, "正在刷新数据...");
        progressDialog.show();
        NetworkApi.queryProjectCameraBy(item.projectId, String.valueOf(mProjectTypeId), new INetCallback() {
            @Override
            public void onCallback(boolean value, String result) {

                if (null != progressDialog)
                    progressDialog.dismiss();
                if (value) {
                    mCameraList.clear();
                    mCameraList.addAll(NetworkApi.parstToCameraList(result));
                    items = new String[mCameraList.size()];
                    checkedItems = new boolean[mCameraList.size()];
                    for (int i = 0; i < mCameraList.size(); i++) {
                        items[i] = mCameraList.get(i).CamId + "_" + mCameraList.get(i).NewCamNam;
                        checkedItems[i] = false;
                    }

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProjectCameraBy.setText("");
                            mSelectCameraId = "";
                            linearlayoutDriverNum.setVisibility(View.GONE);
                            mProjectCameraBy.setText("");
                            mEditInstallSpace.setText("");
                        }
                    });
                }
            }
        });
    }

    private ListView user_multiChoiceListView;

    // 随行人员
    private class UsersAsyncTask extends AsyncTask<Void, Void, ArrayList<UsersItem>> {

        @Override
        protected ArrayList<UsersItem> doInBackground(Void... params) {
            return new NetworkApi().GetUsersGongJian();
        }

        @Override
        protected void onPostExecute(final ArrayList<UsersItem> data) {
            if (data == null) {
                return;
            }
            final String[] user_items = new String[data.size()];
            final boolean[] user_checkedItems = new boolean[data.size()];
            for (int i = 0; i < data.size(); i++) {
                user_items[i] = data.get(i).UserName;
                user_checkedItems[i] = false;
            }
            findViewById(R.id.btn_users).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TakePhotoInstallOpenActivityOld.this);
                    builder.setMultiChoiceItems(user_items, user_checkedItems, null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            String msg = "";
                            mUsers = "";
                            for (int i = 0; i < user_items.length; i++) {
                                if (user_multiChoiceListView.getCheckedItemPositions().get(i)) {
                                    if (!TextUtils.isEmpty(msg)) {
                                        msg += "|";
                                        mUsers += "|";
                                    }
                                    msg += data.get(i).UserName;
                                    mUsers += data.get(i).UserName;
                                }
                            }
                            mEditUsers.setText(msg);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    user_multiChoiceListView = dialog.getListView();
                    dialog.show();
                }
            });
        }
    }

    private class GetJingWeiAsyncTask extends AsyncTask<Void, Void, PlaceMessgaeItem> {

        @Override
        protected PlaceMessgaeItem doInBackground(Void... params) {
            dqEditDriverNum = mEditDriverNum.getText().toString();
            return new NetworkApi().getPlaceMessage(mEditDriverNum.getText().toString());
        }

        @Override
        protected void onPostExecute(final PlaceMessgaeItem data) {
            progressDialog.dismiss();
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.getjingweidudialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    new ContextThemeWrapper(TakePhotoInstallOpenActivityOld.this, R.style.Theme_Transparent));
            builder.setView(view);
            final AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            final EditText editText_JingDu = (EditText) view.findViewById(R.id.EditText_JingDu);
            final EditText editText_WeiDu = (EditText) view.findViewById(R.id.EditText_WeiDu);
            final EditText editText_Aphal = (EditText) view.findViewById(R.id.EditText_Aphla);
            Button btn_cretain = (Button) view.findViewById(R.id.btn_center);
            Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
            btn_cretain.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (TextUtils.isEmpty(editText_JingDu.getText().toString())) {
                        ToastHelp.showToast(TakePhotoInstallOpenActivityOld.this, "经度不能为空！");
                        return;
                    }
                    if (TextUtils.isEmpty(editText_WeiDu.getText().toString())) {
                        ToastHelp.showToast(TakePhotoInstallOpenActivityOld.this, "经度不能为空！");
                        return;
                    }
                    if (TextUtils.isEmpty(editText_Aphal.getText().toString())) {
                        ToastHelp.showToast(TakePhotoInstallOpenActivityOld.this, "经度不能为空！");
                        return;
                    }
                    mTextViewJingDu.setText(editText_JingDu.getText().toString());
                    mTextViewWeiDu.setText(editText_WeiDu.getText().toString());
                    mTextViewAlpha.setText(editText_Aphal.getText().toString());
                    dialog.cancel();
                }
            });
            btn_cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.cancel();
                }
            });
            if (data != null) {
                editText_JingDu.setText(data.getLongtitude());
                editText_WeiDu.setText(data.getLatitude());
                editText_Aphal.setText(data.getAlpha());

            } else {
                ToastHelp.showToast(TakePhotoInstallOpenActivityOld.this, "获取经纬度失败！");
            }
            dialog.show();
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = (int) (display.getWidth()); // 设置宽度
            dialog.getWindow().setAttributes(lp);
        }
    }


    /**
     * 判断该项目原来是否绑定、是否为已经到达、定位是否开启
     */
    private void Update() {
        getLocalPosition();

        // 已经绑定、已经到达现场
        if (isBind && mPayId == 233) {
            showBindProjectLocationDialog();

        } else {// 未绑定
            // sendPhoto();
            takePhoto();
        }
    }

    private void showBindProjectLocationDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(TakePhotoInstallOpenActivityOld.this);
        builder.setMessage(
                "发现当前工程还未绑定经纬度信息，是否绑定？" + "\n当前位置:" + PreferenceUtil.getProjectAddrStr());
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
                // TODO Auto-generated method stub
                dialog.dismiss();
                takePhoto();
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
                ProjectItem item = mData.get(mProjectNameList.getSelectedItemPosition());
                String projid = item.projectId;
                NetworkApi.uploadDataForSavePosition(TakePhotoInstallOpenActivityOld.this, projid, mProjcLat, mProjcLng,
                        new INetCallback() {
                            @Override
                            public void onCallback(boolean value, String result) {
                                LogUtil.logD("sendData onCallback" + result);
                                if (value) {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastHelp.showToast(TakePhotoInstallOpenActivityOld.this, "发送成功！");
                                        }
                                    });
                                } else {
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastHelp.showToast(TakePhotoInstallOpenActivityOld.this, "发送失败！");
                                        }
                                    });
                                }
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.cancel();
                                        // 进行拍照
                                        takePhoto();
                                    }
                                });
                            }
                        });
            }
        }).start();
    }

    private void sendPhoto() {
        if (!HttpUtil.networkIsAvailable(this)) {
            ToastHelp.showToast(this, "手机没有网络连接！");
            return;
        }
        final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送数据...");
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProjectItem item = mData.get(mProjectNameList.getSelectedItemPosition());
                /*
                 * Bitmap bitmap = ImageUtil.scaleImage(mPhotoUri, 500f, 500f);
				 * if (bitmap == null) { mHandler.post(new Runnable() {
				 * 
				 * @Override public void run() {
				 * ToastHelp.showToast(TakePhotoInstallOpenActivity.this, "发送失败！");
				 * dialog.cancel(); } }); return; } String imgStr =
				 * ImageUtil.convertIconToString(bitmap); bitmap.recycle();
				 * bitmap = null;
				 */

                final SendDataBean data = new SendDataBean();
                // mData.ImgStr = imgStr;
                data.Note = mProgressNote;
                data.ProjectId = item.projectId;
                data.ProjectName = item.ProjectName;
                data.UserName = PreferenceUtil.getName();
                data.ProjcLat = mProjcLat;
                data.ProjcLng = mProjcLng;
                data.Type = mProjectType;
                data.CameraId = mSelectCameraId;
                data.Progress = mProgress;
                data.IsSaveLocation = mSavePostion;
                data.ispay = isPay;
                data.projectTypeId = mProjectTypeId;
                data.payId = mPayId;
                data.UserId = PreferenceUtil.getUserId();
                Log.e("1111", mEditDriverNum.getText().toString());
                data.DriverNum = mEditDriverNum.getText().toString();
                Log.e("1111", mEditDriverNum.getText().toString());
                data.CamName = mProjectCameraBy.getText().toString();
                if (mTextViewUnitProject.getText().toString().equals("公共区域")) {
                    data.UnitProjectId = "";
                } else {
                    data.UnitProjectId = mUnitProjectId;
                }
                data.InstallPlace = mEditInstallSpace.getText().toString();
                if (mTextViewDriverJingDu.getText().toString().equals("无")) {
                    data.DriverJingDu = "";
                } else {
                    data.DriverJingDu = mTextViewDriverJingDu.getText().toString();
                }
                if (mTextViewDriverWeiDu.getText().toString().equals("无")) {
                    data.DriverWeiDu = "";
                } else {
                    data.DriverWeiDu = mTextViewDriverWeiDu.getText().toString();
                }
                if (mTextViewDriverAlpha.getText().toString().equals("无")) {
                    data.DriverAlpha = "";
                } else {
                    data.DriverAlpha = mTextViewDriverAlpha.getText().toString();
                }
                final JsonModel result = NetworkApi.sendIntallOpenData(TakePhotoInstallOpenActivityOld.this, data);
                if (result == null) {
                    Looper.prepare();
                    DefaultDialog.showDialog(TakePhotoInstallOpenActivityOld.this, "网络异常，请重试！");
                    if (null != dialog && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Looper.loop();
                    return;
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                        if (result.getResult().equals("0")) {
                            Log.e("errcode", (result.getMsg()));
                            Log.e("errcode", (result.getMsg().indexOf(":") + 1 + ""));
                            Log.e("errcode", (result.getMsg().indexOf("！") + ""));
                            Log.e("errcode", (result.getMsg().lastIndexOf("！") + ""));
                            Log.e("errcode", result.getMsg().substring(result.getMsg().indexOf(":") + 1,
                                    result.getMsg().indexOf(":") + 6));
                            DefaultDialog.showDialogIntentErr(TakePhotoInstallOpenActivityOld.this, result.getMsg(), result.getMsg()
                                    .substring(result.getMsg().indexOf(":") + 1, result.getMsg().indexOf(":") + 6));
                        } else if (result.getResult().equals("1")) {
                            DefaultDialog.showDialog(TakePhotoInstallOpenActivityOld.this, result.getMsg());
                            initCameraProjectData();
                            // DefaultDialog.showDialogIsFinish(TakePhotoInstallOpenActivity.this,
                            // result.getMsg());

                        } else if (result.getResult().equals("2")) {
                            new AlertDialog.Builder(TakePhotoInstallOpenActivityOld.this).setTitle("提示")
                                    .setMessage("省站信息不全仅保存到HMS平台，是否继续配置？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which) {
                                            dialog.show();
                                            new Thread() {
                                                public void run() {
                                                    final JsonModel result = NetworkApi
                                                            .sendIntallOpenDataToSZ(TakePhotoInstallOpenActivityOld.this, data);
                                                    if (result == null) {
                                                        Looper.prepare();
                                                        DefaultDialog.showDialog(TakePhotoInstallOpenActivityOld.this, "网络异常，请重试！");
                                                        if (null != dialog && dialog.isShowing())
                                                            dialog.dismiss();
                                                        Looper.loop();
                                                        return;
                                                    }
                                                    mHandler.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            dialog.cancel();
                                                            if (result.getResult().equals("0")) {
                                                                Log.e("errcode",
                                                                        result.getMsg().substring(
                                                                                result.getMsg().indexOf(":") + 1, result
                                                                                        .getMsg().lastIndexOf("!")));
                                                                DefaultDialog.showDialogIntentErr(
                                                                        TakePhotoInstallOpenActivityOld.this, result.getMsg(),
                                                                        result.getMsg().substring(
                                                                                result.getMsg().indexOf(":") + 1, result
                                                                                        .getMsg().lastIndexOf("!")));
                                                            } else if (result.getResult().equals("1")) {
                                                                DefaultDialog.showDialog(TakePhotoInstallOpenActivityOld.this,
                                                                        result.getMsg());
                                                                initCameraProjectData();
                                                                // DefaultDialog.showDialogIsFinish(TakePhotoInstallOpenActivity.this,
                                                                // result.getMsg());
                                                            } else {
                                                                DefaultDialog.showDialog(TakePhotoInstallOpenActivityOld.this,
                                                                        result.getMsg());
                                                            }

                                                        }
                                                    });

                                                }
                                            }.start();
                                        }
                                    }).setCancelable(false).create().show();

                        } else if (result.getResult().equals("3")) {
                            DefaultDialog.showDialog(TakePhotoInstallOpenActivityOld.this, "安装成功，但未开通设备！");
                            initCameraProjectData();
                            // DefaultDialog.showDialogIsFinish(TakePhotoInstallOpenActivity.this,
                            // "安装成功，但未开通设备！");
                        } else {
                            DefaultDialog.showDialog(TakePhotoInstallOpenActivityOld.this, "网络超时，请重新上传！");
                        }
                    }
                });

            }
        }).start();
    }

    private void takePhoto() {
        getLocalPosition();

        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            ToastHelp.showToast(this, "手机没有安装SD卡，不能拍照");
            return;
        }
        if (mData.size() <= 0) {
            ToastHelp.showToast(this, "工程名称不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mProjectCameraBy.getText()) && mProjectTypeId != 230) {
            ToastHelp.showToast(this, "设备名称不能为空！");
            return;
        }
        if (isNum) {
            if (TextUtils.isEmpty(mEditDriverNum.getText().toString())) {
                ToastHelp.showToast(this, "设备序列号不能为空！");
                return;
            }
        }
        if (isGetJingWei) {
            if (dqEditDriverNum == null) {
                ToastHelp.showToast(this, "请获得经纬度信息");
                return;
            }
            if (!dqEditDriverNum.equals(mEditDriverNum.getText().toString())) {
                ToastHelp.showToast(this, "设备序列号改变，请重新获取经纬度！");
                return;
            }
            if (mTextViewJingDu.getText().equals("无") || mTextViewJingDu.getText().equals("")) {
                ToastHelp.showToast(this, "经纬度信息不能为空，请获取经纬度信息！");
                return;
            }
            if (mTextViewWeiDu.getText().equals("无") || mTextViewWeiDu.getText().equals("")) {
                ToastHelp.showToast(this, "经纬度信息不能为空，请获取经纬度信息！");
                return;
            }
            if (mTextViewAlpha.getText().equals("无") || mTextViewAlpha.getText().equals("")) {
                ToastHelp.showToast(this, "经纬度信息不能为空，请获取经纬度信息！");
                return;
            }
        }
        if (TextUtils.isEmpty(mEditInstallSpace.getText().toString())) {
            ToastHelp.showToast(this, "安装位置不能为空！");
            return;
        }
        sendPhoto();
        /*
         * Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); String
		 * dirName = Environment.getExternalStorageDirectory() + "/huixin"; File
		 * f = new File(dirName); if (!f.exists()) { f.mkdir(); } String name =
		 * DateFormat.format("yyyyMMdd_hhmmss",
		 * Calendar.getInstance().getTime()) + ".jpg"; mPhotoUri = dirName + "/"
		 * + name; File file = new File(dirName, name);
		 *
		 * intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		 * startActivityForResult(intent, 0);
		 */
    }

    private void getLocalPosition() {
        mProjcLat = PreferenceUtil.getProjectLat();
        mProjcLng = PreferenceUtil.getProjectLng();
        String addr = PreferenceUtil.getProjectAddrStr();
        mTextViewLocalPostion.setText("经度：" + mProjcLng + " 纬度：" + mProjcLat + "\n地址：" + addr);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, mData);
        Log.e("resultCode", resultCode + "");
        if (requestCode == 0x11) {
            if (data != null) {
                mEditDriverNum.setText(data.getStringExtra("SCAN_RESULT"));
            }

        } else if (resultCode == Activity.RESULT_OK) {
            try {
                if (!(new File(mPhotoUri)).exists()) {
                    Log.e("onActivityResult", "mPhotoUri not exists!");
                    ToastHelp.showToast(this, "拍照失败！");
                    return;
                }
                isShipPage = true;
                messageRegulatoryBack.setVisibility(View.VISIBLE);
                regulatoryBack.setVisibility(View.GONE);
                mShowPhotoView.setVisibility(View.VISIBLE);
                mTakePhotoView.setVisibility(View.GONE);
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.ARGB_8888)
                        .cacheInMemory(false).cacheOnDisc(true).build();
                ImageLoader.getInstance().displayImage("file://" + mPhotoUri, mPhotoImage, options);
                ProjectItem item = mData.get(mProjectNameList.getSelectedItemPosition());
                mTextViewProjectName.setText(item.ProjectName);
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                String time = df.format(new Date());
                mTextViewTime.setText(time);
                mTextViewUsers.setText(mUsers);
                mTextViewSelectCamera.setText(mProjectCameraBy.getText().toString());
                mTextViewDriverJingDu.setText(mTextViewJingDu.getText().toString());
                mTextViewDriverWeiDu.setText(mTextViewWeiDu.getText().toString());
                mTextViewDriverAlpha.setText(mTextViewAlpha.getText().toString());
                mTextViewDriverNum.setText(mEditDriverNum.getText().toString());
                mTextViewInstallSpace.setText(mEditInstallSpace.getText().toString());
                mTextViewUnitProject.setText(mUnitList.get(mUnitProjectList.getSelectedItemPosition()).ProjectName);
                if (mProjectTypeId == 230) {
                    File file = new File(mPhotoUri);
                    // 人脸识别。
                    // Distinguish(new File(mPhotoUri));
                }

            } catch (Exception e) {
                e.printStackTrace();
                ToastHelp.showToast(this, "拍照失败，请重新拍照！");
            }
        } else {
            ToastHelp.showToast(this, "拍照失败，请重新拍照！");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_jing_wei:
                if (!TextUtils.isEmpty(mEditDriverNum.getText().toString())) {
                    progressDialog = DialogUtil.createProgressDialog(this, "正在获取经纬度...");
                    progressDialog.show();
                    new GetJingWeiAsyncTask().execute();
                } else {
                    ToastHelp.showToast(this, "设备序列号不能为空！");
                    return;
                }
                break;
            case R.id.message_regulatory_back:
                mShowPhotoView.setVisibility(View.GONE);
                mTakePhotoView.setVisibility(View.VISIBLE);
                messageRegulatoryBack.setVisibility(View.GONE);
                regulatoryBack.setVisibility(View.VISIBLE);
                isShipPage = false;
                break;
            case R.id.regulatory_back:
                back();
                break;
            case R.id.take_photo:
                // 先判断是否项目定位，没有就绑定定位，然后在takePhoto();
                Update();
                break;
            case R.id.send_photo:
                sendPhoto();
                break;
            case R.id.search:
                searchProject();
                break;
            case R.id.btn_driver_num_scan:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, 0x11);
                break;
            default:
                break;
        }
    }
}
