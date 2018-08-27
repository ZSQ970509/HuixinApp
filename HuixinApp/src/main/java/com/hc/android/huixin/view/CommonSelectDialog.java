package com.hc.android.huixin.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonAdapter;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.CameraItem;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonSelectDialog<T> extends Dialog implements ICommonSelectCall<T> {
    private ListView mListView; // 列表
    private Button mConfirmBtn;
    private List<T> mData = new ArrayList<>();
    private CommonAdapter<T> mAdapt;
    private T mSelectItem;

    public CommonSelectDialog(Context context, List<T> data) {
        super(context, R.style.Dialog);
        initViews(context, data);
    }

    private void initViews(Context context, List<T> msgs) {
        setContentView(R.layout.dialog_choice);
        mData = msgs;
        if(mData!=null&&mData.size()>0)
            mSelectItem = mData.get(0);
        mListView = (ListView) findViewById(R.id.dialog_lv);
        mConfirmBtn = (Button) findViewById(R.id.dialog_confirm_bt);
        // 设置对话框位置大小
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        dialogWindow.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setAttributes(lp);// 此处暂未设置偏移量
        // setCanceledOnTouchOutside(false);
        // setCancelable(false);
        mAdapt = new CommonAdapter<T>(context, R.layout.item_dialog_choice, mData) {
            private int selectPosition = 0;

            @Override
            public void onUpdate(BaseAdapterHelper helper, T item, final int position) {
                RelativeLayout rl = helper.getView(R.id.test_rl);
                rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPosition = position;
                        mSelectItem = mData.get(selectPosition);
                        mAdapt.notifyDataSetChanged();
                    }
                });
                RadioButton radio = helper.getView(R.id.test_rb);
                radio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPosition = position;
                        mSelectItem = mData.get(selectPosition);
                        mAdapt.notifyDataSetChanged();
                    }
                });
                if (position == selectPosition) {
                    radio.setChecked(true);
                } else {
                    radio.setChecked(false);
                }
                helper.setText(R.id.test, getItemName(item));
            }
        };
        mListView.setAdapter(mAdapt);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                try {
                    if (mAdapt.getData() == null || mAdapt.getData().size() < 0) {//没有数据
                        return;
                    } else {
                        onCall(mSelectItem);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public abstract String getItemName(T item);

    /**
     * 设置确定按钮字符串
     */
    public CommonSelectDialog setConfirmText(String text) {
        if (!TextUtils.isEmpty(text))
            mConfirmBtn.setText(text);
        return this;
    }
}