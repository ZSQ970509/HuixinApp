package com.king.photo.activity;

import java.util.ArrayList;

import com.hc.android.huixin.R;
import com.hc.android.huixin.TakePhoto2Activity;
import com.hc.android.huixin.network.ConstrucTypeItem;
import com.hc.android.huixin.network.NetworkApi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityAdjustProjectMain extends Activity implements OnClickListener {

	private PagedQueryResultHelper pagedQueryResultHelper;
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adjust_project_main);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		findViewById(R.id.searchBtn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pagedQueryResultHelper.searchToFirstPage();
				query(((EditText) findViewById(R.id.searchWord)).getText().toString().trim());

			}
		});
		pagedQueryResultHelper = new PagedQueryResultHelper(this, getIntent().getStringExtra("title"));
		query("");
	}

	protected void query(String content) {
		pagedQueryResultHelper.setQueryParams(content, "aa");
		if (pagedQueryResultHelper.initialQuery() == null) {

			new AlertDialog.Builder(this).setTitle("信息").setMessage("没有查询到相关信息，请返回！").setPositiveButton("OK", null)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							ActivityAdjustProjectMain.this.finish();
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
		}
	}

}
