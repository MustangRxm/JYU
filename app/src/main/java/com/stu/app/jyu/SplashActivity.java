package com.stu.app.jyu;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stu.app.jyu.Utils.NewsUtils;
import com.stu.app.jyu.Utils.SpTools;
import com.stu.app.jyu.Utils.TimeUtils;
import com.stu.app.jyu.Utils.constantsVAR;

import org.greenrobot.eventbus.EventBus;

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
        checkVersion();
        initData();
        if (SpTools.getBoolean(this, constantsVAR.FirstTimeUse, true)) {
            //                SpTools.putBoolean(this,constantsVAR.FirstTimeUse,false);
            //进入轮播图+动画
            //            mHandler.sendEmptyMessageDelayed(constantsVAR.LoadMainActivity,5000);
        } else {
            //            new Material

        }
        initEvent();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (LoginPopupWindow.isShowing()) {
                LoginPopupWindow.dismiss();
                return true;
            }
            //            return super.onKeyDown(keyCode, event);
        }
        //        else {
        return super.onKeyDown(keyCode, event);
        //        }
    }

    private void initEvent() {
        startActivity.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                //弹登录框
                final TextInputLayout userpwd_textInputLayout;
                final TextInputLayout usernumber_textInputLayout;
                final EditText et_login_userName;
                final EditText et_login_userPassword;
                final Button bt_login;
                final TextView tv_forgetpwd;
                final TextView tv_registered;
                final View LoginView = LayoutInflater.from(SplashActivity.this).inflate(R.layout.activity_log_in, null);
                LoginPopupWindow = new PopupWindow(SplashActivity.this);
                LoginPopupWindow.setContentView(LoginView);
                LoginPopupWindow.setFocusable(true);//如果不设置，不获取焦点，里面的Edittext就无法获得焦点，弹不出虚拟键盘
                LoginPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                LoginPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                LoginPopupWindow.showAtLocation(LoginView, Gravity.CENTER, 0, 0);
                //                LoginPopupWindow.setSoftInputMode(SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                userpwd_textInputLayout = (TextInputLayout) LoginView.findViewById(R.id.userpwd_textInputLayout);
                usernumber_textInputLayout = (TextInputLayout) LoginView.findViewById(R.id.usernumber_textInputLayout);
                et_login_userName = (EditText) LoginView.findViewById(R.id.et_login_userName);
                et_login_userPassword = (EditText) LoginView.findViewById(R.id.et_login_userPassword);
                bt_login = (Button) LoginView.findViewById(R.id.bt_login);
                tv_forgetpwd = (TextView) LoginView.findViewById(R.id.tv_forgetpwd);
                tv_registered = (TextView) LoginView.findViewById(R.id.tv_registered);
                //                et_login_userName.setFocusable(true);
                //                et_login_userName.setShowSoftInputOnFocus(true);
                tv_registered.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View LoginForget = LayoutInflater.from(LoginView.getContext()).inflate(R.layout.activity_registered,null);
                        PopupWindow LoginRegisteredPopWindow = new PopupWindow(LoginView.getContext());
                        LoginRegisteredPopWindow.setContentView(LoginForget);
                        LoginRegisteredPopWindow.setFocusable(true);
                        LoginRegisteredPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        LoginRegisteredPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                        LoginRegisteredPopWindow.showAtLocation(LoginForget, Gravity.CENTER, 0, 0);
                        Toast.makeText(LoginView.getContext(),"hehe",Toast.LENGTH_LONG).show();
                    }
                });
                tv_forgetpwd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View LoginForget = LayoutInflater.from(LoginView.getContext()).inflate(R.layout.activity_forgetpwd,null);
                PopupWindow LoginForgetPopWindow = new PopupWindow(LoginView.getContext());
                        LoginForgetPopWindow.setContentView(LoginForget);
                        LoginForgetPopWindow.setFocusable(true);
                        LoginForgetPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        LoginForgetPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                        LoginForgetPopWindow.showAtLocation(LoginForget, Gravity.CENTER, 0, 0);
                        Toast.makeText(LoginView.getContext(),"hehe",Toast.LENGTH_LONG).show();
                     }
                });
                bt_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //验证号码和密码的正确性
                        //将号码和密码发送给服务器验证登录
                    }
                });


                et_login_userName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        Log.i("test20160525", "before::s==" + s);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Log.i("test20160525", "ing::s==" + s);
                        Log.i("test20160525", "ing::s.size==" + s.length());
                        Log.i("test20160525", "ing::count==" + count);
                        if (TextUtils.isEmpty(s)) {
                            usernumber_textInputLayout.setErrorEnabled(true);
                            usernumber_textInputLayout.setError("号码不能为空");
                        } else if (s.length() != 11) {
                            usernumber_textInputLayout.setErrorEnabled(true);
                            usernumber_textInputLayout.setError("请输入正确的号码");
                        } else {
                            usernumber_textInputLayout.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.i("test20160525", "after::s==" + s);

                    }

                });
                et_login_userPassword.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (TextUtils.isEmpty(s)) {
                            userpwd_textInputLayout.setErrorEnabled(true);
                            userpwd_textInputLayout.setError("密码不能为空");
                        } else if (s.length() < 6) {
                            userpwd_textInputLayout.setErrorEnabled(true);
                            userpwd_textInputLayout.setError("请输入长度大于6位字符的密码");
                        } else {
                            userpwd_textInputLayout.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });
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
