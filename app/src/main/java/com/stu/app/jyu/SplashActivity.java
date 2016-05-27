package com.stu.app.jyu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.stu.app.jyu.Domain.JyuUser;
import com.stu.app.jyu.Utils.NewsUtils;
import com.stu.app.jyu.Utils.SpTools;
import com.stu.app.jyu.Utils.TimeUtils;
import com.stu.app.jyu.Utils.constantsVAR;
import com.stu.app.jyu.view.Activity.SignInActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private int versionCode;
    private AlphaAnimation aa;
    private String versionName;
    private RelativeLayout rl_splash_activity;
    private List list = null;
    private Button startActivity;
    private PopupWindow LoginPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        EventBus.getDefault().register(this);
        checkVersion();
        initData();
        if (SpTools.getBoolean(this, constantsVAR.FirstTimeUse, true)) {
            //进入轮播图+动画
            startActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
//                    finish();
                }
            });
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
        //        initEvent();

    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void deal_withUser(JyuUser user) {
        Log.i(constantsVAR.TAG, "_____Enter eventbus______");

        Toast.makeText(SplashActivity.this, "ueser " + user.getUsername() + "login success", Toast.LENGTH_LONG).show();
    }


    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case constantsVAR.LoadMainActivity:
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    //                    overridePendingTransition();
                    break;
            }
        }
    };

    private void initAnimation() {
        aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(3000);
        aa.setFillAfter(true);
        rl_splash_activity.setAnimation(aa);
        rl_splash_activity.startAnimation(aa);

    }

    private void initData() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            versionName = packageInfo.versionName;
        } catch (Exception e) {

        } finally {

        }
        String year_month = TimeUtils.getServerTime(SplashActivity.this, "yy-MM");
        NewsUtils.getNewsData(SplashActivity.this, year_month);
    }

    private void checkVersion() {
        //        如果有新版本，弹框提示用户
        //        否则进入splash页面
    }

    private void initView() {
        setContentView(R.layout.activity_splash);

        startActivity = (Button) findViewById(R.id.button);
        rl_splash_activity = (RelativeLayout) findViewById(R.id.rl_splash_activity);
    }
}
