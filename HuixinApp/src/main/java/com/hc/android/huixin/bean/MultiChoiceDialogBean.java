package com.hc.android.huixin.bean;

import android.text.TextUtils;

/**
 * 多选择对话框数据类
 */

public class MultiChoiceDialogBean {
    private String[] data;
    private String selectData;//选中的数据
    private String selectDataId;//选中的数据id
    private boolean[] isSelect;

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
        isSelect = new boolean[data.length];
        selectData = "";
        selectDataId = "";
    }

    public boolean[] getIsSelect() {
        return isSelect;
    }

    public String getSelectData() {
        return selectData;
    }

    public void clearSelectData() {
        selectData = "";
    }

    public void addSelectData(String selectData) {
        if (TextUtils.isEmpty(this.selectData)) {
            this.selectData = selectData;
        } else {
            this.selectData += "|" + selectData;
        }
    }

    public String getSelectDataId() {
        return selectDataId;
    }

    public void clearSelectDataId() {
        selectDataId = "";
    }

    public void addSelectDataId(String selectDataId) {
        if (TextUtils.isEmpty(this.selectDataId)) {
            this.selectDataId = selectDataId;
        } else {
            this.selectDataId += "|" + selectDataId;
        }
    }
}
