package com.hc.android.huixin.network;

import java.io.Serializable;

public class FaceAddModel {
	private String Faceid;// 根据这个来删除person里的这个face
	private String Imgid; // 根据这个获取到福富的照片流
	private String Account;// 用户名
	private String ImgStr;// 存在我方服务器的流

	public String getFaceid() {
		return Faceid;
	}

	public void setFaceid(String faceid) {
		Faceid = faceid;
	}

	public String getImgid() {
		return Imgid;
	}

	public void setImgid(String imgid) {
		Imgid = imgid;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public String getImgStr() {
		return ImgStr;
	}

	public void setImgStr(String imgStr) {
		ImgStr = imgStr;
	}

}
