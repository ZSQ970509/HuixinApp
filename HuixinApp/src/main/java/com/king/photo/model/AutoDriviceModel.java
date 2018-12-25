package com.king.photo.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/24.
 */

public class AutoDriviceModel implements Serializable{

    public AutoDriviceModel() {
    }

    /**
     * completedDate :
     * configXml : <xml><cam><type>7</type><ip>218.207.182.53</ip><port>35060</port><account>hcjk</account><password>hckj831903</password><naming>591000006090101</naming><hxPlayNaming>591000006090101</hxPlayNaming><lacId>1468</lacId><camSeqId>65178240293_001</camSeqId><sysId>30</sysId><camId>1028922</camId><name>1-1吴鹤区间全景成像测距摄像机</name><isFromSz>1</isFromSz><showid>1</showid><isusinghczc>1</isusinghczc><ishxplay>0</ishxplay><channelId>1</channelId><obdistance/><horizontalAngle/><verticalAngle/><xo/><yo/><zo/><alpha/><lng/><lat/><ccdL/><ccdW/><hidCross1/><hidTriangle1/><hidCross2/><hidTriangle2/><hidCross3/><hidTriangle3/><htIp/><htPort/><htAccount/><htPassword/><positionX>0</positionX><positionY>0</positionY><devLng>119.283362</devLng><devLat>26.074715</devLat><devElevation/><pipNaming/><pipAccount/><pipPassword/><pipIp/><pipPort/><Pm10WarningVal/><Pm25WarningVal/><TemWarningVal/><HumidityWarningVal/><NoiseWarningVal/><MinWorkVal/><MaxWorkVal/><RestWorkVal/><hxPlayAccount/><hxPlayPassword>654321</hxPlayPassword><hxPlayIp/><hxPlayPort/><vissPlayAccount/><vissPlayPassword>654321</vissPlayPassword><vissPlayIp/><vissPlayPort/><usn>a5ae42480473daf4282d3ef6636c8257</usn><uid>100175</uid><fun/><hikCameraType>2</hikCameraType><hikEnable>off</hikEnable><camName>3/3吴鹤区间2号竖井1-1施工现场（全景）</camName><camItemId/><deviceNum/><hikxml/><SPID>1</SPID><serverIp>119.23.13.32</serverIp><serverPort>28888</serverPort></cam></xml>
     * contractGuid :
     * devConfigDto : {"account":"hcjk","camSeqId":"65178240293_001","ip":"218.207.182.53","naming":"591000006090101","password":"hckj831903","port":35060,"serverIp":"119.23.13.32","serverPort":28888,"type":7}
     * devGuid : 8F122B32-0002-41B9-B9A7-BBC9CC2BF6F5
     * devId : 1028922
     * devMac :
     * devName : 3/3吴鹤区间2号竖井1-1施工现场（全景）
     * devSn : 65178240293_001
     * devTypeGuid :
     * endUseDate :
     * flowState : 15
     * flowStateStr : 监控中
     * hlhtDcpk : DCPK_402881b85de0b44a015de9f48562157c
     * installDate : 2017-08-16 15:31:30
     * isDelete : false
     * isDeleteStr : 否
     * isEnable : true
     * isEnableStr : 是
     * isPrivate : 1
     * isTest : 0
     * isTestStr : 否
     * memo :
     * photo :
     * platAccountId : 1468
     * platAccountIdStr : 移动
     * projId : 38788
     * sptId : 1
     * startUseDate : 2017-08-16 15:31:30
     * stockOutDate :
     * szItemId :
     * typeId : 118
     * typeIdStr : 全景成像测距摄像机
     * uninstallDate :
     */

    private String completedDate;
    private String configXml;
    private String contractGuid;
    private DevConfigDtoBean devConfigDto;
    private String devGuid;
    private String devId;
    private String devMac;
    private String devName;
    private String devSn;
    private String devTypeGuid;
    private String endUseDate;
    private String flowState;
    private String flowStateStr;
    private String hlhtDcpk;
    private String installDate;
    private String isDelete;
    private String isDeleteStr;
    private String isEnable;
    private String isEnableStr;
    private String isPrivate;
    private String isTest;
    private String isTestStr;
    private String memo;
    private String photo;
    private String platAccountId;
    private String platAccountIdStr;
    private String projId;
    private String sptId;
    private String startUseDate;
    private String stockOutDate;
    private String szItemId;
    private String typeId;
    private String typeIdStr;
    private String uninstallDate;

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public String getConfigXml() {
        return configXml;
    }

    public void setConfigXml(String configXml) {
        this.configXml = configXml;
    }

    public String getContractGuid() {
        return contractGuid;
    }

    public void setContractGuid(String contractGuid) {
        this.contractGuid = contractGuid;
    }

    public DevConfigDtoBean getDevConfigDto() {
        return devConfigDto;
    }

    public void setDevConfigDto(DevConfigDtoBean devConfigDto) {
        this.devConfigDto = devConfigDto;
    }

    public String getDevGuid() {
        return devGuid;
    }

    public void setDevGuid(String devGuid) {
        this.devGuid = devGuid;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getDevMac() {
        return devMac;
    }

    public void setDevMac(String devMac) {
        this.devMac = devMac;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevSn() {
        return devSn;
    }

    public void setDevSn(String devSn) {
        this.devSn = devSn;
    }

    public String getDevTypeGuid() {
        return devTypeGuid;
    }

    public void setDevTypeGuid(String devTypeGuid) {
        this.devTypeGuid = devTypeGuid;
    }

    public String getEndUseDate() {
        return endUseDate;
    }

    public void setEndUseDate(String endUseDate) {
        this.endUseDate = endUseDate;
    }

    public String getFlowState() {
        return flowState;
    }

    public void setFlowState(String flowState) {
        this.flowState = flowState;
    }

    public String getFlowStateStr() {
        return flowStateStr;
    }

    public void setFlowStateStr(String flowStateStr) {
        this.flowStateStr = flowStateStr;
    }

    public String getHlhtDcpk() {
        return hlhtDcpk;
    }

    public void setHlhtDcpk(String hlhtDcpk) {
        this.hlhtDcpk = hlhtDcpk;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public String isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getIsDeleteStr() {
        return isDeleteStr;
    }

    public void setIsDeleteStr(String isDeleteStr) {
        this.isDeleteStr = isDeleteStr;
    }

    public String isIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getIsEnableStr() {
        return isEnableStr;
    }

    public void setIsEnableStr(String isEnableStr) {
        this.isEnableStr = isEnableStr;
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getIsTest() {
        return isTest;
    }

    public void setIsTest(String isTest) {
        this.isTest = isTest;
    }

    public String getIsTestStr() {
        return isTestStr;
    }

    public void setIsTestStr(String isTestStr) {
        this.isTestStr = isTestStr;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPlatAccountId() {
        return platAccountId;
    }

    public void setPlatAccountId(String platAccountId) {
        this.platAccountId = platAccountId;
    }

    public String getPlatAccountIdStr() {
        return platAccountIdStr;
    }

    public void setPlatAccountIdStr(String platAccountIdStr) {
        this.platAccountIdStr = platAccountIdStr;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getSptId() {
        return sptId;
    }

    public void setSptId(String sptId) {
        this.sptId = sptId;
    }

    public String getStartUseDate() {
        return startUseDate;
    }

    public void setStartUseDate(String startUseDate) {
        this.startUseDate = startUseDate;
    }

    public String getStockOutDate() {
        return stockOutDate;
    }

    public void setStockOutDate(String stockOutDate) {
        this.stockOutDate = stockOutDate;
    }

    public String getSzItemId() {
        return szItemId;
    }

    public void setSzItemId(String szItemId) {
        this.szItemId = szItemId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeIdStr() {
        return typeIdStr;
    }

    public void setTypeIdStr(String typeIdStr) {
        this.typeIdStr = typeIdStr;
    }

    public String getUninstallDate() {
        return uninstallDate;
    }

    public void setUninstallDate(String uninstallDate) {
        this.uninstallDate = uninstallDate;
    }

    public static class DevConfigDtoBean {
        /**
         * account : hcjk
         * camSeqId : 65178240293_001
         * ip : 218.207.182.53
         * naming : 591000006090101
         * password : hckj831903
         * port : 35060
         * serverIp : 119.23.13.32
         * serverPort : 28888
         * type : 7
         */

        private String account;
        private String camSeqId;
        private String ip;
        private String naming;
        private String password;
        private String port;
        private String serverIp;
        private String serverPort;
        private String type;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getCamSeqId() {
            return camSeqId;
        }

        public void setCamSeqId(String camSeqId) {
            this.camSeqId = camSeqId;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getNaming() {
            return naming;
        }

        public void setNaming(String naming) {
            this.naming = naming;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getServerIp() {
            return serverIp;
        }

        public void setServerIp(String serverIp) {
            this.serverIp = serverIp;
        }

        public String getServerPort() {
            return serverPort;
        }

        public void setServerPort(String serverPort) {
            this.serverPort = serverPort;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    @Override
    public String toString() {
        return "AutoDriviceModel{" +
                "completedDate='" + completedDate + '\'' +
                ", configXml='" + configXml + '\'' +
                ", contractGuid='" + contractGuid + '\'' +
                ", devConfigDto=" + devConfigDto +
                ", devGuid='" + devGuid + '\'' +
                ", devId=" + devId +
                ", devMac='" + devMac + '\'' +
                ", devName='" + devName + '\'' +
                ", devSn='" + devSn + '\'' +
                ", devTypeGuid='" + devTypeGuid + '\'' +
                ", endUseDate='" + endUseDate + '\'' +
                ", flowState=" + flowState +
                ", flowStateStr='" + flowStateStr + '\'' +
                ", hlhtDcpk='" + hlhtDcpk + '\'' +
                ", installDate='" + installDate + '\'' +
                ", isDelete=" + isDelete +
                ", isDeleteStr='" + isDeleteStr + '\'' +
                ", isEnable=" + isEnable +
                ", isEnableStr='" + isEnableStr + '\'' +
                ", isPrivate=" + isPrivate +
                ", isTest=" + isTest +
                ", isTestStr='" + isTestStr + '\'' +
                ", memo='" + memo + '\'' +
                ", photo='" + photo + '\'' +
                ", platAccountId=" + platAccountId +
                ", platAccountIdStr='" + platAccountIdStr + '\'' +
                ", projId=" + projId +
                ", sptId=" + sptId +
                ", startUseDate='" + startUseDate + '\'' +
                ", stockOutDate='" + stockOutDate + '\'' +
                ", szItemId='" + szItemId + '\'' +
                ", typeId=" + typeId +
                ", typeIdStr='" + typeIdStr + '\'' +
                ", uninstallDate='" + uninstallDate + '\'' +
                '}';
    }
}
