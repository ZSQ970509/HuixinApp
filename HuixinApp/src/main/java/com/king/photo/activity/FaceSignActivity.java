//package com.king.photo.activity;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.fisc.facesignsdk.error.FacesignParseException;
//import com.fisc.facesignsdk.http.HttpRequests;
//import com.fisc.facesignsdk.http.PostParameters;
//import com.hc.android.huixin.R;
//import com.hc.android.huixin.util.ToastHelp;
//import com.onesafe.util.AssetUtil;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//
//public class FaceSignActivity extends Activity {
//
//	Handler mHandler = new Handler() {
//
//	};
//
//	Button mBtnCheckFace;
//
//	Button mBtnAddFace;
//	Button BtnAnalysisFace;
//	Button BtnContrastFace;
//	Button BtnAddPerson;
//	Button BtnDelPerson;
//	Button BtnAddFaceToPerson;
//	Button BtnDelFaceToPerson;
//
//	Button BtnSetInfo;
//	Button BtnGetInfo;
//
//	Button mBtnCreateFaceset;
//	Button mBtnDelFaceset;
//
//	Button mBtnAddFaceToFaceset;
//	Button mBtnDelFaceToFaceset;
//
//	Button mBtnSetFaceset;
//	Button mBtnGetFaceset;
//
//	Button mBtnAddGroup;
//	Button mBtnDelGroup;
//
//	Button mBtnAddPersonToGroup;
//	Button mBtnDelPersonToGroup;
//
//	Button mBtnSetGroupInfo;
//	Button mBtnGetGroupInfo;
//
//	Button mBtnGetImageInfo;
//
//	Button mBtnGetFaceInfo;
//
//	Button mBtnGetPersonListInfo;
//	Button mBtnGetFacesetListInfo;
//	Button mBtnGetGroupListInfo;
//	Button mBtnGetSessionInfo;
//
//	String FaceId1 = null;
//	String FaceId2 = null;
//	String FaceId3 = null;
//
//	String ImgId1 = null;
//	String ImgId2 = null;
//	String ImgId3 = null;
//
//	String Session1 = null;
//	String Session2 = null;
//	String Session3 = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_face_sign);
//		mBtnCheckFace = (Button) findViewById(R.id.btn_check_face);
//		mBtnAddFace = (Button) findViewById(R.id.btn_add_face);
//		BtnAnalysisFace = (Button) findViewById(R.id.btn_analysis_face);
//		mBtnCheckFace = (Button) findViewById(R.id.btn_check_face);
//		BtnContrastFace = (Button) findViewById(R.id.btn_contrast_face);
//		BtnAddPerson = (Button) findViewById(R.id.btn_add_person);
//		BtnDelPerson = (Button) findViewById(R.id.btn_del_person);
//		BtnAddFaceToPerson = (Button) findViewById(R.id.btn_add_face_to_person);
//		BtnDelFaceToPerson = (Button) findViewById(R.id.btn_del_face_to_person);
//		BtnSetInfo = (Button) findViewById(R.id.btn_Set_info);
//		BtnGetInfo = (Button) findViewById(R.id.btn_Get_info);
//		mBtnCreateFaceset = (Button) findViewById(R.id.btn_Create_Faceset);
//		mBtnDelFaceset = (Button) findViewById(R.id.btn_Del_Faceset);
//		mBtnAddFaceToFaceset = (Button) findViewById(R.id.btn_Add_Face_To_Faceset);
//		mBtnDelFaceToFaceset = (Button) findViewById(R.id.btn_Del_Face_To_Faceset);
//		mBtnSetFaceset = (Button) findViewById(R.id.btn_Set_Faceset);
//		mBtnGetFaceset = (Button) findViewById(R.id.btn_Get_Faceset);
//		mBtnAddGroup = (Button) findViewById(R.id.btn_Add_Group);
//		mBtnDelGroup = (Button) findViewById(R.id.btn_Del_Group);
//		mBtnAddPersonToGroup = (Button) findViewById(R.id.btn_Add_Person_To_Group);
//		mBtnDelPersonToGroup = (Button) findViewById(R.id.btn_Del_Person_To_Group);
//		mBtnSetGroupInfo = (Button) findViewById(R.id.btn_Set_Group_Info);
//		mBtnGetGroupInfo = (Button) findViewById(R.id.btn_Get_Group_Info);
//
//		mBtnGetImageInfo = (Button) findViewById(R.id.btn_Get_Img_Info);
//
//		mBtnGetFaceInfo = (Button) findViewById(R.id.btn_Get_Face_Info);
//
//		mBtnGetPersonListInfo = (Button) findViewById(R.id.btn_Get_PersonList_Info);
//
//		mBtnGetFacesetListInfo = (Button) findViewById(R.id.btn_Get_FacesetList_Info);
//
//		mBtnGetGroupListInfo = (Button) findViewById(R.id.btn_Get_GroupList_Info);
//
//		mBtnGetSessionInfo = (Button) findViewById(R.id.btn_Get_Session_Info);
//		mBtnCheckFace.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				CheckFace();
//
//			}
//
//		});
//
//		BtnAnalysisFace.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				AnalysisFace();
//			}
//		});
//
//		mBtnAddFace.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				HttpRequests request = new HttpRequests(true);
//				try {
//					JSONObject rtn = request.detectionDetect();
//
//				} catch (FacesignParseException e) {
//
//					e.printStackTrace();
//				}
//
//			}
//		});
//
//		BtnContrastFace.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				ContrastFace();
//			}
//		});
//
//		BtnAddPerson.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				AddPerson();
//			}
//		});
//
//		BtnDelPerson.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				DelPerson();
//			}
//		});
//		BtnAddFaceToPerson.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				AddFaceToPerson();
//			}
//		});
//
//		BtnDelFaceToPerson.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				DelFaceToPerson();
//			}
//		});
//		BtnSetInfo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				SetInfo();
//			}
//		});
//
//		BtnGetInfo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				GetInfo();
//			}
//		});
//
//		mBtnCreateFaceset.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				CreateFaceset();
//			}
//		});
//		mBtnDelFaceset.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				DelFaceset();
//			}
//		});
//
//		mBtnAddFaceToFaceset.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				AddFaceToFaceset();
//			}
//		});
//
//		mBtnDelFaceToFaceset.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				DelFaceToFaceset();
//			}
//		});
//
//		mBtnSetFaceset.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				SetFaceset();
//			}
//		});
//
//		mBtnGetFaceset.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				GetFaceset();
//			}
//		});
//
//		mBtnAddGroup.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				AddGroup();
//			}
//		});
//
//		mBtnDelGroup.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				DelGroup();
//			}
//		});
//
//		mBtnAddPersonToGroup.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				AddPersonToGroup();
//			}
//		});
//		mBtnDelPersonToGroup.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				DelPersonToGroup();
//			}
//		});
//		mBtnGetGroupInfo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				GetGroupInfo();
//			}
//		});
//		mBtnSetGroupInfo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				SetGroupInfo();
//			}
//		});
//
//		mBtnGetImageInfo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				GetImageInfo();
//			}
//		});
//
//		mBtnGetFaceInfo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				GetFaceInfo();
//			}
//		});
//
//		mBtnGetPersonListInfo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				GetPersonListInfo();
//			}
//		});
//
//		mBtnGetFacesetListInfo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				GetFacesetListInfo();
//			}
//		});
//
//		mBtnGetGroupListInfo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				GetGroupListInfo();
//			}
//		});
//		mBtnGetSessionInfo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				GetSessionInfo();
//			}
//		});
//	}
//
//	protected void GetSessionInfo() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//					params.setSessionId(Session1);
//					final JSONObject rtn;
//					rtn = request.infoGetSession(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void GetGroupListInfo() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					// PostParameters params = new PostParameters();
//
//					final JSONObject rtn;
//					rtn = request.infoGetGroupList();
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void GetFacesetListInfo() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					// PostParameters params = new PostParameters();
//
//					final JSONObject rtn;
//					rtn = request.infoGetFacesetList();
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void GetPersonListInfo() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					// PostParameters params = new PostParameters();
//
//					final JSONObject rtn;
//					rtn = request.infoGetPersonList();
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void GetFaceInfo() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setFaceId(FaceId1);
//					final JSONObject rtn;
//					rtn = request.infoGetFace(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void GetImageInfo() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setImgId(ImgId1);
//					final JSONObject rtn;
//					rtn = request.infoGetImage(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void GetGroupInfo() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setGroupName("AAA的组");
//					final JSONObject rtn;
//					rtn = request.groupGetInfo(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void SetGroupInfo() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setGroupName("AAA的组");
//					params.setTag("AAA的组的TAG");
//
//					final JSONObject rtn;
//					rtn = request.groupSetInfo(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void AddPersonToGroup() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setGroupName("AAA的组");
//					params.setPersonName("AAA");
//					final JSONObject rtn;
//					rtn = request.groupAddPerson(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void DelPersonToGroup() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setGroupName("AAA的组");
//					params.setPersonName("AAA");
//					final JSONObject rtn;
//					rtn = request.groupRemovePerson(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void DelGroup() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setGroupName("AAA的组");
//
//					final JSONObject rtn;
//					rtn = request.groupDelete(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void AddGroup() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setGroupName("AAA的组");
//					params.setTag("AAA的TAG");
//					params.setPersonName("AAA");
//
//					final JSONObject rtn;
//					rtn = request.groupCreate(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void GetFaceset() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setFacesetName("AAA的人脸集");
//
//					final JSONObject rtn;
//					rtn = request.facesetGetInfo(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void SetFaceset() {
//		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setFacesetName("AAA的人脸集");
//					params.setTag("AAA的演员？");
//					final JSONObject rtn;
//					rtn = request.facesetSetInfo(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void DelFaceToFaceset() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setFacesetName("AAA的人脸集");
//					params.setFaceId(FaceId3);
//					final JSONObject rtn;
//					rtn = request.facesetRemoveFace(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void AddFaceToFaceset() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setFacesetName("AAA的人脸集");
//					params.setFaceId(FaceId3);
//					final JSONObject rtn;
//					rtn = request.facesetAddFace(params);
//
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void DelFaceset() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setFacesetName("AAA的人脸集");
//
//					final JSONObject rtn;
//					rtn = request.facesetDelete(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void CreateFaceset() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setFacesetName("AAA的人脸集");
//					params.setTag("AAA");
//					params.setFaceId(FaceId1 + "," + FaceId2 + "," + FaceId3);
//					final JSONObject rtn;
//					rtn = request.facesetCreate(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void SetInfo() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setPersonName("AAA");
//					// params.setPersonId(personId);
//					params.setTag("BB");
//					final JSONObject rtn;
//					rtn = request.personSetInfo(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void GetInfo() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setPersonName("AAA");
//					// params.setPersonId(personId);
//
//					final JSONObject rtn;
//					rtn = request.personGetInfo(params);
//					System.out.println(rtn.toString());
//
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void DelFaceToPerson() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setPersonName("AAA");
//					// params.setPersonId(personId);
//					params.setFaceId(FaceId3);
//					final JSONObject rtn;
//					rtn = request.personRemoveFace(params);
//
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void AddFaceToPerson() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//
//					params.setPersonName("AAA");
//					// params.setPersonId(personId);
//					params.setFaceId(FaceId3);
//					final JSONObject rtn;
//					rtn = request.personAddFace(params);
//
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void DelPerson() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//					params.setPersonName("AAA");
//					// params.setPersonId(personId);
//					final JSONObject rtn;
//					rtn = request.personDelete(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//
//			}
//		}).start();
//	}
//
//	protected void AddPerson() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//					params.setPersonName("AAA");
//					ArrayList<String> facelist = new ArrayList<String>();
//					facelist.add(FaceId1);
//					facelist.add(FaceId2);
//					params.setFaceId(FaceId1 + "," + FaceId2);
//					params.setTag("AAA");
//					// params.setGroupId(groupIds);
//					// params.setGroupName(groupNames);
//					final JSONObject rtn;
//					rtn = request.personCreate(params);
//					System.out.println(rtn.toString());
//				} catch (Exception e) {
//
//					System.out.println(e.toString());
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	protected void ContrastFace() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//					params.setFaceId1(FaceId1);
//					params.setFaceId2(FaceId2);
//					params.setAsync(false);
//					final JSONObject rtn;
//					rtn = request.recognitionCompare(params);
//					System.out.println(rtn.toString());
//
//					mHandler.post(new Runnable() {
//
//						@Override
//						public void run() {
//
//							try {
//								ToastHelp.showCurrentToast(FaceSignActivity.this, rtn.getString("similarity"));
//							} catch (JSONException e) {
//
//								e.printStackTrace();
//							}
//
//						}
//					});
//
//				} catch (Exception e) {
//
//					e.printStackTrace();
//					System.out.println(e.toString());
//
//				}
//
//			}
//		}).start();
//	}
//
//	protected void AnalysisFace() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				if (FaceId1 == null) {
//					ToastHelp.showCurrentToast(FaceSignActivity.this, "FaceId=" + FaceId1);
//					return;
//				}
//				try {
//					HttpRequests request = new HttpRequests(true);
//					PostParameters params = new PostParameters();
//					params.setFaceId(FaceId1);
//					params.setType("25p");// 25|83p
//					JSONObject rtn = request.detectionLandmark(params);
//					System.out.println(rtn.toString());
//
//				} catch (Exception e) {
//
//					e.printStackTrace();
//					System.out.println(e.toString());
//
//				}
//
//			}
//		}).start();
//	}
//
//	public void CheckFace() {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				HttpRequests request = new HttpRequests(true);
//				try {
//					PostParameters param = new PostParameters();
//
//					param.setImg(AssetUtil.InputStreamToByte(getAssets().open("fbb1.jpg")));
//					param.setMode("normal");// oneface|normal
//					param.setAttribute("none");// gender | age | race |
//												// all |
//												// none
//					param.setTag("AAA");
//					param.setAsync(false);// 如果置为true，该API将会以异步方式被调用；也就是立即返回一个session
//											// id，稍后可通过getSession（String
//											// sessionId)查询结果。默认值为false。
//					JSONObject rtn = request.detectionDetect(param);
//
//					System.out.println(rtn.toString());
//					System.out.println(rtn.getJSONArray("face").getJSONObject(0).getString("face_id"));
//					FaceId1 = rtn.getJSONArray("face").getJSONObject(0).getString("face_id");
//					ImgId1 = rtn.getString("img_id");
//					Session1 = rtn.getString("session_id");
//
//					PostParameters param2 = new PostParameters();
//
//					param2.setImg(AssetUtil.InputStreamToByte(getAssets().open("fbb2.jpg")));
//					param2.setMode("normal");// oneface|normal
//					param2.setAttribute("none");// gender | age | race |
//												// all |
//												// none
//					param2.setTag("AAA");
//					param2.setAsync(false);// 如果置为true，该API将会以异步方式被调用；也就是立即返回一个session
//											// id，稍后可通过getSession（String
//											// sessionId)查询结果。默认值为false。
//					JSONObject rtn2 = request.detectionDetect(param2);
//
//					System.out.println(rtn2.toString());
//					System.out.println(rtn2.getJSONArray("face").getJSONObject(0).getString("face_id"));
//					FaceId2 = rtn2.getJSONArray("face").getJSONObject(0).getString("face_id");
//					ImgId2 = rtn2.getString("img_id");
//					Session2 = rtn2.getString("session_id");
//
//					PostParameters param3 = new PostParameters();
//
//					param3.setImg(AssetUtil.InputStreamToByte(getAssets().open("fbb3.jpg")));
//					param3.setMode("normal");// oneface|normal
//					param3.setAttribute("none");// gender | age | race |
//												// all |
//												// none
//					param3.setTag("AAA");
//					param3.setAsync(false);// 如果置为true，该API将会以异步方式被调用；也就是立即返回一个session
//											// id，稍后可通过getSession（String
//											// sessionId)查询结果。默认值为false。
//					JSONObject rtn3 = request.detectionDetect(param3);
//
//					System.out.println(rtn3.toString());
//					System.out.println(rtn3.getJSONArray("face").getJSONObject(0).getString("face_id"));
//					FaceId3 = rtn3.getJSONArray("face").getJSONObject(0).getString("face_id");
//					ImgId3 = rtn3.getString("img_id");
//					Session3 = rtn3.getString("session_id");
//
//				} catch (Exception e) {
//
//					e.printStackTrace();
//					System.out.println(e.toString());
//				}
//			}
//		}).start();
//	}
//
//}
