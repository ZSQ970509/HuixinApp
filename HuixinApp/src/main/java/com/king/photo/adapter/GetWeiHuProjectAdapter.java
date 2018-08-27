package com.king.photo.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.hc.android.huixin.R;
import com.king.photo.model.WeiHuProjectModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/18.
 */

public class GetWeiHuProjectAdapter extends BaseItemDraggableAdapter<WeiHuProjectModel.DataBean, BaseViewHolder> {
    public GetWeiHuProjectAdapter(int layoutResId, ArrayList<WeiHuProjectModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeiHuProjectModel.DataBean item) {
//        DecimalFormat df = new DecimalFormat("######0");
        TextView tv = helper.getView(R.id.tvWeiHuProjectName);
        tv.setText(item.getProjectName());
        tv.setSelected(true);
        helper.setText(R.id.tvWeiHuProjectName, item.getProjectName())
                .setText(R.id.tvWeiHuError, item.getCamfailReslt())
                .setText(R.id.tvWeiHuTime, item.getCreateTime())
                .setText(R.id.tvWeiHuLong, item.getActualDistance() + "km")
                .setText(R.id.tvWeiErrorNum, item.getFailureNum())
                .addOnClickListener(R.id.tvWeiHuLong);
        if (item.getActualDistance().equals("-1.0")) {
            helper.setTextColor(R.id.tvWeiHuLong, Color.rgb(255, 0, 0));
        } else {
            helper.setTextColor(R.id.tvWeiHuLong, Color.rgb(74, 137, 220));
        }
    }
}
