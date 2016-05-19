package com.louisgeek.louisservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;

/**
 * Created by louisgeek on 2016/5/19.
 */
public class LongRunningService extends Service {

    private static final String TAG = "LongRunningService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //这里开辟一条线程,用来执行具体的逻辑操作:
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, new Date().toString());
            }
        }).start();

        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }



}
