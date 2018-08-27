package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.andorid.zxing.app.CaptureActivity;
import com.hc.android.huixin.MyApplication;
import com.hc.android.huixin.R;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.MaintenanceTypeItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectItem;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.ActionItem;
import com.hc.android.huixin.view.DefaultDialog;
import com.hc.android.huixin.view.TitlePopup;
import com.hc.android.huixin.view.TitlePopup.OnItemOnClickListener;
import com.hc.android.laddercontrolcamera.TakeCameraActivity;
import com.king.photo.model.HumanModel;
import com.king.photo.model.LockDriverModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LadderControlHumenMessage extends Activity implements OnClickListener {
	ImageButton regulatory_back;
	Button btnUpload;
	LinearLayout LinearLayoutUploadHumen;
	TextView txtName,txtIDNum,txtQualificationCertificate,txtPhone,txtProject;
	ImageView fristImageView,secondImageView,thirdImageView;
	ProgressDialog dialog;
	String url_1;
	String url_2;
	String url_3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ladder_control_humen_message);
		MyApplication myApplication = (MyApplication) this.getApplication();
		myApplication.addHumanActivity(this);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		regulatory_back = (ImageButton)findViewById(R.id.regulatory_back);
		regulatory_back.setOnClickListener(this);
		LinearLayoutUploadHumen = (LinearLayout) findViewById(R.id.LinearLayout_upload_humen);
		LinearLayoutUploadHumen.setOnClickListener(this);
		btnUpload = (Button) findViewById(R.id.btn_upload_humen);
		btnUpload.setOnClickListener(this);
		txtName = (TextView) findViewById(R.id.text_humen_name);
		HumanModel humanModel = (HumanModel)getIntent().getSerializableExtra("humanModel");
		txtName.setText(humanModel.getUserName());
		txtIDNum = (TextView) findViewById(R.id.text_humen_idnum);
		txtIDNum.setText(humanModel.getUserIDNum());
		txtQualificationCertificate = (TextView) findViewById(R.id.text_humen_qualification_certificate);
		txtQualificationCertificate.setText(humanModel.getUserCredentials());
		txtPhone = (TextView) findViewById(R.id.text_humen_phone);
		txtPhone.setText(humanModel.getUserPhone());
		txtProject = (TextView) findViewById(R.id.text_humen_project);
		txtProject.setText(humanModel.getProjectName());
		fristImageView = (ImageView) findViewById(R.id.frist_imageView);
		secondImageView = (ImageView) findViewById(R.id.sencond_imageView);
		thirdImageView = (ImageView) findViewById(R.id.third_imageView);
		fristImageView.setImageBitmap(ImageUtil.getLoacalBitmap("/sdcard/tempphoto1.jpg"));
		secondImageView.setImageBitmap(ImageUtil.getLoacalBitmap("/sdcard/tempphoto2.jpg"));
		thirdImageView.setImageBitmap(ImageUtil.getLoacalBitmap("/sdcard/tempphoto3.jpg"));
		fristImageView.setOnClickListener(this);
		secondImageView.setOnClickListener(this);
		thirdImageView.setOnClickListener(this);
		url_1 = getIntent().getStringExtra("image_1");
		url_2 = getIntent().getStringExtra("image_2");
		url_3 = getIntent().getStringExtra("image_3");
	}
	public class AddHumanAsyncTask extends AsyncTask<HumanModel, Void, String> {
		@Override
		protected String doInBackground(HumanModel... params) {
		
			// TODO Auto-generated method stub
			return new NetworkApi().addHumanIsImage(params[0], url_1, url_2, url_3);

		}

		@Override
		protected void onPostExecute(String data) {
			dialog.dismiss();
			if(data.equals("添加操作员成功")){
				DefaultDialog.showDialogRemoveActivity(LadderControlHumenMessage.this, data);
			}else{
				DefaultDialog.showDialog(LadderControlHumenMessage.this, data);
			}
		}
	}

	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void back() {
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(LadderControlHumenMessage.this,ShowImageMessageActivity.class);
		switch (v.getId()) {
		
		case R.id.regulatory_back:
			back();
			break;
		case R.id.LinearLayout_upload_humen:
			dialog = DialogUtil.createProgressDialog(this, "正在绑定...");
			dialog.show();
			new AddHumanAsyncTask().execute((HumanModel)getIntent().getSerializableExtra("humanModel"));
			break;
		case R.id.btn_upload_humen:
			dialog = DialogUtil.createProgressDialog(this, "正在绑定...");
			dialog.show();
			new AddHumanAsyncTask().execute((HumanModel)getIntent().getSerializableExtra("humanModel"));
			break;
		case R.id.frist_imageView:
			intent.putExtra("imageUrl","/sdcard/tempphoto1.jpg");
			startActivity(intent);
			break;
		case R.id.sencond_imageView:
			intent.putExtra("imageUrl","/sdcard/tempphoto2.jpg");
			startActivity(intent);
			break;
		case R.id.third_imageView:
			intent.putExtra("imageUrl","/sdcard/tempphoto3.jpg");
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	

}
