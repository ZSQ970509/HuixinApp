package com.king.photo.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.FaceAddModel;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.util.Bimp;
import com.king.photo.util.FileUtils;
import com.king.photo.util.ImageItem;
import com.onesafe.util.AssetUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class TowerCraneAddperson extends Activity {
	public static Bitmap bimap;
	private GridView noScrollgridview;
	private View parentView;
	private GridAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		bimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
		parentView = getLayoutInflater().inflate(R.layout.activity_towercrane_addperson, null);
		setContentView(parentView);

		Init();
	}

	private void Init() {
		// TODO Auto-generated method stub
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		findViewById(R.id.activity_selectimg_send).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!HttpUtil.networkIsAvailable(TowerCraneAddperson.this)) {
					ToastHelp.showCurrentToast(TowerCraneAddperson.this, "手机没有网络连接！");
					return;
				}

				// countFace();

				uploadImage();
			}
		});

		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					// pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
					photo();
				} else {
					Intent intent = new Intent(TowerCraneAddperson.this, GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

	}

	String token;

	protected void countFace() {
		// TODO Auto-generated method stub

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url = "http://wap-app1.153.cn:48004/ivu_authorization/getTokenStr";
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("appkey", "0585449e"));
				nvps.add(new BasicNameValuePair("secret", "eb1d2042bf794144")); // 封装表
				String result = HttpUtil.postToUrl(url, nvps);
			
				try {
					if ("1".equals(new JSONObject(result).getString("resultCode"))) {
						token = new JSONObject(result).getString("token");
						
					}
					String url2 = "http://wap-app1.153.cn:48004/ivu_tembin/tembin/tembinDoRegister";
					List<NameValuePair> nvps2 = new ArrayList<NameValuePair>();
					nvps2.add(new BasicNameValuePair("token", token));
					// nvps2.add(new BasicNameValuePair("photo",
					// ImageUtil.convertIconToString(Bimp.tempSelectBitmap.get(0).getBitmap())));
					// // 封装表
					String result2 = HttpUtil.postToUrl(url2, nvps2);
		
				} catch (JSONException e) {
					// TODO Auto-generated catch block
				
					e.printStackTrace();
				}

			}
		}).start();

	}

	private static final int TAKE_PICTURE = 0x000002;
	public static final String PICTURE_FILE = "tempAddface.jpg";

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PICTURE_FILE));
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

		openCameraIntent.putExtra("camerasensortype", 1); // 调用前置摄像头
		openCameraIntent.putExtra("autofocus", true); // 自动对焦
		openCameraIntent.putExtra("fullScreen", false); // 全屏
		openCameraIntent.putExtra("showActionIcons", false);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			// if (Bimp.tempSelectBitmap.size() == 9) {
			// return 9;
			// }
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size()) {
				holder.image
						.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
				// if (position == 9) {
				// holder.image.setVisibility(View.GONE);
				// }
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	private Handler mHandler = new Handler();
	ProgressDialog dialog = null;
	int flag = 0;

	private void uploadImage() {

		dialog = DialogUtil.createProgressDialog(this, "正在录入人脸...");
		dialog.show();
		flag = 0;

		if (Bimp.tempSelectBitmap.size() == 0) {
			dialog.cancel();
			ToastHelp.showCurrentToast(TowerCraneAddperson.this, "请拍照录入人脸");
			return;
		}
		new SendAddFacetoTowerCraneAsy().execute(PreferenceUtil.getName(),
				ImageUtil.convertIconToString(Bimp.tempSelectBitmap.get(flag).getBitmap()));

	}

	class SendAddFacetoTowerCraneAsy extends AsyncTask<String, String, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			return NetworkApi.InsertBaseForTowerCrane(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (result) {// 8 9
				flag++;
				if (flag < Bimp.tempSelectBitmap.size()) {
					new SendAddFacetoTowerCraneAsy().execute(PreferenceUtil.getName(),
							ImageUtil.convertIconToString(Bimp.tempSelectBitmap.get(flag).getBitmap()));
				} else {
					ToastHelp.showCurrentToast(TowerCraneAddperson.this, "录入成功");
					dialog.cancel();
				}
			} else {
				ToastHelp.showCurrentToast(TowerCraneAddperson.this, "第" + flag + 1 + "张识别失败");
				dialog.cancel();
			}

		}

	}

	@Override
	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		Bimp.tempSelectBitmap.clear();
		Bimp.max = 0;
		AlbumActivity.bitmap = null;
		AlbumActivity.contentList = null;
		ShowAllPhoto.dataList.clear();
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			// if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK)
			// {
			if (resultCode == RESULT_OK && requestCode == TAKE_PICTURE) {

				// String fileName = String.valueOf(System.currentTimeMillis());
				// Bitmap bm = (Bitmap) data.getExtras().get("data");
				//
				// FileUtils.saveBitmap(bm, fileName);
				//
				// ImageItem takePhoto = new ImageItem();
				// takePhoto.setBitmap(bm);
				//
				// Bimp.tempSelectBitmap.add(takePhoto);

				try {
					File f = new File(Environment.getExternalStorageDirectory() + "/" + PICTURE_FILE);
		
					if (f.exists()) {

						Bitmap bm = ImageUtil.imageZoom(ImageUtil.scaleImage(f.getAbsolutePath(), 500f, 240f));
						ImageItem takePhoto = new ImageItem();
						takePhoto.setBitmap(bm);
						Bimp.tempSelectBitmap.add(takePhoto);
					}
				} catch (Exception e) {
					// TODO: handle exception
				
				}

			}
			break;

		}

	}

}
