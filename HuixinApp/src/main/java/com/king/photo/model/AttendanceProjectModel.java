package com.king.photo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */

public class AttendanceProjectModel implements Serializable{
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

    public static class DataBean implements Serializable {
        /**
         * ProjectId : 44251
         * ProjectName : 颐荷·亚洲A 地块23#、25~33#、35#~38#、S1#~S3#、S5#楼
         * ProjectLat : null
         * ProjectLng : null
         * ActualDistance : -1
         */

        private String ProjectId;
        private String ProjectName;
        private String ProjectLat;
        private String ProjectLng;
        private String ActualDistance;

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
    }
}
