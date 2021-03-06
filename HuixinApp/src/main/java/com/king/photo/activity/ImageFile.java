package com.king.photo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.hc.android.huixin.R;
import com.king.photo.adapter.FolderAdapter;
import com.king.photo.util.Bimp;

/**
 * 这个类主要是用来进行显示包含图片的文件夹
 */
public class ImageFile extends Activity {

	private FolderAdapter folderAdapter;
	private Button bt_cancel;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plugin_camera_image_file);
		bt_cancel = (Button) findViewById(R.id.cancel);
		bt_cancel.setOnClickListener(new CancelListener());
		GridView gridView = (GridView) findViewById(R.id.fileGridView);
		TextView textView = (TextView) findViewById(R.id.headerTitle);
		textView.setText(getString(R.string.photo));
		folderAdapter = new FolderAdapter(this);
		gridView.setAdapter(folderAdapter);
	}

	private class CancelListener implements OnClickListener {// 取消按钮的监听
		public void onClick(View v) {
			// 清空选择的图片
			Bimp.tempSelectBitmap.clear();
			finish();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return true;
	}

}
