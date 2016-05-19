package com.louisgeek.louisservicedemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    boolean isBind = false;

    boolean flag1=false;
    boolean flag2=false;
    boolean flag3=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button idbtn2 = (Button) findViewById(R.id.id_btn2);
        Button idbtn = (Button) findViewById(R.id.id_btn);

        Button idbtn3 = (Button) findViewById(R.id.id_btn3);
        Button idbtn4 = (Button) findViewById(R.id.id_btn4);
        Button idbtn5 = (Button) findViewById(R.id.id_btn5);

        Button idbtn6 = (Button) findViewById(R.id.id_btn6);

        Button idbtn_service = (Button) findViewById(R.id.id_btn7);
        Button idbtn_receiver = (Button) findViewById(R.id.id_btn8);
        Button idbtn_aty = (Button) findViewById(R.id.id_btn9);

        //创建启动Service的Intent,以及Intent属性
        final Intent intent = new Intent();
        intent.setAction("com.zfq.service.MY_SERVICE");
        intent.setPackage(getPackageName());//这里你需要设置你应用的包名

        idbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });

        idbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });
        //  /、、、
        final Intent intent2 = new Intent();
        intent2.setAction("com.zfq.service.MY_SERVICE2");
        intent2.setPackage(getPackageName());//这里你需要设置你应用的包名
        idbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBind = bindService(intent2, serviceConnection, Service.BIND_AUTO_CREATE);
            }
        });
        idbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBind) {
                    unbindService(serviceConnection);
                } else {
                    Toast.makeText(MainActivity.this, "未绑定", Toast.LENGTH_SHORT).show();
                }
            }
        });
        idbtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "查询结果：" + msmbinder.getcount());
            }
        });

        idbtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSer_Test();
            }
        });


        idbtn_receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!flag1) {
                    alarmInit4Broadcast(MainActivity.this,false);
                    flag1=true;
                }else{
                    alarmInit4Broadcast(MainActivity.this,true);
                    flag1=false;
                }
            }
        });

        idbtn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag2) {
                    alarmInit4Service(false);
                    flag2=true;
                }else{
                    alarmInit4Service(true);
                    flag2=false;
                }
            }
        });
        idbtn_aty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!flag3) {
                    alarmInit4Activity(MainActivity.this, false);
                    flag3=true;
                }else{
                    alarmInit4Activity(MainActivity.this, true);
                    flag3=false;
                }
            }
        });

    }


    MyService2.MyBinder msmbinder;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected方法被调用!");
            msmbinder = (MyService2.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected方法被调用!");
        }
    };


    void intentSer_Test() {
        Intent intent1 = new Intent("com.zfq.service.MY_INTENTSERVICE");
        intent1.setPackage(getPackageName());//这里你需要设置你应用的包名
        Bundle b1 = new Bundle();
        b1.putString("param", "s1");
        intent1.putExtras(b1);

        Intent intent2 = new Intent("com.zfq.service.MY_INTENTSERVICE");
        intent2.setPackage(getPackageName());//这里你需要设置你应用的包名
        Bundle b2 = new Bundle();
        b2.putString("param", "s2");
        intent2.putExtras(b2);

        Intent intent3 = new Intent("com.zfq.service.MY_INTENTSERVICE");
        intent3.setPackage(getPackageName());//这里你需要设置你应用的包名
        Bundle b3 = new Bundle();
        b3.putString("param", "s3");
        intent3.putExtras(b3);

        //接着启动多次IntentService,每次启动,都会新建一个工作线程
        //但始终只有一个IntentService实例
        startService(intent1);
        startService(intent2);
        startService(intent3);
    }

    void alarmInit4Activity(Context context,boolean isCancel) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //这里是定时的,这里设置的是每隔2秒打印一次时间=-=,自己改
        int anTime = 2 * 1000;
        /**
         * 第一个参数决定第二个参数的类型,如果是REALTIME的话就用： SystemClock.elapsedRealtime( )方法可以获得系统开机到现在经历的毫秒数 如果是RTC的就用:System.currentTimeMillis()可获得从1970.1.1 0点到 现在做经历的毫秒数
         */
        long triggerAtTime = SystemClock.elapsedRealtime() + anTime;


        /**
         * 如果是通过启动服务来实现闹钟提示的话， PendingIntent对象的获取就应该采用Pending.getService (Context c,int i,Intent intent,int j)方法；
         如果是通过广播来实现闹钟提示的话， PendingIntent对象的获取就应该采用 PendingIntent.getBroadcast (Context c,int i,Intent intent,int j)方法；
         如果是采用Activity的方式来实现闹钟提示的话，PendingIntent对象的获取 就应该采用 PendingIntent.getActivity(Context c,int i,Intent intent,int j) 方法。
         如果这三种方法错用了的话，虽然不会报错，但是看不到闹钟提示效果。
         */
        Intent intent = new Intent(context, NextActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        if (isCancel){
            Log.i(TAG, "alarmInit4Activity isCancel");
            alarmManager.cancel(pendingIntent);
        }else {
            /**
             * type: 有五个可选值:
             AlarmManager.ELAPSED_REALTIME: 闹钟在手机睡眠状态下不可用，该状态下闹钟使用相对时间（相对于系统启动开始），状态值为3;
             AlarmManager.ELAPSED_REALTIME_WAKEUP 闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟也使用相对时间，状态值为2；
             AlarmManager.RTC 闹钟在睡眠状态下不可用，该状态下闹钟使用绝对时间，即当前系统时间，状态值为1；
             AlarmManager.RTC_WAKEUP 表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用绝对时间，状态值为0;
             AlarmManager.POWER_OFF_WAKEUP 表示闹钟在手机关机状态下也能正常进行提示功能，所以是5个状态中用的最多的状态之一， 该状态下闹钟也是用绝对时间，状态值为4；不过本状态好像受SDK版本影响，某些版本并不支持；
             */
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);

            /**
             * 另外:
             从4.4版本后(API 19),Alarm任务的触发时间可能变得不准确,有可能会延时,是系统 对于耗电性的优化,如果需要准确无误可以调用setExtra()方法~
             */
        }
    }

    void alarmInit4Service(boolean isCancel) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        /**
         * 第一个参数决定第二个参数的类型,如果是REALTIME的话就用： SystemClock.elapsedRealtime( )方法可以获得系统开机到现在经历的毫秒数 如果是RTC的就用:System.currentTimeMillis()可获得从1970.1.1 0点到 现在做经历的毫秒数
         */


        /**
         * 如果是通过启动服务来实现闹钟提示的话， PendingIntent对象的获取就应该采用Pending.getService (Context c,int i,Intent intent,int j)方法；
         如果是通过广播来实现闹钟提示的话， PendingIntent对象的获取就应该采用 PendingIntent.getBroadcast (Context c,int i,Intent intent,int j)方法；
         如果是采用Activity的方式来实现闹钟提示的话，PendingIntent对象的获取 就应该采用 PendingIntent.getActivity(Context c,int i,Intent intent,int j) 方法。
         如果这三种方法错用了的话，虽然不会报错，但是看不到闹钟提示效果。
         */
        Intent intent = new Intent();
        intent.setAction("com.zfq.service.LONG_RUNNING_SERVICE");
        intent.setPackage(getPackageName());//这里你需要设置你应用的包名
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

        if (isCancel){
            Log.i(TAG, "alarmInit4Service isCancel");
            alarmManager.cancel(pendingIntent);
        }else {
            /**
             * type: 有五个可选值:
             AlarmManager.ELAPSED_REALTIME: 闹钟在手机睡眠状态下不可用，该状态下闹钟使用相对时间（相对于系统启动开始），状态值为3;
             AlarmManager.ELAPSED_REALTIME_WAKEUP 闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟也使用相对时间，状态值为2；
             AlarmManager.RTC 闹钟在睡眠状态下不可用，该状态下闹钟使用绝对时间，即当前系统时间，状态值为1；
             AlarmManager.RTC_WAKEUP 表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用绝对时间，状态值为0;
             AlarmManager.POWER_OFF_WAKEUP 表示闹钟在手机关机状态下也能正常进行提示功能，所以是5个状态中用的最多的状态之一， 该状态下闹钟也是用绝对时间，状态值为4；不过本状态好像受SDK版本影响，某些版本并不支持；
             */
            //  alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 4 * 1000, pendingIntent);
            /**
             * 另外:
             从4.4版本后(API 19),Alarm任务的触发时间可能变得不准确,有可能会延时,是系统 对于耗电性的优化,如果需要准确无误可以调用setExtra()方法~
             */
        }
    }


    void alarmInit4Broadcast(Context context,boolean isCancel) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        /**
         * 第一个参数决定第二个参数的类型,如果是REALTIME的话就用： SystemClock.elapsedRealtime( )方法可以获得系统开机到现在经历的毫秒数 如果是RTC的就用:System.currentTimeMillis()可获得从1970.1.1 0点到 现在做经历的毫秒数
         */

        /**
         * 如果是通过启动服务来实现闹钟提示的话， PendingIntent对象的获取就应该采用Pending.getService (Context c,int i,Intent intent,int j)方法；
         如果是通过广播来实现闹钟提示的话， PendingIntent对象的获取就应该采用 PendingIntent.getBroadcast (Context c,int i,Intent intent,int j)方法；
         如果是采用Activity的方式来实现闹钟提示的话，PendingIntent对象的获取 就应该采用 PendingIntent.getActivity(Context c,int i,Intent intent,int j) 方法。
         如果这三种方法错用了的话，虽然不会报错，但是看不到闹钟提示效果。
         */
        // Intent intent = new Intent(context,AlarmReceiver.class);
        Intent intent = new Intent("com.zfq.receiver.ALARMRECEIVER");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);


        if (isCancel){
            Log.i(TAG, "alarmInit4Broadcast isCancel");
            alarmManager.cancel(pendingIntent);
        }else{
            /**
             * type: 有五个可选值:
             AlarmManager.ELAPSED_REALTIME: 闹钟在手机睡眠状态下不可用，该状态下闹钟使用相对时间（相对于系统启动开始），状态值为3;
             AlarmManager.ELAPSED_REALTIME_WAKEUP 闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟也使用相对时间，状态值为2；
             AlarmManager.RTC 闹钟在睡眠状态下不可用，该状态下闹钟使用绝对时间，即当前系统时间，状态值为1；
             AlarmManager.RTC_WAKEUP 表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用绝对时间，状态值为0;
             AlarmManager.POWER_OFF_WAKEUP 表示闹钟在手机关机状态下也能正常进行提示功能，所以是5个状态中用的最多的状态之一， 该状态下闹钟也是用绝对时间，状态值为4；不过本状态好像受SDK版本影响，某些版本并不支持；
             */
            // alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 6 * 1000, pendingIntent);
            /**
             * 另外:
             从4.4版本后(API 19),Alarm任务的触发时间可能变得不准确,有可能会延时,是系统 对于耗电性的优化,如果需要准确无误可以调用setExtra()方法~
             */
        }
    }


    public static class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: AlarmReceiver");
        }
    }


}
