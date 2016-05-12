package com.stu.app.jyu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.stu.app.jyu.Utils.constantsVAR;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        checkVersion();
        initData();
        initAnimation();
        finish();
    }
private Handler mHandler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case constantsVAR.LoadMainActivity:
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                break;

        }



    }
};
    private void initAnimation() {

    }

    private void initData() {

    }

    private void checkVersion() {
        mHandler.sendEmptyMessage(constantsVAR.LoadMainActivity);

    }
    private void initView() {
        setContentView(R.layout.activity_splash);
        SystemClock.sleep(5000);


    }
}
