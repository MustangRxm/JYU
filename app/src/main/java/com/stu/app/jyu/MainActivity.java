package com.stu.app.jyu;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.QualityInfo;
import com.stu.app.jyu.Adapter.FragmentWithViewPagerAdapter;
import com.stu.app.jyu.Adapter.RecyclerViewAdapter;
import com.stu.app.jyu.Domain.AppItem;
import com.stu.app.jyu.Utils.constantsVAR;
import com.stu.app.jyu.view.Fragment.SchoolNews;
import com.stu.app.jyu.view.Fragment.Subscription;
import com.stu.app.jyu.view.Fragment.SubscriptionFind;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView nav_view;
    private Toolbar toolbar;
    private TextView login;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private List<Fragment> mFragmentList;
    private List<String> mFragmentTitleList;
    private android.support.design.widget.TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentManager fm;
    private RecyclerView rv_sch_live_app;
    private RecyclerViewAdapter adapter;
    private List<AppItem> mAppItemList = null;
    private CoordinatorLayout mCoordinatorLayout;
    private View bottomsheet;
    private BottomSheetBehavior behavior;
    private ImageView iv_bottom_sheet_pull;
    private LinearLayout iv__bottom_sheet_pull_parent;
    private RecyclerView rv_sliding_Menu;
    private SimpleDraweeView sv_sliding_img;
    private TextView tv_sliding_userIntroduction;
    private TextView tv_sliding_userOtherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        Fresco.initialize(this);
        //        Bmob.initialize(this,"06beaae856eb317097fd9381493b62ed");
        setContentView(R.layout.activity_main);
        //EventBus.getDefault().
        initView();
        initData();
        //        EventBus.getDefault().register(MainActivity.this);
        initEvent();

    }
    /*
    PostThread：如果使用事件处理函数指定了线程模型为PostThread，那么该事件在哪个线程发布出来的，事件处理函数就会在这个线程中运行，也就是说发布事件和接收事件在同一个线程。在线程模型为PostThread的事件处理函数中尽量避免执行耗时操作，因为它会阻塞事件的传递，甚至有可能会引起ANR。
    MainThread：如果使用事件处理函数指定了线程模型为MainThread，那么不论事件是在哪个线程中发布出来的，该事件处理函数都会在UI线程中执行。该方法可以用来更新UI，但是不能处理耗时操作。
    BackgroundThread：如果使用事件处理函数指定了线程模型为BackgroundThread，那么如果事件是在UI线程中发布出来的，那么该事件处理函数就会在新的线程中运行，如果事件本来就是子线程中发布出来的，那么该事件处理函数直接在发布事件的线程中执行。在此事件处理函数中禁止进行UI更新操作。
    Async：如果使用事件处理函数指定了线程模型为Async，那么无论事件在哪个线程发布，该事件处理函数都会在新建的子线程中执行。同样，此事件处理函数中禁止进行UI更新操作。
    */
    //    @Subscribe(sticky = true, threadMode = ThreadMode.BACKGROUND)
    //    public void getli(List li) {
    //        for (int i = 0; i < li.size(); i++) {
    //            Log.i("testeventbus", "li.get(i)==" + li.get(i) + " in " + Thread.currentThread());
    //        }
    //    }

    protected void onDestroy() {
        super.onDestroy();
        //        EventBus.getDefault().unregister(this);
    }

    private void initEvent() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initData() {
        mAppItemList = new ArrayList<>();
        //        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_game, "Game"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_chat, "吹水地"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_lostfind, "失物认领"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_activity_outline, "线下活动"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_trip, "旅游"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_new_stu_nav, "新生导航"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_chattree, "树洞"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_market, "跳蚤市场"));
        //        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_library, "图书馆"));
        //        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_sk_tree, "技能树"));
        adapter = new RecyclerViewAdapter(this, mAppItemList, R.layout.sch_live_app_recycleview_item);
        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();
        mFragmentList.add(new SchoolNews());
        mFragmentList.add(new Subscription());
        mFragmentList.add(new SubscriptionFind());
        mFragmentTitleList.add("校园资讯");
        mFragmentTitleList.add("订阅");
        mFragmentTitleList.add("发现");

        fm = getSupportFragmentManager();
        //        FragmentStatePagerAdapter 是PagerAdapter的一个子类
        FragmentWithViewPagerAdapter fwp = new FragmentWithViewPagerAdapter(fm, mFragmentList, mFragmentTitleList);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(fwp);
        mTabLayout.setupWithViewPager(mViewPager);
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i(constantsVAR.TAG, "position::" + position);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        rv_sliding_Menu.setAdapter(adapter);
    }

    private void initView() {
        bindView();
//        if (Bmob)
        nav_view.setItemIconTintList(null);//设置这个图标颜色会使用默认色，不会变成灰色
        rv_sliding_Menu.setLayoutManager(new GridLayoutManager(this, 4));
        ProgressiveJpegConfig config = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int i) {
                return 0;
            }

            @Override
            public QualityInfo getQualityInfo(int i) {
                return null;
            }
        };
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(config)
                .build();
        Fresco.initialize(this, imagePipelineConfig);
    }

    private void bindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav_view = (NavigationView) findViewById(R.id.nav_view);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        View slidingView = nav_view.getHeaderView(0);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //        login = (TextView) nav_view.getHeaderView(0).findViewById(R.id.tv_login);
        mTabLayout = (android.support.design.widget.TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        CoordinatorLayout mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl_Main_layout);
        rv_sliding_Menu = (RecyclerView) slidingView.findViewById(R.id.rv_sliding_Menu);
        sv_sliding_img = (SimpleDraweeView) slidingView.findViewById(R.id.sv_sliding_img);
        tv_sliding_userIntroduction = (TextView) slidingView.findViewById(R.id.tv_sliding_userIntroduction);
        tv_sliding_userOtherName = (TextView) slidingView.findViewById(R.id.tv_sliding_userOtherName);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_collect) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
