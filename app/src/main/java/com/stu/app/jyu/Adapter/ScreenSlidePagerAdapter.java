package com.stu.app.jyu.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.stu.app.jyu.view.Fragment.ScreenSlidePageFragment;

/**
 * @author Jack
 * @time 2016/5/27 0027 15:00
 * @des TODO
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    int Num_Page ;
    FragmentManager fm;
    public ScreenSlidePagerAdapter(FragmentManager fm,int Num_Page) {
        super(fm);
        this.fm = fm;
        this.Num_Page = Num_Page;
    }

    @Override
    public Fragment getItem(int position) {
        return new ScreenSlidePageFragment();
    }

    @Override
    public int getCount() {
        return Num_Page;
    }
}
