package com.stu.app.jyu;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.stu.app.jyu.Adapter.FragmentWithViewPagerAdapter;
import com.stu.app.jyu.Adapter.RecyclerViewAdapter;
import com.stu.app.jyu.Domain.AppItem;
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
    FloatingActionButton fab;

    DrawerLayout drawer;
    NavigationView navigationView;
    private List<Fragment> mFragmentList;
    private List<String> mFragmentTitleList;
    private android.support.design.widget.TabLayout mTabLayout;
    ViewPager mViewPager;
    FragmentManager fm;
    private RecyclerView rv_sch_live_app;
    private RecyclerViewAdapter adapter;
    private List<AppItem> mAppItemList = null;
    private CoordinatorLayout mCoordinatorLayout;
    private View bottomsheet;
    private BottomSheetBehavior behavior;
    private ImageView iv_bottom_sheet_pull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        if()
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();

    }

    private void initEvent() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "please login", Toast.LENGTH_LONG).show();
            }
        });

        //        fab.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                        .setAction("Action", null).show();
        //            }
        //        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        iv_bottom_sheet_pull.setImageResource(R.drawable.ic_sch_live_pull_up);
                        Log.i("BottomSheetBehavior__",newState+"");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetBehavior__",newState+"");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        iv_bottom_sheet_pull.setImageResource(R.drawable.ic_sch_live_pull_down);
                        Log.i("BottomSheetBehavior__",newState+"");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        Log.i("BottomSheetBehavior__",newState+"");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetBehavior__",newState+"");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        iv_bottom_sheet_pull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                        .setAction("Action", null).show();
                //                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                if(behavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
    }

    private void initData() {
        mAppItemList = new ArrayList<>();
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_game, "Game"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_chat, "吹水地"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_lostfind, "失物认领"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_activity_outline, "线下活动"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_trip, "旅游"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_new_stu_nav, "新生导航"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_chattree, "树洞"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_market, "跳蚤市场"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_library, "图书馆"));
        mAppItemList.add(new AppItem(R.drawable.ic_sch_live_item_sk_tree, "技能树"));
        adapter = new RecyclerViewAdapter(this, mAppItemList, R.layout.sch_live_app_recycleview_item);
        //        rv_sch_live_app.setAnimation(new DefaultItemAnimator());
        rv_sch_live_app.setAdapter(adapter);
        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();
        //        mFragmentList.add(new SchoolLiveApplication());
        mFragmentList.add(new SchoolNews());
        mFragmentList.add(new Subscription());
        mFragmentList.add(new SubscriptionFind());
        //        mFragmentTitleList.add("校园应用");
        mFragmentTitleList.add("校园新闻");
        mFragmentTitleList.add("订阅");
        mFragmentTitleList.add("发现");

        fm = getSupportFragmentManager();
        //        FragmentStatePagerAdapter 是PagerAdapter的一个子类
        FragmentWithViewPagerAdapter fwp = new FragmentWithViewPagerAdapter(fm, mFragmentList, mFragmentTitleList);
        mViewPager.setAdapter(fwp);
        mTabLayout.setupWithViewPager(mViewPager);







    }

    private void initView() {
        bindView();
        nav_view.setItemIconTintList(null);//设置这个图标颜色会使用默认色，不会变成灰色
        //        fab = (FloatingActionButton) findViewById(R.id.fab);
        rv_sch_live_app.setLayoutManager(new GridLayoutManager(this, 4));
        behavior =  BottomSheetBehavior.from(bottomsheet);
    }

    private void bindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        login = (TextView) nav_view.getHeaderView(0).findViewById(R.id.tv_login);
        mTabLayout = (android.support.design.widget.TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        rv_sch_live_app = (RecyclerView) findViewById(R.id.rv_sch_live_app);
        CoordinatorLayout mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.cl_Main_layout);
        bottomsheet = mCoordinatorLayout.findViewById(R.id.nsv);
        iv_bottom_sheet_pull = (ImageView) findViewById(R.id.iv_bottom_sheet_pull);
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

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_find) {
            item.setChecked(false);

        } else if (id == R.id.nav_collect) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
