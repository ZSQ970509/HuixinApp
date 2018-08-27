package com.king.photo.model;

public class ErrorMsgModel {
	
	private String $id;
	private String RecordId;
	private String Code;
	private String Reason;
	private String Solution;
	private String CreateTime;
	private String EntityKey;
	
	public ErrorMsgModel(String $id, String recordId, String code, String reason, String solution, String createTime,
			String entityKey) {
		super();
		this.$id = $id;
		RecordId = recordId;
		Code = code;
		Reason = reason;
		Solution = solution;
		CreateTime = createTime;
		EntityKey = entityKey;
	}
	public String get$id() {
		return $id;
	}
	public void set$id(String $id) {
		this.$id = $id;
	}
	public String getRecordId() {
		return RecordId;
	}
	public void setRecordId(String recordId) {
		RecordId = recordId;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}
	public String getSolution() {
		return Solution;
	}
	public void setSolution(String solution) {
		Solution = solution;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getEntityKey() {
		return EntityKey;
	}
	public void setEntityKey(String entityKey) {
		EntityKey = entityKey;
	}
	
}
