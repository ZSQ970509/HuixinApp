package com.king.photo.model;

public class PowerDevBindModel {

	String ProjID;
	String ProjName;
	String CamId;
	String CamName;
	String DevSeqID;
	String CamInstalPlace;
	
	
	
	public String getCamInstalPlace() {
		return CamInstalPlace;
	}

	public void setCamInstalPlace(String camInstalPlace) {
		CamInstalPlace = camInstalPlace;
	}

	public String getProjID() {
		return ProjID;
	}

	public void setProjID(String projID) {
		ProjID = projID;
	}

	public String getProjName() {
		return ProjName;
	}

	public void setProjName(String projName) {
		ProjName = projName;
	}

	public String getCamId() {
		return CamId;
	}

	public void setCamId(String camId) {
		CamId = camId;
	}

	public String getCamName() {
		return CamName;
	}

	public void setCamName(String camName) {
		CamName = camName;
	}

	public String getDevSeqID() {
		return DevSeqID;
	}

	public void setDevSeqID(String devSeqID) {
		DevSeqID = devSeqID;
	}

	@Override
	public String toString() {
		return "PowerDevBindModel [ProjID=" + ProjID + ", ProjName=" + ProjName + ", CamId=" + CamId + ", CamName="
				+ CamName + ", DevSeqID=" + DevSeqID + ", getProjID()=" + getProjID() + ", getProjName()="
				+ getProjName() + ", getCamId()=" + getCamId() + ", getCamName()=" + getCamName() + ", getDevSeqID()="
				+ getDevSeqID() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
