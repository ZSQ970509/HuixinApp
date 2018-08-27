package com.king.photo.activity;

import java.util.ArrayList;
import java.util.List;

import com.hc.android.huixin.AttendancePathProjectHumenActivity;
import com.hc.android.huixin.DisassembleSelectProActivity;
import com.hc.android.huixin.MyApplication;
import com.hc.android.huixin.ProjectAttendanceActivity;
import com.hc.android.huixin.MaintenanceSelectProActivity;
import com.hc.android.huixin.R;
import com.hc.android.huixin.TakePhoto2Activity;
import com.hc.android.huixin.TakePhotoInstallOpenSelectProActivity;
import com.hc.android.huixin.network.ConstrucTypeItem;
import com.hc.android.huixin.network.ModuleItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.DialogUtil;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.view.LineGridView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TakePhoto2MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo2main);
		initView();
	}

	private void initView() {
		String title = getIntent().getStringExtra("title");
		TextView titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		new ProjectTypeAsyncTask().execute();
	}

	private class ProjectTypeAsyncTask extends AsyncTask<Void, Void, ArrayList<ConstrucTypeItem>> {

		@Override
		protected ArrayList<ConstrucTypeItem> doInBackground(Void... params) {
			return new NetworkApi().GetConstrucType();
		}

		@Override
		protected void onPostExecute(final ArrayList<ConstrucTypeItem> data) {
			if (data == null) {
				return;
			}

			// ConstrucTypeItem itemTemp = new ConstrucTypeItem();
			// itemTemp.id=371;
			// itemTemp.text="掉电设备查询";
			// data.add(itemTemp);
			//
			// ConstrucTypeItem itemTemp2 = new ConstrucTypeItem();
			// itemTemp2.id=372;
			// itemTemp2.text="塔吊控制操作";
			// data.add(itemTemp2);

			LineGridView grid = (LineGridView) findViewById(R.id.moduleGrid);
			ArrayList<ModuleItem> moduleList = new ArrayList<ModuleItem>();
			String[] nameData = new String[data.size()];
			for (int i = 0; i < nameData.length; i++) {
				nameData[i] = data.get(i).text;

				ModuleItem item = new ModuleItem();
				switch (data.get(i).id) {
				case 230://工程人员考勤
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.icon_work_attendance;
				/*	Log.e("111",MyApplication.isAttendancePath+"");
					if(MyApplication.isAttendancePath){
						item.jumpActivity = AttendancePathActivity.class;
					}else{
						item.jumpActivity = ProjectAttendanceActivity.class;
					}*/
					 //item.jumpActivity = TakePhoto2Activity.class;
					moduleList.add(item);
					break;
				case 191://交底 停用隐藏掉
//					item.id = data.get(i).id;
//					item.name = data.get(i).text;
//					item.imageId = R.drawable.icon_clarification;
//					item.jumpActivity = TakePhoto2Activity.class;
//					moduleList.add(item);
					//设备移机
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.icon_remove_device;
//					item.jumpActivity = TakePhotoRemoveDeviceActivity.class;
					item.jumpActivity = TakePhotoRemoveDeviceSelectProActivity.class;
					moduleList.add(item);
					break;
				case 168://"安装开通"名字改成了"安装接入"
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.icon_installation_open;
//					item.jumpActivity = TakePhotoInstallOpenActivity.class;
					item.jumpActivity = TakePhotoInstallOpenSelectProActivity.class;
					//item.jumpActivity = TakePhoto2Activity.class;
					moduleList.add(item);
					
					break;
				case 171://拆机
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.icon_take_apart;
					//item.jumpActivity = TakePhoto2Activity.class;
//					item.jumpActivity = DisassembleActivityOld.class;
					item.jumpActivity = DisassembleSelectProActivity.class;
					moduleList.add(item);
					break;
				case 169://维护
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.icon_maintenance;
					item.jumpActivity = MaintenanceSelectProActivity.class;
					//item.jumpActivity = MaintenanceActivity.class;
//					item.jumpActivity = TakePhoto2Activity.class;
					moduleList.add(item);
					break;
				case 366:// IP/ID地址查询
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.icon_ipid;
					item.jumpActivity = IPIDSearchActivity.class;
					moduleList.add(item);
					break;
//				case 370://人脸识别测试
//					item.id = data.get(i).id;
//					item.name = data.get(i).text;
//					item.imageId = R.drawable.icon_face_verify;
//					item.jumpActivity = FaceSignVerifyActivity.class;
//					moduleList.add(item);
//					break;
				case 433://断电设备查询
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.icon_power_fail;
					item.jumpActivity = PowerFailDeviceSearchActivity.class;
					moduleList.add(item);
					break;
				case 432://塔吊控制操作
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.icon_face_verify;
					item.jumpActivity = TowerCraneMainActivity.class;
					moduleList.add(item);
					break;
				case 434://掉电设备绑定
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.icon_power_fail;
					item.jumpActivity = PowerFailDeviceBindSearchActivity.class;
					moduleList.add(item);
					break;
				case 438:
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.driverbind;
//					item.jumpActivity = LadderControlDeviceSearchOld.class;
					item.jumpActivity = LadderControlDeviceSelectProActivity.class;
					moduleList.add(item);
					break;
				case 439:
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.driverdebind;
					item.jumpActivity = LadderControlDeviceDeBindSearch.class;
					moduleList.add(item);
					break;
				case 440:
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.inserthumen;
					item.jumpActivity = LadderControlAddHumen.class;
					moduleList.add(item);
					break;
				/*case 441:
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.benchmarkbinding;
					item.jumpActivity = DatBasicLevelSearch.class;
					moduleList.add(item);
					break;*/
				/*case 441:
					item.id = data.get(i).id;
					item.name = data.get(i).text;
					item.imageId = R.drawable.benchmarkbinding;
					item.jumpActivity = DatBasicLevelSearch.class;
					moduleList.add(item);
					break;*/
				default:
					// item.id = data.get(i).id;
					// item.name = data.get(i).text;
					// item.imageId = R.drawable.btn_photo_history;
					// item.jumpActivity = TakePhoto2Activity.class;
					// moduleList.add(item);
					break;
				}
			}

			grid.setAdapter(new MyAdapter(moduleList));

		}
	}

	private class MyAdapter extends BaseAdapter {

		private ArrayList<ModuleItem> moduleList = new ArrayList<ModuleItem>();

		public MyAdapter(List<ModuleItem> list) {
			if (list != null && list.size() > 0) {
				moduleList.clear();
				moduleList.addAll(list);
			}
		}

		@Override
		public int getCount() {
			return moduleList.size();
		}

		@Override
		public Object getItem(int position) {
			return moduleList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_item, null);
			final ModuleItem item = moduleList.get(position);
			((ImageView) itemView.findViewById(R.id.module_img)).setBackgroundResource(item.imageId);
			((TextView) itemView.findViewById(R.id.module_text)).setText(item.name);
			itemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					if(item.id == 230){
						if(!PreferenceUtil.getAttendandcePath()) {
							if (PreferenceUtil.getAttendandcePathProjectHumen() ) {
								item.jumpActivity = AttendancePathProjectHumenActivity.class;
							} else {
								item.jumpActivity = ProjectAttendanceActivity.class;
							}
						}else{
							DialogUtil.showDialog(TakePhoto2MainActivity.this,"外勤人员考勤路径已开启，请先结束考勤！");
							return;
						}
					}
					if (item.jumpActivity == null) {

						return;
					}
					jumpActivity(item.jumpActivity, item.name, item.id);
				}
			});
			return itemView;
		}

	}

	private void jumpActivity(Class<?> cls, String title, int ProjectTypeId) {
		Intent intent = new Intent(this, cls);
		intent.putExtra("ProjectType", title);
		intent.putExtra("title", title);
		intent.putExtra("ProjectTypeId", ProjectTypeId);
		startActivity(intent);
	}

}
