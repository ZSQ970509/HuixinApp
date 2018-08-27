package com.hc.android.huixin.bean.json;

import java.util.List;

/**
 *
 */

public class GetRemoveDatCameraCamNameListJs {

    /**
     * result : 1
     * msg : 根据拆机工单Id查询点位信息列表成功！
     * data : [{"RCameraId":1018803,"CamName":"1-1智能测距摄像机","CamProjId":13196},{"RCameraId":1018804,"CamName":"1-3巡航摄像机","CamProjId":13196},{"RCameraId":1018805,"CamName":"1-2长焦摄像机","CamProjId":13196},{"RCameraId":1018806,"CamName":"1-4可控摄像机","CamProjId":13196}]
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
         * RCameraId : 1018803
         * CamName : 1-1智能测距摄像机
         * CamProjId : 13196
         */

        private String RCameraId;
        private String CamName;
        private String CamProjId;

        public String getRCameraId() {
            return RCameraId;
        }

        public void setRCameraId(String RCameraId) {
            this.RCameraId = RCameraId;
        }

        public String getCamName() {
            return CamName;
        }

        public void setCamName(String CamName) {
            this.CamName = CamName;
        }

        public String getCamProjId() {
            return CamProjId;
        }

        public void setCamProjId(String CamProjId) {
            this.CamProjId = CamProjId;
        }
    }
}