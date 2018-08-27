package com.king.photo.model;

public class CabineModel {
	private String CamName;
	private String CamInstalPlace;
	public String getCamName() {
		return CamName;
	}
	public void setCamName(String camName) {
		CamName = camName;
	}
	public String getCamInstalPlace() {
		return CamInstalPlace;
	}
	public void setCamInstalPlace(String camInstalPlace) {
		CamInstalPlace = camInstalPlace;
	}
	public CabineModel(String camName, String camInstalPlace) {
		super();
		CamName = camName;
		CamInstalPlace = camInstalPlace;
	}
	
}
