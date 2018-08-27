package com.king.photo.activity;

import java.util.ArrayList;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hc.android.huixin.R;
import com.hc.android.huixin.TakePhoto2Activity;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectItem;
import com.hc.android.huixin.network.SendDataBean;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.util.Bimp;
import com.king.photo.util.FileUtils;
import com.king.photo.util.ImageItem;

/**
 * 首页面activity
 */
public class PhotoMainActivity extends Activity {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap;
	private EditText mEditNote;
	private Spinner mProjectNameList;
	private ArrayList<ProjectItem> mData = new ArrayList<ProjectItem>();
	private EditText mKeyWork;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
		parentView = getLayoutInflater().inflate(R.layout.activity_photo_selectimg, null);
		setContentView(parentView);
		Init();
	}

	public void Init() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mEditNote = (EditText) findViewById(R.id.edit_note);
		findViewById(R.id.activity_selectimg_send).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				uploadImage();
			}
		});
		mKeyWork = (EditText) findViewById(R.id.edit_project_name);
		mProjectNameList = (Spinner) findViewById(R.id.spinner_project_name);
		pop = new PopupWindow(PhotoMainActivity.this);

		View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PhotoMainActivity.this, AlbumActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		findViewById(R.id.search).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchProject();
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
					ll_popup.startAnimation(
							AnimationUtils.loadAnimation(PhotoMainActivity.this, R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(PhotoMainActivity.this, GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

	}

	private Handler mHandler = new Handler();

	private void uploadImage() {
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送图片...");
		dialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				ProjectItem projitem = mData.get(mProjectNameList.getSelectedItemPosition());
				// String SessionId = UUID.randomUUID().toString();
				String note = mEditNote.getText().toString();
				for (ImageItem item : Bimp.tempSelectBitmap) {
					Bitmap bitmap = ImageUtil.scaleImage(item.getImagePath(), 500f, 500f);
					if (bitmap == null) {
						return;
					}
					String imgStr = ImageUtil.convertIconToString(bitmap);
					bitmap.recycle();
					bitmap = null;
					SendDataBean data = new SendDataBean();
					data.UserName = PreferenceUtil.getName();
					data.Note = note;
					data.ImgStr = imgStr;
					data.ProjcLat = PreferenceUtil.getProjectLat();
					data.ProjcLng = PreferenceUtil.getProjectLng();
					data.ProjectId = projitem.projectId;
					data.ProjectName = projitem.ProjectName;
					NetworkApi.UploadImage(data);
				}

				mHandler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						ToastHelp.showToast(PhotoMainActivity.this, "图片发送完成！");
					}
				});
			}
		}).start();
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
			if (Bimp.tempSelectBitmap.size() == 9) {
				return 9;
			}
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
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
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

	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				FileUtils.saveBitmap(bm, fileName);

				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		finish();
		return true;
	}

	private void searchProject() {
		final String name = mKeyWork.getText().toString();
		if (TextUtils.isEmpty(name)) {
			ToastHelp.showToast(PhotoMainActivity.this, "关键字不能为空！");
			return;
		}
		mData.clear();
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在获取工程名称...");
		dialog.show();
		dialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				return true;
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				NetworkApi.queryProjectData(PhotoMainActivity.this, name, "", new INetCallback() {
					@Override
					public void onCallback(boolean value, String result) {
						if (value) {
							mData.addAll(NetworkApi.parstToProjectList(result));
							int count = mData.size();
							if (count == 0) {
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										dialog.cancel();
										ToastHelp.showToast(PhotoMainActivity.this, "未搜索到工程数据！");
									}
								});
								return;
							}
							String[] dataList = new String[count];
							for (int i = 0; i < count; i++) {
								dataList[i] = mData.get(i).ProjectName;
							}
							final ArrayAdapter<String> adapter = new ArrayAdapter<String>(PhotoMainActivity.this,
									R.layout.simple_spinner_item, dataList);
							adapter.setDropDownViewResource(R.layout.simple_spinner_item);
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									dialog.cancel();
									mProjectNameList.setAdapter(adapter);
								}
							});
						} else {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									dialog.cancel();
									ToastHelp.showToast(PhotoMainActivity.this, "获取工程名称失败！");
								}
							});
						}
					}
				});
			}
		}).start();
	}
}
