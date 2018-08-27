package com.hc.android.huixin.network;

public class FaceModel {
	private String Imgstr;
	private String account;
	private String value;
	private String describe;
	private String detectionDetectJson;
	private String recognitionVerifyJson;

	public String getImgstr() {
		return Imgstr;
	}

	public void setImgstr(String imgstr) {
		Imgstr = imgstr;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getDetectionDetectJson() {
		return detectionDetectJson;
	}

	public void setDetectionDetectJson(String detectionDetectJson) {
		this.detectionDetectJson = detectionDetectJson;
	}

	public String getRecognitionVerifyJson() {
		return recognitionVerifyJson;
	}

	public void setRecognitionVerifyJson(String recognitionVerifyJson) {
		this.recognitionVerifyJson = recognitionVerifyJson;
	}

	@Override
	public String toString() {
		return "FaceModel [Imgstr=" + Imgstr + ", account=" + account + ", value=" + value + ", describe=" + describe
				+ ", detectionDetectJson=" + detectionDetectJson + ", recognitionVerifyJson=" + recognitionVerifyJson
				+ "]";
	}

}
