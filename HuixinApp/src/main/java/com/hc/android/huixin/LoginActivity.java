package com.hc.android.huixin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DeviceUtil;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.activity.BaseActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class LoginActivity extends BaseActivity implements OnClickListener {

    private EditText mName, mPwd;
    private Handler mUiHandler = new Handler();
    private boolean mIsLogining = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkAutoLogin();
        if (PreferenceUtil.getIsBattery() && PreferenceUtil.getIsAutoStart()) {

        } else {
            Intent intent = new Intent(LoginActivity.this, PermissionsSettingsActivity.class);
            startActivity(intent);
        }
        initView(this);
    }

    /**
     * 检查是否自动登录
     *
     * @return
     */
    private boolean checkAutoLogin() {
        if (PreferenceUtil.autoLogin()) {
            String name = PreferenceUtil.getName();
            String pwd = PreferenceUtil.getPassword();
            login(this, name, pwd);
            return true;
        }
        return false;
    }

    private void initView(final Context context) {
        mName = (EditText) findViewById(R.id.login_name);
        mPwd = (EditText) findViewById(R.id.login_pwd);
        findViewById(R.id.main_login).setOnClickListener(this);
        final CheckBox savePassword = (CheckBox) findViewById(R.id.login_save_password);
        savePassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    savePassword.setBackgroundResource(R.drawable.login_auto_check_bg);
                } else {
                    savePassword.setBackgroundResource(R.drawable.login_auto_uncheck_bg);
                }
                PreferenceUtil.setIsSavePassword(isChecked);
            }
        });
        final CheckBox autoLogin = (CheckBox) findViewById(R.id.login_auto_login);
        autoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    savePassword.setChecked(true);
                    savePassword.setEnabled(false);
                    autoLogin.setBackgroundResource(R.drawable.login_auto_check_bg);
                } else {
                    savePassword.setEnabled(true);
                    autoLogin.setBackgroundResource(R.drawable.login_auto_uncheck_bg);
                }
                PreferenceUtil.setAutoLogin(isChecked);
            }
        });
        if (PreferenceUtil.isSavePassword()) {
            mName.setText(PreferenceUtil.getName());
            mPwd.setText(PreferenceUtil.getPassword());
            savePassword.setChecked(true);
        }
        if (PreferenceUtil.autoLogin()) {
            autoLogin.setChecked(true);
        }
//        if (!TextUtils.isEmpty(PreferenceUtil.getCrash(this)))
//            startActivity(new Intent(LoginActivity.this, ErrorActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_login:
                String name = mName.getText().toString();
                String pwd = mPwd.getText().toString();
                login(this, name, pwd);
                break;
            default:
                break;
        }
    }

    /**
     * 登录方法
     *
     * @return
     */
    private void login(final Context context, final String name, final String pwd) {
        if (mIsLogining) {
            return;
        }
        if (TextUtils.isEmpty(name)) {
            ToastHelp.showToast(this, "请输入手机号码！");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastHelp.showToast(this, "请输入密码！");
            return;
        }

        if (!HttpUtil.networkIsAvailable(this)) {
            ToastHelp.showToast(this, "手机没有网络连接！");
            return;
        }

        PreferenceUtil.saveName(name);
        mIsLogining = true;
        final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在登陆...");
        dialog.show();
        String imei = DeviceUtil.getImei(this);
        NetworkApi.login(name, pwd, imei, new INetCallback() {
            @Override
            public void onCallback(boolean value, String result) {
                dialog.cancel();
                if (value) {
                    // if (PreferenceUtil.isSavePassword())
                    // {//为了后面接口可以取到密码值
                    PreferenceUtil.savePassword(pwd);
                    // }
                    try {
                        JSONArray array = new JSONArray(result);
                        PreferenceUtil.saveUserName(array.getJSONObject(0).getString("UserName"));
                        PreferenceUtil.saveUserId(array.getJSONObject(0).getString("UserId"));
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    if (PreferenceUtil.autoLogin()) {
                        PreferenceUtil.setAutoLogin(false);
                    }
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastHelp.showToast(LoginActivity.this, "您输入的用户名或者密码错误！");
                        }
                    });
                }
                mIsLogining = false;
            }
        });
    }

}
