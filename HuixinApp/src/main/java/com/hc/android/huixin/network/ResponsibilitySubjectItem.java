package com.hc.android.huixin.network;

public class ResponsibilitySubjectItem {

	public String id;
	public String text;
	public int hasSubset;
	public String explain;
	
	public ResponsibilitySubjectItem(String id, String text) {
		super();
		this.id = id;
		this.text = text;

	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getHasSubset() {
		return hasSubset;
	}
	public void setHasSubset(int hasSubset) {
		this.hasSubset = hasSubset;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	
}
