//package com.king.photo.activity;
//
//import java.io.File;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.TimeZone;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.fisc.facesignsdk.error.FacesignParseException;
//import com.fisc.facesignsdk.http.HttpRequests;
//import com.fisc.facesignsdk.http.PostParameters;
//import com.github.nkzawa.emitter.Emitter;
//import com.github.nkzawa.socketio.client.IO;
//import com.github.nkzawa.socketio.client.Socket;
//
//import com.hc.android.huixin.R;
//import com.hc.android.huixin.network.FaceMSGModel;
//import com.hc.android.huixin.network.FaceModel;
//import com.hc.android.huixin.network.HttpUtil;
//import com.hc.android.huixin.network.INetCallback;
//import com.hc.android.huixin.network.NetworkApi;
//import com.hc.android.huixin.util.DialogUtil;
//import com.hc.android.huixin.util.ImageUtil;
//import com.hc.android.huixin.util.PreferenceUtil;
//import com.hc.android.huixin.util.ToastHelp;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.onesafe.util.AssetUtil;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.provider.MediaStore;
//import android.provider.Settings;
//import android.text.TextUtils;
//import android.text.format.DateFormat;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class FaceSignVerifyActivity extends Activity {
//	Socket socket;
//	Button mBtnVerify;
//	private String mPhotoUri;
//	private Handler mHandler = new Handler();
//	private ImageView mPhotoImage;
//	FaceModel model = null;
//
//	int flag = 0;
//	TextView mTextValue1, mTextValue2, mTextValue3, mTextValue4, mTextValue5;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_face_sign_verify);
//
//		findViewById(R.id.regulatory_back).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
//
//		mTextValue1 = (TextView) findViewById(R.id.txt_value1);
//		mTextValue2 = (TextView) findViewById(R.id.txt_value2);
//		mTextValue3 = (TextView) findViewById(R.id.txt_value3);
//		mTextValue4 = (TextView) findViewById(R.id.txt_value4);
//		mTextValue5 = (TextView) findViewById(R.id.txt_value5);
//
//		new GetPersonIsExist().execute(PreferenceUtil.getName(FaceSignVerifyActivity.this));
//		new GetMSGByName().execute(PreferenceUtil.getName(FaceSignVerifyActivity.this));
//
//		mPhotoImage = (ImageView) findViewById(R.id.photo_image);
//
//		mBtnVerify = (Button) findViewById(R.id.btn_verify);
//		mBtnVerify.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				takePhoto();
//			}
//		});
//
//	}
//
//	// 本人为true 不是 Flase
//	public boolean GetMyPerson(String name) throws Exception {
//		boolean isTrue = false;
//
//		HttpRequests request = new HttpRequests(true);
//		// PostParameters params = new PostParameters();
//		final JSONObject rtn;
//		rtn = request.infoGetPersonList();
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
//		return isTrue;
//	}
//
//	private void takePhoto() {
//
//		// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		// String dirName = Environment.getExternalStorageDirectory() +
//		// "/huixin";
//		// File f = new File(dirName);
//		// if (!f.exists()) {
//		// f.mkdir();
//		// }
//		// String name = DateFormat.format("yyyyMMdd_hhmmss",
//		// Calendar.getInstance().getTime()) + ".jpg";
//		// mPhotoUri = dirName + "/" + name;
//		// File file = new File(dirName, name);
//		// Log.d("TAG", "mPhotoUri:=" + file.toString());
//		//
//		// intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//		// startActivityForResult(intent, 0);
//
//		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		intent.putExtra("camerasensortype", 1); // 调用前置摄像头
//		intent.putExtra("autofocus", true); // 自动对焦
//		intent.putExtra("fullScreen", false); // 全屏
//		intent.putExtra("showActionIcons", false);
//		startActivityForResult(intent, 0);
//	}
//
//	private void Distinguish(final Bitmap photo) {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//
//					Boolean isTrueSelf = GetMyPerson(PreferenceUtil.getName(FaceSignVerifyActivity.this));
//					if (!isTrueSelf) {
//
//						isTrueSelf = false;
//						mHandler.post(new Runnable() {
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								new AlertDialog.Builder(FaceSignVerifyActivity.this).setTitle("提示")
//										.setMessage("您还没录入过人脸，请先录入人脸再进行测试，是否跳转到录入界面？")
//										.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//											@Override
//											public void onClick(DialogInterface dialog, int which) {
//												dialog.cancel();
//												Intent intent = new Intent(FaceSignVerifyActivity.this,
//														AddFaceToPersonActivity.class);
//												FaceSignVerifyActivity.this.startActivity(intent);
//											}
//										}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//											@Override
//											public void onClick(DialogInterface dialog, int which) {
//												dialog.cancel();
//											}
//										}).setCancelable(false).create().show();
//
//							}
//						});
//						return;
//					}
//
//					HttpRequests request = new HttpRequests(true);
//					PostParameters param = new PostParameters();
//
//					// param.setImg(AssetUtil.InputStreamToByte(new
//					// FileInputStream(file)));
//					model = new FaceModel();
//
//					param.setImg(AssetUtil.Bitmap2Bytes(photo));
//					param.setMode("normal");
//					param.setAttribute("none");
//					param.setTag(PreferenceUtil.getName(FaceSignVerifyActivity.this));
//					param.setAsync(false);
//					JSONObject rtn = request.detectionDetect(param);
//					System.out.println("JSON:" + rtn.toString());
//
//					model.setAccount(PreferenceUtil.getName(FaceSignVerifyActivity.this));
//					model.setImgstr(ImageUtil.convertIconToString(photo));
//					model.setDetectionDetectJson(rtn.toString());
//
//					if ("[]".equals(rtn.getString("face"))) {
//
//						model.setValue("3");
//						model.setDescribe("无法生成人脸ID，无法识别。");
//						// 发送
//						SendPost();
//
//						mHandler.post(new Runnable() {
//
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								ToastHelp.showCurrentToast(FaceSignVerifyActivity.this, "没有识别到人脸 ");
//							}
//						});
//						return;
//					}
//					String FaceId = rtn.getJSONArray("face").getJSONObject(0).getString("face_id");
//					PostParameters params = new PostParameters();
//					params.setPersonName(PreferenceUtil.getName(FaceSignVerifyActivity.this));
//					params.setFaceId(FaceId);
//					final JSONObject rtnPerson;
//					rtnPerson = request.recognitionVerify(params);
//				
//					System.out.println("JSON:" + rtnPerson.toString());
//
//					model.setRecognitionVerifyJson(rtnPerson.toString());
//
//					if (rtnPerson.getString("is_same_person").equals("true")) {
//						System.out.println("是本人");
//						model.setValue("1");
//						model.setDescribe("是本人");
//
//						new Thread(new Runnable() {
//
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//
//								connection("1");
//							}
//						}).start();
//						mHandler.post(new Runnable() {
//
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								AlertDialog.Builder dialog = new AlertDialog.Builder(FaceSignVerifyActivity.this);
//								dialog.setTitle("是本人，验证成功")
//										.setPositiveButton("进入到下一页", new DialogInterface.OnClickListener() {
//
//											@Override
//											public void onClick(DialogInterface dialog, int which) {
//												// TODO Auto-generated method
//												// stub
//												Intent intent = new Intent();
//												intent.setClass(FaceSignVerifyActivity.this, facesignverifytest1.class);
//												startActivity(intent);
//											}
//										}).show();
//							}
//						});
//
//						// mHandler.post(new Runnable() {
//						//
//						// @Override
//						// public void run() {
//						// // TODO Auto-generated method stub
//						// ToastHelp.showCurrentToast(FaceSignVerifyActivity.this,
//						// "是本人 ");
//						// }
//						// });
//					} else if (!rtnPerson.getString("is_same_person").equals("true")) {
//						flag++;
//
//						mHandler.post(new Runnable() {
//
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								ToastHelp.showCurrentToast(FaceSignVerifyActivity.this, "不是本人 ");
//							}
//						});
//
//						if (flag >= 3) {
//							mHandler.post(new Runnable() {
//
//								@Override
//								public void run() {
//									// TODO Auto-generated method stub
//									AlertDialog.Builder dialog = new AlertDialog.Builder(FaceSignVerifyActivity.this);
//									dialog.setTitle("请联系安全员开锁")
//											.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//
//												@Override
//												public void onClick(DialogInterface dialog, int which) {
//													// TODO Auto-generated
//													// method stub
//
//												}
//											}).show();
//								}
//							});
//						}
//
//						// 发送
//						SendPost();
//
//					} else if ("false".equals(rtnPerson.getString("is_same_person"))) {
//						model.setValue("2");
//						model.setDescribe("不是本人");
//
//						// mHandler.post(new Runnable() {
//						//
//						// @Override
//						// public void run() {
//						// // TODO Auto-generated method stub
//						// ToastHelp.showCurrentToast(FaceSignVerifyActivity.this,
//						// "不是本人 ");
//						// }
//						// });
//					} else {
//						System.out.println("错误的json：is_same_person不是true也不是false");
//						model.setValue("4");
//						model.setDescribe("错误的json：is_same_person不是true也不是false");
//						mHandler.post(new Runnable() {
//
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								ToastHelp.showCurrentToast(FaceSignVerifyActivity.this, "人脸识别不到 ");
//							}
//						});
//					}
//
//					// 发送
//					SendPost();
//					return;
//				} catch (Exception e) {
//					System.out.println(e.toString());
//					model.setValue("5");
//					model.setDescribe("发生异常");
//				
//					// 发送
//					SendPost();
//					mHandler.post(new Runnable() {
//
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							ToastHelp.showCurrentToast(FaceSignVerifyActivity.this, "人脸识别失败");
//						}
//					});
//					e.printStackTrace();
//				}
//
//			}
//
//		}).start();
//	}
//
//	// public String GetNowTime() throws ParseException {
//	// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	// format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//	// Date date = format.parse(new Date().toString());
//	// String time =String.valueOf(date.getTime() / 1000);
//	// return time;
//	// }
//
//	private void SendPost() {
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				new NetworkApi().sendFaceVerifyMsg(model, new INetCallback() {
//
//					@Override
//					public void onCallback(boolean value, String result) {
//						// TODO Auto-generated method stub
//						if (value) {
//							mHandler.post(new Runnable() {
//								@Override
//								public void run() {
//
//									// ToastHelp.showCurrentToast(FaceSignVerifyActivity.this,
//									// "发送成功！");
//								}
//							});
//						} else {
//							mHandler.post(new Runnable() {
//								@Override
//								public void run() {
//									// ToastHelp.showCurrentToast(FaceSignVerifyActivity.this,
//									// "发送失败！");
//								}
//							});
//						}
//						mHandler.post(new Runnable() {
//							@Override
//							public void run() {
//								new GetMSGByName().execute(PreferenceUtil.getName(FaceSignVerifyActivity.this));
//							}
//						});
//
//					}
//				});
//			}
//		}).start();
//	}
//
//	class GetPersonIsExist extends AsyncTask<String, String, Boolean> {
//
//		@Override
//		protected Boolean doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			try {
//				return GetMyPerson(PreferenceUtil.getName(FaceSignVerifyActivity.this));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return false;
//
//		}
//
//		@Override
//		protected void onPostExecute(Boolean result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//
//			if (!result) {
//				mHandler.post(new Runnable() {
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						new AlertDialog.Builder(FaceSignVerifyActivity.this).setTitle("提示")
//								.setMessage("您还没录入过人脸，请先录入人脸再进行测试，是否跳转到录入界面？")
//								.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//									@Override
//									public void onClick(DialogInterface dialog, int which) {
//										dialog.cancel();
//										Intent intent = new Intent(FaceSignVerifyActivity.this,
//												AddFaceToPersonActivity.class);
//										FaceSignVerifyActivity.this.startActivity(intent);
//									}
//								}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//									@Override
//									public void onClick(DialogInterface dialog, int which) {
//										dialog.cancel();
//									}
//								}).setCancelable(false).create().show();
//
//					}
//				});
//			}
//		}
//
//	}
//
//	class GetMSGByName extends AsyncTask<String, String, List<FaceMSGModel>> {
//
//		@Override
//		protected List<FaceMSGModel> doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			return new NetworkApi().GetFaceData(params[0]);
//		}
//
//		@Override
//		protected void onPostExecute(List<FaceMSGModel> result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//
//			if (result == null) {
//				return;
//			}
//
//			// for (int i = 0; i < result.size(); i++) {
//			// switch (Integer.parseInt(result.get(i).getValue())) {
//			// case 1:
//			// mTextValue1.setText("是本人：" + result.get(i).getNum() + "/" +
//			// result.get(i).getAllNum());
//			// break;
//			// case 2:
//			// mTextValue2.setText("不是本人：" + result.get(i).getNum() + "/" +
//			// result.get(i).getAllNum());
//			// break;
//			// case 3:
//			// mTextValue3.setText("非法图片：" + result.get(i).getNum() + "/" +
//			// result.get(i).getAllNum());
//			// break;
//			// case 4:
//			// mTextValue4.setText("错误：" + result.get(i).getNum() + "/" +
//			// result.get(i).getAllNum());
//			// break;
//			// case 5:
//			// mTextValue5.setText("异常：" + result.get(i).getNum() + "/" +
//			// result.get(i).getAllNum());
//			// break;
//			// default:
//			// break;
//			// }
//			// }
//
//		}
//
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//
//		if (resultCode == Activity.RESULT_OK) {
//			if (!HttpUtil.networkIsAvailable(FaceSignVerifyActivity.this)) {
//				ToastHelp.showCurrentToast(FaceSignVerifyActivity.this, "手机没有网络连接！");
//				return;
//			}
//			Bundle extras = data.getExtras();
//			if (extras != null) {
//
//				Bitmap photo = extras.getParcelable("data");
//				mPhotoImage.setImageBitmap(photo);
//				Distinguish(photo);
//			}
//		}
//
//		// if (resultCode == Activity.RESULT_OK) {
//		// if (!(new File(mPhotoUri)).exists()) {
//		// Log.e("onActivityResult", "mPhotoUri not exists!");
//		// ToastHelp.showToast(this, "拍照失败！");
//		// return;
//		// }
//		// DisplayImageOptions options = new DisplayImageOptions.Builder()
//		// .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.ARGB_8888)
//		// .cacheInMemory(false).cacheOnDisc(true).build();
//		// ImageLoader.getInstance().displayImage("file://" + mPhotoUri,
//		// mPhotoImage, options);
//		//
//		// Distinguish(new File(mPhotoUri));
//		// }
//	}
//
//	public void connection(String num) {
//
//		try {
//			IO.Options opts = new IO.Options();
//
//			socket = IO.socket("http://120.35.11.49:28888", opts);
//			// socket = IO.socket("http://120.35.11.49:28883", opts);
//
//			// socket = IO.socket("http://10.1.3.13:3002");
//			
//			// { DeviceID: "2115618451", MsgType: 99999 };
//
//			JSONObject obj = new JSONObject();
//			obj.put("MsgType", 400);
//			// obj.put("DeviceID", "220312509");
//			obj.put("Status", num);
//
//			System.out.println("json:" + obj);
//			System.out.println("回调了参数??obj:" + obj);
//
//			socket.emit("login", obj);//
//
//			// 请求websocket后台方法
//			socket.on("message", new Emitter.Listener() { // 监听回调函数
//				@Override
//				public void call(Object... args) {
//
//					try {
//
//						// JSONObject obj = new JSONObject();
//						// obj.put("MsgType", 99999);
//						// // obj.put("DeviceID", "220312509");
//						// obj.put("DeviceID", CurrentSevNum);
//						// socket.emit("logout", obj);//
//						//
//						//
//						//
//						// socket.disconnect();
//						// socket.close();
//					
//					} catch (Exception e) {
//						// TODO: handle exception
//					
//						e.printStackTrace();
//					}
//				}
//			});
//
//			socket.connect();
//
//		} catch (Exception e1) {
//			System.out.println("回调了参数 :" + e1.toString());
//			e1.printStackTrace();
//		}
//
//	}
//
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//		try {
//			socket.off("message");
//			socket.disconnect();
//			socket.close();
//		
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.toString();
//		}
//
//	}
//}
