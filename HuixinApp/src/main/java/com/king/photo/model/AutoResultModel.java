package com.king.photo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/12/26.
 */

public class AutoResultModel implements Serializable{


    /**
     * checkAcceptDevices : [{"checkAcceptId":20,"controllerId":null,"controllerStatus":2,"createTime":"2019-01-02 14:16:07","deviceId":1028922,"deviceName":"3/3吴鹤区间2号竖井1-1施工现场（全景）","id":11,"rangingId":null,"rangingStatus":2,"updateTime":null,"videoScanId":null,"videoStatus":3},{"checkAcceptId":20,"controllerId":null,"controllerStatus":2,"createTime":"2019-01-02 14:16:07","deviceId":1028923,"deviceName":"1-1营前站施工现场（全景）","id":12,"rangingId":null,"rangingStatus":2,"updateTime":null,"videoScanId":null,"videoStatus":3},{"checkAcceptId":20,"controllerId":null,"controllerStatus":2,"createTime":"2019-01-02 14:16:07","deviceId":1028924,"deviceName":"1-2营前站施工现场（全景）","id":13,"rangingId":null,"rangingStatus":2,"updateTime":null,"videoScanId":null,"videoStatus":3}]
     * createTime : 2019-01-02 14:16:07
     * deviceIds : null
     * id : 20
     * lat : 26.075669
     * lng : 119.284755
     * projectId : 38788
     * status : 0
     * userAccount : 17301370712
     * userId : 103481
     * userName : 郑书强
     */

    private String createTime;
    private Object deviceIds;
    private int id;
    private String lat;
    private String lng;
    private int projectId;
    private int status;
    private String userAccount;
    private int userId;
    private String userName;
    private List<CheckAcceptDevicesBean> checkAcceptDevices;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(Object deviceIds) {
        this.deviceIds = deviceIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<CheckAcceptDevicesBean> getCheckAcceptDevices() {
        return checkAcceptDevices;
    }

    public void setCheckAcceptDevices(List<CheckAcceptDevicesBean> checkAcceptDevices) {
        this.checkAcceptDevices = checkAcceptDevices;
    }

    public static class CheckAcceptDevicesBean {
        /**
         * checkAcceptId : 20
         * controllerId : null
         * controllerStatus : 2
         * createTime : 2019-01-02 14:16:07
         * deviceId : 1028922
         * deviceName : 3/3吴鹤区间2号竖井1-1施工现场（全景）
         * id : 11
         * rangingId : null
         * rangingStatus : 2
         * updateTime : null
         * videoScanId : null
         * videoStatus : 3
         */

        private int checkAcceptId;
        private Object controllerId;
        private int controllerStatus;
        private String createTime;
        private int deviceId;
        private String deviceName;
        private int id;
        private Object rangingId;
        private int rangingStatus;
        private Object updateTime;
        private Object videoScanId;
        private int videoStatus;

        public int getCheckAcceptId() {
            return checkAcceptId;
        }

        public void setCheckAcceptId(int checkAcceptId) {
            this.checkAcceptId = checkAcceptId;
        }

        public Object getControllerId() {
            return controllerId;
        }

        public void setControllerId(Object controllerId) {
            this.controllerId = controllerId;
        }

        public int getControllerStatus() {
            return controllerStatus;
        }

        public void setControllerStatus(int controllerStatus) {
            this.controllerStatus = controllerStatus;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getRangingId() {
            return rangingId;
        }

        public void setRangingId(Object rangingId) {
            this.rangingId = rangingId;
        }

        public int getRangingStatus() {
            return rangingStatus;
        }

        public void setRangingStatus(int rangingStatus) {
            this.rangingStatus = rangingStatus;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getVideoScanId() {
            return videoScanId;
        }

        public void setVideoScanId(Object videoScanId) {
            this.videoScanId = videoScanId;
        }

        public int getVideoStatus() {
            return videoStatus;
        }

        public void setVideoStatus(int videoStatus) {
            this.videoStatus = videoStatus;
        }
    }
}
