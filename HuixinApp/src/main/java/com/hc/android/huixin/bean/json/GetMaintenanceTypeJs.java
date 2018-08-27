package com.hc.android.huixin.bean.json;

import java.util.List;

/**
 *
 */

public class GetMaintenanceTypeJs {

    /**
     * Rows : [{"id":181,"text":"摄像机5米范围内无稳定电源"},{"id":179,"text":"没有提供附着物"},{"id":188,"text":"运营商平台数据未调通"},{"id":180,"text":"光纤未开通"},{"id":193,"text":"其他"}]
     * result : 1
     * msg : 查询成功!
     * Total : 25
     */

    private String result;
    private String msg;
    private String Total;
    private List<RowsBean> Rows;

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

    public String getTotal() {
        return Total;
    }

    public void setTotal(String Total) {
        this.Total = Total;
    }

    public List<RowsBean> getRows() {
        return Rows;
    }

    public void setRows(List<RowsBean> Rows) {
        this.Rows = Rows;
    }

    public static class RowsBean {


        /**
         * id : 181
         * text : 摄像机5米范围内无稳定电源
         */

        private String id;
        private String text;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
