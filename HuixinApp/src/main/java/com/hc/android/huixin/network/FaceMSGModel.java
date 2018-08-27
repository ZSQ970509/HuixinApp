package com.hc.android.huixin.network;

public class FaceMSGModel {

	String allNum;
	String Num;
	String Account;
	String value;

	public String getAllNum() {
		return allNum;
	}

	public void setAllNum(String allNum) {
		this.allNum = allNum;
	}

	public String getNum() {
		return Num;
	}

	public void setNum(String num) {
		Num = num;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "FaceMSGModel [allNum=" + allNum + ", Num=" + Num + ", Account=" + Account + ", value=" + value + "]";
	}

}
