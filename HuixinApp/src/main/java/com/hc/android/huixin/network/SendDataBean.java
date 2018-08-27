package com.hc.android.huixin.network;

import java.io.Serializable;

public class SendDataBean implements Serializable {
    public String UserId;
    public String DriverJingDu;
    public String DriverWeiDu;
    public String DriverAlpha;
    public String DriverNum;
    public String InstallPlace;
    public String UnitProjectId;
    public String UnitProjectName;
    public String CamName;

    public String ResponsibilitySubject;
    public String ProjectId;
    public String ProjectName;
    public String proTypeName;
    public String UserName;
    public String Note;
    public String ImgStr;
    public String ProjcLat;
    public String ProjcLng;
    public String addr;
    public String Province;
    public String BuildNum;
    public String PlaceType;
    public String keytype;
    public String ImgstrDev;
    public String ImgstrAculvert;
    public String Type;
    public String CameraId;
    public int ispay;
    public String Progress;
    public int IsSaveLocation;
    public String removeId;
    public String jobId;//工单id
    public String jobName;//工单名
    /**
     * 施工类型id
     */
    public int projectTypeId;
    /**
     * 维护类型id
     */
    public int payId;
    // 唯一ID
    public String SessionId;
    // 部位类型
    public String PositionType;


}
