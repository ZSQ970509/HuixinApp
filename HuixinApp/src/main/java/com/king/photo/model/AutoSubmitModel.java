package com.king.photo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/12/24.
 */

public class AutoSubmitModel implements Serializable{
    public AutoSubmitModel( String projectId, List<String> deviceIds) {
        this.projectId = projectId;
        this.deviceIds = deviceIds;
    }

    private String projectId;
    private List<String> deviceIds;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<String> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<String> deviceIds) {
        this.deviceIds = deviceIds;
    }
}
