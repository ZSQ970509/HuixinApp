package com.hc.android.huixin.bean.json;

import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class RemovedeviceGetProjectListJs implements Serializable {


    /**
     * result : 1
     * msg : 获取移机任务单列表成功
     * totalcount : 1
     * data : [{"ProjectId":40174,"ProjectName":"厦门阳光恩耐照明有限公司LED照明智能化生产项目(四期）","ProjAddress":"厦门市海沧区后祥路88号","DshwId":1010,"DshwStatus":"10","ProjectLat":null,"ProjectLng":null,"ActualDistance":-1}]
     */

    private String result;
    private String msg;
    private String totalcount;
    private List<DataBean> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * ProjectId : 40174
         * ProjectName : 厦门阳光恩耐照明有限公司LED照明智能化生产项目(四期）
         * ProjAddress : 厦门市海沧区后祥路88号
         * DshwId : 1010
         * DshwStatus : 10
         * ProjectLat : null
         * ProjectLng : null
         * ActualDistance : -1.0
         */

        private String ProjectId;
        private String ProjectName;
        private String ProjAddress;
        private String DshwId;
        private String DshwStatus;
        private String ProjectLat;
        private String ProjectLng;
        private String ActualDistance;
        private String RemoveNum= "";
        public String getProjectId() {
            return ProjectId;
        }

        public void setProjectId(String ProjectId) {
            this.ProjectId = ProjectId;
        }

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public String getProjAddress() {
            return ProjAddress;
        }

        public void setProjAddress(String ProjAddress) {
            this.ProjAddress = ProjAddress;
        }

        public String getDshwId() {
            return DshwId;
        }

        public void setDshwId(String DshwId) {
            this.DshwId = DshwId;
        }

        public String getDshwStatus() {
            return DshwStatus;
        }

        public void setDshwStatus(String DshwStatus) {
            this.DshwStatus = DshwStatus;
        }

        public String getProjectLat() {
            return ProjectLat;
        }

        public void setProjectLat(String ProjectLat) {
            this.ProjectLat = ProjectLat;
        }

        public String getProjectLng() {
            return ProjectLng;
        }

        public void setProjectLng(String ProjectLng) {
            this.ProjectLng = ProjectLng;
        }

        public String getActualDistance() {
            return ActualDistance;
        }

        public void setActualDistance(String ActualDistance) {
            this.ActualDistance = ActualDistance;
        }

        public String getRemoveNum() {
            return RemoveNum;
        }

        public void setRemoveNum(String removeNum) {
            RemoveNum = removeNum;
        }
    }
}