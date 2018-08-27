package com.hc.android.huixin.bean.json;

import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class DevInstallJobCodeListJs implements Serializable{

    /**
     * result : 1
     * msg : 获取安装工单号列表！
     * data : [{"JobId":3233,"JobCode":"HCSG_17110340247(未上传确认单)","JobPathFiles":null}]
     */

    private String result;
    private String msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * JobId : 3233
         * JobCode : HCSG_17110340247(未上传确认单)
         * JobPathFiles : null
         */

        private String JobId;
        private String JobCode;
        private String JobPathFiles;
        private String JobCodeName;
        public String getJobId() {
            return JobId;
        }

        public void setJobId(String JobId) {
            this.JobId = JobId;
        }

        public String getJobCode() {
            return JobCode;
        }

        public void setJobCode(String JobCode) {
            this.JobCode = JobCode;
        }

        public String getJobPathFiles() {
            return JobPathFiles;
        }

        public void setJobPathFiles(String JobPathFiles) {
            this.JobPathFiles = JobPathFiles;
        }

        public String getJobCodeName() {
            return JobCodeName;
        }

        public void setJobCodeName(String jobCodeName) {
            JobCodeName = jobCodeName;
        }
    }
}
