package com.king.photo.adapter;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import org.json.JSONException;
import org.json.JSONObject;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.INetCallback;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.PreferenceUtil;
import com.hc.android.huixin.view.CustomDialog;
import com.hc.android.huixin.view.LoadingDialog;
import com.king.photo.activity.TowerCraneMainActivity;
import com.king.photo.adapter.SettingBaseadapter1.ViewHolder;
import com.king.photo.model.TowerCraneManagerModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import uk.co.senab.photoview.PhotoView;

public class TowerCraneManagerBaseadapter extends BaseAdapter {

	Context context;
	ArrayList<TowerCraneManagerModel> list;

	protected ImageLoader imageLoader;

	DisplayImageOptions options;
	Handler mHandler;
	LoadingDialog loadingDialog = null;

	public TowerCraneManagerBaseadapter(Context context, Handler Handler, ArrayList<TowerCraneManagerModel> list) {
		this.context = context;
		this.list = list;
		this.mHandler = Handler;
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon).showImageForEmptyUri(R.drawable.icon)
				.showImageOnFail(R.drawable.icon).cacheInMemory(true).cacheOnDisc(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class ViewHolder {
		LinearLayout mLinearLayout;
		TextView mName;
		TextView mStatus;
		TextView mTime;
		ImageView mImageView1;
		ImageView mImageView2;
		ImageView mImageView3;
		ImageView mImageView4;
		Button mBtnPass;
		Button mBtnDenied;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		// if (convertView == null) {
		viewHolder = new ViewHolder();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.item_tower_crane_manager, null);
		viewHolder.mLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll);

		viewHolder.mName = (TextView) convertView.findViewById(R.id.item_tower_crane_name);
		viewHolder.mStatus = (TextView) convertView.findViewById(R.id.item_tower_crane_status);
		viewHolder.mImageView1 = (ImageView) convertView.findViewById(R.id.item_image_1);
		viewHolder.mImageView2 = (ImageView) convertView.findViewById(R.id.item_image_2);
		viewHolder.mImageView3 = (ImageView) convertView.findViewById(R.id.item_image_3);
		viewHolder.mImageView4 = (ImageView) convertView.findViewById(R.id.item_image_4);
		viewHolder.mTime = (TextView) convertView.findViewById(R.id.item_tower_crane_time);

		viewHolder.mBtnPass = (Button) convertView.findViewById(R.id.btn_pass);
		viewHolder.mBtnDenied = (Button) convertView.findViewById(R.id.btn_denied);

		viewHolder.mName.setText(list.get(position).getUserName());
	
		viewHolder.mTime.setText(list.get(position).getTrCreateTime());
		switch (Integer.parseInt(list.get(position).getTrStatus())) {
		case 0:
			viewHolder.mStatus.setText("待审核");// 待审核
			viewHolder.mStatus.setTextColor(Color.parseColor("#fa9d4c"));
			viewHolder.mBtnPass.setVisibility(View.VISIBLE);
			viewHolder.mBtnDenied.setVisibility(View.VISIBLE);
			break;
		case 1:
			viewHolder.mStatus.setText("已审核");// 审核通过，开锁成功K
			viewHolder.mStatus.setTextColor(Color.parseColor("#398de3"));
			viewHolder.mBtnPass.setVisibility(View.GONE);
			viewHolder.mBtnDenied.setVisibility(View.GONE);
			break;
		case 2:
			viewHolder.mStatus.setText("已审核");// 审核通过，开锁失败
			viewHolder.mStatus.setTextColor(Color.parseColor("#398de3"));
			viewHolder.mBtnPass.setVisibility(View.GONE);
			viewHolder.mBtnDenied.setVisibility(View.GONE);
			break;
		case 3:
			viewHolder.mStatus.setText("审核不通过");// 审核不通过
			viewHolder.mStatus.setTextColor(Color.parseColor("#dc5562"));
			viewHolder.mBtnPass.setVisibility(View.VISIBLE);
			viewHolder.mBtnDenied.setVisibility(View.VISIBLE);
			break;
		case 4:
			viewHolder.mStatus.setText("已关闭");// 在审核之前验证成功直接关闭
			viewHolder.mStatus.setTextColor(Color.parseColor("#dc5562"));
			viewHolder.mBtnPass.setVisibility(View.GONE);
			viewHolder.mBtnDenied.setVisibility(View.GONE);
			break;
		default:
			break;
		}
		if (!"".equals(list.get(position).getTrFailPaths())) {

			list.get(position).getTrFailPaths().lastIndexOf(";");

			String imageUrl = list.get(position).getTrFailPaths().substring(0,
					list.get(position).getTrFailPaths().lastIndexOf(";"));

			String[] UrlArr = imageUrl.split(";");

			imageLoader.displayImage(UrlArr[0].replace(" ", ""), viewHolder.mImageView1);
			imageLoader.displayImage(UrlArr[1].replace(" ", ""), viewHolder.mImageView2);
			imageLoader.displayImage(UrlArr[2].replace(" ", ""), viewHolder.mImageView3);
		}
		if (!"".equals(list.get(position).getTrSuccessPath())) {
			viewHolder.mImageView4.setVisibility(View.VISIBLE);
			imageLoader.displayImage(list.get(position).getTrSuccessPath(), viewHolder.mImageView4);
		} else {
			viewHolder.mImageView4.setVisibility(View.GONE);
		}

		// convertView.setTag(viewHolder);
		// } else {
		// viewHolder = (ViewHolder) convertView.getTag();
		//
		// }
		viewHolder.mBtnPass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				checkTypeForAdmin(position, "1");
			}
		});

		viewHolder.mBtnDenied.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * new
				 * verifiyForTowerCraneForAsy().execute(PreferenceUtil.getName(
				 * context), list.get(position).getTowerRecordID(), "0");
				 */

				checkTypeForAdmin(position, "0");

			}

		});

		return convertView;

	}

	public void showDialog(String text) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(text);
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
			}
		});

		builder.create().show();
	}

	private void checkTypeForAdmin(final int position, final String type) {
		loadingDialog = new LoadingDialog(context);
		loadingDialog.setMessage("正在发送中...").show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				NetworkApi.verifiyForTowerCrane(PreferenceUtil.getName(), list.get(position).getTowerRecordID(),
						type, new INetCallback() {

							@Override
							public void onCallback(boolean value, String result) {
								// TODO Auto-generated method stub
								if (value) {
									try {

										list.get(position).setTrStatus(new JSONObject(result).getString("data"));

										mHandler.post(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												notifyDataSetChanged();
											}
										});

									} catch (JSONException e) {
										// TODO Auto-generated catch
										// block
										
										e.printStackTrace();
										mHandler.post(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated
												// method stub
												showDialog("审核失败");
											}
										});
									}

								} else {
									mHandler.post(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated
											// method stub

											showDialog("审核失败");
										}
									});
								}
								mHandler.post(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method
										// stub
										loadingDialog.cancel();

									}
								});

							}
						});
			}
		}).start();
	}

}
