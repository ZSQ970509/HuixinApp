package com.king.photo.model;

public class PowerDevModel {

	private String CamProjId;
	private String CamId;
	private String CamName;
	private String CamSeqID;
	private String BindDate;
	private Integer DevStatus;
	private Integer DevNetstatus;


	private Integer CamFlowState;
	private String ProjChargePerson;
	private String ProjChargePersonPhone;
	private String ProjName;
	private String ProjAddress;

	
	public String getProjChargePerson() {
		return ProjChargePerson;
	}
	public Integer getDevNetstatus() {
		return DevNetstatus;
	}

	public void setDevNetstatus(Integer devNetStatus) {
		DevNetstatus = devNetStatus;
	}
	public void setProjChargePerson(String projChargePerson) {
		ProjChargePerson = projChargePerson;
	}

	public String getProjChargePersonPhone() {
		return ProjChargePersonPhone;
	}

	public void setProjChargePersonPhone(String projChargePersonPhone) {
		ProjChargePersonPhone = projChargePersonPhone;
	}

	public String getProjName() {
		return ProjName;
	}

	public void setProjName(String projName) {
		ProjName = projName;
	}

	public String getProjAddress() {
		return ProjAddress;
	}

	public void setProjAddress(String projAddress) {
		ProjAddress = projAddress;
	}

	/**
	 * @return the camProjId
	 */
	public String getCamProjId() {
		return CamProjId;
	}

	/**
	 * @param camProjId
	 *            the camProjId to set
	 */
	public void setCamProjId(String camProjId) {
		CamProjId = camProjId;
	}

	/**
	 * @return the camId
	 */
	public String getCamId() {
		return CamId;
	}

	/**
	 * @param camId
	 *            the camId to set
	 */
	public void setCamId(String camId) {
		CamId = camId;
	}

	/**
	 * @return the camName
	 */
	public String getCamName() {
		return CamName;
	}

	/**
	 * @param camName
	 *            the camName to set
	 */
	public void setCamName(String camName) {
		CamName = camName;
	}

	/**
	 * @return the camSeqID
	 */
	public String getCamSeqID() {
		return CamSeqID;
	}

	/**
	 * @param camSeqID
	 *            the camSeqID to set
	 */
	public void setCamSeqID(String camSeqID) {
		CamSeqID = camSeqID;
	}

	/**
	 * @return the bindDate
	 */
	public String getBindDate() {
		return BindDate;
	}

	/**
	 * @param bindDate
	 *            the bindDate to set
	 */
	public void setBindDate(String bindDate) {
		BindDate = bindDate;
	}

	/**
	 * @return the devStatus
	 */
	public Integer getDevStatus() {
		return DevStatus;
	}

	/**
	 * @param devStatus
	 *            the devStatus to set
	 */
	public void setDevStatus(Integer devStatus) {
		DevStatus = devStatus;
	}

	/**
	 * @return the camFlowState
	 */
	public Integer getCamFlowState() {
		return CamFlowState;
	}

	/**
	 * @param camFlowState
	 *            the camFlowState to set
	 */
	public void setCamFlowState(Integer camFlowState) {
		CamFlowState = camFlowState;
	}

}
