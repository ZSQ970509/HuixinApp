package com.hc.android.huixin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.BitmapUtils;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.king.photo.model.AttendancePathModel;
import com.king.photo.model.AttendanceProjectModel;
import com.king.photo.model.WeiHuProjectModel;
import com.king.photo.util.Constants;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.fisc.entity.IVMBaseEntity;
import cn.fisc.libproject.facerecognition.FaceRecognition;
import okhttp3.Call;

public class AttendancePathProjectHumenActivity extends Activity implements View.OnClickListener {
    ImageButton ibBack;
    MapView mMapView;
    BaiduMap mBaiduMap;
    MapStatus.Builder builder;
    LatLng target;
    List<LatLng> latLngs = new ArrayList<LatLng>();
    Button btnStart, btnEnd;
    private String imageOneBase64 = "";
    private String imageTwoBase64 = "";
    private ProgressDialog progressDialog;
    private static final int TAKE_PICTURE = 0x000001;
    //String SDPATH = Environment.getExternalStorageDirectory() + "/Photo_LJ/";
    String SDPATH_1 = Environment.getExternalStorageDirectory() + "/headPic_1.jpg";
    String SDPATH_2 = Environment.getExternalStorageDirectory() + "/headPic_2.jpg";
    File file_1, file_2;
    double rate;
    Intent intent;
    String projectId, projectName, humenType = "工建人员";
    AttendanceProjectModel.DataBean dataBean;
    BitmapDescriptor startBD = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_me_history_startpoint);
    BitmapDescriptor finishBD = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_me_history_finishpoint);
    AlertDialog dialog = null;
    private Marker mMarkerA;
    private Marker mMarkerB;
    Polyline mPolyline;
    boolean isHeadImg = false;//是否有底图

    @Override
    protected void onStart() {
        super.onStart();
        openGPSSettings();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_path);
        if (PreferenceUtil.getIsBattery() && PreferenceUtil.getIsAutoStart()) {

        } else {
            Intent intent = new Intent(AttendancePathProjectHumenActivity.this, PermissionsSettingsActivity.class);
            startActivity(intent);
        }
        intent = getIntent();
        dataBean = (AttendanceProjectModel.DataBean) getIntent().getSerializableExtra("dataBean");
        if (dataBean != null) {
            projectId = dataBean.getProjectId();
            projectName = dataBean.getProjectName();
        } else {
            projectId = PreferenceUtil.getPathProjectId();
            projectName = PreferenceUtil.getPathProjectName();
        }
        progressDialog = DialogUtil.createProgressDialog(AttendancePathProjectHumenActivity.this, "初始化中，请稍后...");
        initHead();
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.mapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        double lanSum = 0;
        double lonSum = 0;
        List<AttendancePathModel> list = MyApplication.attendancePathDao.loadPhysiological();
        if (list.size() == 0) {
            latLngs.add(new LatLng(Double.parseDouble(PreferenceUtil.getProjectLat()),
                    (Double.parseDouble(PreferenceUtil.getProjectLng()))));
            latLngs.add(new LatLng(Double.parseDouble(PreferenceUtil.getProjectLat()),
                    (Double.parseDouble(PreferenceUtil.getProjectLng()))));
            lanSum += latLngs.get(0).latitude;
            lonSum += latLngs.get(0).longitude;
            lanSum += latLngs.get(1).latitude;
            lonSum += latLngs.get(1).longitude;
        } else {
            for (AttendancePathModel dataList : list) {
                if (latLngs.size() < 1) {
                    LatLng latlng_1 = new LatLng(Double.parseDouble(dataList.getAttendancePathLat()),
                            (Double.parseDouble(dataList.getAttendancePathLng())));
                    latLngs.add(latlng_1);
                    lanSum += latlng_1.latitude;
                    lonSum += latlng_1.longitude;
                }
                LatLng latlng = new LatLng(Double.parseDouble(dataList.getAttendancePathLat()),
                        (Double.parseDouble(dataList.getAttendancePathLng())));
                latLngs.add(latlng);
                lanSum += latlng.latitude;
                lonSum += latlng.longitude;
            }
        }

        target = new LatLng(lanSum / latLngs.size(), lonSum / latLngs.size());
        builder = new MapStatus.Builder();
        builder.target(target).zoom(18f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        MarkerOptions oStart = new MarkerOptions();//地图标记覆盖物参数配置类
        oStart.position(latLngs.get(0));//覆盖物位置点，第一个点为起点
        oStart.icon(startBD);//设置覆盖物图片
        oStart.zIndex(1);//设置覆盖物Index
        mMarkerA = (Marker) (mBaiduMap.addOverlay(oStart)); //在地图上添加此图层
        OverlayOptions ooPolyline = new PolylineOptions().width(10).color(Color.rgb(88, 190, 252)).points(latLngs);
        mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
        mPolyline.setZIndex(3);
        //添加终点图层
        if (list.size() > 2) {
            MarkerOptions oFinish = new MarkerOptions().position(latLngs.get(latLngs.size() - 1)).icon(finishBD).zIndex(2);
            mMarkerB = (Marker) (mBaiduMap.addOverlay(oFinish));
        }
        btnStart = (Button) findViewById(R.id.btnStart);
        btnEnd = (Button) findViewById(R.id.btnEnd);
        ibBack = (ImageButton) findViewById(R.id.regulatory_back);
        ibBack.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnEnd.setOnClickListener(this);
        if (PreferenceUtil.getAttendandcePathProjectHumen()) {
            btnStart.setEnabled(false);
            btnEnd.setBackgroundColor(Color.rgb(88, 190, 252));
            btnStart.setBackgroundColor(Color.rgb(209, 209, 209));
        } else {
            btnEnd.setEnabled(false);
            btnStart.setBackgroundColor(Color.rgb(88, 190, 252));
            btnEnd.setBackgroundColor(Color.rgb(209, 209, 209));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regulatory_back:
                finish();
                break;
            case R.id.btnStart:

                if (isHeadImg) {
                    new AlertDialog.Builder(AttendancePathProjectHumenActivity.this).setTitle("提示").setMessage("请先进行人脸识别。")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    photo();

                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).setCancelable(false).create().show();

                } else {
                    Toast.makeText(AttendancePathProjectHumenActivity.this, "您无底图请联系管理员！", Toast.LENGTH_SHORT).show();
                }

                //JSONArray listArrays= JSONArray.fromObject(list);
                break;
            case R.id.btnEnd:
                new AlertDialog.Builder(AttendancePathProjectHumenActivity.this).setTitle("提示").setMessage("是否确认上传路径？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog = DialogUtil.createProgressDialog(AttendancePathProjectHumenActivity.this, "正在上传中，请稍后...");
                                insertLocation(false);
                                new AttendancePathProjectHumenActivity.insertDatTrajectoryAsyncTask().execute();

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setCancelable(false).create().show();

                break;
        }
    }

    public void initHead() {
        new getHeadImageAsyncTask().execute();
    }

    public void photo() {

        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.addCategory(Intent.CATEGORY_DEFAULT);

        file_2 = new File(SDPATH_2);
        if (file_2.exists()) {
            file_2.delete();
        }
        Uri uri = Uri.fromFile(file_2);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    public void faceRec() {
        progressDialog = DialogUtil.createProgressDialog(AttendancePathProjectHumenActivity.this, "人脸监测中，请稍后...");
        progressDialog.show();
        FaceRecognition.getInstance().startFaceMatching(AttendancePathProjectHumenActivity.this,Constants.RSA_KEY, Constants.PRIVATE_RSA, imageOneBase64, imageTwoBase64, new StringCallback() {


            @Override
            public void onError(Call call, Exception e, int i) {
                Log.e("111", "111");
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(AttendancePathProjectHumenActivity.this);
                // 设置提示框的标题
                builder.setTitle("提示：").
                        // 设置提示框的图标
                        // setIcon(R.drawable.icon).
                        // 设置要显示的信息
                                setMessage("网络连接异常，请稍候！").
                        // 设置确定按钮
                                setPositiveButton("确定", null);

                // 生产对话框fisc_zny_facerecognition_http_1.1.3.jar
                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                // 显示对话框
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                // Toast.makeText(HomePageActivity.this,"网络连接异常，请稍后！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String s, int i) {
                Gson gson = new Gson();
                IVMBaseEntity entity = gson.fromJson(s, IVMBaseEntity.class);
                if (entity != null) {
                    if (entity.getResultCd() == 0) {
                        try {
                            String str = gson.toJson(entity.getRes());
                            JSONObject json = new JSONObject(str);
                            rate = json.getDouble("rate");
                            //Toast.makeText(getApplicationContext(),"比对相似度为"+save2Num((double)rate*100.0)+"%",Toast.LENGTH_SHORT).show();

                            new upLoadPhotoAsyncTask().execute();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {
                        Log.e("no face", entity.getMsg());
                        progressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(AttendancePathProjectHumenActivity.this);
                        // 设置提示框的标题
                        builder.setTitle("提示：").
                                // 设置提示框的图标
                                // setIcon(R.drawable.icon).
                                // 设置要显示的信息
                                        setMessage("未检出到人脸，请重试！").
                                // 设置确定按钮
                                        setPositiveButton("确定", null);

                        // 生产对话框
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        // 显示对话框
                        alertDialog.show();
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                        // Toast.makeText(HomePageActivity.this,"底图采集不符合标准，请联系管理员！",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();


                    AlertDialog.Builder builder = new AlertDialog.Builder(AttendancePathProjectHumenActivity.this);
                    // 设置提示框的标题
                    builder.setTitle("提示：").
                            // 设置提示框的图标
                            // setIcon(R.drawable.icon).
                            // 设置要显示的信息
                                    setMessage("网络连接异常，请稍候！").
                            // 设置确定按钮
                                    setPositiveButton("确定", null);

                    // 生产对话框
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    // 显示对话框
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(88, 190, 252));
                    // Toast.makeText(HomePageActivity.this,"网络连接异常，请稍后！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class getHeadImageAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            return new NetworkApi().getHeadImage(AttendancePathProjectHumenActivity.this);
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(final String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result == null) {
                progressDialog.dismiss();
                // Toast.makeText(AttendancePathProjectHumenActivity.this,"暂无头像信息",Toast.LENGTH_SHORT).show();
                return;
            } else {
                new Thread() {
                    public void run() {
                        HttpUtil.download(result, SDPATH_1, new INetCallback() {
                            @Override
                            public void onCallback(boolean value, String result) {
                                progressDialog.dismiss();
                                isHeadImg = true;
                                file_1 = new File(SDPATH_1);
                                imageOneBase64 = BitmapUtils.getImageStr(file_1.getAbsolutePath());
                                Log.e("imageOneBase64", imageOneBase64);
                            }
                        });
                    }
                }.start();

            }


        }

    }

    class upLoadPhotoAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            return new NetworkApi().upLoadPhoto(AttendancePathProjectHumenActivity.this, projectId, projectName, "其他", humenType, imageTwoBase64);
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (result == null) {
                Toast.makeText(AttendancePathProjectHumenActivity.this, "信息上传失败，请重试！", Toast.LENGTH_SHORT).show();
                return;
            } else {
                PreferenceUtil.saveOfficeCapId(result);
                Log.e("log", rate + "");
                if (rate >= 0.6) {
                    //progressDialog.dismiss();
                    Toast.makeText(AttendancePathProjectHumenActivity.this, "考勤记录已开启！", Toast.LENGTH_SHORT).show();
                    PreferenceUtil.saveAttendandcePathProjectHumen(true);
                    PreferenceUtil.savePathProjectId(projectId);
                    PreferenceUtil.savePathProjectName(projectName);
                    btnEnd.setEnabled(true);
                    btnStart.setEnabled(false);
                    btnEnd.setBackgroundColor(Color.rgb(88, 190, 252));
                    btnStart.setBackgroundColor(Color.rgb(209, 209, 209));
                    insertLocation(true);


                } else {
                    Toast.makeText(getApplicationContext(), "人脸验证失败请重试！", Toast.LENGTH_SHORT).show();
                    // mPresenter.playCard(time,loginBean.getMemberId()+"",homePageAddress.getText().toString(),type,loginBean.getProjId(),loginBean.getEmtpId(),loginBean.getEmtpRolesId(),Longitude+"",latitude+"");

                }

            }


        }

    }

    class insertDatTrajectoryAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String StrartTime = simpleDateFormat.format(date);
            String EndTime = simpleDateFormat.format(date);
            List<AttendancePathModel> list = MyApplication.attendancePathDao.loadPhysiological();
            if (list.size() != 0) {
                StrartTime = list.get(0).getAttendancePathDate();
                EndTime = list.get(list.size() - 1).getAttendancePathDate();
            }
            Gson gson = new Gson();
            Log.e("1111", gson.toJson(list));
            //Context context,String projectId,String paramJson,String officeCapId
            return new NetworkApi().insertDatTrajectory(AttendancePathProjectHumenActivity.this, projectId, gson.toJson(list), PreferenceUtil.getOfficeCapId(), StrartTime, EndTime);
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (result == null) {
                Toast.makeText(AttendancePathProjectHumenActivity.this, "信息上传失败，请重试！", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(AttendancePathProjectHumenActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                PreferenceUtil.saveAttendandcePathProjectHumen( false);
                btnEnd.setEnabled(false);
                btnStart.setEnabled(true);
                btnStart.setBackgroundColor(Color.rgb(88, 190, 252));
                btnEnd.setBackgroundColor(Color.rgb(209, 209, 209));
                MyApplication.attendancePathDao.clearAttendancePatn();
            }


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:

                if (resultCode == RESULT_OK) {
                    if (data != null) { //可能尚未指定intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        //返回有缩略图
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            //得到bitmap后的操作
                            ;
                            imageTwoBase64 = ImageUtil.convertIconToString(ImageUtil.zoomImage(thumbnail, 800, 400));
                        }
                    } else {
                        //由于指定了目标uri，存储在目标uri，intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        // 通过目标uri，找到图片
                        // 对图片的缩放处理
                        // 操作
                        imageTwoBase64 = ImageUtil.convertIconToString(ImageUtil.scaleImage(file_2.getAbsolutePath(), 800, 400));
                    }
                    faceRec();

                }
                Log.e("imageOneBase64", imageOneBase64);
                Log.e("imageTwoBase64", imageTwoBase64);
                break;
        }

    }

    private void openGPSSettings() {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (checkGPSIsOpen()) {

        } else {
            //没有打开则弹出对话框
            dialog = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("当前应用需要打开定位功能。\n\n请点击“设置”-“定位服务”-打开定位功能。")
                    // 拒绝, 退出应用
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    openGPSSettings();
                                }
                            })

                    .setPositiveButton("设置",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //跳转GPS设置界面
                                    if (checkGPSIsOpen()) {
                                        dialog.dismiss();
                                    } else {
                                        dialog.dismiss();
                                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivityForResult(intent, 10);
                                    }


                                }
                            })

                    .setCancelable(false)
                    .show();

        }
    }

    private boolean checkGPSIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    private void insertLocation(boolean isClearSharePreference) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            if (isClearSharePreference) {
                PreferenceUtil.saveTime(date.getTime() + "");
            } else {
                PreferenceUtil.saveTime("");
            }
            if (Double.parseDouble(PreferenceUtil.getProjectLat()) == 4.9E-324 || Double.parseDouble(PreferenceUtil.getProjectLng()) == 4.9E-324) {
                if (MyApplication.attendancePathDao.loadPhysiological().size() != 0) {
                    MyApplication.attendancePathDao.loadPhysiological().get(MyApplication.attendancePathDao.loadPhysiological().size() - 1);
                }
            } else {

                MyApplication.attendancePathDao.addPhysiological(new AttendancePathModel(simpleDateFormat.format(date), PreferenceUtil.getProjectLng(), PreferenceUtil.getProjectLat()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
