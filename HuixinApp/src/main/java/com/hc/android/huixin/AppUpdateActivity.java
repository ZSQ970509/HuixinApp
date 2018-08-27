package com.hc.android.huixin;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.util.DeviceUtil;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class AppUpdateActivity extends Activity {

    private Handler mHandler = new Handler();
    private Button mUpdate;
    private Button mManualUpdate;
    private TextView mNewVersion;
    private String mDownloadUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_update);
        initView();
        startUpdate();
    }

    private void initView() {
        String title = getIntent().getStringExtra("title");
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(title);

        findViewById(R.id.update_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUpdate = (Button) findViewById(R.id.update_btn);
        mUpdate.setVisibility(View.GONE);
        mUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload(mDownloadUrl);
            }
        });
        TextView currentVersion = (TextView) findViewById(R.id.update_current_version);
        currentVersion.setText("当前版本：" + DeviceUtil.getVersionName(this));
        mNewVersion = (TextView) findViewById(R.id.update_new_version);

        mManualUpdate = (Button) findViewById(R.id.update_manual_btn);
        mManualUpdate.setVisibility(View.GONE);
        mManualUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload(mDownloadUrl);
            }
        });

    }

    private void startUpdate() {
        String result = PreferenceUtil.getUpdateData();
        try {
            JSONArray dataArray = new JSONArray(result);
            JSONObject data = dataArray.getJSONObject(0);
            int versionCode = data.getInt("UpdateVersionCode");
            if (versionCode > DeviceUtil.getVersionCode(AppUpdateActivity.this)) {
                final String versionName = data.getString("UpdateVersionName");
                mDownloadUrl = data.getString("UpdateDownLoadUrl");
                mHandler.post(new Runnable() {
                    public void run() {
                        mNewVersion.setText("最新版本：" + versionName);
                        mUpdate.setVisibility(View.VISIBLE);
                        mManualUpdate.setVisibility(View.GONE);
                    }
                });
            } else {
                mDownloadUrl = data.getString("UpdateDownLoadUrl");
                mHandler.post(new Runnable() {
                    public void run() {
                        mNewVersion.setText("当前版本为最新版本！");
                        mUpdate.setVisibility(View.GONE);
                        mManualUpdate.setVisibility(View.VISIBLE);
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void startDownload(final String url) {
        if (TextUtils.isEmpty(url)) {

            return;
        }
        final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在下载...");
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/huixin.apk";
                HttpUtil.download(url, savePath, new INetCallback() {
                    @Override
                    public void onCallback(boolean value, String result) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                dialog.cancel();
                            }
                        });
                        if (value) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(new File(savePath)),
                                    "application/vnd.android.package-archive");
                            startActivity(intent);
                        } else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastHelp.showToast(AppUpdateActivity.this, "下载失败！");
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }

}
