package com.stu.app.jyu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.stu.app.jyu.Adapter.splashViewPagerAdapter;
import com.stu.app.jyu.Domain.JyuUser;
import com.stu.app.jyu.Utils.NewsUtils;
import com.stu.app.jyu.Utils.SpTools;
import com.stu.app.jyu.Utils.TimeUtils;
import com.stu.app.jyu.Utils.constantsVAR;
import com.stu.app.jyu.view.Activity.SignInActivity;
import com.stu.app.jyu.view.widget.CircleIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity   extends AppCompatActivity {
    private int versionCode;
    private AlphaAnimation aa;
    private String versionName;
    private RelativeLayout rl_splash_activity;
    private Button startActivity;
    private PopupWindow LoginPopupWindow;
    private ViewPager vp_splash;
    private CircleIndicator circleIndicator;
    View view = null;
    splashViewPagerAdapter fwp;
    List<View> list=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
                    finish();
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
        list=null;
        view=null;
        fwp=null;
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

        list = new ArrayList<>();
        ImageView imageView ;

                view = new View(this);
        view.setBackgroundResource(R.mipmap.login_background);
        view.setAlpha(0.7f);
        list.add(view);
        view = new View(this);
        view.setBackgroundResource(R.drawable.registered_background);
        view.setAlpha(0.7f);
        list.add(view);
        view = new View(this);
        view.setBackgroundResource(R.drawable.forget_find_backgroud);
        view.setAlpha(0.7f);
        list.add(view);

//        FragmentManager fm =getSupportFragmentManager();
//        List<View> list = new ArrayList<>();
//        imageView = new ImageView(this);
//        imageView.setAlpha(0.7f);
//        imageView.setImageResource(R.drawable.forget_find_backgroud);
//        list.add(imageView);
//        imageView = new ImageView(this);
//        imageView.setAlpha(0.7f);
//        imageView.setImageResource(R.drawable.registered_background);
//        list.add(imageView);
//        imageView = new ImageView(this);
//        imageView.setAlpha(0.7f);
//        imageView.setImageResource(R.mipmap.login_background);
//        list.add(imageView);
//        List<Integer> list = new ArrayList<>();
//        list.add(R.mipmap.login_background);
//        list.add(R.drawable.registered_background);
//        list.add(R.drawable.forget_find_backgroud);
        splashViewPagerAdapter fwp;
        fwp = new splashViewPagerAdapter( list);

        vp_splash.setAdapter(fwp);
        circleIndicator.setViewPager(vp_splash);
        //下面两个步骤需要扔到线程里
        String year_month = TimeUtils.getServerTime(SplashActivity.this, "yy-MM");
        Log.i("20160601","now time is ::"+year_month);
        NewsUtils.getNewsData(SplashActivity.this, year_month);

    }

    private void checkVersion() {
        //        如果有新版本，弹框提示用户
        //        否则进入splash页面
    }

    private void initView() {
        setContentView(R.layout.activity_splash);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarAlpha(0f);
//        tintManager.setTintColor(Color.BLUE);
        startActivity = (Button) findViewById(R.id.button);
        rl_splash_activity = (RelativeLayout) findViewById(R.id.rl_splash_activity);
//        rl_splash_activity.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        vp_splash = (ViewPager) findViewById(R.id.vp_splash);
vp_splash.setOffscreenPageLimit(2);
        circleIndicator = (CircleIndicator) findViewById(R.id.indicator);

    }

}
