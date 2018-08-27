package com.hc.android.huixin.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2018/4/2.
 */

public class AttendancePathService  extends Service {
    private static final String TAG="AttendancePathService";
    int i = 1;
    @Override
    //Service时被调用
    public void onCreate()
    {
        Log.i(TAG, "Service onCreate--->");
        super.onCreate();
    }

    @Override
    //当调用者使用startService()方法启动Service时，该方法被调用
    public void onStart(Intent intent, int startId)
    {
        Log.i(TAG, "Service onStart--->");
         new Thread(){
            @Override
            public void run(){
                try {

                    for (int i = 0 ; i<1000000; i++){
                        Log.e("service",i+"");
                        sleep(3000);
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        super.onStart(intent, startId);
    }
    /**
     * 每次通过startService()方法启动Service时都会被回调。
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand invoke");
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    //当Service不在使用时调用
    public void onDestroy()
    {
        Log.i(TAG, "Service onDestroy--->");
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
