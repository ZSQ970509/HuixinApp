package com.hc.android.huixin.bean;

import com.hc.android.huixin.type.CommonShowInfoTypeEnum;

import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class CommonShowInfoBean implements Serializable{
    private int requestCode;
    private List<DataBean> dataList;

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public List<DataBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataBean> dataList) {
        this.dataList = dataList;
    }

    public class DataBean implements Serializable{
        private String data;
        private CommonShowInfoTypeEnum type;
        public String getData() {
            return data;
        }
        public void setData(String data) {
            this.data = data;
        }
        public CommonShowInfoTypeEnum getType() {
            return type;
        }
        public void setType(CommonShowInfoTypeEnum type) {
            this.type = type;
        }
    }
}
