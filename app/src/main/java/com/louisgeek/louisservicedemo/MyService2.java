package com.louisgeek.louisservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by louisgeek on 2016/5/19.
 */
public class MyService2 extends Service{
    private final String TAG = "MyService2";
    private boolean quit=false;
    private int count=0;
    MyBinder myBinder=new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate方法被调用!");
        //创建一个线程动态地修改count的值
        new Thread()
        {
            public void run()
            {
                while(!quit)
                {
                    try
                    {
                        Thread.sleep(1000);
                    }catch(InterruptedException e){e.printStackTrace();}
                    count++;
                }
            };
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand方法被调用!");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit = true;
        Log.i(TAG, "onDestroyed方法被调用!");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind方法被调用!");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
       // return super.onUnbind(intent);
        Log.i(TAG, "onUnbind方法被调用!");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind方法被调用!");
        super.onRebind(intent);
    }

    class MyBinder extends Binder {
public int getcount(){
    return  count;
}

    }
}
