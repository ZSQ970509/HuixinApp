package com.hc.android.huixin.bean.json;

/**
 *
 */

public class GetTotalTypeByCamTypeIdJs {

    /**
     * result : 1
     * msg : 根据设备类型Id获取统类（0：未定义，1：测距类，2：普通类，3后续加入）
     * data : 1
     */

    private String result;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
