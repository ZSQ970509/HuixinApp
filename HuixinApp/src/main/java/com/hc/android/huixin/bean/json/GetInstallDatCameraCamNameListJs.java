package com.hc.android.huixin.bean.json;

import java.util.List;

/**
 *
 */

public class GetInstallDatCameraCamNameListJs {

    /**
     * result : 1
     * msg : 获取安装工单Id查询点位信息列表成功！
     * data : [{"CamId":1033849,"NewCamName":"1033849_加工场（预制厂）高清摄像机4G版","CamTypeId":136,"Flag":0},{"CamId":1033850,"NewCamName":"1033850_大门出入口（隧道洞内）高清摄像机4G版","CamTypeId":137,"Flag":0},{"CamId":1033851,"NewCamName":"1033851_吊装设备操作员智能识别设备","CamTypeId":401,"Flag":0}]
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

    public static class DataBean {
        /**
         * CamId : 1033849
         * NewCamName : 1033849_加工场（预制厂）高清摄像机4G版
         * CamTypeId : 136
         * Flag : 0
         */

        private String CamId;
        private String NewCamName;
        private String CamTypeId;
        private String Flag;
        private String jobCodeName;
        public String getCamId() {
            return CamId;
        }

        public void setCamId(String CamId) {
            this.CamId = CamId;
        }

        public String getNewCamName() {
            return NewCamName;
        }

        public void setNewCamName(String NewCamName) {
            this.NewCamName = NewCamName;
        }

        public String getCamTypeId() {
            return CamTypeId;
        }

        public void setCamTypeId(String CamTypeId) {
            this.CamTypeId = CamTypeId;
        }

        public String getFlag() {
            return Flag;
        }

        public void setFlag(String Flag) {
            this.Flag = Flag;
        }

        public String getJobCodeName() {
            return jobCodeName;
        }

        public void setJobCodeName(String jobCodeName) {
            this.jobCodeName = jobCodeName;
        }
    }
}