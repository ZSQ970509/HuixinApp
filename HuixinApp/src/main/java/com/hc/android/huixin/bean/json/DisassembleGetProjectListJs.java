package com.hc.android.huixin.bean.json;

import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class DisassembleGetProjectListJs implements Serializable {


    /**
     * result : 1
     * msg : 获取拆机列表成功
     * totalcount : 15
     * data : [{"ProjectId":31447,"ProjectName":"福州北站改扩建工程安置地Ｇ地块（安置房）","RemoveJobCode":"HCCJ_1709072513","ProjectLat":null,"ProjectLng":null,"ActualDistance":-1,"RemoveNum":0},{"ProjectId":21902,"ProjectName":"湄洲湾北岸荔港大道东吴段一期工程与莆田市东吴中大道二期工程捆绑BT项目","RemoveJobCode":"HCCJ_1611211738","ProjectLat":null,"ProjectLng":null,"ActualDistance":-1,"RemoveNum":0},{"ProjectId":12356,"ProjectName":"卧龙小区二期工程B标段","RemoveJobCode":"HCCJ_1710202221","ProjectLat":"25.08021","ProjectLng":"117.040915","ActualDistance":12725.55,"RemoveNum":0},{"ProjectId":11200,"ProjectName":"挺秀花园（主体工程）","RemoveJobCode":"HCCJ_1711012624","ProjectLat":"25.088587","ProjectLng":"117.044365","ActualDistance":12725.7,"RemoveNum":0},{"ProjectId":11243,"ProjectName":"龙岩冠虹老年颐养院一期B地块工程（4#、8#、9#、12#楼公寓）","RemoveJobCode":"FJLYA1206632C0000-00078","ProjectLat":"25.090881","ProjectLng":"117.047813","ActualDistance":12725.98,"RemoveNum":0},{"ProjectId":11078,"ProjectName":"2012JP03地块A1子地块工程","RemoveJobCode":"XMSG-XM163","ProjectLat":"24.610749","ProjectLng":"118.060077","ActualDistance":12837.31,"RemoveNum":0},{"ProjectId":11154,"ProjectName":"禹洲·溪堤尚城3号地块地下室及上部工程","RemoveJobCode":"HCCJ_1604221134","ProjectLat":"24.751194","ProjectLng":"118.170999","ActualDistance":12844.85,"RemoveNum":0},{"ProjectId":10370,"ProjectName":"后埔社区发展中心项目一期（南楼）基坑支护工程","RemoveJobCode":"HCCJ_1603011025","ProjectLat":"24.512253","ProjectLng":"118.153944","ActualDistance":12848.96,"RemoveNum":0},{"ProjectId":12217,"ProjectName":"养正中学新校区图书馆及行政科研楼、科技楼、艺术楼、体育馆、看台、高中生宿舍楼1~6#、第一食堂工程","RemoveJobCode":"HCCJ_1607241400","ProjectLat":"24.735999","ProjectLng":"118.473174","ActualDistance":12875.03,"RemoveNum":0},{"ProjectId":19854,"ProjectName":"南台新苑（上渡旧屋改造工程）兰花园Ⅰ标段1#-3#楼","RemoveJobCode":"HCCJ_1708262497","ProjectLat":"26.037923","ProjectLng":"119.299747","ActualDistance":12922.55,"RemoveNum":0},{"ProjectId":10940,"ProjectName":"东二环泰禾城市广场（三期）22#及其地下室工程","RemoveJobCode":"FJAY2012-214-0000159","ProjectLat":"26.095586","ProjectLng":"119.340983","ActualDistance":12925.02,"RemoveNum":0},{"ProjectId":10450,"ProjectName":"晋安新城鹤林片区横屿安置房(一期C区1~11#楼、地下室及幼儿园）工程","RemoveJobCode":"T5R5686","ProjectLat":"26.104471","ProjectLng":"119.365106","ActualDistance":12927.12,"RemoveNum":0},{"ProjectId":10249,"ProjectName":"福建海峡银行新办公大楼桩基工程","RemoveJobCode":"hc-cj-fz20151030109","ProjectLat":"26.032358","ProjectLng":"119.360305","ActualDistance":12928.58,"RemoveNum":0},{"ProjectId":10169,"ProjectName":"福晟.钱隆广场（上部工程）","RemoveJobCode":"566778","ProjectLat":"26.076721","ProjectLng":"119.376102","ActualDistance":12928.93,"RemoveNum":0},{"ProjectId":30285,"ProjectName":"香海新城A区11#-13#楼、15#-18#楼、31#-33#楼、35#-39#楼、50#-53#楼、55#楼、56#楼及相应连体地下室工程","RemoveJobCode":"HCCJ_1706032274","ProjectLat":"26.085582","ProjectLng":"119.570409","ActualDistance":12947.55,"RemoveNum":0}]
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

    public static class DataBean implements Serializable{
        /**
         * ProjectId : 31447
         * ProjectName : 福州北站改扩建工程安置地Ｇ地块（安置房）
         * RemoveJobCode : HCCJ_1709072513
         * ProjectLat : null
         * ProjectLng : null
         * ActualDistance : -1.0
         * RemoveNum : 0
         */

        private String ProjectId;
        private String ProjectName;
        private String RemoveJobCode;
        private String ProjectLat;
        private String ProjectLng;
        private String ActualDistance;
        private String RemoveNum;

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

        public String getRemoveJobCode() {
            return RemoveJobCode;
        }

        public void setRemoveJobCode(String RemoveJobCode) {
            this.RemoveJobCode = RemoveJobCode;
        }

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

        public String getActualDistance() {
            return ActualDistance;
        }

        public void setActualDistance(String ActualDistance) {
            this.ActualDistance = ActualDistance;
        }

        public String getRemoveNum() {
            return RemoveNum;
        }

        public void setRemoveNum(String RemoveNum) {
            this.RemoveNum = RemoveNum;
        }
    }
}