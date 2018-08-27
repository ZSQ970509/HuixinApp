package com.hc.android.huixin.util;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by yepx on 2017/9/15.
 */

public class BitmapUtils {
    private static final File parentPath = Environment.getExternalStorageDirectory();
    private static String storagePath = "";
    private static final String DST_FOLDER_NAME = "pic_temp";
    public static String tmpPath = "";

    //图片按比例大小压缩方法（根据路径获取图片并压缩）：
    public static String getImageByPath(String srcPath) throws Exception{
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        //打开图片获取分辨率
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        //传过来图片分辨率的宽度和高度
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //这里设置高度为800f
        //这里设置宽度为480f
        float hh = 320.0F;
        float ww = 320.0F;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        //be=1表示不缩放
        int be = 1;
        //如果宽度大的话根据宽度固定大小缩放
        if ((w > h) && (w > ww))
            be =(int) Math.rint(newOpts.outWidth / ww);
            //如果高度高的话根据宽度固定大小缩放
        else if ((w < h) && (h > hh)) {
            be = (int)Math.rint (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //设置缩放比例
        newOpts.inSampleSize = be;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        //压缩好比例大小后再进行质量压缩
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;
        //循环判断如果压缩后图片是否大于90kb,大于继续压缩
        while (Base64.encode(baos.toByteArray(),Base64.DEFAULT).length / 1024 > 50) {
            //重置baos即清空baos
            baos.reset();
            //这里压缩options%，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //每次都减少10
            options -= 5;
        }
        return new String(Base64.encode(baos.toByteArray(),Base64.DEFAULT),"utf-8");
    }

    /**
     * 从相册获取图片后，从uri获取路径
     */
    public static String getFilePathFromUrl(Context context, Uri uri) {
        String path = null;
        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            String projection[] = {MediaStore.Images.ImageColumns.DATA};
            Cursor c = context.getContentResolver().query(uri, projection, null, null, null);
            if (c != null && c.moveToFirst()) {
                path = c.getString(0);
            }
            if (c != null)
                c.close();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            path = uri.getPath();
        }
        return path;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * //@param newWidth  ：缩放后宽度
     * //@param newHeight ：缩放后高度  double newWidth, double newHeight
     * @return 返回base64后的字符串
     * new String(Base64.encode(baos.toByteArray()), "utf-8");
     */
    public static String zoomImage(Bitmap bgimage) throws Exception {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
        float scaleWidth = ((float) 320) / width;
        float scaleHeight = ((float) 320) / height;
        //选择一个合适的压缩比，防止图片压缩变形
        if( scaleWidth > scaleHeight){
            scaleHeight = scaleWidth;
        } else{
            scaleWidth = scaleHeight;
        }
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;
        /*循环判断如果压缩后图片是否大于60kb,大于继续压缩
          2015-12-30 华为mate7，出现大于100KB的情况，故修改
        */
        while (Base64.encode(baos.toByteArray(),Base64.DEFAULT).length / 1024 >80) {
            //重置baos即清空baos
            baos.reset();
            //这里压缩options%，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //每次都减少5
            options -= 5;
        }
        return new String(Base64.encode(baos.toByteArray(),Base64.DEFAULT), "utf-8");

    }

    /**
     * 从文件中解图 解大图内存不足时尝试5此, samplesize增大
     *
     * @param
     * @param max 宽或高的最大值, <= 0 , 能解多大解多大, > 0, 最大max, 内存不足解更小
     */
    public static Bitmap getBitmapFromFileLimitSize(String filePath, int max) {

        if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
            return null;

        }
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;

        if (max > 0) {
            options.inJustDecodeBounds = true;
            // 获取这个图片的宽和高
            options.inJustDecodeBounds = false;
            /*
            将需要压缩的尺寸为 600,450
             */
            options.inSampleSize = calculateInSampleSize(options, max, max);
//            options.inSampleSize = 2;
        }
        bm = rotateBitmap(BitmapFactory.decodeFile(filePath, options), filePath);
        return bm;
    }

    /**
     * 保存图片到文件
     */
    public static boolean saveBmpToFile(Bitmap bmp, String path, Bitmap.CompressFormat format) {
        if (bmp == null || bmp.isRecycled())
            return false;

        OutputStream stream = null;
        try {
            File file = new File(path);
            File filePath = file.getParentFile();
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            stream = new FileOutputStream(path);
            return bmp.compress(format, 80, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (null != stream) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        return bmp.compress(format, 80, stream);
    }

    /**
     * 获取图片旋转角度
     */
    //判断图片的旋转角度
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            //Log.e("---->", ex.getMessage());
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                    default:
                        break;
                }
            }
        }
        return degree;
    }

    /**
     * 旋转图片
     */
    public static Bitmap rotateBitmap(Bitmap b, String filepath) {
        int degrees = getExifOrientation(filepath) ;
        if (degrees != 0 && b != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) b.getWidth() / 2, (float) b.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if (b != b2) {
                    b.recycle();
                    b = b2;
                }
            } catch (OutOfMemoryError ex) {

            }
        }
        return b;
    }

    /**
     * 压缩图片
     *
     * @param options   BitmapFactory.Options
     * @param reqWidth  要求的宽度
     * @param reqHeight 要求的高度
     * @return 返回 bitmap
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = (int) Math.rint((float) height / (float) reqHeight);
            final int widthRatio = (int) Math.rint((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * @return
     */
    private static String initPath() throws Exception {
        if (storagePath.equals("")) {
            storagePath = parentPath.getAbsolutePath() + "/" + DST_FOLDER_NAME;
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdir();
            }
        }
        return storagePath;
    }

    /**
     *
     * @param b
     */
    public static String saveBitmap(Bitmap b) {
        String path = "";
        try {
            path = initPath();
        } catch (Exception e) {
        }

//    long dataTake = System.currentTimeMillis();
        String imgCaptureFace = "zt_imgCaptureFace";
        String jpegName = path + "/" + imgCaptureFace + ".jpg";

        BitmapUtils.saveBmpToFile(b, jpegName, Bitmap.CompressFormat.JPEG);
        tmpPath = jpegName;
        return jpegName;

    }


    public static String saveBitmap(Bitmap b, String name) {

        String path = "";
        try {
            path = initPath();
        } catch (Exception e) {
        }
        long dataTake = System.currentTimeMillis();
        String jpegName = path + "/" + name + "_" + dataTake + ".jpg";
        BitmapUtils.saveBmpToFile(b, jpegName, Bitmap.CompressFormat.JPEG);
        return jpegName;
    }

    // 把Bitmap转换成Base64
    public static String getBitmapStrBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, 0);
    }

    // 把Base64转换成Bitmap
    public static Bitmap getBitmapFromBase64(String iconBase64) {
        byte[] bitmapArray = Base64.decode(iconBase64, Base64.DEFAULT);
        return BitmapFactory
                .decodeByteArray(bitmapArray, 0, bitmapArray.length);
    }

    Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于300kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 读取uri所在的图片
     *
     * @param uri      图片对应的Uri
     * @param mContext 上下文对象
     * @return 获取图像的Bitmap
     */
    public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
    public static String getImageStr(String imgFilePath) {
        byte[] data = null;

        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    // 对字节数组字符串进行Base64解码并生成图片
    public static boolean generateImage(String imgStr, String imgFilePath) {
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(String pathName,
                                                         int reqWidth, int reqHeight) {
        // 首先设置 inJustDecodeBounds=true 来获取图片尺寸
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max((double) options.outWidth / 1024f, (double) options.outHeight / 1024f)));
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

    //获取图片转向
    public static  File getImageMatrix(int cameraId , File  pictureFile) {
        //对图片进行镜像处理
        Matrix matrix = new Matrix();
        if (Camera.CameraInfo.CAMERA_FACING_FRONT == cameraId) {
            matrix.setScale(1, 1);//不反转
        }else if (Camera.CameraInfo.CAMERA_FACING_BACK == cameraId){
            matrix.setScale(-1, 1);//反转
        }
        try {
            Bitmap bitmapSource = decodeSampledBitmapFromResource(pictureFile.getAbsolutePath(),0,0);
            Bitmap bitmap = Bitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.getWidth(), bitmapSource.getHeight(), matrix, true);;
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return  pictureFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  pictureFile;
    }
}
