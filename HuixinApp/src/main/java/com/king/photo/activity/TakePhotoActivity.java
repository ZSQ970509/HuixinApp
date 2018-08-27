package com.king.photo.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.HttpUtil;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.MaintenanceTypeItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.network.ProjectItem;
import com.hc.android.huixin.network.SendDataBean;
import com.hc.android.huixin.network.SendPhotoDataBean;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.ImageUtil;
import com.hc.android.huixin.util.LogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.util.ToastHelp;
import com.hc.android.huixin.view.ActionItem;
import com.hc.android.huixin.view.TitlePopup;
import com.hc.android.huixin.view.TitlePopup.OnItemOnClickListener;
import com.king.photo.activity.TakeSafeActivity.baseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;

/**
 * 监管影像采集
 */
public class TakePhotoActivity extends Activity implements OnClickListener {

	private ScrollView mTakePhotoView;
	private ScrollView mShowPhotoView;
	private ImageView mPhotoImage;
	private Handler mHandler = new Handler();
	private String mPhotoUri;
	private ArrayList<ProjectItem> mData = new ArrayList<ProjectItem>();
	private EditText mNoteEdit;

	private TextView mTextViewBuildNum;// 抓拍地点
	private EditText mEditBuildNum;// 抓拍地点
	private TextView mTextViewCameraId;// 具体部位
	private EditText mEditCameraId;// 具体部位
	private TextView mTextViewCameraType;// 部位类型
	private EditText mEditCameraType;// 部位类型
	private TextView mTextViewPlaceType;// 分部工程
	private TextView mTextViewType;// 子分部工程
	private TextView mTextViewProjectNote;// 现场描述
	private TextView mTextViewProgress;// 评价
	private TextView mTextViewLocalPostion;// 当前位置
	private TextView mTextViewTextTime;// 当前时间
	private TextView mTextViewNormRule;// 规范条例
	private String mProjcLat = "0.0";
	private String mProjcLng = "0.0";

	private String mNormRequire = "";// 规范要求
	private String mNormRules = "";// 规范要求

	private String mBuildNum = "";// 抓拍地点
	private String mCameraId = "";// 具体部位
	private String mCameraType = "";// 部位类型
	private String mPlaceType = "";// 分部工程
	private String mType = "";// 子分部工程
	private String mProgress = "符合";// 评价
	private Button mRendPhoto;
	private Button mBtnProgress;
	private int mDirType;// 分部工程id
	private int mDirNormRules;// 规范条例id
	private int mDowmDirNormRule;// 规范条例id
	private RelativeLayout mTakePhotoLayout;
	private String mProjectId;
	private String mProjectName;
	private TextView mTextRequre;

	Stack stack = new Stack<Integer>();
	int mRulesHasSubset = 1;
	AlertDialog mAlertDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo);
		initView();
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

	private void initView() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);
		mProjectId = getIntent().getStringExtra("projectId");
		mProjectName = getIntent().getStringExtra("projectName");
		TextView textViewProjectName = (TextView) findViewById(R.id.text_project_name);
		textViewProjectName.setText(mProjectName);
		textViewProjectName.setTextSize(16);
		TextView textViewProjectName2 = (TextView) findViewById(R.id.txt_project_name2);
		textViewProjectName2.setText(mProjectName);
		mTakePhotoLayout = (RelativeLayout) findViewById(R.id.take_photo_Layout);
		findViewById(R.id.regulatory_back).setOnClickListener(this);
		findViewById(R.id.send_photo).setOnClickListener(this);
		mTakePhotoView = (ScrollView) findViewById(R.id.send_photo_view);
		mShowPhotoView = (ScrollView) findViewById(R.id.show_photo_view);
		mRendPhoto = (Button) findViewById(R.id.send_photo);
		mRendPhoto.setOnClickListener(this);
		mBtnProgress = (Button) findViewById(R.id.btn_progress);
		mBtnProgress.setOnClickListener(this);
		findViewById(R.id.take_photo).setOnClickListener(this);
		mPhotoImage = (ImageView) findViewById(R.id.photo_image);
		mNoteEdit = (EditText) findViewById(R.id.project_note);
		mTextViewBuildNum = (TextView) findViewById(R.id.txt_build_num);
		mEditBuildNum = (EditText) findViewById(R.id.edit_build_num);
		mTextViewCameraId = (TextView) findViewById(R.id.txt_camera_id);
		mEditCameraId = (EditText) findViewById(R.id.edit_CameraId);
		mTextViewCameraType = (TextView) findViewById(R.id.txt_camera_type);
		mEditCameraType = (EditText) findViewById(R.id.edit_Cameratype);
		mTextViewPlaceType = (TextView) findViewById(R.id.txt_place_type);
		mTextViewType = (TextView) findViewById(R.id.txt_type);
		mTextViewProjectNote = (TextView) findViewById(R.id.txt_project_note);
		mTextViewProgress = (TextView) findViewById(R.id.txt_progress);
		mTextRequre = (TextView) findViewById(R.id.txt_Requre);
		mTextViewLocalPostion = (TextView) findViewById(R.id.txt_local_position);
		mTextViewTextTime = (TextView) findViewById(R.id.txt_time);

		mTextViewNormRule = (TextView) findViewById(R.id.txt_Norm_Rule);

		// 隐藏软键盘弹出
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		findViewById(R.id.violate_rule).setVisibility(View.GONE);
		findViewById(R.id.quote_requre).setVisibility(View.GONE);

		new GetBuildNumAsyncTask().execute();
		new GetCameraIdAsyncTask().execute();
		new GetCameraTypeAsyncTask().execute();
		new GetPlaceTypeAsyncTask().execute();
		RadioGroup radio = (RadioGroup) findViewById(R.id.radiogroup_progress);
		radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_2:
					mProgress = "符合";
					findViewById(R.id.violate_rule).setVisibility(View.GONE);
					findViewById(R.id.quote_requre).setVisibility(View.GONE);
					break;
				case R.id.radio_3:
					mProgress = "不符合";
					findViewById(R.id.violate_rule).setVisibility(View.VISIBLE);
					findViewById(R.id.quote_requre).setVisibility(View.VISIBLE);
					break;
				case R.id.radio_4:
					mProgress = "优良";
					findViewById(R.id.violate_rule).setVisibility(View.GONE);
					findViewById(R.id.quote_requre).setVisibility(View.GONE);

					break;
				default:
					break;
				}
			}
		});
	}

	Button BuildNumspinner;
	TitlePopup BuildNumpopup;

	// 抓拍地点
	private class GetBuildNumAsyncTask extends AsyncTask<Void, Void, ArrayList<MaintenanceTypeItem>> {

		@Override
		protected ArrayList<MaintenanceTypeItem> doInBackground(Void... params) {
			return new NetworkApi().GetBuildNum();
		}

		@Override
		protected void onPostExecute(final ArrayList<MaintenanceTypeItem> data) {
			if (data == null) {
				return;
			}
			if (data.size() == 0) {
				return;
			}

			BuildNumspinner = (Button) findViewById(R.id.spinner_build_num);

			// 实例化标题栏弹窗
			BuildNumpopup = new TitlePopup(TakePhotoActivity.this, BuildNumspinner.getWidth() / 2,
					LayoutParams.WRAP_CONTENT);

			String[] nameData = new String[data.size()];
			for (int i = 0; i < nameData.length; i++) {
				nameData[i] = data.get(i).text;
				BuildNumpopup
						.addAction(new ActionItem(TakePhotoActivity.this, nameData[i].toString(), R.drawable.account));

			}

			mBuildNum = data.get(0).text;
			BuildNumspinner.setText(mBuildNum);
			if (mBuildNum.equals("其他")) {
				mEditBuildNum.setVisibility(View.VISIBLE);
				mEditBuildNum.setInputType(InputType.TYPE_CLASS_TEXT);
			} else if (mBuildNum.equals("栋号")) {
				mEditBuildNum.setVisibility(View.VISIBLE);
				mEditBuildNum.setInputType(InputType.TYPE_CLASS_NUMBER);
			} else {
				mEditBuildNum.setVisibility(View.GONE);
			}

			BuildNumpopup.setItemOnClickListener(new OnItemOnClickListener() {

				@Override
				public void onItemClick(ActionItem item, int position) {
					// TODO Auto-generated method stub
					mBuildNum = data.get(position).text;
					BuildNumspinner.setText(mBuildNum);
					if (mBuildNum.equals("其他")) {
						mEditBuildNum.setVisibility(View.VISIBLE);
						mEditBuildNum.setInputType(InputType.TYPE_CLASS_TEXT);
					} else if (mBuildNum.equals("栋号")) {
						mEditBuildNum.setVisibility(View.VISIBLE);
						mEditBuildNum.setInputType(InputType.TYPE_CLASS_NUMBER);
					} else {
						mEditBuildNum.setVisibility(View.GONE);
					}
				}
			});

			BuildNumspinner.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					BuildNumpopup.show(v);

				}
			});

		}
	}

	Button CameraIdspinner;
	TitlePopup CameraIdpopup;

	// 具体部位
	private class GetCameraIdAsyncTask extends AsyncTask<Void, Void, ArrayList<MaintenanceTypeItem>> {

		@Override
		protected ArrayList<MaintenanceTypeItem> doInBackground(Void... params) {
			return new NetworkApi().GetCameraId();
		}

		@Override
		protected void onPostExecute(final ArrayList<MaintenanceTypeItem> data) {
			if (data == null) {
				return;
			}
			if (data.size() == 0) {
				return;
			}

			CameraIdspinner = (Button) findViewById(R.id.spinner_CameraId);

			// 实例化标题栏弹窗
			CameraIdpopup = new TitlePopup(TakePhotoActivity.this, CameraIdspinner.getWidth() / 2,
					LayoutParams.WRAP_CONTENT);

			String[] nameData = new String[data.size()];
			for (int i = 0; i < nameData.length; i++) {
				nameData[i] = data.get(i).text;
				CameraIdpopup
						.addAction(new ActionItem(TakePhotoActivity.this, nameData[i].toString(), R.drawable.account));
			}

			mCameraId = data.get(0).text;
			CameraIdspinner.setText(mCameraId);
			if (mCameraId.equals("其他")) {
				mEditCameraId.setVisibility(View.VISIBLE);
				mEditCameraId.setInputType(InputType.TYPE_CLASS_TEXT);
			} else if (mCameraId.equals("层数")) {
				mEditCameraId.setVisibility(View.VISIBLE);
				mEditCameraId.setInputType(InputType.TYPE_CLASS_NUMBER);
			} else {
				mEditCameraId.setVisibility(View.GONE);
			}

			CameraIdpopup.setItemOnClickListener(new OnItemOnClickListener() {

				@Override
				public void onItemClick(ActionItem item, int position) {
					// TODO Auto-generated method stub
					mCameraId = data.get(position).text;
					CameraIdspinner.setText(mCameraId);
					if (mCameraId.equals("其他")) {
						mEditCameraId.setVisibility(View.VISIBLE);
						mEditCameraId.setInputType(InputType.TYPE_CLASS_TEXT);
					} else if (mCameraId.equals("层数")) {
						mEditCameraId.setVisibility(View.VISIBLE);
						mEditCameraId.setInputType(InputType.TYPE_CLASS_NUMBER);
					} else {
						mEditCameraId.setVisibility(View.GONE);
					}
				}
			});
			CameraIdspinner.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CameraIdpopup.show(v);

				}
			});

		}
	}

	// 规范性要求
	private class GetNormRequireAsyncTask extends AsyncTask<Void, Void, ArrayList<MaintenanceTypeItem>> {

		@Override
		protected ArrayList<MaintenanceTypeItem> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return new NetworkApi().GetPhotoNormRequire();
		}

		@Override
		protected void onPostExecute(final ArrayList<MaintenanceTypeItem> data) {
			// TODO Auto-generated method stub
			super.onPostExecute(data);
			if (data == null) {
				return;
			}
			if (data.size() == 0) {
				mNormRequire = "";
				mTextRequre.setText(mNormRequire);
				mNormRules = "";
				mTextViewNormRule.setText(mNormRules);
				return;
			}
			final String[] nameData = new String[data.size()];
			for (int i = 0; i < nameData.length; i++) {
				nameData[i] = data.get(i).text;
			}

			PopQuoteRuleDialog(TakePhotoActivity.this, nameData, data);
		}

	}

	AlertDialog PopQuoteRuleAlertDialog;
	/**
	 * 弹出质量监管规范条例
	 * @param context
	 * @param nameData
	 * @param data
	 * @return
	 */
	public boolean PopQuoteRuleDialog(final Context context, String[] nameData,
			final ArrayList<MaintenanceTypeItem> data) {

		String tempdata[] = new String[data.size()];
		for (int i = 0; i < data.size(); i++) {
			tempdata[i] = data.get(i).text;
		}

		PopQuoteRuleBaseadpater mAdapter = new PopQuoteRuleBaseadpater(context, data);

		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.alertdialog, null);

		TextView titleView = (TextView) view.findViewById(R.id.titleView);
		titleView.setText("   请选择" + getIntent().getStringExtra("title") + "规范条例");
		ListView listview = (ListView) view.findViewById(R.id.listView);

		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

			}
		});

		view.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PopQuoteRuleAlertDialog.cancel();
			}
		});

		view.findViewById(R.id.btn_search).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showSearchDialog();
			}

		});

		view.findViewById(R.id.btn_lastpage).setVisibility(View.GONE);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		PopQuoteRuleAlertDialog = builder.create();
		PopQuoteRuleAlertDialog.setCanceledOnTouchOutside(false);
		PopQuoteRuleAlertDialog.show();
		PopQuoteRuleAlertDialog.getWindow().setContentView(view);
		// mAlertDialog.getWindow().setLayout(150, 320);
		return false;

	}

	public class PopQuoteRuleBaseadpater extends BaseAdapter {

		ArrayList<MaintenanceTypeItem> data;
		Context context;

		public PopQuoteRuleBaseadpater(Context context, ArrayList<MaintenanceTypeItem> data) {
			// TODO Auto-generated constructor stub

			this.context = context;
			this.data = data;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public class ViewHolder {

			TextView textView;

		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;

			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.dialog_item, null);
				viewHolder = new ViewHolder();
				viewHolder.textView = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.textView.setText("　" + data.get(position).text);
			viewHolder.textView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					mNormRequire = data.get(position).text;
					mTextRequre.setText(mNormRequire);
					// 一起使用
					mDirNormRules = data.get(position).id;
					// 加入栈
					stack.removeAllElements();
					stack.push(data.get(position).id);
					new GetNormRulesAsyncTask().execute();
					// 没有下一级返回0
					// 如果用户已经选过条例，再重新选择。应该把规范条例重新置为空，避免提交时候把之前的条例传上来
					mNormRules = "";
					mTextViewNormRule.setText(mNormRules);
					mDowmDirNormRule = 0;
					PopQuoteRuleAlertDialog.cancel();

				}
			});

			return convertView;
		}

	}

	public boolean PopRequireDialog(final Context context, String[] nameData,
			final ArrayList<MaintenanceTypeItem> data) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("   请选择" + getIntent().getStringExtra("title") + "规范条例！");
		builder.setItems(nameData, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				mNormRequire = data.get(which).text;
				mTextRequre.setText(mNormRequire);
				// 一起使用
				mDirNormRules = data.get(which).id;
				// 加入栈
				stack.removeAllElements();
				stack.push(data.get(which).id);

				new GetNormRulesAsyncTask().execute();
				// 一起使用
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		builder.create().show();
		return true;
	}

	// 规范条例
	private class GetNormRulesAsyncTask extends AsyncTask<Void, Void, ArrayList<MaintenanceTypeItem>> {

		@Override
		protected ArrayList<MaintenanceTypeItem> doInBackground(Void... params) {
			return new NetworkApi().GetSafeRulesType(mDirNormRules);
		}

		@Override
		protected void onPostExecute(final ArrayList<MaintenanceTypeItem> data) {

			if (data == null) {
				return;
			}
			if (data.size() == 0) {
				// mNormRules = mNormRequire;
				// mTextViewNormRule.setText(mNormRules);
				// mDowmDirNormRule = mDirNormRules;
				/*
				 * mNormRequire=""; mTextRequre.setText(mNormRequire);
				 */
				mNormRules = "";
				mTextViewNormRule.setText(mNormRules);
				// 没有下一级返回0
				mDowmDirNormRule = 0;
				return;
			}

			String[] nameData = new String[data.size()];
			int[] hasSubset = new int[data.size()];
			for (int i = 0; i < nameData.length; i++) {
				nameData[i] = data.get(i).text;
				hasSubset[i] = data.get(i).hasSubset;
			}

			showNormRuleDialog(TakePhotoActivity.this, nameData, data, hasSubset);
		}
	}

	private void showNormRuleDialog(final Context context, String[] itemList, final ArrayList<MaintenanceTypeItem> data,
			int[] hasSubset) {

		MyBaseAdapter baseAdapter = new MyBaseAdapter(context, itemList, data, hasSubset);

		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.alertdialog, null);

		TextView titleView = (TextView) view.findViewById(R.id.titleView);
		titleView.setText("   请选择" + getIntent().getStringExtra("title") + "规范条例！");
		ListView listview = (ListView) view.findViewById(R.id.listView);
		listview.setAdapter(baseAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				mAlertDialog.cancel();
			}
		});

		view.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mAlertDialog.cancel();
			}
		});
		view.findViewById(R.id.btn_search).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showSearchDialog();

			}

		});
		view.findViewById(R.id.btn_lastpage).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mAlertDialog.cancel();
				if (stack.isEmpty()) {

					return;
				}
				if (stack.size() <= 1) {
					showNormRequireDialog();
					return;
				}
				stack.pop();
				mDirNormRules = (Integer) (stack.get(stack.size() - 1));

				new GetNormRulesAsyncTask().execute();
			}
		});
		;

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		mAlertDialog = builder.create();
		mAlertDialog.setCanceledOnTouchOutside(false);
		mAlertDialog.show();
		mAlertDialog.getWindow().setContentView(view);
		// mAlertDialog.getWindow().setLayout(150, 320);

	}

	public class MyBaseAdapter extends BaseAdapter {

		Context context;
		String[] list;
		int[] hasSubset;
		ArrayList<MaintenanceTypeItem> data;

		public MyBaseAdapter(Context context, String[] list, ArrayList<MaintenanceTypeItem> data, int[] hasSubset) {
			// TODO Auto-generated constructor stub
			this.context = context;
			this.list = list;
			this.hasSubset = hasSubset;
			this.data = data;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public class ViewHolder {

			TextView mContent;
			TextView mBtn;

		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;

			if (convertView == null) {

				convertView = LayoutInflater.from(context).inflate(R.layout.takephotosafe_page_item, null);
				viewHolder = new ViewHolder();
				viewHolder.mContent = (TextView) convertView.findViewById(R.id.dialog_text);
				viewHolder.mBtn = (TextView) convertView.findViewById(R.id.dialog_button);

				convertView.setTag(viewHolder);

			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.mContent.setText(list[position]);
			if (hasSubset[position] == 1) {
				viewHolder.mBtn.setVisibility(View.GONE);
			}

			viewHolder.mBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(TakePhotoActivity.this, TakeDetailsActivity.class);
					intent.putExtra("text", data.get(position).text);
					intent.putExtra("detail", data.get(position).explain);
					intent.putExtra("title", getIntent().getStringExtra("title"));
					intent.putExtra("id", data.get(position).id);
					startActivityForResult(intent, 0);
					// Toast.makeText(context, "你点击了" + position + "位置",
					// Toast.LENGTH_SHORT).show();
					return;
				}
			});

			viewHolder.mContent.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					stack.push(data.get(position).id);
					mDirNormRules = data.get(position).id;
					if (hasSubset[position] == 1) {

						new GetNormRulesAsyncTask().execute();
					} else {

						mNormRules = data.get(position).text;
						mTextViewNormRule.setText(mNormRules);
						// mBtnProgress.setVisibility(View.GONE);
						mRendPhoto.setVisibility(View.VISIBLE);
						mBtnProgress.setText("请重选规范条例");
						mDowmDirNormRule = mDirNormRules;
					}
					mAlertDialog.cancel();

				}
			});

			return convertView;
		}

	}

	private void showNormRequireDialog() {
		// TODO Auto-generated method stub
		new GetNormRequireAsyncTask().execute();
	}

	AlertDialog SearchDialog;

	private void showSearchDialog() {
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.takephotosafe_page_searchdialog,
				(ViewGroup) findViewById(R.id.searchdialog));

		final EditText etname = (EditText) layout.findViewById(R.id.etname);
		layout.findViewById(R.id.search).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SearchDialog != null) {
					SearchDialog.cancel();
				}
				if (PopQuoteRuleAlertDialog != null) {
					PopQuoteRuleAlertDialog.cancel();
				}

				if (mAlertDialog != null) {
					mAlertDialog.cancel();
				}

				Intent intent = new Intent(TakePhotoActivity.this, ListviewpageDiologActivity.class);
				intent.putExtra("TypeCode", "aqjgtl");
				intent.putExtra("Values", etname.getText().toString().trim());
				intent.putExtra("title", getIntent().getStringExtra("title"));
				startActivityForResult(intent, 0x258);
			}
		});

		layout.findViewById(R.id.btn_cancel).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SearchDialog.cancel();
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(TakePhotoActivity.this);
		SearchDialog = builder.create();
		SearchDialog.setCanceledOnTouchOutside(false);
		SearchDialog.show();
		SearchDialog.getWindow().setContentView(layout);

	}

	Button CameraTypespinner;
	TitlePopup CameraTypepopup;

	// 部位类型
	private class GetCameraTypeAsyncTask extends AsyncTask<Void, Void, ArrayList<MaintenanceTypeItem>> {

		@Override
		protected ArrayList<MaintenanceTypeItem> doInBackground(Void... params) {
			return new NetworkApi().GetCameraType();
		}

		@Override
		protected void onPostExecute(final ArrayList<MaintenanceTypeItem> data) {
			if (data == null) {
				return;
			}
			if (data.size() == 0) {
				return;
			}

			CameraTypespinner = (Button) findViewById(R.id.spinner_Cameratype);
			CameraTypepopup = new TitlePopup(TakePhotoActivity.this, CameraTypespinner.getWidth(),
					LayoutParams.WRAP_CONTENT);

			String[] nameData = new String[data.size()];
			for (int i = 0; i < nameData.length; i++) {
				nameData[i] = data.get(i).text;
				CameraTypepopup
						.addAction(new ActionItem(TakePhotoActivity.this, nameData[i].toString(), R.drawable.account));

			}

			mCameraType = data.get(0).text;
			CameraTypespinner.setText(mCameraType);
			if (mCameraType.equals("其他")) {
				mEditCameraType.setVisibility(View.VISIBLE);
			} else {
				mEditCameraType.setVisibility(View.GONE);
			}

			CameraTypepopup.setItemOnClickListener(new OnItemOnClickListener() {

				@Override
				public void onItemClick(ActionItem item, int position) {
					// TODO Auto-generated method stub
					mCameraType = data.get(position).text;
					CameraTypespinner.setText(mCameraType);
					if (mCameraType.equals("其他")) {
						mEditCameraType.setVisibility(View.VISIBLE);
						CameraTypepopup.setWidth(CameraTypespinner.getWidth() / 2);
					} else {
						mEditCameraType.setVisibility(View.GONE);
						CameraTypepopup.setWidth(CameraTypespinner.getWidth());
					}
				}
			});

			CameraTypespinner.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					CameraTypepopup.show(v);

				}
			});
		}
	}

	Button PlaceTypespinner;
	TitlePopup PlaceTypepopup;

	// 分部工程
	private class GetPlaceTypeAsyncTask extends AsyncTask<Void, Void, ArrayList<MaintenanceTypeItem>> {

		@Override
		protected ArrayList<MaintenanceTypeItem> doInBackground(Void... params) {
			return new NetworkApi().GetPlaceType();
		}

		@Override
		protected void onPostExecute(final ArrayList<MaintenanceTypeItem> data) {
			if (data == null) {
				return;
			}
			if (data.size() == 0) {
				return;
			}
			PlaceTypespinner = (Button) findViewById(R.id.spinner_PlaceType);
			PlaceTypepopup = new TitlePopup(TakePhotoActivity.this, PlaceTypespinner.getWidth(),
					LayoutParams.WRAP_CONTENT);

			String[] nameData = new String[data.size()];
			for (int i = 0; i < nameData.length; i++) {
				nameData[i] = data.get(i).text;
				PlaceTypepopup
						.addAction(new ActionItem(TakePhotoActivity.this, nameData[i].toString(), R.drawable.account));
			}

			mPlaceType = data.get(0).text;
			PlaceTypespinner.setText(mPlaceType);
			mDirType = data.get(0).id;
			new GetTypeAsyncTask().execute();

			PlaceTypepopup.setItemOnClickListener(new OnItemOnClickListener() {

				@Override
				public void onItemClick(ActionItem item, int position) {
					// TODO Auto-generated method stub
					mPlaceType = data.get(position).text;
					PlaceTypespinner.setText(mPlaceType);
					mDirType = data.get(position).id;
					new GetTypeAsyncTask().execute();
				}
			});

			PlaceTypespinner.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					PlaceTypepopup.show(v);

				}
			});

		}
	}

	Button Typespinner;
	TitlePopup Typepopup;

	// 子分部工程
	private class GetTypeAsyncTask extends AsyncTask<Void, Void, ArrayList<MaintenanceTypeItem>> {

		@Override
		protected ArrayList<MaintenanceTypeItem> doInBackground(Void... params) {
			return new NetworkApi().GetType(mDirType);
		}

		@Override
		protected void onPostExecute(final ArrayList<MaintenanceTypeItem> data) {
			Typepopup = new TitlePopup(TakePhotoActivity.this);
			Typespinner = (Button) findViewById(R.id.spinner_Type);
			// 实例化标题栏弹窗
			Typepopup = new TitlePopup(TakePhotoActivity.this, Typespinner.getWidth(), LayoutParams.WRAP_CONTENT);

			if (data == null) {
				return;
			}
			if (data.size() == 0) {
				mType = "";
				Typepopup.cleanAction();
				return;
			}

			final String[] nameData = new String[data.size()];
			for (int i = 0; i < nameData.length; i++) {
				nameData[i] = data.get(i).text;
				Typepopup.addAction(new ActionItem(TakePhotoActivity.this, nameData[i].toString(), R.drawable.account));

			}
			mType = data.get(0).text;
			Typespinner.setText(mType);
			Typepopup.setItemOnClickListener(new OnItemOnClickListener() {

				@Override
				public void onItemClick(ActionItem item, int position) {
					// TODO Auto-generated method stub
					mType = data.get(position).text;
					Typespinner.setText(mType);
				}
			});

			Typespinner.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Typepopup.show(v);
				}
			});
		}
	}

	public String getDeviceUuid(Context context) {
		try {
			final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(context.TELEPHONY_SERVICE);
			final String tmDevice, tmSerial, tmPhone, androidId;
			tmDevice = "" + tm.getDeviceId();
			tmSerial = "" + tm.getSimSerialNumber();
			androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
					android.provider.Settings.Secure.ANDROID_ID);
			// UUID deviceUuid = new UUID(androidId.hashCode(),
			// ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
			String uniqueId = tmDevice.toString();
			return uniqueId;
		} catch (Exception e) {
			// TODO: handle exception
			return "null";
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regulatory_back:
			back();
			break;
		case R.id.take_photo:
			takePhoto();
			break;
		case R.id.send_photo:
			sendPhoto();
			break;
		case R.id.btn_progress:
			showNormRequireDialog();
			break;
		default:
			break;
		}
	}

	private void sendPhoto() {
		if (!HttpUtil.networkIsAvailable(this)) {
			ToastHelp.showToast(this, "手机没有网络连接！");
			return;
		}
		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在发送图片...");
		dialog.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Bitmap bitmap = ImageUtil.scaleImage(mPhotoUri, 500f, 500f);
				if (bitmap == null) {
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							ToastHelp.showToast(TakePhotoActivity.this, "发送失败！");
							dialog.cancel();
						}
					});
					return;
				}
				String imgStr = ImageUtil.convertIconToString(bitmap);
				bitmap.recycle();
				bitmap = null;
				String note = mNoteEdit.getText().toString();
				if (note.length() == 0) {
					note = "未填写";
				}

				SendPhotoDataBean data = new SendPhotoDataBean();
				data.ProjectId = mProjectId;
				data.ProjectName = mProjectName;
				data.UserName = PreferenceUtil.getName();
				data.Note = note;
				data.ImgStr = imgStr;
				data.ProjcLat = mProjcLat;
				data.ProjcLng = mProjcLng;
				data.Province = PreferenceUtil.getProvince();
				data.BuildNum = mBuildNum;
				data.PlaceType = mPlaceType;
				data.CameraId = mCameraId;
				data.Type = mType;
				data.Progress = mProgress;
				data.PositionType = mCameraType;
				data.NormRules = mDowmDirNormRule;

				String UUID = getDeviceUuid(TakePhotoActivity.this);
				NetworkApi.sendData(TakePhotoActivity.this, UUID, data, new INetCallback() {
					@Override
					public void onCallback(boolean value, String result) {
						LogUtil.logD("sendData onCallback" + result);
						if (value) {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									mPhotoImage.setImageBitmap(null);
									/*
									 * mShowPhotoView.setVisibility(View.GONE);
									 * mTakePhotoView.setVisibility(View.VISIBLE
									 * );
									 */
									Intent intent = new Intent(TakePhotoActivity.this, TakePhotoActivity.class);
									intent.putExtra("title", getIntent().getStringExtra("title"));
									intent.putExtra("projectId", getIntent().getStringExtra("projectId"));
									intent.putExtra("projectName", getIntent().getStringExtra("projectName"));
									startActivity(intent);
									finish();
									ToastHelp.showToast(TakePhotoActivity.this, "发送成功！");
								}
							});
						} else {
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									ToastHelp.showToast(TakePhotoActivity.this, "发送失败！");
								}
							});
						}
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								dialog.cancel();
							}
						});
					}
				});
			}
		}).start();
	}

	private void takePhoto() {
		getLocalPosition();
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			ToastHelp.showToast(this, "手机没有安装SD卡，不能拍照");
			return;
		}
		if (mProjectName.length() <= 0) {
			ToastHelp.showToast(this, "项目名称为空！");
			return;
		}
		if (mNoteEdit.getText().toString().length() <= 0) {
			ToastHelp.showToast(this, "现场描述为空！");
			return;
		}
		if (mBuildNum.equals("其他") || mBuildNum.equals("栋号")) {
			if (mEditBuildNum.getText().toString().length() <= 0) {
				ToastHelp.showToast(this, "抓拍地点！");
				return;
			}

		}
		if (mCameraId.equals("其他") || mCameraId.equals("层数")) {
			if (mEditCameraId.getText().toString().length() <= 0) {
				ToastHelp.showToast(this, "具体部位！");
				return;
			}

		}
		if (mCameraType.equals("其他")) {
			if (mEditCameraType.getText().toString().length() <= 0) {
				ToastHelp.showToast(this, "部位类型不能为空！");
				return;
			}
		}
		if (mNoteEdit.getText().toString().length() > 20) {
			ToastHelp.showToast(this, "现场描述字数超过20个！");
			return;
		}

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String dirName = Environment.getExternalStorageDirectory() + "/huixin";
		File f = new File(dirName);
		if (!f.exists()) {
			f.mkdir();
		}
		String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance().getTime()) + ".jpg";
		mPhotoUri = dirName + "/" + name;
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(dirName, name)));
		startActivityForResult(intent, 0);
	}

	private void getLocalPosition() {
		mProjcLat = PreferenceUtil.getProjectLat();
		mProjcLng = PreferenceUtil.getProjectLng();
		String addr = PreferenceUtil.getProjectAddrStr();
		mTextViewLocalPostion.setText("经度：" + mProjcLng + " 纬度：" + mProjcLat + "\n地址：" + addr);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == 0x125) {
			mNormRules = data.getStringExtra("NormRule");
			mTextViewNormRule.setText(mNormRules);
			// mBtnProgress.setVisibility(View.GONE);
			mRendPhoto.setVisibility(View.VISIBLE);
			mBtnProgress.setText("请重选规范条例");
			mDirNormRules = Integer.parseInt(data.getStringExtra("DirNormRules"));

			mDowmDirNormRule = mDirNormRules;
			return;
		}
		if (requestCode == 0x258) {
			return;
		}

		if (resultCode == 0x123) {
			if (mAlertDialog != null) {
				mAlertDialog.cancel();
			}
			mDowmDirNormRule = data.getIntExtra("id", -1);

			mTextViewNormRule.setText(data.getStringExtra("text"));
			mRendPhoto.setVisibility(View.VISIBLE);
			// 防止继续执行
			return;
		}

		if (resultCode == Activity.RESULT_OK) {
			if (!(new File(mPhotoUri)).exists()) {
				Log.e("onActivityResult", "mPhotoUri not exists!");
				ToastHelp.showToast(this, "拍照失败！");
				return;
			}
			try {
				mShowPhotoView.setVisibility(View.VISIBLE);
				mTakePhotoView.setVisibility(View.GONE);
				// mShowPhotoView.scrollTo(0,
				// mTakePhotoLayout.getMeasuredHeight() -
				// mShowPhotoView.getHeight());
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						mShowPhotoView.fullScroll(ScrollView.FOCUS_DOWN);
					}
				});

				if (mProgress.equals("不符合")) {
					mRendPhoto.setVisibility(View.GONE);
					mBtnProgress.setVisibility(View.VISIBLE);
				}
				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.ARGB_8888)
						.cacheInMemory(false).cacheOnDisc(true).build();

				ImageLoader.getInstance().displayImage("file://" + mPhotoUri, mPhotoImage, options);
				if (mBuildNum.equals("其他")) {
					mBuildNum = "其他|" + mEditBuildNum.getText().toString();
				} else if (mBuildNum.equals("栋号")) {
					mBuildNum = "栋号|" + mEditBuildNum.getText().toString().replaceFirst("^0*", "") + "栋";
				} else {
					// mBuildNum += "|";
				}
				mTextViewBuildNum.setText(mBuildNum);
				if (mCameraId.equals("其他")) {
					mCameraId = "其他|" + mEditCameraId.getText().toString();
				} else if (mCameraId.equals("层数")) {
					mCameraId = "层数|" + mEditCameraId.getText().toString().replaceFirst("^0*", "") + "层";
				} else {
					// mCameraId += "|";
				}
				mTextViewCameraId.setText(mCameraId);
				if (mCameraType.equals("其他")) {
					mCameraType = "其他|" + mEditCameraType.getText().toString();
				} else {
					mCameraType += "|";
				}
				mTextViewCameraType.setText(mCameraType);
				mTextViewPlaceType.setText(mPlaceType);
				mTextViewType.setText(mType);
				mTextViewProgress.setText(mProgress);
				mTextViewProjectNote.setText(mNoteEdit.getText().toString());
				mTextViewNormRule.setText(mNormRules);
				SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
				String time = df.format(new Date());
				mTextViewTextTime.setText(time);
			} catch (Exception e) {
				e.printStackTrace();
				ToastHelp.showToast(this, "拍照失败，请重新拍照！");
			}
		} else {
			ToastHelp.showToast(this, "拍照失败，请重新拍照！");
		}
	}

}
