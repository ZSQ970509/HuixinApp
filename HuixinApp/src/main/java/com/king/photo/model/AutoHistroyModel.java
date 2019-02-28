package com.king.photo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/12/24.
 */

public class AutoHistroyModel implements Serializable{


    /**
     * checkAcceptDevices : [{"checkAcceptId":0,"controllerId":0,"controllerStatus":0,"createTime":"2018-12-25T06:03:09.720Z","deviceId":0,"id":0,"rangingId":0,"rangingStatus":0,"updateTime":"2018-12-25T06:03:09.720Z","videoScanId":0,"videoStatus":0}]
     * createTime : 2018-12-25T06:03:09.720Z
     * deviceIds : [0]
     * id : 0
     * lat : string
     * lng : string
     * projectId : 0
     * status : 0
     * userId : 0
     * userName : string
     */

    private String createTime;
    private int id;
    private String lat;
    private String lng;
    private int projectId;
    private int status;
    private int userId;
    private String userName;
    private List<CheckAcceptDevicesBean> checkAcceptDevices;
    private List<Integer> deviceIds;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public List<Integer> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<Integer> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public static class CheckAcceptDevicesBean {
        /**
         * checkAcceptId : 0
         * controllerId : 0
         * controllerStatus : 0
         * createTime : 2018-12-25T06:03:09.720Z
         * deviceId : 0
         * id : 0
         * rangingId : 0
         * rangingStatus : 0
         * updateTime : 2018-12-25T06:03:09.720Z
         * videoScanId : 0
         * videoStatus : 0
         */

        private int checkAcceptId;
        private int controllerId;
        private int controllerStatus;
        private String createTime;
        private int deviceId;
        private int id;
        private int rangingId;
        private int rangingStatus;
        private String updateTime;
        private int videoScanId;
        private int videoStatus;

        public int getCheckAcceptId() {
            return checkAcceptId;
        }

        public void setCheckAcceptId(int checkAcceptId) {
            this.checkAcceptId = checkAcceptId;
        }

        public int getControllerId() {
            return controllerId;
        }

        public void setControllerId(int controllerId) {
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRangingId() {
            return rangingId;
        }

        public void setRangingId(int rangingId) {
            this.rangingId = rangingId;
        }

        public int getRangingStatus() {
            return rangingStatus;
        }

        public void setRangingStatus(int rangingStatus) {
            this.rangingStatus = rangingStatus;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getVideoScanId() {
            return videoScanId;
        }

        public void setVideoScanId(int videoScanId) {
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
