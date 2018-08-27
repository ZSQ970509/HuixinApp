package com.hc.android.huixin.network;

public class PlaceMessgaeItem {

	private String Longtitude;
	private String Latitude;
	private String Alpha;
	
	public PlaceMessgaeItem(String longtitude, String latitude, String alpha) {
		super();
		Longtitude = longtitude;
		Latitude = latitude;
		Alpha = alpha;
	}
	public String getLongtitude() {
		return Longtitude;
	}
	public void setLongtitude(String longtitude) {
		Longtitude = longtitude;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getAlpha() {
		return Alpha;
	}
	public void setAlpha(String alpha) {
		Alpha = alpha;
	}
}
