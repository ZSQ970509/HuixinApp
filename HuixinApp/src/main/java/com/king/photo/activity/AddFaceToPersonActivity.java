//package com.king.photo.activity;
//
//import java.lang.reflect.Array;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.TimeZone;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import com.fisc.facesignsdk.error.FacesignParseException;
//import com.fisc.facesignsdk.http.HttpRequests;
//import com.fisc.facesignsdk.http.PostParameters;
//import com.hc.android.huixin.R;
//import com.hc.android.huixin.network.FaceAddModel;
//import com.hc.android.huixin.network.HttpUtil;
//import com.hc.android.huixin.network.NetworkApi;
//import com.hc.android.huixin.util.DialogUtil;
//import com.hc.android.huixin.util.ImageUtil;
//import com.hc.android.huixin.util.PreferenceUtil;
//import com.hc.android.huixin.util.ToastHelp;
//import com.king.photo.util.Bimp;
//import com.king.photo.util.FileUtils;
//import com.king.photo.util.ImageItem;
//import com.onesafe.util.AssetUtil;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//
//public class AddFaceToPersonActivity extends Activity {
//	public static Bitmap bimap;
//	private GridView noScrollgridview;
//	private View parentView;
//	private GridAdapter adapter;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		bimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
//		parentView = getLayoutInflater().inflate(R.layout.activity_facesign_addperson, null);
//		setContentView(parentView);
//
//		Init();
//	}
//
//	private void Init() {
//		// TODO Auto-generated method stub
//		// String title = getIntent().getStringExtra("title");
//		// TextView titleView = (TextView) findViewById(R.id.title);
//		// titleView.setText(title);
//		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
//
//		findViewById(R.id.activity_selectimg_send).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (!HttpUtil.networkIsAvailable(AddFaceToPersonActivity.this)) {
//					ToastHelp.showCurrentToast(AddFaceToPersonActivity.this, "手机没有网络连接！");
//					return;
//				}
//				uploadImage();
//			}
//		});
//
//		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
//		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
//		adapter = new GridAdapter(this);
//		adapter.update();
//		noScrollgridview.setAdapter(adapter);
//		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {
//
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				if (arg2 == Bimp.tempSelectBitmap.size()) {
//					// pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
//					photo();
//				} else {
//					Intent intent = new Intent(AddFaceToPersonActivity.this, GalleryActivity.class);
//					intent.putExtra("position", "1");
//					intent.putExtra("ID", arg2);
//					startActivity(intent);
//				}
//			}
//		});
//
//	}
//
//	private static final int TAKE_PICTURE = 0x000002;
//
//	public void photo() {
//		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		startActivityForResult(openCameraIntent, TAKE_PICTURE);
//	}
//
//	@SuppressLint("HandlerLeak")
//	public class GridAdapter extends BaseAdapter {
//		private LayoutInflater inflater;
//		private int selectedPosition = -1;
//		private boolean shape;
//
//		public boolean isShape() {
//			return shape;
//		}
//
//		public void setShape(boolean shape) {
//			this.shape = shape;
//		}
//
//		public GridAdapter(Context context) {
//			inflater = LayoutInflater.from(context);
//		}
//
//		public void update() {
//			loading();
//		}
//
//		public int getCount() {
//			// if (Bimp.tempSelectBitmap.size() == 9) {
//			// return 9;
//			// }
//			return (Bimp.tempSelectBitmap.size() + 1);
//		}
//
//		public Object getItem(int arg0) {
//			return null;
//		}
//
//		public long getItemId(int arg0) {
//			return 0;
//		}
//
//		public void setSelectedPosition(int position) {
//			selectedPosition = position;
//		}
//
//		public int getSelectedPosition() {
//			return selectedPosition;
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder holder = null;
//			if (convertView == null) {
//				convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
//				holder = new ViewHolder();
//				holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//
//			if (position == Bimp.tempSelectBitmap.size()) {
//				holder.image
//						.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
//				// if (position == 9) {
//				// holder.image.setVisibility(View.GONE);
//				// }
//			} else {
//				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
//			}
//
//			return convertView;
//		}
//
//		public class ViewHolder {
//			public ImageView image;
//		}
//
//		Handler handler = new Handler() {
//			public void handleMessage(Message msg) {
//				switch (msg.what) {
//				case 1:
//					adapter.notifyDataSetChanged();
//					break;
//				}
//				super.handleMessage(msg);
//			}
//		};
//
//		public void loading() {
//			new Thread(new Runnable() {
//				public void run() {
//					while (true) {
//						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
//							Message message = new Message();
//							message.what = 1;
//							handler.sendMessage(message);
//							break;
//						} else {
//							Bimp.max += 1;
//							Message message = new Message();
//							message.what = 1;
//							handler.sendMessage(message);
//						}
//					}
//				}
//			}).start();
//		}
//	}
//
//	private Handler mHandler = new Handler();
//
//	private void uploadImage() {
//
//		final ProgressDialog dialog = DialogUtil.createProgressDialog(this, "正在录入人脸...");
//		dialog.show();
//
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					ArrayList<FaceAddModel> personAl = new ArrayList<FaceAddModel>();
//					// person生成 必须person跟face一起生成。
//					String name = PreferenceUtil.getName(AddFaceToPersonActivity.this);
//
//					boolean isTrue = false;
//
//					isTrue = GetMyPerson(name);
//
//					// 生成一组faceId 不管是创建还是新增都需要。
//					String[] faceid = new String[Bimp.tempSelectBitmap.size()];
//					int i = 0;
//					for (ImageItem item : Bimp.tempSelectBitmap) {
//						Bitmap bitmap = item.getBitmap();
//						if (bitmap == null) {
//							mHandler.post(new Runnable() {
//								@Override
//								public void run() {
//									// dialog.cancel();
//									ToastHelp.showCurrentToast(AddFaceToPersonActivity.this, "录入人脸失败！");
//									dialog.cancel();
//								}
//							});
//							return;
//						}
//						// System.out.println(name);
//						HttpRequests request = new HttpRequests(true);
//						PostParameters param = new PostParameters();
//						param.setImg(AssetUtil.Bitmap2Bytes(bitmap));
//						param.setMode("normal");
//						param.setAttribute("none");
//						param.setTag(name);
//						param.setAsync(false);
//						JSONObject rtn;
//
//						rtn = request.detectionDetect(param);
//						System.out.println("JSONFACE:" + rtn.toString());
//
//						if ("[]".equals(rtn.getString("face"))) {
//							mHandler.post(new Runnable() {
//								@Override
//								public void run() {
//
//									ToastHelp.showCurrentToast(AddFaceToPersonActivity.this, "有一张照片无法识别,请对准人脸进行拍照！");
//									dialog.cancel();
//
//								}
//							});
//							return;
//						}
//
//						String Faceid = rtn.getJSONArray("face").getJSONObject(0).getString("face_id");
//						String ImgId = rtn.getString("img_id");
//
//						FaceAddModel faceAddModel = new FaceAddModel();
//						faceAddModel.setAccount(name);
//						faceAddModel.setImgStr(ImageUtil.convertIconToString(bitmap));
//						faceAddModel.setFaceid(Faceid);
//						faceAddModel.setImgid(ImgId);
//
//						personAl.add(faceAddModel);
//
//						faceid[i] = Faceid;
//						i++;
//					}
//					String groupIds = "";
//
//					for (int j = 0; j < faceid.length; j++) {
//						if (TextUtils.isEmpty(groupIds)) {
//							groupIds = faceid[j];
//						} else {
//							groupIds += "," + faceid[j];
//						}
//					}
//					if (isTrue) {
//						// 如果已经创建了person，就返回。不允许创建
//						HttpRequests request = new HttpRequests(true);
//						PostParameters params = new PostParameters();
//						params.setPersonName(name);
//						params.setFaceId(groupIds);
//						// params.setGroupName(groupNames);
//						final JSONObject rtn;
//						rtn = request.personAddFace(params);
//						System.out.println(rtn.toString());
//						if (rtn.getString("success").equals("false")) {
//							mHandler.post(new Runnable() {
//								@Override
//								public void run() {
//
//									ToastHelp.showCurrentToast(AddFaceToPersonActivity.this, "录入人脸失败！");
//									dialog.cancel();
//								}
//							});
//							return;
//						}
//					} else {
//						HttpRequests request = new HttpRequests(true);
//						PostParameters params = new PostParameters();
//						params.setPersonName(name);
//						params.setTag(name);
//						params.setFaceId(groupIds);
//						// params.setGroupName(groupNames);
//						final JSONObject rtn;
//						rtn = request.personCreate(params);
//						System.out.println(rtn.toString());
//						// 可能是已经录入过了
//						if (rtn.getString("added_face").equals("0")) {
//							mHandler.post(new Runnable() {
//								@Override
//								public void run() {
//									ToastHelp.showCurrentToast(AddFaceToPersonActivity.this, "录入人脸失败！");
//									dialog.cancel();
//								}
//							});
//							return;
//						}
//					}
//					// 训练
//					HttpRequests request = new HttpRequests(true);
//					PostParameters paramTrain = new PostParameters();
//					paramTrain.setPersonName(PreferenceUtil.getName(AddFaceToPersonActivity.this));
//					JSONObject rtnPersonTrain;
//					rtnPersonTrain = request.trainVerify(paramTrain);
//					System.out.println(rtnPersonTrain.toString());
//					if (!"200".equals(rtnPersonTrain.getString("response_code"))) {
//						mHandler.post(new Runnable() {
//							@Override
//							public void run() {
//								ToastHelp.showCurrentToast(AddFaceToPersonActivity.this, "录入人脸失败！");
//								dialog.cancel();
//							}
//						});
//						return;
//					}
//
//					mHandler.post(new Runnable() {
//						@Override
//						public void run() {
//							// dialog.cancel();
//							Bimp.tempSelectBitmap.clear();
//							Bimp.max = 0;
//							AlbumActivity.bitmap = null;
//							AlbumActivity.contentList = null;
//							ShowAllPhoto.dataList.clear();
//							adapter.notifyDataSetChanged();
//							dialog.cancel();
//							ToastHelp.showCurrentToast(AddFaceToPersonActivity.this, "录入完成！");
//						}
//					});
//
//					new NetworkApi().sendFaceAddToPersonMsg(personAl);
//
//				} catch (Exception e) {
//					mHandler.post(new Runnable() {
//						@Override
//						public void run() {
//
//							ToastHelp.showCurrentToast(AddFaceToPersonActivity.this, "录入人脸失败！");
//							dialog.cancel();
//
//						}
//					});
//					e.printStackTrace();
//					return;
//				}
//			}
//
//		}).start();
//	}
//
//	public boolean GetMyPerson(String name) throws Exception {
//		boolean isTrue = false;
//
//		HttpRequests request = new HttpRequests(true);
//		// PostParameters params = new PostParameters();
//		final JSONObject rtn;
//		rtn = request.infoGetPersonList();
//		System.out.println(rtn.toString());
//		if ("200".equals(rtn.getString("response_code"))) {
//
//			JSONArray json = rtn.getJSONArray("person");
//			for (int i = 0; i < json.length(); i++) {
//				JSONObject item = json.getJSONObject(i);
//
//				if (name.equals(item.getString("person_name"))) {
//					isTrue = true;
//
//					break;
//				}
//			}
//		}
//
//		return isTrue;
//	}
//
//	@Override
//	protected void onRestart() {
//		adapter.update();
//		super.onRestart();
//	}
//
//	@Override
//	protected void onDestroy() {
//		Bimp.tempSelectBitmap.clear();
//		Bimp.max = 0;
//		AlbumActivity.bitmap = null;
//		AlbumActivity.contentList = null;
//		ShowAllPhoto.dataList.clear();
//		super.onDestroy();
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch (requestCode) {
//		case TAKE_PICTURE:
//			// if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK)
//			// {
//			if (resultCode == RESULT_OK) {
//
//				String fileName = String.valueOf(System.currentTimeMillis());
//				Bitmap bm = (Bitmap) data.getExtras().get("data");
//
//				FileUtils.saveBitmap(bm, fileName);
//
//				ImageItem takePhoto = new ImageItem();
//				takePhoto.setBitmap(bm);
//
//				Bimp.tempSelectBitmap.add(takePhoto);
//
//			}
//			break;
//
//		}
//
//	}
//
//}
