package com.hc.android.huixin.binding;

import java.io.ByteArrayOutputStream;
import java.io.File;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.NfcHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BindingNfcActivity extends Activity {

	private static final int REQUEST_TAKEPHOTO = 3;
	private ImageView mImageView;
	private String SampleId;
	private String SampleEPC;
	private String EPCContent;
	private String Imgstr;
	private TextView cardnum;
	private TextView cardcontent;
	private ProgressDialog progressDialog = null;
	private Handler mPostHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bingding_nfc);

		NfcHelper.getInstance().init(this);

		Bundle bundle = getIntent().getExtras();
		SampleId = bundle.getString("SampleId");

		TextView project = (TextView) findViewById(R.id.project_name);
		project.setText(project.getText() + bundle.getString("ProjectName"));

		TextView test = (TextView) findViewById(R.id.test_name);
		test.setText(test.getText() + bundle.getString("SampleName"));

		TextView type = (TextView) findViewById(R.id.test_type);
		type.setText(type.getText() + bundle.getString("SampleType"));

		cardnum = (TextView) findViewById(R.id.card_num);
		cardcontent = (TextView) findViewById(R.id.card_content);
		String epc = bundle.getString("SampleEPC");
		if (epc == null || "".equals(epc)) {
			cardnum.setText("未读取");
			cardcontent.setText("未读取");
		} else {
			int a = epc.indexOf(",-");
			if (a >= 0) {
				String[] str = epc.split(",-");
				if (str.length > 0) {
					SampleEPC = str[0];
				}
				if (str.length > 1) {
					EPCContent = str[1];
				}
			} else {
				SampleEPC = epc;
				EPCContent = epc;
			}
			cardnum.setText("标签ID：" + SampleEPC);
			cardcontent.setText("描述：" + EPCContent);

		}
		mImageView = (ImageView) findViewById(R.id.img);

		Button photoButton = (Button) findViewById(R.id.button_photo);
		photoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				takePhoto();
			}
		});
		Button upload = (Button) findViewById(R.id.button_upload);
		upload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(SampleEPC) && !TextUtils.isEmpty(Imgstr)) {
					progressDialog = ProgressDialog.show(BindingNfcActivity.this, "请稍等...", "上传数据中...", false);
					progressDialog.setCancelable(false);
					new Thread() {
						public void run() {
							NetworkApi.sendBindingData(BindingNfcActivity.this, SampleId, SampleEPC + ",-" + EPCContent,
									Imgstr, new INetCallback() {

										@Override
										public void onCallback(boolean value, String result) {
											if (value) {
												mPostHandler.post(new Runnable() {
													@Override
													public void run() {
														progressDialog.dismiss();
														Toast.makeText(BindingNfcActivity.this, "操作成功!!!",
																Toast.LENGTH_SHORT).show();
													}
												});
											} else {
												mPostHandler.post(new Runnable() {
													@Override
													public void run() {
														progressDialog.dismiss();
														Toast.makeText(BindingNfcActivity.this, "操作失败!!!",
																Toast.LENGTH_SHORT).show();
													}
												});
											}
										}
									});
						}
					}.start();
				} else {
					Toast.makeText(BindingNfcActivity.this, "信息不完整", Toast.LENGTH_SHORT).show();
				}

			}
		});

		if (NfcHelper.getInstance().isSupportNfc()) {
			if (!NfcHelper.getInstance().isEnableNfc()) {
				DialogUtil.showNfcSettingDialog(this);
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		NfcHelper.getInstance().onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		NfcHelper.getInstance().onPause();
	}

	@Override
	public void onNewIntent(Intent intent) {
		String idstr = NfcHelper.getInstance().readNFCTagId(intent);
		if (!TextUtils.isEmpty(idstr)) {
			SampleEPC = idstr;
			cardnum.setText("标签ID：" + SampleEPC);
			String text = NfcHelper.getInstance().readNfcTag(intent);
			EPCContent = text;
			cardcontent.setText("描述：" + EPCContent);
			Toast.makeText(this, "标签读取成功！", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "标签读取失败！", Toast.LENGTH_SHORT).show();
		}
	}

	private String photoUrl;

	/**
	 * 拍摄照片
	 */
	protected void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String dirName = Environment.getExternalStorageDirectory() + "/hf";
		File f = new File(dirName);
		if (!f.exists()) {
			f.mkdir();
		}
		photoUrl = dirName + "/hf.jpg";
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(dirName, "hf.jpg")));
		startActivityForResult(intent, REQUEST_TAKEPHOTO);

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_TAKEPHOTO:
			if (resultCode == Activity.RESULT_OK) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile", "SD card is not avaiable/writeable right now.");
					return;
				}
				if (!(new File(photoUrl)).exists())
					return;
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(photoUrl, opts);
				int h = opts.outHeight;
				int w = opts.outWidth;
				if (h > w) {
					if (h > 800) {
						opts.inSampleSize = h / 800;
					}
				} else {
					if (w > 800) {
						opts.inSampleSize = w / 800;
					}
				}
				opts.inJustDecodeBounds = false;
				Bitmap bitmap = null;
				try {
					bitmap = BitmapFactory.decodeFile(photoUrl, opts);
					w = bitmap.getWidth();
					h = bitmap.getHeight();
					float scale = 1;
					if (h > w) {
						if (h > 800) {
							scale = h * 1.0f / 800.0f;
							h = 800;
							w = (int) (w / scale);
						}
					} else {
						if (w > 800) {
							scale = w * 1.0f / 800.0f;
							w = 800;
							h = (int) (h / scale);
						}
					}
					;
					bitmap = Bitmap.createScaledBitmap(bitmap, w, h, false);
				} catch (OutOfMemoryError err) {

				}

				// 获取相机返回的数据，并转换为Bitmap图片格式
				if (bitmap == null)
					return;
				Imgstr = Base64.encodeToString(Bitmap2Bytes(bitmap), Base64.DEFAULT);
				mImageView.setImageBitmap(bitmap);
			}
		}
	}

	private byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

}