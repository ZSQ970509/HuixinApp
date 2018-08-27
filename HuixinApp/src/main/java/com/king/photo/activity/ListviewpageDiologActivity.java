package com.king.photo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hc.android.huixin.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;

public class ListviewpageDiologActivity extends Activity implements OnClickListener {
	private TakePhotoSafePagedQueryResultHelper pagedQueryResultHelper;
	private ListView list;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setFinishOnTouchOutside(false);
		setContentView(R.layout.activity_listviewpage);
		getWindow().setGravity(Gravity.CENTER);
		list = (ListView) findViewById(R.id.list);
		pagedQueryResultHelper = new TakePhotoSafePagedQueryResultHelper(this, getIntent().getStringExtra("title"));
		query();
	}

	protected void query() {
		pagedQueryResultHelper.setQueryParams(getIntent().getStringExtra("Values"),
				getIntent().getStringExtra("TypeCode"));

		if (pagedQueryResultHelper.initialQuery() == null) {
			new AlertDialog.Builder(this).setTitle("信息").setMessage("没有查询到相关信息，请返回！").setPositiveButton("OK", null)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ListviewpageDiologActivity.this.finish();
						}
					}).show();
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.first_page:
			pagedQueryResultHelper.firstPage();
			break;
		case R.id.page_down:
			pagedQueryResultHelper.pageDown();
			break;
		case R.id.page_up:
			pagedQueryResultHelper.pageUp();
			break;
		case R.id.last_page:
			pagedQueryResultHelper.lastPage();
			break;
		case R.id.to_page:
			pagedQueryResultHelper.toPage();
			break;
		case R.id.to_cancel:
			pagedQueryResultHelper.toCancel();
			break;

		}
	}

}
