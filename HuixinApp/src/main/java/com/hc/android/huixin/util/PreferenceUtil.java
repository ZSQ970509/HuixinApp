package com.hc.android.huixin.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.hc.android.huixin.MyApplication;
import com.king.photo.db.HealthyValuationTable;
import com.king.photo.db.pref.PrefHelper;

public class PreferenceUtil {
    private static final String Crash = "crash";
    private static final String TIME = "currenTime";
    private static final String ISBATTERY = "isBattery";
    private static final String ISAUTOSTART = "isAutoStart";
    private static final String OFFICECAPID = "OfficeCapId";
    private static final String IS_ATTENDANCEPATH = "isattendandcepath";
    private static final String IS_ATTENDANCEPATHPROJECTPATH = "isattendandcepathprojectpath";
    private static final String PREFEREN_NAME = "preference_huixin";
    private static final String FIRST_OPEN_APP = "first_open_app";
    private static final String AUTO_LOGIN = "auto_login";
    private static final String SAVE_PASSWORD = "save_password";
    private static final String SAVE_NAME = "save_name";//登录时输入的账户，即手机号
    private static final String IS_SAVE_PASSWORD = "is_save_name";
    private static final String PATH_PROJECT_NAME = "PATH_PROJECT_NAME";
    private static final String PATH_PROJECT_ID = "PATH_PROJECT_ID";
    private static final String PROJECT_LAT = "project_lat";
    private static final String PROJECT_LNG = "project_lng";
    private static final String PROJECT_ADDRSTR = "project_addrstr";
    private static final String UPDATE_DATA = "update_data";
    private static final String PROVINCE = "province";
    private static final String CITY = "city";
    private static final String USERID = "UserId";
    private static final String USER_NAME = "UserName";//用户的真实名字
    private static PrefHelper mPrefHelper = PrefHelper.Instance;
    public static void saveCrash(String value) {
        mPrefHelper.setPref(Crash, value);
    }

    public static String getCrash() {

        return mPrefHelper.getPref(Crash, "");
    }

    public static void saveTime(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.APP_DATA_TIME,value+"");
        //mPrefHelper.setPref(TIME, value);
    }

    public static String getTime() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getTIME();
       // return mPrefHelper.getPref(TIME, "");
    }

    public static void saveIsBattery(boolean value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.ISBATTERY,value+"");
        //mPrefHelper.setPref(ISBATTERY, value);
    }

    public static boolean getIsBattery() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getISBATTERY().equals("true")?true:false;
        //return mPrefHelper.getPref(ISBATTERY, false);
    }

    public static void saveIsAutoStart(boolean value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.ISAUTOSTART,value+"");
        //mPrefHelper.setPref(ISAUTOSTART, value);
    }

    public static boolean getIsAutoStart() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getISAUTOSTART().equals("true")?true:false;
        //return mPrefHelper.getPref(ISAUTOSTART, false);
    }

    public static void saveUserId(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.USERID,value+"");
        //mPrefHelper.setPref(USERID, value);
    }

    public static String getUserId() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getUSERID();
       // return mPrefHelper.getPref(USERID, "");
    }

    public static void saveCity(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.CITY,value+"");
        //mPrefHelper.setPref(CITY, value);
    }

    public static String getCity() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getCITY();
        //return mPrefHelper.getPref(CITY, "");
    }

    public static void saveProvince(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.PROVINCE,value+"");
        //mPrefHelper.setPref(PROVINCE, value);
    }

    public static String getProvince() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getPROVINCE();
        //return mPrefHelper.getPref(PROVINCE, "35");
    }

    public static void saveUpdateData(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.UPDATE_DATA,value+"");
        //mPrefHelper.setPref(UPDATE_DATA, value);
    }

    public static String getUpdateData() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getUPDATE_DATA();
        //return mPrefHelper.getPref(UPDATE_DATA, "");
    }

    public static void saveOfficeCapId(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.OFFICECAPID,value+"");
        //mPrefHelper.setPref(OFFICECAPID, value);
    }

    public static String getOfficeCapId() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getOFFICECAPID();
        //return mPrefHelper.getPref(OFFICECAPID, "");
    }

    public static void saveAttendandcePathProjectHumen(boolean value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.IS_ATTENDANCEPATHPROJECTPATH,value+"");
        //mPrefHelper.setPref(IS_ATTENDANCEPATHPROJECTPATH, value);
    }

    public static boolean getAttendandcePathProjectHumen() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getIS_ATTENDANCEPATHPROJECTPATH().equals("true")?true:false;
        //return mPrefHelper.getPref(IS_ATTENDANCEPATHPROJECTPATH, false);
    }

    public static void saveAttendandcePath(boolean value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.IS_ATTENDANCEPATH,value+"");
        //mPrefHelper.setPref(IS_ATTENDANCEPATH, value);
    }

    public static String getPathProjectName() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getPATH_PROJECT_NAME();
        //return mPrefHelper.getPref(PATH_PROJECT_NAME, "");
    }

    public static void savePathProjectName(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.PATH_PROJECT_NAME,value+"");
        //mPrefHelper.setPref(PATH_PROJECT_NAME, value);
    }

    public static String getPathProjectId() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getPATH_PROJECT_ID();
        //return mPrefHelper.getPref(PATH_PROJECT_ID, "");
    }

    public static void savePathProjectId(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.PATH_PROJECT_ID,value+"");
        //mPrefHelper.setPref(PATH_PROJECT_ID, value);
    }

    public static boolean getAttendandcePath() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getIS_ATTENDANCEPATH().equals("true")?true:false;
        //return mPrefHelper.getPref(IS_ATTENDANCEPATH, false);
    }

    public static void saveProjectLat(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.PROJECT_LAT,value+"");
       // mPrefHelper.setPref(PROJECT_LAT, value);
    }

    public static String getProjectLat() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getPROJECT_LAT();
        //return mPrefHelper.getPref(PROJECT_LAT, "");
    }

    public static void saveProjectLng(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.PROJECT_LNG,value+"");
        //mPrefHelper.setPref(PROJECT_LNG, value);
    }

    public static String getProjectLng() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getPROJECT_LNG();
        //return mPrefHelper.getPref(PROJECT_LNG, "");
    }

    public static void saveProjectAddrStr(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.PROJECT_ADDRSTR,value+"");
        //mPrefHelper.setPref(PROJECT_ADDRSTR, value);
    }

    public static String getProjectAddrStr() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getPROJECT_ADDRSTR();
        //return mPrefHelper.getPref(PROJECT_ADDRSTR, "");
    }

    public static boolean isSavePassword() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getIS_SAVE_PASSWORD().equals("true")?true:false;
        //return mPrefHelper.getPref(IS_SAVE_PASSWORD, false);
    }

    public static void setIsSavePassword(boolean value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.IS_SAVE_PASSWORD,value+"");
        //mPrefHelper.setPref(IS_SAVE_PASSWORD, value);
    }

    public static boolean autoLogin() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getAUTO_LOGIN().equals("true")?true:false;
        //return mPrefHelper.getPref(AUTO_LOGIN, false);
    }

    public static void setAutoLogin(boolean value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.AUTO_LOGIN,value+"");
        //mPrefHelper.setPref(AUTO_LOGIN, value);
    }

    public static void saveName(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.SAVE_NAME,value+"");
        //mPrefHelper.setPref(SAVE_NAME, value);
    }

    public static String getName() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getSAVE_NAME();
        //return mPrefHelper.getPref(SAVE_NAME, "");
    }

    public static void savePassword(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.SAVE_PASSWORD,value+"");
        //mPrefHelper.setPref(SAVE_PASSWORD, value);
    }

    public static String getPassword() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getSAVE_PASSWORD();
        //return mPrefHelper.getPref(SAVE_PASSWORD, "");
    }

    public static String getUserName() {
        return MyApplication.attendancePathDao.loadAppDataPhysiological().get(0).getUSER_NAME();
        //return mPrefHelper.getPref(USER_NAME, "");
    }

    public static void saveUserName(String value) {
        MyApplication.attendancePathDao.updateAppDataPhysiological(HealthyValuationTable.USER_NAME,value+"");
        //mPrefHelper.setPref(USER_NAME, value);
    }

    public static boolean isFirstOpenApp() {
        String value = mPrefHelper.getPref(FIRST_OPEN_APP, "");
        if (value.equals("true")) {
            return false;
        } else {
            mPrefHelper.setPref(FIRST_OPEN_APP, "true");
            return true;
        }
    }
}
