package com.king.photo.model;

import java.io.Serializable;

public class HumanModel implements Serializable{
	private String userName;
	private String userPhone;
	private String userIDNum;
	private String userCredentials;
	private String projectId;
	private String projectName;
	private String userType;
	private String userLoginPass;
	private String userOpenPass;
	
	public HumanModel(String userName, String userPhone, String userIDNum, String userCredentials, String projectId,
			String projectName, String userType, String userLoginPass) {
		super();
		this.userName = userName;
		this.userPhone = userPhone;
		this.userIDNum = userIDNum;
		this.userCredentials = userCredentials;
		this.projectId = projectId;
		this.projectName = projectName;
		this.userType = userType;
		this.userLoginPass = userLoginPass;
	}
	public HumanModel(String userName, String userPhone, String userIDNum, String userCredentials, String projectId,
			String projectName, String userType, String userLoginPass, String userOpenPass) {
		super();
		this.userName = userName;
		this.userPhone = userPhone;
		this.userIDNum = userIDNum;
		this.userCredentials = userCredentials;
		this.projectId = projectId;
		this.projectName = projectName;
		this.userType = userType;
		this.userLoginPass = userLoginPass;
		this.userOpenPass = userOpenPass;
	}
	public String getUserLoginPass() {
		return userLoginPass;
	}
	public void setUserLoginPass(String userLoginPass) {
		this.userLoginPass = userLoginPass;
	}
	public String getUserOpenPass() {
		return userOpenPass;
	}
	public void setUserOpenPass(String userOpenPass) {
		this.userOpenPass = userOpenPass;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserIDNum() {
		return userIDNum;
	}
	public void setUserIDNum(String userIDNum) {
		this.userIDNum = userIDNum;
	}
	public String getUserCredentials() {
		return userCredentials;
	}
	public void setUserCredentials(String userCredentials) {
		this.userCredentials = userCredentials;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
