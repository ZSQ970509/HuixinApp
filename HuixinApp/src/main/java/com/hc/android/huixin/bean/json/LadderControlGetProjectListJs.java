package com.hc.android.huixin.bean.json;

import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class LadderControlGetProjectListJs implements Serializable {

    /**
     * result : 1
     * msg : 获取梯控绑定列表成功
     * totalcount : 23
     * data : [{"ProjectLat":null,"ProjectLng":null,"ProjectId":40037,"ProjectName":"泉州艺通箱包有限公司厂房B工程","CamJobCode":"HCSG_17102543138","ActualDistance":-1,"InstallNum":1},{"ProjectLat":null,"ProjectLng":null,"ProjectId":39959,"ProjectName":"惠安第二中学学生宿舍楼工程","CamJobCode":"HCSG_17070741419","ActualDistance":-1,"InstallNum":1},{"ProjectLat":null,"ProjectLng":null,"ProjectId":39567,"ProjectName":"莆田文献中学学生宿舍楼改扩建项目A栋宿舍楼工程","CamJobCode":"HCSG_17101943004","ActualDistance":-1,"InstallNum":1},{"ProjectLat":null,"ProjectLng":null,"ProjectId":39305,"ProjectName":"武夷学院圣农食品学院实验实训楼及报告厅工程","CamJobCode":"HCSG_17101142820","ActualDistance":-1,"InstallNum":2},{"ProjectLat":null,"ProjectLng":null,"ProjectId":39198,"ProjectName":"体育场西侧主席台和观众看台（一期）工程","CamJobCode":"HCSG_17110841489","ActualDistance":-1,"InstallNum":1},{"ProjectLat":null,"ProjectLng":null,"ProjectId":39071,"ProjectName":"惠安县紫山镇敬老院工程","CamJobCode":"HCSG_17070741246","ActualDistance":-1,"InstallNum":1},{"ProjectLat":"25.866947","ProjectLng":"116.820401","ProjectId":39999,"ProjectName":"漳平市灵地乡卫生院综合楼工程","CamJobCode":"HCSG_17101842190","ActualDistance":12685.21,"InstallNum":1},{"ProjectLat":"27.546276","ProjectLng":"117.340695","ProjectId":42237,"ProjectName":"财富花园12#楼工程","CamJobCode":"HCSG_17102542763","ActualDistance":12693.77,"InstallNum":1},{"ProjectLat":"26.786096","ProjectLng":"117.843493","ProjectId":39084,"ProjectName":"顺昌天龙公交总站维修车间及附属用房工程","CamJobCode":"HCSG_17101943045","ActualDistance":12761.84,"InstallNum":1},{"ProjectLat":"24.484587","ProjectLng":"117.988265","ProjectId":39703,"ProjectName":"华中师大海沧附属中学三期工程","CamJobCode":"HCSG_17101741134","ActualDistance":12833.24,"InstallNum":1},{"ProjectLat":"25.50224","ProjectLng":"118.276116","ProjectId":38627,"ProjectName":"红星时代广场-红星家世界一期工程","CamJobCode":"HCSG_17091440080","ActualDistance":12836.6,"InstallNum":1},{"ProjectLat":"25.04486","ProjectLng":"118.196781","ProjectId":40556,"ProjectName":"百福豪城二期工程","CamJobCode":"HCSG_17101341616","ActualDistance":12840.2,"InstallNum":1},{"ProjectLat":"24.669408","ProjectLng":"118.437425","ProjectId":40014,"ProjectName":"南益海联新厂一期第一标段工程","CamJobCode":"HCSG_17102341976","ActualDistance":12873.14,"InstallNum":4},{"ProjectLat":"26.195742","ProjectLng":"118.890932","ProjectId":36482,"ProjectName":"闽清县梅溪新城两校一场工程","CamJobCode":"HCSG_17090542137","ActualDistance":12878.69,"InstallNum":6},{"ProjectLat":"24.972775","ProjectLng":"118.578913","ProjectId":36079,"ProjectName":"中国联通泉州通信综合楼新建工程（福建企业云计算产业园项目一期工程）","CamJobCode":"HCSG_17102042922","ActualDistance":12879.57,"InstallNum":1},{"ProjectLat":"24.824524","ProjectLng":"118.573416","ProjectId":39210,"ProjectName":"晋江自来水股份有限公司梅岭水厂宿舍楼扩建工程","CamJobCode":"HCSG_17082541410","ActualDistance":12882.72,"InstallNum":1},{"ProjectLat":"24.921446","ProjectLng":"118.657982","ProjectId":39477,"ProjectName":"CBD尚都三期工程：6#、7#楼，不包含地下室工程","CamJobCode":"HCSG_17081741204","ActualDistance":12888.63,"InstallNum":2},{"ProjectLat":"24.927203","ProjectLng":"118.674749","ProjectId":42410,"ProjectName":"泉州市第一医院城东分院配套工程－－许书典教学楼项目","CamJobCode":"HCSG_17101042469","ActualDistance":12890.13,"InstallNum":1},{"ProjectLat":"27.109289","ProjectLng":"119.337269","ProjectId":38284,"ProjectName":"武夷·龙腾世纪1#-4#楼工程","CamJobCode":"HCSG_17092540451","ActualDistance":12897.12,"InstallNum":1},{"ProjectLat":"25.125074","ProjectLng":"118.92188","ProjectId":36761,"ProjectName":"田润.御园（三期）2#、3#、5#、6#、8#、9#楼及地下室工程","CamJobCode":"HCSG_17052439952","ActualDistance":12909.42,"InstallNum":2},{"ProjectLat":"25.375151","ProjectLng":"119.085039","ProjectId":40414,"ProjectName":"莆田市公安局涵江分局业务技术用房工程","CamJobCode":"HCSG_17101041925","ActualDistance":12919,"InstallNum":1},{"ProjectLat":"25.167921","ProjectLng":"119.115099","ProjectId":38868,"ProjectName":"莆田市湄洲湾北岸经济开发区医院门诊大楼建设工程","CamJobCode":"HCSG_17072540563","ActualDistance":12927.28,"InstallNum":1},{"ProjectLat":"27.303345","ProjectLng":"120.264224","ProjectId":38066,"ProjectName":"福鼎市实验小学百胜校区（一期）工程","CamJobCode":"HCSG_17073140713","ActualDistance":12980.42,"InstallNum":3}]
     */

    private String result;
    private String msg;
    private String totalcount;
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

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ProjectLat : null
         * ProjectLng : null
         * ProjectId : 40037
         * ProjectName : 泉州艺通箱包有限公司厂房B工程
         * CamJobCode : HCSG_17102543138
         * ActualDistance : -1.0
         * InstallNum : 1
         */

        private String ProjectLat;
        private String ProjectLng;
        private String ProjectId;
        private String ProjectName;
        private String CamJobCode;
        private String ActualDistance;
        private String InstallNum;

        public String getProjectLat() {
            return ProjectLat;
        }

        public void setProjectLat(String ProjectLat) {
            this.ProjectLat = ProjectLat;
        }

        public String getProjectLng() {
            return ProjectLng;
        }

        public void setProjectLng(String ProjectLng) {
            this.ProjectLng = ProjectLng;
        }

        public String getProjectId() {
            return ProjectId;
        }

        public void setProjectId(String ProjectId) {
            this.ProjectId = ProjectId;
        }

        public String getProjectName() {
            return ProjectName;
        }

        public void setProjectName(String ProjectName) {
            this.ProjectName = ProjectName;
        }

        public String getCamJobCode() {
            return CamJobCode;
        }

        public void setCamJobCode(String CamJobCode) {
            this.CamJobCode = CamJobCode;
        }

        public String getActualDistance() {
            return ActualDistance;
        }

        public void setActualDistance(String ActualDistance) {
            this.ActualDistance = ActualDistance;
        }

        public String getInstallNum() {
            return InstallNum;
        }

        public void setInstallNum(String InstallNum) {
            this.InstallNum = InstallNum;
        }
    }
}