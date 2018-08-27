package com.hc.android.huixin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
import com.igexin.sdk.PushManager;
import com.king.photo.db.AttendancePathDao;
import com.king.photo.db.AttendancePathlmpl;
import com.king.photo.model.UserModel;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.widget.Toast;

public class WelcomeActivity extends Activity {

	private static final long DELAY_MILLIS = 500;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AttendancePathDao attendancePathDao = new AttendancePathlmpl();
		setContentView(R.layout.activity_welcome);
		if(attendancePathDao.loadAppDataPhysiological().size() != 1){
			attendancePathDao.clearAppData();
			attendancePathDao.addAppDataPhysiological(new UserModel("","false","false","","false","false","false","","","false","","","","","","","35","","",""));
			int size = attendancePathDao.loadAppDataPhysiological().size();
			Log.e("111",size+"");
		}
		startUpdate();
		initGeTuiPush();
	}

	private void initGeTuiPush() {
		// TODO Auto-generated method stub
		PushManager.getInstance().initialize(this.getApplicationContext());
	}

	private boolean timeout = true;

	private void autoJumpActivity() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
				finish();
			}
		}, DELAY_MILLIS);
	}

	private void checkTimeOut() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (timeout) {
					autoJumpActivity();
				}
			}
		}, 5000);
	}

	private void startUpdate() {
		if (!HttpUtil.networkIsAvailable(this)) {
			autoJumpActivity();
		}
		NetworkApi.checkUpdate(new INetCallback() {
			@Override
			public void onCallback(boolean value, String result) {
				timeout = false;
				if (value) {
					PreferenceUtil.saveUpdateData( result);
					try {
						JSONArray dataArray = new JSONArray(result);
						JSONObject data = dataArray.getJSONObject(0);
						int versionCode = data.getInt("UpdateVersionCode");
						if (versionCode > DeviceUtil.getVersionCode(WelcomeActivity.this)) {
							String versionName = data.getString("UpdateVersionName");
							String updateMsg = data.getString("UpdateLogMsg");
							String downloadUrl = data.getString("UpdateDownLoadUrl");
							String forceupdate = data.getString("UpdateForceUpdate");
							if (forceupdate.isEmpty()) {
								showUpdateDialog("最新版本：" + versionName, updateMsg, downloadUrl);

							} else {
								showForceUpdateDialog("最新版本：" + versionName, updateMsg, downloadUrl);
							}

						} else {
							autoJumpActivity();
						}
					} catch (JSONException e) {
						e.printStackTrace();
						autoJumpActivity();
					}
				} else {
					autoJumpActivity();
				}
			}
		});
		checkTimeOut();
	}

	private void showForceUpdateDialog(final String title, final String msg, final String url) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				new AlertDialog.Builder(WelcomeActivity.this).setTitle(title).setMessage(msg)
						.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								startDownload(url);
								dialog.dismiss();
							}
						}).setNegativeButton("退出", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								dialog.cancel();
								System.exit(0);
							}
						}).setOnCancelListener(new OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								System.exit(0);
							}
						}).create().show();
			}
		});
	}

	private void showUpdateDialog(final String title, final String msg, final String url) {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				new AlertDialog.Builder(WelcomeActivity.this).setTitle(title).setMessage(msg)
						.setPositiveButton("更新", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								startDownload(url);
								dialog.dismiss();
							}
						}).setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								dialog.cancel();
								autoJumpActivity();
							}
						}).setOnCancelListener(new OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								autoJumpActivity();
							}
						}).create().show();
			}
		});
	}

	private void startDownload(final String url) {
		final ProgressDialog pd;    //进度条对话框
		pd = new  ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setProgressDrawable(getResources().getDrawable(R.drawable.progessbar));
		pd.setMessage("正在下载更新");
		pd.setCanceledOnTouchOutside(false);
		pd.setProgressNumberFormat("");
		pd.show();

		//启动子线程下载任务
		new Thread(){
			@Override
			public void run() {
				try {
					Looper.prepare();
					File file = getFileFromServer(url, pd);
					sleep(3000);
					installApk(file);
					pd.dismiss(); //结束掉进度条对话框
				} catch (Exception e) {
					//下载apk失败
					Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}}.start();
		/*final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在下载...");
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
									ToastHelp.showToast(WelcomeActivity.this, "下载失败！");
								}
							});
						}
					}
				});
			}
		}).start();*/
	}
	public static File getFileFromServer(String uri, ProgressDialog pd) throws Exception{
		//如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			URL url = new URL(uri);
			final HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			//获取到文件的大小
			pd.setMax(conn.getContentLength());
			pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					conn.disconnect();
				}
			});
			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory(), "huixin.apk");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len ;
			int total=0;
			while((len =bis.read(buffer))!=-1){
				fos.write(buffer, 0, len);
				total+= len;
				//获取当前下载量
				pd.setProgress(total);
				//pd.setProgressNumberFormat(String.format("%.2fM/%.2fM", total/1024/1024, conn.getContentLength()/1024/1024));
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		}
		else{
			return null;
		}
	}
	protected void installApk(File file) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		/*if (Build.VERSION.SDK_INT >= 24) { //适配安卓7.0
			i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri apkFileUri = FileProvider.getUriForFile(this.getApplicationContext(),
					this.getPackageName(), file);
			i.setDataAndType(apkFileUri, "application/vnd.android.package-archive");
		} else {*/
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.setDataAndType(Uri.parse("file://" + file.toString()),
					"application/vnd.android.package-archive");// File.toString()会返回路径信息
		//}
        /*Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        */
		startActivity(i);
	}
}
