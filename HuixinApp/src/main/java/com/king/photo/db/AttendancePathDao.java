package com.king.photo.db;

import com.king.photo.model.AttendancePathModel;
import com.king.photo.model.UserModel;

import java.util.List;

public interface AttendancePathDao {

    public void addPhysiological(AttendancePathModel attendancePathModel);

    public void delPhysiological(int id);

    public List<AttendancePathModel> loadPhysiological();

    public void clearAttendancePatn();
    public void addAppDataPhysiological(UserModel userModel);
    public List<UserModel> loadAppDataPhysiological();
    public void clearAppData();
    public void updateAppDataPhysiological(String colName , String value);
}
