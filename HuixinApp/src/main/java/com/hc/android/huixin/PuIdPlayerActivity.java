package com.hc.android.huixin;

import org.json.JSONException;
import org.json.JSONObject;

import com.ffcs.surfingscene.function.SurfingScenePlayer;
import com.ffcs.surfingscene.function.onPlayListener;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.PreferenceUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class PuIdPlayerActivity extends Activity implements OnClickListener {

	private SurfingScenePlayer splay;
	private String userId;
	private TextView prossTV,txtSpeedNum;
	private View layoutPross;
	GLSurfaceView glv;
	String speedNum = "1";
	SeekBar speedSeekBar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
			setContentView(R.layout.activity_puid_player);
			
			initView();

			initVideo();

	}

	private void initView() {
		findViewById(R.id.btn_back).setOnClickListener(this);
		
		
		findViewById(R.id.btn_up).setOnClickListener(this);
		findViewById(R.id.btn_down).setOnClickListener(this);
		findViewById(R.id.btn_left).setOnClickListener(this);
		findViewById(R.id.btn_right).setOnClickListener(this);

		findViewById(R.id.btn_big).setOnClickListener(this);

		findViewById(R.id.btn_small).setOnClickListener(this);

		prossTV = (TextView) findViewById(R.id.txt_pross);
		layoutPross = findViewById(R.id.layout_pross);
		txtSpeedNum = (TextView) findViewById(R.id.textview_speed_num);
		speedSeekBar  = (SeekBar) findViewById(R.id.seekBar_speed);
		speedSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				speedNum = progress+1+"";
				txtSpeedNum.setText(speedNum);
			}
		});
	}
	
	
	/**
	 * 加载播放控件，获取视频流进行播放
	 */
	private void initVideo() {

		splay = new SurfingScenePlayer(this);
		glv = (GLSurfaceView) this.findViewById(R.id.GLsurface_view);
		glv.setVisibility(View.VISIBLE);
		splay.init(glv);
		splay.getProgressValue();

		splay.setPlayListener(new onPlayListener() {

			@Override
			public void setOnPlaysuccess() {
				prossTV.setText("视频缓冲进度：100%");
				layoutPross.setVisibility(View.GONE);
			}

			@Override
			public void onPlayFail(int arg0, final String error) {
				layoutPross.setVisibility(View.GONE);
				// DialogUtil.showDialog(PuIdPlayerActivity.this, error);
				
				showErrorDialog(PuIdPlayerActivity.this,"播放失败："+error);
				//上传错误信息
				sendErrorData(error);
				
			}

		
			@Override
			public void onBufferProssgress(int bufferValue) {
				
				if (bufferValue >= 99) {
					prossTV.setText("视频缓冲进度：100%");
					layoutPross.setVisibility(View.GONE);
				} else {
					prossTV.setText("视频缓冲进度：" + bufferValue + "%");
				}
			}
		});

		String puName = getIntent().getStringExtra("puName");
		TextView title = (TextView) findViewById(R.id.txt_title);
		title.setText(puName);

		final String puidAndChannelno = getIntent().getStringExtra("puId");
		userId = getIntent().getStringExtra("userId");
		final int ptzlag = Integer.parseInt(getIntent().getStringExtra("ptzlag"));
		int areaCodeNum = getIntent().getIntExtra("areaCode", 0);
		final String areaCode = areaCodeNum + "";
		// 视频播放
		// splay.changetofullScreen();
		// streamType 视频流表示 ,2:表清,3:高清
		splay.playerVideoByPuId(puidAndChannelno, 2, areaCode, 2, 1, userId, ptzlag);
	}

	public static void showErrorDialog(final Context context,String error) {
		new AlertDialog.Builder(context).setTitle("播放提示").setMessage(error+"")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						((PuIdPlayerActivity) context).finish();

					}
				}).setCancelable(false).create().show();
	}

	public void sendErrorData(final String error) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String puName = getIntent().getStringExtra("puName");
				final String puidAndChannelno = getIntent().getStringExtra("puId");
				userId = getIntent().getStringExtra("userId");
				final int ptzlag = Integer.parseInt(getIntent().getStringExtra("ptzlag"));
				int areaCodeNum = getIntent().getIntExtra("areaCode", 0);
				final String areaCode = areaCodeNum + "";
				String camId=getIntent().getStringExtra("camId");
				
				try {
				JSONObject msgObj = new JSONObject();
				msgObj.put("memberid", PreferenceUtil.getName());
				msgObj.put("phoneMANUFACTURER",  android.os.Build.MANUFACTURER);
				msgObj.put("phoneMODEL",  android.os.Build.MODEL);
				msgObj.put("error", error);
				msgObj.put("puName", puName);
				msgObj.put("puidAndChannelno",  puidAndChannelno);
				msgObj.put("userId",  userId);
				msgObj.put("ptzlag",  ptzlag+"");
				msgObj.put("areaCode",  areaCode);
				msgObj.put("camId",  camId);

				NetworkApi.sendCrashError(PuIdPlayerActivity.this,"1702","Api视频播放","1",msgObj.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Log.d("TAG", e.toString());
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_up:
			splay.control(userId, "1", speedNum);//param1 为转动速度
			break;
		case R.id.btn_down:
			splay.control(userId, "2", speedNum);
			break;
		case R.id.btn_left:
			splay.control(userId, "3", speedNum);
			break;
		case R.id.btn_right:
			splay.control(userId, "4", speedNum);
			break;
		case R.id.btn_big:
			splay.control(userId, "7", "1");
			break;
		case R.id.btn_small:
			splay.control(userId, "8", "1");
			break;
		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		splay.playEnd();
		System.gc();
		super.onDestroy();
	}

}
