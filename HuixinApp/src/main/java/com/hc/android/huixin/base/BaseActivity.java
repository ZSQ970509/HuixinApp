package com.hc.android.huixin.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hc.android.huixin.R;
import com.hc.android.huixin.util.DialogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */

public abstract class BaseActivity extends Activity {

    protected TextView mTvTitle;
    protected ImageView mIvLeft;
    protected TextView mTvRight;
    private Unbinder unbinder;

    /**
     * 获取布局id
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化数据
     *
     * @param intent
     */
    protected void initData(Intent intent) {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initView();
        initData(getIntent());
    }

    /**
     * 设置标题栏
     */
    protected void setToolBar(String title) {
        mTvTitle = (TextView) findViewById(R.id.actionbar_mid_tv);
        mTvTitle.setText(title);
        mIvLeft = (ImageView) findViewById(R.id.actionbar_left_iv);
        mIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mTvRight = (TextView) findViewById(R.id.actionbar_right_tv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadDialog();
        unbinder.unbind();
    }

    ProgressDialog loadDialog;

    public BaseActivity getActivity() {
        return this;
    }

    /**
     * 显示加载对话框
     *
     * @param msg 内容
     */
    public void showLoadDialog(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadDialog == null) {
                    loadDialog = new ProgressDialog(getActivity());
                    loadDialog.setCanceledOnTouchOutside(false);
                    loadDialog.setCancelable(false);
                }
                loadDialog.setMessage(msg);
                loadDialog.show();
            }
        });
    }

    public void dismissLoadDialog() {
        if (loadDialog != null && loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
    }

    /**
     * 显示吐司()
     *
     * @param msg 内容
     */
    public void showToast(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getResource(@StringRes int resId) {
        return getActivity().getString(resId);
    }
}

