package com.hc.android.huixin.util;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by yc on 2018/4/29/0029.
 */

public class CrashHanlder implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHanlder INSTANCE = new CrashHanlder();
    // 程序的Context对象
    private Context mContext;

    private CrashHanlder() {
    }

    public static CrashHanlder getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 异常捕获
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//        if (!handleException(ex) && mDefaultHandler != null) {
//            // 如果用户没有处理则让系统默认的异常处理器来处
//            mDefaultHandler.uncaughtException(thread, ex);
//        } else {
        // 跳转到崩溃提示Activity
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String errorMessage = writer.toString();
        PreferenceUtil.saveCrash(errorMessage);
        System.exit(1);//关闭已奔溃的app进程
    }
}