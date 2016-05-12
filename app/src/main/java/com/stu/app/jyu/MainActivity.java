package com.stu.app.jyu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stu.app.jyu.Adapter.FragmentWithViewPagerAdapter;
import com.stu.app.jyu.view.Fragment.SchoolLiveApplication;
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
    }

    private void initData() {
        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();
        mFragmentList.add(new SchoolLiveApplication());
        mFragmentList.add(new SchoolNews());
        mFragmentList.add(new Subscription());
        mFragmentList.add(new SubscriptionFind());
        mFragmentTitleList.add("校园应用");
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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setItemIconTintList(null);//设置这个图标颜色会使用默认色，不会变成灰色
//        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        login = (TextView) nav_view.getHeaderView(0).findViewById(R.id.tv_login);
        mTabLayout = (android.support.design.widget.TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
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
