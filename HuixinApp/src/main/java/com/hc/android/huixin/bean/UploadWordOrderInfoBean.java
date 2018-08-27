package com.hc.android.huixin.bean;

/**
 * 上传工单信息（安装验收单、拆机单）
 */

public class UploadWordOrderInfoBean {
    private String projectId;//项目Id
    private String imgstr;//设备图片上传
    private String note;//备注
    private String projectName;//项目名称
    private String type;//类型名 移机、维护、安装、拆除
    private String typeId;//类型id
    private String cameraId;//设备Id  默认0或空字符串
    private String jobId;//安装工单Id
    private String removeId;//拆机工单Id
    private String jobCodeName;//拆机工单名
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getImgstr() {
        return imgstr;
    }

    public void setImgstr(String imgstr) {
        this.imgstr = imgstr;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }


    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getRemoveId() {
        return removeId;
    }

    public void setRemoveId(String removeId) {
        this.removeId = removeId;
    }

    public String getJobCodeName() {
        return jobCodeName;
    }

    public void setJobCodeName(String jobCodeName) {
        this.jobCodeName = jobCodeName;
    }
}
