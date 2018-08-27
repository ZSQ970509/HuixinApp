package com.king.photo.model;

public class PowerNetHistoryModel {

	private String RecordID;
	private String CamseqID;
	private String Status;
	private String NetDownTime;
	private String NetOnTime;

	/**
	 * @return the recordID
	 */
	public String getRecordID() {
		return RecordID;
	}

	/**
	 * @param recordID
	 *            the recordID to set
	 */
	public void setRecordID(String recordID) {
		RecordID = recordID;
	}

	/**
	 * @return the camseqID
	 */
	public String getCamseqID() {
		return CamseqID;
	}

	/**
	 * @param camseqID
	 *            the camseqID to set
	 */
	public void setCamseqID(String camseqID) {
		CamseqID = camseqID;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return Status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		Status = status;
	}

	public String getNetDownTime() {
		return NetDownTime; 
	}

	public void setNetDownTime(String netDownTime) {
		NetDownTime = netDownTime;
	}

	public String getNetOnTime() {
		return NetOnTime;
	}

	public void setNetOnTime(String netOnTime) {
		NetOnTime = netOnTime;
	}

	

}
