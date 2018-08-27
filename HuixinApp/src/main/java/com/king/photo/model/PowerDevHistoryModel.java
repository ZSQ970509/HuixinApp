package com.king.photo.model;

public class PowerDevHistoryModel {

	private String RecordID;
	private String CamseqID;
	private String Status;
	private String PowerDownTime;
	private String PowerOnTime;

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

	/**
	 * @return the powerDownTime
	 */
	public String getPowerDownTime() {
		return PowerDownTime;
	}

	/**
	 * @param powerDownTime
	 *            the powerDownTime to set
	 */
	public void setPowerDownTime(String powerDownTime) {
		PowerDownTime = powerDownTime;
	}

	/**
	 * @return the powerOnTime
	 */
	public String getPowerOnTime() {
		return PowerOnTime;
	}

	/**
	 * @param powerOnTime
	 *            the powerOnTime to set
	 */
	public void setPowerOnTime(String powerOnTime) {
		PowerOnTime = powerOnTime;
	}

}
