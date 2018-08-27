package com.hc.android.huixin;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hc.android.huixin.base.BaseActivity;
import com.hc.android.huixin.util.PreferenceUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/22.
 */

public class ErrorActivity extends BaseActivity {
    @BindView(R.id.tvError)
    TextView tvError;
    @BindView(R.id.btn_test)
    Button btn_test;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        tvError.setText(PreferenceUtil.getCrash());
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
