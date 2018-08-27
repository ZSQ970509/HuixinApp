package com.king.photo.db;

/**
 * 生理数据的表字段
 */
public class HealthyValuationTable {
    /** app保存数据
     * 生理数据表的表名
     */
    public static final String TABLE_NAME_DATA = "app_data_table";
    /**
     * id,唯一主键
     */
    public static final String APP_DATA_ID = "id";
    /**
     * id,唯一主键
     */
    public static final String APP_DATA_TIME = "currenTime";
    /**
     * 经度
     */
    public static final String ISBATTERY = "isBattery";
    /**
     * 纬度
     */
    public static final String ISAUTOSTART = "isAutoStart";
    /**
     * id,唯一主键
     */
    public static final String OFFICECAPID = "OfficeCapId";
    /**
     * 经度
     */
    public static final String IS_ATTENDANCEPATH = "isattendandcepath";
    /**
     * 纬度
     */
    public static final String IS_ATTENDANCEPATHPROJECTPATH = "isattendandcepathprojectpath";


    /**
     * 纬度
     */
    public static final String AUTO_LOGIN = "auto_login";
    /**
     * id,唯一主键
     */
    public static final String SAVE_PASSWORD = "save_password";
    /**
     * 经度
     */
    public static final String SAVE_NAME = "save_name";
    /**
     * 纬度
     */
    public static final String IS_SAVE_PASSWORD = "is_save_name";
    /**
     * id,唯一主键
     */
    public static final String PATH_PROJECT_NAME = "PATH_PROJECT_NAME";
    /**
     * 经度
     */
    public static final String PATH_PROJECT_ID = "PATH_PROJECT_ID";
    /**
     * 纬度
     */
    public static final String PROJECT_LAT = "project_lat";
    /**
     * id,唯一主键
     */
    public static final String PROJECT_LNG = "project_lng";
    /**
     * 经度
     */
    public static final String PROJECT_ADDRSTR = "project_addrstr";
    /**
     * 纬度
     */
    public static final String UPDATE_DATA = "update_data";
    /**
     * id,唯一主键
     */
    public static final String PROVINCE = "province";
    /**
     * 经度
     */
    public static final String CITY = "city";
    /**
     * 纬度
     */
    public static final String USERID = "UserId";
    /**
     * id,唯一主键
     */
    public static final String USER_NAME = "UserName";

    // 字段的索引号
    public static final int INDEX_APP_DATA_ID = 0;
    public static final int INDEX_APP_DATA_TIME = 1;
    public static final int INDEX_ISBATTERY = 2;
    public static final int INDEX_ISAUTOSTART = 3;
    public static final int INDEX_OFFICECAPID = 4;
    public static final int INDEX_IS_ATTENDANCEPATH = 5;
    public static final int INDEX_IS_ATTENDANCEPATHPROJECTPATH = 6;
    public static final int INDEX_AUTO_LOGIN = 7;
    public static final int INDEX_SAVE_PASSWORD = 8;
    public static final int INDEX_SAVE_NAME = 9;
    public static final int INDEX_IS_SAVE_PASSWORD = 10;
    public static final int INDEX_PATH_PROJECT_NAME = 11;
    public static final int INDEX_PATH_PROJECT_ID = 12;
    public static final int INDEX_PROJECT_LAT = 13;
    public static final int INDEX_PROJECT_LNG = 14;
    public static final int INDEX_PROJECT_ADDRSTR = 15;
    public static final int INDEX_UPDATE_DATA = 16;
    public static final int INDEX_PROVINCE = 17;
    public static final int INDEX_CITY = 18;
    public static final int INDEX_USERID = 19;
    public static final int INDEX_USER_NAME = 20;
    /**
     * SQL,创建表
     */
    public static final String SQL_APP_DATA_TABLE_CREATE = new StringBuilder()
            .append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME_DATA)
            .append("(")
            .append(APP_DATA_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(APP_DATA_TIME).append(" TEXT,")
            .append(ISBATTERY).append(" TEXT,")
            .append(ISAUTOSTART).append(" TEXT,")
            .append(OFFICECAPID).append(" TEXT,")
            .append(IS_ATTENDANCEPATH).append(" TEXT,")
            .append(IS_ATTENDANCEPATHPROJECTPATH).append(" TEXT,")
            .append(AUTO_LOGIN).append(" TEXT,")
            .append(SAVE_PASSWORD).append(" TEXT,")
            .append(SAVE_NAME).append(" TEXT,")
            .append(IS_SAVE_PASSWORD).append(" TEXT,")
            .append(PATH_PROJECT_NAME).append(" TEXT,")
            .append(PATH_PROJECT_ID).append(" TEXT,")
            .append(PROJECT_LAT).append(" TEXT,")
            .append(PROJECT_LNG).append(" TEXT,")
            .append(PROJECT_ADDRSTR).append(" TEXT,")
            .append(UPDATE_DATA).append(" TEXT,")
            .append(PROVINCE).append(" TEXT,")
            .append(CITY).append(" TEXT,")
            .append(USERID).append(" TEXT,")
            .append(USER_NAME).append(" TEXT")
            .append(");")
            .toString();
    /**
     * SQL,向表插入一条数据
     */
    public static final String SQL_APP_DATA = new StringBuilder()
            .append("insert into ")
            .append(TABLE_NAME_DATA)
            .append("(")
            .append(APP_DATA_ID).append(",")
            .append(APP_DATA_TIME).append(",")
            .append(ISBATTERY).append(",")
            .append(ISAUTOSTART).append(",")
            .append(OFFICECAPID).append(",")
            .append(IS_ATTENDANCEPATH).append(" ,")
            .append(IS_ATTENDANCEPATHPROJECTPATH).append(" ,")
            .append(AUTO_LOGIN).append(" ,")
            .append(SAVE_PASSWORD).append(" ,")
            .append(SAVE_NAME).append(" ,")
            .append(IS_SAVE_PASSWORD).append(" ,")
            .append(PATH_PROJECT_NAME).append(" ,")
            .append(PATH_PROJECT_ID).append(" ,")
            .append(PROJECT_LAT).append(" ,")
            .append(PROJECT_LNG).append(" ,")
            .append(PROJECT_ADDRSTR).append(" ,")
            .append(UPDATE_DATA).append(" ,")
            .append(PROVINCE).append(" ,")
            .append(CITY).append(" ,")
            .append(USERID).append(" ,")
            .append(USER_NAME).append(" ,")
            .append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
            .toString();

    /**
     * SQL,清空表数据
     */
    public static final String SQL_APP_DATA_CLEAR = new StringBuilder()
            .append("delete from ")
            .append(TABLE_NAME_DATA)
            .toString();
    /**
     * 生理数据表的表名
     */
    public static final String TABLE_NAME = "healthy_valuation_table";
    /**
     * id,唯一主键
     */
    public static final String ID = "id";
    /**
     * id,唯一主键
     */
    public static final String TIME = "time";
    /**
     * 经度
     */
    public static final String LNG = "lng";
    /**
     * 纬度
     */
    public static final String LAT = "lat";


    // 字段的索引号
    public static final int INDEX_ID = 0;
    public static final int INDEX_TIME = 1;
    public static final int INDEX_LNG = 2;
    public static final int INDEX_LAT = 3;


    /**
     * SQL,创建表
     */
    public static final String SQL_TABLE_CREATE = new StringBuilder()
            .append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME)
            .append("(")
            .append(ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(TIME).append(" TEXT,")
            .append(LNG).append(" TEXT,")
            .append(LAT).append(" TEXT")
            .append(");")
            .toString();
    /**
     * SQL,向表插入一条数据
     */
    public static final String SQL_DATA_ADD = new StringBuilder()
            .append("insert into ")
            .append(TABLE_NAME)
            .append("(")
            .append(ID).append(",")
            .append(TIME).append(",")
            .append(LNG).append(",")
            .append(LAT)
            .append(" values(?,?,?,?)")
            .toString();

    /**
     * SQL,向表删除一条数据
     */
    public static final String SQL_DATA_DEL = new StringBuilder()
            .append("delete from ")
            .append(TABLE_NAME)
            .append(" where id=?")
            .toString();

    /**
     * SQL,清空表数据
     */


    public static final String SQL_DATA_CLEAR = new StringBuilder()
            .append("delete from ")
            .append(TABLE_NAME_DATA)
            .toString();
    /**
     * SQL,清空表数据
     */


    public static final String SQL_DATA_CLEAR_PATH = new StringBuilder()
            .append("delete from ")
            .append(TABLE_NAME)
            .toString();
}
