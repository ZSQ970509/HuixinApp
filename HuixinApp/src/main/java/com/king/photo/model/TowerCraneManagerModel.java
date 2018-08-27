package com.king.photo.model;

public class TowerCraneManagerModel {

	private String TowerRecordID;
	private String TrStatus;
	private String TrFailPaths;
	private String TrSuccessPath;
	private String TrCreateTime;
	private String TrCheckTime;
	private String TrRefreshTime;
	private String UserName;

	public String getTowerRecordID() {
		return TowerRecordID;
	}

	public void setTowerRecordID(String towerRecordID) {
		TowerRecordID = towerRecordID;
	}

	public String getTrStatus() {
		return TrStatus;
	}

	public void setTrStatus(String trStatus) {
		TrStatus = trStatus;
	}

	public String getTrFailPaths() {
		return TrFailPaths;
	}

	public void setTrFailPaths(String trFailPaths) {
		TrFailPaths = trFailPaths;
	}

	public String getTrSuccessPath() {
		return TrSuccessPath;
	}

	public void setTrSuccessPath(String trSuccessPath) {
		TrSuccessPath = trSuccessPath;
	}

	public String getTrCreateTime() {
		return TrCreateTime;
	}

	public void setTrCreateTime(String trCreateTime) {
		TrCreateTime = trCreateTime;
	}

	public String getTrCheckTime() {
		return TrCheckTime;
	}

	public void setTrCheckTime(String trCheckTime) {
		TrCheckTime = trCheckTime;
	}

	public String getTrRefreshTime() {
		return TrRefreshTime;
	}

	public void setTrRefreshTime(String trRefreshTime) {
		TrRefreshTime = trRefreshTime;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

}
