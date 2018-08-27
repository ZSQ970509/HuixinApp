package com.king.photo.activity;

import com.hc.android.huixin.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TakeDetailsActivity extends Activity implements OnClickListener {

	TextView mRuleName, mRuleDetails;
	Button btnElect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_details);
		initview();

	}

	void initview() {
		mRuleName = (TextView) findViewById(R.id.ruleName);
		mRuleDetails = (TextView) findViewById(R.id.ruleDetails);
		TextView titleView = (TextView) findViewById(R.id.title);
		mRuleName.setText(getIntent().getStringExtra("text"));
		mRuleDetails.setText(getIntent().getStringExtra("detail"));
		titleView.setText(getIntent().getStringExtra("title"));
		findViewById(R.id.detail_back).setOnClickListener(this);
		btnElect = (Button) findViewById(R.id.btn_elect);
		btnElect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				elect();
			}
		});
	}

	public void elect() {
		Intent intent = getIntent();

		setResult(0x123, intent);
		finish();
	}

	private void back() {
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.detail_back:
			back();
			break;

		default:
			break;
		}
	}

}
