package com.hc.android.huixin.bean.json;

import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class RemoveJobCodeListJs implements Serializable{

    /**
     * result : 1
     * msg : 获取拆机工单号列表！
     * data : [{"RemoveId":2563,"RemoveJobCode":"HCCJ_1709262563(未上传确认单)","RemovePathFiles":null}]
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
         * RemoveId : 2563
         * RemoveJobCode : HCCJ_1709262563(未上传确认单)
         * RemovePathFiles : null
         */

        private String RemoveId;
        private String RemoveJobCode;
        private String RemovePathFiles;

        public String getRemoveId() {
            return RemoveId;
        }

        public void setRemoveId(String RemoveId) {
            this.RemoveId = RemoveId;
        }

        public String getRemoveJobCode() {
            return RemoveJobCode;
        }

        public void setRemoveJobCode(String RemoveJobCode) {
            this.RemoveJobCode = RemoveJobCode;
        }

        public String getRemovePathFiles() {
            return RemovePathFiles;
        }

        public void setRemovePathFiles(String RemovePathFiles) {
            this.RemovePathFiles = RemovePathFiles;
        }
    }
}
