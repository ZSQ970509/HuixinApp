package com.hc.android.huixin.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Spinner自定义AlertDialog类 2016年3月25日11:02:56
 * 
 */
public class SpinnerSelectDialog extends AlertDialog {

	public SpinnerSelectDialog(Context context, int theme) {
		super(context, theme);
	}

	public SpinnerSelectDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.slt_cnt_type);
	}
}