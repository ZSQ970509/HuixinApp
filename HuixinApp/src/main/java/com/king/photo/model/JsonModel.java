package com.king.photo.model;

public class JsonModel {
    private String result;
    private String msg;

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

    public JsonModel(String result, String msg) {
        super();
        this.result = result;
        this.msg = msg;
    }

}
