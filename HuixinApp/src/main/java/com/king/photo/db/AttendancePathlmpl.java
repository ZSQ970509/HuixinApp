package com.king.photo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.hc.android.huixin.MyApplication;
import com.king.photo.model.AttendancePathModel;
import com.king.photo.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class AttendancePathlmpl implements AttendancePathDao {

    private static final String TAG = AttendancePathlmpl.class.getSimpleName();

    public static SQLiteDatabase Db;

    public AttendancePathlmpl() {
        super();
        // 获取单例模式的数据库实例
        Db = SQLiteDb.getInstance(MyApplication.getInstance()).getDB();
    }
    /**
     * 添加一条生理数据
     *
     * @param data
     * @return
     */
    public void addAppDataPhysiological(UserModel data) {
        if (null == data) {
            Log.w(TAG, "添加的数据为空");
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(HealthyValuationTable.APP_DATA_TIME, data.getTIME());
        cv.put(HealthyValuationTable.ISBATTERY, data.getISBATTERY());
        cv.put(HealthyValuationTable.ISAUTOSTART, data.getISAUTOSTART());
        cv.put(HealthyValuationTable.OFFICECAPID, data.getOFFICECAPID());
        cv.put(HealthyValuationTable.IS_ATTENDANCEPATH, data.getIS_ATTENDANCEPATH());
        cv.put(HealthyValuationTable.IS_ATTENDANCEPATHPROJECTPATH, data.getIS_ATTENDANCEPATHPROJECTPATH());
        cv.put(HealthyValuationTable.AUTO_LOGIN, data.getAUTO_LOGIN());
        cv.put(HealthyValuationTable.SAVE_PASSWORD, data.getSAVE_PASSWORD());
        cv.put(HealthyValuationTable.SAVE_NAME, data.getSAVE_NAME());
        cv.put(HealthyValuationTable.IS_SAVE_PASSWORD, data.getIS_SAVE_PASSWORD());
        cv.put(HealthyValuationTable.PATH_PROJECT_NAME, data.getPATH_PROJECT_NAME());
        cv.put(HealthyValuationTable.PATH_PROJECT_ID, data.getPATH_PROJECT_ID());
        cv.put(HealthyValuationTable.PROJECT_LAT, data.getPROJECT_LAT());
        cv.put(HealthyValuationTable.PROJECT_LNG, data.getPROJECT_LNG());
        cv.put(HealthyValuationTable.PROJECT_ADDRSTR, data.getPROJECT_ADDRSTR());
        cv.put(HealthyValuationTable.UPDATE_DATA, data.getUPDATE_DATA());
        cv.put(HealthyValuationTable.PROVINCE, data.getPROVINCE());
        cv.put(HealthyValuationTable.CITY, data.getCITY());
        cv.put(HealthyValuationTable.USERID, data.getUSERID());
        cv.put(HealthyValuationTable.USER_NAME, data.getUSER_NAME());
        Db.insert(HealthyValuationTable.TABLE_NAME_DATA, null, cv);
    }

    /**
     * 修改app数据
     *
     * @param
     * @return
     */
    public void updateAppDataPhysiological(String colName, String value) {

        String SQL_UPDATE_DEL = "update " + HealthyValuationTable.TABLE_NAME_DATA + " set " + colName + "=?"
                + " where id=1";
        Object[] object = new Object[]{value};
        Db.execSQL(SQL_UPDATE_DEL, object);
    }

    /**
     * 查询全部生理数据
     *
     * @return
     */
    public List<UserModel> loadAppDataPhysiological() {
        // 创建返回值
        List<UserModel> userModelList = new ArrayList<UserModel>();
        // 查询条件（id 降序）
        String order = HealthyValuationTable.ID + " ASC";
        // 获取游标
        Cursor cursor = null;
        try {
            cursor = Db.query(HealthyValuationTable.TABLE_NAME_DATA, null, null, null, null, null, order);
            if (cursor != null && cursor.moveToFirst()) {
                // 循环取出所有数值
                do {
                    String TIME = cursor.getString(HealthyValuationTable.INDEX_APP_DATA_TIME);
                    String ISBATTERY = cursor.getString(HealthyValuationTable.INDEX_ISBATTERY);
                    String ISAUTOSTART = cursor.getString(HealthyValuationTable.INDEX_ISAUTOSTART);
                    String OFFICECAPID = cursor.getString(HealthyValuationTable.INDEX_OFFICECAPID);
                    String IS_ATTENDANCEPATH = cursor.getString(HealthyValuationTable.INDEX_IS_ATTENDANCEPATH);
                    String IS_ATTENDANCEPATHPROJECTPATH = cursor.getString(HealthyValuationTable.INDEX_IS_ATTENDANCEPATHPROJECTPATH);
                    String AUTO_LOGIN = cursor.getString(HealthyValuationTable.INDEX_AUTO_LOGIN);
                    String SAVE_PASSWORD = cursor.getString(HealthyValuationTable.INDEX_SAVE_PASSWORD);
                    String SAVE_NAME = cursor.getString(HealthyValuationTable.INDEX_SAVE_NAME);
                    String IS_SAVE_PASSWORD = cursor.getString(HealthyValuationTable.INDEX_IS_SAVE_PASSWORD);
                    String PATH_PROJECT_NAME = cursor.getString(HealthyValuationTable.INDEX_PATH_PROJECT_NAME);
                    String PATH_PROJECT_ID = cursor.getString(HealthyValuationTable.INDEX_PATH_PROJECT_ID);
                    String PROJECT_LAT = cursor.getString(HealthyValuationTable.INDEX_PROJECT_LAT);
                    String PROJECT_LNG = cursor.getString(HealthyValuationTable.INDEX_PROJECT_LNG);
                    String PROJECT_ADDRSTR = cursor.getString(HealthyValuationTable.INDEX_PROJECT_ADDRSTR);
                    String UPDATE_DATA = cursor.getString(HealthyValuationTable.INDEX_UPDATE_DATA);
                    String PROVINCE = cursor.getString(HealthyValuationTable.INDEX_PROVINCE);
                    String CITY = cursor.getString(HealthyValuationTable.INDEX_CITY);
                    String USERID = cursor.getString(HealthyValuationTable.INDEX_USERID);
                    String USER_NAME = cursor.getString(HealthyValuationTable.INDEX_USER_NAME);

                    UserModel userModel = new UserModel(TIME, ISBATTERY, ISAUTOSTART, OFFICECAPID, IS_ATTENDANCEPATH, IS_ATTENDANCEPATHPROJECTPATH
                            , AUTO_LOGIN, SAVE_PASSWORD, SAVE_NAME, IS_SAVE_PASSWORD, PATH_PROJECT_NAME, PATH_PROJECT_ID, PROJECT_LAT, PROJECT_LNG, PROJECT_ADDRSTR
                            , UPDATE_DATA, PROVINCE, CITY, USERID, USER_NAME);

                    // 添加到集合中
                    userModelList.add(userModel);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userModelList;
    }
    /**
     * 添加一条生理数据
     *
     * @param data
     * @return
     */
    public void addPhysiological(AttendancePathModel data) {
        if (null == data) {
            Log.w(TAG, "添加的数据为空");
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(HealthyValuationTable.TIME, data.getAttendancePathDate());
        cv.put(HealthyValuationTable.LNG, data.getAttendancePathLng());
        cv.put(HealthyValuationTable.LAT, data.getAttendancePathLat());
        Db.insert(HealthyValuationTable.TABLE_NAME, null, cv);
    }

    /**
     * 查询全部生理数据
     *
     * @return
     */
    public List<AttendancePathModel> loadPhysiological() {
        // 创建返回值
        List<AttendancePathModel> attendancePathModelList = new ArrayList<>();
        // 查询条件（id 降序）
        String order = HealthyValuationTable.ID + " ASC";
        // 获取游标
        Cursor cursor = null;
        try {
            cursor = Db.query(HealthyValuationTable.TABLE_NAME, null, null, null, null, null, order);
            if (cursor != null && cursor.moveToFirst()) {
                // 循环取出所有数值
                do {
                    int id = cursor.getInt(HealthyValuationTable.INDEX_ID);
                    String data = cursor.getString(HealthyValuationTable.INDEX_TIME);
                    String lng = cursor.getString(HealthyValuationTable.INDEX_LNG);
                    String lat = cursor.getString(HealthyValuationTable.INDEX_LAT);
                    //Log.i(TAG, "读1:" + data + " " + lng + " " + lat);
                    AttendancePathModel attendancePathModel = new AttendancePathModel(id+"",data, lng, lat);
                    // 添加到集合中
                    attendancePathModelList.add(attendancePathModel);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return attendancePathModelList;
    }

    /**
     * 根据id删除一条生理数据
     *
     * @param id
     */
    public void delPhysiological(int id) {
        Db.execSQL(HealthyValuationTable.SQL_DATA_DEL, new Object[]{id});
    }
    /**
     * 清空数据表
     *
     *
     */
    public void clearAttendancePatn() {
        Db.execSQL(HealthyValuationTable.SQL_DATA_CLEAR_PATH);
    }
    public void clearAppData() {
        Db.execSQL(HealthyValuationTable.SQL_DATA_CLEAR);
    }
}
