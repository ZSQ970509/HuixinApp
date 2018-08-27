package com.king.photo.model;

/**
 * Created by Administrator on 2018/4/2.
 */

public class UserModel {


    private String TIME;
    private String ISBATTERY;
    private String ISAUTOSTART;
    private String OFFICECAPID;
    private String IS_ATTENDANCEPATH;
    private String IS_ATTENDANCEPATHPROJECTPATH;
    private String AUTO_LOGIN;
    private String SAVE_PASSWORD;
    private String SAVE_NAME;//登录时输入的账户，即手机号
    private String IS_SAVE_PASSWORD;
    private String PATH_PROJECT_NAME;
    private String PATH_PROJECT_ID;
    private String PROJECT_LAT;
    private String PROJECT_LNG;
    private String PROJECT_ADDRSTR;
    private String UPDATE_DATA;
    private String PROVINCE;
    private String CITY;
    private String USERID;
    private String USER_NAME;//用户的真实名字

    public UserModel(String TIME, String ISBATTERY, String ISAUTOSTART, String OFFICECAPID, String IS_ATTENDANCEPATH
            , String IS_ATTENDANCEPATHPROJECTPATH, String AUTO_LOGIN, String SAVE_PASSWORD, String SAVE_NAME
            , String IS_SAVE_PASSWORD, String PATH_PROJECT_NAME, String PATH_PROJECT_ID, String PROJECT_LAT, String PROJECT_LNG, String PROJECT_ADDRSTR
            , String UPDATE_DATA, String PROVINCE, String CITY, String USERID, String USER_NAME) {

        this.TIME = TIME;
        this.ISBATTERY = ISBATTERY;
        this.ISAUTOSTART = ISAUTOSTART;
        this.OFFICECAPID = OFFICECAPID;
        this.IS_ATTENDANCEPATH = IS_ATTENDANCEPATH;
        this.IS_ATTENDANCEPATHPROJECTPATH = IS_ATTENDANCEPATHPROJECTPATH;
        this.AUTO_LOGIN = AUTO_LOGIN;
        this.SAVE_PASSWORD = SAVE_PASSWORD;
        this.SAVE_NAME = SAVE_NAME;
        this.IS_SAVE_PASSWORD = IS_SAVE_PASSWORD;
        this.PATH_PROJECT_NAME = PATH_PROJECT_NAME;
        this.PATH_PROJECT_ID = PATH_PROJECT_ID;
        this.PROJECT_LAT = PROJECT_LAT;
        this.PROJECT_LNG = PROJECT_LNG;
        this.PROJECT_ADDRSTR = PROJECT_ADDRSTR;
        this.UPDATE_DATA = UPDATE_DATA;
        this.PROVINCE = PROVINCE;
        this.CITY = CITY;
        this.USERID = USERID;
        this.USER_NAME = USER_NAME;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getISBATTERY() {
        return ISBATTERY;
    }

    public void setISBATTERY(String ISBATTERY) {
        this.ISBATTERY = ISBATTERY;
    }

    public String getISAUTOSTART() {
        return ISAUTOSTART;
    }

    public void setISAUTOSTART(String ISAUTOSTART) {
        this.ISAUTOSTART = ISAUTOSTART;
    }

    public String getOFFICECAPID() {
        return OFFICECAPID;
    }

    public void setOFFICECAPID(String OFFICECAPID) {
        this.OFFICECAPID = OFFICECAPID;
    }

    public String getIS_ATTENDANCEPATH() {
        return IS_ATTENDANCEPATH;
    }

    public void setIS_ATTENDANCEPATH(String IS_ATTENDANCEPATH) {
        this.IS_ATTENDANCEPATH = IS_ATTENDANCEPATH;
    }

    public String getIS_ATTENDANCEPATHPROJECTPATH() {
        return IS_ATTENDANCEPATHPROJECTPATH;
    }

    public void setIS_ATTENDANCEPATHPROJECTPATH(String IS_ATTENDANCEPATHPROJECTPATH) {
        this.IS_ATTENDANCEPATHPROJECTPATH = IS_ATTENDANCEPATHPROJECTPATH;
    }



    public String getAUTO_LOGIN() {
        return AUTO_LOGIN;
    }

    public void setAUTO_LOGIN(String AUTO_LOGIN) {
        this.AUTO_LOGIN = AUTO_LOGIN;
    }

    public String getSAVE_PASSWORD() {
        return SAVE_PASSWORD;
    }

    public void setSAVE_PASSWORD(String SAVE_PASSWORD) {
        this.SAVE_PASSWORD = SAVE_PASSWORD;
    }

    public String getSAVE_NAME() {
        return SAVE_NAME;
    }

    public void setSAVE_NAME(String SAVE_NAME) {
        this.SAVE_NAME = SAVE_NAME;
    }

    public String getIS_SAVE_PASSWORD() {
        return IS_SAVE_PASSWORD;
    }

    public void setIS_SAVE_PASSWORD(String IS_SAVE_PASSWORD) {
        this.IS_SAVE_PASSWORD = IS_SAVE_PASSWORD;
    }

    public String getPATH_PROJECT_NAME() {
        return PATH_PROJECT_NAME;
    }

    public void setPATH_PROJECT_NAME(String PATH_PROJECT_NAME) {
        this.PATH_PROJECT_NAME = PATH_PROJECT_NAME;
    }

    public String getPATH_PROJECT_ID() {
        return PATH_PROJECT_ID;
    }

    public void setPATH_PROJECT_ID(String PATH_PROJECT_ID) {
        this.PATH_PROJECT_ID = PATH_PROJECT_ID;
    }

    public String getPROJECT_LAT() {
        return PROJECT_LAT;
    }

    public void setPROJECT_LAT(String PROJECT_LAT) {
        this.PROJECT_LAT = PROJECT_LAT;
    }

    public String getPROJECT_LNG() {
        return PROJECT_LNG;
    }

    public void setPROJECT_LNG(String PROJECT_LNG) {
        this.PROJECT_LNG = PROJECT_LNG;
    }

    public String getPROJECT_ADDRSTR() {
        return PROJECT_ADDRSTR;
    }

    public void setPROJECT_ADDRSTR(String PROJECT_ADDRSTR) {
        this.PROJECT_ADDRSTR = PROJECT_ADDRSTR;
    }

    public String getUPDATE_DATA() {
        return UPDATE_DATA;
    }

    public void setUPDATE_DATA(String UPDATE_DATA) {
        this.UPDATE_DATA = UPDATE_DATA;
    }

    public String getPROVINCE() {
        return PROVINCE;
    }

    public void setPROVINCE(String PROVINCE) {
        this.PROVINCE = PROVINCE;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }
}
