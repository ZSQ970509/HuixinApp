package com.king.photo.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hc.android.huixin.R;
import com.king.photo.model.AttendanceProjectModel;
import com.king.photo.model.AttendanceProjectModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/18.
 */

public class AttendanceProjectAdapter extends BaseItemDraggableAdapter<AttendanceProjectModel.DataBean, BaseViewHolder> {
    public AttendanceProjectAdapter(int layoutResId, ArrayList<AttendanceProjectModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AttendanceProjectModel.DataBean item) {
//        DecimalFormat df = new DecimalFormat("######0");
        TextView tv = helper.getView(R.id.tvAttendanceProjectName);
        tv.setText(item.getProjectName()+"");
        tv.setSelected(true);
        helper.setText(R.id.tvAttendanceDistance, item.getActualDistance() + "km");
        if (item.getActualDistance().equals("-1.0")) {
            helper.setTextColor(R.id.tvAttendanceDistance, Color.rgb(255, 0, 0));
        } else {
            helper.setTextColor(R.id.tvAttendanceDistance, Color.rgb(74, 137, 220));
        }
    }
}
