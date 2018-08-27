package com.hc.android.huixin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.SendDataBean;
import com.hc.android.huixin.network.UsersItem;
import com.hc.android.huixin.util.DeviceUtil;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;

/**
 * 本地照片上传
 */
public class UploadImageActivity extends Activity implements OnClickListener {

	private DisplayImageOptions options;
	private List<String> imageData = new ArrayList<String>();
	private GridView mGridView;
	private GridAdapter mGridAapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_image);
		initView();
		options = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	private void initView() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		findViewById(R.id.btn_back).setOnClickListener(this);
		findViewById(R.id.btn_add_image).setOnClickListener(this);

		mGridView = (GridView) findViewById(R.id.grid_view);
		mGridAapter = new GridAdapter();
		mGridView.setAdapter(mGridAapter);

		mEditUsers = (EditText) findViewById(R.id.edit_users);// 随行人员
		new UsersAsyncTask().execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_add_image:
			addImage();
			break;
		case R.id.btn_upload_image:
			uploadImage();
			break;
		default:
			break;
		}
	}

	private void addImage() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, 0);
	}

	private Handler mHandler = new Handler();

	private void uploadImage() {
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送图片...");
		dialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				String SessionId = UUID.randomUUID().toString();
				for (String imagePath : imageData) {
					Bitmap bitmap = ImageUtil.scaleImage(imagePath, 500f, 500f);
					if (bitmap == null) {
						return;
					}
					String imgStr = ImageUtil.convertIconToString(bitmap);
					bitmap.recycle();
					bitmap = null;
					SendDataBean data = new SendDataBean();
					data.UserName = PreferenceUtil.getName();
					data.Note = mUsers;
					data.ImgStr = imgStr;
					data.ProjcLat = PreferenceUtil.getProjectLat();
					data.ProjcLng = PreferenceUtil.getProjectLng();
					data.SessionId = SessionId;
					NetworkApi.UploadImage(data);
				}

				mHandler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						ToastHelp.showToast(UploadImageActivity.this, "图片发送完成！");
					}
				});
			}
		}).start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			try {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();

				imageData.add(picturePath);
				mGridAapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	class GridAdapter extends BaseAdapter {

		class ViewHolder {
			ImageView image;
			Button btnDelete;
		}

		@Override
		public int getCount() {
			return imageData.size();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_grid_item, null);
				int width = DeviceUtil.getDeviceWidth(UploadImageActivity.this) / 3;
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.ItemImage);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width);
				holder.image.setLayoutParams(params);
				holder.btnDelete = (Button) convertView.findViewById(R.id.btn_delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ImageLoader.getInstance().displayImage("file://" + imageData.get(position), holder.image, options);
			holder.btnDelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					imageData.remove(position);
					mGridAapter.notifyDataSetChanged();
				}
			});
			return convertView;
		}

		@Override
		public Object getItem(int position) {
			return imageData.get(position);
		}

	}

	// 随行人员
	private EditText mEditUsers;
	// 随行人员
	private String mUsers = "";
	private ListView user_multiChoiceListView;

	// 随行人员
	private class UsersAsyncTask extends AsyncTask<Void, Void, ArrayList<UsersItem>> {

		@Override
		protected ArrayList<UsersItem> doInBackground(Void... params) {
			return new NetworkApi().GetUsers();
		}

		@Override
		protected void onPostExecute(final ArrayList<UsersItem> data) {
			if (data == null) {
				return;
			}
			final String[] user_items = new String[data.size()];
			final boolean[] user_checkedItems = new boolean[data.size()];
			for (int i = 0; i < data.size(); i++) {
				user_items[i] = data.get(i).UserName;
				user_checkedItems[i] = false;
			}
			findViewById(R.id.btn_users).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(UploadImageActivity.this);
					builder.setMultiChoiceItems(user_items, user_checkedItems, null);
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							String msg = "";
							mUsers = "";
							for (int i = 0; i < user_items.length; i++) {
								if (user_multiChoiceListView.getCheckedItemPositions().get(i)) {
									if (!TextUtils.isEmpty(msg)) {
										msg += "|";
										mUsers += "|";
									}
									msg += data.get(i).UserName;
									mUsers += data.get(i).UserName;
								}
							}
							mEditUsers.setText(msg);
						}
					});
					AlertDialog dialog = builder.create();
					user_multiChoiceListView = dialog.getListView();
					dialog.show();
				}
			});
		}
	}

}
