package com.king.photo.util;

import java.io.IOException;
import com.king.photo.model.ImageInfoModel;

import android.media.ExifInterface;
import android.util.Log;

public class EXIFFile {

	public ImageInfoModel GetIMGWidthAndHeigh(String fileName) {

		ExifInterface exifInterface;
		try {
			ImageInfoModel model = new ImageInfoModel();
			exifInterface = new ExifInterface(fileName);
			model.setImgWidth(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
			model.setImgHeight(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));
	
			return model;
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		return null;

	}

	public static void main(String[] args) throws Exception {
		new EXIFFile().GetIMGWidthAndHeigh("G:\\1d80b99136ca9b4c54a69b6d795ba3c5.jpg");
	}

}
