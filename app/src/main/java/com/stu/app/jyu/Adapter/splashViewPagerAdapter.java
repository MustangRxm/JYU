package com.stu.app.jyu.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Jack
 * @time 2016/5/6 0006 18:19
 * @des TODO
 */

/**
 * 把Fragment加到一个集合中当作item
 * FragmentStatePagerAdapter 是PagerAdapter的一个子类
 */
public class splashViewPagerAdapter extends PagerAdapter {
    List<View> ViewLists;
//    List<Integer> ViewLists;

    public splashViewPagerAdapter(List<View> ViewLists) {
        this.ViewLists = ViewLists;
    }
    //    public splashViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tittles) {
    //        super(fm);
    //        this.fragments=fragments;
    //        this.tittles=tittles;
    //    }
//class ViewHolder{
//        ImageView iv = null;
//    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(ViewLists.get(position));
//        container.setBackgroundResource(ViewLists.get(position));
//        ((ImageView) container.findViewById(R.id.iv_vp_img)).setImageResource(ViewLists.get(position));
        return ViewLists.get(position);
        //        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(ViewLists.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    //
    //    @Override
    //    public Fragment getItem(int position) {
    //        return ViewLists.get(position);
    //    }

    @Override
    public int getCount() {
        return ViewLists.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //        if (tittles==null){return null;}
        //
        //        return ViewLists.get(position);
        return null;
    }
}
