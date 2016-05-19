package com.louisgeek.louisservicedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by louisgeek on 2016/5/19.
 */
public class NextActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button = new Button(this);
        button.setText("由AlarmManager启动的Btn");
        setContentView(button);
    }
}
