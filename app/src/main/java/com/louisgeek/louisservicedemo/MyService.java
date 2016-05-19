package com.louisgeek.louisservicedemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by louisgeek on 2016/5/19.
 */
public class MyService extends Service {
    private final String TAG = "MyService";
    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate方法被调用!");
        super.onCreate();
        notificationInit();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand方法被调用!");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy方法被调用!");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind方法被调用!");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind方法被调用!");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind方法被调用!");
        super.onRebind(intent);
    }

    void notificationInit(){
        Notification.Builder nBuilder = new Notification.Builder(this);
        nBuilder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0));
        nBuilder.setAutoCancel(false);//用户点击Notification点击面板后是否让通知取消(默认false 不取消)
        nBuilder.setSmallIcon(R.mipmap.ic_launcher);//设置右下角的小图标，在接收到通知的时候顶部也会显示这个小图标
        //nBuilder.setLargeIcon();//设置左边的大图标
        nBuilder.setTicker("前台服务启动");//设置收到通知时在顶部显示的文字信息
        nBuilder.setContentTitle("服务");//设置标题
        nBuilder.setContentText("正在运行...");//设置内容
        //nBuilder.setSubText("内容下面一小行的文字");//设置内容下面一小行的文字
        startForeground(1, nBuilder.getNotification());
    }
}
