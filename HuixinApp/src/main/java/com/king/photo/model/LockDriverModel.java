package com.king.photo.model;

public class LockDriverModel {
	private String driverInstallPlace;
	private String driverMac;
	private String driverName;
	private String driverStatic;
	private String projectName;
	private String camName;
	public LockDriverModel(String driverName, String driverMac, String projectName, String driverStatic,
			String driverInstallPlace,String camName) {
		super();
		this.driverName = driverName;
		this.driverMac = driverMac;
		this.projectName = projectName;
		this.driverStatic = driverStatic;
		this.driverInstallPlace = driverInstallPlace;
		this.camName = camName;
	}
	
	public String getCamName() {
		return camName;
	}

	public void setCamName(String camName) {
		this.camName = camName;
	}

	public String getDriverInstallPlace() {
		return driverInstallPlace;
	}
	public String getDriverMac() {
		return driverMac;
	}
	public String getDriverName() {
		return driverName;
	}
	public String getDriverStatic() {
		return driverStatic;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setDriverInstallPlace(String driverInstallPlace) {
		this.driverInstallPlace = driverInstallPlace;
	}
	public void setDriverMac(String driverMac) {
		this.driverMac = driverMac;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public void setDriverStatic(String driverStatic) {
		this.driverStatic = driverStatic;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}
