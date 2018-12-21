package com.hc.android.huixin.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hc.android.huixin.MyApplication;
import com.hc.android.huixin.PuIdPlayerActivity;
import com.hc.android.huixin.bean.UploadWordOrderInfoBean;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.king.photo.activity.LadderControlDeviceChoosePlace;
import com.king.photo.model.AttendanceProjectModel;
import com.king.photo.model.CabineModel;
import com.king.photo.model.DriverModel;
import com.king.photo.model.ErrorMsgModel;
import com.king.photo.model.FullImageDevModel;
import com.king.photo.model.FullImageMainModel;
import com.king.photo.model.FullImageMeasureModel;
import com.king.photo.model.FullImageNodeModel;
import com.king.photo.model.HumanModel;
import com.king.photo.model.JsonModel;
import com.king.photo.model.LockDriverModel;
import com.king.photo.model.PositionImgModel;
import com.king.photo.model.PowerDevBindModel;
import com.king.photo.model.PowerDevHistoryModel;
import com.king.photo.model.PowerDevModel;
import com.king.photo.model.PowerNetHistoryModel;
import com.king.photo.model.TowerCraneManagerModel;
import com.king.photo.model.WeiHuProjectModel;
import com.onesafe.util.DateUtil;

import android.app.Activity;
import android.content.Context;
import android.net.UrlQuerySanitizer.ParameterValuePair;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

public class NetworkApi {

    private final static String TAG = "NetworkApi";
    //    private static final String HOST_URL = "http://10.1.3.201:15066";
//    private static final String HOST_URL = "http://10.1.3.201:42174";// 世标
   // private static final String HOST_URL = "http://120.35.11.49:15066";

    //    public static final String HOST_URL = "http://192.168.1.186:5066";// 测试
//        private static final String HOST_URL = "http://10.1.3.68:42173";//朱强
    //private static final String HOST_URL = "http://192.168.1.86:5066"; // 测试 2017.12.21新
    //private static final String HOST_URL = "http://192.168.1.186:5066";//外面工建测试用
    //private static final String ATTENDANCE_URL = "http://ad.jsqqy.com/Handler/MobileAppsHandler.ashx";
    //private static final String ATTENDANCE_URL = "http://192.168.1.186:8086/Handler/MobileAppsHandler.ashx";


    public static final String HOST_URL_UPLOAD_IMAGE = "http://ftp.jsqqy.com:8021";// 正式上传图片
private static final String HOST_URL = "http://api.jsqqy.com";// 正式
    //private static final String HOST_URL = "http://10.1.3.33:8093";
private static final String ATTENDANCE_URL = "http://ad.jsqqy.com/Handler/MobileAppsHandler.ashx";

    //private static final String HOST_URL = "http://10.1.3.201:15066";
    //private static final String HOST_URL = "http://192.168.1.81:25454";
    // 车辆登记
    public static final String AD_URL = "http://api.jsqqy.com";
    //public static final String AD_URL = "http://192.168.1.186:5454";
    public static final String BASE_URL = HOST_URL + "/api.ashx?";
    public static final String BASE_URL_NEW = HOST_URL + "/OpenInterface/CameraBindService.ashx?";
    public static final String BASE_URL_TRAJECTORYMAPHANDLER = HOST_URL + "/OpenInterface/TrajectoryMapHandler.ashx?";
    // 测试地址
    private static final String ACTION_LOGIN = "action=checklogin";
    private static final String ACTION_GET_PROJECT = "action=GetProjectList";
    private static final String ACTION_GET_PROJECT_NEW = "action=getShiftWorkProjectList";
    // 运维影像采集
    private static final String ACTION_SEND_INSIDEDATA = "action=uploadDataAndImage&module=insidecapture";
    // 运维影像采集
    private static final String ACTION_SEND_INSIDEDATA_NEW = "action=updateShiftDetailedByProjId";
    //安装开通采集（保存配置到汇川）
    private static final String ACTION_SAVE_CONFIG_BY_HLHT_API = "?action=SaveConfigByHlhtApi";
    //安装开通采集（保存配置到省站）
    private static final String ACTION_SAVE_CONFIG = "?action=SaveConfig";
    // 监管影像采集
    private static final String ACTION_SEND_DATA = "action=uploadDataAndImage&module=outsidecapture";
    // 保存位置
    private static final String ACTION_SAVE_POSITION = "action=uploadDataForSavePosition&module=ProjectPosition";
    // 视频查看参数定义
    private static final String ACTION_GET_PROJECT_CAMERA_BY = "action=getCameraList&module=outsidevideo";
    // 视频查看参数定义
    private static final String ACTION_GET_PROJECT_CAMERA_BY_NEW = "action=getCamSeqIdByProjIdList";
    //获得单位工程定义
    private static final String ACTION_GET_UNIT_PROJECT = "action=GetUnitProjectByProjId&projId=";
    // apk升级参数定义
    private static final String ACTION_UPDATE = "action=update&type=com.hc.android.huixin";
    // 见证取样参数定义
    private static final String ACTION_GET_SAMPLE_TYPE = "action=GetSampleType&module=outsidejzqy";
    private static final String ACTION_SEND_BINDING_DATA = "action=uploadDataAndImage&module=outsidejzqy";

    // 网络考勤参数定义
    private static final String ACTION_SEND_DATA_MOBILE = "action=uploadDataAndImage&module=outsideAttendance";
    // private static final String ATTENDANCE_URL =
    //基层高程绑定
    private static final String ACTION_BIND_DATBASICLEVE = "action=updateDatBasicLevel&projId=";
    // private static final String ATTENDANCE_URL =
    //安装开通获取设备经纬度
    private static final String ACTION_GET_DRIVER_PLACE_MESSAGE = "?action=GetCamLngAndLat&camSeqId=";
    //获取设备类型

    private static final String ACTION_GET_CRAME_TYPE = "?action=GetTotalTypeByCamTypeId&camTypeId=";
    //获取错误代码信息
    private static final String ACTION_GET_ERROR_MESSAGE = "action=geterror&code=";
    //获取维护项目
    private static final String GET_COMMONWORK_PROJECTLIST = "action=getCommonWorkProjectList&lat=";
    //获取当前用户头像
    private static final String ACTION_GET_HEAD_IMAGE = "action=getHeadImage&userId=";
    //上传当前人脸对比底图
    private static final String ACTION_UPLOAD_PHOTO = "action=upLoadPhoto";
    //添加当前人员轨迹信息接口
    private static final String ACTION_INSERT_DATTRAJECTORY = "action=insertDatTrajectory";
    //获取拆机工单号列表接口
    private static final String ACTION_REMOVE_JOBCODE_LIST = "action=removeJobCodeList&projId=";
    //获取拆机工单Id查询点位信息列表接口
    private static final String ACTION_REMOVE_DAT_CAMERA_CAMNAME_LIST = "action=getRemoveDatCameraCamNameList&projId=";
    // "http://120.35.11.49:17080/Handler/MobileAppsHandler.ashx";

    // private static final String Test_URL = "http://120.35.11.49:26969/"; //
    // private static final String Test_TEMP_URL = "http://api.jsqqy.com/"; //
    // http://120.35.11.49:26969/

    // private static final String Test_URL =
    // "http://120.35.11.49:3333/Services/DataService.ashx";
    //梯控锁测试接口
    private static final String DRIVER_TESTURL = HOST_URL + "/ApiLadderLock.ashx?";
    //private static final String DRIVER_TESTURL = "http://192.168.1.85:26969/ApiLadderLock.ashx?";
    private static final String DRIVER_TESTURL_UPLAOD_IMAGE = HOST_URL_UPLOAD_IMAGE + "/ApiLadderLock.ashx?";
    private static final String ACTION_QUERY_DRIVER_EXIT = "action=getLadderControlLockExists&LadderLockNo=";
    private static final String ACTION_OPEN_DRIVER_LOCK = "action=openLadderControlLock&LadderLockNo=";
    private static final String ACTION_GET_DRIVER = "action=getLadderControlLock&LadderLockNo=";
    private static final String ACTION_DEBIND_DRIVER = "action=bindLadderControlLock&LadderLockNo=";
    private static final String ACTION_GET_DRIVER_NAME = "action=getLadderControlNameList&ProjId=";
    private static final String ACTION_UPLOAD_IAMAGE = "action=upLoadPhoto";
    //新石器地址
    private static final String DRIVER_NEOLITHIC_PATHURL = "http://tkgl.jsqqy.com:8088/WebBackend_buildlock/interface/";
    private static final String DRIVER_ADD_HUMAN = "memberAction!addMember.action";
    private static final String DRIVER_CHECK_HUMAN = "memberAction!checkMember.action?identity=";

    // 根据工程id工单id获取视频探头数据
    public static void queryProjectCameraByWorkOrder(final String projectId, final String removeId, final INetCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BASE_URL_NEW + ACTION_REMOVE_DAT_CAMERA_CAMNAME_LIST + projectId + "&removeId="
                        + removeId;
                String result = HttpUtil.getFromUrl(url);
                if (TextUtils.isEmpty(result)) {
                    Log.e(TAG, "result null");
                    callback.onCallback(false, "result null");
                    return;
                } else {
                    callback.onCallback(true, result);
                }
            }


        }).start();
    }


    public ArrayList<ProjectItem> removeJobCodeList(String projId) {
        ArrayList<ProjectItem> projectList = new ArrayList<ProjectItem>();
        String url = BASE_URL_NEW + ACTION_REMOVE_JOBCODE_LIST + projId;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            String data = json.getString("result");
            if (data.equals("1")) {
                JSONArray jobCodeList = json.getJSONArray("data");
                for (int i = 0; i < jobCodeList.length(); i++) {
                    ProjectItem item = new ProjectItem();
                    item.RemoveId = jobCodeList.getJSONObject(i).getString("RemoveId");
                    item.RemoveJobCode = jobCodeList.getJSONObject(i).getString("RemoveJobCode");
                    item.RemovePathFiles = jobCodeList.getJSONObject(i).getString("RemovePathFiles");
                    projectList.add(item);
                }
                return projectList;
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String insertDatTrajectory(Context context, String projectId, String paramJson, String officeCapId, String StrartTime, String EndTime) {
        String url = BASE_URL_TRAJECTORYMAPHANDLER + ACTION_INSERT_DATTRAJECTORY;
        JSONObject param = new JSONObject();
        try {
            param.put("StrartTime", StrartTime);
            param.put("EndTime", EndTime);
            param.put("userId", PreferenceUtil.getUserId());
            param.put("userAccount", PreferenceUtil.getUserName());
            param.put("projId", projectId);
            param.put("officeCapId", officeCapId);
            param.put("param", paramJson);

        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String result = HttpUtil.postToUrl(url, param.toString());
        try {
            JSONObject json = new JSONObject(result);
            String resultJson = json.getString("result");
            ;

            return resultJson;


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String upLoadPhoto(Context context, String projectId, String projectName, String type, String note, String imageUrl) {
        String url = BASE_URL_TRAJECTORYMAPHANDLER + ACTION_UPLOAD_PHOTO;
        String imageAddress = "";
        JSONObject param = new JSONObject();
        try {

            param.put("userId", PreferenceUtil.getUserId());
            param.put("userMobile", PreferenceUtil.getName());
            param.put("projId", projectId);
            param.put("projectName", projectName);
            param.put("provinceId", PreferenceUtil.getProvince());
            param.put("camId", 0);
            param.put("type", type);
            param.put("note", note);//
            param.put("projcLng", PreferenceUtil.getProjectLng());
            param.put("projcLat", PreferenceUtil.getProjectLat());
            param.put("imgstr", imageUrl);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String result = HttpUtil.postToUrl(url, param.toString());
        try {
            JSONObject json = new JSONObject(result);
            String resultJson = json.getString("result");
            ;
            if (resultJson.equals("1")) {
                imageAddress = json.getString("data");
                return imageAddress;

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return imageAddress;
    }

    public String getHeadImage(Context context) {
        String url = BASE_URL_TRAJECTORYMAPHANDLER + ACTION_GET_HEAD_IMAGE + PreferenceUtil.getUserId();
        //String url = BASE_URL_TRAJECTORYMAPHANDLER+ACTION_GET_HEAD_IMAGE+"100858";

        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            String data = json.getString("result");
            if (data.equals("1")) {
                return json.getString("msg");
            }
            return null;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //考勤获取项目列表
    public AttendanceProjectModel getPathProject(Context context, String projName, String pageIndex, String pageSize, boolean isUploadLoaction) {

        String projcLat = "";
        String projcLng = "";
//        if (isUploadLoaction) {
            projcLat = PreferenceUtil.getProjectLat();
            projcLng = PreferenceUtil.getProjectLng();
//        }
        String userId = PreferenceUtil.getUserId();
        String userName = PreferenceUtil.getUserName(); //用户名
        AttendanceProjectModel data = null;
        String url = BASE_URL_NEW + GET_COMMONWORK_PROJECTLIST + projcLat + "&lng=" + projcLng + "&phoneNum="
                + PreferenceUtil.getName() + "&Province=" + PreferenceUtil.getProvince() +
                "&City=&projName=" + projName + "&typeid=230&pageIndex=" + pageIndex + "&pageSize=" + pageSize + "&userId=" + userId + "&userName=" + userName + "&isFalg=0";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            String resultJson = json.getString("result");
            if (resultJson.equals("1")) {
                data = new Gson().fromJson(result, AttendanceProjectModel.class);
            }
            return data;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    //维护获取项目
    public WeiHuProjectModel getWeiHuProject(Context context, String projName, String pageIndex, String pageSize) {
        String projcLat = PreferenceUtil.getProjectLat();
        String projcLng = PreferenceUtil.getProjectLng();
        String userId = PreferenceUtil.getUserId();
        String userName = PreferenceUtil.getUserName(); //用户名
        WeiHuProjectModel weiHuProjectModel = null;
        String url = BASE_URL_NEW + GET_COMMONWORK_PROJECTLIST + projcLat + "&lng=" + projcLng + "&phoneNum="
                + PreferenceUtil.getName() + "&Province=" + PreferenceUtil.getProvince() +
                "&City=&projName=" + projName + "&typeid=169&pageIndex=" + pageIndex + "&pageSize=" + pageSize + "&userId=" + userId + "&userName=" + userName + "&isFalg=0";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            String resultJson = json.getString("result");
            if (resultJson.equals("1")) {
                ArrayList<WeiHuProjectModel.DataBean> dataBean = new ArrayList<WeiHuProjectModel.DataBean>();
                JSONArray data = json.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    String ProjectId = data.getJSONObject(i).getString("ProjectId");
                    String ProjectName = data.getJSONObject(i).getString("ProjectName");
                    String CamfailReslt = data.getJSONObject(i).getString("CamfailReslt");
                    String CreateTime = data.getJSONObject(i).getString("CreateTime");
                    String FailureNum = data.getJSONObject(i).getString("FailureNum");
                    String ActualDistance = data.getJSONObject(i).getString("ActualDistance");
                    String ProjectLat = data.getJSONObject(i).getString("ProjectLat");
                    String ProjectLng = data.getJSONObject(i).getString("ProjectLng");
                    dataBean.add(new WeiHuProjectModel.DataBean(ProjectId, ProjectName, CamfailReslt, CreateTime, FailureNum, ActualDistance, ProjectLat, ProjectLng));
                }
                weiHuProjectModel = new WeiHuProjectModel(resultJson, json.getString("msg"), json.getString("totalcount"), dataBean);
            }


            return weiHuProjectModel;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return weiHuProjectModel;
    }

    public ErrorMsgModel getErrMsg(String errCode) {
        ErrorMsgModel errorMsgModel = null;
        String url = BASE_URL + ACTION_GET_ERROR_MESSAGE + errCode;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            JSONObject data = json.getJSONObject("data");
            if (!(data == null)) {
                errorMsgModel = new ErrorMsgModel(data.getString("$id"), data.getString("RecordId"), data.getString("Code"), data.getString("Reason"), data.getString("Solution"), data.getString("CreateTime"), data.getString("EntityKey"));
            }
            return errorMsgModel;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return errorMsgModel;
    }

    public boolean bindDatbasicLeve(String projectId, String driverDescNum, String datbasicHigh) {
        String url = HOST_URL + "/ApiDatBasicLevel.ashx?" + ACTION_BIND_DATBASICLEVE + projectId + "&altitudeHeight=" + datbasicHigh + "&camSeqID=" + driverDescNum;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("result").equals("1")) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public String checkHuman(String idNum) {
        String url = DRIVER_NEOLITHIC_PATHURL + DRIVER_CHECK_HUMAN + idNum;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("state").equals("1")) {
                return "";
            } else if (json.getString("state").equals("0")) {
                return json.getString("descript");
            } else if (json.getString("state").equals("-1")) {
                return json.getString("descript");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "网络异常，请检查网络";

    }

    public String addHumanIsImage(HumanModel humanModel, String url_1, String url_2, String url_3) {
        /*String url = DRIVER_NEOLITHIC_PATHURL+DRIVER_CHECK_HUMAN+humanModel.getUserIDNum();
        String result = HttpUtil.getFromUrl(url);*/
        try {
            /*JSONObject json = new JSONObject(result);
            if(json.getString("state").equals("1")){*/
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userName", humanModel.getUserPhone()));
            params.add(new BasicNameValuePair("realName", humanModel.getUserName()));
            params.add(new BasicNameValuePair("identity", humanModel.getUserIDNum()));
            params.add(new BasicNameValuePair("credentials", humanModel.getUserCredentials()));
            params.add(new BasicNameValuePair("projectId", humanModel.getProjectId()));
            params.add(new BasicNameValuePair("type", humanModel.getUserType()));
            params.add(new BasicNameValuePair("userPwd", humanModel.getUserLoginPass()));
            params.add(new BasicNameValuePair("devicePwd", humanModel.getUserOpenPass()));
            params.add(new BasicNameValuePair("url1", url_1));
            params.add(new BasicNameValuePair("url2", url_2));
            params.add(new BasicNameValuePair("url3", url_3));
            String addHumanUrl = DRIVER_NEOLITHIC_PATHURL + DRIVER_ADD_HUMAN;
            String addHumanResult = HttpUtil.postToUrl(addHumanUrl, params);
            JSONObject addHumanJson = new JSONObject(addHumanResult);
            if (addHumanJson.getString("success").equals("true")) {
                return "添加操作员成功";
            } else {
                return addHumanJson.getString("descript");
            }
            /*}else if(json.getString("state").equals("0")){
                return json.getString("descript");
			}else if(json.getString("state").equals("-1")){
				return json.getString("descript");
			}*/
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "网络异常，请检查网络";

    }

    public String addHuman(HumanModel humanModel) {
        String url = DRIVER_NEOLITHIC_PATHURL + DRIVER_CHECK_HUMAN + humanModel.getUserIDNum();
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("state").equals("1")) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("userName", humanModel.getUserPhone()));
                params.add(new BasicNameValuePair("realName", humanModel.getUserName()));
                params.add(new BasicNameValuePair("identity", humanModel.getUserIDNum()));
                params.add(new BasicNameValuePair("credentials", humanModel.getUserCredentials()));
                params.add(new BasicNameValuePair("projectId", humanModel.getProjectId()));
                params.add(new BasicNameValuePair("type", humanModel.getUserType()));
                params.add(new BasicNameValuePair("userPwd", humanModel.getUserLoginPass()));
                String addHumanUrl = DRIVER_NEOLITHIC_PATHURL + DRIVER_ADD_HUMAN;
                String addHumanResult = HttpUtil.postToUrl(addHumanUrl, params);
                JSONObject addHumanJson = new JSONObject(addHumanResult);
                if (addHumanJson.getString("success").equals("true")) {
                    return "添加安全员成功";
                } else {
                    return "添加安全员失败";
                }
            } else if (json.getString("state").equals("0")) {
                return json.getString("descript");
            } else if (json.getString("state").equals("-1")) {
                return json.getString("descript");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        return "网络异常，请检查网络";

    }

    public String uploadImage(String imageUrl, String type) {
        String imageAddress = "";
        JSONObject param = new JSONObject();
        try {
            param.put("type", type);
            param.put("img", imageUrl);

        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String result = HttpUtil.postToUrl(DRIVER_TESTURL_UPLAOD_IMAGE + ACTION_UPLOAD_IAMAGE, param.toString());
        try {
            JSONObject json = new JSONObject(result);
            String resultJson = json.getString("result");
            ;
            if (resultJson.equals("1")) {
                imageAddress = json.getString("img" + type);
                return imageAddress;

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return imageAddress;

    }

    public ArrayList<DriverModel> getLadderControlNameList(String projId) {
        String url = DRIVER_TESTURL + ACTION_GET_DRIVER_NAME + projId;
        String result = HttpUtil.getFromUrl(url);
        ArrayList<DriverModel> list = new ArrayList<DriverModel>();
        try {
            JSONObject json = new JSONObject(result);
            String resultJson = json.getString("result");
            ;
            if (resultJson.equals("1")) {
                JSONArray dataArray = json.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    String camID = dataArray.getJSONObject(i).getString("CamId");
                    String camName = dataArray.getJSONObject(i).getString("CamName");
                    DriverModel driverModel = new DriverModel(camID, camName);
                    list.add(driverModel);
                }
                return list;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;

    }

    public JsonModel debindDriver(String code) {
        String url = DRIVER_TESTURL + ACTION_DEBIND_DRIVER + code;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            JsonModel jsonModel = new JsonModel(json.getString("result"), json.getString("msg"));
            return jsonModel;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public LockDriverModel getDriverMessage(String code) {
        String url = DRIVER_TESTURL + ACTION_GET_DRIVER + code;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("result").equals("1")) {
                String driverName = code;
                String driverMac = json.getString("MacAddress");
                String projectName = json.getString("ProjectName");
                String driverStatic = json.getString("BindStatus");
                String driverInstallPlace = json.getString("InstallationLocation");
                String driverCamName = json.getString("CamId") + "_" + json.getString("CamName");
                Log.e("driverCamName", driverCamName);
                LockDriverModel lockDriverModel = new LockDriverModel(driverName, driverMac, projectName, driverStatic, driverInstallPlace, driverCamName);
                return lockDriverModel;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public JsonModel OpenLadderControlLock(String code, String userId, String userName, String userAccount, String MACAddress, String projectID, String InstallationLocation, String camId) {
        //String url = "http://10.1.3.160:56017/ApiLadderLock.ashx?action=openLadderControlLock&LadderLockNo=D19406CB0&MACAddress=C8:FD:19:40:6C:B0&ProjId=&InstallationLocation=%E6%B5%8B%E8%AF%95&CamId=1014468";


        String url = DRIVER_TESTURL + ACTION_OPEN_DRIVER_LOCK + code + "&userName=" + userName + "&userId=" + userId + "&userAccount=" + userAccount + "&MACAddress=" + MACAddress + "&ProjId=" + projectID + "&InstallationLocation=" + InstallationLocation + "&CamId=" + camId;
//		String url = DRIVER_TESTURL + ACTION_OPEN_DRIVER_LOCK+code+"&MACAddress="+MACAddress+"&ProjId="+projectID+"&InstallationLocation="+InstallationLocation+"&CamId="+camId;
        String result = HttpUtil.getFromUrl(url);

        try {
            JSONObject json = new JSONObject(result);
            JsonModel jsonModel = new JsonModel(json.getString("result"), json.getString("msg"));
            return jsonModel;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //查询梯控设备是否入库
    public String QueryDriverExit(String code) {
        String url = DRIVER_TESTURL + ACTION_QUERY_DRIVER_EXIT + code;
        String result = HttpUtil.getFromUrl(url);
        String data = "-1";
        try {
            JSONObject json = new JSONObject(result);
            data = json.getString("result");
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    // 登陆接口
    public static void login(final String name, final String pwd, final String imei, final INetCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BASE_URL + ACTION_LOGIN + "&Province=" + "&Name=" + name + "&Pwd=" + pwd
                        + "&module=outside" + "&Imei=";
                String result = HttpUtil.getFromUrl(url);

                if (TextUtils.isEmpty(result)) {
                    Log.e("login", "result null");
                    callback.onCallback(false, "result null");
                    return;
                }
                try {
                    JSONArray dataArray = new JSONArray(result);
                    JSONObject json = dataArray.getJSONObject(0);
                    if (json.getString("UserAccount").equals(name)) {
                        callback.onCallback(true, result);
                    } else {
                        callback.onCallback(false, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onCallback(false, e.getMessage());
                }
            }
        }).start();
    }

    // 用户账号权限
    public ArrayList<ModuleItem> GetMDJsonByUserAndSysId(String name, String pwd) {
        ArrayList<ModuleItem> dataList = new ArrayList<ModuleItem>();
        String url = ATTENDANCE_URL + "?interfaceName=GetMDJsonByUserAndSysId&userName=" + name + "&userPsw=" + pwd
                + "&SysId=23";
        String result = HttpUtil.getFromUrl(url);

        try {
            JSONObject json = new JSONObject(result);
            JSONArray SysModule = json.getJSONArray("SysModule");
            for (int i = 0; i < SysModule.length(); i++) {
                JSONObject item = SysModule.getJSONObject(i);
                ModuleItem module = new ModuleItem();
                module.id = item.optInt("id", 0);
                module.name = item.optString("Name", "");
                dataList.add(module);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // 检查升级
    public static void checkUpdate(final INetCallback listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HttpUtil.getFromUrl(BASE_URL + ACTION_UPDATE);
                if (TextUtils.isEmpty(result)) {
                    Log.e("checkUpdate", "result null");
                    listener.onCallback(false, "result null");
                    return;
                } else {
                    listener.onCallback(true, result);
                }
            }
        }).start();
    }

    // ///////////////////////////视频查看模块start////////////////////
    // 视频查看获取视频工程
    public static void getVideoProjectList(final Context context, final String name, final INetCallback callback) {
        String projcLat = PreferenceUtil.getProjectLat();
        String projcLng = PreferenceUtil.getProjectLng();
        String url = BASE_URL + ACTION_GET_PROJECT + "&lat=" + projcLat + "&lng=" + projcLng + "&phoneNum="
                + PreferenceUtil.getName() + "&Province=" + PreferenceUtil.getProvince() + "&City="
                + PreferenceUtil.getCity() + "&module=outsidevideo" + "&q=" + name;
        String result = HttpUtil.getFromUrl(url);

        if (TextUtils.isEmpty(result)) {
            Log.e("getBindingProjectItem", "result null");
            callback.onCallback(false, "result null");
            return;
        } else {
            callback.onCallback(true, result);
        }
    }

    // 工程数据解析（公用）
    public static ArrayList<ProjectItem> parstToProjectList(String data) {
        ArrayList<ProjectItem> dataList = new ArrayList<ProjectItem>();
        try {
            JSONArray all = new JSONArray(data);
            for (int i = 0; i < all.length(); i++) {
                JSONObject result = all.getJSONObject(i);
                ProjectItem projectItem = new ProjectItem();
                projectItem.projectId = result.getString("ProjectId");
                projectItem.ProjectName = result.getString("ProjectName");
                projectItem.ProjectLat = result.getString("ProjectLat");
                projectItem.ProjectLng = result.getString("ProjectLng");
                dataList.add(projectItem);
            }
        } catch (Exception e) {
            Log.e("parstToProjectList", e.getMessage());
        }
        return dataList;
    }

    // 工程数据解析
    public static ArrayList<ProjectMoveItem> parstToProjectMoveList(String data) {
        ArrayList<ProjectMoveItem> dataList = new ArrayList<ProjectMoveItem>();
        try {
            JSONObject all = new JSONObject(data);

            JSONArray dataJsons = all.getJSONArray("data");
            for (int i = 0; i < dataJsons.length(); i++) {
                JSONObject result = dataJsons.getJSONObject(i);
                ProjectMoveItem projectItem = new ProjectMoveItem();
                projectItem.ProjID = result.getString("ProjID");
                projectItem.ProjName = result.getString("ProjName");
                projectItem.ProjAddress = result.getString("ProjAddress");
                projectItem.DshwId = result.getString("DshwId");
                projectItem.DshwStatus = result.getString("DshwStatus");
                dataList.add(projectItem);
            }
        } catch (Exception e) {
            Log.e("parstToProjectList", e.getMessage());
        }
        return dataList;
    }

    // 根据工程id获取视频探头数据
    public static void queryProjectCameraBy(final String projectId, final String projectTypeId, final INetCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BASE_URL + ACTION_GET_PROJECT_CAMERA_BY + "&projid=" + projectId + "&typeid="
                        + projectTypeId;
                String result = HttpUtil.getFromUrl(url);
                if (TextUtils.isEmpty(result)) {
                    Log.e(TAG, "result null");
                    callback.onCallback(false, "result null");
                    return;
                } else {
                    callback.onCallback(true, result);
                }
            }
        }).start();
    }

    // 根据工程id获取视频探头数据
    public static void queryProjectCameraByNew(final String projectId, final String dshwId, final INetCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BASE_URL_NEW + ACTION_GET_PROJECT_CAMERA_BY_NEW;
//				String data = "&projId=" + projectId + "&dshwId="+ dshwId;
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("projId", projectId));
                data.add(new BasicNameValuePair("dshwId", dshwId));
                String result = HttpUtil.postToUrl(url, data);
                if (TextUtils.isEmpty(result)) {
                    Log.e(TAG, "result null");
                    callback.onCallback(false, "result null");
                    return;
                } else {
                    callback.onCallback(true, result);
                }
            }
        }).start();
    }

    // 根据工程id获取单位工程数据
    public static void queryUnitProject(final String projectId, final INetCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = HOST_URL + "/Services/CameraBindService.ashx?" + ACTION_GET_UNIT_PROJECT + projectId;
                String result = HttpUtil.getFromUrl(url);
                if (TextUtils.isEmpty(result)) {
                    Log.e(TAG, "result null");
                    callback.onCallback(false, "result null");
                    return;
                } else {
                    callback.onCallback(true, result);
                }
            }
        }).start();
    }

    // 单位工程数据解析
    public static ArrayList<UnitProjectItem> parstToUnitProjectList(String data) {
        ArrayList<UnitProjectItem> dataList = new ArrayList<UnitProjectItem>();
        try {
            JSONObject jsonData = new JSONObject(data);
            JSONArray all = jsonData.getJSONArray("data");
            for (int i = 0; i < all.length(); i++) {
                JSONObject result = all.getJSONObject(i);
                UnitProjectItem unitProjectItem = new UnitProjectItem();
                unitProjectItem.projectId = result.optString("itemId", "");
                unitProjectItem.ProjectName = result.optString("itemName", "");
                dataList.add(unitProjectItem);
            }
        } catch (Exception e) {
            Log.e("parstToUnitProjectList", e.getMessage());
        }
        return dataList;
    }

    // 视频查看解析视频列表（公用）
    public static ArrayList<CameraItem> parstToCameraList(String data) {
        ArrayList<CameraItem> dataList = new ArrayList<CameraItem>();
        try {
            JSONArray all = new JSONArray(data);
            for (int i = 0; i < all.length(); i++) {
                JSONObject result = all.getJSONObject(i);
                CameraItem projectItem = new CameraItem();
                projectItem.CamId = result.optString("CamId", "");
                projectItem.CamName = result.optString("CamName", "");
                projectItem.VideoName = result.optString("VideoName", "");
                projectItem.Cam_LoginName = result.optString("Cam_LoginName", "");
                projectItem.Cam_LoginPsw = result.optLong("Cam_LoginPsw", 0);
                projectItem.areaCode = result.optInt("areaCode", 0);
                projectItem.ptzlag = result.optString("ptzlag", "0");
                projectItem.VideoId = result.optString("VideoId", "");
                projectItem.IsOnline = result.optBoolean("IsOnline", true);
                projectItem.NewCamNam = result.optString("NewCamName", "");
                projectItem.CamInstallPlace = result.optString("CamInstalPlace", "");
                projectItem.CamTypeId = result.optString("CamTypeId", "");
                projectItem.CamDateInstall = result.optString("CamDateInstall", "");
                dataList.add(projectItem);
            }
        } catch (Exception e) {
            Log.e("parstToCameraList", e.getMessage());
        }
        return dataList;
    }

    // 视频查看解析视频列表（公用）
    public static ArrayList<CameraItemNew> parstToCameraNewList(String data) {
        ArrayList<CameraItemNew> dataList = new ArrayList<CameraItemNew>();
        try {
            JSONObject all = new JSONObject(data);
            JSONArray dataJsons = all.getJSONArray("data");
            for (int i = 0; i < dataJsons.length(); i++) {
                JSONObject result = dataJsons.getJSONObject(i);
                CameraItemNew projectItem = new CameraItemNew();
                projectItem.CamId = result.optString("CamId", "");
                projectItem.CamName = result.optString("CamName", "");
                projectItem.CamInstalPlace = result.optString("CamInstalPlace", "");
                projectItem.DshwId = result.optString("DshwId", "");
                projectItem.DsdtID = result.optString("DsdtID", "");
                dataList.add(projectItem);
            }
        } catch (Exception e) {
            Log.e("parstToCameraList", e.getMessage());
        }
        return dataList;
    }
    // ///////////////////////////视频查看模块end////////////////////

    // ///////////////////////////运维影像采集start////////////////////
    public String getCarmType(String carmTypeId) {
        String data = "";
        String url = HOST_URL + "/Services/CameraBindService.ashx" + ACTION_GET_CRAME_TYPE + carmTypeId;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getString("result").equals("1")) {
                data = jsonObject.getString("data");
                return data;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;

    }

    //安装开通获取经纬度
    public PlaceMessgaeItem getPlaceMessage(String driverDescNum) {
        PlaceMessgaeItem placeMessgaeItem = null;
        //String url = "http://10.1.3.66:26969/Services/CameraBindService.ashx"+ACTION_GET_DRIVER_PLACE_MESSAGE+driverDescNum;
        String url = HOST_URL + "/Services/CameraBindService.ashx" + ACTION_GET_DRIVER_PLACE_MESSAGE + driverDescNum;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.getString("result").equals("1")) {
                JSONObject data = jsonObject.getJSONObject("data");
                String longtitude = data.getString("Longtitude");
                String latitude = data.getString("Latitude");
                String alpha = data.getString("Alpha");
                placeMessgaeItem = new PlaceMessgaeItem(longtitude, latitude, alpha);
                return placeMessgaeItem;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return placeMessgaeItem;

    }

    //安装开通上传数据(省站)
    public static JsonModel sendIntallOpenDataToSZ(Context context, SendDataBean data) {
        String result = "";
        JSONObject param = new JSONObject();
        try {
            param.put("ProjectId", data.ProjectId);
            param.put("ProjectName", data.ProjectName);
            param.put("Note", data.Note);
            param.put("ResponsibilitySubject", data.ResponsibilitySubject);
            param.put("UserName", data.UserName);
            param.put("ProjcLat", data.ProjcLat);
            param.put("ProjcLng", data.ProjcLng);
            param.put("Province", PreferenceUtil.getProvince());
            param.put("Type", data.Type);
            param.put("Progress", data.Progress);
            param.put("IsSaveLocation", data.IsSaveLocation);
            param.put("CameraId", data.CameraId);
            param.put("ispay", data.ispay);
            param.put("typeid", data.projectTypeId);
            param.put("payid", data.payId);
            param.put("UserId", data.UserId);
            param.put("CamSeqId", data.DriverNum);
            param.put("CamItemId", data.UnitProjectId);
            param.put("CamName", data.CamName);
            param.put("CamInstalPlace", data.InstallPlace);
            param.put("DevLng", data.DriverJingDu);
            param.put("DevLat", data.DriverWeiDu);
            param.put("Imgstr", data.ImgStr);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(param.toString());
        String resultHttp = HttpUtil.postToUrl(HOST_URL + "/Services/CameraBindService.ashx" + ACTION_SAVE_CONFIG, param.toString());

        try {
            JSONObject dataJson = new JSONObject(resultHttp);
            JsonModel jsonModel = new JsonModel(dataJson.getString("result"), dataJson.getString("msg"));
            if (dataJson.getString("result").equals("0")) {
                return jsonModel;
            } else if (dataJson.getString("result").equals("1")) {
                return jsonModel;
            } else {
                return jsonModel;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;


    }

    //安装开通上传数据（汇川）
    public static JsonModel sendIntallOpenData(Context context, SendDataBean data) {
        JSONObject param = new JSONObject();
        try {
            param.put("ProjectId", data.ProjectId);
            param.put("ProjectName", data.ProjectName);
            param.put("Note", data.Note);
            param.put("ResponsibilitySubject", data.ResponsibilitySubject);
            param.put("UserName", data.UserName);
            param.put("ProjcLat", data.ProjcLat);
            param.put("ProjcLng", data.ProjcLng);
            param.put("Province", PreferenceUtil.getProvince());
            param.put("Type", data.Type);
            param.put("Progress", data.Progress);
            param.put("IsSaveLocation", data.IsSaveLocation);
            param.put("CameraId", data.CameraId);
            param.put("ispay", data.ispay);
            param.put("typeid", data.projectTypeId);
            param.put("payid", data.payId);
            param.put("Imgstr", data.ImgStr);
            param.put("UserId", data.UserId);
            param.put("CamSeqId", data.DriverNum);
            param.put("CamItemId", data.UnitProjectId);
            param.put("CamName", data.CamName);
            param.put("CamInstalPlace", data.InstallPlace);
            param.put("DevLng", data.DriverJingDu);
            param.put("DevLat", data.DriverWeiDu);
            param.put("Alpha", data.DriverAlpha);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //http://120.35.11.49:26969/Services/CameraBindService.ashx?action=SaveConfigByHlhtApi
        System.out.println(param.toString());
        String resultHttp = HttpUtil.postToUrl(HOST_URL + "/Services/CameraBindService.ashx" + ACTION_SAVE_CONFIG_BY_HLHT_API, param.toString());

        try {
            JSONObject dataJson = new JSONObject(resultHttp);
            Log.e("resultHttp", dataJson.getString("result").equals("2") + "");
            JsonModel jsonModel = new JsonModel(dataJson.getString("result"), dataJson.getString("msg"));
            if (dataJson.getString("result").equals("0")) {
                return jsonModel;
            } else if (dataJson.getString("result").equals("1")) {
                return jsonModel;
            } else if (dataJson.getString("result").equals("2")) {
                return jsonModel;
            } else if (dataJson.getString("result").equals("3")) {
                return jsonModel;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //拆机上传数据
    public static void sendDisassembleData(Context context, SendDataBean data, final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {
            param.put("ProjectId", data.ProjectId);
            param.put("Imgstr", data.ImgstrDev);
            param.put("ImgstrAculvert", data.ImgstrAculvert);//工程图片
            param.put("Note", data.Note);
            param.put("UserName", data.UserName);
            param.put("userid", PreferenceUtil.getUserId());
            param.put("ProjectName", data.ProjectName);
            param.put("Type", data.Type);
            param.put("ProjcLat", data.ProjcLat);
            param.put("ProjcLng", data.ProjcLng);
            param.put("Province", PreferenceUtil.getProvince());
            param.put("CameraId", data.CameraId);
            param.put("Progress", data.Progress);
            param.put("IsSaveLocation", data.IsSaveLocation);
            param.put("payid", data.payId);
            param.put("typeid", data.projectTypeId);
            param.put("ispay", data.ispay);
            param.put("removeId", data.removeId);
            param.put("ResponsibilitySubject", data.ResponsibilitySubject);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
        Log.e("网络请求", "请求：" + BASE_URL_NEW + "action=uploadRemoveDataAndImage" + param.toString());
        String result = HttpUtil.postToUrl(BASE_URL_NEW + "action=uploadRemoveDataAndImage", param.toString());
        Log.e("网络请求", "请求结果：" + result);
        if (TextUtils.isEmpty(result)) {
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("result").equals("1")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 运维影像采集上传数据
    public static void sendInsideData(Context context, SendDataBean data, final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {
            param.put("ProjectId", data.ProjectId);
            param.put("ProjectName", data.ProjectName);
            param.put("Note", data.Note);
            param.put("ResponsibilitySubject", data.ResponsibilitySubject);
            param.put("UserName", data.UserName);
            param.put("ProjcLat", data.ProjcLat);
            param.put("ProjcLng", data.ProjcLng);
            param.put("Province", PreferenceUtil.getProvince());
            param.put("Type", data.Type);
            param.put("Progress", data.Progress);
            param.put("IsSaveLocation", data.IsSaveLocation);
            param.put("CameraId", data.CameraId);
            param.put("ispay", data.ispay);
            param.put("typeid", data.projectTypeId);
            param.put("payid", data.payId);
            param.put("Imgstr", data.ImgStr);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }

        System.out.println(param.toString());
        String result = HttpUtil.postToUrl(BASE_URL + ACTION_SEND_INSIDEDATA, param.toString());

        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("Result").equals("True")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 运维影像采集上传数据
    public static void sendInsideNewData(Context context, SendRemoveDataBean data, final INetCallback callback) {
        //
        // String url = BASE_URL_NEW + ACTION_SEND_INSIDEDATA_NEW + "&typeId=" +
        // data.typeId + "&userId=" + data.userId + "&note="
        // + data.note + "&capId=" + data.capId
        // + "&projId=" + data.projId + "&dshwId=" + data.dshwId + "&camId=" +
        // data.camId;
//		// String result = HttpUtil.getFromUrl(url.replaceAll(" ", "%20"));
        String url = BASE_URL_NEW + ACTION_SEND_INSIDEDATA_NEW;
        JSONObject param = new JSONObject();
        try {
            param.put("typeId", data.typeId);
            param.put("userId", data.userId);
            param.put("note", data.note);//
            param.put("projId", data.projId);
            param.put("dshwId", data.dshwId);
            param.put("camId", data.camId);
            param.put("type", data.type);
            param.put("projectName", data.projectName);
//			param.put("projectName", "项目名");
            param.put("userMobile", data.userMobile);
            param.put("projcLng", data.projcLng);
            param.put("projcLat", data.projcLat);
            param.put("provinceId", data.provinceId);
//			param.put("provinceId", "福州");
            param.put("imgstr", data.imgstr);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }

//		 String data = "&typeId="+dataBean.typeId+
//				 "&userId="+dataBean.userId+
//				 "&note="+dataBean.note+
//				 "&projId="+dataBean.projId+
//				 "&dshwId="+dataBean.dshwId+
//				 "&camId="+dataBean.camId+
//				 "&type="+dataBean.type+
//				 "&projectName="+dataBean.projectName+
//				 "&userMobile="+dataBean.userMobile+
//				 "&projcLng="+dataBean.projcLng+
//				 "&projcLat="+dataBean.projcLat+
//				 "&provinceId="+dataBean.provinceId+
//				 "&imgstr="+dataBean.imgstr;
        String result = HttpUtil.postToUrl(url, param.toString());
        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("result").equals("1")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 运维影像采集获取施工类型
    public ArrayList<ConstrucTypeItem> GetConstrucType() {
        ArrayList<ConstrucTypeItem> dataList = new ArrayList<ConstrucTypeItem>();
        String url = ATTENDANCE_URL + "?interfaceName=GetConstrucType";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("Rows");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                ConstrucTypeItem item = new ConstrucTypeItem();
                item.text = itemJson.optString("text", "");
                item.id = itemJson.optInt("id", 0);
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // 运维影像采集获取进度描述
    public ArrayList<MaintenanceTypeItem> GetMaintenanceType(String typeid) {
        ArrayList<MaintenanceTypeItem> dataList = new ArrayList<MaintenanceTypeItem>();
        String url = ATTENDANCE_URL + "?interfaceName=GetMaintenanceType&typeid=" + typeid;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("Rows");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                MaintenanceTypeItem item = new MaintenanceTypeItem();
                item.text = itemJson.optString("text", "");
                item.id = itemJson.optInt("id", 0);
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // 梯控设备，高程获取项目
    public static void queryProjectData(final Context context, final String name, final INetCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String projcLat = PreferenceUtil.getProjectLat();
                String projcLng = PreferenceUtil.getProjectLng();
                String url = BASE_URL + ACTION_GET_PROJECT + "&lat=" + projcLat + "&lng=" + projcLng + "&phoneNum="
                        + PreferenceUtil.getName() + "&Province=" + PreferenceUtil.getProvince()
                        + "&City=" + PreferenceUtil.getCity() + "&q=" + name + "&typeid="
                        + "&module=insidecapture";

                String result = HttpUtil.getFromUrl(url.replaceAll(" ", "%20"));
                if (TextUtils.isEmpty(result)) {
                    Log.e("queryProjectData", "result null");
                    callback.onCallback(false, "result null");
                    return;
                } else {
                    callback.onCallback(true, result);
                }
            }
        }).start();
    }

    // 运维影像采集获取工程列表
    public static void queryProjectData(final Context context, final String name, final String id,
                                        final INetCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String projcLat = PreferenceUtil.getProjectLat();
                String projcLng = PreferenceUtil.getProjectLng();
                String url = BASE_URL + ACTION_GET_PROJECT + "&lat=" + projcLat + "&lng=" + projcLng + "&phoneNum="
                        + PreferenceUtil.getName() + "&Province=" + PreferenceUtil.getProvince()
                        + "&City=" + PreferenceUtil.getCity() + "&q=" + name + "&typeid=" + id
                        + "&module=insidecapture";

                String result = HttpUtil.getFromUrl(url.replaceAll(" ", "%20"));
                if (TextUtils.isEmpty(result)) {
                    Log.e("queryProjectData", "result null");
                    callback.onCallback(false, "result null");
                    return;
                } else {
                    callback.onCallback(true, result);
                }
            }
        }).start();
    }

    // 运维影像采集获取工程列表
    public static void queryNewProjectData(final Context context, final String name, final String id,
                                           final INetCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String projcLat = PreferenceUtil.getProjectLat();
                String projcLng = PreferenceUtil.getProjectLng();
//				 String url = BASE_URL_NEW + ACTION_GET_PROJECT_NEW +
//				 "&projName=" + name + "&type=" + id;
//
//				 String result = HttpUtil.getFromUrl(url.replaceAll(" ",
//				 "%20"));
                String url = BASE_URL_NEW + ACTION_GET_PROJECT_NEW;
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("projName", name));
                data.add(new BasicNameValuePair("type", id));
                String result = HttpUtil.postToUrl(url, data);
                if (TextUtils.isEmpty(result)) {
                    Log.e("queryProjectData", "result null");
                    callback.onCallback(false, "result null");
                    return;
                } else {
                    callback.onCallback(true, result);
                }
            }
        }).start();
    }

    // 获取所有用户名
    public ArrayList<UsersItem> GetUsers() {
        ArrayList<UsersItem> dataList = new ArrayList<UsersItem>();
        String url = BASE_URL + "action=GetUsers&module=insideCapture";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONArray Rows = new JSONArray(result);
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                UsersItem item = new UsersItem();
                item.UserAccount = itemJson.optString("UserAccount", "");
                item.UserName = itemJson.optString("UserName", "");
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public ArrayList<UsersItem> GetUsersGongJian() {
        ArrayList<UsersItem> dataList = new ArrayList<UsersItem>();
        String url = BASE_URL + "action=GetProjectBuildUsers&module=insideCapture";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONArray Rows = new JSONArray(result);
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                UsersItem item = new UsersItem();
                item.UserAccount = itemJson.optString("UserAccount", "");
                item.UserName = itemJson.optString("UserName", "");
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }
    // ///////////////////////////运维影像采集end////////////////////

    // ///////////////////////////监管影像采集模块start////////////////////

    // 监管影像采集上传数据
    public static void sendData(Context context, String UUID, SendPhotoDataBean data, final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {
            param.put("ProjectId", data.ProjectId);// 工程id
            param.put("ProjectName", data.ProjectName);// 工程名称
            param.put("Note", data.Note);// 现场描述
            param.put("Imgstr", data.ImgStr);// 图片
            param.put("UserName", data.UserName);// 用户名
            param.put("ProjcLat", data.ProjcLat);//
            param.put("ProjcLng", data.ProjcLng);
            param.put("Province", PreferenceUtil.getProvince());
            param.put("BuildNum", data.BuildNum);// 抓拍地点
            param.put("PlaceType", data.PlaceType);// 分部工程
            param.put("PositionType", data.PositionType);// 部位类型
            param.put("Type", data.Type);// 子分部工程
            param.put("CameraId", data.CameraId);// 具体部位
            param.put("Progress", data.Progress);// 评价
            param.put("NormRule", data.NormRules);// 规范条例
            // param.put("keytype", data.keytype);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
        String result = HttpUtil.postToUrl(AD_URL + "/api.ashx?" + ACTION_SEND_DATA + "&uuid=" + UUID,
                param.toString());

        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("Result").equals("True")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 工程搜索条件项
    public ArrayList<MaintenanceTypeItem> GetKeytype() {
        ArrayList<MaintenanceTypeItem> dataList = new ArrayList<MaintenanceTypeItem>();
        String url = ATTENDANCE_URL + "?interfaceName=GetMaintenanceType&DirType=1007";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("Rows");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                MaintenanceTypeItem item = new MaintenanceTypeItem();
                item.text = itemJson.optString("text", "");
                item.id = itemJson.optInt("id", 0);
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // 质量规范要求
    public ArrayList<MaintenanceTypeItem> GetPhotoNormRequire() {
        ArrayList<MaintenanceTypeItem> dataList = new ArrayList<MaintenanceTypeItem>();
        String url = HOST_URL + "/Services/DataService.ashx?action=getDataByCode&code=zljgtl";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("data");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                MaintenanceTypeItem item = new MaintenanceTypeItem();
                item.text = itemJson.optString("Name", "");
                item.id = itemJson.optInt("Value", 0);
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // ----安全监控~~
    // 工程搜索条件项
    // 分项检查
    public ArrayList<MaintenanceTypeItem> GetSafePlaceType() {
        ArrayList<MaintenanceTypeItem> dataList = new ArrayList<MaintenanceTypeItem>();
        String url = HOST_URL + "/Services/DataService.ashx?action=getDataByCode&code=aqjg";
        String result = HttpUtil.getFromUrl(url);

        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("data");

            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                MaintenanceTypeItem item = new MaintenanceTypeItem();
                item.text = itemJson.optString("Name", "");
                item.id = itemJson.optInt("Value", 0);
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    public static void sendSafeData(Context context, String UUID, SendSafeDataBean data, final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {
            param.put("ProjectId", data.ProjectId);// 工程id
            param.put("ProjectName", data.ProjectName);// 工程名称
            param.put("Note", data.Note);// 现场描述
            param.put("Imgstr", data.ImgStr);// 图片
            param.put("UserName", data.UserName);// 用户名
            param.put("ProjcLat", data.ProjcLat);//
            param.put("ProjcLng", data.ProjcLng);
            param.put("Province", PreferenceUtil.getProvince());
            param.put("BuildNum", data.BuildNum);// 抓拍地点
            param.put("PlaceType", data.PlaceType);// 分项检查
            param.put("NormRule", data.NormRules);// 规范条例
            param.put("Type", data.Type);// 检查项目
            param.put("CameraId", data.CameraId);// 具体部位
            param.put("Progress", data.Progress);// 评价
            // param.put("keytype", data.keytype);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
        String result = HttpUtil.postToUrl(AD_URL + "/ApiServer.ashx?action=safetysupervision&uuid=" + UUID,
                param.toString());

        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("Result").equals("True")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 检查项目
    public ArrayList<MaintenanceTypeItem> GetSafeType(int mDirType) {
        ArrayList<MaintenanceTypeItem> dataList = new ArrayList<MaintenanceTypeItem>();
        String url = HOST_URL + "/Services/DataService.ashx?action=getDataByType&type=" + mDirType;

        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);

            JSONArray Rows = json.getJSONArray("data");

            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                MaintenanceTypeItem item = new MaintenanceTypeItem();
                item.text = itemJson.optString("Name", "");
                item.id = itemJson.optInt("Value", 0);

                dataList.add(item);
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return dataList;
    }

    // 获取安全监管子条例
    public ArrayList<MaintenanceTypeItem> GetSafeRulesType(int mDirType) {
        ArrayList<MaintenanceTypeItem> dataList = new ArrayList<MaintenanceTypeItem>();
        String url = HOST_URL + "/Services/DataService.ashx?action=getMoreDataByType&type=" + mDirType;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("data");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                MaintenanceTypeItem item = new MaintenanceTypeItem();
                item.text = itemJson.optString("Name", "");
                item.id = itemJson.optInt("Value", 0);
                item.hasSubset = itemJson.optInt("HasSubset", 0);
                item.explain = itemJson.optString("Explain", "");
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // 安全规范要求
    public ArrayList<MaintenanceTypeItem> GetSafeNormRequire() {
        ArrayList<MaintenanceTypeItem> dataList = new ArrayList<MaintenanceTypeItem>();
        String url = HOST_URL + "/Services/DataService.ashx?action=getDataByCode&code=aqjgtl";
        String result = HttpUtil.getFromUrl(url);

        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("data");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                MaintenanceTypeItem item = new MaintenanceTypeItem();
                item.text = itemJson.optString("Name", "");
                item.id = itemJson.optInt("Value", 0);
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // 发送车辆图片
    public static void sendVehicleImgData(Context context, String data, String ProjcLat, String ProjcLng, String CarNum,
                                          String LocalPostion, final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {
            param.put("ImgStr", data);
            param.put("ProjcLat", ProjcLat);
            param.put("ProjcLng", ProjcLng);
            param.put("CarNum", CarNum);
            param.put("LocalPostion", LocalPostion);

        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
        String result = HttpUtil.postToUrl(AD_URL + "/ApiServer.ashx?action=InterfaceGetFileId", param.toString());
        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("Result").equals("True")) {

                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 车辆预订
    public static void sendVehicleDebitReserveData(Context context, String CarDebitId, SendInVehicleDataBean data,
                                                   final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {
            param.put("CarDebitId", data.CarDebitId);
            param.put("ImgStr", data.ImgStr);
            param.put("OutProjcLat", data.OutProjcLat);
            param.put("OutProjcLng", data.OutProjcLng);
            param.put("LocalPostion", data.LocalPostion);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
        String result = HttpUtil.postToUrl(AD_URL + "/ApiServer.ashx?action=UpdatePic", param.toString());
        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            // if (json.getString("Result").equals("True")) {
            if (json.getString("Result").equals("True")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 车辆归还
    public static void sendVehicleDebitRevertData(Context context, SendInVehicleDataBean data,
                                                  final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {
            param.put("CarDebitId", data.CarDebitId);
            param.put("CarInsideTime", data.CarInsideTime);
            param.put("CarInKm", data.CarInKm);
            param.put("CarInOil", data.CarInOil);
            param.put("CarInsideAddress", data.CarInsideAddress);
            param.put("CarInSituation", data.CarInSituation);
            param.put("CarInsideTime", data.CarInsideTime);
            param.put("CarProjcLngIn", data.CarProjcLngIn);
            param.put("CarProjcLatIn", data.CarProjcLatIn);
            param.put("CarImgStrIn", data.CarImgStrIn);
            param.put("LocalPostion", data.LocalPostion);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
        String result = HttpUtil.postToUrl(AD_URL + "/ApiServer.ashx?action=Update", param.toString());
        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("Result").equals("True")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 车辆借出
    public static void sendVehicleDebitData(Context context, SendInVehicleDataBean data, final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {
            param.put("DebitName", data.DebitName);
            param.put("LocalPostion", data.LocalPostion);
            param.put("licenseNum", data.licenseNum);
            param.put("idCard", data.idCard);
            param.put("TEL", data.TEL);
            param.put("CarNum", data.CarNum);
            param.put("OutKm", data.OutKm);
            param.put("OutOil", data.OutOil);
            param.put("OutSituation", data.OutSituation);
            param.put("ImgStr", data.ImgStr);
            param.put("ProjcLat", data.OutProjcLat);
            param.put("ProjcLng", data.OutProjcLng);
            param.put("CarOutsideTime", data.CarOutsideTime);
            param.put("ManagerName", data.VehicleUser);// 登录账户
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
        String result = HttpUtil.postToUrl(AD_URL + "/ApiServer.ashx?action=InterfaceInsertCM", param.toString());
        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("Result").equals("True")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 读取车辆状态
    public ArrayList<DriverManageTypeItem> GetDriverState() {
        ArrayList<DriverManageTypeItem> dataList = new ArrayList<DriverManageTypeItem>();
        String url = AD_URL + "/ApiServer.ashx?action=GetCarNumList";
        String result = HttpUtil.getFromUrl(url);

        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("Rows");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                DriverManageTypeItem item = new DriverManageTypeItem();
                item.CarNum = itemJson.optString("CarNum", "");
                item.Status = itemJson.optInt("FormStatus", 0);
                item.DebitId = itemJson.optInt("DebitId", 0);
                dataList.add(item);
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return dataList;
    }

    // 读取车辆信息
    public SendInVehicleDataBean GetVehicleDebitData(Integer carNum) {
        SendInVehicleDataBean dataList = new SendInVehicleDataBean();
        String url = AD_URL + "/ApiServer.ashx?action=InterfaceGetCMbyMId&carDebitId=" + carNum;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject Json = new JSONObject(result);
            JSONObject itemJson = Json.getJSONObject("Rows");
            SendInVehicleDataBean item = new SendInVehicleDataBean();
            item.CarNum = itemJson.optString("CarNum", "");
            item.DebitName = itemJson.optString("CarDebitName", "");
            item.LocalPostion = itemJson.optString("CarOutsideAddress", "");
            item.licenseNum = itemJson.optString("CarLicenseNum", "");
            item.idCard = itemJson.optString("CarIdCard", "");
            item.TEL = itemJson.optString("CarTel", "");
            item.OutKm = itemJson.optString("CarOutKm", "");
            item.OutOil = itemJson.optString("CarOutOil", "");
            item.OutSituation = itemJson.optString("CarOutSituation", "");
            item.CarOutsideTime = itemJson.optString("CarOutsideTime", "");
            item.CarFormStauts = itemJson.optString("CarFormStauts", "");
            item.CarDebitId = itemJson.optString("CarDebitId", "");
            dataList = item;
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return dataList;
    }

    // 抓拍地点
    public ArrayList<MaintenanceTypeItem> GetBuildNum() {
        ArrayList<MaintenanceTypeItem> dataList = new ArrayList<MaintenanceTypeItem>();
        String url = ATTENDANCE_URL + "?interfaceName=GetDictByDirType&DirType=1011";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("Rows");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                MaintenanceTypeItem item = new MaintenanceTypeItem();
                item.text = itemJson.optString("text", "");
                item.id = itemJson.optInt("id", 0);
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // 具体部位
    public ArrayList<MaintenanceTypeItem> GetCameraId() {
        ArrayList<MaintenanceTypeItem> dataList = new ArrayList<MaintenanceTypeItem>();
        String url = ATTENDANCE_URL + "?interfaceName=GetDictByDirType&DirType=1012";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("Rows");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                MaintenanceTypeItem item = new MaintenanceTypeItem();
                item.text = itemJson.optString("text", "");
                item.id = itemJson.optInt("id", 0);
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // 部位类型
    public ArrayList<MaintenanceTypeItem> GetCameraType() {
        ArrayList<MaintenanceTypeItem> dataList = new ArrayList<MaintenanceTypeItem>();
        String url = ATTENDANCE_URL + "?interfaceName=GetDictByDirType&DirType=1015";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("Rows");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                MaintenanceTypeItem item = new MaintenanceTypeItem();
                item.text = itemJson.optString("text", "");
                item.id = itemJson.optInt("id", 0);
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // 分部工程
    public ArrayList<MaintenanceTypeItem> GetPlaceType() {
        ArrayList<MaintenanceTypeItem> dataList = new ArrayList<MaintenanceTypeItem>();
        String url = ATTENDANCE_URL + "?interfaceName=GetDictByDirType&DirType=1013";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("Rows");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                MaintenanceTypeItem item = new MaintenanceTypeItem();
                item.text = itemJson.optString("text", "");
                item.id = itemJson.optInt("id", 0);
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // 子分部工程
    public ArrayList<MaintenanceTypeItem> GetType(int mDirType) {
        ArrayList<MaintenanceTypeItem> dataList = new ArrayList<MaintenanceTypeItem>();
        String url = ATTENDANCE_URL + "?interfaceName=GetDictByDirType&DirType=" + mDirType;
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("Rows");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                MaintenanceTypeItem item = new MaintenanceTypeItem();
                item.text = itemJson.optString("text", "");
                item.id = itemJson.optInt("id", 0);
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // 监管影像采集获取工程列表
    public static void getProjectListForOutSideCapture(final Context context, final String name, final String keytype,
                                                       final INetCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String projcLat = PreferenceUtil.getProjectLat();
                String projcLng = PreferenceUtil.getProjectLng();
                String url = BASE_URL + ACTION_GET_PROJECT + "&lat=" + projcLat + "&lng=" + projcLng + "&phoneNum="
                        + PreferenceUtil.getName() + "&Province=" + PreferenceUtil.getProvince()
                        + "&City=" + PreferenceUtil.getCity() + "&q=" + name + "&keytype=" + keytype
                        + "&module=outsidecapture";

                url = url.replaceAll(" ", "");
                String result = HttpUtil.getFromUrl(url);
                if (TextUtils.isEmpty(result)) {
                    Log.e("getProjectListForOutSid", "result null");
                    callback.onCallback(false, "result null");
                    return;
                } else {
                    callback.onCallback(true, result);
                }
            }
        }).start();
    }

    // 工作影像采集工程查詢列表
    public ArrayList<IpIdModel> getProjectContent(final String projectId) {
        String url = AD_URL + "/ApiServer.ashx?action=GetContractList&projId=" + projectId;
        System.out.println(url);
        String result = HttpUtil.getFromUrl(url);
        System.out.println(result);
        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            return null;
        }
        ArrayList<IpIdModel> al = new ArrayList<IpIdModel>();
        try {
            JSONObject json = new JSONObject(result);
            JSONArray Rows = json.getJSONArray("Rows");
            for (int i = 0; i < Rows.length(); i++) {
                IpIdModel model = new IpIdModel();
                JSONObject itemJson = Rows.getJSONObject(i);
                // model.setConSet_IpId(itemJson.getString("ConSet_IpId").replace("
                // ", " ").replace(" ", " ")
                // .replace(" ", " ").replace(" ", "\n"));
                model.setConSet_IpId(itemJson.getString("ConSet_IpId"));
                model.setProjAddress(itemJson.getString("ProjAddress"));
                model.setProjSiteChaPerson(itemJson.getString("ProjSiteChaPerson"));
                model.setProjSiteChaPersonPhone(itemJson.getString("ProjSiteChaPersonPhone"));
                model.setConCode(itemJson.getString("ConCode"));
                al.add(model);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.toString());

            return null;
        }

        return al;
    }

    // ///////////////////////////监管影像采集end////////////////////

    // ///////////////////////见证取样模块代码start/////////////////////////////

    // 见证取样获取工程和探头列表
    public static void getBindingProjectItemList(final Context context, final String name,
                                                 final INetCallback callback) {
        String projcLat = PreferenceUtil.getProjectLat();
        String projcLng = PreferenceUtil.getProjectLng();
        String url = BASE_URL + ACTION_GET_PROJECT + "&lat=" + projcLat + "&lng=" + projcLng + "&phoneNum="
                + PreferenceUtil.getName() + "&Province=" + PreferenceUtil.getProvince() + "&City="
                + PreferenceUtil.getCity() + "&module=outsidejzqy" + "&q=" + name;
        String result = HttpUtil.getFromUrl(url);
        if (TextUtils.isEmpty(result)) {
            Log.e("getBindingProjectItem", "result null");
            callback.onCallback(false, "result null");
            return;
        } else {
            callback.onCallback(true, result);
        }
    }

    // 解析见证取样工程和探头数据
    public static ArrayList<BindingProjectItem> parstToBindingProjectList(String data) {
        ArrayList<BindingProjectItem> dataList = new ArrayList<BindingProjectItem>();
        try {
            JSONArray all = new JSONArray(data.trim());
            for (int i = 0; i < all.length(); i++) {
                JSONObject result = all.getJSONObject(i);
                BindingProjectItem projectItem = new BindingProjectItem();
                projectItem.ProjectId = result.getString("ProjectId");
                projectItem.ProjectName = result.getString("ProjectName");
                JSONArray array = result.optJSONArray("SampleList");
                if (array != null) {
                    int count = array.length();
                    ArrayList<BindingTestItem> testList = new ArrayList<BindingTestItem>();
                    for (int j = 0; j < count; j++) {
                        JSONObject object = array.getJSONObject(j);
                        BindingTestItem testItem = new BindingTestItem();
                        testItem.SampleId = object.getInt("SampleId");
                        testItem.SampleName = object.getString("SampleName");
                        testItem.SampleType = object.getString("SampleType");
                        testItem.SampleEPC = object.getString("SampleEPC");
                        testList.add(testItem);
                    }
                    projectItem.SampleAPIlist = testList;
                }
                dataList.add(projectItem);
            }
        } catch (Exception e) {
            Log.e("parstToProjectList", e.getMessage());
        }
        return dataList;
    }

    // 见证取样上传数据
    public static void sendBindingData(Context context, String SampleId, String SampleEPC, String Imgstr,
                                       final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {
            param.put("SampleId", SampleId);
            param.put("SampleEPC", SampleEPC);
            param.put("UserName", PreferenceUtil.getName());
            param.put("Imgstr", Imgstr);
            param.put("ProjcLat", PreferenceUtil.getProjectLat());
            param.put("ProjcLng", PreferenceUtil.getProjectLng());
            param.put("Province", PreferenceUtil.getProvince());
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
        String result = HttpUtil.postToUrl(BASE_URL + ACTION_SEND_BINDING_DATA, param.toString());
        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("Result").equals("True")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 见证取样获取事件类型列表
    public static void getBindingSampleTypeList(final INetCallback callback) {
        String url = BASE_URL + ACTION_GET_SAMPLE_TYPE;
        String result = HttpUtil.getFromUrl(url);
        if (TextUtils.isEmpty(result)) {
            Log.e("getBindingSampleType", "result null");
            callback.onCallback(false, "result null");
            return;
        } else {
            callback.onCallback(true, result);
        }
    }

    // 解析见证取样事件类型数据
    public static ArrayList<BindingTestItem> parstToBindingSampleTypeList(String data) {
        ArrayList<BindingTestItem> dataList = new ArrayList<BindingTestItem>();
        try {
            JSONArray all = new JSONArray(data.trim());
            for (int i = 0; i < all.length(); i++) {
                JSONObject result = all.getJSONObject(i);
                BindingTestItem projectItem = new BindingTestItem();
                projectItem.SampleType = result.getString("SampleTypeName");
                dataList.add(projectItem);
            }
        } catch (Exception e) {
            Log.e("parstToProjectList", e.getMessage());
        }
        return dataList;
    }

    // ///////////////////////见证取样模块代码end/////////////////////////////

    // ///////////////////////网络考勤模块代码start/////////////////////////////

    // 考勤上传数据
    public static void sendAttendanceMobileData(Context context, SendDataBean data, final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {
            param.put("Note", data.Note);
            param.put("Imgstr", data.ImgStr);
            param.put("UserName", data.UserName);
            param.put("ProjcLat", data.ProjcLat);
            param.put("ProjcLng", data.ProjcLng);
            param.put("Province", data.Province);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
        String result = HttpUtil.postToUrl(BASE_URL + ACTION_SEND_DATA_MOBILE, param.toString());
        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("Result").equals("True")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 考勤提醒消息推送
    public static void GetWarnAttendance(final INetCallback callback, final String UserAccount) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = ATTENDANCE_URL + "?interfaceName=GetWarnAttendance&UserAccount=" + UserAccount;
                    String result = HttpUtil.getFromUrl(url);
                    if (TextUtils.isEmpty(result)) {
                        Log.e("GetWarnAttendance", "result null");
                        callback.onCallback(false, "result null");
                        return;
                    } else {
                        callback.onCallback(true, result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 考勤查询明细
    public static void GetAttendanceDetails(final INetCallback callback, final String UserAccount,
                                            final String StartTime, final String EndTime) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = ATTENDANCE_URL + "?interfaceName=GetAttendanceDetails&UserAccount=" + UserAccount
                            + "&StartTime=" + StartTime + "&EndTime=" + EndTime;
                    String result = HttpUtil.getFromUrl(url);
                    if (TextUtils.isEmpty(result)) {
                        Log.e("getAttendanceDatail", "result null");
                        callback.onCallback(false, "result null");
                        return;
                    } else {
                        callback.onCallback(true, result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 考勤查询明细（解析）
    public static List<BeanAttendanceDetails> parstToAttendanceDetails(String data) {
        List<BeanAttendanceDetails> listTime = new ArrayList<BeanAttendanceDetails>();
        try {
            JSONObject json = new JSONObject(data);
            int result = json.getInt("result");
            if (result == 1) {

                JSONArray josnArray = json.getJSONArray("Rows");
                for (int i = 0; i < josnArray.length(); i++) {
                    BeanAttendanceDetails bean = new BeanAttendanceDetails();
                    String time = josnArray.getJSONObject(i).getString("AttendanceTime");

                    time = time.substring(6, 19);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    time = format.format(new Date(Long.parseLong(time)));
                    bean.AttendanceTime = time;
                    String name = josnArray.getJSONObject(i).getString("StaffName");
                    bean.StaffName = name;
                    listTime.add(bean);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return listTime;
    }

    // 考勤日报
    public static void AttendDailyReport(final INetCallback callback, final String UserAccount, final String StartTime,
                                         final String EndTime) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = ATTENDANCE_URL + "?interfaceName=GetAttendDailyReport&UserAccount=" + UserAccount
                            + "&StartTime=" + StartTime + "&EndTime=" + EndTime;
                    String result = HttpUtil.getFromUrl(url);
                    if (TextUtils.isEmpty(result)) {
                        Log.e("getAttendanceDatail", "result null");
                        callback.onCallback(false, "result null");
                        return;
                    } else {
                        callback.onCallback(true, result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 考勤日报（解析）
    public static List<BeanAttendDailyReport> parstToAttendDailyReport(String data) {
        List<BeanAttendDailyReport> listData = new ArrayList<BeanAttendDailyReport>();
        try {
            JSONObject json = new JSONObject(data);
            int result = json.getInt("result");
            if (result == 1) {
                JSONArray josnArray = json.getJSONArray("Rows");
                for (int i = 0; i < josnArray.length(); i++) {
                    BeanAttendDailyReport bean = new BeanAttendDailyReport();
                    String date = josnArray.getJSONObject(i).getString("DailyDate");
                    bean.DailyDate = date;
                    String content = josnArray.getJSONObject(i).getString("RepContent");
                    bean.RepContent = content;
                    listData.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    // 考勤月报
    public static void AttendMonthReport(final INetCallback callback, final String UserAccount, final String StartTime,
                                         final String EndTime) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = ATTENDANCE_URL + "?interfaceName=GetAttendMonthReport&UserAccount=" + UserAccount
                            + "&StartTime=" + StartTime + "&EndTime=" + EndTime;
                    String result = HttpUtil.getFromUrl(url);
                    if (TextUtils.isEmpty(result)) {
                        Log.e("getAttendanceDatail", "result null");
                        callback.onCallback(false, "result null");
                        return;
                    } else {
                        callback.onCallback(true, result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 考勤月报（解析）
    public static List<BeanAttendMonthReport> parstToAttendMonthReport(String data) {
        List<BeanAttendMonthReport> listData = new ArrayList<BeanAttendMonthReport>();
        try {
            JSONObject json = new JSONObject(data);
            int result = json.getInt("result");
            if (result == 1) {
                JSONArray josnArray = json.getJSONArray("Rows");
                for (int i = 0; i < josnArray.length(); i++) {
                    BeanAttendMonthReport bean = new BeanAttendMonthReport();
                    String date = josnArray.getJSONObject(i).getString("AttendMonth");
                    bean.AttendMonth = date;
                    String content = josnArray.getJSONObject(i).getString("RepContent");
                    bean.RepContent = content;
                    listData.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    // ///////////////////////网络考勤模块代码end/////////////////////////////

    // 驾驶员模块上传数据
    public static void sendDataDriver(Context context, SendDataBean data, final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {
            param.put("ProjectId", data.ProjectId);
            param.put("ProjectName", data.ProjectName);
            param.put("Note", data.Note);
            param.put("Imgstr", data.ImgStr);
            param.put("UserName", data.UserName);
            param.put("ProjcLat", data.ProjcLat);
            param.put("ProjcLng", data.ProjcLng);
            param.put("Province", PreferenceUtil.getProvince());
            param.put("BuildNum", data.BuildNum);
            param.put("PlaceType", data.PlaceType);
            param.put("CameraId", data.CameraId);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
        String result = HttpUtil.postToUrl(BASE_URL + "action=uploadDataAndImage&module=Driver", param.toString());
        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("Result").equals("True")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 获取车牌号
    public ArrayList<CarItem> GetDictByDirType() {
        ArrayList<CarItem> dataList = new ArrayList<CarItem>();
        String url = ATTENDANCE_URL + "?interfaceName=GetDictByDirType&DirType=1006";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONArray Rows = new JSONObject(result).optJSONArray("Rows");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                CarItem item = new CarItem();
                item.id = itemJson.optString("id", "");
                item.text = itemJson.optString("text", "");
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // 项目定位保存
    public static void uploadDataForSavePosition(Context context, String projid, String lat, String lng, final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {
            param.put("projid", projid);
            param.put("lat", lat);
            param.put("lng", lng);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
        String result = HttpUtil.postToUrl(BASE_URL + ACTION_SAVE_POSITION, param.toString());
        if (TextUtils.isEmpty(result)) {
            Log.e("uploadDataForSave", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("Result").equals("True")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
    }

    // 批量上传图片
    public static void UploadImage(SendDataBean data) {
        JSONObject param = new JSONObject();
        try {
            param.put("UserName", data.UserName);
            param.put("Note", data.Note);
            param.put("Imgstr", data.ImgStr);
            param.put("ProjcLat", data.ProjcLat);
            param.put("ProjcLng", data.ProjcLng);
            param.put("ProjectId", data.ProjectId);
            param.put("ProjectName", data.ProjectName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.postToUrl(BASE_URL + "action=uploadDataAndImage&module=localimage", param.toString());
    }

    // 试件检测
    public static String JZQYValidateAndGetUrl(String jzqyid, String jzqycontent) {
        String url = BASE_URL + "action=JZQYValidateAndGetUrl&module=jzqyvalidate&jzqyid=" + jzqyid + "&jzqycontent="
                + jzqycontent;
        String result = HttpUtil.getFromUrl(url);
        if (TextUtils.isEmpty(result)) {
            return "";
        }
        try {
            JSONObject json = new JSONObject(result);
            result = json.getString("Result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 分页
    public String GetotalPage(String keyword, String pageIndex, String pageSize, String code) {
        // TODO Auto-generated method stub
        String url = AD_URL + "/ApiServer.ashx?action=SearchProjectTypeData&keyword=" + keyword + "&pageIndex="
                + pageIndex + "&pageSize=" + pageSize + "&code=" + code;

        String result = HttpUtil.getFromUrl(url);
        if (TextUtils.isEmpty(result)) {
            Log.e("uploadDataForSave", "result null");

            return null;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("result").equals("1")) {

                return result;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    // 工程项目人员调整

    public static void updateAdjustProject(String Mid, String Mtype, String ProjectNumber, String Users,
                                           INetCallback callback) {
        // TODO Auto-generated method stub
        JSONObject param = new JSONObject();
        try {
            param.put("Mid", Mid);
            param.put("MmanList", ProjectNumber);
            param.put("Mtype", Mtype);
            param.put("Account", Users);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }
        String result = HttpUtil.postToUrl(AD_URL + "/ApiServer.ashx?action=AdjustProjectMember", param.toString());

        if (TextUtils.isEmpty(result)) {
            Log.e("uploadDataForSave", "result null");
            callback.onCallback(false, "result null");
            return;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("result").equals("1")) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }

    }

    public ArrayList<UsersItem> GetBuilders() {
        ArrayList<UsersItem> dataList = new ArrayList<UsersItem>();
        String url = AD_URL + "/ApiServer.ashx?action=GetUserList";
        String result = HttpUtil.getFromUrl(url);
        try {
            JSONArray Rows = new JSONObject(result).optJSONArray("rows");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                UsersItem item = new UsersItem();
                // UserAccount为ID
                item.UserAccount = itemJson.optString("UserId", "");
                item.UserName = itemJson.optString("UserName", "");
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public ArrayList<ProjectAdjustUsersItem> GetJobNumber(String PrejctID) {
        ArrayList<ProjectAdjustUsersItem> dataList = new ArrayList<ProjectAdjustUsersItem>();
        String url = AD_URL + "/ApiServer.ashx?action=GetProjCodeInfo&projId=" + PrejctID;

        String result = HttpUtil.getFromUrl(url);
        try {
            JSONArray Rows = new JSONObject(result).optJSONArray("rows");
            for (int i = 0; i < Rows.length(); i++) {
                JSONObject itemJson = Rows.getJSONObject(i);
                ProjectAdjustUsersItem item = new ProjectAdjustUsersItem();
                item.Mid = itemJson.optString("Mid", "");
                item.Mcode = itemJson.optString("Mcode", "");
                item.MType = itemJson.optString("MType", "");
                item.MManListID = itemJson.optString("MManListID", "");
                item.projId = itemJson.optString("projId", "");
                item.MManList = itemJson.optString("MManList", "");
                dataList.add(item);
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }

        return dataList;
    }

    public String GetotalRules(String keyword, String pageIndex, String pageSize, String code) {
        // TODO Auto-generated method stub
        String url = HOST_URL + "/Services/DataService.ashx?action=searchData&keyword=" + keyword + "&pageIndex="
                + pageIndex + "&pageSize=" + pageSize + "&code=" + code;

        String result = HttpUtil.getFromUrl(url);
        if (TextUtils.isEmpty(result)) {
            Log.e("uploadDataForSavePositi", "result null");

            return null;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("result").equals("1")) {

                return result;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String GetotalAdjustPage(String keyword, String pageIndex, String pageSize, String code) {
        // TODO Auto-generated method stub
        String url = AD_URL + "/ApiServer.ashx?action=SearchProjectTypeData&keyword=" + keyword + "&pageIndex="
                + pageIndex + "&pageSize=" + pageSize + "&code=" + code;

        String result = HttpUtil.getFromUrl(url);
        if (TextUtils.isEmpty(result)) {
            Log.e("uploadDataForSave", "result null");

            return null;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("result").equals("1")) {

                return result;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void sendFaceVerifyMsg(FaceModel model, final INetCallback callback) {
        JSONObject param = new JSONObject();
        try {

            param.put("ImgStr", model.getImgstr());
            param.put("account", model.getAccount());
            param.put("value", model.getValue());
            param.put("describe", model.getDescribe());
            param.put("detectionDetectJson", model.getDetectionDetectJson());
            param.put("recognitionVerifyJson", model.getRecognitionVerifyJson());

            String result = HttpUtil.postToUrl("http://120.35.11.49:26969/Services/FaceVerify.ashx?action=uploadRecord",
                    param.toString());
            System.out.println(param.toString());
            if (TextUtils.isEmpty(result)) {
                Log.e("sendData", "result null");
                callback.onCallback(false, "result null");
                return;
            }

            JSONObject json = new JSONObject(result);
            if (json.getString("result").equals("1")) {

                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.onCallback(false, e.getMessage());
        }

    }

    public boolean sendFaceAddToPersonMsg(ArrayList al) {

        // param.put("account", model.getAccount());
        // param.put("Imgid", model.getImgid());
        // param.put("Faceid", model.getFaceid());
        // param.put("ImgStr", model.getImgStr());

        Gson gson = new Gson();
        String str = gson.toJson(al);
        System.out.println("json:STR" + str);

        String result = HttpUtil.postToUrl("http://120.35.11.49:26969/Services/FaceVerify.ashx?action=registerFace",
                str);
        // String result = null;
        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            return false;
        }
        try {
            JSONObject json = new JSONObject(result);
            if (json.getString("result").equals("1")) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<FaceMSGModel> GetFaceData(String name) {
        // TODO Auto-generated method stub
        String url = "http://120.35.11.49:26969/Services/FaceVerify.ashx?action=countFaceVerify&account=" + name;

        String result = HttpUtil.getFromUrl(url);

        if (result.equals("[]")) {
            return null;
        }
        if (TextUtils.isEmpty(result)) {
            Log.e("sendData", "result null");
            return null;
        }

        Gson gson = new Gson();
        List<FaceMSGModel> ps = gson.fromJson(result, new TypeToken<List<FaceMSGModel>>() {
        }.getType());

        return ps;
    }

    public void UpdatePWD(String Account, String OldPWD, String NewPWD, INetCallback callback) {

        try {

            String result = HttpUtil.getFromUrl(BASE_URL + "action=checkchangepwd&account=" + Account + "&password="
                    + OldPWD + "&newpwd=" + NewPWD);
            System.out.println(BASE_URL + "action=checkchangepwd&account=" + Account + "&password=" + OldPWD
                    + "&newpwd=" + NewPWD);
            System.out.println(result);
            if (TextUtils.isEmpty(result)) {
                Log.e("sendData", "result null");
                callback.onCallback(false, null);
            }
            JSONObject json = new JSONObject(result);
            if (json.getString("result").equals("1")) {
                callback.onCallback(true, "success");
            } else {
                callback.onCallback(false, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.onCallback(false, null);
        }

    }

    /**
     * 修改机柜信息
     */
    public boolean updateCabinet(String camId, String camName, String camInstalPlace) {

        try {
            String result = HttpUtil
                    .getFromUrl(HOST_URL + "/Services/DevPowerService.ashx?action=UpdateDevPower&CamId=" + camId + "&CamName=" + camName + "&CamInstalPlace=" + camInstalPlace);
            JSONObject jsonObject = new JSONObject(result);
            if ("1".equals(jsonObject.getString("result"))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取机柜信息
     * CamId
     */
    public CabineModel getCabinet(String camId) {

        try {
            String result = HttpUtil
                    .getFromUrl(HOST_URL + "/Services/DevPowerService.ashx?action=GetPowerMode&CamId=" + camId);
            JSONObject jsonObject = new JSONObject(result);
            if ("1".equals(jsonObject.getString("result"))) {
                CabineModel cabineModel = new CabineModel(jsonObject.getJSONArray("data").getJSONObject(0).getString("CamName"), jsonObject.getJSONArray("data").getJSONObject(0).getString("CamInstalPlace"));

                return cabineModel;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询是否是机柜
     */
    public boolean checkCabinet(String camId) {

        try {
            String result = HttpUtil
                    .getFromUrl(HOST_URL + "/Services/DevPowerService.ashx?action=GetIsGame&CamId=" + camId);
            if ("1".equals(new JSONObject(result).getString("result"))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 添加机柜
     */
    public boolean insertCabinet(String project, String camName, String camInstallPlace) {

        try {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("CamProjId", project));
            nvps.add(new BasicNameValuePair("CamInstalPlace", camInstallPlace));
            nvps.add(new BasicNameValuePair("CamName", camName));
            nvps.add(new BasicNameValuePair("timestamp", DateUtil.getCurrentTimeStamp()));

            String result = HttpUtil
                    .postToUrl(HOST_URL + "/Services/DevPowerService.ashx?action=AddDevPower", nvps);
            if ("1".equals(new JSONObject(result).getString("result"))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询设备的断电情况和故障情况
     */
    public ArrayList<PowerDevModel> getDevMsgForPowerDev(String project) {

        try {
            String result = HttpUtil
                    .getFromUrl(HOST_URL + "/Services/DevPowerService.ashx?action=GetPowerDevData&projId=" + project);

            if (!"1".equals(new JSONObject(result).getString("result"))) {
                return null;
            }
            ArrayList<PowerDevModel> al = new ArrayList<PowerDevModel>();
            Gson gson = new Gson();
            String data = new JSONObject(result).getString("data");
            ArrayList<PowerDevModel> list = gson.fromJson(data, new TypeToken<List<PowerDevModel>>() {
            }.getType());

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 掉电工程搜索
    public static void getProjectListForBindFailSev(final Context context, final String keyword, final String keytype,
                                                    final INetCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String projcLat = PreferenceUtil.getProjectLat();
                String projcLng = PreferenceUtil.getProjectLng();
                String url = HOST_URL + "/Services/DevPowerService.ashx?action=GetOnlineProjectCamara" + "&lat="
                        + projcLat + "&lng=" + projcLng + "&phoneNum=" + PreferenceUtil.getName() + "&Province="
                        + PreferenceUtil.getProvince() + "&City=" + PreferenceUtil.getCity() + "&keyword="
                        + keyword + "&keytype=" + keytype + "&module=outsidecapture";

                url = url.replaceAll(" ", "");
                String result = HttpUtil.getFromUrl(url);
                if (TextUtils.isEmpty(result)) {
                    Log.e("getProjectList", "result null");
                    callback.onCallback(false, "result null");
                    return;
                } else {
                    callback.onCallback(true, result);
                }
            }
        }).start();
    }

    // 掉电工程数据解析
    public static ArrayList<ProjectItem> parstToFailSevProjectList(String data) {
        ArrayList<ProjectItem> dataList = new ArrayList<ProjectItem>();
        try {
            ;
            JSONArray all = new JSONObject(data).getJSONArray("data");
            for (int i = 0; i < all.length(); i++) {
                JSONObject result = all.getJSONObject(i);
                ProjectItem projectItem = new ProjectItem();
                projectItem.projectId = result.getString("ProjectId");
                projectItem.ProjectName = result.getString("ProjectName");
                dataList.add(projectItem);
            }
        } catch (Exception e) {
            Log.e("parstToProjectList", e.getMessage());
        }
        return dataList;
    }

    /**
     * 绑定设备
     */
    public static void sendBindDevPowerSnToCamIds(String CamIds, String sn, Context context,
                                                  final INetCallback callback) {

        try {
            String result = HttpUtil.getFromUrl(HOST_URL + "/Services/DevPowerService.ashx?action=BindDevPower&CamId="
                    + CamIds + "&DevPowerSn=" + sn + "&UserId=" + PreferenceUtil.getUserId());

            if ("1".equals(new JSONObject(result).getString("result"))) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, result);
            }

        } catch (Exception e) {
            callback.onCallback(false, e.getMessage());
        }

    }

    /**
     * 解绑设备
     */
    public static void sendUnBindDevPowerSnToCamIds(JSONArray arr, final INetCallback callback) {

        try {

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("postdata", arr.toString()));

            String result = HttpUtil.postToUrl(HOST_URL + "/Services/DevPowerService.ashx?action=UnBindDevPower", nvps);

            if ("1".equals(new JSONObject(result).getString("result"))) {
                callback.onCallback(true, result);

            } else {
                callback.onCallback(false, result);
            }

        } catch (Exception e) {
            callback.onCallback(false, e.getMessage());
        }

    }

    /**
     * 查询断电设备 的开启时间和关闭时间
     */
    public ArrayList<PowerDevHistoryModel> getPowerDevListForDevNum(String camSeqId) {
        try {
            String result = HttpUtil
                    .getFromUrl(HOST_URL + "/Services/DevPowerService.ashx?action=GetPowerDevLog&camSeqId=" + camSeqId);
            if (!"1".equals(new JSONObject(result).getString("result"))) {
                return null;
            }
            ArrayList<PowerDevHistoryModel> al = new ArrayList<PowerDevHistoryModel>();
            Gson gson = new Gson();
            String data = new JSONObject(result).getString("data");
            ArrayList<PowerDevHistoryModel> list = gson.fromJson(data, new TypeToken<List<PowerDevHistoryModel>>() {
            }.getType());

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询断网设备 的开启时间和关闭时间
     */
    public ArrayList<PowerNetHistoryModel> getPowerNetDevListForDevNum(String camSeqId) {
        try {
            String result = HttpUtil
                    .getFromUrl(HOST_URL + "/Services/DevPowerService.ashx?action=GetPowerNetDevLog&camSeqId=" + camSeqId);
            if (!"1".equals(new JSONObject(result).getString("result"))) {
                return null;
            }
            ArrayList<PowerNetHistoryModel> al = new ArrayList<PowerNetHistoryModel>();
            Gson gson = new Gson();
            String data = new JSONObject(result).getString("data");
            ArrayList<PowerNetHistoryModel> list = gson.fromJson(data, new TypeToken<List<PowerNetHistoryModel>>() {
            }.getType());

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询设备列表和是否绑定设备
     * <p>
     * ID
     */
    public ArrayList<PowerDevBindModel> getDevMsgForPowerDevBind(String project) {

        try {
            String result = HttpUtil
                    .getFromUrl(HOST_URL + "/Services/DevPowerService.ashx?action=GetDevPowerList&projId=" + project);

            if (!"1".equals(new JSONObject(result).getString("result"))) {
                return null;
            }
            ArrayList<PowerDevBindModel> al = new ArrayList<PowerDevBindModel>();
            Gson gson = new Gson();
            String data = new JSONObject(result).getString("data");
            ArrayList<PowerDevBindModel> list = gson.fromJson(data, new TypeToken<List<PowerDevBindModel>>() {
            }.getType());

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询全景拼图设备
     */
    public static ArrayList<FullImageDevModel> getProjectListForFullImage(final String projId, final String systemid) {

        String url = BASE_URL + "action=GetDevList" + "&projId=" + projId + "&systemid=" + systemid;

        url = url.replaceAll(" ", "");
        String result = HttpUtil.getFromUrl(url);
        try {
            if (!"1".equals(new JSONObject(result).getString("result"))) {
                return null;
            }

            Gson gson = new Gson();
            String data;

            data = new JSONObject(result).getString("data");
            ArrayList<FullImageDevModel> list = gson.fromJson(data, new TypeToken<List<FullImageDevModel>>() {
            }.getType());
            return list;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 获取影像日志全景图片
     */
    public static FullImageMainModel getProjectForFullImage(final String camid) {

        String url = "http://api.jsqqy.com/OpenInterface/SystemDependentService.ashx?action=GetPuzzleData&camid="
                + camid;

        url = url.replaceAll(" ", "");
        String result = HttpUtil.getFromUrl(url);

        try {
            if (!"1".equals(new JSONObject(result).getString("result"))) {
                return null;
            }

            Gson gson = new Gson();
            String data;
            data = new JSONObject(result).getString("data");
            String arr = new JSONArray(data).getString(3);
            return gson.fromJson(arr, FullImageMainModel.class);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    // /**
    // * 获取全景拼图---节点图
    // *
    // * @param Camid
    // */
    // public static ArrayList<PositionImgModel>
    // GetPositionImgForFullImage(String camId, String recordId, String lng,
    // String lat, String timestamp) {
    // String url =
    // "http://120.35.11.49:7878/Handler/PanoramaHandler.ashx?action=GetPositionImg";
    //
    // try {
    //
    // List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    // nvps.add(new BasicNameValuePair("camId", camId));
    // nvps.add(new BasicNameValuePair("recordId", recordId)); // 封装表单
    // nvps.add(new BasicNameValuePair("lng", lng)); // 封装表单
    // nvps.add(new BasicNameValuePair("lat", lat)); // 封装表单
    // nvps.add(new BasicNameValuePair("timestamp", timestamp)); // 封装表单
    // String result = HttpUtil.postToUrl(url, nvps);
    //
    // if (!"1".equals(new JSONObject(result).getString("result"))) {
    // return null;
    // }
    //
    // Gson gson = new Gson();
    // String data;
    // data = new JSONObject(result).getString("data");
    // ArrayList<PositionImgModel> list = gson.fromJson(data, new
    // TypeToken<List<PositionImgModel>>() {
    // }.getType());
    // return list;
    // } catch (Exception e) {
    // // TODO: handle exception
    // e.printStackTrace();
    // }
    //
    // return null;
    //
    // }

    /**
     * 获取全景拼图---节点图
     */
    public static ArrayList<PositionImgModel> GetPositionImgForFullImage(String camId, String recordId, String lng,
                                                                         String lat, String timestamp, String ImageTimes) {
        String url = "http://120.35.11.49:7878/Handler/PanoramaHandler.ashx?action=GetRangeNodeImg";
        try {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("ImageTimes", "43"));
            nvps.add(new BasicNameValuePair("camId", camId));
            nvps.add(new BasicNameValuePair("puzzleId", recordId));
            nvps.add(new BasicNameValuePair("pointx", lng));
            nvps.add(new BasicNameValuePair("pointy", lat));
            nvps.add(new BasicNameValuePair("timestamp", DateUtil.getCurrentTimeStamp()));

            String result = HttpUtil.postToUrl(url, nvps);

            if (!"1".equals(new JSONObject(result).getString("result"))) {
                return null;
            }

            Gson gson = new Gson();
            String data;
            data = new JSONObject(result).getString("data");
            ArrayList<PositionImgModel> list = gson.fromJson(data, new TypeToken<List<PositionImgModel>>() {
            }.getType());
            return list;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return null;

    }

    // /**
    // * 获取全景拼图---节点图记录
    // *
    // * @param Camid
    // */
    // public static ArrayList<FullImageNodeModel>
    // GetNodeImgHistoryForFullImage(String RecordId, String DistanceRang,
    // String startDate, String endDate, String timestamp) {
    //
    // try {
    // String url =
    // "http://120.35.11.49:7878/Handler/PanoramaHandler.ashx?action=GetNodeImgByRanks";
    // List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    // nvps.add(new BasicNameValuePair("RecordId", RecordId));
    // nvps.add(new BasicNameValuePair("DistanceRang", DistanceRang)); // 封装表单
    // nvps.add(new BasicNameValuePair("startDate", startDate)); // 封装表单
    // nvps.add(new BasicNameValuePair("endDate", endDate)); // 封装表单
    // nvps.add(new BasicNameValuePair("timestamp", timestamp)); // 封装表单
    // String result = HttpUtil.postToUrl(url, nvps);
    //
    // if (!"1".equals(new JSONObject(result).getString("result"))) {
    // return null;
    // }
    // Gson gson = new Gson();
    // String data;
    // data = new JSONObject(result).getString("data");
    // ArrayList<FullImageNodeModel> list = gson.fromJson(data, new
    // TypeToken<List<FullImageNodeModel>>() {
    // }.getType());
    // return list;
    // } catch (JSONException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // return null;
    // }

    /**
     * 全景拼图 通过节点图片信息获取同一位置图片列表
     */
    public static ArrayList<FullImageNodeModel> GetNodeImgHistoryForFullImage(String RecordId, String DistanceRang,
                                                                              String startDate, String endDate, String timestamp) {

        try {
            String url = "http://120.35.11.49:7878/Handler/PanoramaHandler.ashx?action=GetNodeImgByRanks";
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("RecordId", RecordId));
            nvps.add(new BasicNameValuePair("DistanceRang", DistanceRang)); // 封装表单
            nvps.add(new BasicNameValuePair("startDate", startDate)); // 封装表单
            nvps.add(new BasicNameValuePair("endDate", endDate)); // 封装表单
            nvps.add(new BasicNameValuePair("timestamp", timestamp)); // 封装表单
            String result = HttpUtil.postToUrl(url, nvps);

            if (!"1".equals(new JSONObject(result).getString("result"))) {
                return null;
            }
            Gson gson = new Gson();
            String data;
            data = new JSONObject(result).getString("data");
            ArrayList<FullImageNodeModel> list = gson.fromJson(data, new TypeToken<List<FullImageNodeModel>>() {
            }.getType());
            return list;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取全景拼图---测量图记录
     */
    public static ArrayList<FullImageMeasureModel> GetMeasureImgForFullImage(String RecordId, String Imgtype,
                                                                             String timestamp) {

        try {
            String url = "http://120.35.11.49:7878/Handler/PanoramaHandler.ashx?action=GetMeasureImg";
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("RecordId", RecordId));
            nvps.add(new BasicNameValuePair("Imgtype", Imgtype)); // 封装表单
            nvps.add(new BasicNameValuePair("timestamp", timestamp)); // 封装表单
            String result = HttpUtil.postToUrl(url, nvps);

            if (!"1".equals(new JSONObject(result).getString("result"))) {
                return null;
            }
            Gson gson = new Gson();
            String data;
            data = new JSONObject(result).getString("data");
            ArrayList<FullImageMeasureModel> list = gson.fromJson(data, new TypeToken<List<FullImageMeasureModel>>() {
            }.getType());
            return list;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 增加塔吊人脸图库
     */
    public static boolean InsertBaseForTowerCrane(String account, String imgstr) {

        try {

            String url = "http://120.35.11.49:26969/OpenInterface/TowerCraneService.ashx?action=InsertBase";
            JSONObject json = new JSONObject();
            json.put("Account", account);
            json.put("Imgstr", imgstr);
            String result = HttpUtil.postToUrl(url, json.toString());
            if (!"1".equals(new JSONObject(result).getString("result"))) {
                return false;
            }

            return true;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;

    }

    public static String InsertConstantDataForTowerCrane(String Account, String Imgstr, String Remark) {
        // TODO Auto-generated method stub
        try {

            String url = "http://120.35.11.49:26969/OpenInterface/TowerCraneService.ashx?action=InsertConstantDataForAppNew";
            JSONObject json = new JSONObject();
            json.put("Account", Account);
            json.put("Imgstr", Imgstr);
            json.put("Remark", Remark);
            String result = HttpUtil.postToUrl(url, json.toString());
            // //连续失败三次 返回2
            // if ("2".equals(new JSONObject(result).getString("result"))) {
            // return 2;
            // }
            //
            // //识别失败返回0
            // if (!"1".equals(new JSONObject(result).getString("result"))) {
            // return 0;
            // }
            // //识别失败返回1

            return result;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    // 判断是否有底库
    public static String IsHasBaseDataForTowerCrane(String Account) {
        // TODO Auto-generated method stub
        try {
            String url = "http://120.35.11.49:26969/OpenInterface/TowerCraneService.ashx?action=IsHasBaseData";
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("Account", Account));

            String result = HttpUtil.postToUrl(url, nvps);
            // //连续失败三次 返回2
            // if ("2".equals(new JSONObject(result).getString("result"))) {
            // return 2;
            // }
            //
            // //识别失败返回0
            // if (!"1".equals(new JSONObject(result).getString("result"))) {
            // return 0;
            // }
            // //识别失败返回1
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static String IsLockAndGetSecondsForTowerCrane(String name) {
        // TODO Auto-generated method stub
        try {
            String url = "http://120.35.11.49:26969/OpenInterface/TowerCraneService.ashx?action=IsLockAndGetSeconds";
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("Account", name));

            String result = HttpUtil.postToUrl(url, nvps);

            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<TowerCraneManagerModel> GetWrongRecordListByAdminIdForTowerCrane(String name, String type) {
        // TODO Auto-generated method stub

        try {
            String url = "http://120.35.11.49:26969/OpenInterface/TowerCraneService.ashx?action=GetWrongRecordListByAdminIdNew";

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("Account", name));
            nvps.add(new BasicNameValuePair("type", type));

            String result = HttpUtil.postToUrl(url, nvps);

            Gson gson = new Gson();
            String data;

            data = new JSONObject(result).getString("data");

            ArrayList<TowerCraneManagerModel> list = gson.fromJson(data, new TypeToken<List<TowerCraneManagerModel>>() {
            }.getType());
            return list;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void verifiyForTowerCrane(String Account, String TowerRecordID, String TrType,
                                            INetCallback callback) {
        // TODO Auto-generated method stub

        try {
            String url = "http://120.35.11.49:26969/OpenInterface/TowerCraneService.ashx?action=UpdateWrongRecord";

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("Account", Account));
            nvps.add(new BasicNameValuePair("TowerRecordID", TowerRecordID));
            nvps.add(new BasicNameValuePair("TrType", TrType));

            String result = HttpUtil.postToUrl(url, nvps);

            String flag = new JSONObject(result).getString("result");
            if ("1".equals(new JSONObject(result).getString("result"))) {
                callback.onCallback(true, result);
            } else {
                callback.onCallback(false, null);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            callback.onCallback(false, e.toString());

        }

    }

    /**
     * @param context
     * @param AppId   错误类型
     * @param Logger  日志类型
     * @param Level   错误等级
     * @param Message 错误文本
     */
    public static void sendCrashError(Context context, String AppId, String Logger, String Level, String Message) {
        // TODO Auto-generated method stub
        try {
//			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//			nvps.add(new BasicNameValuePair("AppId", AppId));
//			nvps.add(new BasicNameValuePair("Logger", Logger));
//			nvps.add(new BasicNameValuePair("Level", Level));
//			nvps.add(new BasicNameValuePair("Message", Message));

            Map<String, String> params = new HashMap<String, String>();

            params.put("AppId", AppId);
            params.put("Logger", Logger);
            params.put("Level", Level);
            params.put("Message", Message);
            String queryString = "";
            for (String key : params.keySet()) {
                String value = params.get(key);
                queryString += key + "=" + value + "&";
            }

            String url = AD_URL + "/Services/DataService.ashx?action=setExecLog&" + queryString;
            //String url = "http://10.1.3.3/Handler/MobileAppsHandler.ashx"+"?interfaceName=SetExecLog";
            Log.d(TAG, queryString);
            String result = HttpUtil.getFromUrl(url);

            //String flag = new JSONObject(result).getString("result");
            //Log.d(TAG, flag + "");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d(TAG, e.toString() + "");
            e.printStackTrace();
        }
    }

    // 获取工单号列表接口(安装开通里的)
    public static void devInstallJobCodeList(Activity activity, String projId, String typeId, NetCall call) {
        HashMap<String, String> params = new HashMap<>();
        params.put("projId", projId + "");
        params.put("typeId", typeId + "");
        get(activity, NetworkApi.BASE_URL_NEW + "action=devInstallJobCodeList", params, call);
    }


    /**
     * 获取项目列表
     * @param activity
     * @param proTypeId
     * @param proName
     * @param pageIndex
     * @param pageSize
     * @param isUploadLocation
     * @param netCall
     */
    public static void getCommonWorkProjectList(Activity activity, String proTypeId, String proName, int pageIndex, int pageSize,boolean isUploadLocation, NetCall netCall) {
        HashMap<String, String> params = new HashMap<>();
        params.put("typeId", proTypeId + "");
        params.put("userId", PreferenceUtil.getUserId());
        params.put("username", PreferenceUtil.getUserName());
        params.put("isFalg", "0");
        params.put("projName", proName + "");
//        if(isUploadLocation){
            params.put("lng", PreferenceUtil.getProjectLng());
            params.put("lat", PreferenceUtil.getProjectLat());
//        }else {
//            params.put("lng", "");
//            params.put("lat", "");
//        }
        params.put("phoneNum", PreferenceUtil.getName());
        params.put("province", PreferenceUtil.getCity());
        params.put("pageIndex", pageIndex + "");
        params.put("pageSize", pageSize + "");
        get(activity, NetworkApi.BASE_URL_NEW + "action=getCommonWorkProjectList", params, netCall);
    }

    // 获取安装开通里的设备名称(施工点位)列表
    public static void getInstallDatCameraCamNameList(Activity activity, String projectId, String jobId, String jobCodeName, NetCall netCall) {
        HashMap<String, String> params = new HashMap<>();
        params.put("projId", projectId + "");
        params.put("jobId", jobId);
        params.put("jobCodeName", jobCodeName);
        get(activity, NetworkApi.BASE_URL_NEW + "action=getInstallDatCameraCamNameList", params, netCall);
    }

    //  根据设备名称(施工点位) 里的camTypeId 获取是否显示一体机编号
    public static void getTotalTypeByCamTypeId(Activity activity, String camTypeId, NetCall netCall) {
        HashMap<String, String> params = new HashMap<>();
        params.put("camTypeId", camTypeId + "");
        get(activity, BASE_URL_NEW + "action=getTotalTypeByCamTypeId", params, netCall);
    }

    //  获取安装开通里的单位工程列表
    public static void queryUnitProject(Activity activity, String projectId, NetCall netCall) {
        HashMap<String, String> params = new HashMap<>();
        params.put("projId", projectId + "");
        get(activity, HOST_URL + "/Services/CameraBindService.ashx?action=GetUnitProjectByProjId", params, netCall);
    }

    // 获取安装开通里的安装接入情况列表
    public static void getInstallOpenStatus(Activity activity, String projectTypeId, NetCall netCall) {
        HashMap<String, String> params = new HashMap<>();
        params.put("typeid", projectTypeId + "");
        get(activity, ATTENDANCE_URL + "?interfaceName=GetMaintenanceType", params, netCall);
    }

    // 更新项目经纬度信息
    public static void uploadDataForSavePosition(Activity activity, String projid, String proLat, String proLng, NetCall netCall) {
        JSONObject params = new JSONObject();

        try {
            params.put("projid", projid);
            params.put("lat", proLat);
            params.put("lng", proLng);
        } catch (JSONException e) {
            e.printStackTrace();
            netCall.onFail(e.getMessage());
        }
        postJson(activity, BASE_URL + "action=uploadDataForSavePosition&module=ProjectPosition", params, netCall);
    }

    //安装开通上传数据（汇川）
    public static void sendIntallOpenData(Activity activity, SendDataBean data, NetCall netCall) {
        JSONObject param = new JSONObject();
        try {
            param.put("ProjectId", data.ProjectId);
            param.put("ProjectName", data.ProjectName);
            param.put("Note", data.Note);
            param.put("ResponsibilitySubject", data.ResponsibilitySubject);
            param.put("UserName", data.UserName);
            param.put("ProjcLat", data.ProjcLat);
            param.put("ProjcLng", data.ProjcLng);
            param.put("Province", PreferenceUtil.getProvince());
            param.put("Type", data.Type);
            param.put("Progress", data.Progress);
            param.put("IsSaveLocation", data.IsSaveLocation);
            param.put("CameraId", data.CameraId);
            param.put("ispay", data.ispay);
            param.put("typeid", data.projectTypeId);
            param.put("payid", data.payId);
            param.put("Imgstr", data.ImgStr);
            param.put("UserId", data.UserId);
            param.put("CamSeqId", data.DriverNum);
            param.put("CamItemId", data.UnitProjectId);
            param.put("CamName", data.CamName);
            param.put("CamInstalPlace", data.InstallPlace);
            param.put("DevLng", data.DriverJingDu);
            param.put("DevLat", data.DriverWeiDu);
            param.put("Alpha", data.DriverAlpha);
            param.put("jobId", data.jobId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postJson(activity, HOST_URL + "/Services/CameraBindService.ashx" + ACTION_SAVE_CONFIG_BY_HLHT_API, param, netCall);
    }

    //安装、拆机上传确认单图片(上传安装验收单、拆机单)
    public static void uploadInsideDataAndImage(Activity activity, UploadWordOrderInfoBean infoData, NetCall netCall) {
        JSONObject param = new JSONObject();
        try {
            param.put("ProjectId", infoData.getProjectId());
            param.put("Imgstr", infoData.getImgstr());
            param.put("UserName", PreferenceUtil.getName());//手机号
            param.put("userId", PreferenceUtil.getUserId());
            param.put("ProjectName", infoData.getProjectName());
            param.put("Type", infoData.getType());
            param.put("typeId", infoData.getTypeId());
            param.put("ProjcLat", PreferenceUtil.getProjectLat());
            param.put("ProjcLng", PreferenceUtil.getProjectLng());
            param.put("Province", PreferenceUtil.getProvince());
            param.put("CameraId", "");//设备Id  默认0或空字符串
            param.put("jobId", infoData.getJobId());
            param.put("removeId", infoData.getRemoveId());
            param.put("jobCodeName", infoData.getJobCodeName());
            param.put("Note", infoData.getNote());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postJson(activity, BASE_URL_NEW + "action=uploadInsideDataAndImage", param, netCall);
    }

    // 获取工单列表（拆机）
    public static void removeJobCodeList(Activity activity, String projectId, String typeId, NetCall netCall) {
        HashMap<String, String> params = new HashMap<>();
        params.put("projId", projectId + "");
        params.put("typeId", typeId + "");
        get(activity, BASE_URL_NEW + "action=removeJobCodeList", params, netCall);
    }

    // 获取设备列表（拆机）
    public static void getRemoveDatCameraCamNameList(Activity activity, String projectId, String removeId, NetCall netCall) {
        HashMap<String, String> params = new HashMap<>();
        params.put("projId", projectId + "");
        params.put("removeId", removeId + "");
        get(activity, BASE_URL_NEW + "action=getRemoveDatCameraCamNameList", params, netCall);
    }

    //拆机上传数据
    public static void uploadRemoveDataAndImage(Activity activity, SendDataBean data, final NetCall netCall) {
        JSONObject param = new JSONObject();
        try {
            param.put("ProjectId", data.ProjectId);
            param.put("Imgstr", data.ImgstrDev);//设备照片（用户选中否则为空）
            param.put("ImgstrAculvert", data.ImgstrAculvert);//工程图片
            param.put("Note", data.Note);
            param.put("UserName", data.UserName);
            param.put("userid", PreferenceUtil.getUserId());
            param.put("ProjectName", data.ProjectName);
            param.put("Type", data.Type);
            param.put("ProjcLat", data.ProjcLat);
            param.put("ProjcLng", data.ProjcLng);
            param.put("Province", PreferenceUtil.getProvince());
            param.put("CameraId", data.CameraId);
            param.put("Progress", data.Progress);
            param.put("IsSaveLocation", data.IsSaveLocation);
            param.put("payid", data.payId);
            param.put("typeid", data.projectTypeId);
            param.put("ispay", data.ispay);
            param.put("removeId", data.removeId);
            param.put("ResponsibilitySubject", data.ResponsibilitySubject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postJson(activity, BASE_URL_NEW + "action=uploadRemoveDataAndImage", param, netCall);
    }

    // 根据项目Id查询设备及工单确认单是否可用
    public static void getIsPlayByProjId(Activity activity, String projectId, String typeId, NetCall netCall) {
        HashMap<String, String> params = new HashMap<>();
        params.put("projId", projectId + "");
        params.put("typeId", typeId + "");
        get(activity, BASE_URL_NEW + "action=getIsPlayByProjId", params, netCall);
    }

    public static void postJson(final Activity activity, final String url, final JSONObject params, final NetCall netCall) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("网络", "请求接口：" + url + params.toString());
                final String result = HttpUtil.postToUrlNoLog(url, params.toString());
                Log.e("网络", "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                Log.e("网络", "" + url + params.toString());
                Log.e("网络", "" + result);
                Log.e("网络", "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                try {
                    JSONObject json = new JSONObject(result);
                    final String resultCode = json.getString("result");
                    final String msg = json.getString("msg");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(result) || resultCode.equals("0")) {
                                netCall.onFail(msg);
                            } else {
                                netCall.onSuccess(result);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }

    public static void get(final Activity activity, final String url, HashMap<String, String> params, final NetCall call) {
        StringBuilder data = new StringBuilder();
        for (String key : params.keySet()) {
            String value = params.get(key);
            data.append("&").append(key).append("=").append(value);
        }
        get(activity, url + data, call);
    }

    public static void get(final Activity activity, final String url, final NetCall call) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("网络", "请求接口：" + url);
                final String result = HttpUtil.getFromUrlNoLog(url);
                Log.e("网络", "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                Log.e("网络", "" + url);
                Log.e("网络", "" + result);
                Log.e("网络", "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                try {
                    JSONObject json = new JSONObject(result);
                    final String resultCode = json.getString("result");
                    final String msg = json.getString("msg");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (TextUtils.isEmpty(result) || resultCode.equals("0")) {
                                call.onFail(msg);
                            } else {
                                call.onSuccess(result);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    call.onFail("网络异常");
                }
            }
        }).start();
    }

    public interface NetCall {
        void onSuccess(String result);

        void onFail(String msg);
    }
}
