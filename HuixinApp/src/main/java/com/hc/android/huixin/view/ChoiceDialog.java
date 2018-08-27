package com.hc.android.huixin.view;

import java.util.ArrayList;
import java.util.List;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.CameraItem;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ListView;

public class ChoiceDialog extends Dialog {

	private ListView mListView; // 列表
	private Button mConfirmBtn;
	private OnDialogListener mListener;
	private List<CameraItem> mMsgs = new ArrayList<CameraItem>();
	private ChoiceDialogAdapt mAdapt;

	public ChoiceDialog(Context context, List<CameraItem> msgs) {
		super(context, R.style.Dialog);
		initViews(context, msgs);
	}

	private void initViews(Context context, List<CameraItem> msgs) {
		setContentView(R.layout.dialog_choice);
		mMsgs = msgs;
		mListView = (ListView) findViewById(R.id.dialog_lv);
		mConfirmBtn = (Button) findViewById(R.id.dialog_confirm_bt);
		// 设置对话框位置大小
		Window dialogWindow = getWindow();
		dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		dialogWindow.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();

		dialogWindow.setAttributes(lp);// 此处暂未设置偏移量
		// setCanceledOnTouchOutside(false);
		// setCancelable(false);
		mAdapt = new ChoiceDialogAdapt(context, mMsgs);
		mListView.setAdapter(mAdapt);
		mConfirmBtn.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				try {
					if (mAdapt.getmSelectRadioIndex() < 0)//没有数据
						return;
					if (mListener != null) {
						mListener.onConfirmClick(mAdapt.getmSelectRadioIndex());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * 设置确定按钮字符串
	 */
	public ChoiceDialog setConfirmText(String text) {
		if (!TextUtils.isEmpty(text))
			mConfirmBtn.setText(text);
		return this;
	}

	/**
	 * 设置确定按钮字符串
	 */
	public ChoiceDialog setConfirmOnclick(OnDialogListener onClick) {
		if (onClick != null) {
			mListener = onClick;
		}
		return this;
	}

	public interface OnDialogListener {
		public void onConfirmClick(int selectIndex);
	}
}