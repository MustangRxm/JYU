package com.stu.app.jyu.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.stu.app.jyu.Adapter.FragmentWithViewPagerAdapter;
import com.stu.app.jyu.Domain.ActivityTYPE;
import com.stu.app.jyu.R;
import com.stu.app.jyu.Utils.constantsVAR;
import com.stu.app.jyu.view.Fragment.ItemFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class InnerAppFunctionActivity extends AppCompatActivity {
    private ActivityTYPE type;
    private List<Fragment> mFragmentList;
    private List<String> mFragmentTitleList;
    private FragmentManager fm;
    public static final String App_Function = "App_Function";
    public static final int LostFind = 0x01;
    public static final int Hollow = 0x02;
    public static final int market = 0x04;

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void receiverActivityTYPE(ActivityTYPE type) {
        this.type = type;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_inner_app_function);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TabLayout tly_inner_app = (TabLayout) findViewById(R.id.tly_inner_app);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.vp_inner_app);
        Toolbar mTabLayout = (Toolbar) findViewById(R.id.tb_inner_app);

        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();
        mFragmentList.add(ItemFragment.newInstance(LostFind));
        mFragmentList.add(ItemFragment.newInstance(Hollow));
        mFragmentList.add(ItemFragment.newInstance(market));
        mFragmentTitleList.add("失物认领");
        mFragmentTitleList.add("树洞");
        mFragmentTitleList.add("跳蚤市场");
        //        mFragmentTitleList.add("跳蚤市场");
        fm = getSupportFragmentManager();
        FragmentWithViewPagerAdapter fwp = new FragmentWithViewPagerAdapter(fm, mFragmentList, mFragmentTitleList);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(fwp);
        tly_inner_app.setupWithViewPager(mViewPager);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "now position::"+  tly_inner_app.getSelectedTabPosition(), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                //选择状态可以在tablayout.addtab(TabLayout.Tab tab, boolean setSelected)的时候设置
                Log.i(constantsVAR.TAG,"POSITION::"+tly_inner_app.getSelectedTabPosition());
                int position =0x01<<tly_inner_app.getSelectedTabPosition();
                switch (position){
                    case LostFind:
                        Bundle args = new Bundle();
                        args.putInt(App_Function,LostFind);
                        Intent intent =new Intent(InnerAppFunctionActivity.this,LostFindAddContentActivity.class);
                        intent.putExtras(args);
                        startActivityForResult(intent,LostFind,args);
                        break;
                    case Hollow:
//                        startActivityForResult(new Intent(InnerAppFunctionActivity.this,LostFindAddContentActivity.class),LostFind);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(),"get back request code"+requestCode,Toast.LENGTH_LONG).show();
//        switch (requestCode){
//            case LostFind:
//                Toast.makeText(getApplicationContext(),"get back request code"+requestCode,Toast.LENGTH_LONG).show();
//                break;
//            case Hollow:
//                Toast.makeText(getApplicationContext(),"get back request code"+requestCode,Toast.LENGTH_LONG).show();
//            case market:
//                Toast.makeText(getApplicationContext(),"get back request code"+requestCode,Toast.LENGTH_LONG).show();
//        }

    }
//
//    @Override
//    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
//        super.startActivityForResult(intent, requestCode, options);
//        switch (requestCode){
//            case LostFind:
//                Toast.makeText(getApplicationContext(),"get back request code"+requestCode,Toast.LENGTH_LONG).show();
//                break;
//            case Hollow:
//                Toast.makeText(getApplicationContext(),"get back request code"+requestCode,Toast.LENGTH_LONG).show();
//            case market:
//                Toast.makeText(getApplicationContext(),"get back request code"+requestCode,Toast.LENGTH_LONG).show();
//        }
//
//    }
}
