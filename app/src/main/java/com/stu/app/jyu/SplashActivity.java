package com.stu.app.jyu;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stu.app.jyu.Domain.JyuUser;
import com.stu.app.jyu.Utils.NewsUtils;
import com.stu.app.jyu.Utils.SpTools;
import com.stu.app.jyu.Utils.TimeUtils;
import com.stu.app.jyu.Utils.constantsVAR;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

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
        }
        return super.onKeyDown(keyCode, event);
        //        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void deal_withUser(JyuUser user) {
        Log.i(constantsVAR.TAG, "_____Enter eventbus______");

        Toast.makeText(SplashActivity.this,"ueser "+user.getUsername()+"login success",Toast.LENGTH_LONG).show();
        //        Log.i(constantsVAR.TAG, "user.name==" + user.getUsername());
        //        Log.i(constantsVAR.TAG, "user.pnb==" + user.getMobilePhoneNumber());
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
                final ProgressBar pb_log_in_wait;

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
                pb_log_in_wait = (ProgressBar) LoginView.findViewById(R.id.pb_log_in_wait);
                tv_forgetpwd = (TextView) LoginView.findViewById(R.id.tv_forgetpwd);
                tv_registered = (TextView) LoginView.findViewById(R.id.tv_registered);

                tv_registered.setOnClickListener(new View.OnClickListener() {

                    /**注册模块**/
                    @Override
                    public void onClick(View v) {
                        final TextInputLayout et_login_registered_userPhoneNum_textInputLayout;
                        final TextInputLayout et_login_registered_email_textInputLayout;
                        final TextInputLayout et_login_registered_msgNum_textInputLayout;
                        final TextInputLayout et_login_registered_userPassword_textInputLayout;
                        final EditText et_login_registered_userPhoneNum;
                        final EditText et_login_registered_email;
                        final EditText et_login_registered_userPassword;
                        final EditText et_login_registered_msgNum;
                        final Button bt_login_registered_commit;
                        final ProgressBar pb_wait;
                        View LoginRegistered = LayoutInflater
                                .from(LoginView.getContext()).inflate(R.layout.activity_registered, null);
                        final PopupWindow LoginRegisteredPopWindow = new PopupWindow(LoginView.getContext());
                        LoginRegisteredPopWindow.setContentView(LoginRegistered);
                        LoginRegisteredPopWindow.setFocusable(true);
                        LoginRegisteredPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        LoginRegisteredPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                        LoginRegisteredPopWindow.showAtLocation(LoginRegistered, Gravity.CENTER, 0, 0);
                        et_login_registered_userPhoneNum_textInputLayout = (TextInputLayout) LoginRegistered.findViewById(R.id.et_login_registered_userPhoneNum_textInputLayout);
                        et_login_registered_email_textInputLayout = (TextInputLayout) LoginRegistered.findViewById(R.id.et_login_registered_email_textInputLayout);
                        et_login_registered_msgNum_textInputLayout = (TextInputLayout) LoginRegistered.findViewById(R.id.et_login_registered_msgNum_textInputLayout);
                        et_login_registered_userPassword_textInputLayout = (TextInputLayout) LoginRegistered.findViewById(R.id.et_login_registered_userPassword_textInputLayout);
                        et_login_registered_userPhoneNum = (EditText) LoginRegistered.findViewById(R.id.et_login_registered_userPhoneNum);
                        et_login_registered_email = (EditText) LoginRegistered.findViewById(R.id.et_login_registered_email);
                        et_login_registered_msgNum = (EditText) LoginRegistered.findViewById(R.id.et_login_registered_msgNum);
                        et_login_registered_userPassword = (EditText) LoginRegistered.findViewById(R.id.et_login_registered_userPassword);
                        bt_login_registered_commit = (Button) LoginRegistered.findViewById(R.id.bt_login_registered_commit);
                        pb_wait = (ProgressBar) LoginRegistered.findViewById(R.id.pb_wait);
                        bt_login_registered_commit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int SignUpFlag = 0x00;
                                hideKeyboard();
                                String userPhoneNum = et_login_registered_userPhoneNum_textInputLayout.getEditText().getText().toString();
                                //验证功能暂时不用
                                String msgNum = et_login_registered_msgNum_textInputLayout.getEditText().getText().toString();
                                String email = et_login_registered_email_textInputLayout.getEditText().getText().toString();
                                String userPassword = et_login_registered_userPassword_textInputLayout.getEditText().getText().toString();
                                if (userPhoneNum.length() != 11) {
//                                    Log.i(constantsVAR.TAG, "phone" + userPhoneNum.toString());
//                                    Log.i(constantsVAR.TAG, "phonesize" + userPhoneNum.toString().length());
                                    et_login_registered_userPhoneNum_textInputLayout.setError("请输入正确的手机号码");
                                    SignUpFlag &= (~(0x01 << 0));
                                    Log.i(constantsVAR.TAG, "phonenum::flag::" + Integer.toHexString(SignUpFlag));
                                } else {
                                    et_login_registered_userPhoneNum_textInputLayout.setErrorEnabled(false);
                                    SignUpFlag |= (0x01 << 0);
                                    Log.i(constantsVAR.TAG, "phonenum::flag::" + Integer.toHexString(SignUpFlag));
                                }
                                if (userPassword.length() < 6) {
                                    SignUpFlag &= (~(0x01 << 1));
                                    Log.i(constantsVAR.TAG, "userPassword::flag::" + Integer.toHexString(SignUpFlag));
                                    et_login_registered_userPassword_textInputLayout.setError("请输入大于6个字符长度的密码");
                                } else {
                                    et_login_registered_userPassword_textInputLayout.setErrorEnabled(false);
                                    SignUpFlag |= (0x01 << 1);
                                    Log.i(constantsVAR.TAG, "userPassword::flag::" + Integer.toHexString(SignUpFlag));
                                }
                                if (!validateEmail(email)) {
                                    et_login_registered_email_textInputLayout.setError("请输入正确的邮箱地址");
                                    SignUpFlag &= (~(0x01 << 2));
                                    Log.i(constantsVAR.TAG, "email::flag::" + Integer.toHexString(SignUpFlag));
                                } else {

                                    et_login_registered_email_textInputLayout.setErrorEnabled(false);
                                    SignUpFlag |= (0x01 << 2);
                                    Log.i(constantsVAR.TAG, "email::flag::" + Integer.toHexString(SignUpFlag));

                                }

                                if (SignUpFlag == 0x07) {
                                    final JyuUser user = new JyuUser();
                                    user.setEmail(email);
                                    user.setUsername(userPhoneNum);
                                    user.setPassword(userPassword);
                                    user.setMobilePhoneNumber(userPhoneNum);
                                    pb_wait.setVisibility(View.VISIBLE);
                                    //                                    SystemClock.sleep(500);
                                    //                                    EventBus.getDefault().post(user);
                                    user.signUp(getApplicationContext(), new SaveListener() {
                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(getApplicationContext(), "signup success", Toast.LENGTH_LONG).show();
                                            pb_wait.setVisibility(View.GONE);
                                            et_login_userName.setText(user.getUsername());
                                            //需要设置动画效果
                                            LoginRegisteredPopWindow.dismiss();

                                        }

                                        @Override
                                        public void onFailure(int i, String s) {
                                            Toast.makeText(getApplicationContext(), "signup fail:::" + s, Toast.LENGTH_LONG).show();
                                            pb_wait.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            }
                        });


                    }
                });
                /**忘记找回模块**/
                tv_forgetpwd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View LoginForget = LayoutInflater.from(LoginView.getContext()).inflate(R.layout.activity_forgetpwd, null);
                        PopupWindow LoginForgetPopWindow = new PopupWindow(LoginView.getContext());
                        LoginForgetPopWindow.setContentView(LoginForget);
                        LoginForgetPopWindow.setFocusable(true);
                        LoginForgetPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        LoginForgetPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                        LoginForgetPopWindow.showAtLocation(LoginForget, Gravity.CENTER, 0, 0);
                        //                        Toast.makeText(LoginView.getContext(),"hehe",Toast.LENGTH_LONG).show();
                    }
                });
                /******************登录模块************************/
                bt_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //验证号码和密码的正确性
                        //将号码和密码发送给服务器验证登录
                        int SignUpFlag = 0x00;
                        hideKeyboard();
                        String userPhoneNum = usernumber_textInputLayout.getEditText().getText().toString();
                        String userPassword = userpwd_textInputLayout.getEditText().getText().toString();
                        if (userPhoneNum.length() != 11) {
                            Log.i(constantsVAR.TAG, "phone" + userPhoneNum.toString());
                            Log.i(constantsVAR.TAG, "phonesize" + userPhoneNum.toString().length());
                            usernumber_textInputLayout.setError("请输入正确的手机号码");
                            SignUpFlag &= (~(0x01 << 0));
                            Log.i(constantsVAR.TAG, "phonenum::flag::" + Integer.toHexString(SignUpFlag));
                        } else {
                            usernumber_textInputLayout.setErrorEnabled(false);
                            SignUpFlag |= (0x01 << 0);
                            Log.i(constantsVAR.TAG, "phonenum::flag::" + Integer.toHexString(SignUpFlag));
                        }
                        if (userPassword.length() < 6) {
                            SignUpFlag &= (~(0x01 << 1));
                            Log.i(constantsVAR.TAG, "userPassword::flag::" + Integer.toHexString(SignUpFlag));
                            userpwd_textInputLayout.setError("请输入大于6个字符长度的密码");
                        } else {
                            userpwd_textInputLayout.setErrorEnabled(false);
                            SignUpFlag |= (0x01 << 1);
                            Log.i(constantsVAR.TAG, "userPassword::flag::" + Integer.toHexString(SignUpFlag));
                        }

                        if (SignUpFlag == 0x03) {
                            final JyuUser user = new JyuUser();
                            //                            user.setEmail(email);
                            user.setUsername(userPhoneNum);
                            user.setPassword(userPassword);
                            user.setMobilePhoneNumber(userPhoneNum);
                            pb_log_in_wait.setVisibility(View.VISIBLE);
                            user.login(getApplicationContext(), new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(getApplicationContext(), "signin success", Toast.LENGTH_LONG).show();
                                    pb_log_in_wait.setVisibility(View.GONE);
//                                    et_login_userName.setText(user.getUsername());
                                    //需要设置动画效果
                                  JyuUser user_obtain = BmobUser.getCurrentUser(getApplicationContext(),JyuUser.class);
//                                    EventBus.getDefault().post(user_obtain);
                                    LoginPopupWindow.dismiss();
                                    Message msg = mHandler.obtainMessage();
                                    msg.obj=user_obtain;
                                    msg.what=constantsVAR.LoadMainActivity;
                                    mHandler.sendMessage(msg);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(getApplicationContext(), "signup fail::"+s, Toast.LENGTH_LONG).show();
                                    pb_log_in_wait.setVisibility(View.GONE);
                                }
                            });
                        }
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
