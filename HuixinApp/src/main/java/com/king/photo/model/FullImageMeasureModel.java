package com.king.photo.model;

import android.graphics.Bitmap;

public class FullImageMeasureModel {

	String PmgId;
	String PmgType;
	String PmgPuzzleId;
	String PmgUserId;
	String PmgAddtime;
	String PmgRemark;
	String PmgIsDeleted;
	String PmgPath;
	Bitmap Bitmap;

	public Bitmap getBitmap() {
		return Bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		Bitmap = bitmap;
	}

	public String getPmgId() {
		return PmgId;
	}

	public void setPmgId(String pmgId) {
		PmgId = pmgId;
	}

	public String getPmgType() {
		return PmgType;
	}

	public void setPmgType(String pmgType) {
		PmgType = pmgType;
	}

	public String getPmgPuzzleId() {
		return PmgPuzzleId;
	}

	public void setPmgPuzzleId(String pmgPuzzleId) {
		PmgPuzzleId = pmgPuzzleId;
	}

	public String getPmgUserId() {
		return PmgUserId;
	}

	public void setPmgUserId(String pmgUserId) {
		PmgUserId = pmgUserId;
	}

	public String getPmgAddtime() {
		return PmgAddtime;
	}

	public void setPmgAddtime(String pmgAddtime) {
		PmgAddtime = pmgAddtime;
	}

	public String getPmgRemark() {
		return PmgRemark;
	}

	public void setPmgRemark(String pmgRemark) {
		PmgRemark = pmgRemark;
	}

	public String getPmgIsDeleted() {
		return PmgIsDeleted;
	}

	public void setPmgIsDeleted(String pmgIsDeleted) {
		PmgIsDeleted = pmgIsDeleted;
	}

	public String getPmgPath() {
		return PmgPath;
	}

	public void setPmgPath(String pmgPath) {
		PmgPath = pmgPath;
	}

}
