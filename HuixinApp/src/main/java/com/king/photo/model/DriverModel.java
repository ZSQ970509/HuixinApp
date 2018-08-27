package com.king.photo.model;

public class DriverModel {
	private String camId;
	private String CamName;
	public DriverModel(String camId, String camName) {
		super();
		this.camId = camId;
		CamName = camName;
	}
	public String getCamId() {
		return camId;
	}
	public void setCamId(String camId) {
		this.camId = camId;
	}
	public String getCamName() {
		return CamName;
	}
	public void setCamName(String camName) {
		CamName = camName;
	}
	
	
}
