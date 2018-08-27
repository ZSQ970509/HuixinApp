package com.king.photo.model;

/**
 * Created by Administrator on 2018/4/2.
 */

public class AttendancePathModel {
    private String AttendanceID;
    private String AttendancePathDate;
    private String AttendancePathLat;
    private String AttendancePathLng;

    public AttendancePathModel(String attendanceID,String attendancePathDate, String attendancePathLat, String attendancePathLng) {
        AttendanceID = attendanceID;
        AttendancePathDate = attendancePathDate;
        AttendancePathLat = attendancePathLat;
        AttendancePathLng = attendancePathLng;
    }
    public AttendancePathModel(String attendancePathDate, String attendancePathLat, String attendancePathLng) {
        AttendancePathDate = attendancePathDate;
        AttendancePathLat = attendancePathLat;
        AttendancePathLng = attendancePathLng;
    }

    public String getAttendanceID() {
        return AttendanceID;
    }

    public void setAttendanceID(String attendanceID) {
        AttendanceID = attendanceID;
    }

    public String getAttendancePathDate() {
        return AttendancePathDate;
    }

    public void setAttendancePathDate(String attendancePathDate) {
        AttendancePathDate = attendancePathDate;
    }

    public String getAttendancePathLat() {
        return AttendancePathLat;
    }

    public void setAttendancePathLat(String attendancePathLat) {
        AttendancePathLat = attendancePathLat;
    }

    public String getAttendancePathLng() {
        return AttendancePathLng;
    }

    public void setAttendancePathLng(String attendancePathLng) {
        AttendancePathLng = attendancePathLng;
    }

    @Override
    public String toString() {
        return "AttendancePathModel{" +
                "AttendanceID='" + AttendanceID + '\'' +
                ", AttendancePathDate='" + AttendancePathDate + '\'' +
                ", AttendancePathLat='" + AttendancePathLat + '\'' +
                ", AttendancePathLng='" + AttendancePathLng + '\'' +
                '}';
    }
}
